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
				<th>Id Pratica</th>
				<th>Id Abuso</th>
				<th>Progressivo abuso</th>
				<th>Nome file</th>
				<th>Data inserimento</th>
				<th>download file</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:forEach var="stampa" items="${stampe}">
				<tr>
					<td>${stampa.idPratica}</td>
					<td>${stampa.idAbuso}</td>
					<td>${stampa.progressivoAbuso}</td>
					<td>${stampa.nomeFileStampa}</td>
					<td>${stampa.dataInserimento}</td>
					<td><a id="x${stampa.idstampeSolleciti}"
						href="<c:url value="download.htm?idfile=${stampa.idstampeSolleciti}" />"
						title="donwload file"><img src="img/24/edit.png"
							title="download file"></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div>
	<a href="<c:url value="ricercaPratica.htm" />" title="INDIETRO">
		Indietro </a>
</div>
<jsp:include page="tpl/footer.jsp" />