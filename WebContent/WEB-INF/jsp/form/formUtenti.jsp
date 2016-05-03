<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="effect3" class="ui-widget-content ui-corner-all">
	<div>
		<form:form action="addUtente.htm" commandName="utenteNew"
			method="POST">
			<table>
				<tr class="ui-widget-content">
					<td>Username :</td>
					<td><form:input path="username" required="required" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Password :</td>
					<td><form:input path="password" required="required" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Cognome :
					<td><form:input path="cognome" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Nome</td>
					<td><form:input path="nome" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Tipo Utenza :</td>
					<td><form:input path="ruolo" required="required" /></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><form:button>Inserisci</form:button></td>
				</tr>
			</table>

		</form:form>
	</div>
</div>