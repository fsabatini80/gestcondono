<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="../tpl/header.jsp" />
<h2
	class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Lista
	Fabbricati</h2>
<img alt="aggiungi fabbricato" title="aggiungi fabbricato"
	src="img/add.png" onclick="runEffect('#effect7')">
<div id="effect7">
	<form:form action="salvaFabbricato.htm" commandName="fabbricatoNew"
		method="POST">
		<table>
			<tr>
				<td>Foglio :</td>
				<td><form:input path="foglio" /> <form:hidden
						path="iddatiFabbricati" /> <form:hidden path="idAlloggio" /></td>
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
	<table id="example7" class="display">
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
			<c:forEach items="${fabbricati}" var="fabbricato">
				<tr>
					<td>${fabbricato.foglio}</td>
					<td>${fabbricato.particella}</td>
					<td>${fabbricato.subalterno}</td>
					<td>${fabbricato.sezione}</td>
					<td><a
						href="<c:url value="removeFabbricato.htm?idfabbricato=${fabbricato.iddatiFabbricati}" />">
							<img title="rimuovi fabbricato" src="img/24/delete.png">
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div>
	<a href="<c:url value="alloggi.htm?idabuso=${idabuso}" />" title="INDIETRO">
		INDIETRO </a>
</div>