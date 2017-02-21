<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="header">

	<div style="float: left; display: block">
		<h1>
			<c:out value="${param.title}" />
		</h1>
	</div>
	<div style="float: left; display: block">
		<ul>
			<li><a href="http://localhost:8080/boardgamemanager/rest/users">Users</a>
			<li><a href="http://localhost:8080/boardgamemanager/rest/games">Games</a>
			<li><a href="http://localhost:8080/boardgamemanager/rest/plays">Plays</a>
		</ul>
	</div>
	<div style="clear: both;"></div>
</div>