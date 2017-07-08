<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><c:choose>
		<c:when test="${it.not_found }">
			User not found
		</c:when>
		<c:otherwise>
			User: ${it.user.id }
		</c:otherwise>
	</c:choose></title><jsp:include page="/WEB-INF/jsp/includes.jsp" />

</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param name="title" value="User" />
	</jsp:include>

	<c:choose>
		<c:when test="${it.not_found }">
			<div class="row">
				<div class="error-cont"
					style="padding: 40px 15px; text-align: center;">
					<h1>Oops!</h1>
					<h2>404 Not Found</h2>
					<div class="error-detils">Sorry an error has occurred,
						requested page not found!</div>

				</div>
			</div>
		</c:when>
		<c:otherwise>

			<div class="page-header">
				<h4>User ${it.user.id} details</h4>
			</div>
			<div class="row">
				<div class="col-md-4">
					<a class="btn btn-default" href="${it.user.playsUri }"
						role="button">Show plays</a>
				</div>
				<div class="col-md-8">
					<dl class="dl-horizontal">
						<dt>Name</dt>
						<dd>${it.user.name }</dd>
						<dt>Country</dt>
						<dd>${it.user.country }</dd>
						<dt>State</dt>
						<dd>${it.user.state }</dd>
						<dt>Town</dt>
						<dd>${it.user.town }</dd>
					</dl>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	<jsp:include page="footer.jsp" />

</body>
</html>