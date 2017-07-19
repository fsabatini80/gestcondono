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
		//$("a").removeClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
		//$("span").removeClass("ui-button-text");
		var listx = document.querySelectorAll('*[id^="x"]');
		var listy = document.querySelectorAll('*[id^="y"]');
		var listz = document.querySelectorAll('*[id^="z"]');
		var listk = document.querySelectorAll('*[id^="k"]');
		for (i = 0; i < listx.length; i++) {
			listx[i].removeAttribute("class");
			listy[i].removeAttribute("class");
			listz[i].removeAttribute("class");
			listk[i].removeAttribute("class");
		}

	});
</script>
<h2
	class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Lista
	Pratiche</h2>
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
				<th>soll.</th>
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

					<td><a id="k${pratica.iddatipratica}"
						href="<c:url value="solleciti.htm?idpratica=${pratica.iddatipratica}" />"
						title="visualizza solleciti"><img
							src="img/24/hand_yellow_card.png" title="visualizza solleciti"></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div>
	<a href="<c:url value="ricercaPratica.htm" />" title="INDIETRO">
		Indietro </a>
</div>