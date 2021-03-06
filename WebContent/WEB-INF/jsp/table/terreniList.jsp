<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="../tpl/header.jsp" />
<h2
	class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Lista
	Terreni</h2>
<img alt="aggiungi terreno" title="aggiungi terreno" src="img/add.png"
	onclick="runEffect('#effect8')">
<div id="effect8">
	<form:form action="salvaTerreno.htm" commandName="terrenoNew"
		method="POST">
		<table>
			<tr>
				<td>Foglio :</td>
				<td><form:input path="foglio" /> <form:hidden
						path="iddatiTerreni" /> <form:hidden path="idAlloggio" /></td>
				<td><form:errors path="foglio" cssClass="ui-state-error" />
			</tr>
			<tr>
				<td>Particella :</td>
				<td><form:input path="particella" /></td>
				<td><form:errors path="particella" cssClass="ui-state-error" />
			</tr>
			<tr>
				<td>Subalterno :
				<td><form:input path="subalterno" /></td>
				<td><form:errors path="subalterno" cssClass="ui-state-error" />
			</tr>
			<tr>
				<td>Sezione :</td>
				<td><form:input path="sezione" /></td>
				<td><form:errors path="sezione" cssClass="ui-state-error" />
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
				<th></th>
			</tr>
		</thead>
		<tbody align="center">
			<c:forEach items="${terreni}" var="terreno">
				<tr>
					<td>${terreno.foglio}</td>
					<td>${terreno.particella}</td>
					<td>${terreno.subalterno}</td>
					<td>${terreno.sezione}</td>
					<td><a
						href="<c:url value="removeTerreno.htm?idterreno=${terreno.iddatiTerreni}" />">
							<img title="rimuovi terreno" src="img/24/delete.png">
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div>
	<a href="<c:url value="alloggi.htm?idabuso=${idabuso}" />"
		title="INDIETRO"> Indietro </a>
</div>