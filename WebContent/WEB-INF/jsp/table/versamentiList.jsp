<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="../tpl/header.jsp" />
<h2
	class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Lista
	Versamenti</h2>
<div>
	<a href="<c:url value="nuovoVersamento.htm" />"
		title="aggiungi un nuovo versamento"> <img
		title="aggiungi un nuovo versamento" src="img/add.png">
	</a>
</div>
<div>
	<table id="example10" class="display">
		<thead>
			<tr>
				<th>Sott.</th>
				<th>Causale Versamento</th>
				<th>Numero Bollettino</th>
				<th>Numero Protocollo</th>
				<th>Data Versamento</th>
				<th>Importo</th>
				<th>mod.</th>
				<th>canc.</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:forEach var="versamento" items="${versamenti}">
				<tr>
					<td>${versamento.progressivo_abuso}</td>
					<td><c:if test="${versamento.causale==1}">Oblazione Comune</c:if>
					<c:if test="${versamento.causale==2}">Oneri</c:if>
					<c:if test="${versamento.causale==3}">Diritti Segreteria</c:if>
					<c:if test="${versamento.causale==4}">Oblazione Ministero</c:if>
					<c:if test="${versamento.causale==5}">Oblazione Regione</c:if></td>
					<td>${versamento.numeroBollettino}</td>
					<td>${versamento.numeroProtocollo}</td>
					<td>${versamento.dataVersamento}</td>
					<td>${versamento.importoEuro}</td>
					<td><a
						href="<c:url value="modificaVersamento.htm?idVersamento=${versamento.iddatiVersamento}" />"
						title="modifica versamento"><img src="img/24/edit.png"
							title="modifica versamento"></a></td>
					<td><a
						href="<c:url value="removeVersamento.htm?idVersamento=${versamento.iddatiVersamento}" />"
						title="cancella versamento"><img src="img/24/delete.png"
							title="cancella versamento"></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div>
	<a href="<c:url value="pratica.htm?idpratica=${idpratica}" />"
		title="INDIETRO"> Indietro </a>
</div>