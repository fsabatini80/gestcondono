<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html>
<jsp:include page="tpl/header.jsp" />

<div id="tabs">
	<div>
		<ol>
			<li><a href="#tabs-1">Home</a></li>
			<li><a href="#tabs-2">Pratiche</a></li>
			<li><a href="#tabs-3">Scadenze</a></li>
			<li><a href="#tabs-4">Solleciti</a></li>
			<li><a href="#tabs-5">Stampe</a></li>
			<sec:authorize access="hasRole('admin')">
				<li><a href="#tabs-6">Cruscotto</a></li>
			</sec:authorize>
		</ol>
	</div>
	<div id="tabs-1">
		<div id="accordion">
			<h3>Utenti</h3>
			<div id="accordion1">
				<sec:authorize access="hasRole('admin')">
					<img alt="aggiungi un nuovo utente"
						title="aggiungi un nuovo utente" src="img/user_add.png"
						onclick="runEffect('#effect3')">
					<jsp:include page="form/formUtenti.jsp"></jsp:include>
				</sec:authorize>
				<jsp:include page="table/utentiList.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<div id="tabs-2">
		<jsp:include page="table/praticheList.jsp"></jsp:include>
	</div>
	<div id="tabs-3">
		<jsp:include page="table/appuntamentiList.jsp"></jsp:include>
	</div>
	<div id="tabs-4">
		<jsp:include page="table/praticheinscadenzaList.jsp"></jsp:include>
	</div>
	<div id="tabs-5"></div>
	<sec:authorize access="hasRole('admin')">
		<div id="tabs-6">
			<jsp:include page="table/praticheinscadenzaList.jsp"></jsp:include>
		</div>
	</sec:authorize>
</div>

</body>
</html>