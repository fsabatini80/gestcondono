package it.soft.service;

import it.soft.dao.DatiAbusoHome;
import it.soft.dao.DatiAlloggioHome;
import it.soft.dao.DatiFabbricatiHome;
import it.soft.dao.DatiTerreniHome;
import it.soft.dao.DestinazioneUsoHome;
import it.soft.dao.EpocaAbusoHome;
import it.soft.domain.DatiAlloggio;
import it.soft.domain.DatiFabbricati;
import it.soft.domain.DatiTerreni;
import it.soft.domain.DocumentiAbuso;
import it.soft.domain.RelSoggettoAbuso;
import it.soft.domain.TipologiaDestinazioneUso;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiPraticaPojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {

	@Autowired
	DestinazioneUsoHome destinazioneUsoHome;
	@Autowired
	DatiTerreniHome datiTerreniHome;
	@Autowired
	DatiFabbricatiHome datiFabbricatiHome;
	@Autowired
	DatiAlloggioHome datiAlloggioHome;
	@Autowired
	DatiAbusoHome datiAbusoHome;
	@Autowired
	EpocaAbusoHome epocaAbusoHome;

	public XWPFDocument createDoc(XWPFDocument document,
			DatiPraticaService praticaService, DatiAbusoService abusoService,
			String idpratica, String idabuso) throws Exception {
		DatiPraticaPojo praticaDB = praticaService.findById(idpratica);
		DatiAbusoPojo abusoDB = abusoService.findById(idabuso);
		List<RelSoggettoAbuso> listaSoggetti = abusoService
				.findAllSoggettiById(idabuso);
		List<DatiAlloggio> alloggi = datiAlloggioHome
				.findByIdAbuso(datiAbusoHome.findById(BigDecimal
						.valueOf(Integer.valueOf(idabuso))));
		createPage1(document, praticaDB, abusoDB, listaSoggetti, alloggi);
		List<DocumentiAbuso> daocumentiDB = abusoService
				.findAllDocById(idabuso);
		createPage2(document, daocumentiDB);
		System.out.println("create successfully");

		scriviLocalTest(document);

		return document;

	}

	private void scriviLocalTest(XWPFDocument document)
			throws FileNotFoundException, IOException {
		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File(
				"C:/createparagraph.docx"));
		document.write(out);
		out.close();
		System.out.println("create successfully");
	}

	public XWPFDocument createPage1(XWPFDocument document,
			DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB,
			List<RelSoggettoAbuso> listaSoggetti, List<DatiAlloggio> alloggi) {
		// create header
		XWPFParagraph paragraph = document.createParagraph();
		createHeader(paragraph);

		// create Protocollo e lista soggetti
		XWPFParagraph paragraphProtocollo = document.createParagraph();
		createListaSoggetti(paragraphProtocollo, praticaDB, abusoDB,
				listaSoggetti);

		XWPFParagraph paragraphggetto = document.createParagraph();
		creaOggetto(paragraphggetto, praticaDB, abusoDB);

		XWPFParagraph paragraphA = document.createParagraph();
		creaPargraphA(paragraphA);

		XWPFParagraph paragraphADati = document.createParagraph();
		creaPargraphADB(paragraphADati, praticaDB, abusoDB);

		XWPFParagraph paragraphUbicazione = document.createParagraph();
		creaParagraphUbicazione(paragraphUbicazione);

		XWPFParagraph paragraphALocalizzazione = document.createParagraph();
		creaParagraphUbicazioneDB(paragraphALocalizzazione, abusoDB);

		XWPFParagraph paragraphDescAbuso = document.createParagraph();
		creaParagraphDescAbuso(paragraphDescAbuso);

		XWPFParagraph paragraphDescAbusoDB = document.createParagraph();
		creaParagraphDescAbusoDB(paragraphDescAbusoDB, abusoDB);

		XWPFParagraph paragraphDatiCatastali = document.createParagraph();
		creaParagraphDatiCatastali(paragraphDatiCatastali);

		XWPFParagraph paragraphDatiCatastaliTable = document.createParagraph();
		for (DatiAlloggio datiAlloggio : alloggi) {
			creaParagraphDatiCatastaliDB(paragraphDatiCatastaliTable,
					datiAlloggio.getIddatiAlloggio(), document);
		}

		XWPFParagraph paragraphDatiTecnici = document.createParagraph();
		creaParagraphDatiTecnici(paragraphDatiTecnici);

		XWPFParagraph paragraphDatiTecniciTable = document.createParagraph();
		creaParagraphDatiTecniciTable(paragraphDatiTecniciTable, abusoDB,
				praticaDB);

		XWPFParagraph paragraphInformativa = document.createParagraph();
		creaParagraphInformativa(paragraphInformativa);

		return document;
	}

	private void creaParagraphDatiTecniciTable(
			XWPFParagraph paragraphDatiTecniciTable, DatiAbusoPojo abusoDB,
			DatiPraticaPojo praticaDB) {
		XWPFRun p1 = paragraphDatiTecniciTable.createRun();
		String testo1 = "Destinazione d'uso: ";
		if (abusoDB.getDestinazioneUso() != null
				&& !"".equals(abusoDB.getDestinazioneUso().trim())) {
			TipologiaDestinazioneUso s = destinazioneUsoHome.findById(Integer
					.valueOf(abusoDB.getDestinazioneUso()));
			testo1.concat(s.getDescrizioneTipologia());
		}

		p1.setText(testo1);
		p1.addBreak();
		XWPFRun p2 = paragraphDatiTecniciTable.createRun();
		String testo2 = "Superficie Utile Mq: " + abusoDB.getSuperficeUtile();
		p2.setText(testo2);
		p2.addBreak();
		XWPFRun p3 = paragraphDatiTecniciTable.createRun();
		String testo3 = "Non resid./accessori Mq: "
				+ abusoDB.getSuperficeUtile();
		p3.setText(testo3);
		p3.addBreak();
		XWPFRun p4 = paragraphDatiTecniciTable.createRun();
		String testo4 = "Mc VxP: " + abusoDB.getSuperficeTotale();
		p4.setText(testo4);
		p4.addBreak();
		XWPFRun p5 = paragraphDatiTecniciTable.createRun();
		String testo5 = "Tipologia: " + abusoDB.getTipologiaAbuso();
		p5.setText(testo5);
		p5.addBreak();
		XWPFRun p6 = paragraphDatiTecniciTable.createRun();
		String testo6 = "Epoca d'abuso: ".concat(epocaAbusoHome.findById(
				Integer.valueOf(abusoDB.getEpocaAbuso())).toString());
		p6.setText(testo6);
		p6.addBreak();
		XWPFRun p7 = paragraphDatiTecniciTable.createRun();
		String testo7 = "Data ultimazione lavori: "
				+ abusoDB.getDataUltimazioneLavori();
		p7.setText(testo7);
		p7.addBreak();

	}

	private void creaParagraphDatiTecnici(XWPFParagraph paragraphDatiTecnici) {
		XWPFRun paragraphAOneRunTwo = paragraphDatiTecnici.createRun();
		String testo = "Dati Tecnici:";
		paragraphAOneRunTwo.setText(testo);
		paragraphAOneRunTwo.setBold(true);

	}

	private void creaParagraphDatiCatastaliDB(
			XWPFParagraph paragraphDatiCatastaliTable, BigDecimal idalloggio,
			XWPFDocument document) {

		List<DatiFabbricati> listFabbric = datiFabbricatiHome
				.findAll(idalloggio.intValue());
		// create table
		XWPFTable table = document.createTable();
		// create first row
		XWPFTableRow tableRowOne = table.getRow(0);
		addTableCellCenter(tableRowOne.getCell(0), "SEZIONE");
		addTableCellCenter(tableRowOne.addNewTableCell(), "FOGLIO");
		addTableCellCenter(tableRowOne.addNewTableCell(), "PARTICELLA");
		addTableCellCenter(tableRowOne.addNewTableCell(), "SUBALTERNO");
		addTableCellCenter(tableRowOne.addNewTableCell(), "ZONA");

		for (DatiFabbricati datiFabbricati : listFabbric) {
			XWPFTableRow tableRowTwo = table.createRow();
			tableRowTwo.getCell(0).setText(datiFabbricati.getSezione());
			tableRowTwo.getCell(1).setText(datiFabbricati.getFoglio());
			tableRowTwo.getCell(2).setText(datiFabbricati.getParticella());
			tableRowTwo.getCell(3).setText(datiFabbricati.getSubalterno());
			// tableRowTwo.getCell(4).setText(datiFabbricati.get);
		}
		List<DatiTerreni> listTerreni = datiTerreniHome.findAll(idalloggio
				.intValue());
		for (DatiTerreni datiTerreni : listTerreni) {
			// create table
			XWPFTableRow tableRowTwo = table.createRow();
			tableRowTwo.getCell(0).setText(datiTerreni.getSezione());
			tableRowTwo.getCell(1).setText(datiTerreni.getFoglio());
			tableRowTwo.getCell(2).setText(datiTerreni.getParticella());
			tableRowTwo.getCell(3).setText(datiTerreni.getSubalterno());
			// tableRowTwo.getCell(4).setText(datiFabbricati.get);
		}

	}

	private void addTableCellCenter(XWPFTableCell tableCell, String testo) {
		XWPFParagraph parCell = tableCell.addParagraph();
		parCell.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = parCell.createRun();
		run.setText(testo);
		tableCell.addParagraph(parCell);
	}

	private void creaParagraphDatiCatastali(XWPFParagraph paragraphDatiCatastali) {
		XWPFRun paragraphAOneRunTwo = paragraphDatiCatastali.createRun();
		String testo = "Dati Catastali:";
		paragraphAOneRunTwo.setText(testo);
		paragraphAOneRunTwo.setBold(true);
	}

	private void creaParagraphDescAbusoDB(XWPFParagraph paragraphDescAbusoDB,
			DatiAbusoPojo abusoDB) {
		XWPFRun paragraphAOneRunTwo = paragraphDescAbusoDB.createRun();
		String testo = abusoDB.getDescrizione();
		paragraphAOneRunTwo.setText(testo);
	}

	private void creaParagraphDescAbuso(XWPFParagraph paragraphDescAbuso) {
		XWPFRun paragraphAOneRunTwo = paragraphDescAbuso.createRun();
		String testo = "Descrizione abuso:";
		paragraphAOneRunTwo.setText(testo);
		paragraphAOneRunTwo.setBold(true);
	}

	private void creaParagraphUbicazioneDB(
			XWPFParagraph paragraphALocalizzazione, DatiAbusoPojo abusoDB) {
		XWPFRun paragraphAOneRunTwo = paragraphALocalizzazione.createRun();
		String testo = "Località " + abusoDB.getLocalizzazione().getComune()
				+ ", Indirizzo " + abusoDB.getLocalizzazione().getIndirizzo();
		paragraphAOneRunTwo.setText(testo);
	}

	private void creaParagraphUbicazione(XWPFParagraph paragraphUbicazione) {
		XWPFRun paragraphAOneRunTwo = paragraphUbicazione.createRun();
		String testo = "Ubicazione dell'abuso:";
		paragraphAOneRunTwo.setText(testo);
		paragraphAOneRunTwo.setBold(true);
	}

	private void creaParagraphInformativa(XWPFParagraph paragraphInformativa) {
		XWPFRun paragraphAOneRunTwo = paragraphInformativa.createRun();
		String testo = "Dall'istruttoria preliminare dell'istanza in oggetto, ai fini di poter completare le attività di disamina tecnico-amministrativo e procedere con il rilascio della Concessione in sanatoria la stessa, dovrà essere integrata con i documenti previsti dalle normative vigenti di cui al punto 1 e dalle attestazioni di versamento di cui al punto 2.";
		paragraphAOneRunTwo.setText(testo);
	}

	private void creaPargraphADB(XWPFParagraph paragraphA,
			DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB) {
		XWPFRun paragraphAOneRunTwo = paragraphA.createRun();
		String testo = "Numero interno: " + praticaDB.getNumeroPratica();
		testo += ", sottonumero: " + abusoDB.getProgressivo();
		testo += ", numero protocollo: " + praticaDB.getNumeroProtocollo();
		paragraphAOneRunTwo.setText(testo);
	}

	private void creaPargraphA(XWPFParagraph paragraphA) {
		paragraphA.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun paragraphAOneRunOne = paragraphA.createRun();
		paragraphAOneRunOne.setText("A) AVVIO ISTRUTTORIA DELLA PRATICA");
		paragraphAOneRunOne.setBold(true);
	}

	private void creaOggetto(XWPFParagraph paragraphggetto,
			DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB) {
		XWPFRun paragOggetto = paragraphggetto.createRun();
		String oggetto = "Oggetto: Avvio dell'attività di istruttoria della Istanza di Sanatoria Edilizia richiesta ai sensi della legge n."
				+ praticaDB.getLeggeCondono();
		oggetto += " presentata il " + praticaDB.getDataDomanda();
		oggetto += " con protocollo n. " + praticaDB.getNumeroProtocollo();
		oggetto += " del " + praticaDB.getDataProtocollo();
		oggetto += " sott " + abusoDB.getProgressivo();
		oggetto += " N° interno " + praticaDB.getNumeroPratica();
		oggetto += " richiesta da " + praticaDB.getCognome() + " "
				+ praticaDB.getNome();
		oggetto += " residente in " + praticaDB.getIndirizzo();
		paragOggetto.setText(oggetto);

	}

	private void createListaSoggetti(XWPFParagraph paragraph,
			DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB,
			List<RelSoggettoAbuso> listaSoggetti) {
		paragraph.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun paragraphOneRunTwo = paragraph.createRun();
		String testo1 = "Prot. N. " + praticaDB.getNumeroProtocollo() + " del "
				+ praticaDB.getDataProtocollo();
		paragraphOneRunTwo.setText(testo1);
		paragraphOneRunTwo.addBreak();
		paragraphOneRunTwo.addBreak();
		for (RelSoggettoAbuso relSoggettoAbuso : listaSoggetti) {
			XWPFRun paragraphSoggettiNominativo = paragraph.createRun();
			paragraphSoggettiNominativo.setText("Cognome e Nome: "
					+ relSoggettoAbuso.getCognome() + " "
					+ relSoggettoAbuso.getNome());
			paragraphSoggettiNominativo.addBreak();
			XWPFRun paragraphSoggettiVia = paragraph.createRun();
			paragraphSoggettiVia.setText("Indirizzo: "
					+ relSoggettoAbuso.getIndirizzo());
			paragraphSoggettiVia.addBreak();
			XWPFRun paragraphSoggettiCap = paragraph.createRun();
			paragraphSoggettiCap.setText("Cap: " + relSoggettoAbuso.getCap());
			paragraphSoggettiCap.addBreak();
			XWPFRun paragraphSoggettiComune = paragraph.createRun();
			paragraphSoggettiComune.setText("Comune: "
					+ relSoggettoAbuso.getComuneResidenza());
			paragraphSoggettiComune.addBreak();
			paragraphSoggettiComune.addBreak();
		}
	}

	private void createHeader(XWPFParagraph paragraph) {
		XWPFRun paragraphOneRunOne = paragraph.createRun();
		paragraphOneRunOne.setBold(true);
		paragraphOneRunOne.setText("COMUNE DI GUIDONIA");
		paragraphOneRunOne.addBreak();
		XWPFRun paragraphOneRunOne1 = paragraph.createRun();
		paragraphOneRunOne1.setText("Area..........");
		paragraphOneRunOne1.addBreak();
		paragraphOneRunOne1.setText("Indirizzo uffici.........");
		paragraphOneRunOne1.addBreak();
		paragraphOneRunOne1.setText("telefono.........");
		paragraphOneRunOne1.addBreak();
		paragraphOneRunOne1.setText("email..........");
	}

	public XWPFDocument createPage2(XWPFDocument document,
			List<DocumentiAbuso> daocumentiDB) {
		XWPFParagraph paragraphDoc = document.createParagraph();
		creaPargraphDOC(paragraphDoc);

		XWPFParagraph paragraphDocTable = document.createParagraph();
		creaPargraphDOCTable(paragraphDocTable, daocumentiDB);

		XWPFParagraph paragraphInfoDoc = document.createParagraph();
		creaPargraphINOFDOC(paragraphInfoDoc);

		return document;
	}

	private void creaPargraphINOFDOC(XWPFParagraph paragraphInfoDoc) {

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

	private void creaPargraphDOCTable(XWPFParagraph paragraphDocTable,
			List<DocumentiAbuso> daocumentiDB) {
		XWPFRun paragraphAOneRunOne = paragraphDocTable.createRun();
		paragraphAOneRunOne.setBold(true);
		for (DocumentiAbuso documentiAbuso : daocumentiDB) {
			paragraphAOneRunOne.setText(" - ".concat(documentiAbuso
					.getIdTipoDocumento().getDescrizione()));
			paragraphAOneRunOne.addBreak();
		}
	}

	private void creaPargraphDOC(XWPFParagraph paragraphDoc) {
		paragraphDoc.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun paragraphAOneRunOne = paragraphDoc.createRun();
		paragraphAOneRunOne.addBreak(BreakType.PAGE);
		paragraphAOneRunOne.setText("1) DOCUMENTI DA INTEGRARE");
		paragraphAOneRunOne.setBold(true);
	}
}
