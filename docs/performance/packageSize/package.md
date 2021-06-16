# Android 包体积优化

### 1、APK 组成

![](./pics/app.jpg)

| 文件/目录           | 描述                                                         |
| ------------------- | ------------------------------------------------------------ |
| lib/                | 存放 so 文件                                                 |
| res/                | 存放编译后的资源文件                                         |
| assets/             | 应用程序的资源，可以使用 AssetManager 来检索该资源           |
| META-INF/           | 一般存放于已签名的 APK 中，它包含了 APK 中所有文件的签名摘要等信息 |
| classes(n).dex      | classes 文件是 Java Class，被 DEX 编译后可供 Dalvik/ART 虚拟机所理解的文件格式 |
| resources.arsc      | 编译后的二进制资源文件                                       |
| AndroidMenifest.xml | Android 的清单文件，格式为 AXML，用于描述应用程序的名称、版本、所需权限、注册的四大组件等 |

[https://tech.meituan.com/2017/04/07/android-shrink-overall-solution.html](https://tech.meituan.com/2017/04/07/android-shrink-overall-solution.html)

### 2、DEX 文件

![dex](./pics/dex.png)

[一篇文章带你搞懂DEX文件的结构](https://blog.csdn.net/sinat_18268881/article/details/55832757)

[Android逆向笔记 —— DEX 文件格式解析](https://juejin.cn/post/6844903847647772686)

[谈谈对Dex文件的认识](https://blog.csdn.net/li0978/article/details/114981769)

### 3、Android 打包流程

1. 打包资源文件，生成 R.java 文件。`aapt` 工具(The Android Asset Packaing Tool)，生成 resources.arsc
2. 处理 aidl 文件，生成对应的 Java 文件。
3. 编译工程源代码，生成对应的 class 文件。调用 `javac` 工具编译工程 src 目录下所有的 java 源文件。
4. 转换所有的 class 文件，生成 classes.dex 文件。`dx` 工具，主要工作是将 Java 字节码转为为 Dalvik 字节码、压缩常量池、消除冗余信息等。
5. 打包生成 APK 文件。打包工具为 `apkbuilder` ，resources.arsc + res + assets + so
6. 对 APK 文件进行签名。
7. 对签名后的 APK 文件进行对齐处理。工具为 `zipalign` ，是 apk 包中的所有资源文件距离文件起始偏移为 4 字节整倍数，这样可以通过内存映射访问 apk 文件时速度会更快。

![build-worlflow](./pics/build-workflow.png)



[10分钟了解Android项目构建流程](https://juejin.cn/post/6844903555795517453)

[Android APK文件结构 完整打包编译的流程 APK安装过程 详解](https://blog.csdn.net/aha_jasper/article/details/104944929)

### 4、D8 & R8

- [ ] [jake wharton blog](https://jakewharton.com/blog/)

### 5、Dex 分包优化

- [ ] [Redex](https://fbredex.com/docs/installation)
- [ ] [Optimizing Android bytecode with ReDex](https://engineering.fb.com/2015/10/01/android/optimizing-android-bytecode-with-redex/)
- [ ] [整体架构](http://yourbay.me/all-about-tech/2020/05/12/redex-1-arch/)
- [ ] [Dex解析](http://yourbay.me/all-about-tech/2020/05/13/redex-2-parse-dex/)

