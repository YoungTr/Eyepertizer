# Logan

### 简介

**1、初始化**

```java
LoganConfig config = new LoganConfig.Builder()
                .setCachePath(getApplicationContext().getFilesDir().getAbsolutePath())
                .setPath(getApplicationContext().getExternalFilesDir(null).getAbsolutePath() + File.separator + FILE_NAME)
                .setEncryptKey16("0123456789012345".getBytes())
                .setEncryptIV16("0123456789012345".getBytes())
                .build();
```

**LoganConfig**

```java
    String mCachePath; //mmap缓存路径
    String mPathPath; //file文件路径

    long mMaxFile = DEFAULT_FILE_SIZE; //删除文件最大值，默认 10 M
    long mDay = DEFAULT_DAY; //删除天数，默认 10 天
    long mMaxQueue = DEFAULT_QUEUE;
    long mMinSDCard = DEFAULT_MIN_SDCARD_SIZE; //最小sdk卡大小

    byte[] mEncryptKey16; //128位aes加密Key
    byte[] mEncryptIv16; //128位aes加密IV
```

**LoganControlCenter**：Logan 功能真正实现的地方

```java
Logan.init(config) => LoganControlCenter.instance(loganConfig) => LoganControlCenter.init()
```

*LoganControlCenter#init()*

```java
    private void init() {
        if (mLoganThread == null) {
            mLoganThread = new LoganThread(mCacheLogQueue, mCachePath, mPath, mSaveTime,
                    mMaxLogFile, mMinSDCard, mEncryptKey16, mEncryptIv16);
            mLoganThread.setName("logan-thread");
            mLoganThread.start();
        }
    }
```

启动一个 LoganThread 线程，命名为 Logan-thread，构建生产者消费者模式来处理写入的数据，使用 ConcurrentLinkedQueue 实现队列

*LoganThread#run*

```java
    public void run() {
        super.run();
        while (mIsRun) {
            synchronized (sync) {
                mIsWorking = true;
                try {
                    LoganModel model = mCacheLogQueue.poll();
                    if (model == null) {
                        mIsWorking = false;
                        sync.wait();
                        mIsWorking = true;
                    } else {
                        action(model);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    mIsWorking = false;
                }
            }
        }
    }
```

* 一开始，mCacheLogQueue 没有数据，会调用 `sync.wait()` 线程进入 **WAITING** 状态，等待数据写入操作。

**2、写数据**

```java
/**
     * @param log  表示日志内容
     * @param type 表示日志类型: log, anr, crash
     */
    public static void w(String log, int type) {
        if (sLoganControlCenter == null) {
            throw new RuntimeException("Please initialize Logan first");
        }
        sLoganControlCenter.write(log, type);
    }
```

```java
Logan.w("logan init success", LOG);
```

*LoganControlCenter#write(String log, int flag)*

```java
    void write(String log, int flag) {
        if (TextUtils.isEmpty(log)) {
            return;
        }
        LoganModel model = new LoganModel();
        model.action = LoganModel.Action.WRITE;
        WriteAction action = new WriteAction();
        String threadName = Thread.currentThread().getName();
        long threadLog = Thread.currentThread().getId();
        boolean isMain = false;
        if (Looper.getMainLooper() == Looper.myLooper()) {
            isMain = true;
        }
        action.log = log;
        action.localTime = System.currentTimeMillis();
        action.flag = flag;
        action.isMainThread = isMain;
        action.threadId = threadLog;
        action.threadName = threadName;
        model.writeAction = action;
        if (mCacheLogQueue.size() < mMaxQueue) {
            mCacheLogQueue.add(model);
            if (mLoganThread != null) {
                mLoganThread.notifyRun();
            }
        }
    }
```

* 如果 mCacheLogQueue 的数量小于 mMaxQueue（默认500），则加入到 mCacheLogQueue 中，然后调用 mLoganThread.notifyRun()，通知 LoganThread 处理数据。

*LoganThread#action(LoganModel model)*

