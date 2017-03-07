<%@page
	import="it.polimi.moscowmule.boardgamemanager.authentication.Authenticator"%>
<%@ page import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String authToken = null;
	Cookie cookie = null;
	Cookie[] cookies = null;
	// Get an array of Cookies associated with this domain
	cookies = request.getCookies();
	if (cookies != null) {
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			if (cookie.getName().equals("auth_token")) {
				authToken = cookie.getValue();
			}
		}
	}
	// export auth_token
	pageContext.setAttribute("auth_token", authToken);

	boolean validToken = Authenticator.getInstance().isAuthTokenValid(authToken);
	pageContext.setAttribute("valid_token", validToken);

	String username = Authenticator.getInstance().getUserFromToken(authToken);
	pageContext.setAttribute("username", username);
%>

<form id="logoutForm" action="http://localhost:8080/boardgamemanager/rest/logout" method="POST" style="display: none">
	
</form>


<nav class="navbar navbar-default">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand"
				href="http://localhost:8080/boardgamemanager/">Boardgame Manager</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<c:choose>
					<c:when test="${param.title == 'users' }">
						<li class="active">
					</c:when>
					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="http://localhost:8080/boardgamemanager/rest/users">Users</a>
				</li>
				<c:choose>
					<c:when test="${param.title == 'games' }">
						<li class="active">
					</c:when>
					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="http://localhost:8080/boardgamemanager/rest/games">Games</a>
				</li>

				<c:choose>
					<c:when test="${param.title == 'plays' }">
						<li class="active">
					</c:when>
					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="http://localhost:8080/boardgamemanager/rest/plays">Plays</a>
				</li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<c:if test="${valid_token }">
					<li><p class="navbar-text">Signed in as ${username }</p></li>
				</c:if>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Menu <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<c:if test="${valid_token }">
							<li><a
								href="http://localhost:8080/boardgamemanager/create_game.html">New
									Game</a></li>
							<li><a
								href="http://localhost:8080/boardgamemanager/create_play.html">New
									Play</a></li>
							<li role="separator" class="divider"></li>

							<li><a href="" onclick="$('#logoutForm').submit(); return false;">Logout</a></li>
						</c:if>
						<c:if test="${! valid_token }">
							<li><a
								href="http://localhost:8080/boardgamemanager/login.jsp">Login</a></li>
							<li><a
								href="http://localhost:8080/boardgamemanager/register.jsp">Register</a></li>
						</c:if>

					</ul></li>
			</ul>



		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>