<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Play: ${it.play.id}</title>
</head>
<body>
	<div>
		<ul>
			<li> Created by <a href="${it.play.userUri }">user ${it.play.userId}</a>
			<li> Played on <a href="${it.play.gameUri }">game ${it.play.gameId }</a>
			<li> Date: ${it.play.date }
			<c:if test="${it.play.numPlayers > 0}">
			<li> Players: ${it.play.numPlayers }
			</c:if>
			<c:if test="${it.play.timeToComplete > 0 }">
			<li> Completed in: ${it.play.timeToComplete } minutes
			</c:if>
			<c:if test="${! it.play.winnerId == NULL}">
			<li> Game won by <a href="${it.play.winnerUri }">user ${it.play.winnerId }</a>
			</c:if>
		</ul>
	</div>
</body>
</html>