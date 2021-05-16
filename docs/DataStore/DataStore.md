# DataStore

DataStore 是基于 Flow 实现的，一种新的数据存储方案，它提供了两种实现方式：

* Proto DataStore：存储类的对象（typed objects），通过 protocol buffers 将对象序列化存储在本地。
* Preferences DataStore：以键值对的形式存储在本地和 SharedPreferences 类似，但是 DataStore 是基于 Flow 实现的，不会阻塞主线程，并且保证类型安全。

