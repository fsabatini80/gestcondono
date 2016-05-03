<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<link rel="stylesheet" href="css/start/jquery-ui.css" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script lang="javascript">
	$(function() {
		$("#tabs").tabs();
		$("#accordion").accordion({
			collapsible : true,
			fillSpace : true
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
		$("#datepicker").datepicker();
		$("#datepicker1").datepicker();

		$("#effect").hide();
		$("#effect1").hide();
	});

	// run the currently selected effect    
	function runEffect(effect) {
		// get effect type from      
		var selectedEffect = "slide";
		// most effect types need no options passed by default      
		var options = {};
		// run the effect      
		$(effect).show(selectedEffect, options, 500, callback);
	}
	function callback() {
		setTimeout(function() {
			$("#effect:visible").removeAttr("style").fadeOut();
		}, 1000000);
	}
	
	function delUser(nome){
		var campo = document.getElementById("nome"+nome.id);
		campo.value = nome.id;
		nome.submit();
	}
	
	function delCommessa(commessa){
		var campo = document.getElementById("nome"+commessa);
		campo.value = commessa;
		document.getElementById(commessa).submit();
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
<style type="text/css">
.festivo {
	background: red;
}

.feriale {
	background: none;
}

.FERIE {
	background: green;
}

.ENTRATA {
	background: #FEFF40;
}

.USCITA {
	background: aqua;
}

#selectable1 .ui-selecting {
	background: #FECA40;
}

#selectable1 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable1 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable1 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable2 .ui-selecting {
	background: #FECA40;
}

#selectable2 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable2 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable2 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable3 .ui-selecting {
	background: #FECA40;
}

#selectable3 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable3 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable3 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable4 .ui-selecting {
	background: #FECA40;
}

#selectable4 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable4 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable4 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable5 .ui-selecting {
	background: #FECA40;
}

#selectable5 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable5 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable5 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable6 .ui-selecting {
	background: #FECA40;
}

#selectable6 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable6 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable6 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable7 .ui-selecting {
	background: #FECA40;
}

#selectable7 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable7 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable7 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable8 .ui-selecting {
	background: #FECA40;
}

#selectable8 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable8 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable8 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable9 .ui-selecting {
	background: #FECA40;
}

#selectable9 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable9 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable9 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable10 .ui-selecting {
	background: #FECA40;
}

#selectable10 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable10 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable10 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable11 .ui-selecting {
	background: #FECA40;
}

#selectable11 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable11 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable11 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable12 .ui-selecting {
	background: #FECA40;
}

#selectable12 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable12 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable12 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#selectable13 .ui-selecting {
	background: #FECA40;
}

#selectable13 .ui-selected {
	background: #F39814;
	color: white;
}

#selectable13 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 70%;
}

#selectable13 li {
	margin: 3px;
	padding: 0.4em;
	font-size: 12px;
	height: 10px;
}

#barra h1 {
	text-align: center;
	margin: 0;
}

#barra h3 {
	text-align: right;
	margin: 0;
}

#accordion1 {
	height: 730px;
}
</style>


