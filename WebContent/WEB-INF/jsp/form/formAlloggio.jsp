<!doctype html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../tpl/header.jsp" />
<div>
	<form:form method="POST" action="salvaAlloggio.htm"
		commandName="datiAlloggio" cssClass="ui-widget">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<table>
			<tr>
				<td>Tipologia alloggio :</td>
				<td><form:select path="tipologiaAlloggio">
						<form:options items="${tipologiaAlloggios}"
							itemLabel="descrizione" itemValue="idtipologiaAlloggio" />
					</form:select>
					<form:hidden path="iddatiAlloggio" /></td>
			</tr>
			<tr>
				<td>Destinazione uso :</td>
				<td><form:select path="destinazioneUso">
						<form:options items="${tipologiaDestinazioneUsos}"
							itemLabel="descrizioneTipologia"
							itemValue="idtipologiaDestinazioneUso" />
					</form:select></td>
			</tr>
			<tr>
				<td>Superficie utile :
				<td><form:input path="superficieUtile" /></td>
			</tr>
			<tr>
				<td>Superficie accessoria :</td>
				<td><form:input path="superficieAccessoria" /></td>
			</tr>
			<tr>
				<td>Caratteristiche speciali :</td>
				<td><form:checkboxes items="${caratteristicheSpecialis}"
						path="caratteriSpeciali" delimiter="<br />"
						itemLabel="descrizione" itemValue="idcaratteristicheSpeciali" /></td>
			</tr>
		</table>
		<form:button value="Salva" name="Salva">Salva</form:button>
	</form:form>
</div>