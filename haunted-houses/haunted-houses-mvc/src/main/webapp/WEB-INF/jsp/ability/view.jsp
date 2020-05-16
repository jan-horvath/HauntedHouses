<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Ability Administration">
<jsp:attribute name="body">

    <form method="post" action="${pageContext.request.contextPath}/ability/delete/${ability.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/ability/update/${ability.id}">
        <button type="submit" class="btn btn-primary">Update</button>
    </form>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${ability.id}</td>
            <td><c:out value="${ability.name}"/></td>
            <td><c:out value="${ability.description}"/></td>
        </tr>
        </tbody>
    </table>

    <my:a href="/ability/list" class="btn btn-primary">
        Back
    </my:a>

</jsp:attribute>
</my:pagetemplate>