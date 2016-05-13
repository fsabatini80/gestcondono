<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../tpl/header.jsp" />

<div>
	<form:form action="salvaDocumento.htm" commandName="documentiAbuso"
		method="POST">
		<table>
			<tr class="ui-widget-content">
				<td>Numero Protocollo :</td>
				<td><form:input path="numeroProtocollo" /> <form:hidden
						path="iddocumentiAbuso" /></td>
			</tr>
			<tr class="ui-widget-content">
				<td>Data Protocollo :
				<td><form:input path="dataProtocollo" /></td>
			</tr>
			<tr class="ui-widget-content">
				<td>Tipo :
				<td><form:input path="tipo" /> <form:hidden
						path="idTipoDocumento" /></td>
			</tr>
			<tr class="ui-widget-content">
				<td>Valido</td>
				<td><form:checkbox path="valido" /></td>
			</tr>
			<tr class="ui-widget-content">
				<td>Presente</td>
				<td><form:checkbox path="presente" /></td>
			</tr>
		</table>
		<form:button>Salva</form:button>
	</form:form>
</div>
