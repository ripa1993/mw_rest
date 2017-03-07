<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Play: ${it.play.id}</title>
<jsp:include page="/WEB-INF/jsp/includes.jsp" />

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
					<a href="${it.play.userUri }">user ${it.play.userId}</a>
				</dd>
				<dt>Played on</dt>
				<dd>
					<a href="${it.play.gameUri }">game ${it.play.gameId }</a>
				</dd>
				<dt>Date</dt>
				<dd>${it.play.date }</dd>
				<c:if test="${it.play.numPlayers > 0}">
					<dt>Players</dt><dd>${it.play.numPlayers }</dd>
				</c:if>
				<c:if test="${it.play.timeToComplete > 0 }">
					<dt>Completed in</dt><dd>${it.play.timeToComplete } minutes</dd>
				</c:if>
				<c:if test="${! it.play.winnerId == NULL}">
					<dt>
						Game won by</dt><dd><a href="${it.play.winnerUri }">user
							${it.play.winnerId }</a><dd>
				</c:if>
			</dl>
		</div>

	</div>
	<jsp:include page="footer.jsp" />

</body>
</html>