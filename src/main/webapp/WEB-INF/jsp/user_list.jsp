<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User list</title>
<jsp:include page="/WEB-INF/jsp/includes.jsp" />
</head>


<body>
	<jsp:include page="header.jsp">
		<jsp:param name="title" value="users" />
	</jsp:include>

	<script>
		var filter = function() {
			var base = "http://localhost:8080/boardgamemanager/rest/users?";
			var url = "";
			if ($("#id").val())
				url = url + "id=" + $("#id").val() + "&";

			if ($("#name").val())
				url = url + "name=" + $("#name").val() + "&";
			if ($("#country").val())
				url = url + "country=" + $("#country").val() + "&";
			if ($("#state").val())
				url = url + "state=" + $("#state").val() + "&";
			if ($("#town").val())
				url = url + "town=" + $("#town").val() + "&";
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
					<label class="" for="id">ID</label> <input type="text"
						class="form-control" id="id" placeholder="ripa1993">
				</div>
				<div class="form-group">
					<label class="" for="name">User name</label> <input type="text"
						class="form-control" id="name" placeholder="Simone">
				</div>
				<div class="form-group">
					<label class="" for="country">Country</label> <input type="text"
						class="form-control" id="country" placeholder="Italy">
				</div>
				<div class="form-group">
					<label class="" for="state">State</label> <input type="text"
						class="form-control" id="state" placeholder="CO">
				</div>
				<div class="form-group">
					<label class="" for="town">Town</label> <input type="text"
						class="form-control" id="town" placeholder="Guanzate">

					<div class="form-group">
						<label class="" for="orderby">Order By</label> <select
							class="form-control" id="orderby">
							<option value="id">ID</option>
							<option value="name">Name</option>
							<option value="country">Country</option>
							<option value="state">State</option>
							<option value="town">Town</option>
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
		<div class="panel-heading">Users list</div>

		<div id="games" class="list-group">
			<c:forEach var="user" items="${it.users}">
				<a href="${user.uri}" class="list-group-item">${user.name }</a>
			</c:forEach>
		</div>
	</div>


	<jsp:include page="footer.jsp" />

</body>
</html>