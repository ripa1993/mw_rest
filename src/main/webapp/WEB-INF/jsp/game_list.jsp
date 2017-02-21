<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Games list</title>
</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param name="title" value="Game list" />
	</jsp:include>

	<div id="menu">
		<a href="http://localhost:8080/boardgamemanager/create_game.html">--New
			Game--</a>
	</div>

	<div id="games">
		<c:forEach var="game" items="${it.games}">
			<div class="game">
				<a href="${game.uri}">${game.name }</a>
			</div>
		</c:forEach>
	</div>
	<jsp:include page="footer.jsp" /></body>
</html>