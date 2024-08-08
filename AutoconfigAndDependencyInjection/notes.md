# Starting Spring Boot

## Spring Framework Overview

- **Spring Framework** is a Dependency Injection framework designed to make Java applications loosely coupled.
- It simplifies the development process for Java EE applications.
- Enables development using "Plain Old Java Objects" (POJOs), applying enterprise services non-invasive.
- Developed by Rod Johnson in 2003.

## Beans

A **bean** is a managed object instantiated, assembled, and managed by the Spring IoC container.
Beans are the core building blocks of a Spring application, wired together to form the application.

### Spring Annotations

- Traditionally, beans were managed using XML-based configuration.
- Java-based configuration is an alternative that allows programmatic management of bean components, leading to the introduction of Spring annotations.

## Defining Beans

1. **Using Stereotype Annotations**  
   Annotate your class with one of the stereotype annotations (`@Component`, `@Service`, `@Repository`, `@Controller`).
   This indicates that the class should be managed as a bean.

2. **Explicit Bean Declaration in Configuration Class**  
   Create a configuration class annotated with `@Configuration`.
   This class will contain methods to define and configure beans.

## Beans Lifecycle

1. **Bean Created**  
   The bean instance is created using a static factory method or an instance factory method
   (for annotation-based configuration).

2. **Dependency Injected**  
   Spring sets the bean's properties and dependencies via setter, constructor, or field injection.

3. **Bean Initialized**  
   If the bean implements `InitializingBean` or defines a custom initialization method annotated with `@PostConstruct`,
   Spring invokes the initialization method post-configuration.

4. **Bean is Used**  
   The bean is fully initialized and ready for application use.

5. **Bean Destroyed**  
   Spring invokes the destruction method when the bean is no longer necessary or when the application context shuts down.

### Bean Lifecycle Hooks

- **`@PostConstruct` Annotation**  
  Marks a method to be invoked immediately after bean construction and dependency injection.

- **`@PreDestroy` Annotation**  
  Marks a method to be invoked just before bean destruction for cleanup tasks.

## Scope of Beans

| **Scope**   | **Description**                                                                                                                                                                   |
|-------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `singleton` | (Default) A single bean definition to a single object instance per Spring IoC container.                                                                                          |
| `prototype` | A single bean definition to multiple object instances.                                                                                                                            |
| `request`   | Scopes a bean to the lifecycle of a single HTTP request. Each request gets its own instance. Valid only in a web-aware Spring ApplicationContext.                                 |
| `websocket` | Scopes a bean to the lifecycle of a WebSocket. Valid only in a web-aware Spring ApplicationContext.                                                                               |

## Dependency Injection

Dependency Injection (DI) is a design pattern used to achieve loose coupling between components.
Instead of a component creating its dependencies, they are injected by an external source managed by Spring.

### Benefits of Dependency Injection

- **Loose Coupling:** Components are decoupled from their dependencies, enhancing maintainability and testability.
- **Flexible Configuration:** Dependencies can be configured externally, allowing for customization and swapping.
- **Improved Testability:** Components can be mocked or replaced during testing, facilitating isolated unit tests.

### How to Inject Dependencies

- **Constructor Injection**  
  Dependencies are provided through a class constructor.

- **Field Injection**  
  Dependencies are provided directly into fields using `@Autowired`.

## Spring Boot vs Spring Framework

- **Auto-Configuration**
- **Starter Dependencies**
- **Externalized Configuration**
- **Embedded Tomcat, Jetty Servers**
- **Built-in Metrics & Health Checks**

## Auto-Configuration and Spring Boot Internal Flow

### pom.xml

- **Maven** is used in Spring Boot projects to specify dependencies in the `pom.xml` file. Maven resolves and includes these dependencies in the classpath.
- **Starters** like `spring-boot-starter-parent` include many third-party libraries and use AutoConfigurations to set up these libraries automatically.
- **spring-boot-dependencies** in `pom.xml` defines third-party libraries and their versions, eliminating the need to specify version numbers manually.

### What is Auto Configuration

Auto-configuration automatically configures Spring applications based on classpath dependencies and application-specific settings.
It simplifies setup, allowing developers to focus more on business logic.

