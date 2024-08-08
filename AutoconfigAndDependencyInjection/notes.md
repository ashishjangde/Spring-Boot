# Starting Spring Boot

- **Beans**
- **Dependency Injection**
- **AutoConfiguration**
- **Maven Build Tool**

## Spring Framework

- **Spring is a Dependency Injection framework** that makes Java applications loosely coupled.
- **Spring framework simplifies the development process** for Java EE applications.
- **Spring enables the development of applications** using "Plain Old Java Objects" (POJOs) and allows enterprise services to be applied to POJOs in a non-invasive manner.
- **Spring was developed by Rod Johnson in 2003.**

# Beans

A **"bean"** is a managed object that is instantiated, assembled, and managed by the Spring IoC container.  
Beans form the backbone of a Spring application and are the core building blocks that are wired together to create the application.

## Spring Annotations

- Traditionally, Spring allows a developer to manage bean dependencies using XML-based configuration.
- An alternative method to define beans and their dependencies is Java-based configuration.
- Unlike the XML approach, Java-based configuration allows you to manage bean components programmatically, which is why Spring annotations were introduced.

# Defining Beans

1. **Using Stereotype Annotations**  
   Annotate your class with one of the stereotype annotations (`@Component`, `@Service`, `@Repository`, `@Controller`). These annotations inform Spring that the class should be managed as a bean.

2. **Explicit Bean Declaration in Configuration Class**  
   Create a configuration class and annotate it with `@Configuration`. This class will contain methods to define and configure beans.

# Beans Lifecycle

1. **Bean Created**  
   The bean instance is created by invoking a static factory method or an instance factory method (for annotation-based configuration).

2. **Dependency Injected**  
   Spring sets the bean's properties and dependencies, either through setter injection, constructor injection, or field injection.

3. **Bean Initialized**  
   If the bean implements the `InitializingBean` interface or defines a custom initialization method annotated with `@PostConstruct`, Spring invokes the initialization method after the bean has been configured.

4. **Bean is Used**  
   The bean is now fully initialized and ready to be used by the application.

5. **Bean Destroyed**  
   Spring invokes the destruction method when the bean is no longer needed or when the application context is being shut down.

# Bean Lifecycle Hooks

- **`@PostConstruct` Annotation**  
  The `@PostConstruct` annotation is used to mark a method that should be invoked immediately after a bean has been constructed and all of its dependencies have been injected.

- **`@PreDestroy` Annotation**  
  The `@PreDestroy` annotation is used to mark a method that should be invoked just before a bean is destroyed by the container. This method can perform any necessary cleanup or resource-releasing tasks.


# Scope of Beans

| **Scope**   | **Description**                                                                                                                                                                   |
|-------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `singleton` | (Default) Scopes a single bean definition to a single object instance for each Spring IoC container.                                                                              |
| `prototype` | Scopes a single bean definition to any number of object instances.                                                                                                                |
| `request`   | Scopes a single bean definition to the lifecycle of a single HTTP request. Each HTTP request has its own instance of a bean. Only valid in a web-aware Spring ApplicationContext. |
| `websocket` | Scopes a single bean definition to the lifecycle of a WebSocket. Only valid in a web-aware Spring ApplicationContext.                                                             |


## Annotations Breakdown

### `@Configuration`
- **Purpose:** Marks a class as a source of bean definitions.
- **Functionality:** Indicates that the class contains methods annotated with `@Bean` that define beans to be managed by the Spring container.
- **Example:**

    ```java
    @Configuration
    public class AppConfig {
        // ... bean definitions
    }
    ```

### `@Bean`
- **Purpose:** Defines a method that creates and returns a bean instance.
- **Functionality:** The return value of the method becomes a bean managed by the Spring container.
- **Example:**

    ```java
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
    ```

### `@Component`
- **Purpose:** Indicates that a class is a component.
- **Functionality:** Automatically detects and registers the class as a Spring bean.
- **Example:**

    ```java
    @Component
    public class MyComponent {
        // ... component logic
    }
    ```

### `@ConditionalOnProperty`
- **Purpose:** Conditionally includes a bean based on the value of a property.
- **Functionality:** The bean is only created if the specified property has the given value.
- **Example:**

    ```java
    @ConditionalOnProperty(name = "db.env", havingValue = "development")
    @Bean
    public DataSource dataSource() {
        // ... development datasource 
    }
    ```

### `@Autowired`
- **Purpose:** Automatically wires dependencies into a bean.
- **Functionality:** Spring injects the required dependencies by type.
- **Example:**

    ```java
    @Component
    public class MyService {
        @Autowired
        private MyRepository repository;
    }
    ```

