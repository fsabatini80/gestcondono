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
						<td><form:textarea path="descrizione" rows="5" cols="40" /><form:hidden path="iddatiabuso" /></td>
					</tr>
					<tr>
						<td>Destinazione d'uso*:</td>
						<td><form:select path="destinazioneUso">
								<form:options items="${tipologiaDestinazioneUsos}"
									itemLabel="descrizioneTipologia"
									itemValue="idtipologiaDestinazioneUso" />
							</form:select></td>
					</tr>
					<tr>
						<td>Tipologia*:</td>
						<td><form:select path="epocaAbuso">
								<form:options items="${tipologiaAbusos}" itemLabel="descrizione"
									itemValue="idtipologiaAbuso" />
							</form:select></td>
					</tr>
					<tr>
						<td>Epoca abuso*:</td>
						<td><form:select path="epocaAbuso">
								<form:options items="${epocaAbusos}" itemLabel="epocaA"
									itemValue="idepocaAbuso" />
							</form:select></td>
					</tr>
					<tr>
						<td>Data ultimazione lavori*:</td>
						<td><form:input path="dataUltimazioneLavori" id="datepicker3" /></td>
					</tr>
					<tr>
						<td>Tipo opera:</td>
						<td><form:select path="tipoOpera">
								<form:options items="${tipoOperas}" itemLabel="descrizione"
									itemValue="idtipoOpera" />
							</form:select></td>
					</tr>
				</table>
			</div>
			<h2>Dimensioni</h2>
			<div id="accordionDatAbuso2">
				<table>
					<tr>
						<td>Suoperficie utile mq:</td>
						<td><form:input path="superficeUtile" /></td>
					</tr>
					<tr>
						<td>Volume utile vuoto per pieno mc:</td>
						<td><form:input path="volumeUtile" /></td>
					</tr>
					<tr>
						<td>Non residenziale/accessori mq:</td>
						<td><form:input path="nonresidenziale" /></td>
					</tr>
					<tr>
						<td>Non redenziale/accessori vuoto per pieno mc:</td>
						<td><form:input path="nonresidenzialeVuoto" /></td>
					</tr>
					<tr>
						<td>Superficie totale insediamento abuso mq:</td>
						<td><form:input path="superficeTotale" /></td>
					</tr>
					<tr>
						<td>Volume totale insediamento abuso mc:</td>
						<td><form:input path="volumeTotale" /></td>
					</tr>
					<tr>
						<td>Volume direzionale mc:</td>
						<td><form:input path="volumeDirezionale" /></td>
					</tr>
					<tr>
						<td>Numero Addetti:</td>
						<td><form:input path="numeroAddetti" /></td>
					</tr>
				</table>
			</div>
			<h2>Esenzioni/riduzioni</h2>
			<div id="accordionDatAbuso3">
				<table>
					<tr>
						<td>Esenzioni pagamenti:</td>
						<td><form:input path="esenzioniPagamenti" /></td>
					</tr>
					<tr>
						<td>Riduzioni:</td>
						<td><form:input path="riduzioni" /></td>
					</tr>
					<tr>
						<td>Tipo reddito:</td>
						<td><form:input path="tipoReddito" /></td>
					</tr>
					<tr>
						<td>Reddito:</td>
						<td><form:input path="reddito" /></td>
					</tr>
				</table>
			</div>
			<h2>Soggetto abuso</h2>
			<div id="accordionDatAbuso4">
				<table>
					<tr>
						<td><form:checkbox path="soggettiAbuso" value="PROPRIETARIO" />PROPRIETARIO</td>
						<td><form:checkbox path="soggettiAbuso" value="RICHIEDENTE" />RICHIEDENTE</td>
						<td><form:checkbox path="soggettiAbuso" value="DELEGATO" />DELEGATO</td>
						<td><form:checkbox path="soggettiAbuso" value="ALTRO" />ALTRO</td>
						<td><form:checkbox path="soggettiAbuso" value="DECEDUTO" />DECEDUTO</td>
						<td><form:checkbox path="soggettiAbuso"
								value="COMPROPRIETARIO" />COMPROPRIETARIO</td>
					</tr>
				</table>
			</div>
			<h2>Localizzazione</h2>
			<div id="accordionDatAbuso5">
				<table>
					<tr>
						<td>Provincia :</td>
						<td><form:input path="localizzazione.provincia" /></td>
					</tr>
					<tr>
						<td>Comune :</td>
						<td><form:input path="localizzazione.comune" /></td>
					</tr>
					<tr>
						<td>Cap :
						<td><form:input path="localizzazione.cap" /></td>
					</tr>
					<tr>
						<td>Indirizzo :</td>
						<td><form:input path="localizzazione.indirizzo" /></td>
					</tr>
					<tr>
						<td>Zona urbanizzazione :</td>
						<td><form:input path="localizzazione.zonaUrbanizzazione" /></td>
					</tr>
					<tr>
						<td>Immobile soggetto a vincoli di tutela :</td>
						<td><form:input path="localizzazione.vincoliTutela" /></td>
					</tr>
				</table>
			</div>
		</div>
		<form:button value="Salva" name="Salva">Salva</form:button>
	</form:form>
</div>

</body>
</html>