<title>Organizer</title>
</head>
<body>
	<div id="barra" class="ui-widget-header">
		<h3>
			Benvenuto ${utenti.nome} ${utenti.cognome} <img
				title="${utenti.nome} ${utenti.cognome}" src="img/32/user.png">
			<br />
			<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
				<a href="<c:url value="/j_spring_security_logout"/>">Login</a>
			</sec:authorize>
			<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
				<a href="<c:url value="/j_spring_security_logout"/>">Logout</a>
			</sec:authorize>
		</h3>
		<h1>ORGANIZER</h1>
	</div>
	<div id="tabs">
		<div>
			<ol>
				<li><a href="#tabs-1">Dati utente</a></li>
				<li><a href="#tabs-2">Anno in corso</a></li>
				<sec:authorize access="hasRole('admin')">
					<li><a href="#tabs-3">Amministrazione attività</a></li>
					<li><a href="#tabs-4">Gestione utenti</a></li>
				</sec:authorize>
			</ol>
		</div>
		<div id="tabs-1">
			<div>
				<form:form action="modificaUtente.htm" commandName="utenti"
					method="POST">
					<table id="selectable1">
						<tr class="ui-widget-content">
							<td>User Name :</td>
							<td><form:input path="user" /></td>
						</tr>
						<tr class="ui-widget-content">
							<td>Password :</td>
							<td><form:password path="password" /></td>
						</tr>
						<tr class="ui-widget-content">
							<td>Nome :</td>
							<td><form:input path="nome" /></td>
						</tr>
						<tr class="ui-widget-content">
							<td>Cognome :</td>
							<td><form:input path="cognome" /></td>
						</tr>
						<tr class="ui-widget-content">
							<td>Email :
							<td><form:input path="email" /></td>
						</tr>
						<tr class="ui-widget-content">
							<td>Ruolo :</td>
							<td><form:input path="ruolo" /></td>
						</tr>
					</table>
					<form:button>Salva modifica</form:button>
				</form:form>

				<h3 class="ui-widget-header">Inserisci le tue preferenze</h3>
				<form:form action="insertPref.htm" commandName="preferenza">
					<p class="ui-widget-content">
						preferenza :
						<form:select path="legenda.idLegenda" cssClass="ui-widget-content">
							<form:option value=""></form:option>
							<c:forEach var="legT" items="${legenda}">
								<c:if test="${legT.tipo == 'TURNO'}">
									<form:option value="${legT.idLegenda}"
										label="${legT.descrizione}"></form:option>
								</c:if>
							</c:forEach>
						</form:select>
						<br /> <br />
						<form:button value="inserisciP">Inserisci</form:button>
					</p>
				</form:form>

				<h3 class="ui-widget-header">Inserisci assenze</h3>
				<form:form action="insertAss.htm" commandName="turno">
					<p class="ui-widget-content">
						Giorno :
						<form:input path="dataTurno" id="datepicker" />
						Tipo assenza :
						<form:select path="legenda.idLegenda" cssClass="ui-widget-content">
							<form:option value=""></form:option>
							<c:forEach var="legA" items="${legenda}">
								<c:if test="${legA.tipo == 'ASSENZA'}">
									<form:option value="${legA.idLegenda}"
										label="${legA.descrizione}"></form:option>
								</c:if>
							</c:forEach>
						</form:select>
						<br /> <br />
						<form:button value="inserisciA">Inserisci</form:button>
					</p>
				</form:form>

				<h3 class="ui-widget-header">Inserisci codice commessa</h3>
				<form:form action="insertComm.htm" commandName="turno">
					<p class="ui-widget-content">
						Giorno :
						<form:input path="dataTurno" id="datepicker1" />
						codice commessa :
						<form:select path="legenda.idLegenda" cssClass="ui-widget-content">
							<form:option value=""></form:option>
							<c:forEach var="legC" items="${legenda}">
								<c:if test="${legC.tipo == 'COMMESSA'}">
									<form:option value="${legC.idLegenda}"
										label="${legC.descrizione}"></form:option>
								</c:if>
							</c:forEach>
						</form:select>
						<br /> <br />
						<form:button value="inserisciC">Inserisci</form:button>
					</p>
				</form:form>
			</div>
		</div>
		<div id="tabs-2">
			<div id="accordion">
				<h3>Gennaio</h3>
				<div id="accordion1">
					<form:form action="gestisciMese.htm" commandName="gen">
						<ol id="selectable2">
							<c:forEach var="giorno" items="${gen}">
								<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
									${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
										var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
								</li>
							</c:forEach>
						</ol>
						<form:button value="Conferma">Conferma</form:button>
						<form:button value="Cambio Turno">Cambio Turno</form:button>
						<a href="output.xls">Esporta file Excel</a>
					</form:form>
				</div>
				<h3>Febbraio</h3>
				<div>
					<ul id="selectable3">
						<c:forEach var="giorno" items="${feb}">
							<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
								${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
									var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
							</li>
						</c:forEach>
					</ul>
					<button>Conferma</button>
					<button>Cambio turno</button>
					<button>Esporta file Excel</button>
				</div>
				<h3>Marzo</h3>
				<div>
					<ul id="selectable4">
						<c:forEach var="giorno" items="${mar}">
							<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
								${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
									var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
							</li>
						</c:forEach>
					</ul>
					<button>Conferma</button>
					<button>Cambio turno</button>
					<button>Esporta file Excel</button>
				</div>
				<h3>Aprile</h3>
				<div>
					<ul id="selectable5">
						<c:forEach var="giorno" items="${apr}">
							<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
								${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
									var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
							</li>
						</c:forEach>
					</ul>
					<br />
					<button>Conferma</button>
					<button>Cambio turno</button>
					<button>Esporta file Excel</button>
				</div>
				<h3>Maggio</h3>
				<div>
					<ul id="selectable6">
						<c:forEach var="giorno" items="${mag}">
							<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
								${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
									var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
							</li>
						</c:forEach>
					</ul>
					<br />
					<button>Conferma</button>
					<button>Cambio turno</button>
					<button>Esporta file Excel</button>
				</div>
				<h3>Giugno</h3>
				<div>
					<ul id="selectable7">
						<c:forEach var="giorno" items="${giu}">
							<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
								${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
									var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
							</li>
						</c:forEach>
					</ul>
					<br />
					<button>Conferma</button>
					<button>Cambio turno</button>
					<button>Esporta file Excel</button>

				</div>
				<h3>Luglio</h3>
				<div>
					<ul id="selectable8">
						<c:forEach var="giorno" items="${lug}">
							<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
								${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
									var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
							</li>
						</c:forEach>
					</ul>
					<br />
					<button>Conferma</button>
					<button>Cambio turno</button>
					<button>Esporta file Excel</button>

				</div>
				<h3>Agosto</h3>
				<div>
					<ul id="selectable9">
						<c:forEach var="giorno" items="${ago}">
							<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
								${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
									var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
							</li>
						</c:forEach>
					</ul>
					<br />
					<button>Conferma</button>
					<button>Cambio turno</button>
					<button>Esporta file Excel</button>

				</div>
				<h3>Settembre</h3>
				<div>
					<ul id="selectable10">
						<c:forEach var="giorno" items="${set}">
							<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
								${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
									var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
							</li>
						</c:forEach>
					</ul>
					<br />
					<button>Conferma</button>
					<button>Cambio turno</button>
					<button>Esporta file Excel</button>

				</div>
				<h3>Ottobre</h3>
				<div>
					<ol id="selectable11">
						<c:forEach var="giorno" items="${ott}">
							<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
								${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
									var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
							</li>
						</c:forEach>
					</ol>
					<br />
					<button>Conferma</button>
					<button>Cambio turno</button>
					<button>Esporta file Excel</button>
				</div>
				<h3>Novembre</h3>
				<div>
					<ul id="selectable12">
						<c:forEach var="giorno" items="${nov}">
							<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
								${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
									var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
							</li>
						</c:forEach>
					</ul>
					<br />
					<button>Conferma</button>
					<button>Cambio turno</button>
					<button>Esporta file Excel</button>
				</div>
				<h3>Dicembre</h3>
				<div>
					<ul id="selectable13">
						<c:forEach var="giorno" items="${dic}">
							<li class="${giorno.tipo}">${giorno.giornoDellaSettimana}
								${giorno.giornoDelMese} ${giorno.nome} ${giorno.cognome} <c:forEach
									var="turno" items="${giorno.turni}"> ${turno} </c:forEach>
							</li>
						</c:forEach>
					</ul>
					<br />
					<button>Conferma</button>
					<button>Cambio turno</button>
					<button>Esporta file Excel</button>

				</div>
			</div>
		</div>
		<div id="tabs-3">
			<img title="aggiungi una nuova commessa" src="img/contract.png"
				onclick="runEffect('#effect')">
			<div>
				<div id="effect" class="ui-widget-content ui-corner-all">
					<h3 class="ui-widget-header ui-corner-all">Inserisci Commessa</h3>
					<form:form action="addCommessa.htm" commandName="legendaNew"
						method="POST">
						<table>
							<tr class="ui-widget-content">
								<td>Codice :</td>
								<td><form:input path="carattere" /></td>
							</tr>
							<tr class="ui-widget-content">
								<td>Descrizione :</td>
								<td><form:input path="descrizione" /></td>
							</tr>
						</table>
						<form:button>Inserisci</form:button>
					</form:form>
				</div>
			</div>
			<h3 class="ui-widget-header">Codici Commessa</h3>
			<table>
				<thead>
					<tr>
						<td>Descrizione Commessa</td>
						<td>Codice Commessa</td>
						<td></td>
						<td></td>
					</tr>
				</thead>
				<c:forEach var="legC" items="${legenda}">
					<form:form action="removeCommessa.htm" commandName="legendaDel"
						id="${legC.idLegenda}">
						<c:if test="${legC.tipo == 'COMMESSA'}">
							<tr>
								<td>${legC.descrizione}</td>
								<td>${legC.carattere}</td>
								<td><form:hidden path="idLegenda"
										id="nome${legC.idLegenda}" /></td>
								<td><img title="rimuovi commessa"
									src="img/32/delete.png"
									onclick="delCommessa(${legC.idLegenda})"></td>
							</tr>
						</c:if>
					</form:form>
				</c:forEach>
			</table>
		</div>
		<div id="tabs-4">
			<img alt="aggiungi un nuovo utente" title="aggiungi un nuovo utente"
				src="img/user_add.png" onclick="runEffect('#effect1')">

			<div>
				<div id="effect1" class="ui-widget-content ui-corner-all">
					<h3 class="ui-widget-header ui-corner-all">Inserisci utente</h3>
					<form:form action="addUtente.htm" commandName="utenteNew"
						method="POST">
						<table>
							<tr class="ui-widget-content">
								<td>User Name :</td>
								<td><form:input path="user" /></td>
							</tr>
							<tr class="ui-widget-content">
								<td>Password :</td>
								<td><form:password path="password" /></td>
							</tr>
							<tr class="ui-widget-content">
								<td>Nome :</td>
								<td><form:input path="nome" /></td>
							</tr>
							<tr class="ui-widget-content">
								<td>Cognome :</td>
								<td><form:input path="cognome" /></td>
							</tr>
							<tr class="ui-widget-content">
								<td>Email :
								<td><form:input path="email" /></td>
							</tr>
							<tr class="ui-widget-content">
								<td>Ruolo :</td>
								<td><form:input path="ruolo" /></td>
							</tr>
						</table>
						<form:button>Inserisci</form:button>
					</form:form>
				</div>
			</div>
			<h3 class="ui-widget-header">Riepilogo Utenti</h3>
			<table>
				<thead>
					<tr>
						<td>NOME</td>
						<td>COGNOME</td>
						<td>RUOLO</td>
						<td>EMAIL</td>
						<td></td>
						<td></td>
					</tr>
				</thead>
				<c:forEach var="utente" items="${utentiAll}">
					<form:form action="removeUtente.htm" commandName="utenteDel"
						id="${utente.nome}">
						<tr>
							<td>${utente.nome}</td>
							<td>${utente.cognome}</td>
							<td>${utente.ruolo}</td>
							<td>${utente.email}</td>
							<td><form:hidden path="user" id="nome${utente.user}" /></td>
							<td><img title="rimuovi utente" src="img/32/user_delete.png"
								onclick="delUser(${utente.nome})"></td>
						</tr>
					</form:form>
				</c:forEach>
			</table>
		</div>

	</div>

</body>
</html>