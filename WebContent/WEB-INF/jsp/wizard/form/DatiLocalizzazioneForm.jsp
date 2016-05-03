<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>

<div id="effect6" class="ui-widget-content ui-corner-all">
	<h2
		class="ui-accordion-header ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons">Localizzazione
	</h2>
	<form action="#" class="ui-widget">
		<div>
			<table>
				<tr>
					<td>Provincia :</td>
					<td><input type="text" /></td>
				</tr>
				<tr>
					<td>Comune :</td>
					<td><input type="text" /></td>
				</tr>
				<tr>
					<td>Cap :
					<td><input type="text" /></td>
				</tr>
				<tr>
					<td>Indirizzo :</td>
					<td><input type="text" /></td>
				</tr>
			</table>
		</div>
		<div>
			<table>
				<tr>
					<td>Zona urbanizzazione :</td>
					<td><input type="text" /></td>
				</tr>
				<tr>
					<td>Immobile soggetto a vincoli di tutela :</td>
					<td><input type="text" /></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="button"
						value="aggiungi" /></td>
				</tr>
			</table>

		</div>
	</form>
	<img alt="aggiungi alloggio" title="aggiungi fabbricati"
		src="img/add.png" onclick="runEffect('#effect7')">aggiungi
	fabbricati
	<jsp:include page="DatiFabbricatiForm.jsp"></jsp:include>
	<jsp:include page="../list/DatiFabbricatiList.jsp"></jsp:include>
	<img alt="aggiungi alloggio" title="aggiungi terreni" src="img/add.png"
		onclick="runEffect('#effect8')">aggiungi terreni
	<jsp:include page="DatiTerreniForm.jsp"></jsp:include>
	<jsp:include page="../list/DatiTerreniList.jsp"></jsp:include>


</div>
</html>