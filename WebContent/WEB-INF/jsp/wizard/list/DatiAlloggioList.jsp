<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<table id="example6" class="display">
	<thead>
		<tr>
			<th>Tipologia alloggio</th>
			<th>Destinazione uso</th>
			<th>Superficie utile</th>
			<th>Superficie accessoria</th>
			<th>Caratteri speciali</th>
		</tr>
	</thead>
	<tbody align="center">
		<tr>
			<td>${pratica.numeroPratica}</td>
			<td>${pratica.numeroProtocollo}</td>
			<td>${pratica.richiedente.codiceFiscale}</td>
			<td>${pratica.dataDomanda}</td>
			<td>${pratica.leggeCondono}</td>
		</tr>
	</tbody>
</table>