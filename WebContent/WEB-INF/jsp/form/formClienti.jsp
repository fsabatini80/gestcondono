<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="effect1" class="ui-widget-content ui-corner-all">
	<div>
		<form:form action="modificaUtente.htm" commandName="utenti"
			method="POST">
			<table>
				<tr class="ui-widget-content">
					<td>Nome :</td>
					<td><form:input path="nome" /></td>
					<td>Cognome :</td>
					<td><form:input path="cognome" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Data Di Nascita :
					<td><form:input path="cognome" id="datepicker" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Maschio</td>
					<td><form:radiobutton path="ruolo" /></td>
					<td>Femmina</td>
					<td><form:radiobutton path="ruolo" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Persona Fisica</td>
					<td><form:radiobutton path="ruolo" /></td>
					<td>Persona Giuridica</td>
					<td><form:radiobutton path="ruolo" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Comune Di Nascita :</td>
					<td><form:input path="cognome" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Indirizzo :</td>
					<td><form:input path="cognome" /></td>
					<td>Civico :</td>
					<td><form:input path="cognome" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Email :</td>
					<td><form:input path="cognome" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Telefono Cellulare :</td>
					<td><form:input path="cognome" /></td>
					<td>Telefono Fisso :</td>
					<td><form:input path="cognome" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Cod. Fiscale/P.I. :</td>
					<td><form:input path="cognome" /></td>
					<td>Comune Di Residenza :</td>
					<td><form:input path="cognome" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Tipo Documento :</td>
					<td><form:input path="cognome" /></td>
					<td>Rilasciato da :</td>
					<td><form:input path="cognome" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Data Documento :</td>
					<td><form:input path="cognome" id="datepicker2" /></td>
				</tr>
			</table>
			<form:button>Salva</form:button>
			<input type="button"
				class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
				value="Indietro" name="Indietro" onclick="history.go(-1)" />
		</form:form>
	</div>
</div>