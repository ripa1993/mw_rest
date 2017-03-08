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
	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="title" value="Game" />
	</jsp:include>

	<div class="container">
		<form class="form-horizontal" action="../boardgamemanager/rest/games"
			method="POST" enctype="multipart/form-data">

			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">Name</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="name"
						placeholder="Risk">
				</div>
			</div>
			<div class="form-group">
				<label for="minPlayers" class="col-sm-2 control-label">Minimum
					number of players</label>
				<div class="col-sm-10">
					<input type="number" class="form-control" name="minPlayers"
						placeholder="2">
				</div>
			</div>
			<div class="form-group">
				<label for="maxPlayers" class="col-sm-2 control-label">Maximum
					number of players</label>
				<div class="col-sm-10">
					<input type="number" class="form-control" name="maxPlayers"
						placeholder="8">
				</div>
			</div>
			<div class="form-group">
				<label for="playTime" class="col-sm-2 control-label">Play
					Time</label>
				<div class="col-sm-10">
					<input type="number" class="form-control" name="playTime"
						placeholder="120">
				</div>
			</div>
			<div class="form-group">
				<label for="minAge" class="col-sm-2 control-label">Minimum
					Age</label>
				<div class="col-sm-10">
					<input type="number" class="form-control" name="minAge"
						placeholder="10">
				</div>
			</div>
			<div class="form-group">
				<label for="difficulty" class="col-sm-2 control-label">Difficulty</label>
				<div class="col-sm-10">
					<input type="number" class="form-control" name="difficulty"
						placeholder="1 easy, 5 hard">
				</div>
			</div>
			<div class="form-group">
				<label for="designers" class="col-sm-2 control-label">Designers</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="designers"
						placeholder="Arthur Boyle, Charlie Doom">
				</div>
			</div>
			<div class="form-group">
				<label for="artists" class="col-sm-2 control-label">Artists</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="artist"
						placeholder="Emily Ferguson, George Hot">
				</div>
			</div>
			<div class="form-group">
				<label for="publisher" class="col-sm-2 control-label">Publisher</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="publisher"
						placeholder="Awesome Games, inc.">
				</div>
			</div>
			<div class="form-group">
				<label for="file" class="col-sm-2 control-label">Image</label>
				<div class="col-sm-10">
					<input type="file" class="form-control" name="file">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">Create</button>
				</div>
			</div>
		</form>
	</div>


	<jsp:include page="/WEB-INF/jsp/footer.jsp" />

</body>
</html>