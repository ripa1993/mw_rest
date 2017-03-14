<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Games list</title>
<jsp:include page="/WEB-INF/jsp/includes.jsp" />

</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param name="title" value="games" />
	</jsp:include>

	<script>
		var filter = function() {
			var base = "http://localhost:8080/boardgamemanager/rest/games?";
			var url = "";
			if ($("#name").val())
				url = url + "name=" + $("#name").val() + "&";
			if ($("#players").val())
				url = url + "players=" + $("#players").val() + "&";
			if ($("#time").val())
				url = url + "time=" + $("#time").val() + "&";
			if ($("#age").val())
				url = url + "age=" + $("#age").val() + "&";
			if ($("#difficulty").val())
				url = url + "difficulty=" + $("#difficulty").val() + "&";
			if ($("#designer").val())
				url = url + "designer=" + $("#designer").val() + "&";
			if ($("#artist").val())
				url = url + "artist=" + $("#artist").val() + "&";
			if ($("#publisher").val())
				url = url + "publisher=" + $("#publisher").val() + "&";
			if ($("#orderby").val())
				url = url + "orderby=" + $("#orderby").val() + "&";
			if ($("#order").val())
				url = url + "order=" + $("#order").val();

			window.location.href = base + url;
		};
	</script>

	<div class="row">
		<div class="container">
			<form class="form-inline" id="filter-form">
				<div class="form-group">
					<label class="" for="name">Game name</label> <input type="text"
						class="form-control" id="name" placeholder="Risk">
				</div>
				<div class="form-group">
					<label class="" for="players">Number of players</label> <input
						type="number" class="form-control" id="players" placeholder="4">
				</div>
				<div class="form-group">
					<label class="" for="time">Available time</label> <input
						type="number" class="form-control" id="time" placeholder="120">
				</div>
				<div class="form-group">
					<label class="" for="age">Age</label> <input type="number"
						class="form-control" id="age" placeholder="8">
				</div>
				<div class="form-group">
					<label class="" for="difficulty">Difficulty</label> <input
						type="number" class="form-control" id="difficulty"
						placeholder="2.50">
				</div>
				<div class="form-group">
					<label class="" for="designer">Designer</label> <input type="text"
						class="form-control" id="designer" placeholder="Mark">
				</div>
				<div class="form-group">
					<label class="" for="artist">Artist</label> <input type="text"
						class="form-control" id="artist" placeholder="Peter">
				</div>
				<div class="form-group">
					<label class="" for="publisher">Publisher</label> <input
						type="text" class="form-control" id="publisher"
						placeholder="Funny Games Inc">
				</div>
				<div class="form-group">
					<label class="" for="orderby">Order By</label> <select
						class="form-control" id="orderby">
						<option value="id">ID</option>
						<option value="name">Name</option>
						<option value="minPlayers">Minimum # of Players</option>
						<option value="maxPlayers">Maximum # of Players</option>
						<option value="playTime">Play Time</option>
						<option value="difficulty">Difficulty</option>
						<option value="minAge">Minimum Age</option>
						<option value="designer">Desisgner</option>
						<option value="artist">Artist</option>
						<option value="publisher">Publisher</option>

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
		<div class="panel-heading">Games list</div>

		<div id="games" class="list-group">
			<c:forEach var="game" items="${it.games}">
				<a href="${game.uri}" class="list-group-item">${game.name }</a>
			</c:forEach>
		</div>
	</div>

	<jsp:include page="footer.jsp" /></body>
</html>