<!doctype html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../tpl/header.jsp" />

<form:form action="salvaSollecito.htm" commandName="datiSollecitoPojo"
	cssClass="ui-widget">
	<form:errors path="*" cssClass="errorblock" element="div" />
	<h2
		class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Dati
		Sollecito</h2>
	<table>

		<tr>
			<td>Sottonumero Abuso:</td>
			<td><form:select path="progressivoAbuso">
					<form:options items="${progressivi}" />
				</form:select></td>
			<td><form:errors path="progressivoAbuso"
					cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Protocollo primo sollecito:</td>
			<td><form:input path="protocolloSoll1" /> <form:hidden
					path="iddatiPratica" /> <form:hidden path="iddatiSollecito" /></td>
			<td><form:errors path="protocolloSoll1"
					cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Data invio primo sollecito:</td>
			<td><form:input path="dataInvioSoll1" id="datepicker"/></td>
			<td><form:errors path="dataInvioSoll1" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Protocollo secondo sollecito:</td>
			<td><form:input path="protocolloSoll2" /></td>
			<td><form:errors path="protocolloSoll2"
					cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Data invio secondo sollecito:</td>
			<td><form:input path="dataInvioSoll2" id="datepicker1" /></td>
			<td><form:errors path="dataInvioSoll2" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Pagato:</td>
			<td><form:checkbox path="pagato" /></td>
			<td><form:errors path="pagato" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Data pagamento:</td>
			<td><form:input path="dataPagamento" id="datepicker3" /></td>
			<td><form:errors path="dataPagamento" cssClass="ui-state-error" /></td>
		</tr>
		<tr>
			<td>Tecnico incaricato:</td>
			<td><form:input path="tecnicoIncaricato" /></td>
			<td><form:errors path="tecnicoIncaricato" cssClass="ui-state-error" /></td>
		</tr>
	</table>
	<form:button value="Salva" name="Salva">Salva</form:button>
	<input type="button"
		class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
		value="Indietro" name="Indietro" onclick="history.go(-1)" />
</form:form>

</body>
</html>