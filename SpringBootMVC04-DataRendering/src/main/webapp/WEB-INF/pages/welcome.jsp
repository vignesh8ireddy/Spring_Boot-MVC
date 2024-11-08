
<%@page isELIgnored="false" import="java.util.*" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>


<h1 style="color:red;text-align:center">Welcome to Spring Web MVC(welcome.jsp) </h1>

<br><br>

 <%-- The   data is  :: ${name},${age} </b> --%><b> 


 
   <%--  
      <b> nick names are :: <%=Arrays.toString(((String[])request.getAttribute("nickNames"))) %></b> <br>
  <b> The   data is  :: ${friends},<br>${phoneNumbers},<br> ${idDetails} </b>
 --%>
  
<%-- <c:if  test="${!empty nickNames}">
    <br><b>  the nick names are :: </b>
           <c:forEach var="nick" items="${nickNames}">
                 ${nick},
           </c:forEach>
  </c:if>  <br>
  
  <c:if  test="${!empty friends}">
    <br><b>  the friends are :: </b>
           <c:forEach var="fr" items="${friends}">
                 ${fr},
           </c:forEach>
  </c:if>  <br>
  
  <c:if  test="${!empty phoneNumbers}">
    <br><b>  the phoneNumbers are :: </b>
           <c:forEach var="ph" items="${phoneNumbers}">
                 ${ph},
           </c:forEach>
  </c:if>  <br>
  
  <c:if  test="${!empty idDetails}">
    <br><b>  the idDetails are :: </b>
           <c:forEach var="id" items="${idDetails}">
                 ${id},
           </c:forEach>
  </c:if>  <br> --%>
  
 
 
  <%-- 
  <b> Emp Data  :: ${empData}  </b>  <br>
   
   <c:if  test="${!empty empData}">
         <b> Emp Data  :: ${empData}  </b>
   </c:if>
  
    --%>
   
   <%-- 
   <c:choose>
     <c:when test="${!empty empsList}">
        <table bgcolor="yellow"  align="center" border="1">
           <tr> <th> eno</th>  <th> ename</th> <th> eaddrs</th> <th> salary</th> </tr>
           <c:forEach var="emp" items="${empsList}">
              <tr>
                <td>${emp.eno}  </td>
                <td>${emp.ename}  </td>
                <td>${emp.eaddrs}  </td>
                <td>${emp.salary}  </td>
              </tr>
           </c:forEach> 
        
        </table>
     
     </c:when>
   </c:choose>
  --%>
  