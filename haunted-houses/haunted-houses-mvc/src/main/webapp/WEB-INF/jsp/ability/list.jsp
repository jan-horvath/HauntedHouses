<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Abilities">
<jsp:attribute name="body">

    <my:a href="/ability/new" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        New ability
    </my:a>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${abilities}" var="ability">
            <tr>
                <td>${ability.id}</td>
                <td><c:out value="${ability.name}"/></td>
                <td><c:out value="${ability.description}"/></td>
                <td>
                    <my:a href="/ability/view/${ability.id}" class="btn btn-primary">Details</my:a>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/ability/update/${ability.id}">
                        <button type="submit" class="btn btn-primary">Update</button>
                    </form>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/ability/delete/${ability.id}">
                        <button type="submit" class="btn btn-primary">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pagetemplate>