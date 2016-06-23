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
				<th>Cod. Versamento</th>
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
					<td>${versamento.codiceVersamento}</td>
					<td>${versamento.numeroBollettino}</td>
					<td>${versamento.numeroProtocollo}</td>
					<td>${versamento.dataVersamento}</td>
					<td>${versamento.importo}</td>
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
		title="INDIETRO"> INDIETRO </a>
</div>