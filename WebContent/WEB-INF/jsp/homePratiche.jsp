<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html>
<jsp:include page="tpl/header.jsp" />

<div>
	<a href="<c:url value="nuovaPratica.htm" />"
		title="crea una nuova pratica" style="width: 350px; vertical-align:middle; text-align: left;"> <img
		title="crea una nuova pratica" src="img/add.png"> Inserisci una
		nuova pratica
	</a>
</div>
<div >
	<a href="<c:url value="ricercaPratica.htm" />" title="cerca pratica"
		style="width: 350px; vertical-align:middle; text-align: left;"> <img title="cerca pratica"
		src="img/find.png"> Cerca tra le pratiche
	</a>
</div>

<jsp:include page="tpl/footer.jsp" />
</body>
</html>