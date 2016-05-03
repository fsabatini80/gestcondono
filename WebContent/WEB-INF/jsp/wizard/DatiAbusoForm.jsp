<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../tpl/header.jsp" />

<div class="ui-tabs-panel ui-widget-content ui-corner-bottom">

	<form:form action="creaNuovoAbuso.htm" commandName="datiPraticaPojo" method="GET">
		<form:button id="idCreaAbuso" name="CreaAbuso" value="crea abuso">Crea nuovo abuso</form:button>
	</form:form>
	<div>
		<jsp:include page="list/DatiAbusoList.jsp"></jsp:include>
	</div>
</div>
</body>
</html>