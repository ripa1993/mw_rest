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
		<form class="form-horizontal" action="../boardgamemanager/rest/users" method="POST">
			<div class="form-group">
				<label for="id" class="col-sm-2 control-label">Username</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="id"
						placeholder="Username">
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" name="password"
						placeholder="Password">
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">Name</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="name"
						placeholder="Name">
				</div>
			</div>
			<div class="form-group">
				<label for="mail" class="col-sm-2 control-label">Mail</label>
				<div class="col-sm-10">
					<input type="email" class="form-control" name="mail"
						placeholder="Mail">
				</div>
			</div>
			<div class="form-group">
				<label for="country" class="col-sm-2 control-label">Country</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="country"
						placeholder="Country">
				</div>
			</div>
			<div class="form-group">
				<label for="state" class="col-sm-2 control-label">State</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="state"
						placeholder="State">
				</div>
			</div>
			<div class="form-group">
				<label for="town" class="col-sm-2 control-label">Town</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="town"
						placeholder="Town">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">Register</button>
				</div>
			</div>
		</form>
	</div>


	<jsp:include page="/WEB-INF/jsp/footer.jsp" />

</body>
</html>