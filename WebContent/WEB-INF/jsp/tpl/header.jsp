<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<head>
<link rel="stylesheet" href="css/start/jquery-ui.css" />
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
			active : false,
			collapsible : true,
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
			shortYearCutoff : 100
		});
		$("#datepicker1").datepicker({
			dateFormat : "dd-mm-yy",
			changeYear : true,
			changeMonth : true,
			shortYearCutoff : 100
		});
		$("#datepicker2").datepicker({
			dateFormat : "dd-mm-yy",
			changeYear : true,
			changeMonth : true,
			shortYearCutoff : 100
		});
		$("#datepicker3").datepicker({
			dateFormat : "dd-mm-yy",
			changeYear : true,
			changeMonth : true,
			shortYearCutoff : 100
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
	 */
</script>

<title>Gestione Condoni</title>
</head>
<body>
	<div id="barra" class="ui-widget-header" align="right">

		<h3>
			Benvenuto
			<sec:authentication property="principal.username" />
			<img title="${utenti.nome} ${utenti.cognome}" src="img/32/user.png">
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
			href="<c:url value="pratiche.htm" />"> Pratiche</a> <a
			href="<c:url value="modificaPratica.htm?idpratica=${pratica.iddatipratica}" />"
			title="modifica pratica">Scadenze</a> <a
			href="<c:url value="modificaPratica.htm?idpratica=${pratica.iddatipratica}" />"
			title="modifica pratica">Solleciti</a> <a
			href="<c:url value="modificaPratica.htm?idpratica=${pratica.iddatipratica}" />"
			title="modifica pratica">Stampe</a> <a
			href="<c:url value="modificaPratica.htm?idpratica=${pratica.iddatipratica}" />"
			title="modifica pratica">Cruscotto</a>
	</div>
	<br />