### How Auto-Configuration Works

1. **Classpath Scanning**  
   Spring Boot scans the classpath for certain libraries and classes, applying corresponding configurations.

2. **Configuration Classes**  
   Spring Boot contains various autoconfiguration classes,
   each responsible for configuring specific parts of the application.

3. **Conditional Beans**  
   Each auto-configuration class uses conditional checks
   to apply configurations based on the presence of certain classes,
   user-defined beans, and property settings.

### Core Features of Auto-Configuration

- **@PropertySources Auto-registration**  
  Automatically registers PropertySources when running the main method of a Spring Boot application.

- **META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports**  
  Contains all Spring Boot’s auto-configuration magic, provided by `spring-boot-autoconfigure`.

### Enhanced Conditional Support

- `@ConditionalOnBean(DataSource.class)`  
  True if a `DataSource` bean is defined.

- `@ConditionalOnClass(DataSource.class)`  
  True if `DataSource` class is on the classpath.

- `@ConditionalOnProperty("my.property")`  
  True if `my.property` is set.

### Spring Boot Internal Flow

1. **Initialization**  
   Starts with a class annotated with `@SpringBootApplication` combining `@Configuration`,
   `@EnableAutoConfiguration`, and `@ComponentScan`.

2. **Spring Application Context Creation**  
   Creates an application context, scanning for components, configurations, and auto-configurations.

3. **Auto-Configuration**  
   Configures beans based on classpath and dependencies using conditional annotations.

4. **Externalized Configuration**  
   Loads configuration properties from various sources, providing defaults and allowing customizations.

5. **Embedded Web Server Initialization**  
   Initializes and configures an embedded web server like Tomcat, Jetty, or Undertow if it's a web application.

6. **Application Startup**  
   Invokes lifecycle callbacks and initialization logic as the application context is set up.

7. **Application Ready**  
   The application context is fully initialized, and the application is ready to handle requests.

## Maven

Maven is a build automation and project management tool primarily used for Java projects.
It plays a crucial role in the development, build, and dependency management of Spring applications,
including those using Spring Framework's component scanning and annotation-driven configuration.

### Maven as the Chef

#### Project & Dependency Management

- Maven provides a standardized way to manage Java projects by defining project structure, dependencies, and build configurations using a declarative XML-based format (`pom.xml`).
- Developers use Maven to specify project metadata, dependencies, plugins, repositories, and other project-related configurations.
- Spring Framework and its various modules (e.g., Spring Core, Spring MVC, Spring Boot) are managed as dependencies in Maven projects. Developers specify the Spring dependencies in the `pom.xml`, and Maven handles the rest.

#### Build Automation

- Maven automates the build process, including compilation, testing, packaging, and deployment, using predefined build lifecycle phases (e.g., `clean`, `compile`, `test`, `package`, `install`).
- Maven facilitates the building and packaging of Spring applications into deployable artifacts (e.g., JAR files, WAR files) for deployment in production environments.

### Maven Commands

| **Command**                   | **Description**                                                                                                                                           |
|-------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| `mvn compile`                 | Compiles the project's source code.                                                                                                                       |
| `mvn clean`                   | Removes all previous build files from the project.                                                                                                        |
| `mvn test`                    | Executes the project's testing steps.                                                                                                                     |
| `mvn install`                 | Deploys the packaged local repository classes (WAR or JAR files) by storing them in the local repository.                                                 |
| `mvn package`                 | Generates a distributable WAR or JAR file for the project so that it can be deployed.                                                                     |
| `mvn deploy`                  | After compilation, project testing, and building, this command copies the packaged WAR or JAR files to the remote repository for other developers to use. |
| `mvn spring-boot:run`         | Runs a Spring Boot application directly from the source code without packaging it into a JAR or WAR file.                                                 |
| `mvn spring-boot:build-image` | Builds a Docker image of the Spring Boot application using the Spring Boot Maven plugin.                                                                  |



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
        // ... development datasource configuration
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



### Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Maven Official Documentation](https://maven.apache.org/guides/index.html)
- [Spring Framework Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/)

