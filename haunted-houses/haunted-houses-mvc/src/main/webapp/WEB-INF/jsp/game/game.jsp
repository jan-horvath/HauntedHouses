<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
<jsp:attribute name="body">

    <table style="font-size: large; border-collapse: separate; margin-left: auto; margin-right: auto;
        table-layout: fixed; border-spacing: 1cm"><tr><td>
    <table style="border-collapse: separate; border-spacing: 10px">
        <caption>Specter info</caption>
        <tbody>
        <tr><td>Name:</td><td>${specter.name}</td></tr>
        <tr><td>Start of haunting:</td><td>${specter.startOfHaunting}</td></tr>
        <tr><td>End of haunting:</td><td>${specter.endOfHaunting}</td></tr>
        <tr><td>Description:</td><td>${specter.description}</td></tr>
        </tbody>
    </table>
    </td><td>
    <table style="border-collapse: separate; border-spacing: 10px">
        <caption>Specter abilities</caption>
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${abilities}" var="ability">
            <tr><td><c:out value="${ability.name}"/></td><td><c:out value="${ability.description}"/></td></tr>
        </c:forEach>
        </tbody>
    </table>
    </td></tr></table>

    <table style="border: 3px solid black; font-size: large; margin-left: auto; margin-right: auto;
        background-color: #BBBBBB; margin-bottom: 0.5cm;">
        <tr><td>Successful banishments required: ${game.banishesRequired}</td></tr>
        <tr><td>Banshments attempted: ${game.banishesAttempted}</td></tr>
    </table>
    <div style="text-align: center; margin-bottom: 1cm; font-size: large;">
        <span><b>Guess the house where the specter is hiding!</b></span>
        <br>
        <span style="border: 3px solid #EE1111; background: #EE1111">Clue: </span>
        <span style="border: 3px solid #EE1111">${clue}</span>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Address</th>
            <th>History</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="house" items="${housesSubset}">
            <tr>
                <td><c:out value="${house.name}"/></td>
                <td><c:out value="${house.address}"/></td>
                <td><c:out value="${house.history}"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/game/banish?houseId=${house.id}">
                        <button type="submit" class="btn btn-primary">Select</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <form method="post" action="${pageContext.request.contextPath}/game/end"
          onsubmit="return confirm('Are you sure you want to end the game prematurely?') ? true : false;">
        <button type="submit" class="btn btn-primary">End game</button>
    </form>

</jsp:attribute>
</my:pagetemplate>