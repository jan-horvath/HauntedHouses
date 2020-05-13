<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
<jsp:attribute name="body">

    <table class="table" style="border:none">
    <tbody>
        <tr><td><h4><b>Specter info</b></h4></td></tr>
        <tr><td>Name: </td><td>${specter.name}</td></tr>
        <tr><td>Description: </td><td>${specter.description}</td></tr>
        <tr><td>ADD MORE INFO<td></td></tr>
        <tr><td><b>Successful banishments required:</b></td><td>${game.banishesRequired}</td></tr>
        <tr><td><b>Banshments attempted:</b></td><td>${game.banishesAttempted}</td></tr>
    </tbody>
    </table>

    <h4>Hint: ${hint}</h4>


    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Address</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${allHouses}" var="house">
            <tr>
                <td><c:out value="${house.name}"/></td>
                <td><c:out value="${house.address}"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/game/banish?houseId=${house.id}">
                        <button type="submit" class="btn btn-primary">Select</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>