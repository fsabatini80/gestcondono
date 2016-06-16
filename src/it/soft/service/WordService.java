package it.soft.service;

import it.soft.dao.DatiAbusoHome;
import it.soft.dao.DatiAlloggioHome;
import it.soft.dao.DatiFabbricatiHome;
import it.soft.dao.DatiTerreniHome;
import it.soft.dao.DestinazioneUsoHome;
import it.soft.dao.EpocaAbusoHome;
import it.soft.dao.LeggiCondonoHome;
import it.soft.domain.DatiAlloggio;
import it.soft.domain.DatiFabbricati;
import it.soft.domain.DatiTerreni;
import it.soft.domain.DocumentiAbuso;
import it.soft.domain.RelSoggettoAbuso;
import it.soft.domain.TipologiaDestinazioneUso;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiPraticaPojo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

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
	@Autowired
	LeggiCondonoHome leggiCondonoHome;

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
		List<DocumentiAbuso> daocumentiDB = abusoService
				.findAllDocById(idabuso);

		createPage1(document, praticaDB, abusoDB, listaSoggetti, alloggi);
		createPage2(document, daocumentiDB);
		createPage3(document);
		createPage4(document);
		System.out.println("create successfully");

		// scriviLocalTest(document);

		return document;

	}

	public void createPage4(XWPFDocument document) {
		document.createParagraph().createRun().addBreak(BreakType.PAGE);
		InputStream is = WordService.class
				.getResourceAsStream("ultima pag statica.jpg");
		try {
			document.createParagraph()
					.createRun()
					.addPicture(is, Document.PICTURE_TYPE_JPEG,
							"ultima pag statica.jpg", Units.toEMU(500),
							Units.toEMU(700));
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void createPage3(XWPFDocument document) {

		document.createParagraph().createRun().addBreak(BreakType.PAGE);
		addTextBoldBreakCenter(document.createParagraph().createRun(),
				"2) ATTESTAZIONI DI VERSAMENTO");
		// OBLAZIONE
		XWPFParagraph parag = document.createParagraph();
		addTextBoldBreak(parag.createRun(), "OBLAZIONE:");
		addTextBold(parag.createRun(), "Autodetermina ");
		addTab(parag, 7);
		addTextBoldBreak(parag.createRun(), "= €");

		addTextBold(parag.createRun(), "Importo versato ");
		addTab(parag, 7);
		addTextBoldBreak(parag.createRun(), "= €");
		addTextBold(parag.createRun(), "Importo calcolato ");
		addTab(parag, 7);
		addTextBoldBreak(parag.createRun(), "= €");
		addTextBold(parag.createRun(),
				"Importo da versare a saldo comprensivo degli interessi dovuti ");
		addTab(parag, 1);
		addTextBoldBreak(parag.createRun(), "= €");
		parag.createRun().addBreak();
		// OBLAZIONE REGIONALE
		addTextBoldBreak(parag.createRun(), "OBLAZIONE REGIONALE:");
		addTextBold(parag.createRun(), "Autodetermina ");
		addTab(parag, 7);
		addTextBoldBreak(parag.createRun(), "= €");
		addTextBold(parag.createRun(), "Importo versato ");
		addTab(parag, 7);
		addTextBoldBreak(parag.createRun(), "= €");
		addTextBold(parag.createRun(), "Importo calcolato ");
		addTab(parag, 7);
		addTextBoldBreak(parag.createRun(), "= €");
		addTextBold(parag.createRun(),
				"Importo da versare a saldo comprensivo degli interessi dovuti ");
		addTab(parag, 1);
		addTextBoldBreak(parag.createRun(), "= €");
		parag.createRun().addBreak();
		// ONERI CONCESSORI
		addTextBoldBreak(parag.createRun(), "ONERI CONCESSORI:");
		addTextBold(parag.createRun(), "Importo versato ");
		addTab(parag, 7);
		addTextBoldBreak(parag.createRun(), "= €");
		addTextBold(parag.createRun(), "Importo calcolato ");
		addTab(parag, 7);
		addTextBoldBreak(parag.createRun(), "= €");
		addTextBold(parag.createRun(), "Importo da versare a saldo ");
		addTab(parag, 6);
		addTextBoldBreak(parag.createRun(), "= €");
		parag.createRun().addBreak();
		// DIRITTI DI SEGRETERIA
		addTextBoldBreak(parag.createRun(), "DIRITTI DI SEGRETERIA:");
		addTextBold(parag.createRun(), "Diritti di istruttoria ");
		addTab(parag, 7);
		addTextBoldBreak(parag.createRun(), "= €");
		addTextBold(parag.createRun(),
				"Diritti rilascio permesso di costruire ");
		addTab(parag, 5);
		addTextBoldBreak(parag.createRun(), "= €");
		addTextBold(parag.createRun(),
				"Diritti istruttoria pareri sui vincoli ");
		addTab(parag, 5);
		addTextBoldBreak(parag.createRun(), "= €");
		addTextBold(parag.createRun(), "Agibilità ");
		addTab(parag, 8);
		addTextBoldBreak(parag.createRun(), "= €");
		addTextBold(parag.createRun(), "Importo da versare ");
		addTab(parag, 7);
		addTextBoldBreak(parag.createRun(), "= €");
		parag.createRun().addBreak();
		// TOTALE VERSAMENTI E RIEPILOGO
		addTextBoldBreak(parag.createRun(),
				"Totale da versare al comune di Palombara Sabina ");
		addTextSimple(
				parag.createRun(),
				"Il versamento va effettuato a favore del Comune di Palombara Sabina può essere effettuato con le seguenti modalità:");
		parag.createRun().addBreak();
		addTextSimple(parag.createRun(), "1) UFFICIO POSTALE sul CCP n. ");
		addTextBold(parag.createRun(), "1020723423 ");
		addTextSimple(parag.createRun(), "IBAN: ");
		addTextBold(parag.createRun(), "  IT 64 Z 07601 03200 001020723423 ");
		addTextSimpleBreak(parag.createRun(),
				"intestato a:  COMUNE DI PALOMBARA SABINA – ONERI CONCESS. IN SANATORIA");
		document.createParagraph().createRun().addBreak();
		addTextSimpleBreak(
				document.createParagraph().createRun(),
				"I versamenti delle somme dovute a saldo al cui causale dovrà riportare il numero di pratica e il numero di protocollo di cui al punto A della presente nota, dovranno essere effettuati entro e non oltre 60 gg. dal ricevimento della presente.");
		addTextBold(
				parag.createRun(),
				"Importo da versare al Ministero LLPP a saldo comprensivo degli interessi su conto C/C255000 ");
		addTextBoldBreak(parag.createRun(), "in €");
		document.createParagraph().createRun().addBreak();

		XWPFParagraph p = document.createParagraph();
		addTextSimple(
				p.createRun(),
				"La circolare n. 1/DPF del 16.1.2004 del Ministero dell'Economia e delle Finanze ha stabilito che le somme dovute dovranno essere versate mediante il bollettino di conto corrente postale a tre sezioni (mod. CH8 ter) sul ");
		addTextBold(p.createRun(),
				"conto corrente postale n. 255000 intestato a Poste Italiane S.p.A.,");
		addTextSimpleBreak(p.createRun(), " indicando:");
		addTextSimpleBreak(p.createRun(), "•	l'importo;");
		addTextSimpleBreak(p.createRun(), "•	gli estremi identificativi e l'indirizzo del richiedente;");
		addTextSimpleBreak(p.createRun(), "•	nonché nello spazio riservato alla causale: ");
		addTextSimpleBreak(p.createRun(), "		1) il comune dove è ubicato l'immobile;");
		addTextSimpleBreak(p.createRun(), "		2) il numero progressivo indicato nella domanda relativa al versamento: il richiedente dovrà infatti  numerare” con numeri progressivi le domande di sanatoria presentate allo stesso comune e riportare il numero nella causale del versamento per consentirne l'abbinamento;");
		addTextSimpleBreak(p.createRun(), "		3)  il codice fiscale del richiedente.");
		// NOTE INFORMATIVE
		p.createRun().addBreak();
		addTextBold(p.createRun(), "NOTA BENE: ");
		addTextBold(
				p.createRun(),
				"In difetto si procederà a norma di legge con un provvedimento di diniego della concessione in sanatoria e conseguente rimozione / demolizione dell'abuso con costi a carico del richiedente ovvero con l'acquisizione del bene al patrimonio dell'amministrazione.");
	}

	@SuppressWarnings("deprecation")
	private void addTextBoldBreakCenter(XWPFRun run, String testo) {
		run.getParagraph().setAlignment(ParagraphAlignment.CENTER);
		run.setText(testo);
		run.setFontSize(16);
		run.setBold(true);
		// run.addBreak();
	}

	// private void scriviLocalTest(XWPFDocument document)
	// throws FileNotFoundException, IOException {
	// // Write the Document in file system
	// FileOutputStream out = new FileOutputStream(new File(
	// "C:/createparagraph.docx"));
	// document.write(out);
	// out.close();
	// System.out.println("create successfully");
	// }

	public XWPFDocument createPage1(XWPFDocument document,
			DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB,
			List<RelSoggettoAbuso> listaSoggetti, List<DatiAlloggio> alloggi) {
		// create header
		createHeader(document.createParagraph());
		// create Protocollo e lista soggetti
		createListaSoggetti(document.createParagraph(), praticaDB, abusoDB,
				listaSoggetti);
		creaOggetto(document.createParagraph(), praticaDB, abusoDB);
		addTextBoldBreakCenter(document.createParagraph().createRun(),
				"A) AVVIO ISTRUTTORIA DELLA PRATICA");
		creaPargraphADB(document, praticaDB, abusoDB);

		addTextBoldBreakCenter(document.createParagraph().createRun(),
				"B) DESCRIZIONE DELL'ABUSO");
		XWPFParagraph paragAbuso = document.createParagraph();
		addTextBoldBreak(paragAbuso.createRun(), "Ubicazione dell'abuso: ");
		addTextSimple(paragAbuso.createRun(), "Località "
				+ abusoDB.getLocalizzazione().getComune() + ", Indirizzo "
				+ abusoDB.getLocalizzazione().getIndirizzo());

		XWPFParagraph paragAbusoDesc = document.createParagraph();
		addTextBoldBreak(paragAbusoDesc.createRun(), "Descrizione abuso: ");
		addTextSimple(paragAbusoDesc.createRun(), abusoDB.getDescrizione());

		addTextBold(document.createParagraph().createRun(), "Dati Catastali:");
		XWPFTable tableHeader = createHeaderTableDatiCatastali(document);
		for (DatiAlloggio datiAlloggio : alloggi) {
			creaParagraphDatiCatastaliDB(datiAlloggio.getIddatiAlloggio(),
					document, tableHeader);
		}
		document.createParagraph().createRun().addBreak();
		XWPFParagraph paragDatiTecnici = document.createParagraph();
		addTextBoldBreak(paragDatiTecnici.createRun(), "Dati Tecnici: ");
		creaParagraphDatiTecniciTable(paragDatiTecnici, abusoDB, praticaDB);
		addTextSimple(
				document.createParagraph().createRun(),
				"Dall'istruttoria preliminare dell'istanza in oggetto, ai fini di poter completare le attività di disamina tecnico-amministrativo e procedere con il rilascio della Concessione in sanatoria la stessa, dovrà essere integrata con i documenti previsti dalle normative vigenti di cui al punto 1 e dalle attestazioni di versamento di cui al punto 2.");
		return document;
	}

	private void creaParagraphDatiTecniciTable(
			XWPFParagraph paragraphDatiTecniciTable, DatiAbusoPojo abusoDB,
			DatiPraticaPojo praticaDB) {
		String testo1 = "";
		addTextSimple(paragraphDatiTecniciTable.createRun(),
				"Destinazione d'uso: ");
		addTab(paragraphDatiTecniciTable, 2);
		if (!StringUtils.isEmptyOrWhitespaceOnly(abusoDB.getDestinazioneUso())) {
			TipologiaDestinazioneUso s = destinazioneUsoHome.findById(Integer
					.valueOf(abusoDB.getDestinazioneUso()));
			testo1.concat(s.getDescrizioneTipologia());
		}
		addTextSimpleBreak(paragraphDatiTecniciTable.createRun(), testo1);
		addTextSimple(paragraphDatiTecniciTable.createRun(),
				"Superficie Utile Mq: ");
		addTab(paragraphDatiTecniciTable, 2);
		addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
				abusoDB.getSuperficeUtile());
		addTextSimple(paragraphDatiTecniciTable.createRun(),
				"Non resid./accessori Mq: " + abusoDB.getSuperficeUtile());
		paragraphDatiTecniciTable.createRun().addTab();
		addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
				abusoDB.getSuperficeUtile());
		addTextSimple(paragraphDatiTecniciTable.createRun(), "Mc VxP: "
				+ abusoDB.getSuperficeTotale());
		addTab(paragraphDatiTecniciTable, 3);
		addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
				abusoDB.getSuperficeUtile());
		addTextSimple(paragraphDatiTecniciTable.createRun(), "Tipologia: ");
		addTab(paragraphDatiTecniciTable, 3);
		addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
				abusoDB.getTipologiaAbuso());
		addTextSimple(paragraphDatiTecniciTable.createRun(), "Epoca d'abuso: ");
		addTab(paragraphDatiTecniciTable, 3);
		addTextSimpleBreak(
				paragraphDatiTecniciTable.createRun(),
				epocaAbusoHome.findById(
						Integer.valueOf(abusoDB.getEpocaAbuso())).toString());
		addTextSimple(paragraphDatiTecniciTable.createRun(),
				"Data ultimazione lavori: ");
		paragraphDatiTecniciTable.createRun().addTab();
		addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
				abusoDB.getDataUltimazioneLavori());
	}

	private XWPFTable createHeaderTableDatiCatastali(XWPFDocument document) {
		// create table
		XWPFTable table = document.createTable();
		// create first row
		XWPFTableRow tableRowOne = table.getRow(0);
		addTableCellCenter(tableRowOne.getCell(0), "SEZIONE");
		addTableCellCenter(tableRowOne.addNewTableCell(), "FOGLIO");
		addTableCellCenter(tableRowOne.addNewTableCell(), "PARTICELLA");
		addTableCellCenter(tableRowOne.addNewTableCell(), "SUBALTERNO");
		addTableCellCenter(tableRowOne.addNewTableCell(), "ZONA");

		adjustTableWidth(table);
		return table;
	}

	private void adjustTableWidth(XWPFTable table) {
		for (int x = 0; x < table.getNumberOfRows(); x++) {
			XWPFTableRow row = table.getRow(x);
			int numberOfCell = row.getTableCells().size();
			for (int y = 0; y < numberOfCell; y++) {
				XWPFTableCell cell = row.getCell(y);

				cell.getCTTc().addNewTcPr().addNewTcW()
						.setW(BigInteger.valueOf(2000));
			}
		}
	}

	private void creaParagraphDatiCatastaliDB(BigDecimal idalloggio,
			XWPFDocument document, XWPFTable table) {

		List<DatiFabbricati> listFabbric = datiFabbricatiHome
				.findAll(idalloggio.intValue());
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

	private XWPFParagraph addTableCellCenter(XWPFTableCell tableCell,
			String testo) {
		XWPFParagraph parCell = tableCell.addParagraph();
		tableCell.setVerticalAlignment(XWPFVertAlign.CENTER);
		parCell.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = parCell.createRun();
		run.setText(testo);
		tableCell.setParagraph(parCell);
		tableCell.removeParagraph(0);
		return parCell;
	}

	private void addTextBold(XWPFRun run, String testo) {
		run.setText(testo);
		run.setBold(true);
	}

	private void addTextBoldBreak(XWPFRun run, String testo) {
		run.setText(testo);
		run.setBold(true);
		run.addBreak();
	}

	private void addTextSimple(XWPFRun run, String testo) {
		run.setText(testo);
	}

	private void addTextSimpleBreak(XWPFRun run, String testo) {
		run.setText(testo);
		run.addBreak();
	}

	private void addTab(XWPFParagraph parag, int numeroTab) {
		for (int i = 0; i < numeroTab; i++) {
			parag.createRun().addTab();
		}
	}

	private void creaPargraphADB(XWPFDocument document,
			DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB) {

		XWPFTable table = document.createTable(1, 6);

		table.getRow(0).getCtRow().addNewTrPr().addNewTrHeight()
				.setVal(new BigInteger("100"));

		addTableCellCenter(table.getRow(0).getCell(0), "Numero interno");
		table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
				.setW(BigInteger.valueOf(4000));

		addTableCellCenter(table.getRow(0).getCell(1),
				"00" + praticaDB.getNumeroPratica());
		table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
				.setW(BigInteger.valueOf(1500));

		addTableCellCenter(table.getRow(0).getCell(2), "Sottonumero");
		table.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
				.setW(BigInteger.valueOf(4000));

		addTableCellCenter(table.getRow(0).getCell(3), abusoDB.getProgressivo());
		table.getRow(0).getCell(3).getCTTc().addNewTcPr().addNewTcW()
				.setW(BigInteger.valueOf(1000));

		addTableCellCenter(table.getRow(0).getCell(4), "Numero Protocollo");
		table.getRow(0).getCell(4).getCTTc().addNewTcPr().addNewTcW()
				.setW(BigInteger.valueOf(4500));

		addTableCellCenter(table.getRow(0).getCell(5),
				praticaDB.getNumeroProtocollo());
		table.getRow(0).getCell(5).getCTTc().addNewTcPr().addNewTcW()
				.setW(BigInteger.valueOf(1000));
	}

	private void creaOggetto(XWPFParagraph paragraphggetto,
			DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB) {
		addTextBold(paragraphggetto.createRun(), "Oggetto: ");
		String oggetto = "Avvio dell'attività di istruttoria della Istanza di Sanatoria Edilizia richiesta ai sensi della legge n."
				.concat(praticaDB.getLeggeCondono() != null ? leggiCondonoHome
						.findById(Integer.valueOf(praticaDB.getLeggeCondono()))
						.getLeggeNumero() : " ");
		oggetto += " presentata il " + praticaDB.getDataDomanda();
		oggetto += " con protocollo n. " + praticaDB.getNumeroProtocollo();
		oggetto += " del " + praticaDB.getDataProtocollo();
		oggetto += " sott " + abusoDB.getProgressivo();
		oggetto += " N° interno " + praticaDB.getNumeroPratica();
		oggetto += " richiesta da " + praticaDB.getCognome() + " "
				+ praticaDB.getNome();
		oggetto += " residente in " + praticaDB.getIndirizzo();

		XWPFRun run = paragraphggetto.createRun();
		run.setText(oggetto);
	}

	private void createListaSoggetti(XWPFParagraph paragraph,
			DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB,
			List<RelSoggettoAbuso> listaSoggetti) {
		addTab(paragraph, 8);
		String testo1 = "Protocollo Numero " + praticaDB.getNumeroProtocollo()
				+ " del " + praticaDB.getDataProtocollo();
		addTextSimpleBreak(paragraph.createRun(), testo1);
		paragraph.createRun().addBreak();
		for (RelSoggettoAbuso relSoggettoAbuso : listaSoggetti) {
			addTab(paragraph, 8);
			addTextSimpleBreak(paragraph.createRun(),
					"Cognome e Nome: " + relSoggettoAbuso.getCognome() + " "
							+ relSoggettoAbuso.getNome());
			addTab(paragraph, 8);
			addTextSimpleBreak(paragraph.createRun(), "Indirizzo: "
					+ relSoggettoAbuso.getIndirizzo());
			addTab(paragraph, 8);
			addTextSimpleBreak(paragraph.createRun(), "Cap: "
					+ relSoggettoAbuso.getCap());
			addTab(paragraph, 8);
			addTextSimpleBreak(paragraph.createRun(), "Comune: "
					+ relSoggettoAbuso.getComuneResidenza());
			paragraph.createRun().addBreak();
		}
	}

	private void createHeader(XWPFParagraph paragraph) {
		InputStream is = WordService.class
				.getResourceAsStream("logoCondono.png");
		try {
			paragraph.createRun().addPicture(is, Document.PICTURE_TYPE_PNG,
					"logoCondono.png", Units.toEMU(375), Units.toEMU(95));
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public XWPFDocument createPage2(XWPFDocument document,
			List<DocumentiAbuso> daocumentiDB) {
		document.createParagraph().createRun().addBreak(BreakType.PAGE);
		addTextBoldBreakCenter(document.createParagraph().createRun(),
				"1) DOCUMENTI DA INTEGRARE");
		creaPargraphDOCTable(document.createParagraph(), daocumentiDB);
		creaPargraphINOFDOC(document.createParagraph());
		return document;
	}

	private void creaPargraphINOFDOC(XWPFParagraph paragraphInfoDoc) {

		addTextSimple(paragraphInfoDoc.createRun(),
				"La documentazione richiesta che dovrà riportare ");
		addTextBold(paragraphInfoDoc.createRun(),
				"il numero di pratica e il numero di protocollo di cui al punto A");
		addTextSimple(paragraphInfoDoc.createRun(),
				" della presente nota, dovrà pervenire ");
		addTextBold(paragraphInfoDoc.createRun(),
				"entro e non oltre 90 gg. dal ricevimento della presente. ");
		addTextSimpleBreak(
				paragraphInfoDoc.createRun(),
				"Si fa presente che tutta la modulistica necessaria è reperibile presso il sito web del all'indirizzo www.comune.palombarasabina.rm.it");
		paragraphInfoDoc.createRun().addBreak();
		addTextBoldBreak(paragraphInfoDoc.createRun(), "NOTA BENE: ");
		paragraphInfoDoc.createRun().addBreak();
		addTextBoldBreak(
				paragraphInfoDoc.createRun(),
				"In difetto si procederà a norma di legge con un provvedimento di diniego della concessione in sanatoria e conseguente rimozione/demolizione dell'abuso con costi a carico del richiedente ovvero dell'acquisizione del bene al patrimonio dell'amministrazione.");

	}

	private void creaPargraphDOCTable(XWPFParagraph paragraphDocTable,
			List<DocumentiAbuso> daocumentiDB) {
		XWPFRun paragraphAOneRunOne = paragraphDocTable.createRun();
		for (DocumentiAbuso documentiAbuso : daocumentiDB) {
			paragraphAOneRunOne.addTab();
			addTextSimpleBreak(paragraphAOneRunOne, " - ".concat(documentiAbuso
					.getIdTipoDocumento().getDescrizione()));
		}

		paragraphAOneRunOne.addBreak();
		paragraphAOneRunOne.addBreak();
		paragraphAOneRunOne.addBreak();
		paragraphAOneRunOne.addBreak();
		paragraphAOneRunOne.addBreak();
		paragraphAOneRunOne.addBreak();

	}
}
