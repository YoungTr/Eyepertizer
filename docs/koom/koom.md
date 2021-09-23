### KOOM 源码分析

### Java

#### 1. 初始化

*KOOM#init(Application app)*

```java
  /**
   * KOOM entry point, make sure be called in the main thread!
   *
   * @param application application needed
   */
  public static void init(Application application) {
    KLog.init(new KLog.DefaultLogger());

    if (inited) {
      KLog.i(TAG, "already init!");
      return;
    }
    inited = true;

    if (koom == null) {
      koom = new KOOM(application);
    }

    koom.start();
  }
```

1. 实例化 KOOM 对象，KOOM 构建函数会实例化 KOOMInteral 对象。
2. 调用 `koom.start()` ，即调用 `KOOMInteral#start()`

*KOOMInteral#start()*

```java
  public void start() {
    HandlerThread koomThread = new HandlerThread("koom");
    koomThread.start();
    koomHandler = new Handler(koomThread.getLooper());
    startInKOOMThread();
  }
```

1. 启动一个名为 “koom” 的 HandlerThread 线程，实例化 koomHandler 对象
2. `startInKOOMThread()` 开始轮询

*KOOMInteral#startInKOOMThread()*

```java
  private void startInKOOMThread() {
    koomHandler.postDelayed(this::startInternal, KConstants.Perf.START_DELAY);
  }
```

*KOOMInteral#startInternal()*

```java
  private void startInternal() {
    try {
      if (started) {
        KLog.i(TAG, "already started!");
        return;
      }
      started = true;

      heapDumpTrigger.setHeapDumpListener(this);
      heapAnalysisTrigger.setHeapAnalysisListener(this);

      // 可用性检查
      if (KOOMEnableChecker.doCheck() != KOOMEnableChecker.Result.NORMAL) {
        KLog.e(TAG, "koom start failed, check result: " + KOOMEnableChecker.doCheck());
        return;
      }

      ReanalysisChecker reanalysisChecker = new ReanalysisChecker();
      if (reanalysisChecker.detectReanalysisFile() != null) {
        KLog.i(TAG, "detected reanalysis file");
        heapAnalysisTrigger
            .trigger(TriggerReason.analysisReason(TriggerReason.AnalysisReason.REANALYSIS));
        return;
      }

      heapDumpTrigger.startTrack();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
```

1. 设置 HeapDumpTrigger，HeapAnalysisTrigger 监听，接口实现是 KOOMInternal 本身。
2. `KOOMEnableChecker.doCheck()` ，可用性检测：
   * 版本检测，Android 5 ~ Android 10
   * 存储空间是否足够，存储空间大于 5 G，默认存储空间是` "/data/data/" + KGlobalConfig.getApplication().getPackageName() + "/cache/" + KOOM_DIR `
   * 默认每个用户应用生命周期内只运行 15 天
   * 默认每个用户应用生命周期只触发 3 次
   * 只在主进程开启
3. `reanalysisChecker.detectReanalysisFile()` ，报告文件检查
   * 是否存在报告文件
   * 如果存在报告文件，是否已经解析 hprof 文件完成，
   * 如果没有解析 hprof 文件完成，是否已经解析了两次
   * 如果存在，则返回 `KHeapFile` ，直接触发解析`REANALYSIS` ，`heapAnalysisTrigger.trigger(TriggerReason.analysisReason(TriggerReason.AnalysisReason.REANALYSIS));`
4. 如果检测都通过，则调用 `heapDumpTrigger.startTrack()` 开始追踪

*HeapDumpTrigger#startTrack()* 

```java
  @Override
  public void startTrack() {
    monitorManager.start();
    monitorManager.setTriggerListener((monitorType, reason) -> {
      trigger(reason);
      return true;
    });
  }
```

HeapDumpTrigger 有两个变量 `MonitorManager` 和 `HeapDumper`

```java
  public HeapDumpTrigger() {
    monitorManager = new MonitorManager();
    monitorManager.addMonitor(new HeapMonitor());
    heapDumper = new ForkJvmHeapDumper();
  }
```

MonitorManager 会添加一个 `HeapMonitor`

*MonitorManager#start()*

```java
  public void start() {
    monitorThread.start(monitors);
  }
```

