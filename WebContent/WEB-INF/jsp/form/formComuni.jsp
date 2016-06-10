<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="effect2" class="ui-widget-content ui-corner-all">
	<div>
		<form:form action="addComune.htm" commandName="comune" method="POST">
			<table>
				<tr class="ui-widget-content">
					<td>Comune :</td>
					<td><form:input path="comune" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Provincia :
					<td><form:input path="provincia" /></td>
				</tr>
				<tr class="ui-widget-content">
					<td>Regione</td>
					<td><form:input path="regione" /></td>
				</tr>
			</table>
			<form:button>Inserisci</form:button>
			<input type="button"
				class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
				value="Indietro" name="Indietro" onclick="history.go(-1)" />
		</form:form>
	</div>
</div>