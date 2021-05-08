# MVP

### MVP 将应用程序分为三个基本组成部分：

1. **Model**：它负责处理应用程序的数据部分。
2. **View**：它负责在屏幕上用特定的数据铺设视图。
3. **Presenter**：它是连接模型和视图的桥梁。它还充当View的指导者。

### MVP为上述组件制定了一些基本规则，如下所列：

1. View 的唯一责任是按照 Presenter 的指示绘制用户界面。它是应用程序的一个哑巴部分。
2. View 将所有的用户交互委托给它的 Presenter 。
3. View 从不与 Model 直接通信。
4. Presenter 负责将 View 的要求委托给 Model，并指示 View 对特定事件进行操作。
5. Model 负责从服务器、数据库和文件系统获取数据。

### 序言

1. Activity, Fragment, and a CustomView 作为应用程序的 View 部分。
2. 每个 View 都有一个一对一关系的 Presenter。
3. View 通过一个接口与它的 Presenter 进行通信，反之亦然。
4. Model 被分成几个部分。ApiHelper, PreferenceHelper, DatabaseHelper, 和 FileHelper。这些都是DataManager的助手，它实质上是绑定了所有的 Model 部分。
5. Presenter 通过一个接口与 DataManager 进行通信。
6. DataManager 只在被要求时提供服务。
7. Presenter 不能访问 Android 的任何 apis

### 架构图

![](./pics/09792-1etz8borfvbwoolchgczq1a.png)

* **View：**它是应用的一部分，渲染用户界面并接受来自用户的交互。Activity，Fragment 和 CustomView 构成这一部分。
* **MvpView：**它是一个接口，由 View 实现。它通过被暴露的方法和 Presenter 进行通信。
* **Presenter：**它是 View 的决策对应物，是一个纯 Java 类，不能访问 Android 的 API。它接收 View 传递过来的用户交互，然后根据业务逻辑进行决策，最后指示 View 执行特定的动作。它还与 DataManager 进行通信，以获得执行业务逻辑所需的任何数据。
* **MvpPresenter：**它是一个接口，由 Presenter 实现。它暴露方法与 View 进行通信。
* **AppDbHelper：**数据库管理和应用中所有与数据库有关的数据处理都是在这一部分完成。
* **DbHelper：**它是一个由 AppDbHelper 实现的接口，包含了给应用程序调用的方法。该层对 DbHelper 的任何具体实现进行解耦，使 AppDbHelper 成为即插即用的单元。
* **AppPreferenceHelper：**与 AppDbHelper 类型，可以从 SharedPreferences 中读写数据。
* **PreferenceHelper：**与 DbHelper 接口类似，由 AppPreferenceHelper 实现。
* **AppApiHelper：**它负责管理网络 API 调用和 API 数据处理。
* **ApiHelper：**它是一个与DbHelper一样的接口，但由AppApiHelper实现。
* **DataManager：**它是一个由 AppDataManager 实现的接口。它包含所有数据处理操作的方法。理想情况下，它委托所有帮助者类提供的服务。为此，DataManager 接口扩展了DbHelper、PreferenceHelper 和 ApiHelper 接口。
* **AppDataManager：**它是应用程序中任何数据相关操作的一个联系点。DbHelper, PreferenceHelper, 和 ApiHelper 只对 DataManager 起作用。它将所有特定的操作委托给任何 Helper。

![](./pics/20210508-143706.png)

