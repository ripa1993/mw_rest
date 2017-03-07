<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
			<!-- <a class="navbar-brand" href="#"><c:out value="${param.title}" /></a> -->
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
			<!-- TODO: mettere active solo per la pagina corrente -->

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Menu <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a
							href="http://localhost:8080/boardgamemanager/create_game.html">New
								Game</a></li>
						<li><a
							href="http://localhost:8080/boardgamemanager/create_play.html">New
								Play</a></li>
						<li role="separator" class="divider"></li>
						<li><a
							href="http://localhost:8080/boardgamemanager/login.jsp">Login</a></li>
						<li><a href="#">Logout</a></li>
						<li><a
							href="http://localhost:8080/boardgamemanager/register.jsp">Register</a></li>
					</ul></li>
			</ul>



		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>