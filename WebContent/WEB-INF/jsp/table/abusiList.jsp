<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="../tpl/header.jsp" />
<h2
	class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Lista
	Abusi</h2>
<div>
	<a href="<c:url value="nuovoAbuso.htm" />" title="crea un nuovo abuso">
		<img title="crea un nuovo abuso" src="img/add.png">
	</a>
</div>
<div class="ui-state-error ui-widget">${error}</div>
<div>
	<table id="example10" class="display">
		<thead>
			<tr>
				<th>N. Prog.</th>
				<th>Descrizione</th>
				<th>Destinazione Uso</th>
				<th>Epoca Abuso</th>
				<th>Tipo Opera</th>
				<th>mod.</th>
				<th>vis. alloggi</th>
				<th>vis. doc.</th>
				<th>vis. sogg.</th>
				<th>stampa lettera</th>
				<th>vis. solleciti</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:forEach var="abuso" items="${abusi}">
				<tr>
					<td>${abuso.progressivo}</td>
					<td>${abuso.descrizione}</td>
					<td>${abuso.destinazioneUso.descrizioneTipologia}</td>
					<td>da ${abuso.epocaAbuso.epocaDa} a
						${abuso.epocaAbuso.epocaA}</td>
					<td>${abuso.tipoOpera.descrizione}</td>
					<td><a
						href="<c:url value="modificaAbuso.htm?idabuso=${abuso.iddatiabuso}" />"
						title="modifica abuso"><img src="img/24/edit.png"
							title="modifica abuso"></a></td>
					<td><a
						href="<c:url value="alloggi.htm?idabuso=${abuso.iddatiabuso}" />"
						title="visualizza alloggi"><img src="img/24/find.png"
							title="visualizza alloggi"></a></td>
					<td><a
						href="<c:url value="documenti.htm?idabuso=${abuso.iddatiabuso}" />"
						title="visualizza documenti"><img
							src="img/24/document_find.png" title="visualizza documenti"></a></td>
					<td><a
						href="<c:url value="soggetti.htm?idabuso=${abuso.iddatiabuso}" />"
						title="visualizza soggetti abuso"><img src="img/24/users3.png"
							title="visualizza soggetti abuso"></a></td>
					<td><a
						href="<c:url value="stampaLettera.htm?idpratica=${abuso.datiPratica.iddatipratica}&idabuso=${abuso.iddatiabuso}&progressivo=${abuso.progressivo}" />"
						title="stampa lettera"> <img title="stampa lettera"
							src="img/24/printer.png">
					</a></td>
					<td><a id="${abuso.iddatiabuso}"
						href="<c:url value="solleciti.htm?idpratica=${abuso.datiPratica.iddatipratica}&idabuso=${abuso.iddatiabuso}" />"
						title="visualizza solleciti"><img
							src="img/24/hand_yellow_card.png" title="visualizza solleciti"></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div>
	<a href="<c:url value="pratica.htm?idpratica=${idpratica}" />"
		title="INDIETRO"> Indietro </a>
</div>