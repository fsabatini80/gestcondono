<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="../tpl/header.jsp" />
<h2
	class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Lista
	Solleciti</h2>
<div>
	<a href="<c:url value="nuovoSollecito.htm" />"
		title="aggiungi un nuovo sollecito"> <img
		title="aggiungi un nuovo sollecito" src="img/add.png">
	</a>
</div>
<div>
	<table id="example10" class="display">
		<thead>
			<tr>
				<th>Sott.</th>
				<th>Tecnico Incaricato</th>
				<th>Numero Protocollo 1</th>
				<th>Numero Protocollo 2</th>
				<th>Data Pagamento</th>
				<th>Stampa 1 sollecito</th>
				<th>Stampa 2 sollecito</th>
				<th>mod.</th>
				<th>canc.</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:forEach var="sollecito" items="${solleciti}">
				<tr>
					<td>${sollecito.progressivoAbuso}</td>
					<td>${sollecito.tecnicoIncaricato}</td>
					<td>${sollecito.protocolloSoll1}</td>
					<td>${sollecito.protocolloSoll2}</td>
					<td>${sollecito.dataPagamento}</td>
					<td><a
						href="<c:url value="modificaSollecito.htm?idSollecito=${sollecito.iddatiSollecito}" />"
						title="modifica sollecito"><img src="img/24/hand_yellow_card.png"
							title="modifica sollecito"></a></td>
					<td><a
						href="<c:url value="removeSollecito.htm?idSollecito=${sollecito.iddatiSollecito}" />"
						title="cancella sollecito"><img src="img/24/hand_red_card.png"
							title="cancella sollecito"></a></td>
					<td><a
						href="<c:url value="modificaSollecito.htm?idSollecito=${sollecito.iddatiSollecito}" />"
						title="modifica sollecito"><img src="img/24/edit.png"
							title="modifica sollecito"></a></td>
					<td><a
						href="<c:url value="removeSollecito.htm?idSollecito=${sollecito.iddatiSollecito}" />"
						title="cancella sollecito"><img src="img/24/delete.png"
							title="cancella sollecito"></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div>
	<a href="<c:url value="pratica.htm?idpratica=${idpratica}" />"
		title="INDIETRO"> Indietro </a>
</div>