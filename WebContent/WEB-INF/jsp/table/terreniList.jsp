<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="../tpl/header.jsp" />

<img alt="aggiungi terreno" title="aggiungi terreno" src="img/add.png"
	onclick="runEffect('#effect8')">
<div id="effect8">
	<form:form action="nuovoTerreno.htm" commandName="terrenoNew"
		method="POST">
		<table>
			<tr>
				<td>Foglio :</td>
				<td><form:input path="foglio" /></td>
			</tr>
			<tr>
				<td>Particella :</td>
				<td><form:input path="particella" /></td>
			</tr>
			<tr>
				<td>Subalterno :
				<td><form:input path="subalterno" /></td>
			</tr>
			<tr>
				<td>Sezione :</td>
				<td><form:input path="sezione" /></td>
			</tr>
			<tr>
				<td><form:button name="aggiungi" value="aggiungi">aggiungi</form:button>
				</td>
			</tr>
		</table>
	</form:form>
</div>
<div>
	<table id="example8" class="display">
		<thead>
			<tr>
				<th>Foglio</th>
				<th>Particella</th>
				<th>Subalterno</th>
				<th>Sezione</th>
			</tr>
		</thead>
		<tbody align="center">
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>