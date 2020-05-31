<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Houses">
<jsp:attribute name="body">

    <my:a href="/house/new" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        New house
    </my:a>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Address</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${houses}" var="house">
            <tr>
                <td>${house.id}</td>
                <td><c:out value="${house.name}"/></td>
                <td><c:out value="${house.address}"/></td>
                <td>
                    <my:a href="/house/view/${house.id}" class="btn btn-primary">Details</my:a>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/house/update/${house.id}">
                        <button type="submit" class="btn btn-primary">Update</button>
                    </form>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/house/delete/${house.id}"
                          onsubmit="return confirm('Are you sure?') ? true : false;">
                        <button type="submit" class="btn btn-primary">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pagetemplate>