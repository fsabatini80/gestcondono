<!doctype html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../tpl/header.jsp" />
<div>
	<form:form method="GET" action="salvaAlloggio.htm"
		commandName="datiPraticaPojo" cssClass="ui-widget">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<table>
			<tr>
				<td>Tipologia alloggio :</td>
				<td><input type="text" /></td>
			</tr>
			<tr>
				<td>Destinazione uso :</td>
				<td><input type="text" /></td>
			</tr>
			<tr>
				<td>Superficie utile :
				<td><input type="text" /></td>
			</tr>
			<tr>
				<td>Superficie accessoria :</td>
				<td><input type="text" /></td>
			</tr>
			<tr>
				<td>Caratteri speciali :</td>
				<td><input type="text" /></td>
			</tr>
		</table>
		<form:button value="Salva" name="Salva">Salva</form:button>
	</form:form>
</div>