<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="footer">
	<c:if test="${existScadenze}">
		<h3>Attenzione!! ci sono pratiche scadute, verifica le pratiche
			nel tab SCADENZE.</h3>
	</c:if>
</div>
