<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Game: ${it.game.id }</title>
</head>
<body>
	<div>
	<img src="${it.game.coverArt}" alt="Cover art" height="200" />
	<ul>
		<li>Name: ${it.game.name}
		<li>Minimum number of players: ${it.game.minPlayers}
		<li>Maximum number of players: ${it.game.maxPlayers}
		<li>Average play time: ${it.game.playTime}
		<li>Minimum age: ${it.game.minAge}
		<li>Difficulty: ${it.game.difficulty}
		<li>Designer: ${it.game.designer}
		<li>Artist: ${it.game.artist}
		<li>Publisher: ${it.game.publisher}
	</ul>
	</div>
</body>
</html>