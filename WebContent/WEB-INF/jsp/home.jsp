<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html>
<jsp:include page="tpl/header.jsp" />

<div id="accordion">
	<h3>Utenti</h3>
	<div id="accordion1">
		<sec:authorize access="hasRole('admin')">
			<img alt="aggiungi un nuovo utente" title="aggiungi un nuovo utente"
				src="img/user_add.png" onclick="runEffect('#effect3')">
			<jsp:include page="form/formUtenti.jsp"></jsp:include>
		</sec:authorize>
		<jsp:include page="table/utentiList.jsp"></jsp:include>
	</div>
</div>
<jsp:include page="tpl/footer.jsp" />
</body>
</html>