```java
private void action(LoganModel model) {
    if (model == null || !model.isValid()) {
        return;
    }
    if (mLoganProtocol == null) {
        mLoganProtocol = LoganProtocol.newInstance();
        mLoganProtocol.setOnLoganProtocolStatus(new OnLoganProtocolStatus() {
            @Override
            public void loganProtocolStatus(String cmd, int code) {
                Logan.onListenerLogWriteStatus(cmd, code);
            }
        });
        mLoganProtocol.logan_init(mCachePath, mPath, (int) mMaxLogFile, mEncryptKey16,
                mEncryptIv16);
        mLoganProtocol.logan_debug(Logan.sDebug);
    }

    if (model.action == LoganModel.Action.WRITE) {
        doWriteLog2File(model.writeAction);
    } else if (model.action == LoganModel.Action.SEND) {
        if (model.sendAction.sendLogRunnable != null) {
            // 是否正在发送
            synchronized (sendSync) {
                if (mSendLogStatusCode == SendLogRunnable.SENDING) {
                    mCacheSendQueue.add(model);
                } else {
                    doSendLog2Net(model.sendAction);
                }
            }
        }
    } else if (model.action == LoganModel.Action.FLUSH) {
        doFlushLog2File();
    }
}
```

* 如果 LoganProtocol 还没初始化，则先初始化

*LoganProtocol#Logan_init(String cache_path, String dir_path, int max_file, String encrypt_key_16,String encrypt_iv_16)*

```java
    @Override
    public void logan_init(String cache_path, String dir_path, int max_file, String encrypt_key_16,
                           String encrypt_iv_16) {
        Log.d("LoganProtocol", "thread name: " + Thread.currentThread().getName());
        if (mIsInit) {
            return;
        }
        if (CLoganProtocol.isCloganSuccess()) {
            mCurProtocol = CLoganProtocol.newInstance();
            mCurProtocol.setOnLoganProtocolStatus(mLoganProtocolStatus);
            mCurProtocol.logan_init(cache_path, dir_path, max_file, encrypt_key_16, encrypt_iv_16);
            mIsInit = true;
        } else {
            mCurProtocol = null;
        }
    }
```

`CLoganProtocol` 是连接 Java 和 native 的桥梁，后面再详细解析。

*LoganThread#doWriteLog2File(WriteAction action)*

```java
    private void doWriteLog2File(WriteAction action) {
        if (Logan.sDebug) {
            Log.d(TAG, "Logan write start");
        }
        if (mFileDirectory == null) {
            mFileDirectory = new File(mPath);
        }

        if (!isDay()) {
            long tempCurrentDay = Util.getCurrentTime();
            //save时间
            long deleteTime = tempCurrentDay - mSaveTime;
            deleteExpiredFile(deleteTime);
            mCurrentDay = tempCurrentDay;
            mLoganProtocol.logan_open(String.valueOf(mCurrentDay));
        }

        long currentTime = System.currentTimeMillis(); //每隔1分钟判断一次
        if (currentTime - mLastTime > MINUTE) {
            mIsSDCard = isCanWriteSDCard();
        }
        mLastTime = System.currentTimeMillis();

        if (!mIsSDCard) { //如果大于50M 不让再次写入
            return;
        }
        mLoganProtocol.logan_write(action.flag, action.log, action.localTime, action.threadName,
                action.threadId, action.isMainThread);
    }
```

* 首次调用时，isDay() 为 false，（是否是一天之内)，会删除过期的文件，然后调用 `mLoganProtocol.logan_open(file)` 打开当天的文件；

* 每隔一分钟，会判断所有的日志文件是否超过设置的文件大小，如果超过了就不再写入。

* 最后调用 `mLoganProtocol.logan_write()` 写入日志内容。

**3、flush 数据**

```java
Logan.f();
```

*LoganControlCenter#flush()*

```java
    void flush() {
        if (TextUtils.isEmpty(mPath)) {
            return;
        }
        LoganModel model = new LoganModel();
        model.action = LoganModel.Action.FLUSH;
        mCacheLogQueue.add(model);
        if (mLoganThread != null) {
            mLoganThread.notifyRun();
        }
    }
```

