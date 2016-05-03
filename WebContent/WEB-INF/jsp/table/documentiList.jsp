<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<table id="example10" class="display">
	<thead>
		<tr>
			<th>Numero Pratica</th>
			<th>Numero Protocollo</th>
			<th>Codice fiscale</th>
			<th>Data domanda</th>
			<th>Legge condono</th>
			<th></th>
		</tr>
	</thead>
	<tbody align="center">
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td><a
				href="<c:url value="modificaPratica.htm?idpratica=${pratica.iddatipratica}" />"
				title="modifica pratica"><img src="img/32/form_blue_edit.png"
					title="modifica pratica"></a><a
				href="<c:url value="visualizzaAbusi.htm?idpratica=${pratica.iddatipratica}" />"
				title="visualizza abusi"><img src="img/32/clipboard.png"
					title="visualizza abusi"></a> <a
				href="<c:url value="insPraticawzd.htm" />"
				title="visualizza versamenti"><img src="img/32/symbol_euro.png"
					title="visualizza versamenti"></a></td>
		</tr>
	</tbody>
</table>