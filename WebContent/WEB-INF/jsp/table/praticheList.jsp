<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<jsp:include page="../tpl/header.jsp" />
	
<div>
	<a href="<c:url value="nuovaPratica.htm" />"
		title="crea una nuova pratica"> <img
		title="crea una nuova pratica" src="img/add.png">
	</a>
</div>
<table id="example1" class="display">
	<thead>
		<tr>
			<th>Numero Pratica</th>
			<th>Numero Protocollo</th>
			<th>Nominativo</th>
			<th>Data domanda</th>
			<th>Legge condono</th>
			<th></th>
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
				<td><a
					href="<c:url value="modificaPratica.htm?idpratica=${pratica.iddatipratica}" />"
					title="modifica pratica"><img src="img/32/form_blue_edit.png"
						title="modifica pratica"></a><a
					href="<c:url value="abusi.htm?idpratica=${pratica.iddatipratica}" />"
					title="visualizza abusi"><img src="img/32/clipboard.png"
						title="visualizza abusi"></a> <a
					href="<c:url value="insPraticawzd.htm" />"
					title="visualizza versamenti"><img src="img/32/symbol_euro.png"
						title="visualizza versamenti"></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>