# MVVM

MVVM架构是一个 Model-View-ViewModel 架构，它消除了每个组件之间的紧密耦合。最重要的是，在这个架构中，子代没有对父代的直接引用，他们只有通过观察物的引用。

![](./pics/mvvm.png)

* **Model**：负责 Android 应用程序的数据和业务逻辑，包括本都和远程数据源、模型类、资源库等。
* **View**：由 UI 代码（Activity，Fragment），XML 组成。它向 ViewModel 发送用户动作，当并不直接获得响应。必须订阅 ViewModel  才能获得相应。
* **ViewModel**：它是 View 和 Model（业务逻辑）之间的一座桥梁，不会对 View 的直接引用，ViewModel 不知道正在与哪个 View 交互。它与 Model 交互，并暴露出可以被 View 观察到的可观察对象。