*MonitorThread#start(List<Monitor> monitors)*

```java
  public void start(List<Monitor> monitors) {
    stop = false;

    Log.i(TAG, "start");

    List<Runnable> runnables = new ArrayList<>();
    for (Monitor monitor : monitors) {
      monitor.start();
      runnables.add(new MonitorRunnable(monitor));
    }
    for (Runnable runnable : runnables) {
      handler.post(runnable);
    }
  }
```

1. 遍历 monitors ，调用 `monitor start()` 方法，这里 monitors 只有前面添加的 `HeapMonitor`，runnables 添加 MonitorRunnable 对象。
2. `handler post(runnable)` 启动所有的 Runnable，这个 handler 是启动了一个名为 `MonitorThread` 的 `HandlerThread`

*MonitorRunnable#run()*

```java
    @Override
    public void run() {
      if (stop) {
        return;
      }

      if (KConstants.Debug.VERBOSE_LOG) {
        Log.i(TAG, monitor.monitorType() + " monitor run");
      }

      if (monitor.isTrigger()) {
        Log.i(TAG, monitor.monitorType() + " monitor "
            + monitor.monitorType() + " trigger");
        stop = monitorTriggerListener
            .onTrigger(monitor.monitorType(), monitor.getTriggerReason());
      }

      if (!stop) {
        handler.postDelayed(this, monitor.pollInterval());
      }
    }
  }

```

1. `monitor#isTrigger()` monitor 开始检测，如果检测触发了异常，则调用 `monitorTriggerListener
               .onTrigger(monitor.monitorType(), monitor.getTriggerReason())` 去执行 dump 和 analysis 逻辑。
2. 若果没有触发异常，延时继续执行该 runnable，延时时间默认 5 s，该值在 `HeapThreshold` 中定义。

#### 2. 阈值检测

*HeapMonitor#isTrigger()*

```java
  @Override
  public boolean isTrigger() {
    if (!started) {
      return false;
    }

    HeapStatus heapStatus = currentHeapStatus();

    if (heapStatus.isOverMaxThreshold) {
      // 已达到最大阀值，强制触发trigger，防止后续出现大内存分配导致OOM进程Crash，无法触发trigger
      KLog.i(TAG, "heap used is over max ratio, force trigger and over times reset to 0");
      currentTimes = 0;
      return true;
    }

    if (heapStatus.isOverThreshold) {
      KLog.i(TAG, "heap status used:" + heapStatus.used / KConstants.Bytes.MB
              + ", max:" + heapStatus.max / KConstants.Bytes.MB
              + ", last over times:" + currentTimes);
      if (heapThreshold.ascending()) {
        if (lastHeapStatus == null || heapStatus.used >= lastHeapStatus.used || heapStatus.isOverMaxThreshold) {
          currentTimes++;
        } else {
          KLog.i(TAG, "heap status used is not ascending, and over times reset to 0");
          currentTimes = 0;
        }
      } else {
        currentTimes++;
      }
    } else {
      currentTimes = 0;
    }

    lastHeapStatus = heapStatus;
    return currentTimes >= heapThreshold.overTimes();
  }

```

1. `currentHeapStatus` 获取当前 Heap status，如果达到最大阈值直接返回 true（触发trigger），如果达到阈值，连续检测到达到阈值次数大于等于 `heapThreshold.overTimes()` （默认 3 次），则返回 true（触发trigger）。

*HeapMonitor#currentHeapStatus()*

```java
  private HeapStatus currentHeapStatus() {
    HeapStatus heapStatus = new HeapStatus();
    heapStatus.max = Runtime.getRuntime().maxMemory();
    heapStatus.used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    KLog.i(TAG, 100.0f * heapStatus.used / heapStatus.max + " " + heapThreshold.value());
    float heapInPercent = 100.0f * heapStatus.used / heapStatus.max;
    heapStatus.isOverThreshold = heapInPercent > heapThreshold.value();
    heapStatus.isOverMaxThreshold = heapInPercent > heapThreshold.maxValue();
    return heapStatus;
  }
```

