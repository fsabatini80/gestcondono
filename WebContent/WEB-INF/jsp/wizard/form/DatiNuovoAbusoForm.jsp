<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<jsp:include page="../../tpl/header.jsp" />

<div>
	<form:form method="GET" action="page2.htm" cssClass="ui-widget">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<div id="accordionDatAbuso">
			<h3>Dati abuso</h3>
			<div id="accordionDatAbuso1">
				<table>
					<tr>
						<td>Descrizione:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Destinazione d'uso*:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Tipologia*:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Epoca abuso*:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Data ultimazione lavori*:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Tipo opera:</td>
						<td><input type="text" /></td>
					</tr>
				</table>
			</div>
			<h2>Dimensioni</h2>
			<div id="accordionDatAbuso2">
				<table>
					<tr>
						<td>Suoperficie utile mq:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Volume utile vuoto per pieno mc:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Non residenziale/accessori mq:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Non redenziale/accessori vuoto per pieno mc:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Superficie totale insediamento abuso mq:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Volume totale insediamento abuso mc:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Volume direzionale mc:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Numero Addetti:</td>
						<td><input type="text" /></td>
					</tr>
				</table>
			</div>
			<h2>Esenzioni/riduzioni</h2>
			<div id="accordionDatAbuso3">
				<table>
					<tr>
						<td>Esenzioni pagamenti:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Riduzioni:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Tipo reddito:</td>
						<td><input type="text" /></td>
					</tr>
					<tr>
						<td>Reddito:</td>
						<td><input type="text" /></td>
					</tr>
				</table>
			</div>
			<h2>Soggetto abuso</h2>
			<div id="accordionDatAbuso4">
				<table>
					<tr>
						<td></td>
						<td><input type="checkbox" value="PROPRIETARIO">PROPRIETARIO
							<input type="checkbox" value="RICHIEDENTE">RICHIEDENTE <input
							type="checkbox" value="DELEGATO">DELEGATO <input
							type="checkbox" value="ALTRO"> ALTRO<input
							type="checkbox" value="DECEDUTO">DECEDUTO <input
							type="checkbox" value="COMPROPRIETARIO">COMPROPRIETARIO</td>
					</tr>
				</table>
			</div>
			<h2>Localizzazione</h2>
			<div id="accordionDatAbuso5">
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
		</div>
		<input type="button" value="Salva Abuso">
	</form:form>

	<!-- h2>Informazioni Alloggio</h2>
	<div>
		<jsp:include page="../list/DatiAlloggioList.jsp"></jsp:include>
		<img alt="aggiungi alloggio" title="aggiungi alloggio"
			src="img/add.png" onclick="runEffect('#effect4')">aggiungi
		alloggio
		<form action="#" class="ui-widget">
			<jsp:include page="../form/DatiAlloggioForm.jsp"></jsp:include>
			<input type="button" value="Salva alloggio" />
		</form>
	</div> -->
</div>

</body>
</html>