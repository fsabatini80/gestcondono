<!doctype html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../tpl/header.jsp" />

<form:form action="salvaVersamento.htm" commandName="pojo"
	cssClass="ui-widget">
	<form:errors path="*" cssClass="errorblock" element="div" />
	<h3>Dati Versamento</h3>
	<table>
		<tr>
			<td>Codice Versamento:</td>
			<td><form:input path="codiceVersamento" /></td>
		</tr>
		<tr>
			<td>CC postale:</td>
			<td><form:input path="ccPostale" /></td>
		</tr>
		<tr>
			<td>Data versamento:</td>
			<td><form:input path="dataVersamento" id="datepicker" /></td>
		</tr>
		<tr>
			<td>Importo euro:</td>
			<td><form:input path="importoEuro" /></td>
		</tr>
		<tr>
			<td>Ufficio postale:</td>
			<td><form:input path="ufficioPostale" /></td>
		</tr>
		<tr>
			<td>Numero bollettino:</td>
			<td><form:input path="numeroBollettino" /></td>
		</tr>
		<tr>
			<td>Data protocollo:</td>
			<td><form:input path="dataProtocollo" /></td>
		</tr>
		<tr>
			<td>Numero protocollo:</td>
			<td><form:input path="numeroProtocollo" /></td>
		</tr>
	</table>
	<h3>Dati pagatore</h3>
	<table>
		<tr>
			<td>Nome:</td>
			<td><form:input path="nome" /></td>
		</tr>
		<tr>
			<td>Cognome:</td>
			<td><form:input path="cognome" /></td>
		</tr>
		<tr>
			<td>Ragione Sociale:</td>
			<td><form:input path="ragioneSociale" /></td>
		</tr>
	</table>
	<form:button value="Salva" name="Salva">Salva</form:button>
</form:form>

</body>
</html>