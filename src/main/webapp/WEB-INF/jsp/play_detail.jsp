<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="it.polimi.moscowmule.boardgamemanager.game.GameStorage"%>
<%@ page import="it.polimi.moscowmule.boardgamemanager.play.Play"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Play: ${it.play.id}</title>
<jsp:include page="/WEB-INF/jsp/includes.jsp" />
<c:set var="play" value="${it.play}"/>

</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param name="title" value="Play" />
	</jsp:include>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-8">
			<dl class="dl-horizontal">
				<dt>Created by</dt>
				<dd>
					<a href="${it.play.userUri }">${it.play.userId}</a>
				</dd>
				<dt>Played on</dt>
				<dd>
					<a href="${it.play.gameUri }">
					<% 
						Play play = (Play) pageContext.getAttribute("play");
						out.println(GameStorage.instance.getAllNames().get(play.getGameId()));
						
					%>
					</a>
				</dd>
				<dt>Date</dt>
				<dd>${it.play.date }</dd>
				<c:if test="${it.play.numPlayers > 0}">
					<dt>Players</dt><dd>${it.play.numPlayers }</dd>
				</c:if>
				<c:if test="${it.play.timeToComplete > 0 }">
					<dt>Completed in</dt><dd>${it.play.timeToComplete } minutes</dd>
				</c:if>
				<c:if test="${fn:length(it.play.winnerUri) > 0 }">
					<dt>
						Game won by</dt><dd><a href="${it.play.winnerUri }">${it.play.winnerId }</a><dd>
				</c:if>
			</dl>
		</div>

	</div>
	<jsp:include page="footer.jsp" />

</body>
</html>