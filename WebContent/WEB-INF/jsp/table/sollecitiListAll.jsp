<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="../tpl/header.jsp" />
<h2
	class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Lista
	Solleciti Inviati</h2>
<div></div>
<div>
	<table id="example10" class="display">
		<thead>
			<tr>
				<th>Numero Protocollo</th>
				<th>Sot</th>
				<th>Legge della Pratica</th>
				<th>Protocollo Sollecito1</th>
				<th>Data Protocollo Sollecito 1</th>
				<th>Protocollo Sollecito 2</th>
				<th>Data Protocollo Sollecito 2</th>
				<th>Tecnico Incaricato</th>
				<th>Stampa Sollecito 1</th>
				<th>Stampa Sollecito 2</th>
				<th>mod.</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:forEach var="sollecito" items="${solleciti}">
				<tr>
					<td>${sollecito.iddatiPratica.numeroProtocollo}</td>
					<td>${sollecito.progressivoAbuso}</td>
					<td>${sollecito.iddatiPratica.leggeCondono.leggeNumero}</td>
					<td>${sollecito.protocolloSoll1}</td>
					<td>${sollecito.dataInvioSoll1}</td>
					<td>${sollecito.protocolloSoll2}</td>
					<td>${sollecito.dataInvioSoll2}</td>
					<td>${sollecito.tecnicoIncaricato}</td>
					<td><a
						href="<c:url value="stampaSollecito1.htm?idpratica=${sollecito.iddatiPratica}&idabuso=${sollecito.idAbuso}&progressivo=${sollecito.progressivoAbuso}" />"
						title="stampa primo sollecito"><img
							src="img/24/hand_yellow_card.png" title="stampa primo sollecito"></a></td>
					<td><a
						href="<c:url value="stampaSollecito2.htm?idpratica=${sollecito.iddatiPratica}&idabuso=${sollecito.idAbuso}&progressivo=${sollecito.progressivoAbuso}" />"
						title="stampa secondo sollecito"><img
							src="img/24/hand_red_card.png" title="stampa secondo sollecito"></a></td>
					<td><a
						href="<c:url value="modificaSollecito.htm?idSollecito=${sollecito.iddatiSollecito}&idabuso=${sollecito.idAbuso}&progressivo=${sollecito.progressivoAbuso}" />"
						title="modifica sollecito"><img src="img/24/edit.png"
							title="modifica sollecito"></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
