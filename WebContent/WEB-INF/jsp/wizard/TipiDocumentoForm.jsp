<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../tpl/header.jsp" />

<div class="ui-tabs-panel ui-widget-content ui-corner-bottom">
	<h2>Tipi documento</h2>

	<form:form method="GET" action="page4.htm"
		commandName="datiPraticaPojo">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<table id="example9" class="display">
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
		<table>
			<tr>
			<tr>
				<td colspan="3"><input type="submit" value="indietro"
					name="_target4" /> <input type="submit" value="fine"
					name="_finish" /> <input type="submit" value="esci" name="_cancel" /></td>
			</tr>

		</table>
	</form:form>
</div>
</body>
</html>