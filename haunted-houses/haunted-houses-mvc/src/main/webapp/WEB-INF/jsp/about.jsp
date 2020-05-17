<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
<jsp:attribute name="body">
    <p class="lead">
        Haunted houses is a game where you search for specters and banish them from haunted houses.
    </p>

    <h2>Create game</h2>
    <p class="lead">
        Before you start playing, you first need to create a game.
        In order to so, you need to set the number of banishes required to complete the game.
        Game will start automatically after creation.
    </p>

    <h2>Gameplay</h2>
    <p class="lead">
        Once the game has been created a unique specter will haunt one of the houses in the selection.
        Your task is to find the haunted house based on the hint you are given.
        Once you find the haunted house, the specter is banished and flees into another house losing life in a process.
        After you banish the specter enough times the specter is defeated and you win the game.
    </p>
</jsp:attribute>
</my:pagetemplate>
