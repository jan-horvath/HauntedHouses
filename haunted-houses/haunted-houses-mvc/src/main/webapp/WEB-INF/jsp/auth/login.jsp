<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Log in">
<jsp:attribute name="body">
           <div class="jumbotron">
               <form name='f' action="perform_login" method='POST'>
                   <table>
                       <tr>
                           <td>Email:</td>
                           <td><input type='text' name='username' value=''></td>
                       </tr>
                       <tr>
                           <td>Password:</td>
                           <td><input type='password' name='password' /></td>
                       </tr>
                       <tr>
                           <td><button class="btn btn-primary" type="submit">Log in</button></td>
                       </tr>
                   </table>
                   <c:if test="${ param.error ne null}">
                       Invalid credentials. Try again.<br />
                   </c:if>
               </form>
           </div>
</jsp:attribute>
</my:pagetemplate>