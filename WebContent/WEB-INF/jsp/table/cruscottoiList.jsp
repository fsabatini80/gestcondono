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
	<tbody align="center">
		<tr>
			<td>Domande presenti in archivio</td>
			<td>n.</td>
			<td>${presentiL47}</td>
			<td>${presentiL724}</td>
			<td>${presentiL326}</td>
			<td>${presentiLTot}</td>
		</tr>
		<tr>
			<td>Abusi istruiti</td>
			<td>n.</td>
			<td>${abusiL47}</td>
			<td>${abusiL724 }</td>
			<td>${abusiL326 }</td>
			<td>${abusiTot}</td>
		</tr>
		<tr>
			<td>Domande scadute</td>
			<td>n.</td>
			<td>${scaduteL47}</td>
			<td>${scaduteL724}</td>
			<td>${scaduteL326}</td>
			<td>${scaduteTot}</td>
		</tr>
		<tr>
			<td>Domande sollecitate</td>
			<td>n.</td>
			<td>${sollecitateL47}</td>
			<td>${sollecitateL724}</td>
			<td>${sollecitateL326}</td>
			<td>${sollecitateTot}</td>
		</tr>
		<tr>
			<td>Domande sollecitate insolute</td>
			<td>n.</td>
			<td>${sollecitateInsoluteL47}</td>
			<td>${sollecitateInsoluteL724 }</td>
			<td>${sollecitateInsoluteL326 }</td>
			<td>${sollecitateInsoluteTot }</td>
		</tr>
	</tbody>
</table>