*LoganControlCenter#doFlushLog2File()*

```java
    private void doFlushLog2File() {
        if (Logan.sDebug) {
            Log.d(TAG, "Logan flush start");
        }
        if (mLoganProtocol != null) {
            mLoganProtocol.logan_flush();
        }
    }
```

* 最终会调用 native `clogan_flush` 方法。

**4、发送数据**

*LoganControlCenter#send(String dates[], SendLogRunnable runnable)* 发送数据请求

```java
    void send(String dates[], SendLogRunnable runnable) {
        if (TextUtils.isEmpty(mPath) || dates == null || dates.length == 0) {
            return;
        }
        for (String date : dates) {
            if (TextUtils.isEmpty(date)) {
                continue;
            }
            long time = getDateTime(date);
            if (time > 0) {
                LoganModel model = new LoganModel();
                SendAction action = new SendAction();
                model.action = LoganModel.Action.SEND;
                action.date = String.valueOf(time);
                action.sendLogRunnable = runnable;
                model.sendAction = action;
                mCacheLogQueue.add(model);
                if (mLoganThread != null) {
                    mLoganThread.notifyRun();
                }
            }
        }
    }
```

*LoganThread#doSendLog2Net(SendAction action)*

```java
    private void doSendLog2Net(SendAction action) {
        if (Logan.sDebug) {
            Log.d(TAG, "Logan send start");
        }
        if (TextUtils.isEmpty(mPath) || action == null || !action.isValid()) {
            return;
        }
        boolean success = prepareLogFile(action);
        if (!success) {
            if (Logan.sDebug) {
                Log.d(TAG, "Logan prepare log file failed, can't find log file");
            }
            return;
        }
        action.sendLogRunnable.setSendAction(action);
        action.sendLogRunnable.setCallBackListener(
                new SendLogRunnable.OnSendLogCallBackListener() {
                    @Override
                    public void onCallBack(int statusCode) {
                        synchronized (sendSync) {
                            mSendLogStatusCode = statusCode;
                            if (statusCode == SendLogRunnable.FINISH) {
                                mCacheLogQueue.addAll(mCacheSendQueue);
                                mCacheSendQueue.clear();
                                notifyRun();
                            }
                        }
                    }
                });
        mSendLogStatusCode = SendLogRunnable.SENDING;
        if (mSingleThreadExecutor == null) {
            mSingleThreadExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    // Just rename Thread
                    Thread t = new Thread(Thread.currentThread().getThreadGroup(), r,
                            "logan-thread-send-log", 0);
                    if (t.isDaemon()) {
                        t.setDaemon(false);
                    }
                    if (t.getPriority() != Thread.NORM_PRIORITY) {
                        t.setPriority(Thread.NORM_PRIORITY);
                    }
                    return t;
                }
            });
        }
        mSingleThreadExecutor.execute(action.sendLogRunnable);
    }
```

* 如果当前正在发送数据，则将任务添加到 `mCacheSendQueue` 等待下一下执行发送任务。
* 发送完成之后，会将 `mCacheSendQueue` 中的任务添加到 `mCacheLogQueue` ，等待下一次执行。

*LoganThread#prepareLogFile(SendAction action)*

```java
    /**
     * 发送日志前的预处理操作
     */
    private boolean prepareLogFile(SendAction action) {
        if (Logan.sDebug) {
            Log.d(TAG, "prepare log file");
        }
        if (isFile(action.date)) { //是否有日期文件
            String src = mPath + File.separator + action.date;
            // 如果上传的当天的日志，则生成一个副本
            if (action.date.equals(String.valueOf(Util.getCurrentTime()))) {
                doFlushLog2File();
                String des = mPath + File.separator + action.date + ".copy";
                if (copyFile(src, des)) {
                    action.uploadPath = des;
                    return true;
                }
            } else {
                action.uploadPath = src;
                return true;
            }
        } else {
            action.uploadPath = "";
        }
        return false;
    }
```

* 如果上传的当天的日志，则生成一个名为 `data.copy` 的副本，将这个副本上传，上传完成之后会将该文件删除。
