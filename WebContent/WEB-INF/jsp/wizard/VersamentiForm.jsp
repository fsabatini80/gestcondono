<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../tpl/header.jsp" />

<div class="ui-tabs-panel ui-widget-content ui-corner-bottom">

	<h2>Versamenti</h2>

	<form:form method="GET" action="page3.htm" commandName="datiPraticaPojo">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<table>
			<tr>
				<td>Numero Protocollo :</td>
				<td><form:input path="numeroProtocollo" /></td>
				<td><form:errors path="numeroProtocollo" cssClass="error" /></td>
			</tr>
			<tr>
			<tr>
				<td colspan="3"><input type="submit" value="indietro"
					name="_target2" /> <input type="submit" value="avanti"
					name="_target3" /> <input type="submit" value="esci"
					name="_cancel" /></td>
			</tr>
		</table>
	</form:form>
</div>
</body>
</html>