<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<jsp:include page="../tpl/header.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		var listx = document.querySelectorAll('*[id^="x"]');
		var listy = document.querySelectorAll('*[id^="y"]');
		for (i = 0; i < listx.length; i++) {
			listx[i].removeAttribute("class");
			listy[i].removeAttribute("class");
		}

	});
</script>
<h2
	class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Lista
	Documenti</h2>
<img alt="aggiungi documento" title="aggiungi documento"
	src="img/add.png" onclick="runEffect('#effect9')">
<div id="effect9">
	<form:form action="nuovoDocumento.htm"
		commandName="tipologiaDocumentoPojo" method="POST">
		<table>
			<tr>
				<td>Documenti da aggiungere:</td>
				<td><form:checkboxes items="${documentiAdd}"
						itemLabel="descrizione" itemValue="idtipologiaDocumento"
						path="docToAdd" delimiter="<br />" /></td>
			</tr>
			<tr>
				<td><form:button name="aggiungi" value="aggiungi">aggiungi</form:button>
				</td>
			</tr>
		</table>
	</form:form>
</div>
<div>
	<table id="example10" class="display">
		<thead>
			<tr>
				<th></th>
				<th>Tipo</th>
				<th>Data protocollo</th>
				<th>Numero protocollo</th>
				<!--<th>Allegato</th>-->
				<th>Presente?</th>
				<th>Valido?</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody align="center">
			<c:forEach items="${documenti}" var="doc">
				<tr>
					<td width="100px"><c:if
							test="${doc.idTipoDocumento.obbligatorio > 0}">
							<img src="img/sign_warning.png">
						</c:if></td>
					<td>${doc.idTipoDocumento.descrizione}</td>
					<td>${doc.dataProtocollo}</td>
					<td>${doc.numeroProtocollo}</td>
					<!-- td><img src="img/document_attachment.png"></td> -->
					<td><c:if test="${doc.presente}">
							<img src="img/24/document_ok.png">
						</c:if> <c:if test="${!doc.presente}">
							<img src="img/24/document_forbidden.png">
						</c:if></td>
					<td><c:if test="${doc.valido}">
							<img src="img/24/document_ok.png">
						</c:if> <c:if test="${!doc.valido}">
							<img src="img/24/document_forbidden.png">
						</c:if></td>
					<td><a id="x${doc.iddocumentiAbuso}"
						href="<c:url value="modificaDocumento.htm?iddocumento=${doc.iddocumentiAbuso}" />"><img
							src="img/24/edit.png"></a></td>
					<td><a id="y${doc.iddocumentiAbuso}"
						href="<c:url value="eliminaDocumento.htm?iddocumento=${doc.iddocumentiAbuso}" />"><img
							src="img/24/delete.png"></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div>
	<a href="<c:url value="abusi.htm?idpratica=${idpratica}" />"
		title="INDIETRO"> Indietro </a>
</div>