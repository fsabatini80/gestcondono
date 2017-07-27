package it.soft.service;

import it.soft.dao.DatiAbusoHome;
import it.soft.dao.DatiAlloggioHome;
import it.soft.dao.DatiFabbricatiHome;
import it.soft.dao.DatiTerreniHome;
import it.soft.dao.DestinazioneUsoHome;
import it.soft.dao.EpocaAbusoHome;
import it.soft.dao.LeggiCondonoHome;
import it.soft.dao.TipologiaAbusoHome;
import it.soft.domain.DatiAlloggio;
import it.soft.domain.DatiFabbricati;
import it.soft.domain.DatiSollecito;
import it.soft.domain.DatiTerreni;
import it.soft.domain.Datiabuso;
import it.soft.domain.DocumentiAbuso;
import it.soft.domain.EpocaAbuso;
import it.soft.domain.RelSoggettoAbuso;
import it.soft.domain.TipologiaAbuso;
import it.soft.domain.TipologiaDestinazioneUso;
import it.soft.util.Constants;
import it.soft.util.Converter;
import it.soft.util.UtilityWord;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiPraticaPojo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
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
    @Autowired
    DatiVersamentiService datiVersamentiService;
    @Autowired
    TipologiaAbusoHome tipologiaAbusoHome;
    @Autowired
    DatiSollecitiService datiSollecitiService;

    @Autowired
    ServletContext context;

    private static Integer CELL_WIDTH_DESC = 9900;
    private static Integer CELL_WIDTH_VALUTA = 500;
    private static Integer CELL_WIDTH_IMPORTO = 1500;
    private static Integer CELL_WIDTH_ALL = 9900;
    private static Integer FONT_SIZE_SUBTITLE = 9;

    public XWPFDocument createDoc(XWPFDocument document,
	    DatiPraticaService praticaService, DatiAbusoService abusoService,
	    String idpratica, String idabuso, String progressivo, Date data_stampa)
	    throws Exception {

	DatiPraticaPojo praticaDB = praticaService.findById(idpratica);
	DatiAbusoPojo abusoDB = abusoService.findById(idabuso);
	Double dataAbuso = Converter.dateToDouble(praticaDB.getDataDomanda());

	List<RelSoggettoAbuso> listaSoggetti = abusoService
		.findAllSoggettiById(idabuso);
	List<DatiAlloggio> alloggi = datiAlloggioHome
		.findByIdAbuso(datiAbusoHome.findById(BigDecimal
			.valueOf(Integer.valueOf(idabuso))));
	List<DocumentiAbuso> daocumentiDB = abusoService.findAllDocValid(
		idabuso, false);
	// .findAllDocById(idabuso);

	Double importoOblazioneAut = datiVersamentiService
		.getAutodeterminaOblazione(idabuso, progressivo);
	importoOblazioneAut = Converter.round(importoOblazioneAut, 2);

	Double importoVersatoObl = datiVersamentiService
		.getImportoVersatoOblazione(idpratica, progressivo);
	importoVersatoObl = Converter.round(importoVersatoObl, 2);

	EpocaAbuso epocaAbuso = epocaAbusoHome.findById(Integer
		.parseInt(abusoDB.getEpocaAbuso()));

	TipologiaAbuso tipologiaAbuso = tipologiaAbusoHome.findById(Integer
		.valueOf(abusoDB.getTipologiaAbuso()));
	List<Datiabuso> abusi = abusoService.findAll(idpratica);
	boolean applicaDefault = false;
	if (abusi.size() == 1) {
	    applicaDefault = true;
	}
	Double importoCalcolato = datiVersamentiService.getOblazioneCalcolata(
		applicaDefault, tipologiaAbuso,
		Converter.dateToDouble(epocaAbuso.getEpocaDa()),
		praticaDB.getLeggeCondono(), idabuso,
		abusoDB.getDestinazioneUso());
	importoCalcolato = Converter.round(importoCalcolato, 2);

	Double oblazioneDovuta = datiVersamentiService.getOblazioneDovuta(
		importoCalcolato, abusoDB, dataAbuso,
		praticaDB.getLeggeCondono(), data_stampa);
	oblazioneDovuta = Converter.round(oblazioneDovuta, 2);

	createPage1(document, praticaDB, abusoDB, listaSoggetti, alloggi,
		tipologiaAbuso);
	createPage2(document, daocumentiDB);

	Double oneriConcessVersato = datiVersamentiService.getOneriVersati(
		idpratica, progressivo);
	oneriConcessVersato = Converter.round(oneriConcessVersato, 2);
	Double oneriConcessCalcolato = datiVersamentiService.getOneriCalcolati(
		tipologiaAbuso, abusoDB, praticaDB, idabuso,
		abusoDB.getDestinazioneUso());
	Double oneriConcessSaldo = oneriConcessCalcolato - oneriConcessVersato;
	if (Constants.ID_LEGGE_724_94.equals(praticaDB.getLeggeCondono())) {
	    oneriConcessSaldo = datiVersamentiService
		    .getOneriConcessSaldoLegge2(oneriConcessVersato,
			    oneriConcessCalcolato, abusoDB, praticaDB, data_stampa);
	}
	if (Constants.ID_LEGGE_326_03.equals(praticaDB.getLeggeCondono())) {
	    oneriConcessSaldo = datiVersamentiService
		    .getOneriConcessSaldoLegge3(oneriConcessVersato,
			    oneriConcessCalcolato, abusoDB, praticaDB, data_stampa);
	}
	if (oneriConcessSaldo < 0)
	    oneriConcessSaldo = new Double(0.0);
	oneriConcessSaldo = Converter.round(oneriConcessSaldo, 2);
	Double dirittiIstrut = new Double(50);
	Double dirittiRilPerm = datiVersamentiService
		.getDirittiRilPerm(abusoDB);
	Double agibilita = new Double(0);
	Double dirittiPareri = new Double(0);
	Double oneriAutodeterminata = abusoDB.getAutodeterminataOneri();

	createPage3(document, importoOblazioneAut, importoCalcolato,
		importoVersatoObl, oblazioneDovuta, oneriConcessVersato,
		oneriConcessCalcolato, oneriConcessSaldo, dirittiIstrut,
		dirittiRilPerm, dirittiPareri, agibilita, oneriAutodeterminata,
		praticaDB.getLeggeCondono(), idabuso, progressivo, idpratica,
		dataAbuso, data_stampa);
	createPage4(document);

	createFooter(document, praticaDB, abusoDB);

	System.out.println("create successfully");

	// scriviLocalTest(document);

	return document;

    }

    private void createFooter(XWPFDocument document, DatiPraticaPojo praticaDB,
	    DatiAbusoPojo abusoDB) {
	try {
	    String testo1 = "Numero Interno " + praticaDB.getNumeroPratica()
		    + ", Sottonumero " + abusoDB.getProgressivo()
		    + ", Numero Protocollo " + praticaDB.getNumeroProtocollo()
		    + "                               Pag.";
	    // create footer
	    XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(
		    document, document.getDocument().getBody().addNewSectPr());
	    // document.getHeaderFooterPolicy();
	    CTP ctpFooter = CTP.Factory.newInstance();
	    CTR ctr = ctpFooter.addNewR();
	    ctr.addNewContinuationSeparator();
	    CTText textt = ctr.addNewT();
	    textt.setStringValue(testo1);
	    XWPFParagraph[] parsFooter;

	    // add style (s.th.)
	    CTPPr ctppr = ctpFooter.addNewPPr();
	    CTString pst = ctppr.addNewPStyle();
	    pst.setVal("style21");
	    CTJc ctjc = ctppr.addNewJc();
	    ctjc.setVal(STJc.RIGHT);
	    ctppr.addNewRPr();

	    // add everything from the footerXXX.xml you need
	    // CTR ctr = ctpFooter.addNewR();
	    ctr.addNewRPr();
	    CTFldChar fch = ctr.addNewFldChar();
	    fch.setFldCharType(STFldCharType.BEGIN);

	    ctr = ctpFooter.addNewR();
	    ctr.addNewInstrText().setStringValue(" PAGE ");

	    ctpFooter.addNewR().addNewFldChar()
		    .setFldCharType(STFldCharType.SEPARATE);
	    ctpFooter.addNewR().addNewT().setStringValue("1");

	    ctpFooter.addNewR().addNewFldChar()
		    .setFldCharType(STFldCharType.END);

	    XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter,
		    document);

	    parsFooter = new XWPFParagraph[1];

	    parsFooter[0] = footerParagraph;

	    policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void createPage4(XWPFDocument document) {
	document.createParagraph().createRun().addBreak(BreakType.PAGE);
	CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
	CTPageMar pageMar = sectPr.addNewPgMar();
	pageMar.setLeft(BigInteger.valueOf(720L));
	pageMar.setTop(BigInteger.valueOf(720L));
	pageMar.setRight(BigInteger.valueOf(720L));
	pageMar.setBottom(BigInteger.valueOf(720L));
	// InputStream is = context
	// .getResourceAsStream("/WEB-INF/ultima pag statica.png");

	// document.createParagraph()
	// .createRun()
	// .addPicture(is, Document.PICTURE_TYPE_PNG,
	// "ultima pag statica.png", Units.toEMU(550),
	// Units.toEMU(650));

	UtilityWord.addText(document.createParagraph().createRun(),
		"MODALITA’ E CRITERI DI CALCOLO APPLICATI", true,
		ParagraphAlignment.CENTER, true, false, FONT_SIZE_SUBTITLE);
	UtilityWord
		.addText(
			document.createParagraph().createRun(),
			"ONERI CONCESSORI = COSTO DI COSTRUZIONE + ONERI DI URBANIZZAZIONE COSTO DI COSTRUZIONE = [(R1+R2+R3)/100]*[(260,81+M)*MQ]",
			true, ParagraphAlignment.CENTER, false, false,
			FONT_SIZE_SUBTITLE);

	XWPFTable table1 = document.createTable(3, 3);
	table1.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));
	UtilityWord.spanCellsAcrossCell(table1, 0, 0, 3);
	UtilityWord.addTableCellCenter(table1.getRow(0).getCell(0), "R1", true,
		ParagraphAlignment.CENTER);
	UtilityWord.addTableCell(table1.getRow(0).getCell(1), "A", true,
		ParagraphAlignment.CENTER, CELL_WIDTH_DESC, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table1.getRow(0).getCell(2), "2,25", true,
		ParagraphAlignment.RIGHT, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table1.getRow(1).getCell(1), "B - C2", true,
		ParagraphAlignment.CENTER, CELL_WIDTH_DESC, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table1.getRow(1).getCell(2), "2,00", true,
		ParagraphAlignment.RIGHT, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table1.getRow(2).getCell(1), "C1 - E", true,
		ParagraphAlignment.CENTER, CELL_WIDTH_DESC, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table1.getRow(2).getCell(2), "1,25", true,
		ParagraphAlignment.RIGHT, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);

	XWPFTable table1_ = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table1_.getRow(0).getCell(0), "", true,
		ParagraphAlignment.LEFT);
	table1_.getCTTbl().getTblPr().unsetTblBorders();

	XWPFTable table2 = document.createTable(5, 3);
	table2.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));
	UtilityWord.spanCellsAcrossCell(table2, 0, 0, 5);
	UtilityWord.addTableCellCenter(table2.getRow(0).getCell(0), "R2", true,
		ParagraphAlignment.CENTER);
	UtilityWord.addTableCell(table2.getRow(0).getCell(1),
		"Unifamiliari singole", true, ParagraphAlignment.CENTER,
		CELL_WIDTH_DESC, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table2.getRow(0).getCell(2), "2,25", true,
		ParagraphAlignment.RIGHT, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table2.getRow(1).getCell(1),
		"Unifamiliari aggregate fino a 2 piani max 4 alloggi", true,
		ParagraphAlignment.CENTER, CELL_WIDTH_DESC, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table2.getRow(1).getCell(2), "1,75", true,
		ParagraphAlignment.RIGHT, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table2.getRow(2).getCell(1),
		"familiari aggregate fino a 2 piani a schiera", true,
		ParagraphAlignment.CENTER, CELL_WIDTH_DESC, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table2.getRow(2).getCell(2), "1,50", true,
		ParagraphAlignment.RIGHT, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table2.getRow(3).getCell(1),
		"Plurifamiliari fino a 3 piani abitabili", true,
		ParagraphAlignment.CENTER, CELL_WIDTH_DESC, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table2.getRow(3).getCell(2), "1,25", true,
		ParagraphAlignment.RIGHT, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table2.getRow(4).getCell(1),
		"Oltre 3 piani abitabili", true, ParagraphAlignment.CENTER,
		CELL_WIDTH_DESC, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table2.getRow(4).getCell(2), "1,75", true,
		ParagraphAlignment.RIGHT, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);

	XWPFTable table2_ = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table2_.getRow(0).getCell(0), "", true,
		ParagraphAlignment.LEFT);
	table2_.getCTTbl().getTblPr().unsetTblBorders();

	XWPFTable table3 = document.createTable(3, 3);
	table3.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));
	UtilityWord.spanCellsAcrossCell(table3, 0, 0, 3);
	UtilityWord.addTableCellCenter(table3.getRow(0).getCell(0), "R3", true,
		ParagraphAlignment.CENTER);
	UtilityWord.addTableCell(table3.getRow(0).getCell(1),
		"Classi I, II, III, IV", true, ParagraphAlignment.CENTER,
		CELL_WIDTH_DESC, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table3.getRow(0).getCell(2), "2,25", true,
		ParagraphAlignment.RIGHT, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table3.getRow(1).getCell(1),
		"Classi V, VI, VII, VIII", true, ParagraphAlignment.CENTER,
		CELL_WIDTH_DESC, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table3.getRow(1).getCell(2), "2,00", true,
		ParagraphAlignment.RIGHT, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table3.getRow(2).getCell(1),
		"Classi IX, X, XI", true, ParagraphAlignment.CENTER,
		CELL_WIDTH_DESC, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table3.getRow(2).getCell(2), "1,25", true,
		ParagraphAlignment.RIGHT, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);

	XWPFRun run = document.createParagraph().createRun();
	UtilityWord
		.addText(
			run,
			"M= maggiorazione % ricavata secondo DM n. 801 del 10 Maggio 1977.",
			true, ParagraphAlignment.LEFT, true, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord
		.addText(
			run,
			"260,81 €/mq è il costo base secondo quanto disposto dalla Delibera del Consiglio Comunale n. 53/2001.",
			true, ParagraphAlignment.LEFT, true, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord
		.addText(
			run,
			"ONERI DI URBANIZZAZIONE = MC*COEFFICIENTE COME DA DELIBERA 53/2001.",
			true, ParagraphAlignment.LEFT, true, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord
		.addText(
			run,
			"N.B. Gli oneri sono soggetti a riduzioni, maggiorazioni, interessi moratori e/o legali secondo quanto stabilito dalle Leggi Regionali n. 76/1985 e n. 58/1996.",
			true, ParagraphAlignment.LEFT, true, false,
			FONT_SIZE_SUBTITLE);

	UtilityWord.addTextBoldUnderlineBreak(document.createParagraph()
		.createRun(), "NOTA BENE:", false);

	XWPFRun run1 = document.createParagraph().createRun();
	UtilityWord
		.addText(
			run1,
			"- Al momento del ritiro della concessione dovrà essere presentata una Marca da bollo da € 16,00 da apporre sulla stessa N.B. Gli oneri sono soggetti a riduzioni, maggiorazioni, interessi moratori e/o legali secondo quanto stabilito dalle Leggi Regionali n. 76/1985 e n. 58/1996.",
			false, ParagraphAlignment.LEFT, true, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord
		.addText(
			run1,
			"- Qualora gli importi siano stati versati oltre le scadenze previste, all’atto del ritiro della Concessione saranno applicati gli interessi legali previsti dalla normativa vigente.",
			false, ParagraphAlignment.LEFT, true, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord
		.addText(
			run1,
			"- Eventuali calcoli che potranno risultare difformi alla luce della nuova documentazione presentata, ove ricorrano i termini di legge, saranno rielaborati direttamente allo sportello in sede di rilascio della Concessione in sanatoria.",
			false, ParagraphAlignment.LEFT, true, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord
		.addText(
			run1,
			"- Previa presentazione della documentazione necessaria, all’atto del ritiro della Concessione, sarà possibile ottenere il certificato di Agibilità. La modulistica necessaria è reperibile sul sito web del Comune di Palombara Sabina all’indirizzo www.comune.palombarasabina.rm.it .",
			false, ParagraphAlignment.LEFT, true, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord
		.addText(
			run1,
			"- Qualsiasi ulteriore comunicazione o inoltro di atti relativi all'istanza di cui in oggetto dovranno tassativamente riportare il numero di pratica ed il numero di protocollo di cui al punto A. Diversamente tali integrazioni non verranno prese in esame.",
			false, ParagraphAlignment.LEFT, true, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord
		.addText(
			run1,
			"La presente è da intendersi anche come Avvio del Procedimento Amministrativo ai sensi degli artt. 5, 7 e 8 della Legge 07/08/1990 n° 24 e successive modifiche ed integrazioni.",
			false, ParagraphAlignment.LEFT, true, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord
		.addText(
			run1,
			"Responsabile del Procedimento è stato nominato : Arch. Paolo Caracciolo",
			false, ParagraphAlignment.LEFT, false, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord
		.addText(
			document.createParagraph().createRun(),
			"L'Unità organizzativa presso la quale sono depositati gli Atti relativi al presente Procedimento è la seguente: Settore VII –Condono Edilizio, Piazza Vittorio Veneto 12 Piano 1° Tel: 0774/636443 Fax: 0774/636443",
			false, ParagraphAlignment.LEFT, false, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord.addText(document.createParagraph().createRun(),
		"Orario sportello:", false, ParagraphAlignment.LEFT, false,
		false, FONT_SIZE_SUBTITLE);

	// TABELLA ORARI
	XWPFTable table4 = document.createTable(1, 5);
	UtilityWord.addTableCell(table4.getRow(0).getCell(0), "Lunedì", true,
		ParagraphAlignment.CENTER, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table4.getRow(0).getCell(1),
		"Martedì 9:00 - 12:00", true, ParagraphAlignment.CENTER,
		CELL_WIDTH_IMPORTO, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table4.getRow(0).getCell(2), "Mercoledì",
		true, ParagraphAlignment.CENTER, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table4.getRow(0).getCell(3),
		"Giovedì 15:00 - 17:00", true, ParagraphAlignment.CENTER,
		CELL_WIDTH_IMPORTO, FONT_SIZE_SUBTITLE);
	UtilityWord.addTableCell(table4.getRow(0).getCell(4), "Venerdì", true,
		ParagraphAlignment.CENTER, CELL_WIDTH_IMPORTO,
		FONT_SIZE_SUBTITLE);
	UtilityWord.adjustTableWidth(table4);
	// FINE TABELLA ORARI

	UtilityWord
		.addText(
			document.createParagraph().createRun(),
			"L'Ufficio scrivente rimarrà a disposizione per le attività di rilascio Concessioni e/o eventuale richiesta di informazioni.",
			false, ParagraphAlignment.LEFT, false, false,
			FONT_SIZE_SUBTITLE);

	XWPFRun run2 = document.createParagraph().createRun();
	UtilityWord.addText(run2, "NOTA BENE:", true, ParagraphAlignment.LEFT,
		true);
	UtilityWord
		.addText(
			run2,
			"Qualora il destinatario della presente non sia a conoscenza della pratica in oggetto o non è più il proprietario è cortesemente pregato di darne segnalazione :",
			true, ParagraphAlignment.LEFT, true, false,
			FONT_SIZE_SUBTITLE);
	UtilityWord.addText(run2, "Ufficio Urbanistica ", true,
		ParagraphAlignment.LEFT, true, false, FONT_SIZE_SUBTITLE);
	UtilityWord.addText(run2, "Piazza Vittorio Veneto", true,
		ParagraphAlignment.LEFT, true, false, FONT_SIZE_SUBTITLE);
	UtilityWord.addText(run2, "00018 Palombara Sabina (RM)", true,
		ParagraphAlignment.LEFT, true, false, FONT_SIZE_SUBTITLE);
	UtilityWord.addText(run2, "Tel: 0774 636443  Fax: 0774 636443", true,
		ParagraphAlignment.LEFT, false, false, FONT_SIZE_SUBTITLE);

	// TABELLA RESPONSABILE
	XWPFTable table5 = document.createTable(1, 1);
	UtilityWord.addTableCell(table5.getRow(0).getCell(0),
		"Il responsabile del procedimento Arch. Paolo Caracciolo",
		true, ParagraphAlignment.CENTER, CELL_WIDTH_ALL,
		FONT_SIZE_SUBTITLE);
	// FINE TABELLA RESPONSABILE

	UtilityWord
		.addText(
			document.createParagraph().createRun(),
			"La firma autografa del responsabile del procedimento è sostituita dall'indicazione in stampa ai sensi dell'art. 6 quater della legge n. 80 del 15/03/1991.",
			false, ParagraphAlignment.LEFT, false, true,
			FONT_SIZE_SUBTITLE);

    }

    public void createPage3(XWPFDocument document, Double importoOblazione,
	    Double importoCalcolato, Double importoVersato,
	    Double importoRediduo, Double oneriConcessVersato,
	    Double oneriConcessCalcolato, Double oneriConcessSaldo,
	    Double dirittiIstrut, Double dirittiRilPerm, Double dirittiPareri,
	    Double agibilita, Double oneriAutodeterminata, Object leggeCondono,
	    String idabuso, String progressivo, String idpratica,
	    Double dataAbuso, Date data_stampa) {

	Double metaImportoResiduo = importoRediduo / 2;
	metaImportoResiduo = Converter.round(metaImportoResiduo, 2);

	document.createParagraph().createRun().addBreak(BreakType.PAGE);
	UtilityWord.addTextBoldBreakCenter(document.createParagraph()
		.createRun(), "2) ATTESTAZIONI DI VERSAMENTO");
	// OBLAZIONE
	XWPFTable table_ = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table_.getRow(0).getCell(0),
		"OBLAZIONE ", true, ParagraphAlignment.LEFT);
	UtilityWord.spanCellsAcrossRow(table_, 0, 0, 3);

	XWPFTable table1 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table1.getRow(0).getCell(0),
		"Autodetermina", false, ParagraphAlignment.LEFT);
	table1.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table1.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table1.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table1.getRow(0).getCell(2),
		Converter.doubleToString(importoOblazione), false,
		ParagraphAlignment.RIGHT);
	table1.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table2 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table2.getRow(0).getCell(0),
		"Importo versato", false, ParagraphAlignment.LEFT);
	table2.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table2.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table2.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table2.getRow(0).getCell(2),
		Converter.doubleToString(importoVersato), false,
		ParagraphAlignment.RIGHT);
	table2.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table3 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table3.getRow(0).getCell(0),
		"Importo calcolato", false, ParagraphAlignment.LEFT);
	table3.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table3.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table3.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table3.getRow(0).getCell(2),
		Converter.doubleToString(importoCalcolato), false,
		ParagraphAlignment.RIGHT);
	table3.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table4 = document.createTable(1, 3);
	UtilityWord
		.addTableCellCenter(
			table4.getRow(0).getCell(0),
			"Importo da versare a saldo comprensivo degli interessi dovuti ",
			true, ParagraphAlignment.LEFT);
	table4.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table4.getRow(0).getCell(1), "€", true,
		ParagraphAlignment.CENTER);
	table4.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table4.getRow(0).getCell(2),
		Converter.doubleToString(importoRediduo), true,
		ParagraphAlignment.RIGHT);
	table4.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	// OBLAZIONE REGIONALE
	Double saldo = Double.valueOf(0);
	if (Constants.ID_LEGGE_326_03.equals(leggeCondono)) {
	    saldo = addOblazioneRegionale(document, importoOblazione,
		    importoCalcolato, importoVersato, importoRediduo, idabuso,
		    progressivo, idpratica, dataAbuso, data_stampa);
	}

	// ONERI CONCESSORI
	XWPFTable table5_ = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table5_.getRow(0).getCell(0),
		"ONERI CONCESSORI", true, ParagraphAlignment.LEFT);
	UtilityWord.spanCellsAcrossRow(table5_, 0, 0, 3);

	if (Constants.ID_LEGGE_724_94.equals(leggeCondono)
		|| Constants.ID_LEGGE_326_03.equals(leggeCondono)) {
	    addAutodeterminataOneri(document, oneriAutodeterminata);
	}
	XWPFTable table6 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table6.getRow(0).getCell(0),
		"Importo versato", false, ParagraphAlignment.LEFT);
	table6.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table6.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table6.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table6.getRow(0).getCell(2),
		Converter.doubleToString(oneriConcessVersato), false,
		ParagraphAlignment.RIGHT);
	table6.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table7 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table7.getRow(0).getCell(0),
		"Importo calcolato", false, ParagraphAlignment.LEFT);
	table7.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table7.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table7.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table7.getRow(0).getCell(2),
		Converter.doubleToString(oneriConcessCalcolato), false,
		ParagraphAlignment.RIGHT);
	table7.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table8 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table8.getRow(0).getCell(0),
		"Importo da versare a saldo comprensivo degli interessi ",
		true, ParagraphAlignment.LEFT);
	table8.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table8.getRow(0).getCell(1), "€", true,
		ParagraphAlignment.CENTER);
	table8.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table8.getRow(0).getCell(2),
		Converter.doubleToString(oneriConcessSaldo), true,
		ParagraphAlignment.RIGHT);
	table8.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	// DIRITTI DI SEGRETERIA
	XWPFTable table9_ = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table9_.getRow(0).getCell(0),
		"DIRITTI DI SEGRETERIA ", true, ParagraphAlignment.LEFT);
	UtilityWord.spanCellsAcrossRow(table9_, 0, 0, 3);

	XWPFTable table10 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table10.getRow(0).getCell(0),
		"Diritti di istruttoria ", false, ParagraphAlignment.LEFT);
	table10.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table10.getRow(0).getCell(1), "€",
		false, ParagraphAlignment.CENTER);
	table10.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table10.getRow(0).getCell(2),
		Converter.doubleToString(dirittiIstrut), false,
		ParagraphAlignment.RIGHT);
	table10.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table11 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table11.getRow(0).getCell(0),
		"Diritti rilascio permesso di costruire ", false,
		ParagraphAlignment.LEFT);
	table11.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table11.getRow(0).getCell(1), "€",
		false, ParagraphAlignment.CENTER);
	table11.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table11.getRow(0).getCell(2),
		Converter.doubleToString(dirittiRilPerm), false,
		ParagraphAlignment.RIGHT);
	table11.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table12 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table12.getRow(0).getCell(0),
		"Diritti istruttoria pareri sui vincoli ", false,
		ParagraphAlignment.LEFT);
	table12.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table12.getRow(0).getCell(1), "€",
		false, ParagraphAlignment.CENTER);
	table12.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table12.getRow(0).getCell(2),
		Converter.doubleToString(dirittiPareri), false,
		ParagraphAlignment.RIGHT);
	table12.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table13 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table13.getRow(0).getCell(0),
		"Agibilità ", false, ParagraphAlignment.LEFT);
	table13.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table13.getRow(0).getCell(1), "€",
		false, ParagraphAlignment.CENTER);
	table13.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table13.getRow(0).getCell(2),
		Converter.doubleToString(agibilita), false,
		ParagraphAlignment.RIGHT);
	table13.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table15 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table15.getRow(0).getCell(0),
		"Totale diritti di segreteria ", true, ParagraphAlignment.LEFT);
	table15.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table15.getRow(0).getCell(1), "€", true,
		ParagraphAlignment.CENTER);
	table15.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(
		table15.getRow(0).getCell(2),
		Converter.doubleToString(new Double(dirittiIstrut
			+ dirittiPareri + dirittiRilPerm + agibilita)), true,
		ParagraphAlignment.RIGHT);
	table15.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table99_ = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table99_.getRow(0).getCell(0), "", true,
		ParagraphAlignment.LEFT);
	UtilityWord.spanCellsAcrossRow(table99_, 0, 0, 3);

	XWPFTable table16 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table16.getRow(0).getCell(0),
		"Totale da versare al comune di Palombara Sabina ", true,
		ParagraphAlignment.LEFT);
	table16.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table16.getRow(0).getCell(1), "€", true,
		ParagraphAlignment.CENTER);
	table16.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	Double d = Converter.round(new Double(oneriConcessSaldo
		+ metaImportoResiduo + dirittiIstrut + dirittiPareri
		+ dirittiRilPerm + agibilita), 2);
	UtilityWord.addTableCellCenter(table16.getRow(0).getCell(2),
		Converter.doubleToString(d), true, ParagraphAlignment.RIGHT);
	table16.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table17 = document.createTable(1, 1);

	createCircolareComuneInfo(table17);

	XWPFTable table18_ = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table18_.getRow(0).getCell(0),
		"OBLAZIONE MINISTERIALE", true, ParagraphAlignment.LEFT);
	UtilityWord.spanCellsAcrossRow(table18_, 0, 0, 3);

	XWPFTable table19 = document.createTable(1, 3);
	UtilityWord
		.addTableCellCenter(
			table19.getRow(0).getCell(0),
			"Importo da versare al Ministero LLPP a saldo comprensivo degli interessi su conto C/C255000 ",
			true, ParagraphAlignment.LEFT);
	UtilityWord.addTableCellCenter(table19.getRow(0).getCell(1), "€", true,
		ParagraphAlignment.CENTER);
	UtilityWord.addTableCellCenter(table19.getRow(0).getCell(2),
		Converter.doubleToString(metaImportoResiduo), true,
		ParagraphAlignment.RIGHT);

	createCircolareMinisteroInfo(document);

	if (Constants.ID_LEGGE_326_03.equals(leggeCondono)) {
	    addOblazioneRegionaleInformativa(document, importoOblazione,
		    importoCalcolato, importoVersato, importoRediduo, idabuso,
		    progressivo, idpratica, dataAbuso, saldo);
	}

	// NOTE INFORMATIVE
	XWPFParagraph p = document.createParagraph();
	// p.createRun().addBreak();
	UtilityWord
		.addTextBoldUnderlineBreak(
			p.createRun(),
			"In difetto si procederà a norma di legge con un provvedimento di diniego della concessione in sanatoria e conseguente rimozione / demolizione dell'abuso con costi a carico del richiedente ovvero con l'acquisizione del bene al patrimonio dell'amministrazione.",
			false);
    }

    private Double addOblazioneRegionale(XWPFDocument document,
	    Double importoOblazione, Double importoCalcolato,
	    Double importoVersato, Double importoRediduo, String idabuso,
	    String progressivo, String idpratica, Double dataAbuso, Date data_stampa) {
	XWPFTable table_ = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table_.getRow(0).getCell(0),
		"OBLAZIONE REGIONALE", true, ParagraphAlignment.LEFT);
	UtilityWord.spanCellsAcrossRow(table_, 0, 0, 3);

	Double autodeterminaRegione = datiVersamentiService
		.getAutodeterminaRegione(idabuso, progressivo);

	XWPFTable table1 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table1.getRow(0).getCell(0),
		"Autodetermina", false, ParagraphAlignment.LEFT);
	table1.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table1.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table1.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table1.getRow(0).getCell(2),
		Converter.doubleToString(autodeterminaRegione), false,
		ParagraphAlignment.RIGHT);
	table1.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	Double importoVersatoRegione = datiVersamentiService
		.getImportoVersatoOblazioneRegione(idpratica, progressivo);
	XWPFTable table2 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table2.getRow(0).getCell(0),
		"Importo versato", false, ParagraphAlignment.LEFT);
	table2.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table2.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table2.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table2.getRow(0).getCell(2),
		Converter.doubleToString(importoVersatoRegione), false,
		ParagraphAlignment.RIGHT);
	table2.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	XWPFTable table3 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table3.getRow(0).getCell(0),
		"Importo calcolato", false, ParagraphAlignment.LEFT);
	table3.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table3.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table3.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table3.getRow(0).getCell(2),
		Converter.doubleToString(importoCalcolato * 0.1), false,
		ParagraphAlignment.RIGHT);
	table3.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));

	Double saldo = datiVersamentiService.getOblazioneSaldo(
		importoVersatoRegione, autodeterminaRegione,
		importoCalcolato * 0.1, dataAbuso,
		BigInteger.valueOf(Integer.valueOf(progressivo)),
		Integer.valueOf(idpratica), data_stampa);
	XWPFTable table4 = document.createTable(1, 3);
	UtilityWord
		.addTableCellCenter(
			table4.getRow(0).getCell(0),
			"Importo da versare a saldo comprensivo degli interessi dovuti ",
			true, ParagraphAlignment.LEFT);
	table4.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table4.getRow(0).getCell(1), "€", true,
		ParagraphAlignment.CENTER);
	table4.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord
		.addTableCellCenter(table4.getRow(0).getCell(2),
			Converter.doubleToString(saldo), true,
			ParagraphAlignment.RIGHT);
	table4.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));
	return saldo;
    }

    private void addOblazioneRegionaleInformativa(XWPFDocument document,
	    Double importoOblazione, Double importoCalcolato,
	    Double importoVersato, Double importoRediduo, String idabuso,
	    String progressivo, String idpratica, Double dataAbuso, Double saldo) {
	document.createParagraph().createRun().addBreak();
	XWPFTable table_ = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table_.getRow(0).getCell(0),
		"OBLAZIONE REGIONALE", true, ParagraphAlignment.LEFT);
	UtilityWord.spanCellsAcrossRow(table_, 0, 0, 3);

	XWPFTable table4 = document.createTable(1, 3);
	UtilityWord
		.addTableCellCenter(
			table4.getRow(0).getCell(0),
			"Importo da versare a saldo comprensivo degli interessi dovuti ",
			true, ParagraphAlignment.LEFT);
	table4.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table4.getRow(0).getCell(1), "€", true,
		ParagraphAlignment.CENTER);
	table4.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord
		.addTableCellCenter(table4.getRow(0).getCell(2),
			Converter.doubleToString(saldo), true,
			ParagraphAlignment.RIGHT);
	table4.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));
	XWPFTable table4_ = document.createTable(1, 1);
	createCircolareRegioneInfo(document, table4_);
    }

    private void addAutodeterminataOneri(XWPFDocument document,
	    Double oneriAutodeterminata) {
	XWPFTable table06 = document.createTable(1, 3);
	UtilityWord.addTableCellCenter(table06.getRow(0).getCell(0),
		"Importo autodeterminato", false, ParagraphAlignment.LEFT);
	table06.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_DESC));
	UtilityWord.addTableCellCenter(table06.getRow(0).getCell(1), "€",
		false, ParagraphAlignment.CENTER);
	table06.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_VALUTA));
	UtilityWord.addTableCellCenter(table06.getRow(0).getCell(2),
		Converter.doubleToString(oneriAutodeterminata), false,
		ParagraphAlignment.RIGHT);
	table06.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(CELL_WIDTH_IMPORTO));
    }

    private void createCircolareMinisteroInfo(XWPFDocument document) {
	XWPFTable table = document.createTable(1, 1);
	table.getRow(0).getCell(0).setColor("FDF5F5");
	XWPFParagraph p = table.getRow(0).getCell(0).addParagraph();
	p.createRun().addBreak();
	UtilityWord
		.addTextSimple(
			p.createRun(),
			"La circolare n. 1/DPF del 16.1.2004 del Ministero dell'Economia e delle Finanze ha stabilito che le somme dovute dovranno essere versate mediante il ");
	UtilityWord.addTextUnderline(p.createRun(),
		"bollettino di conto corrente postale a tre sezioni");
	UtilityWord.addTextSimple(p.createRun(), " (mod. CH8 ter) sul ");
	UtilityWord
		.addTextBold(p.createRun(),
			"conto corrente postale n. 255000 intestato a Poste Italiane S.p.A.");
	UtilityWord.addTextSimpleBreak(p.createRun(), ", indicando:");
	UtilityWord.addTextSimple(p.createRun(), "•");
	UtilityWord.addTab(p, 1);
	UtilityWord.addTextSimpleBreak(p.createRun(), "l'importo;");

	UtilityWord.addTextSimple(p.createRun(), "•");
	UtilityWord.addTab(p, 1);
	UtilityWord.addTextSimpleBreak(p.createRun(),
		"gli estremi identificativi e l'indirizzo del richiedente;");

	UtilityWord.addTextSimple(p.createRun(), "•");
	UtilityWord.addTab(p, 1);
	UtilityWord.addTextSimpleBreak(p.createRun(),
		"nonché nello spazio riservato alla causale; ");

	UtilityWord.addTab(p, 2);
	UtilityWord.addTextSimpleBreak(p.createRun(),
		"1) il comune dove è ubicato l'immobile;");
	UtilityWord.addTab(p, 2);
	UtilityWord
		.addTextSimpleBreak(
			p.createRun(),
			"2) il numero progressivo indicato nella domanda relativa al versamento: il richiedente dovrà ");
	UtilityWord.addTab(p, 2);
	UtilityWord
		.addTextSimpleBreak(
			p.createRun(),
			"infatti  numerare” con numeri progressivi le domande di sanatoria presentate allo stesso comune ");
	UtilityWord.addTab(p, 2);
	UtilityWord
		.addTextSimpleBreak(
			p.createRun(),
			"e riportare il numero nella causale del versamento per consentirne l'abbinamento;");
	UtilityWord.addTab(p, 2);
	UtilityWord.addTextSimple(p.createRun(),
		"3)  il codice fiscale del richiedente.");
	table.getRow(0).getCell(0).removeParagraph(0);
	UtilityWord.spanCellsAcrossRow(table, 0, 0, 3);
	table.getCTTbl().getTblPr().unsetTblBorders();
    }

    private void createCircolareComuneInfo(XWPFTable table17) {
	XWPFParagraph parag = table17.getRow(0).getCell(0).addParagraph();
	// table17.getRow(0).getCell(0).setColor("E6DADA");
	parag.createRun().addBreak();
	UtilityWord
		.addTextSimpleBreak(
			parag.createRun(),
			"Il versamento a favore del Comune di Palombara Sabina può essere effettuato con le seguenti modalità:");
	parag.createRun().addBreak();
	UtilityWord
		.addTextSimpleBreak(
			parag.createRun(),
			"Beneficiario: Comune di Palombara Sabina - Servizio Tesoreria a mezzo bonifico bancario.");
	parag.createRun().addBreak();
	UtilityWord.addTab(parag, 1);
	UtilityWord.addTextSimple(parag.createRun(),
		"1) UFFICIO POSTALE sul CCP n. ");
	UtilityWord.addTextBoldBreak(parag.createRun(), "1020723423 ");
	UtilityWord.addTab(parag, 1);
	UtilityWord.addTextSimple(parag.createRun(), "2) IBAN: ");
	UtilityWord.addTextBold(parag.createRun(),
		"IT 64 Z 07601 03200 001020723423 ");
	UtilityWord
		.addTextSimpleBreak(parag.createRun(),
			"intestato a: COMUNE DI PALOMBARA SABINA – ONERI CONCESSORI IN SANATORIA");
	parag.createRun().addBreak();
	UtilityWord
		.addTextSimple(parag.createRun(),
			"I versamenti delle somme dovute a saldo al cui causale dovrà riportare ");
	UtilityWord
		.addTextBold(parag.createRun(),
			"il numero di pratica e il numero di protocollo di cui all'oggetto");
	UtilityWord.addTextSimple(parag.createRun(),
		" della presente nota, dovranno essere effettuati ");
	UtilityWord.addTextBoldUnderlineBreak(parag.createRun(),
		"entro e non oltre 60 gg. dal ricevimento della presente.",
		false);
	table17.getRow(0).getCell(0).removeParagraph(0);
	UtilityWord.spanCellsAcrossRow(table17, 0, 0, 3);
	table17.getCTTbl().getTblPr().unsetTblBorders();

    }

    private void createCircolareRegioneInfo(XWPFDocument document,
	    XWPFTable table) {
	XWPFParagraph parag = table.getRow(0).getCell(0).addParagraph();
	parag.createRun().addBreak();
	UtilityWord
		.addTextSimpleBreak(
			parag.createRun(),
			"Il versamento a favore della Regione Lazio può essere effettuato con le seguenti modalità: ");
	parag.createRun().addBreak();
	UtilityWord.addTextSimpleBreak(parag.createRun(),
		"Beneficiario: REGIONE LAZIO – Servizio Tesoreria");
	parag.createRun().addBreak();
	UtilityWord.addTab(parag, 1);
	UtilityWord.addTextSimple(parag.createRun(),
		"1) UFFICIO POSTALE sul CCP n. ");
	UtilityWord.addTextBold(parag.createRun(), "785014");
	parag.createRun().addBreak();
	parag.createRun().addBreak();
	UtilityWord
		.addTextSimple(parag.createRun(),
			"I versamenti delle somme dovute a saldo la cui causale dovrà riportare ");
	UtilityWord
		.addTextBold(
			parag.createRun(),
			"il numero di pratica e sottonumero e il numero di protocollo di cui all'oggetto");
	UtilityWord.addTextSimple(parag.createRun(),
		" della presente nota, dovranno essere effettuati ");
	UtilityWord.addTextBold(parag.createRun(),
		"entro e non oltre 60 gg. dal ricevimento della presente.");
	table.getRow(0).getCell(0).removeParagraph(0);
	UtilityWord.spanCellsAcrossRow(table, 0, 0, 3);
	table.getCTTbl().getTblPr().unsetTblBorders();

    }

    public XWPFDocument createPage1(XWPFDocument document,
	    DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB,
	    List<RelSoggettoAbuso> listaSoggetti, List<DatiAlloggio> alloggi,
	    TipologiaAbuso tipologiaAbuso) {

	// create header
	createHeader(document.createParagraph());
	// create Protocollo e lista soggetti
	createListaSoggetti(document.createParagraph(), praticaDB, abusoDB,
		listaSoggetti);
	creaOggetto(document.createParagraph(), praticaDB, abusoDB);

	XWPFTable table = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table.getRow(0).getCell(0),
		"Ubicazione dell'abuso: ", true, ParagraphAlignment.LEFT);
	table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(9999));
	XWPFTable table1 = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table1.getRow(0).getCell(0), "Località "
		+ abusoDB.getLocalizzazione().getComune() + ", Indirizzo "
		+ abusoDB.getLocalizzazione().getIndirizzo() + ", cap "
		+ abusoDB.getLocalizzazione().getCap(), false,
		ParagraphAlignment.LEFT);

	XWPFTable table2 = document.createTable(1, 1);
	UtilityWord.addTableCellCenter(table2.getRow(0).getCell(0),
		"Descrizione abuso: ", true, ParagraphAlignment.LEFT);
	table2.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(9999));

	XWPFTable table3 = document.createTable(2, 1);
	UtilityWord.addTableCellCenter(table3.getRow(0).getCell(0),
		abusoDB.getDescrizione(), false, ParagraphAlignment.LEFT);
	table3.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(9999));
	UtilityWord.addTableCellCenter(table3.getRow(1).getCell(0),
		"Dati Catastali", true, ParagraphAlignment.LEFT);

	UtilityWord.spanCellsAcrossRow(table, 0, 0, 5);
	UtilityWord.spanCellsAcrossRow(table1, 0, 0, 5);
	UtilityWord.spanCellsAcrossRow(table2, 0, 0, 5);
	UtilityWord.spanCellsAcrossRow(table3, 0, 0, 5);
	UtilityWord.spanCellsAcrossRow(table3, 1, 0, 5);

	XWPFTable tableHeader = createHeaderTableDatiCatastali(document);
	String zonaUrbanistica = abusoDB.getLocalizzazione()
		.getZonaUrbanizzazione() != null ? abusoDB.getLocalizzazione()
		.getZonaUrbanizzazione() : "";
	for (DatiAlloggio datiAlloggio : alloggi) {
	    creaParagraphDatiCatastaliDB(datiAlloggio.getIddatiAlloggio(),
		    document, tableHeader, zonaUrbanistica);
	}
	XWPFParagraph paragDatiTecnici = document.createParagraph();
	paragDatiTecnici.createRun().addBreak();
	UtilityWord.addTextBoldBreak(paragDatiTecnici.createRun(),
		"Dati Tecnici: ");
	creaParagraphDatiTecniciTable(paragDatiTecnici, abusoDB, praticaDB,
		tipologiaAbuso);
	UtilityWord
		.addTextSimple(
			document.createParagraph().createRun(),
			"Dall'istruttoria preliminare dell'istanza in oggetto, ai fini di poter completare le attività di disamina tecnico-amministrativo e procedere con il rilascio della Concessione in sanatoria la stessa, dovrà essere integrata con i documenti previsti dalle normative vigenti di cui al punto 1 e dalle attestazioni di versamento di cui al punto 2.");
	return document;
    }

    private void creaParagraphDatiTecniciTable(
	    XWPFParagraph paragraphDatiTecniciTable, DatiAbusoPojo abusoDB,
	    DatiPraticaPojo praticaDB, TipologiaAbuso tipologiaAbuso) {
	String testo1 = "";
	UtilityWord.addTextSimple(paragraphDatiTecniciTable.createRun(),
		"Destinazione d'uso: ");
	UtilityWord.addTab(paragraphDatiTecniciTable, 2);
	if (!StringUtils.isEmptyOrWhitespaceOnly(abusoDB.getDestinazioneUso())) {
	    TipologiaDestinazioneUso s = destinazioneUsoHome.findById(Integer
		    .valueOf(abusoDB.getDestinazioneUso()));
	    testo1 = testo1.concat(s.getDescrizioneTipologia());
	}
	UtilityWord.addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
		testo1);
	UtilityWord.addTextSimple(paragraphDatiTecniciTable.createRun(),
		"Superficie Utile Mq: ");
	UtilityWord.addTab(paragraphDatiTecniciTable, 2);
	UtilityWord.addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
		abusoDB.getSuperficeUtile());
	UtilityWord.addTextSimple(paragraphDatiTecniciTable.createRun(),
		"Non resid./accessori Mq: ");
	paragraphDatiTecniciTable.createRun().addTab();
	UtilityWord.addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
		abusoDB.getNonresidenziale());
	UtilityWord.addTextSimple(paragraphDatiTecniciTable.createRun(),
		"Mc VxP: ");
	UtilityWord.addTab(paragraphDatiTecniciTable, 3);
	UtilityWord.addTextSimpleBreak(
		paragraphDatiTecniciTable.createRun(),
		new Double(Double.valueOf(abusoDB.getVolumeUtile())
			+ Double.valueOf(abusoDB.getNonresidenzialeVuoto()))
			.toString());
	UtilityWord.addTextSimple(paragraphDatiTecniciTable.createRun(),
		"Tipologia: ");
	UtilityWord.addTab(paragraphDatiTecniciTable, 3);
	UtilityWord.addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
		String.valueOf(tipologiaAbuso.getDescrizioneBreve()));
	UtilityWord.addTextSimple(paragraphDatiTecniciTable.createRun(),
		"Epoca d'abuso: ");
	UtilityWord.addTab(paragraphDatiTecniciTable, 3);
	UtilityWord.addTextSimpleBreak(
		paragraphDatiTecniciTable.createRun(),
		epocaAbusoHome.findById(
			Integer.valueOf(abusoDB.getEpocaAbuso())).toString());
	UtilityWord.addTextSimple(paragraphDatiTecniciTable.createRun(),
		"Data ultimazione lavori: ");
	paragraphDatiTecniciTable.createRun().addTab();
	UtilityWord.addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
		abusoDB.getDataUltimazioneLavori());
    }

    private XWPFTable createHeaderTableDatiCatastali(XWPFDocument document) {
	// create table
	XWPFTable table = document.createTable(1, 5);
	// create first row
	XWPFTableRow tableRowOne = table.getRow(0);
	UtilityWord.addTableCellCenter(tableRowOne.getCell(0), "SEZIONE", true,
		ParagraphAlignment.CENTER);
	UtilityWord.addTableCellCenter(tableRowOne.getCell(1), "FOGLIO", true,
		ParagraphAlignment.CENTER);
	UtilityWord.addTableCellCenter(tableRowOne.getCell(2), "PARTICELLA",
		true, ParagraphAlignment.CENTER);
	UtilityWord.addTableCellCenter(tableRowOne.getCell(3), "SUBALTERNO",
		true, ParagraphAlignment.CENTER);
	UtilityWord.addTableCellCenter(tableRowOne.getCell(4), "ZONA", true,
		ParagraphAlignment.CENTER);

	UtilityWord.adjustTableWidth(table);
	return table;
    }

    private void creaParagraphDatiCatastaliDB(BigDecimal idalloggio,
	    XWPFDocument document, XWPFTable table, String zonaUrbanistica) {
	List<DatiFabbricati> listFabbric = datiFabbricatiHome
		.findAll(idalloggio.intValue());
	for (DatiFabbricati datiFabbricati : listFabbric) {
	    XWPFTableRow tableRowTwo = table.createRow();
	    UtilityWord.addTableCellCenter(tableRowTwo.getCell(0),
		    datiFabbricati.getSezione(), false,
		    ParagraphAlignment.CENTER);
	    UtilityWord.addTableCellCenter(tableRowTwo.getCell(1),
		    datiFabbricati.getFoglio(), false,
		    ParagraphAlignment.CENTER);
	    UtilityWord.addTableCellCenter(tableRowTwo.getCell(2),
		    datiFabbricati.getParticella(), false,
		    ParagraphAlignment.CENTER);
	    UtilityWord.addTableCellCenter(tableRowTwo.getCell(3),
		    datiFabbricati.getSubalterno(), false,
		    ParagraphAlignment.CENTER);
	    UtilityWord.addTableCellCenter(tableRowTwo.getCell(4),
		    zonaUrbanistica, false, ParagraphAlignment.CENTER);
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
	    tableRowTwo.getCell(4).setText("E3");

	    UtilityWord.addTableCellCenter(tableRowTwo.getCell(0),
		    datiTerreni.getSezione(), false, ParagraphAlignment.CENTER);
	    UtilityWord.addTableCellCenter(tableRowTwo.getCell(1),
		    datiTerreni.getFoglio(), false, ParagraphAlignment.CENTER);
	    UtilityWord.addTableCellCenter(tableRowTwo.getCell(2),
		    datiTerreni.getParticella(), false,
		    ParagraphAlignment.CENTER);
	    UtilityWord.addTableCellCenter(tableRowTwo.getCell(3),
		    datiTerreni.getSubalterno(), false,
		    ParagraphAlignment.CENTER);
	    UtilityWord.addTableCellCenter(tableRowTwo.getCell(4), "E3", false,
		    ParagraphAlignment.CENTER);
	}
    }

    // private void creaPargraphADB(XWPFDocument document,
    // DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB) {
    //
    // XWPFTable table = document.createTable(1, 6);
    //
    // addTableCellCenter(table.getRow(0).getCell(0), "Numero interno", true,
    // ParagraphAlignment.CENTER);
    // table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
    // .setW(BigInteger.valueOf(4000));
    //
    // addTableCellCenter(table.getRow(0).getCell(1),
    // "00" + praticaDB.getNumeroPratica(), true,
    // ParagraphAlignment.CENTER);
    // table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
    // .setW(BigInteger.valueOf(1500));
    //
    // addTableCellCenter(table.getRow(0).getCell(2), "Sottonumero", true,
    // ParagraphAlignment.CENTER);
    // table.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
    // .setW(BigInteger.valueOf(4000));
    //
    // addTableCellCenter(table.getRow(0).getCell(3),
    // abusoDB.getProgressivo(), true, ParagraphAlignment.CENTER);
    // table.getRow(0).getCell(3).getCTTc().addNewTcPr().addNewTcW()
    // .setW(BigInteger.valueOf(1000));
    //
    // addTableCellCenter(table.getRow(0).getCell(4), "Numero Protocollo",
    // true, ParagraphAlignment.CENTER);
    // table.getRow(0).getCell(4).getCTTc().addNewTcPr().addNewTcW()
    // .setW(BigInteger.valueOf(4500));
    //
    // addTableCellCenter(table.getRow(0).getCell(5),
    // praticaDB.getNumeroProtocollo(), true,
    // ParagraphAlignment.CENTER);
    // table.getRow(0).getCell(5).getCTTc().addNewTcPr().addNewTcW()
    // .setW(BigInteger.valueOf(1000));
    // }

    private void creaOggetto(XWPFParagraph paragraphggetto,
	    DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB) {

	UtilityWord.addTextBold(paragraphggetto.createRun(), "Oggetto: ");
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
	oggetto += " residente in "
		+ UtilityWord.concatStringVirgola(
			praticaDB.getComuneResidenza(), false)
		+ " "
		+ UtilityWord.concatStringVirgola(praticaDB.getIndirizzo(),
			false) + " "
		+ UtilityWord.concatStringVirgola(praticaDB.getCap(), true)
		+ ".";

	XWPFRun run = paragraphggetto.createRun();
	UtilityWord.addTextSimple(run, oggetto);
    }

    private void createListaSoggetti(XWPFParagraph paragraph,
	    DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB,
	    List<RelSoggettoAbuso> listaSoggetti) {
	UtilityWord.addTab(paragraph, 7);
	String testo1 = "Prot. n°";
	UtilityWord.addTextSimple(paragraph.createRun(), testo1);
	UtilityWord.addTab(paragraph, 3);
	String testo2 = "del ";// + praticaDB.getNumeroProtocollo()+
	// praticaDB.getDataProtocollo();
	UtilityWord.addTextSimpleBreak(paragraph.createRun(), testo2);
	paragraph.createRun().addBreak();
	for (RelSoggettoAbuso relSoggettoAbuso : listaSoggetti) {
	    UtilityWord.addTab(paragraph, 7);
	    UtilityWord.addTextSimpleBreak(
		    paragraph.createRun(),
		    "" + relSoggettoAbuso.getCognome() + " "
			    + relSoggettoAbuso.getNome());
	    UtilityWord.addTab(paragraph, 7);
	    UtilityWord.addTextSimpleBreak(paragraph.createRun(), ""
		    + relSoggettoAbuso.getIndirizzo());
	    UtilityWord.addTab(paragraph, 7);
	    UtilityWord.addTextSimpleBreak(paragraph.createRun(),
		    relSoggettoAbuso.getComuneResidenza() + " ("
			    + relSoggettoAbuso.getProvinciaResidenza() + "), "
			    + relSoggettoAbuso.getCap());
	    paragraph.createRun().addBreak();
	}
    }

    private void createHeader(XWPFParagraph paragraph) {
	InputStream is = context
		.getResourceAsStream("/WEB-INF/logoCondono.png");
	try {
	    paragraph.createRun().addPicture(is, Document.PICTURE_TYPE_PNG,
		    "logoCondono.png", Units.toEMU(318), Units.toEMU(63));
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
	UtilityWord.addTextBoldBreakCenter(document.createParagraph()
		.createRun(), "1) DOCUMENTI DA INTEGRARE");
	creaPargraphDOCTable(document.createParagraph(), daocumentiDB);
	creaPargraphINOFDOC(document.createParagraph());
	return document;
    }

    private void creaPargraphINOFDOC(XWPFParagraph paragraphInfoDoc) {

	UtilityWord.addTextSimple(paragraphInfoDoc.createRun(),
		"La documentazione richiesta che dovrà riportare ");
	UtilityWord
		.addTextBold(paragraphInfoDoc.createRun(),
			"il numero di pratica e il numero di protocollo di cui all'oggetto");
	UtilityWord.addTextSimple(paragraphInfoDoc.createRun(),
		" della presente nota, dovrà pervenire ");
	UtilityWord.addTextBold(paragraphInfoDoc.createRun(),
		"entro e non oltre 90 gg. dal ricevimento della presente. ");
	UtilityWord
		.addTextSimpleBreak(
			paragraphInfoDoc.createRun(),
			"Si fa presente che tutta la modulistica necessaria è reperibile presso il sito web del all'indirizzo www.comune.palombarasabina.rm.it");
	// paragraphInfoDoc.createRun().addBreak();
	UtilityWord.addTextBoldBreak(paragraphInfoDoc.createRun(),
		"NOTA BENE: ");
	// paragraphInfoDoc.createRun().addBreak();
	UtilityWord
		.addTextBoldUnderlineBreak(
			paragraphInfoDoc.createRun(),
			"In difetto si procederà a norma di legge con un provvedimento di diniego della concessione in sanatoria e conseguente rimozione/demolizione dell'abuso con costi a carico del richiedente ovvero dell'acquisizione del bene al patrimonio dell'amministrazione.",
			false);

    }

    private void creaPargraphDOCTable(XWPFParagraph paragraphDocTable,
	    List<DocumentiAbuso> daocumentiDB) {
	XWPFRun paragraphAOneRunOne = paragraphDocTable.createRun();
	for (DocumentiAbuso documentiAbuso : daocumentiDB) {
	    // paragraphAOneRunOne.addTab();
	    UtilityWord
		    .addTextSimpleBreak(paragraphAOneRunOne, "- "
			    .concat(documentiAbuso.getIdTipoDocumento()
				    .getDesc_word()));
	}

	paragraphAOneRunOne.addBreak();
	paragraphAOneRunOne.addBreak();
	paragraphAOneRunOne.addBreak();
	paragraphAOneRunOne.addBreak();
	paragraphAOneRunOne.addBreak();
	paragraphAOneRunOne.addBreak();

    }

    public XWPFDocument creaSollecito(XWPFDocument document,
	    boolean primosollecito, DatiPraticaService datiPraticaService,
	    DatiAbusoService datiAbusoService, String idpratica,
	    String idabuso, String progressivo) {

	DatiPraticaPojo praticaDB = datiPraticaService.findById(idpratica);
	DatiAbusoPojo abusoDB = datiAbusoService.findById(idabuso);
	DatiSollecito sollecito = datiSollecitiService.findAll(idpratica,
		progressivo);

	List<RelSoggettoAbuso> listaSoggetti = datiAbusoService
		.findAllSoggettiById(idabuso);
	// create header
	createHeader(document.createParagraph());
	document.createParagraph().createRun().addBreak();
	// create Protocollo e lista soggetti
	createListaSoggettiSollecito(document.createParagraph(), praticaDB,
		abusoDB, listaSoggetti, sollecito, primosollecito);
	document.createParagraph().createRun().addBreak();
	creaOggettoSollecito(document.createParagraph(), praticaDB, abusoDB);

	String testo = "In riferimento alla nostra precedente comunicazione che per conoscenza si allega in copia alla presente, si sollecita la Signoria Vostra all’integrazione della documentazione e dei pagamenti richiesti al fine di poter procedere alla predetta definizione dell’istanza in oggetto.";
	UtilityWord.addTextSimpleBreak(document.createParagraph().createRun(),
		testo);

	testo = "I nostri uffici sono a Vostra disposizione per fornire tutta l’assistenza necessaria per il completamento delle procedure d’istruttoria ed il rilascio della concessione in sanatoria presso lo sportello dedicato del Comune di Palombara Sabina, in Piazza Vittorio Veneto 12, nei seguenti giorni:";
	UtilityWord.addTextSimpleBreak(document.createParagraph().createRun(),
		testo);

	// TABELLA ORARI
	XWPFTable table1 = document.createTable(1, 1);
	UtilityWord
		.addTableCell(
			table1.getRow(0).getCell(0),
			"Il martedì dalle ore 9.00 alle ore 12.00 e il giovedì dalle ore 15.00 alle ore 17.00.",
			true, ParagraphAlignment.CENTER, CELL_WIDTH_ALL,
			FONT_SIZE_SUBTITLE);
	// FINE TABELLA ORARI

	testo = "Per qualsiasi comunicazione o richiesta di appuntamento è sempre possibile contattare il numero telefonico 0774/636443.";
	UtilityWord.addTextBoldBreak(document.createParagraph().createRun(),
		testo);

	testo = "La presente comunicazione è stata redatta dalla CO.GE.SI. Srl quale affidataria del servizio a supporto della definizione delle pratiche di condono edilizio del Comune di Palombara Sabina.";
	UtilityWord.addTextBoldBreak(document.createParagraph().createRun(),
		testo);

	// TABELLA RESPONSABILE
	XWPFTable table2 = document.createTable(1, 1);
	UtilityWord.addTableCell(table2.getRow(0).getCell(0),
		"Il responsabile del procedimento Arch. Paolo Caracciolo",
		true, ParagraphAlignment.CENTER, CELL_WIDTH_ALL,
		FONT_SIZE_SUBTITLE);
	// FINE TABELLA RESPONSABILE

	testo = "La firma autografa del responsabile del procedimento, è sostituita dall’indicazione a stampa ai sensi dell’art. 6.quater della legge 80 del 15/03/1991.";
	UtilityWord.addText(document.createParagraph().createRun(), testo,
		true, ParagraphAlignment.LEFT, true, true, 9);

	document.createParagraph().createRun().addBreak(BreakType.PAGE);

	return document;
    }

    private void creaOggettoSollecito(XWPFParagraph paragraphggetto,
	    DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB) {

	UtilityWord.addTextBold(paragraphggetto.createRun(), "Oggetto: ");
	String oggetto = "Definizione Istanza di Sanatoria edilizia ai sensi della legge "
		.concat(praticaDB.getLeggeCondono() != null ? leggiCondonoHome
			.findById(Integer.valueOf(praticaDB.getLeggeCondono()))
			.getLeggeNumero() : " ");
	oggetto += " presentata il " + praticaDB.getDataDomanda();
	oggetto += " con protocollo n. " + praticaDB.getNumeroProtocollo();
	oggetto += " del " + praticaDB.getDataProtocollo();
	oggetto += " sott " + abusoDB.getProgressivo();
	oggetto += " N° interno " + praticaDB.getNumeroPratica();
	oggetto += " richiesta da " + praticaDB.getCognome() + " "
		+ praticaDB.getNome().concat(".");
	XWPFRun run = paragraphggetto.createRun();
	UtilityWord.addTextBoldBreak(run, oggetto);
    }

    private void createListaSoggettiSollecito(XWPFParagraph paragraph,
	    DatiPraticaPojo praticaDB, DatiAbusoPojo abusoDB,
	    List<RelSoggettoAbuso> listaSoggetti, DatiSollecito sollecito,
	    boolean primosollecito) {
	String testo1;
	String testo2;
	if (primosollecito) {
	    testo1 = "Protocollo n° ".concat(sollecito.getProtocolloSoll1() != null?sollecito.getProtocolloSoll1():"");
	    UtilityWord.addTextSimple(paragraph.createRun(), testo1);
	    UtilityWord.addTab(paragraph, 1);
	    testo2 = "del ".concat(sollecito.getDataInvioSoll1()!=null?sollecito.getDataInvioSoll1():"");
	    UtilityWord.addTextSimpleBreak(paragraph.createRun(), testo2);
	} else {
	    testo1 = "Protocollo n° ".concat(sollecito.getProtocolloSoll2() != null?sollecito.getProtocolloSoll2():"");
	    UtilityWord.addTextSimple(paragraph.createRun(), testo1);
	    UtilityWord.addTab(paragraph, 1);
	    testo2 = "del ".concat(sollecito.getDataInvioSoll2()!=null?sollecito.getDataInvioSoll2():"");
	    UtilityWord.addTextSimpleBreak(paragraph.createRun(), testo2);
	}
	paragraph.createRun().addBreak();
	for (RelSoggettoAbuso relSoggettoAbuso : listaSoggetti) {
	    UtilityWord.addTab(paragraph, 7);
	    UtilityWord.addTextBoldBreak(
		    paragraph.createRun(),
		    "" + relSoggettoAbuso.getCognome() + " "
			    + relSoggettoAbuso.getNome());
	    UtilityWord.addTab(paragraph, 7);
	    UtilityWord.addTextBoldBreak(paragraph.createRun(), ""
		    + relSoggettoAbuso.getIndirizzo());
	    UtilityWord.addTab(paragraph, 7);
	    UtilityWord.addTextBoldBreak(paragraph.createRun(),
		    relSoggettoAbuso.getComuneResidenza() + " ("
			    + relSoggettoAbuso.getProvinciaResidenza() + "), "
			    + relSoggettoAbuso.getCap());
	    paragraph.createRun().addBreak();
	}
    }
}
