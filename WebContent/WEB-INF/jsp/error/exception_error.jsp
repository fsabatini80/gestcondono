<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<jsp:include page="../tpl/header.jsp" />
<body>
	<c:if test="${not empty exception.cause}">
		<h1>${exception.cause} : System Errors</h1>
	</c:if>

	<c:if test="${empty exception.cause}">
		<h1>System Errors</h1>
	</c:if>

	<c:if test="${not empty exception.message}">
		<h2>${exception.message}</h2>
	</c:if>
</body>
</html>