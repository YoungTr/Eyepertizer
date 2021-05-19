# Dagger2

### 为什么需要依赖注入？

1、依赖注入建立在控制反转的概念之上，一个类应该从外部获得它的依赖关系，任何类不应该直接实例化另一个类，应该从配置类中获取实例。

2、如果一个类通过 **new** 操作符创建了另一个类的实例，那么这个类就不能独立于该类使用和测试，这被称为硬依赖（hard dependency）。

3、从类外提供依赖关系，增加了重复使用类的可能性，并且能够独立于其他类进行测试。

### 注入方式

1. Constructor Injection：注入方法参数
2. Field Injection：注入成员变量（不能是 private）
3. Method Injection：注入方法参数

* **Dependency provider**：用 `@Module` 注解的类负责提供可以被注入的对象。这些类定义了用 `@Provides` 注解的方法。从这些方法返回的对象可用于依赖性注入。
* **Dependency consumer**：`@Inject` 注解用来定义一个依赖关系。
* **Connecting consumer and producer**：一个 `@Component` 注释的接口定义了对象（modules）的 provider 和表达依赖关系的对象之间的连接。这种连接类是由 Dagger 生成的。

### Qualifier & Scop

1、`@Qualifier` 注解用于限定依赖关系。例如，一个类可以同时需要 Application Context 和 Activity Context。这两个对象都是 Context 类型的。Dagger 想要知道提供哪个变量，就需要明确指定他的标识符。因此，`@Qualifier` 被用来区分相同类型但具有不同实例的对象。

2、`@Scope` 同样用于自定义注解，我能可以通过@Scope自定义的注解来限定注解作用域，实现局部的单例；

 ### Component

#### 依赖关系（组件依赖）

AppComponent 持有一个全局的 Context 对象：

```kotlin
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(context: Context)
    fun context(): Context
}
```

```kotlin
@Module
class AppModule(private val context: Context) {
    @Provides
    fun provideContext(): Context = context
}
```

ActivityComponent 需要使用 AppComponent 中的 Context 对象：

```kotlin
// 声明了依赖关系
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: Activity)
}
```

```kotlin
@Module
class ActivityModule {
    @Provides
    fun provideSp(context: Context) = context.getSharedPreferences("Cooker", Context.MODE_PRIVATE)
}
```

依赖注入：

```kotlin
val appComponent = DaggerAppComponent.builder()
    .appModule(AppModule(this))
    .build()
appComponent.inject(this)

val activityComponent = DaggerActivityComponent.builder().appComponent(appComponent)
    .build()
activityComponent.inject(this)
```

ActivityComponent 声明依赖了 AppComponent，AppComponent 拥有 AppModule 中有可以提供 context 的 Provides，因此 ActivityModule 可以从 AppModule 拿到 context。

#### 包含关系（组件继承）

声明继承需要如下几步：

1. 子 Component 用 `@SubComponent` 注解；
2. 子 Component 声明一个 Builder 来告诉父 Component 如何创建自己；
3. 父 Component 对应的 Module 用 `subcomponent` 属性指明拥有哪些子 Component；
4. 父 Component 声明一个抽象方法来获取子 Component 的 Builder。

```kotlin
// 子 Component 用 @SubComponent 注解
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    // 声明一个 Builder 来告诉父 Component 如何创建自己
    @Subcomponent.Builder
    interface Builder {
        fun build(): ActivityComponent
    }
}
```

```kotlin
// 父 Component 对应的 Module 用 subcomponents 属性指定拥有哪些子 Component
@Module(subcomponents = [ActivityComponent::class])
class AppModule(private val context: Context) {
    @Provides
    fun provideContext(): Context = context
}
```

```kotlin
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(context: Context)

    //    fun context(): Context 不再需要

    // 父 Component 声明一个抽象方法来获取子 Component 的 Builder
    fun activityComponent(): ActivityComponent.Builder
}
```

依赖注入：

```kotlin
val appComponent = DaggerAppComponent.builder()
    .appModule(AppModule(this))
    .build()
    appComponent.inject(this)

val activityComponent = appComponent.activityComponent()
    .build()
activityComponent.inject(this)
```



https://johnnyshieh.me/posts/dagger-subcomponent/

https://juejin.cn/post/6844904201219211272
