<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<form:form action="removeComune.htm" commandName="comuni"
	id="comuneDelete">
	<table id="example1" class="display" >
		<thead>
			<tr>
				<th>Id</th>
				<th>Comune</th>
				<th>Provincia</th>
				<th>Regione</th>
				<th>Modifica</th>
				<th>Cancella</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="comu" items="${comuni}">

				<tr>
					<td>${comu.idcomuni}</td>
					<td>${comu.comune}</td>
					<td>${comu.provincia}</td>
					<td>${comu.regione}</td>
					<td><a
						href="<c:url value="/modificaComune.htm?id=${comu.idcomuni}" />">
							<img title="modifica comune" src="img/32/user_edit.png">
					</a></td>
					<td><a
						href="<c:url value="/removeComune.htm?id=${comu.idcomuni}" />"><img
							title="rimuovi comune" src="img/32/user_delete.png"></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form:form>