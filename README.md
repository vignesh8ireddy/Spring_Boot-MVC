# Spring Boot MVC

1. Spring Boot MVC Application Setup
    * Maven Dependencies(Spring Web, Spring DevTools Api, Apache JSTL API)
    * application.properties file
        * spring.mvc.view.prefix
        * spring.mvc.view.suffix
        * server.port
        * server.servlet.context-path
    * controller layer (handlers i.e @Controller)
    * webapps/WEB-INF/pages/
        * .jsp/.html files
2. Components and Working of a Spring MVC application.<br/>
    i. FrontController (also called DispatcherServlet)<br/>
    ii. HandlerMapping<br/>
    iii. Controller classes (handler classes and handler methods returning logical view name)<br/>
    iv. ViewResolver (returning details of physical view components) (configure it in application.properties)<br/>
    v. View Components(physical view components stored in webapps/WEB-INF/ directory)<br/>
    
    [@Controller, @RequestMapping, @GetMapping, @PostMapping,..., request path, global X local request mappings, logical view name, physical view components]

    ### Working of a Spring Boot MVC application
    I. The Spring MVC application is deployed in the web server <br/>
    II. The DispatcherServlet would be pre-instantiated and initialized because the load-on-startup is enabled for it. As a part of it's initialization the IOC container will be created(of type ApplicationContext) and performs pre-instantion of singleton scope spring beans like HandlerMappings, Handler Classes, service layer classes, DAO layer classes, View Resolvers,.. and completes the necessary dependancy injections and keeps all these spring beans in the internal cache of IOC container.<br/>
    III. When request is sent to MVC application (through browser/Postman), DispatcherServlet (FrontContoller) traps the request, applies the common system services and hand-overs the trapped request to the Handler Mapping component.<br/>
    IV. Handler Mapping component searches in all the @Controller classes (handlers) for the corresponding handler method whose request path matches with the path of currently trapped request (using reflection api) and returns the found handler controller's bean id and handler methods' signature to the Dispatcher Servlet.<br/>
    V. Dispatcher Servlet submits them to the IOC container(which is managing the Dispatcher Servlet), gets the instance of the handler class and calls the signature based handler method on it.<br/>
    VI. The handler method either directly process the request or delegates the request to service layer or DAO layer classes for processing and returns the Logical View Name to the Dispatcher Servlet.<br/>
    VII. Dispatcher Servlet now gives the Logical View Name to the View Resolver which resolves and identifies the physical view component and returns the View component's details.<br/>
    VIII. Finally, Dispatcher Servlet communicates with the physical View Component where final results (response) are gathered and formatted and sends back the formatted results to the browser as a response.<br/>
3. Data Rendering
* Shared memory has request scope, so for every new request new shared memory is created.
* Map<String,Object> map or Model class is used for creating a shared memory.
* Using Map is recommended to reduce spring api usage.
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

        