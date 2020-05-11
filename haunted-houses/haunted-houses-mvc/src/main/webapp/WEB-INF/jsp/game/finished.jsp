<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
<jsp:attribute name="body">

    <div class="jumbotron">
        <<h1>CONGRATULATIONS!</h1>
        <p class="lead">You have successfully banished the ghost after ${banishments} tries.</p>
        <p class="lead">Would you like to create a new game?</p>
        <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/game/new?playerId=${playerId}"
              role="button">Yes</a></p>
        <p><a class="btn btn-lg btn-danger" href="${pageContext.request.contextPath}"
              role="button">No</a></p>
    </div>

</jsp:attribute>
</my:pagetemplate>
