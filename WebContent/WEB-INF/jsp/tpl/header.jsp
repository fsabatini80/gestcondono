<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<head>
<link rel="stylesheet" href="css/start/jquery.ui.theme.css" />
<link rel="stylesheet" href="css/start/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="css/datatables.min.css" />

<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/datatables.min.js"></script>

<script lang="javascript">
	$(document).ready(function() {
		$('#example').dataTable();
		$('#example1').dataTable();
		$('#example3').dataTable();
		$('#example4').dataTable();
		$('#example5').dataTable();
		$('#example6').dataTable();
		$('#example7').dataTable();
		$('#example8').dataTable();
		$('#example9').dataTable();
		$('#example10').dataTable();
	});

	$(function() {
		$("#tabs").tabs();
		$("#accordion").accordion({
			active : false,
			collapsible : false,
			heightStyle : "content"
		});
		$("#accordionDatAbuso").accordion({
			active : true,
			collapsible : false,
			heightStyle : "content"
		});
		$("#selectable1").selectable();
		$("#selectable2").selectable();
		$("#selectable3").selectable();
		$("#selectable4").selectable();
		$("#selectable5").selectable();
		$("#selectable6").selectable();
		$("#selectable7").selectable();
		$("#selectable8").selectable();
		$("#selectable9").selectable();
		$("#selectable10").selectable();
		$("#selectable11").selectable();
		$("#selectable12").selectable();
		$("#selectable13").selectable();
		$("input[type=submit], button, a[href]").button();
		//.click(function(event) {
		//	event.preventDefault();
		//});
		$("#datepicker").datepicker({
			dateFormat : "dd-mm-yy",
			changeYear : true,
			changeMonth : true,
			yearRange : "1900:2026"
		});
		$("#datepicker1").datepicker({
			dateFormat : "dd-mm-yy",
			changeYear : true,
			changeMonth : true,
			yearRange : "1900:2026"
		});
		$("#datepicker2").datepicker({
			dateFormat : "dd-mm-yy",
			changeYear : true,
			changeMonth : true,
			yearRange : "1900:2026"

		});
		$("#datepicker3").datepicker({
			dateFormat : "dd-mm-yy",
			changeYear : true,
			changeMonth : true,
			yearRange : "1900:2026"
		});

		$("#effect").hide();
		$("#effect1").hide();
		$("#effect2").hide();
		$("#effect3").hide();
		$("#effect4").hide();
		$("#effect5").hide();
		$("#effect6").hide();
		$("#effect7").hide();
		$("#effect8").hide();
		$("#effect9").hide();

		$("#dialog").dialog({
			autoOpen : false,
			minHeight : 200
		});
		$("#opener").click(function() {
			$("#dialog").dialog("open");
		});
		$("#converti")
				.click(
						function() {
							document.getElementById('euro').value = Math
									.round(document.getElementById('lire').value * 100 / 1936.27) / 100;
							return false;
						});

	});

	// run the currently selected effect    
	function runEffect(effect) {
		// get effect type from      
		var selectedEffect = "clip";
		// most effect types need no options passed by default      
		var options = {};
		// run the effect      
		$(effect).show(selectedEffect, options, 400, callback);
	}
	function callback() {
		setTimeout(function() {
			$("#effect:visible").removeAttr("style").fadeOut();
		}, 1000000);
	}

	function delUser(nome) {
		var campo = document.getElementById("nome" + nome.id);
		campo.value = nome.id;
		nome.submit();
	}

	function textToUpperCase() {
		var listInput = document.getElementsByTagName("input");
		var listInputLenght = document.getElementsByTagName("input").length;
		for (i = 0; i < listInputLenght; i++) {
			document.getElementsByTagName("input").item(i).value = document
					.getElementsByTagName("input").item(i).value.toUpperCase();
		}
	}
	/**
	Expression	Description
	hasRole([role])	Returns true if the current principal has the specified role.
	hasAnyRole([role1,role2])	Returns true if the current principal has any of the supplied roles (given as a comma-separated list of strings)
	principal	Allows direct access to the principal object representing the current user
	authentication	Allows direct access to the current Authentication object obtained from the SecurityContext
	permitAll	Always evaluates to true
	denyAll	Always evaluates to false
	isAnonymous()	Returns true if the current principal is an anonymous user
	isRememberMe()	Returns true if the current principal is a remember-me user
	isAuthenticated()	Returns true if the user is not anonymous
	isFullyAuthenticated()	Returns true if the user is not an anonymous or a remember-me user
	 **/
</script>

<title>Gestione Condoni</title>
</head>
<body onkeypress="textToUpperCase();" onkeyup="textToUpperCase();">
	<div id="barra" class="ui-widget-header" style="text-align: right">

		<h3>
			<!-- <img title="logo" src="img/logoCondono.png"
				style="position: absolute; left: 5px; top: 5px" alt="logo"
				height="85px"> -->
			Benvenuto
			<sec:authentication property="principal.username" />
			<img align="right" title="${utenti.nome} ${utenti.cognome}"
				src="img/32/user.png">
			<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
				<a href="<c:url value="/j_spring_security_logout"/>">Login</a>
			</sec:authorize>
			<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
				<a href="<c:url value="/j_spring_security_logout"/>">Logout</a>
			</sec:authorize>
		</h3>
	</div>
	<br />
	<div id="barraMenu" class="ui-widget-header">
		<a href="<c:url value="home.htm" />">Home</a> <a
			href="<c:url value="homePratica.htm" />"> Pratiche</a> <a
			href="scadenzeListAll.htm">Scadenze</a> <a
			href="sollecitiListAll.htm">Solleciti</a>
		<sec:authorize access="hasRole('superadmin')">
			<a href="stampe.htm">Stampe</a>
		</sec:authorize>
		<a href="<c:url value="cruscotto.htm" />" title="cruscotto">Cruscotto</a>
		<button id="opener">Lire/Euro</button>
	</div>
	<br />
	<div id="dialog" title="Convertitore lire/euro">
		<form>
			<fieldset style="width: 190px">
				<label for="lire" style="width: 50px">Lire</label> <input
					type="text" name="lire" id="lire"> <label for="euro"
					style="width: 50px">Euro</label> <input type="text" name="euro"
					id="euro">
				<button id="converti" style="vertical-align: middle;">converti</button>
			</fieldset>
		</form>
	</div>