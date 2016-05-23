<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="../tpl/header.jsp" />

<table id="example4" class="display">
	<thead>
		<tr>
			<th>DESCRIZIONE</th>
			<th>Q.ta</th>
			<th>L.47/85</th>
			<th>L.724/94</th>
			<th>L.326/03</th>
			<th>TOTALE</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>Domande presenti in archivio</td>
			<td>n.</td>
			<td>${presentiL47}</td>
			<td>${presentiL724}</td>
			<td>${presentiL326}</td>
			<td>${presentiLTot}</td>
		</tr>
		<tr>
			<td>Domande istruite</td>
			<td>n.</td>
			<td>${istruiteL47}</td>
			<td>${istruiteL724}</td>
			<td>${istruiteL326}</td>
			<td>${istruiteLTot}</td>
		</tr>
		<tr>
			<td>Domande da istruire</td>
			<td>n.</td>
			<td>${daistruireL47}</td>
			<td>${daistruireL724 }</td>
			<td>${daistruireL326 }</td>
			<td>${daistruireTot }</td>
		</tr>
		<tr>
			<td>Abusi istruiti</td>
			<td>n.</td>
			<td>${abusiL47}</td>
			<td>${abusiL724 }</td>
			<td>${abusiL326 }</td>
			<td>${abusiTot}</td>
		</tr>
	</tbody>
</table>
</table>