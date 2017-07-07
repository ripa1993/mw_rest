<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="it.polimi.moscowmule.boardgamemanager.game.GameStorage"%>
<%@ page import="it.polimi.moscowmule.boardgamemanager.user.UserStorage"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create new play</title>
<jsp:include page="/WEB-INF/jsp/includes.jsp" />

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="title" value="Play" />
	</jsp:include>

	<div class="container">
	
		<c:forEach var="error" items="${it.errors}">
			<div class="alert alert-danger">
			  ${error }
			</div>
		</c:forEach>
	
		<form class="form-horizontal" action="../rest/plays" method="POST">

			<!-- <div class="form-group">
				<label for="userId" class="col-sm-2 control-label">User</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="userId"
						placeholder="Pippo">
				</div>
			</div> -->
			<div class="form-group">
				<label for="gameId" class="col-sm-2 control-label">Game</label>
				<div class="col-sm-10">
					<!-- <input type="text" class="form-control" name="gameId"
						placeholder="Risk"> -->
				    <select name="gameId" class="form-control">
				      <c:forEach var="game" items="<%=GameStorage.instance.getAllNames()%>">
				        <option value="${game.key}"><c:out value="${game.value}"/></option>
				      </c:forEach>
				    </select>
						
				</div>
			</div>
			<div class="form-group">
				<label for="date" class="col-sm-2 control-label">Date</label>
				<div class="col-sm-10">
					<input type="date" class="form-control" name="date"
						placeholder="12/03/2015">
				</div>
			</div>
			<div class="form-group">
				<label for="timeToComplete" class="col-sm-2 control-label">Time to complete</label>
				<div class="col-sm-10">
					<input type="number" class="form-control" name="timeToComplete"
						placeholder="120">
				</div>
			</div>
			<div class="form-group">
				<label for="numPlayers" class="col-sm-2 control-label">Num of players</label>
				<div class="col-sm-10">
					<input type="number" class="form-control" name="numPlayers"
						placeholder="4">
				</div>
			</div>
			<div class="form-group">
				<label for="winnerId" class="col-sm-2 control-label">Winner</label>
				<div class="col-sm-10">
					<!-- <input type="text" class="form-control" name="winnerId"
						placeholder="Pluto">-->
					<select name="winnerId" class="form-control">
					  <option value=""></option>
				      <c:forEach var="user" items="<%=UserStorage.instance.getAllIds()%>">
				        <option value="${user}"><c:out value="${user}"/></option>
				      </c:forEach>
				    </select>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">Submit</button>
				</div>
			</div>
		</form>
	</div>


	<jsp:include page="/WEB-INF/jsp/footer.jsp" />

</body>
</html>