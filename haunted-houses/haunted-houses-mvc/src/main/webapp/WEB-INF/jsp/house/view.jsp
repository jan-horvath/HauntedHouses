<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="House Administration">
<jsp:attribute name="body">

    <div class="card">
        <ul class="list-group list-group-flush">
            <li class="list-group-item"><b>ID: </b>${house.id}</li>
            <li class="list-group-item"><b>Name: </b><c:out value="${house.name}"/></li>
            <li class="list-group-item"><b>Address: </b><c:out value="${house.address}"/></li>
            <li class="list-group-item"><b>Haunted since: </b><fmt:formatDate value="${house.hauntedSince}" pattern="yyyy-MM-dd"/></li>
            <li class="list-group-item"><b>History: </b><c:out value="${house.history}"/></li>
            <li class="list-group-item"><b>Clue: </b><c:out value="${house.clue}"/></li>
        </ul>
    </div>

    <table class="table" style="table-layout: fixed">
        <tbody>
            <tr>
                <td style="text-align: center;">
                    <form method="post" action="${pageContext.request.contextPath}/house/update/${house.id}">
                        <button type="submit" class="btn btn-primary">Update</button>
                    </form>
                </td>
                <td style="text-align: center;">
                    <form method="post" action="${pageContext.request.contextPath}/house/delete/${house.id}"
                          onsubmit="return confirm('Are you sure?') ? true : false;">
                        <button type="submit" class="btn btn-primary">Delete</button>
                    </form>
                </td>
                <td style="text-align: center;">
                    <my:a href="/house/list" class="btn btn-primary">
                        Back
                    </my:a>
                </td>
            </tr>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>