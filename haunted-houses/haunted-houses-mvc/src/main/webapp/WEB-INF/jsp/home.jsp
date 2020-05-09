<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
<jsp:attribute name="body">

    <div class="jumbotron">
        <h1>Welcome to Haunted Houses game</h1>
        <p class="lead">Lorem ipsum dolor sit amet, consectetur adipiscing elit. </p>
        <p><a class="button" href="${pageContext.request.contextPath}/game/check_game?playerid=1">Call game controller</a></p>
        <p><a class="button" href="${pageContext.request.contextPath}/game/test">TEST</a></p>
        <p><a class="button" href="${pageContext.request.contextPath}/game/populate">POPULATE</a></p>
        <p><a class="button" href="${pageContext.request.contextPath}/game/populate_players">populate with more players (click before attempting to log in)</a></p>
        <p><a class="button" href="${pageContext.request.contextPath}/login">Go to log in page.</a></p>
        <p><a class="button" href="${pageContext.request.contextPath}/admin">Go to admin page.</a></p>
    </div>

</jsp:attribute>
</my:pagetemplate>
