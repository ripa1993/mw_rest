<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User: ${it.user.id}</title>
</head>
<body>
	<div>
		<ul>
			<li> Name: ${it.user.name }
			<li> Country: ${it.user.country }
			<li> State: ${it.user.state }
			<li> Town: ${it.user.town }
		</ul>
	</div>
</body>
</html>