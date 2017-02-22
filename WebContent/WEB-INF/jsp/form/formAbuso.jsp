<!doctype html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../tpl/header.jsp" />

<div>
	<form:form action="salvaAbuso.htm" commandName="datiAbusoPojo"
		cssClass="ui-widget">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<div id="accordionDatAbuso">
			<h3>Dati abuso</h3>
			<div id="accordionDatAbuso1">
				<table>
					<tr>
						<td>Descrizione:</td>
						<td><form:textarea path="descrizione" rows="5" cols="40" />
							<form:hidden path="iddatiabuso" /> <form:hidden
								path="progressivo" /></td>
					</tr>
					<tr>
						<td>Destinazione d'uso*:</td>
						<td><form:select path="destinazioneUso">
								<form:options items="${tipologiaDestinazioneUsos}"
									itemLabel="descrizioneTipologia"
									itemValue="idtipologiaDestinazioneUso" />
							</form:select></td>
						<td><form:errors path="destinazioneUso"
								cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Tipologia*:</td>
						<td><form:select path="tipologiaAbuso">
								<form:options items="${tipologiaAbusos}"
									itemLabel="descrizioneBreve" itemValue="idtipologiaAbuso" />
							</form:select></td>
						<td><form:errors path="tipologiaAbuso"
								cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Epoca abuso*:</td>
						<td><form:radiobuttons items="${epocaAbusos}"
								itemValue="idepocaAbuso" path="epocaAbuso" delimiter="<br />" /></td>
						<td><form:errors path="epocaAbuso" cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Data ultimazione lavori*:</td>
						<td><form:input path="dataUltimazioneLavori" id="datepicker3" /></td>
						<td><form:errors path="dataUltimazioneLavori"
								cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Tipo opera:</td>
						<td><form:select path="tipoOpera">
								<form:options items="${tipoOperas}" itemLabel="descrizione"
									itemValue="idtipoOpera" />
							</form:select></td>
						<td><form:errors path="tipoOpera" cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Autodetermina Oblazione:</td>
						<td><form:input path="autodeterminata" /></td>
						<td><form:errors path="autodeterminata"
								cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Autodetermina Oneri:</td>
						<td><form:input path="autodeterminataOneri" /></td>
						<td><form:errors path="autodeterminataOneri"
								cssClass="ui-state-error" /></td>
					</tr>
				</table>
			</div>
			<h2>Dimensioni</h2>
			<div id="accordionDatAbuso2">
				<table>
					<tr>
						<td>Superficie utile mq:</td>
						<td><form:input path="superficeUtile" /></td>
						<td><form:errors path="superficeUtile"
								cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Volume utile vuoto per pieno mc:</td>
						<td><form:input path="volumeUtile" /></td>
						<td><form:errors path="volumeUtile" cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Non residenziale/accessori mq:</td>
						<td><form:input path="nonresidenziale" /></td>
						<td><form:errors path="nonresidenziale"
								cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Non redenziale/accessori vuoto per pieno mc:</td>
						<td><form:input path="nonresidenzialeVuoto" /></td>
						<td><form:errors path="nonresidenzialeVuoto"
								cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Superficie totale insediamento abuso mq:</td>
						<td><form:input path="superficeTotale" /></td>
						<td><form:errors path="superficeTotale"
								cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Volume totale insediamento abuso mc:</td>
						<td><form:input path="volumeTotale" /></td>
						<td><form:errors path="volumeTotale"
								cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Volume direzionale mc:</td>
						<td><form:input path="volumeDirezionale" /></td>
						<td><form:errors path="volumeDirezionale"
								cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Numero Addetti:</td>
						<td><form:input path="numeroAddetti" /></td>
						<td><form:errors path="numeroAddetti"
								cssClass="ui-state-error" /></td>
					</tr>
				</table>
			</div>
			<h2>Esenzioni/riduzioni</h2>
			<div id="accordionDatAbuso3">
				<table>
					<tr>
						<td>Esenzioni pagamenti:</td>
						<td><form:select path="esenzioniPagamenti">
								<form:options items="${esenzioniPagamentis}"
									itemLabel="descrizione" itemValue="idtipoEsenzioni" />
							</form:select></td>
						<td><form:errors path="esenzioniPagamenti"
								cssClass="ui-state-error" /></td>
					</tr>
					<!--<tr>
						<td>Riduzioni:</td>
						<td><form:select path="riduzioni">
								<form:options items="${riduzionis}" itemLabel="descrizione"
									itemValue="idtipoRiduzione" />
							</form:select></td>
						<td><form:errors path="riduzioni" cssClass="ui-state-error" /></td>
					</tr> -->
					<tr>
						<td>Prima casa :</td>
						<td><form:checkbox path="localizzazione.isprimaCasa" /></td>
					</tr>
					<tr>
						<td>Iscrizione alla camera di commercio :</td>
						<td><form:checkbox path="localizzazione.iscrizioneCamera" /></td>
					</tr>
					<tr>
						<td>Immobile soggetto a vincoli di tutela :</td>
						<td><form:checkbox path="localizzazione.vincoliTutela" /></td>
					</tr>
					<tr>
						<td>Abitazione di lusso :</td>
						<td><form:checkbox path="localizzazione.abitazioneLusso" /></td>
					</tr>
					<tr>
						<td>Immobile con convenzione urbanistica :</td>
						<td><form:checkbox
								path="localizzazione.convenzione_urbanistica" /></td>
					</tr>
					<tr>
						<td>Tipo reddito:</td>
						<td><form:select path="tipoReddito">
								<form:options items="${tipoRedditos}" itemLabel="descrizione"
									itemValue="idtipologiaRiduzioneReddito" />
							</form:select></td>
						<td><form:errors path="tipoReddito" cssClass="ui-state-error" /></td>
					</tr>
					<tr>
						<td>Reddito in euro:</td>
						<td><form:input path="reddito" /></td>
						<td><form:errors path="reddito" cssClass="ui-state-error" /></td>
					</tr>
				</table>
			</div>
			<h2>Localizzazione</h2>
			<div id="accordionDatAbuso5">
				<table>
					<tr>
						<td>Provincia :</td>
						<td><form:input path="localizzazione.provincia" value="RM"/><form:hidden
								path="localizzazione.iddatiLocalizzazione" /></td>
					</tr>
					<tr>
						<td>Comune :</td>
						<td><form:input path="localizzazione.comune" value="PALOMBARA SABINA" /></td>
					</tr>
					<tr>
						<td>Cap :
						<td><form:input path="localizzazione.cap" value="00018" /></td>
					</tr>
					<tr>
						<td>Indirizzo :</td>
						<td><form:input path="localizzazione.indirizzo" /></td>
					</tr>
					<tr>
						<td>Zona urbanizzazione :</td>
						<td><form:select path="localizzazione.zonaUrbanizzazione">
								<form:options items="${oneriConcessoris}" />
							</form:select></td>
						<td><form:errors path="localizzazione.zonaUrbanizzazione"
								cssClass="ui-state-error" /></td>
					</tr>
				</table>
			</div>
		</div>
		<form:button value="Salva" name="Salva">Salva</form:button>
		<input type="button"
			class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
			value="Indietro" name="Indietro" onclick="history.go(-1)" />
	</form:form>
</div>
<div>
	<form:form action="calcolaOblazione.htm" commandName="datiAbusoPojo">
		<form:hidden path="iddatiabuso" />
		<form:hidden path="progressivo" />
		<form:hidden path="destinazioneUso" />
		<form:hidden path="tipologiaAbuso" />
		<form:hidden path="epocaAbuso" />

		<form:input path="oblazioneCalcolata" />
		<form:button value="calcola" name="calcola">Calcola Oblazione</form:button>
	</form:form>
</div>

</body>
</html>