<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="../tpl/header.jsp" />

<div>
	<a href="<c:url value="nuovoAbuso.htm" />" title="crea un nuovo abuso">
		<img title="crea un nuovo abuso" src="img/add.png">
	</a>
</div>
<table id="example10" class="display">
	<thead>
		<tr>
			<th>Descrizione</th>
			<th>Destinazione Uso</th>
			<th>Epoca Abuso</th>
			<th>Tipo Opera</th>
			<th></th>
		</tr>
	</thead>
	<tbody align="center">
		<c:forEach var="abuso" items="${abusi}">
			<tr>
				<td>${abuso.descrizione}</td>
				<td>${abuso.destinazioneUso}</td>
				<td>${abuso.epocaAbuso}</td>
				<td>${abuso.tipoOpera}</td>
				<td><a
					href="<c:url value="modificaAbuso.htm?idabuso=${abuso.iddatiabuso}" />"
					title="modifica abuso"><img src="img/32/form_blue_edit.png"
						title="modifica abuso"></a> <a
					href="<c:url value="alloggi.htm?idpratica=${pratica.iddatipratica}" />"
					title="visualizza alloggi"><img src="img/32/form_blue_edit.png"
						title="visualizza alloggi"></a> <a
					href="<c:url value="documenti.htm?idpratica=${pratica.iddatipratica}" />"
					title="visualizza documenti"><img src="img/32/clipboard.png"
						title="visualizza documenti"></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>