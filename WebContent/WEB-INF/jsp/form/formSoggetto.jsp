<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="../tpl/header.jsp" />
<h2
	class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Modifica
	Soggetto</h2>
<form:form action="salvaSoggetto.htm" commandName="soggettoNew"
	method="POST">
	<table>
		<tr class="ui-widget-content">
			<td>Tipologia soggetto :</td>
			<td><form:select path="idSoggetto.idsoggettiAbuso"
					items="${tiposoggetti}" itemLabel="descrizioneSoggetto"
					itemValue="idsoggettiAbuso"></form:select> <form:hidden
					path="idrelSoggettoAbuso" /></td>
		</tr>
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
			<td>Provincia di nascita</td>
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
		<tr class="ui-widget-content">
			<td></td>
			<td><form:button value="Modifica" name="Modifica">Modifica</form:button><input
				type="button"
				class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
				value="Indietro" name="Indietro" onclick="history.go(-1)" /></td>
		</tr>
	</table>
</form:form>
