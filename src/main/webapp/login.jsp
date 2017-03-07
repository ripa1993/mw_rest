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
		<form class="form-signin" action="../boardgamemanager/rest/login"
			method="POST">
			<h2 class="form-signin-heading">Please sign in</h2>
			<label for="username" class="sr-only">Username</label> <input
				type="text" name="username" class="form-control"
				placeholder="Username" required autofocus> <label
				for="password" class="sr-only">Password</label> <input
				type="password" name="password" class="form-control"
				placeholder="Password" required>

			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>
		</form>
	</div>


	<jsp:include page="/WEB-INF/jsp/footer.jsp" />

</body>
</html>