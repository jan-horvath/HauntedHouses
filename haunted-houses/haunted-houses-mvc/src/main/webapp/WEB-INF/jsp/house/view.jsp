<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="House Administration">
<jsp:attribute name="body">

    <form method="post" action="${pageContext.request.contextPath}/house/delete/${house.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/house/update/${house.id}">
        <button type="submit" class="btn btn-primary">Update</button>
    </form>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Address</th>
            <th>Haunted since</th>
            <th>History</th>
            <th>Hint</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${house.id}</td>
            <td><c:out value="${house.name}"/></td>
            <td><c:out value="${house.address}"/></td>
            <td><fmt:formatDate value="${house.hauntedSince}" pattern="yyyy-MM-dd"/></td>
            <td><c:out value="${house.history}"/></td>
            <td><c:out value="${house.hint}"/></td>
        </tr>
        </tbody>
    </table>

    <my:a href="/house/list" class="btn btn-primary">
        Back
    </my:a>

</jsp:attribute>
</my:pagetemplate>