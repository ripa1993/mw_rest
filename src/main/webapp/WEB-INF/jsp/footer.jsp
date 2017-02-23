<%@ page import="it.polimi.moscowmule.boardgamemanager.game.GameStorage"%>
<%@ page import="it.polimi.moscowmule.boardgamemanager.user.UserStorage" %>
<%@ page import="it.polimi.moscowmule.boardgamemanager.play.PlayStorage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="footer" style="float: center;">
	<div id="credits">
		<h4>--- Powered by Moscow Mules ---</h4>
	</div>
	<div style="float: left; display: block">
		<p>
			There are
			<%=UserStorage.instance.getModel().size()%>
			users
		</p>
	</div>
	<div style="float: left; display: block">
		<p>
			There are
			<%=GameStorage.instance.getModel().size()%>
			games
		</p>
	</div>
	<div style="float: left; display: block">
		<p>
			There are
			<%=PlayStorage.instance.getModel().size()%>
			plays
		</p>
	</div>
	<div style="clear: both;"></div>
</div>