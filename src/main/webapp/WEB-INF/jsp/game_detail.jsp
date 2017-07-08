<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	<c:choose>
		<c:when test="${it.not_found }">
			Game not found
		</c:when>
		<c:otherwise>
			Game: ${it.game.name }
		</c:otherwise>
	</c:choose>
</title>
<jsp:include page="/WEB-INF/jsp/includes.jsp" />

</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param name="title" value="Game" />
	</jsp:include>

	<c:choose>
		<c:when test="${it.not_found }">
			<div class="row">
				<div class="error-cont" style="padding: 40px 15px;text-align: center;">
					<h1>Oops!</h1>
					<h2>404 Not Found</h2>
					<div class="error-detils">Sorry an error has occurred, requested page not found!</div>
					
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="row">
				<div class="col-md-4">
					<img src="${it.game.coverArt}" alt="Cover art" height="auto"
						class="img-thumbnail" />
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
		</c:otherwise>
	</c:choose>


	<jsp:include page="footer.jsp" />

</body>
</html>