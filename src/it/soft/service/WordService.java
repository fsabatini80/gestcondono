package it.soft.service;

import it.soft.domain.Datiabuso;
import it.soft.domain.DocumentiAbuso;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiPraticaPojo;
import it.soft.web.pojo.DocumentiAbusoPojo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

@Service
public class WordService {

	public XWPFDocument createDoc(XWPFDocument document,
			DatiPraticaService praticaService, DatiAbusoService abusoService,
			String idpratica, String idabuso) throws Exception {
		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File(
				"createparagraph.docx"));
		DatiPraticaPojo praticaDB = praticaService.findById(idpratica);
		DatiAbusoPojo abusoDB = abusoService.findById(idabuso);
		WordService.createPage1(document, praticaDB, abusoDB);
		List<DocumentiAbuso> daocumentiDB  = abusoService.findAllDocById(idpratica);
		WordService.createPage2(document, daocumentiDB);
		document.write(out);
		out.close();
		System.out.println("createparagraph.docx written successfully");
		return document;

	}

	public static XWPFDocument createPage1(XWPFDocument document,
			DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB) {
		// create header
		XWPFParagraph paragraph = document.createParagraph();
		createHeader(paragraph);

		// create Protocollo e lista soggetti
		XWPFParagraph paragraphProtocollo = document.createParagraph();
		createListaSoggetti(paragraphProtocollo);

		XWPFParagraph paragraphggetto = document.createParagraph();
		creaOggetto(paragraphggetto);

		XWPFParagraph paragraphA = document.createParagraph();
		creaPargraphA(paragraphA);

		XWPFParagraph paragraphADati = document.createParagraph();
		creaPargraphAdati(paragraphADati);

		XWPFParagraph paragraphUbicazione = document.createParagraph();
		creaParagraphUbicazione(paragraphUbicazione);

		XWPFParagraph paragraphALocalizzazione = document.createParagraph();
		creaParagraphALocalizzazione(paragraphALocalizzazione);

		XWPFParagraph paragraphDescAbuso = document.createParagraph();
		creaParagraphDescAbuso(paragraphDescAbuso);

		XWPFParagraph paragraphDescAbusoDB = document.createParagraph();
		creaParagraphDescAbusoDB(paragraphDescAbusoDB);

		XWPFParagraph paragraphDatiCatastali = document.createParagraph();
		creaParagraphDatiCatastali(paragraphDatiCatastali);

		XWPFParagraph paragraphDatiCatastaliTable = document.createParagraph();
		creaParagraphDatiCatastaliTable(paragraphDatiCatastaliTable, document);

		XWPFParagraph paragraphDatiTecnici = document.createParagraph();
		creaParagraphDatiTecnici(paragraphDatiTecnici);

		XWPFParagraph paragraphDatiTecniciTable = document.createParagraph();
		creaParagraphDatiTecniciTable(paragraphDatiTecniciTable);

		XWPFParagraph paragraphInformativa = document.createParagraph();
		creaParagraphInformativa(paragraphInformativa);

		return document;
	}

	private static void creaParagraphDatiTecniciTable(
			XWPFParagraph paragraphDatiTecniciTable) {
		// TODO Auto-generated method stub

	}

	private static void creaParagraphDatiTecnici(
			XWPFParagraph paragraphDatiTecnici) {
		XWPFRun paragraphAOneRunTwo = paragraphDatiTecnici.createRun();
		String testo = "Dati Tecnici:";
		paragraphAOneRunTwo.setText(testo);
		paragraphAOneRunTwo.setBold(true);

	}

	private static void creaParagraphDatiCatastaliTable(
			XWPFParagraph paragraphDatiCatastaliTable, XWPFDocument document) {

		XWPFRun paragraphAOneRunTwo = paragraphDatiCatastaliTable.createRun();
		String testo = "Dati Catastali:";
		paragraphAOneRunTwo.setText(testo);
		paragraphAOneRunTwo.setBold(true);

		// create table
		XWPFTable table = document.createTable();
		// create first row
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).setText("SEZIONE");
		tableRowOne.addNewTableCell().setText("FOGLIO");
		tableRowOne.addNewTableCell().setText("PARTICELLA");
		tableRowOne.addNewTableCell().setText("SEZIONE");
		tableRowOne.addNewTableCell().setText("ZONA");
		// create second row
		XWPFTableRow tableRowTwo = table.createRow();
		tableRowTwo.getCell(0).setText("col one, row two");
		tableRowTwo.getCell(1).setText("col two, row two");
		tableRowTwo.getCell(2).setText("col three, row two");
		tableRowTwo.getCell(3).setText("col four, row two");
		tableRowTwo.getCell(4).setText("col five, row two");

		XWPFRun paragraphAOneRun = paragraphDatiCatastaliTable.createRun();
		paragraphAOneRun.addBreak();

	}

	private static void creaParagraphDatiCatastali(
			XWPFParagraph paragraphDatiCatastali) {
	}

	private static void creaParagraphDescAbusoDB(
			XWPFParagraph paragraphDescAbusoDB) {
		XWPFRun paragraphAOneRunTwo = paragraphDescAbusoDB.createRun();
		String testo = "Descrizione abuso: recuperato da DB";
		paragraphAOneRunTwo.setText(testo);
	}

	private static void creaParagraphDescAbuso(XWPFParagraph paragraphDescAbuso) {
		XWPFRun paragraphAOneRunTwo = paragraphDescAbuso.createRun();
		String testo = "Descrizione abuso:";
		paragraphAOneRunTwo.setText(testo);
		paragraphAOneRunTwo.setBold(true);
	}

	private static void creaParagraphALocalizzazione(
			XWPFParagraph paragraphALocalizzazione) {
		XWPFRun paragraphAOneRunTwo = paragraphALocalizzazione.createRun();
		String testo = "Località , Indirizzo ";
		paragraphAOneRunTwo.setText(testo);
	}

	private static void creaParagraphUbicazione(
			XWPFParagraph paragraphUbicazione) {
		XWPFRun paragraphAOneRunTwo = paragraphUbicazione.createRun();
		String testo = "Ubicazione dell'abuso:";
		paragraphAOneRunTwo.setText(testo);
		paragraphAOneRunTwo.setBold(true);
	}

	private static void creaParagraphInformativa(
			XWPFParagraph paragraphInformativa) {
		XWPFRun paragraphAOneRunTwo = paragraphInformativa.createRun();
		String testo = "Dall'istruttoria preliminare dell'istanza in oggetto, ai fini di poter completare le atività di disamina tecnico-amministrativo e procedere con il rilascio della Concessione in sanatoria la stessa, dovrà essere integrata con i documenti previsti dalle normative vigenti di cui al punto 1 e dalle attestazioni di versamento di cui al punto 2.";
		paragraphAOneRunTwo.setText(testo);
	}

	private static void creaPargraphAdati(XWPFParagraph paragraphA) {
		XWPFRun paragraphAOneRunTwo = paragraphA.createRun();
		String testo = "Numero interno: ";
		testo += ", sottonumero: ";
		testo += ", numero protocollo: ";
		paragraphAOneRunTwo.setText(testo);
	}

	private static void creaPargraphA(XWPFParagraph paragraphA) {
		paragraphA.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun paragraphAOneRunOne = paragraphA.createRun();
		paragraphAOneRunOne.setText("A) AVVIO ISTRUTTORIA DELLA PRATICA");
		paragraphAOneRunOne.setBold(true);
	}

	private static void creaOggetto(XWPFParagraph paragraphggetto) {
		XWPFRun paragOggetto = paragraphggetto.createRun();
		String oggetto = "Oggetto: Avvio dell'attività di istruttoria della Istanza di Sanatoria Edilizia richiesta ai sensi della legge n.___";
		oggetto += "presentata il __________";
		oggetto += "con protocollo n. __________";
		oggetto += "del ____";
		oggetto += "sott___";
		oggetto += "N° interno______";
		oggetto += "richiesta da ";
		oggetto += "residente in ";
		paragOggetto.setText(oggetto);

	}

	private static void createListaSoggetti(XWPFParagraph paragraph) {
		paragraph.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun paragraphOneRunTwo = paragraph.createRun();
		paragraphOneRunTwo.setText("Prot. N. xxxxx del XXXX");
		paragraphOneRunTwo.addBreak();
		XWPFRun paragraphSoggettiNominativo = paragraph.createRun();
		paragraphSoggettiNominativo.setText("Cognome e Nome: ");
		paragraphSoggettiNominativo.addBreak();
		XWPFRun paragraphSoggettiVia = paragraph.createRun();
		paragraphSoggettiVia.setText("Indirizzo: ");
		paragraphSoggettiVia.addBreak();
		XWPFRun paragraphSoggettiCap = paragraph.createRun();
		paragraphSoggettiCap.setText("Cap: ");
		paragraphSoggettiCap.addBreak();
		XWPFRun paragraphSoggettiComune = paragraph.createRun();
		paragraphSoggettiComune.setText("Comune: ");
	}

	private static void createHeader(XWPFParagraph paragraph) {
		// Set Bold an Italic
		XWPFRun paragraphOneRunOne = paragraph.createRun();
		paragraphOneRunOne.setBold(true);
		// COMUNE
		paragraphOneRunOne.setText("COMUNE DI GUIDONIA");
		paragraphOneRunOne.addBreak();
		// AREA
		XWPFRun paragraphOneRunOne1 = paragraph.createRun();
		paragraphOneRunOne1.setText("Area..........");
		paragraphOneRunOne1.addBreak();
		// Indirizzo ufficio
		paragraphOneRunOne1.setText("Indirizzo uffici.........");
		paragraphOneRunOne1.addBreak();
		// Telefono
		paragraphOneRunOne1.setText("telefono.........");
		paragraphOneRunOne1.addBreak();
		// Email
		paragraphOneRunOne1.setText("email..........");
	}

	public static XWPFDocument createPage2(XWPFDocument document, List<DocumentiAbuso> daocumentiDB) {
		XWPFParagraph paragraphDoc = document.createParagraph();
		creaPargraphDOC(paragraphDoc);

		XWPFParagraph paragraphDocTable = document.createParagraph();
		creaPargraphDOCTable(paragraphDocTable);

		XWPFParagraph paragraphInfoDoc = document.createParagraph();
		creaPargraphINOFDOC(paragraphInfoDoc);

		return document;
	}

	private static void creaPargraphINOFDOC(XWPFParagraph paragraphInfoDoc) {

		XWPFRun paragraphOne = paragraphInfoDoc.createRun();
		String testo = "La documentazione richiesta che dovrà riportare ";
		paragraphOne.setText(testo);

		XWPFRun paragraphTwo = paragraphInfoDoc.createRun();
		String testoBold1 = "il numero di pratica e il numero di protocollo di cui al punto A";
		paragraphTwo.setText(testoBold1);
		paragraphTwo.setBold(true);

		XWPFRun paragraphThree = paragraphInfoDoc.createRun();
		String testo2 = " della presente nota, dovrà pervenire ";
		paragraphThree.setText(testo2);

		XWPFRun paragraphFour = paragraphInfoDoc.createRun();
		String testoBoldUl1 = "entro e non oltre 90 gg. dal ricevimento della presente. ";
		paragraphFour.setText(testoBoldUl1);
		paragraphFour.setBold(true);

		XWPFRun paragraphFive = paragraphInfoDoc.createRun();
		String testo3 = "Si fa presente che tutta la modulistica necessaria è reperibile presso il sito web del all'indirizzo";
		paragraphFive.setText(testo3);
		paragraphFive.addBreak();

		XWPFRun paragraphSix = paragraphInfoDoc.createRun();
		String testoBold2 = "NOTA BENE:";
		paragraphSix.setText(testoBold2);
		paragraphSix.setBold(true);
		paragraphSix.addBreak();

		XWPFRun paragraphSeven = paragraphInfoDoc.createRun();
		String testoBold3 = "In difetto si procederà a norma di legge con un provvedimento di diniego della concessione in sanatoria e conseguente rimozione/demolizione dell'abuso con costi a carico del richiedente ovvero dell'acquisizione del bene al patrimonio dell'";
		paragraphSeven.setText(testoBold3);
		paragraphSeven.setBold(true);
		paragraphSeven.addBreak();
		paragraphSeven.addBreak();

		XWPFRun paragraphEithg = paragraphInfoDoc.createRun();
		String testoBold4 = "Qualora il destinatario della presente non sia a conoscenza della pratica in oggetto è cortesemente pregato di darne segnalazione all'Area Urbanistica ed Assetto del";
		paragraphEithg.setText(testoBold4);
		paragraphEithg.setBold(true);
		paragraphEithg.addBreak();

	}

	private static void creaPargraphDOCTable(XWPFParagraph paragraphDocTable) {
		// TODO Auto-generated method stub

	}

	private static void creaPargraphDOC(XWPFParagraph paragraphDoc) {
		paragraphDoc.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun paragraphAOneRunOne = paragraphDoc.createRun();
		paragraphAOneRunOne.addBreak(BreakType.PAGE);
		paragraphAOneRunOne.setText("1) DOCUMENTI DA INTEGRARE");
		paragraphAOneRunOne.setBold(true);
	}
}
