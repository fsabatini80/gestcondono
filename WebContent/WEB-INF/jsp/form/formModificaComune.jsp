<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel="stylesheet" href="css/start/jquery-ui.css" />
</head>
<body>
	<div>
		<form:form action="modificaComuneForm.htm" commandName="comuneMod"
			method="POST">
			<table>
				<tr>
					<td>Comune :</td>
					<td><form:hidden path="idcomuni" />
						<form:input path="descrizione" /></td>
				</tr>
				<tr>
					<td>Provincia :
					<td><form:input path="provincia" /></td>
				</tr>
				<tr>
					<td>Regione :</td>
					<td><form:input path="regione" /></td>
				</tr>
			</table>
			<form:button>Conferma</form:button>
			<input type="button"
				class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
				value="Indietro" name="Indietro" onclick="history.go(-1)" />
		</form:form>
	</div>
</body>
</html>