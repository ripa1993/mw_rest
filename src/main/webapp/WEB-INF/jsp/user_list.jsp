<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User list</title>
</head>
<body>
	<div id="users">
		<c:forEach var="user" items="${it.users}">
			<div class="user">
				<a href="${user.uri}">${user.name }</a>
			</div>
		</c:forEach>
	</div>

</body>
</html>