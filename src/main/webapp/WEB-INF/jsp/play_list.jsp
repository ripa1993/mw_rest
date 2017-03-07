<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Play list</title>
<jsp:include page="/WEB-INF/jsp/includes.jsp" />

</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param name="title" value="plays" />
	</jsp:include>


	<div class="panel panel-default">
		<div class="panel-heading">Plays list</div>

		<div id="games" class="list-group">
			<c:forEach var="play" items="${it.plays}">
				<a href="${play.uri}" class="list-group-item">Created by user
					${play.userId } on game ${play.gameId } on ${play.date }</a>
			</c:forEach>
		</div>
	</div>


	<jsp:include page="footer.jsp" />

</body>
</html>