1. 获取最大内存、已用内存、可用内存，计算出使用的内存占用最大内存的百分比。
2. `isOverThreshold` : heapInPercent > heapThreshold.value()，`value` 会根据不同的设备生成不同的值（在 `KConstants.HeapThreshold.getDefaultPercentRation()` 定义 ）。
3. `isOverMaxThreshold` : heapInPercent > heapThreshold.maxValue()， `maxValue` 默认 95。

*KConstants.HeapThreshold.getDefaultPercentRation()*

```java
    public static float getDefaultPercentRation() {
      int maxMem = (int) (Runtime.getRuntime().maxMemory() / MB);
      if (Debug.VERBOSE_LOG) {
        KLog.i("koom", "max mem " + maxMem);
      }
      if (maxMem >= VM_512_DEVICE) {
        return KConstants.HeapThreshold.PERCENT_RATIO_IN_512_DEVICE;
      } else if (maxMem >= VM_256_DEVICE) {
        return KConstants.HeapThreshold.PERCENT_RATIO_IN_256_DEVICE;
      } else if (maxMem >= VM_128_DEVICE) {
        return KConstants.HeapThreshold.PERCENT_RATIO_IN_128_DEVICE;
      }
      return KConstants.HeapThreshold.PERCENT_RATIO_IN_512_DEVICE;
    }
```

#### 3、Heap dump

`HeapDumpTrigger#trigger(TiggerReason reson)`

```java
  @Override
  public void trigger(TriggerReason reason) {
    if (triggered) {
      KLog.e(TAG, "Only once trigger!");
      return;
    }
    triggered = true;

    monitorManager.stop();

    KLog.i(TAG, "trigger reason:" + reason.dumpReason);
    if (heapDumpListener != null) {
      heapDumpListener.onHeapDumpTrigger(reason.dumpReason);
    }

    try {
      doHeapDump(reason.dumpReason);
    } catch (Exception e) {
      KLog.e(TAG, "doHeapDump failed");
      e.printStackTrace();
      if (heapDumpListener != null) {
        heapDumpListener.onHeapDumpFailed();
      }
    }

    KVData.addTriggerTime(KGlobalConfig.getRunningInfoFetcher().appVersion());
  }
```

*HeapDumpTrigger#doHeapDump(TriggerReason.DumpReason reason)*

```java
  public void doHeapDump(TriggerReason.DumpReason reason) {
    KLog.i(TAG, "doHeapDump");

    KHeapFile.getKHeapFile().buildFiles();

    HeapAnalyzeReporter.addDumpReason(reason);
    HeapAnalyzeReporter.addDeviceRunningInfo();

    boolean res = heapDumper.dump(KHeapFile.getKHeapFile().hprof.path);

    if (res) {
      heapDumpListener.onHeapDumped(reason);
    } else {
      KLog.e(TAG, "heap dump failed!");
      heapDumpListener.onHeapDumpFailed();
      KHeapFile.delete();
    }
  }
```

1. `buildFiles` 构建 hprof 和 report 文件，格式为当前日期 `yyyy-MM-dd_HH-mm-ss`
2. 添加 dump 的 reason 和 device running info，写入 report 文件中
3. `heapDumper.dump` 开始执行 dump 操作
4. dump 成功，`heapDumpListener.onHeapDumped(reason)` 通知解析 hprof 文件

*ForkJvmHeapDumper#dump(String path)*

```java
  @Override
  public boolean dump(String path) {
    KLog.i(TAG, "dump " + path);
    if (!soLoaded) {
      KLog.e(TAG, "dump failed caused by so not loaded!");
      return false;
    }

    if (!KOOMEnableChecker.get().isVersionPermit()) {
      KLog.e(TAG, "dump failed caused by version not permitted!");
      return false;
    }

    if (!KOOMEnableChecker.get().isSpaceEnough()) {
      KLog.e(TAG, "dump failed caused by disk space not enough!");
      return false;
    }

    // Compatible with Android 11
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
      return dumpHprofDataNative(path);
    }

    boolean dumpRes = false;
    try {
      int pid = trySuspendVMThenFork();
      if (pid == 0) {
        Debug.dumpHprofData(path);
        KLog.i(TAG, "notifyDumped:" + dumpRes);
        //System.exit(0);
        exitProcess();
      } else {
        resumeVM();
        dumpRes = waitDumping(pid);
        KLog.i(TAG, "hprof pid:" + pid + " dumped: " + path);
      }

    } catch (IOException e) {
      e.printStackTrace();
      KLog.e(TAG, "dump failed caused by IOException!");
    }
    return dumpRes;
  }
```

