<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<!-- This file has been downloaded from Bootsnipp.com. Enjoy! -->
<title>TeleAsistencia</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<!-- hoja de estilo -->

<link rel="stylesheet" type="text/css" href="/formato.css"
	media="screen" />

<!-- favicon -->
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>


<script type="text/javascript">
	var position;
	var lat;
	var lon;

	function initCoords() {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(showPosition,
					locationError);
		} else {
			console.log("Geolocation is not supported by this browser.");
		}
	}

	function locationError() {
		console.log("MAL");
	}

	function showPosition(position) {
		this.lat = position.coords.latitude;
		this.lon = position.coords.longitude;

		console.log(position.coords);

		$('#lat').val(this.lat);
		$('#lon').val(this.lon);
	}
</script>


<div class="container" style="padding-top: 1em;">
	<nav role="navigation" class="navbar navbar-default navbar-fixed-top ">
	<div class="navbar-header">

		<h2>Aplicación android</h2>


	</div>
</div>
</head>
<body onLoad="initCoords()">
	<br>
	<br>
	<form class="con_boton" action="/rojoAlerta" method="post"
		accept-charset="utf-8">

				<br><strong>Usuario:</strong>
				<br><input name="usr" id="usr" type="radio" value="1" checked>   Yelmo Garcia,Juan Carlos 
				<br><input name="usr" id="usr" type="radio" value="2">   Dueñas Lopez, Juan Carlos  
				<br><input name="usr" id="usr" type="radio" value="3">   del Alamo Ramiro, Jose Maria 

		<br><input type="hidden" value="-1" id="alarma" name="alarma"> 
		<input type="hidden" id="lat" name="lat"> 
		<input type="hidden" id="lon" name="lon"> 
		



		
		<input type=image src='boton.png' border='0' onClick="" type="submit">
	</form>
	<form class="con_boton" action="/nuevaAlerta" method="post"
		accept-charset="utf-8">
		<input type="button" value="modo Prueba"
			onClick=" window.location.href='boton.html' ">
	</form>

</body>
</html>