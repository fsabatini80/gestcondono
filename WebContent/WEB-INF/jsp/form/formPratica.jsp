<!doctype html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../tpl/header.jsp" />

<div class="ui-tabs-panel ui-widget-content ui-corner-bottom">

	<form:form action="salvaPratica.htm" commandName="datiPraticaPojo"
		cssClass="ui-widget">
		<!-- form:errors path="*" cssClass="ui-state-error" element="div" /> -->
		<h2
			class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Dati
			della Pratica</h2>
		<table>
			<tr>
				<td>Numero pratica *:</td>
				<td><form:input path="numeroPratica" /> <form:hidden
						path="iddatipratica" /></td>
				<td><form:errors path="numeroPratica" cssClass="ui-state-error" /></td>
			</tr>
			<tr>
				<td>Legge condono *:</td>
				<td><form:select path="leggeCondono">
						<form:options items="${leggiList}" itemLabel="leggeNumero"
							itemValue="idleggiCondono" />
					</form:select></td>
				<td><form:errors path="leggeCondono" cssClass="ui-state-error" /></td>
			</tr>
			<tr>
				<td>Numero protocollo *:</td>
				<td><form:input path="numeroProtocollo" /></td>
				<td><form:errors path="numeroProtocollo"
						cssClass="ui-state-error" /></td>
			</tr>
			<tr>
				<td>Data protocollo *:</td>
				<td><form:input path="dataProtocollo" id="datepicker" /></td>
				<td><form:errors path="dataProtocollo"
						cssClass="ui-state-error" /></td>
			</tr>
			<tr>
				<td>Data presentazione domanda *:</td>
				<td><form:input path="dataDomanda" id="datepicker2" /></td>
				<td><form:errors path="dataDomanda" cssClass="ui-state-error" /></td>
			</tr>
			<tr>
				<td>Autodetermina:</td>
				<td><form:input path="autodeterminata" /></td>
				<td><form:errors path="autodeterminata" cssClass="ui-state-error" /></td>
			</tr>
		</table>


		<h2
			class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Richiedente</h2>
		<div class="ui-widget-content ui-corner-all">
			<table>
				<tr class="ui-widget-content">
					<td>Provincia di residenza :</td>
					<td><form:input path="provinciaResidenza" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Comune di Residenza :</td>
					<td><form:input path="comuneResidenza" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Cap :</td>
					<td><form:input path="cap" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Stato estero residenza :</td>
					<td><form:input path="statoEsteroRes" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Comune estero residenza :</td>
					<td><form:input path="comuneEsteroRes" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Cognome :</td>
					<td><form:input path="cognome" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Nome :</td>
					<td><form:input path="nome" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Data di nascita :
					<td><form:input path="dataNascita" id="datepicker1" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Provincia di nasicita</td>
					<td><form:input path="provinciaNascita" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Comune di nascita</td>
					<td><form:input path="comuneNascita" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Stato estero nascita</td>
					<td><form:input path="statoEsteroNas" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Comune estero nascita</td>
					<td><form:input path="comuneEsteroNas" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Ragione sociale</td>
					<td><form:input path="ragioneSociale" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Partita iva</td>
					<td><form:input path="partitaIva" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Codice fiscale :</td>
					<td><form:input path="codiceFiscale" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Indirizzo :</td>
					<td><form:input path="indirizzo" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Email :</td>
					<td><form:input path="email" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Telefono /Cellulare :</td>
					<td><form:input path="telefono" /></td>
				</tr>
			</table>
		</div>
		<form:button value="Salva" name="Salva">Salva</form:button>
		<input type="button"
			class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
			value="Indietro" name="Indietro" onclick="history.go(-1)" />
	</form:form>
</div>
</body>
</html>