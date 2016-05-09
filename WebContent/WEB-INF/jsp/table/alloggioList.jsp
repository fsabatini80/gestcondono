<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<jsp:include page="../tpl/header.jsp" />

<div>
	<a href="<c:url value="nuovoAlloggio.htm" />" title="aggiungi alloggio">
		<img title="aggiungi alloggio" src="img/add.png">
	</a>
</div>

<table id="example6" class="display">
	<thead>
		<tr>
			<th>Tipologia alloggio</th>
			<th>Destinazione uso</th>
			<th>Superficie utile</th>
			<th>Superficie accessoria</th>
			<th></th>
		</tr>
	</thead>
	<tbody align="center">
		<c:forEach var="alloggio" items="${alloggi}">
			<tr>
				<td>${alloggio.tipologiaAlloggio.descrizione}</td>
				<td>${alloggio.destinazioneUso.descrizioneTipologia}</td>
				<td>${alloggio.superficieUtile}</td>
				<td>${alloggio.superficieAccessoria}</td>
				<td><a
					href="<c:url value="modificaAlloggio.htm?idalloggio=${alloggio.iddatiAlloggio}" />"
					title="modifica alloggio"><img src="img/32/form_blue_edit.png"
						title="modifica alloggio"></a> <a
					href="<c:url value="fabbricati.htm?idalloggio=${alloggio.iddatiAlloggio}" />"
					title="visualizza fabbricati"><img
						src="img/32/form_blue_edit.png" title="visualizza fabbricati"></a>
					<a
					href="<c:url value="terreni.htm?idalloggio=${alloggio.iddatiAlloggio}" />"
					title="visualizza terreni"><img src="img/32/clipboard.png"
						title="visualizza terreni"></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>