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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
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
    ServletContext context;

    private static Integer CELL_WIDTH_DESC = 9900;
    private static Integer CELL_WIDTH_VALUTA = 500;
    private static Integer CELL_WIDTH_IMPORTO = 1500;

    public XWPFDocument createDoc(XWPFDocument document,
	    DatiPraticaService praticaService, DatiAbusoService abusoService,
	    String idpratica, String idabuso, String progressivo)
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
		praticaDB.getLeggeCondono());
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
			    oneriConcessCalcolato, abusoDB, praticaDB);
	}
	if (Constants.ID_LEGGE_326_03.equals(praticaDB.getLeggeCondono())) {
	    oneriConcessSaldo = datiVersamentiService
		    .getOneriConcessSaldoLegge3(oneriConcessVersato,
			    oneriConcessCalcolato, abusoDB, praticaDB);
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
		dataAbuso);
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
	InputStream is = context
		.getResourceAsStream("/WEB-INF/ultima pag statica.png");
	try {
	    document.createParagraph()
		    .createRun()
		    .addPicture(is, Document.PICTURE_TYPE_PNG,
			    "ultima pag statica.png", Units.toEMU(550),
			    Units.toEMU(650));
	} catch (InvalidFormatException e) {
	    e.printStackTrace();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public void createPage3(XWPFDocument document, Double importoOblazione,
	    Double importoCalcolato, Double importoVersato,
	    Double importoRediduo, Double oneriConcessVersato,
	    Double oneriConcessCalcolato, Double oneriConcessSaldo,
	    Double dirittiIstrut, Double dirittiRilPerm, Double dirittiPareri,
	    Double agibilita, Double oneriAutodeterminata, Object leggeCondono,
	    String idabuso, String progressivo, String idpratica,
	    Double dataAbuso) {

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
		    progressivo, idpratica, dataAbuso);
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
	p.createRun().addBreak();
	UtilityWord
		.addTextBoldUnderlineBreak(
			p.createRun(),
			"In difetto si procederà a norma di legge con un provvedimento di diniego della concessione in sanatoria e conseguente rimozione / demolizione dell'abuso con costi a carico del richiedente ovvero con l'acquisizione del bene al patrimonio dell'amministrazione.",
			false);
    }

    private Double addOblazioneRegionale(XWPFDocument document,
	    Double importoOblazione, Double importoCalcolato,
	    Double importoVersato, Double importoRediduo, String idabuso,
	    String progressivo, String idpratica, Double dataAbuso) {
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
		Integer.valueOf(idpratica));
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
}
