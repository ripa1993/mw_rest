<%@ page import="it.polimi.moscowmule.boardgamemanager.game.GameStorage"%>
<%@ page import="it.polimi.moscowmule.boardgamemanager.user.UserStorage"%>
<%@ page import="it.polimi.moscowmule.boardgamemanager.play.PlayStorage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<footer class="footer">
	<div class="container">
		<p class="text-muted">Powered by Moscow Mules. There are <%=UserStorage.instance.getModel().size()%>
		users, <%=GameStorage.instance.getModel().size()%> games and <%=PlayStorage.instance.getModel().size()%>
		plays.</p>
	</div>
</footer>