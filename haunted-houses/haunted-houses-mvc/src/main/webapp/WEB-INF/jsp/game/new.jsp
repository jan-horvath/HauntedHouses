<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New game">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/game/create?playerId=${playerId}"
               modelAttribute="createDTO" cssClass="form-horizontal">
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="banishesRequired" cssClass="col-sm-2 control-label">Banishes required:</form:label>
            <div class="col-sm-10">
                <form:input path="banishesRequired" cssClass="form-control"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Create game</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>