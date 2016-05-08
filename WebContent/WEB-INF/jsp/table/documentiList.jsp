<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<jsp:include page="../tpl/header.jsp" />
<table id="example10" class="display">
	<thead>
		<tr>
			<th></th>
			<th>Tipo</th>
			<th>Data protocollo</th>
			<th>Numero protocollo</th>
			<th>Allegato</th>
			<th>Presente?</th>
			<th>Valido?</th>
			<th></th>
		</tr>
	</thead>
	<tbody align="center">
		<tr>
			<td><img src="img/sign_warning.png"></td>
			<td>Domanda condono</td>
			<td></td>
			<td></td>
			<td><img src="img/document_attachment.png"></td>
			<td><img src="img/document_ok.png"></td>
			<td><img src="img/document_forbidden.png"></td>
			<td><img src="img/document_edit.png"><img
				src="img/document_delete.png"></td>
		</tr>
		<tr>
			<td><img src="img/sign_warning.png"></td>
			<td>1° attestazione pagamento oblazione</td>
			<td></td>
			<td></td>
			<td><img src="img/document_attachment.png"></td>
			<td><img src="img/document_ok.png"></td>
			<td><img src="img/document_ok.png"></td>
			<td><img src="img/document_edit.png"><img
				src="img/document_delete.png"></td>
		</tr>
		<tr>
			<td><img src="img/sign_warning.png"></td>
			<td>1° attestazione pagamento oblazione regionale</td>
			<td></td>
			<td></td>
			<td><img src="img/document_attachment.png"></td>
			<td><img src="img/document_forbidden.png"></td>
			<td><img src="img/document_forbidden.png"></td>
			<td><img src="img/document_edit.png"><img
				src="img/document_delete.png"></td>
		</tr>
	</tbody>
</table>