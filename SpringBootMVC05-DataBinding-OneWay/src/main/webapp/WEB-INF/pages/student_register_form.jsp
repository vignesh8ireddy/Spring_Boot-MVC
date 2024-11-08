<%@ page isELIgnored="false"  %>

<h1 style="color:red;text-align:center"> Student registration form page</h1>

<form action="register" method="POST">
   <table border="0"  bgcolor="cyan" align="center">
      <tr>
         <td>Student Id:: </td>
         <td><input type="text" name="sno"> </td>
      </tr>
        <tr>
         <td>Student Name:: </td>
         <td><input type="text" name="sname"> </td>
      </tr>
        <tr>
         <td>Student Address:: </td>
         <td><input type="text" name="sadd"> </td>
      </tr>
       <tr>
         <td>Student avg:: </td>
         <td><input type="text" name="avg"> </td>
      </tr>
      <tr>
        <td colspan="2"><input type="submit" value="register"> </td>
      </tr>
   
   </table>

</form>