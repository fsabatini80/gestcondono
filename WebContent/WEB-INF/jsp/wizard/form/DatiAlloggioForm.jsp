<!doctype html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="effect4"
	class="ui-tabs-panel ui-widget-content ui-corner-bottom">
	<h2
		class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Alloggio
	</h2>
	<div>
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

		<div>
			<img alt="aggiungi localizzazione" title="aggiungi localizzazione"
				src="img/add.png" onclick="runEffect('#effect6')">aggiungi
			localizzazione
			<jsp:include page="DatiLocalizzazioneForm.jsp"></jsp:include>
		</div>
	</div>
</div>
