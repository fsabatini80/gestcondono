<!doctype html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../tpl/header.jsp" />

<form:form action="salvaVersamento.htm" commandName="pojo"
	cssClass="ui-widget">
	<form:errors path="*" cssClass="errorblock" element="div" />
	<h2
		class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Dati
		Versamento</h2>
	<table>

		<tr>
			<td>Sottonumero Abuso:</td>
			<td><form:input path="progressivo_abuso" /></td>
			<td><form:errors path="progressivo_abuso"
					cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Codice Versamento:</td>
			<td><form:input path="codiceVersamento" /> <form:hidden
					path="iddatipratica" /></td>
			<td><form:errors path="codiceVersamento"
					cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>CC postale:</td>
			<td><form:input path="ccPostale" /></td>
			<td><form:errors path="ccPostale" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Data versamento:</td>
			<td><form:input path="dataVersamento" id="datepicker" /></td>
			<td><form:errors path="dataVersamento" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Importo euro:</td>
			<td><form:input path="importoEuro" /></td>
			<td><form:errors path="importoEuro" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Importo lire:</td>
			<td><form:input path="importoLire" /></td>
			<td><form:errors path="importoLire" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Causale:</td>
			<td><form:input path="causale" /></td>
			<td><form:errors path="causale" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Ufficio postale:</td>
			<td><form:input path="ufficioPostale" /></td>
			<td><form:errors path="ufficioPostale" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Numero bollettino:</td>
			<td><form:input path="numeroBollettino" /></td>
			<td><form:errors path="numeroBollettino"
					cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Data protocollo:</td>
			<td><form:input path="dataProtocollo" id="datepicker1" /></td>
			<td><form:errors path="dataProtocollo" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Numero protocollo:</td>
			<td><form:input path="numeroProtocollo" /></td>
			<td><form:errors path="numeroProtocollo"
					cssClass="ui-state-error" /></td>
		</tr>
	</table>
	<h2
		class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Dati
		pagatore</h2>
	<table>
		<tr>
			<td>Nome:</td>
			<td><form:input path="nome" /></td>
			<td><form:errors path="nome" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Cognome:</td>
			<td><form:input path="cognome" /></td>
			<td><form:errors path="cognome" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Ragione Sociale:</td>
			<td><form:input path="ragioneSociale" /></td>
			<td><form:errors path="ragioneSociale" cssClass="ui-state-error" /></td>
		</tr>
	</table>
	<form:button value="Salva" name="Salva">Salva</form:button>
	<input type="button"
		class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
		value="Indietro" name="Indietro" onclick="history.go(-1)" />
</form:form>

</body>
</html>