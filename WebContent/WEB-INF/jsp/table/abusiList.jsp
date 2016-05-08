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
			<th>N. Prog.</th>
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
				<td>${abuso.progressivo}</td>
				<td>${abuso.descrizione}</td>
				<td>${abuso.destinazioneUso.descrizioneTipologia}</td>
				<td>da ${abuso.epocaAbuso.epocaDa} a ${abuso.epocaAbuso.epocaA}</td>
				<td>${abuso.tipoOpera.descrizione}</td>
				<td width="300px"><a
					href="<c:url value="modificaAbuso.htm?idabuso=${abuso.iddatiabuso}" />"
					title="modifica abuso"><img src="img/24/clipboard.png"
						title="modifica abuso"></a> <a
					href="<c:url value="alloggi.htm?idpratica=${abuso.iddatiabuso}" />"
					title="visualizza alloggi"><img src="img/24/houses.png"
						title="visualizza alloggi"></a> <a
					href="<c:url value="documenti.htm?idpratica=${abuso.iddatiabuso}" />"
					title="visualizza documenti"><img
						src="img/24/document_find.png" title="visualizza documenti"></a>
					<a
					href="<c:url value="documenti.htm?idpratica=${abuso.iddatiabuso}" />"
					title="visualizza soggetti abuso"><img
						src="img/24/users3.png" title="visualizza soggetti abuso"></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>