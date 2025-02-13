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
  3. HandlerMapping: This is a helper component which maps the requests trapped by the FrontController to the appropriate Handler class
  4. View Component: .jsp files or any other physical UI components stored in webapps/WEB-INF/ directory
  5. View Resolver: A helper component which resolves the logical view name to the physical View Components

* Spring Boot MVC Application Setup
    * Maven Dependencies(Spring Web, Spring DevTools API, Apache JSTL API)
    * application.properties file
        * spring.mvc.view.prefix
        * spring.mvc.view.suffix
        * server.port
        * server.servlet.context-path
    * controller layer (handlers i.e @Controller)
    * webapps/WEB-INF/pages/
        * .jsp/.html files

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
* Data Rendering
  * Shared memory has request scope, so for every new request new shared memory is created.
  * Map<String,Object> map or Model class is used for creating a shared memory.
  * Using Map is recommended to reduce spring api usage.
  * A Request Path must start with "/".
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

* If return type of  a handler method is void then request path becomes logical view name
* If return type of a handler method is String but the returned value is null then also request path becomes the logical view name
* What is difference between a href=”/” and a href=”./”
    ```
    /=> http:localhost:8080/
    ./ => “http:localhost:8080/”+{current project’s path}+“/”
    ". means current directory/ webapplication"
    ```

* Forwarding request from one handler to another handler, forward:{request_path} or
redirect:{request_path}
```java
@RequestMapping("/request_path1")
public String handlerMethod1(){
    /@@@@/
    /@@@@/
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
* How to access Request, Response, Session objects in the handler methods?
```java
@RequestMapping("/request_path1")
public String handlerMethod(HttpServletRequest req, HttpServletResponse res, HttpSession ses){
    System.out.println("ShowHomeController.process()::"+req.hashCode());
    req.setAttribute("attr1","val1");
    ses.setAttribute("sysDt",LocalDateTime.now);
    return "logical_view_name";    
}
```
* How to access ServletConfig object and ServletContext obj to controller class handler methods?
```java
//autowire them
@RequestMapping("/request_path")
public String handlerMethod(){
    @Autowired
    private ServletContext scontext;
    @Autowired
    private ServletConfig sconfig;
    /@@@/
    /@@@/
    return "logical_view_name";
}
```
We are injecting them here from DispatcherServlet as spring beans given by AutoConfiguration.
> Note: If any parameter type is not in the list of allowed parameters of hanlder method and that object available
through DispatcherServlet then go for @Autowired based injections to controller classes otherwise
take them as the param types of the handler methods.<br/>

> Note: Generally, the objects that are specific to each request directly or indirectly take them
as handler method arguments and similarly the objects that are visible across the multiple requests take them as 
@Autowired based injections.
<br/>
* How to send data direclty without using view components?<br>
```java
@RequestMapping("/request_path")
public String handlerMethod(){
    PrintWriter pw=res.getWriter();
    res.setContentType("text/html");
    pw.println("<b>response directly from handler method</b>");
}
```
* Understanding the request paths

    1. Must start with “/”
    2. Request path is case sensitive
    3. One handler method can be mapped with multiple request paths: @RequestMapping({“/req1”,”req2”}) or @RequestMapping(value={“/req1”,”req2”})
    4. Default request path “/”
    5. @RequestMapping without any arguments is possible
    6. Taking request path as “/” is equal to not taking any arguments
    7. Two handler methods of controller class can have same request path having two different request modes like GET, POST
* You know that hyperlink by default generates get  request, don't you?
* In Spring 4.x, @xxxxMapping annotations are introduced as alternate for specifying request modes in @RequestMapping annotation and these annotations are recomended to use.<br/>
    @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @PatchMapping,...
* In spring mvc or spring boot mvc maximum two methods can have same request path, one method with GET mode and the other is POST mode
* In spring mvc or spring boot mvc maximum two methods of a controller class can be there without request path.
* What happens of two handler methods of two different controller classes are having same request path? <br/>
=> ambiguity error. To resolve it along with method level request paths provide the class level global path using @RequestMapping annotation.
```java
@RequestMapping("/global_request_path1")
@Controller
public class HandlerClass1{
    @GetMapping("/local_request_path1")
    public String handlerMethod1(){
        /@@@/
        return "logical_view_component";
    }
}

@RequestMapping("/global_request_path2")
@Controller
public class HandlerClass2{
    @GetMapping("/local_request_path2")
    public String handlerMethod2(){
        /@@@/
        return "logical_view_component";
    }
}
```
> localhost:8081/@@@@@@/gobal_request_path1/local_request_path1
<br/>
* In request path of handler method we can place very limited special characters like, /, -, $,... we cannot place characters like white space, % because they have different meanings in the request url.
* How to pass collections and arrays, Model class obejct, collection of Model class objects from controller component to view component using Data Rendering techniques.

4. Data Binding (One-way and Two-way)
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