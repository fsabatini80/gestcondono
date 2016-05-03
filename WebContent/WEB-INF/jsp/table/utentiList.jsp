<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<table id="example" class="display">
	<thead>
		<tr>
			<th>User</th>
			<th>Cognome</th>
			<th>Nome</th>
			<th>Tipo Utenza</th>
			<th>Cancella Utente</th>
		</tr>
	</thead>
	<tbody align="center">

		<c:forEach var="utente" items="${utentiAll}">
			<tr>
				<td>${utente.username}</td>
				<td>${utente.cognome}</td>
				<td>${utente.nome}</td>
				<td>${utente.ruolo}</td>
				<td><a
					href="<c:url value="removeUtente.htm?user=${utente.idUtenti}" />">
						<img title="rimuovi utente" src="img/32/user_delete.png">
				</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
