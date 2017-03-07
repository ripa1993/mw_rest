<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Game: ${it.game.id }</title>
<jsp:include page="/WEB-INF/jsp/includes.jsp" />

</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param name="title" value="Game" />
	</jsp:include>

	<div class="row">
		<div class="col-md-4">
			<img src="${it.game.coverArt}" alt="Cover art" height:
				auto; class="img-thumbnail" />
		</div>
		<div class="col-md-8">
			<dl class="dl-horizontal">
				<dt>Name</dt>
				<dd>${it.game.name}</dd>
				<dt>Min players</dt>
				<dd>${it.game.minPlayers}</dd>
				<dt>Max players</dt>
				<dd>${it.game.maxPlayers}</dd>
				<dt>Average play time</dt>
				<dd>${it.game.playTime}</dd>
				<dt>Minimum age</dt>
				<dd>${it.game.minAge}</dd>
				<dt>Difficulty</dt>
				<dd>${it.game.difficulty}</dd>
				<c:if test="${! it.game.designer == NULL}">
					<dt>Designer</dt>
					<dd>${it.game.designer}</dd>
				</c:if>
				<c:if test="${! it.game.artist == NULL}">

					<dt>Artist</dt>
					<dd>${it.game.artist}</dd>
				</c:if>
				<c:if test="${! it.game.publisher == NULL}">

					<dt>Publisher</dt>
					<dd>${it.game.publisher}</dd>
				</c:if>
			</dl>
		</div>
	</div>


	<jsp:include page="footer.jsp" />

</body>
</html>