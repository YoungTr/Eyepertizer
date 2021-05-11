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

 

