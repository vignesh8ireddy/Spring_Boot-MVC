## Spring Boot MVC

* MVC is an architecture to develop an application in layers and each layer is developed using an appropriate design patterns
* MVC: Model - View - Controller
  * Model - Business Logics (Service classes), Persistence Logics (DAO classes)
  * View - UI logics (React, Angular)
  * Controller - Control, Monitoring and Tracing logics

* In MVC and Monolithic architectures, all services will be packaged into single unit project
* In SOAP and Microservices, different services will be developed as different unit projects
* FrontController
  * It is a special servlet component(and handler/controller class) of a web application that acts as entry and exit point for all the request and responses.
  * FrontController traps all the requests and multiple requests, applies common system services, performs navigation management,
    Data management, View management and lastly sends the response to the browser (client)
    * system services : security, logging, auditing,...
    * navigation management : delegating the uri/url requests to appropriate handler components
    * data/model management : passing data(inputs by client) coming along with the requests to appropriate handler components
      passing data(results by service classes) from handler class to the appropriate view components by keeping it in
      request scope or session scope
    * view management : sending view components as responses to the client
  * Instead of developing Controller class as normal servlet component or servlet filet component, it is recommended to
    develop it as FrontController Servlet (a servlet component based on FrontController Design Patterns)
  * So, entire flow of the application from request to response will be under the control and monitoring of FrontController
  * We need to configure FrontController servlet component having extension match url pattern (like *.do for trapping
    multiple requests), directory match url pattern (/x/y/* for multiple requests) or with "/" (for trapping all requests)

* DispatcherServlet of Spring MVC
  * It is a Spring MVC supplied servlet component given as FrontController Servlet.
  * or.springframework.web.servlet.DispatcherServlet (found in spring-web-<version>.jar)
  * So, FrontController servlet component is predefined and given with navigation, view, data managements by the Spring Web starter.
  * In Spring MVC applications, we need to explicitly configure and register FrontController with Servlet Container having directory match url pattern (like x/y/*) or
    extension match url pattern (like *.do) or global url pattern ("/").
  * But, in Spring Boot MVC applications, it is automatically registered with Servlet Container having url pattern "/" to trap all
    the requests and load on startup enabled.
    * Three ways to configure a Servlet Component with Servlet Container:
      1. Declarative Approach (using web.xml configurations)
      2. Annotation Approach (using @WebServlet)
      3. Programmatic or DynamicServlet Registration Approach (using sc.addServlet(,))

* Java Web Application = FrontController + MVC Architecture

* Generally, in the implementation of MVC architecture based web application we can see the following design patterns:
    * DAO, Server/BusinessDelegate and Factory design patterns in Model layer
    * FrontController design patterns in Controller layer
    * View Helper, CompositeView design patterns in View layer

* The following operations takes place when a Spring Boot MVC application is deployed over application server
  1. Because of the "load-on-startup" of DispatcherServlet, the Servlet Container creates DispatcherServlet class object
     either on the deployment of the application (for hot deployment) or during the server startup (for cold deployment)
     * Hot Deployment: Deploying the application when server is running
     * Cold Deployment: Stopping the server and deploying the application and starting again.
  2. init() method of DispatcherSerlvet executes and creates IOC Container of type WebApplicationContext
  3. IOC container performs pre-instantiation of singleton scope beans (like controller,service, repository classes, DataSource,
      Handler Mappings, ViewResolver,...), performs Dependency Injection and keeps them in internal cache of it.
  * In Spring Boot MVC applications, DispatcherServlet is taken care by Servlet Container, View Components (jsp files) are 
    taken care by Jasper Container and Spring Beans are taken care by IOC Container created by Dispatcher Servlet.
* In Standalone applications, we create IOC Container manually in main() method, but web applications doesn't have any main() method,
  IOC container is automatically created by init() method of DispatcherServlet given Spring Web starter

* Five main components of a Spring Boot MVC application
  1. FrontController (DispatcherServlet)
  2. Handler/Controller class is a spring bean (@Controller) and a controller that either can process the received request (from FrontController) directly or can
      take the support of service, dao classes for the response.
  3. HandlerMapping: This is a helper component which maps the requests trapped by the FrontController to the 
     appropriate Handler class. (HandlerMapping component uses reflection api internally)
     * HandlerMapping classes are ready-made and given by SpringBoot MVC
     * This component (DefaultAnnotationHandlerMapping class given by spring boot) implements org.springframework.web.servlet.HandlerMapping(I) directly or indirectly.
     * So, you don't have to develop custom HandlerMapping classes
  4. View Component: .jsp files or any other physical UI components stored in webapps/WEB-INF/ directory
  5. View Resolver: A helper component which resolves the logical view name to the physical View Components
     * View Resolver doesn't execute physical View Components, it just identifies the name and location of physical view
       components and gives those details to DispatcherServlet
     * No need of developing user-defined View Resolver classes, Spring Boot gives ready-made ViewResolver (by default, InternalResourceViewResolver)
       implementing org.springframework.web.servlet.ViewResolver(I)
     * Use application.properties files to give inputs to InternalResourceViewResolver
     * InternalResourceViewResolver is not designed to resolve .html view components, it is good with private area servlet and .jsp files

* HandlerMapping class and ViewResolver class become spring beans through autoconfiguration

* Spring Boot MVC Application Setup
    * Maven Dependencies(Spring Web, Spring DevTools API, Apache JSTL API)
    * application.properties file
      * spring.mvc.view.prefix
      * spring.mvc.view.suffix
      * server.port
      * server.servlet.context-path
    * controller layer (handlers i.e @Controller)
    * webapp/WEB-INF/pages/ for .html, .jsp, .js or .jsp files
    * webapp/WEB-INF/classes/ for .java and .class files
    * webapp/WEB-INF/lib/ for .jar files
    * webapp/WEB-INF/config/ for .xml files
* WEB-INF and its sub folders are private area and everything outside WEB-INF is public
* Everything in src folder (src/main/java, src/main/test,...) is kept in WEB-INF folder internally

* Working of Spring Boot MVC application 
  1. The Spring MVC application is deployed in the web server
  2. The DispatcherServlet would be pre-instantiated and initialized because the load-on-startup is enabled for it.
     As a part of its initialization the IOC container will be created(of type ApplicationContext) and performs 
     pre-instantiation of singleton scope spring beans like HandlerMappings, Handler Classes, service layer classes, 
     DAO layer classes, View Resolvers,.. and completes the necessary dependency injections and keeps all these spring 
     beans in the internal cache of IOC container.
  3. When request is sent to MVC application (through browser/Postman), DispatcherServlet (FrontController) traps the 
     request, applies the common system services and hand-overs the trapped request to the Handler Mapping component.
  4. Handler Mapping component searches in all the @Controller classes (handlers) for the corresponding handler method 
     whose request path matches with the path of currently trapped request (using reflection api) and returns the found 
     handler controller's bean id and handler methods' signature to the Dispatcher Servlet.
  5. Dispatcher Servlet submits them to the IOC container, gets the instance of the handler class and calls the 
     signature based handler method on it.
  6. The handler method either directly process the request or delegates the request to service layer or DAO layer 
     classes for processing and returns the Logical View Name to the Dispatcher Servlet.
  7. Dispatcher Servlet now gives the Logical View Name to the View Resolver which resolves and identifies the physical 
     view component and returns the View component's details.
  8. Finally, Dispatcher Servlet communicates with the physical View Component where final results (response) are 
     gathered and formatted and sends back the formatted results to the browser as a response.

* To use embedded tomcat server, just configure the port number as server.port = 4041 (4041 is by default) in application.properties,
  if didn't work, add the following dependency from mvnrepository.com
    ```
    <!-- https://mvnrepository.com/artifact/org.apache.tomcat.embed/tomcat-embed-jasper -->
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
        <version>11.0.1</version>
    </dependency>
    ```
    ```
    The difference between <a href=”/”> and <a href=”./”>
    /=> http:localhost:8080/
    ./ => “http:localhost:8080/”+{current project’s path}+“/”
    . means current directory/ webapplication
    ```
* Data Rendering
  * It is the process of passing data from a controller class to a physical view component by keeping it in a scope.
  * For every new request to the Spring Boot MVC app, the DispatcherServlet app creates one special class object called
    BindingAwareModelMap object having request scope, and it is used as a shared memory for data rendering 
  * The handler methods can access the object as one of their parameters as the following types 
    * Map<K,V>, [Best because of the non-invasive programming]
    * HashMap class
    * LinkedHashMap class
    * ModelMap class
    * Model interface
    * ExtendedModelMap class
    * BindingAwareModelMap class
    ```java
    //shared memory in handler method
    @RequestMapping("/request_path")
    public Model handlerMethod(){
        Model model=new BindingAwareModelMap();
        model.addAttribute("attr1","val1");
        model.addAttribute("sysDt",LocalDateTime.now());
        return model;
        //here request path of this method itself is Logical View Name
    }
    ```
    ```java
    //shared memory in handler method
    @RequestMapping("/request_path")
    public Map<String,Object> handlerMethod(){
        Map<String,Object> map=new HashMap();
        map.put("attr1","val1");
        map.put("sysDt",LocalDateTime.now());
        return map;
        //here request path of this method itself is Logical View Name
    }
    ```
  * If the return type of handler method is void or if handler method returns null then, request path of it becomes logical view name
  * If handler method is not returning logical view name i.e String directly or indirectly, then request path of the handler
    method becomes the logical view name
  * Using ModelAndView class you can add data and logical view name to shared memory and return it in handler methods. [Now this is legacy style]
  * Advantages of taking shared memory class as parameter instead of return type of handler method
    1. Need not to create shared memory manually, DispatcherServlet created object is used and not wasted
    2. Can have control over logical view name
  * Disadvantages of taking shared memory class as return type instead of parameter of handler method
    1. Need to create object for shared memory class i.e BindingAwareModelMap manually and return it
    2. DispatcherServlet created object would be wasted
    3. Loose control and flexibility over logical view name
  * Handler method chaining using forward and redirect internally uses RequestDispatcher's forward() and redirect() methods
    ```java
    @RequestMapping("/request_path1")
    public String handlerMethod1(){
       //
       //
       return "forward:request_path2";
    }
    @RequestMapping("/request_path2")
    public Model handlerMethod2(){
       Map<String,Object> map=new HashMap();
       map.put("attr1","val1");
       map.put("sysDt",LocalDateTime.now());
       return model;
    }
    ```
  * In forwarding request mode handler method chaining the source handler method and the destination handler method can be there either in same controller
    class or in two different controller classes of same web application.
  * In redirect request mode handler method chaining the source handler method and the destination handler method can be there either in same controller
    class or in two different controller classes of same web application or different web applications.
  * Handler methods can have HttpRequest,HttpResponse and HttpSession as parameters, on observing them, DispatcherServlet passes those
    objects as arguments while calling them.
  * To access ServletConfig and ServletContext objects you can inject them using @Autowired, by SpringBoot's autoconfiguration
    they are injected as spring beans, you don't have to do anything
  * Without using physical view components, a void type handler method can write output to browser directly using PrintWriter class

* Request paths
    1. Must start with “/”
    2. Request path is case-sensitive
    3. One handler method can be mapped with multiple request paths: @RequestMapping({“/req1”,”req2”}) or @RequestMapping(value={“/req1”,”req2”})
    4. Default request path “/”
    5. @RequestMapping without any arguments is possible
    6. Taking request path as “/” is equal to not taking any arguments
    7. Two handler methods of controller class can have same request path having two different request modes like GET, POST
* Hyperlink by default generates get request.
* In Spring 4.x, @xxxxMapping annotations are introduced as alternate for specifying request modes in @RequestMapping 
  annotation and these annotations are recommended to use.<br/>
    @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @PatchMapping,...
* In spring boot mvc maximum two methods can have same request path, one method with GET mode and the other is POST mode
* In spring boot mvc maximum two methods of a controller class can be there without request path.
* If two handler methods of two different controller classes are having same request path, 
  it results ambiguity error. To resolve it along with method level request paths provide the class level global path 
  using @RequestMapping annotation.
  > localhost:8081/######/gobal_request_path1/local_request_path1
* In request path of handler method we can place very limited special characters like, /, -, $,... we cannot place characters like white space, % because they have different meanings in the request url.
* How to pass collections and arrays, Model class obejct, collection of Model class objects from controller component to view component using Data Rendering techniques.
* Data Binding (One-way and Two-way)
  * One-way form data binding
      * use name attribute in form
      * use model class (Candidate)
      * use controller comp as follows
      ```java
      @Controller
      public class handlerClass{
          @GetMapping("/register")
          public String showFormPage(){
              return "form_page";
          }
          @PostMapping("/register")
          public String processFormPage(Map<String,Object> map, @ModelAttribute("candidate") Candidate candidObect) {
              System.out.println("Model class object data::"+candidObect);
              map.put("candidInfo",candidObject);
              return "results";
          }
      }
      ```
  * Two-way form data binding
    * use name attribute in form
      * use model class (Candidate)
      * use controller comp as follows
      ```java
      @Controller
      public class handlerClass{
          @GetMapping("/register")
          public String showFormPage(@ModelAttribute("candidate") Candidate candidObject){
              return "form_page";
          }
          @PostMapping("/register")
          public String processFormPage(Map<String,Object> map, @ModelAttribute("candidate2") Candidate candidObect) {
              System.out.println("Model class object data::"+candidObect);
              map.put("candidInfo",candidObject);
              return "results";
          }
      }
      ```
    * Data Binding using @RequestParam
      * use @RequestParam annotated attributes in handler method's signature
      * use param implicit object in jsp or jstl like header and many others
      * send arguments in the url: http://localhost:8081/@@@@@/request_path?parameter1=xx&parameter2=xxx&parameter3=xxx

* Useful Annotations of Spring Boot MVC
    * Stereotype annotations (@Controller, @Service, @Repository,...)
    * @RequestMapping, @GetMapping, @PostMapping for specifying request path, global X local request mappings.