<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<jsp:include page="tpl/header.jsp" />
<h2
	class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Lista
	Stampe</h2>
<div>
	<table id="example1" class="display">
		<thead>
			<tr>
				<th>Numero Pratica</th>
				<th>Numero Protocollo</th>
				<th>Nominativo</th>
				<th>Data domanda</th>
				<th>Legge condono</th>
				<th>mod.</th>
				<th>vis. abusi</th>
				<th>vis. vers.</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:forEach var="pratica" items="${pratiche}">
				<tr>
					<td>${pratica.numeroPratica}</td>
					<td>${pratica.numeroProtocollo}</td>
					<td>${pratica.richiedente.cognome} ${pratica.richiedente.nome}</td>
					<td>${pratica.dataDomanda}</td>
					<td>${pratica.leggeCondono.leggeNumero}</td>
					<td><a id="x${pratica.iddatipratica}"
						href="<c:url value="modificaPratica.htm?idpratica=${pratica.iddatipratica}" />"
						title="modifica pratica"><img src="img/24/edit.png"
							title="modifica pratica"></a></td>
					<td><a id="y${pratica.iddatipratica}"
						href="<c:url value="abusi.htm?idpratica=${pratica.iddatipratica}" />"
						title="visualizza abusi"><img src="img/24/find.png"
							title="visualizza abusi"></a></td>
					<td><a id="z${pratica.iddatipratica}"
						href="<c:url value="versamenti.htm?idpratica=${pratica.iddatipratica}" />"
						title="visualizza versamenti"><img
							src="img/24/symbol_euro.png" title="visualizza versamenti"></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div>
	<a href="<c:url value="ricercaPratica.htm" />" title="INDIETRO">
		Indietro </a>
</div>