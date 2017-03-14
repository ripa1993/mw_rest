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

<script>
	var filter = function(){
		var base = window.location.href.split('?')[0] + "?";
		var url = "";
		if ($("#game").val())
			url = url + "game=" + $("#game").val() + "&";
		if ($("#date").val())
			url = url + "date=" + $("#date").val() + "&";
		if ($("#orderby").val())
			url = url + "orderby=" + $("#orderby").val() + "&";
		if ($("#order").val())
			url = url + "order=" + $("#order").val();

		window.location.href = base + url;
	}
</script>

<div class="row">
		<div class="container">
			<form class="form-inline" id="filter-form">
				<div class="form-group">
					<label class="" for="game">Game name</label> <input type="text"
						class="form-control" id="game" placeholder="Risk">
				</div>
				<div class="form-group">
					<label class="" for="date">Date</label> <input
						type="date" class="form-control" id="date" placeholder="18/03/2017">
				</div>
				
				<div class="form-group">
					<label class="" for="orderby">Order By</label> <select
						class="form-control" id="orderby">
						<option value="id">Play ID</option>
						<option value="name">Name</option>
						<option value="date">Date</option>
					</select>
				</div>
				<div class="form-group">
					<label class="" for="order">Ordering</label> <select
						class="form-control" id="order">
						<option value="asc">Ascending</option>
						<option value="desc">Descending</option>
					</select>
				</div>

				<button type="button" class="btn btn-default" onclick="filter();">Filter</button>
			</form>
		</div>
	</div>

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