<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Play list</title>
</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param name="title" value="Play List" />
	</jsp:include>
	<div id="plays">
		<c:forEach var="play" items="${it.plays}">
			<div class="play">
				<a href="${play.uri}">Created by user ${play.userId } on game
					${play.gameId } on ${play.date }</a>
			</div>
		</c:forEach>
	</div>
	<jsp:include page="footer.jsp" />

</body>
</html>