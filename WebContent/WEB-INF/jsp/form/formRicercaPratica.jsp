<!doctype html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../tpl/header.jsp" />


<div>
	<form:form action="eseguiRicerca.htm" commandName="ricercaPraticaPojo"
		cssClass="ui-widget" method="GET">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<table>
			<tr>
				<td>Numero Pratica:</td>
				<td><form:input path="numeroPratica" /></td>
				<td>Numero Protocollo:</td>
				<td><form:input path="numeroProtocollo" /></td>
			</tr>
			<tr>
				<td>Nominativo:
				<td><form:input path="nominativo" /></td>
				<td>Data domanda :</td>
				<td><form:input path="dataDomanda" /></td>
			</tr>
			<tr>
				<td>Legge condono:</td>
				<td><form:select path="leggeCondono">
						<form:options items="${leggiList}" itemLabel="leggeNumero"
							itemValue="idleggiCondono" />
					</form:select></td>
			</tr>
		</table>
		<form:button value="Ricerca" name="Ricerca">Ricerca</form:button>
	</form:form>
</div>
</html>