* Android 11 以上调用 `dumpHprofDataNative(path)` dump hprof

* fork 子进程的方式 dump hprof（后续解析）

  > 该进程为父进程时，返回子进程的 pid
  >
  > 该进程为子进程时，返回 0
  >
  > fork 执行失败，返回 -1

#### 4、解析 hprof

*HeapAnalysisTrigger#startTrack()*

```java
  @Override
  public void startTrack() {
    KTriggerStrategy strategy = strategy();
    if (strategy == KTriggerStrategy.RIGHT_NOW) {
      trigger(TriggerReason.analysisReason(TriggerReason.AnalysisReason.RIGHT_NOW));
    }
  }
```

*HeapAnalysisTrigger#trigger(TriggerReason triggerReason)*

```java
  @Override
  public void trigger(TriggerReason triggerReason) {
    //do trigger when foreground
    if (!isForeground) {
      KLog.i(TAG, "reTrigger when foreground");
      this.reTriggerReason = triggerReason;
      return;
    }

    KLog.i(TAG, "trigger reason:" + triggerReason.analysisReason);

    if (triggered) {
      KLog.i(TAG, "Only once trigger!");
      return;
    }
    triggered = true;

    HeapAnalyzeReporter.addAnalysisReason(triggerReason.analysisReason);

    if (triggerReason.analysisReason == TriggerReason.AnalysisReason.REANALYSIS) {
      HeapAnalyzeReporter.recordReanalysis();
    }

    //test reanalysis
    //if (triggerReason.analysisReason != TriggerReason.AnalysisReason.REANALYSIS) return;

    if (heapAnalysisListener != null) {
      heapAnalysisListener.onHeapAnalysisTrigger();
    }

    try {
      doAnalysis(KGlobalConfig.getApplication());
    } catch (Exception e) {
      KLog.e(TAG, "doAnalysis failed");
      e.printStackTrace();
      if (heapAnalysisListener != null) {
        heapAnalysisListener.onHeapAnalyzeFailed();
      }
    }
  }

```

* 如果应用进入后台，则返回
* 如果正在执行解析操作，则返回
* 调用 `doAnalysis(KGlobalConfig.getApplication())` 开启一个 service 解析

*HeapAnalyzeService#runAnalysis*

```java
  public static void runAnalysis(Application application,
      HeapAnalysisListener heapAnalysisListener) {
    KLog.i(TAG, "runAnalysis startService");
    Intent intent = new Intent(application, HeapAnalyzeService.class);
    IPCReceiver ipcReceiver = buildAnalysisReceiver(heapAnalysisListener);
    intent.putExtra(KConstants.ServiceIntent.RECEIVER, ipcReceiver);
    KHeapFile heapFile = KHeapFile.getKHeapFile();
    intent.putExtra(KConstants.ServiceIntent.HEAP_FILE, heapFile);
    application.startService(intent);
  }
```

*HeapAnalysisTrigger#doAnalyze()*

```java
  /**
   * run in the heap_analysis process
   */
  private boolean doAnalyze() {
    return heapAnalyzer.analyze();
  }
```

*KHeapAnalyzer#analyze()*

```java
  public boolean analyze() {
    KLog.i(TAG, "analyze");
    Pair<List<ApplicationLeak>, List<LibraryLeak>> leaks = leaksFinder.find();
    if (leaks == null) {
      return false;
    }

    //Add gc path to report file.
    HeapAnalyzeReporter.addGCPath(leaks, leaksFinder.leakReasonTable);

    //Add done flag to report file.
    HeapAnalyzeReporter.done();
    return true;
  }
```

* 开始解析并添加解析结果和解析完成标记记录到 report 文件中

### 参考

[fork的原理及实现](https://zhuanlan.zhihu.com/p/36872365)

[fork()----父子进程共享](https://yuhao0102.github.io/2019/05/05/fork----%E7%88%B6%E5%AD%90%E8%BF%9B%E7%A8%8B%E5%85%B1%E4%BA%AB/)

