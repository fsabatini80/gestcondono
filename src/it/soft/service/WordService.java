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
import it.soft.domain.DocumentiAbuso;
import it.soft.domain.EpocaAbuso;
import it.soft.domain.RelSoggettoAbuso;
import it.soft.domain.TipologiaDestinazioneUso;
import it.soft.util.Converter;
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
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
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

    public XWPFDocument createDoc(XWPFDocument document,
	    DatiPraticaService praticaService, DatiAbusoService abusoService,
	    String idpratica, String idabuso, String progressivo)
	    throws Exception {

	DatiPraticaPojo praticaDB = praticaService.findById(idpratica);
	DatiAbusoPojo abusoDB = abusoService.findById(idabuso);

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

	Double importoVersato = datiVersamentiService
		.getImportoVersatoOblazione(idpratica, progressivo);

	EpocaAbuso epocaAbuso = epocaAbusoHome.findById(Integer
		.parseInt(praticaDB.getLeggeCondono()));

	Double importoCalcolato = datiVersamentiService
		.getImportoCalcolatoOblazione(
			tipologiaAbusoHome.findById(Integer.valueOf(abusoDB
				.getTipologiaAbuso())), Converter
				.dateToDouble(epocaAbuso.getEpocaDa()),
			praticaDB.getLeggeCondono(), idabuso, abusoDB
				.getDestinazioneUso());

	Double oblazioneDovuta = datiVersamentiService.getOblazioneDovuta(
		importoCalcolato, abusoDB,
		Converter.dateToDouble(praticaDB.getDataCreazione()),
		praticaDB.getLeggeCondono());

	createPage1(document, praticaDB, abusoDB, listaSoggetti, alloggi);
	createPage2(document, daocumentiDB);
	createPage3(document, importoOblazioneAut, importoCalcolato,
		importoVersato, oblazioneDovuta);
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
	InputStream is = WordService.class
		.getResourceAsStream("ultima pag statica.jpg");
	try {
	    document.createParagraph()
		    .createRun()
		    .addPicture(is, Document.PICTURE_TYPE_JPEG,
			    "ultima pag statica.jpg", Units.toEMU(450),
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
	    Double importoRediduo) {

	document.createParagraph().createRun().addBreak(BreakType.PAGE);
	addTextBoldBreakCenter(document.createParagraph().createRun(),
		"2) ATTESTAZIONI DI VERSAMENTO");

	// OBLAZIONE
	XWPFTable table = document.createTable(1, 3);
	addTableCellCenter(table.getRow(0).getCell(0), "OBLAZIONE ", true,
		ParagraphAlignment.LEFT);
	table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(7099));
	addTableCellCenter(table.getRow(0).getCell(1), "", false,
		ParagraphAlignment.CENTER);
	table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(400));
	addTableCellCenter(table.getRow(0).getCell(2), "", false,
		ParagraphAlignment.CENTER);
	table.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(2500));

	XWPFTable table1 = document.createTable(1, 3);
	addTableCellCenter(table1.getRow(0).getCell(0), "Autodetermina", false,
		ParagraphAlignment.LEFT);
	table1.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table1.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table1.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table1.getRow(0).getCell(2),
		importoOblazione.toString(), false, ParagraphAlignment.RIGHT);
	table1.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table2 = document.createTable(1, 3);
	addTableCellCenter(table2.getRow(0).getCell(0), "Importo versato",
		false, ParagraphAlignment.LEFT);
	table2.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table2.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table2.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table2.getRow(0).getCell(2),
		importoVersato.toString(), false, ParagraphAlignment.RIGHT);
	table2.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table3 = document.createTable(1, 3);
	addTableCellCenter(table3.getRow(0).getCell(0), "Importo calcolato",
		false, ParagraphAlignment.LEFT);
	table3.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table3.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table3.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table3.getRow(0).getCell(2),
		importoCalcolato.toString(), false, ParagraphAlignment.RIGHT);
	table3.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table4 = document.createTable(1, 3);
	addTableCellCenter(
		table4.getRow(0).getCell(0),
		"Importo da versare a saldo comprensivo degli interessi dovuti ",
		true, ParagraphAlignment.LEFT);
	table4.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table4.getRow(0).getCell(1), "€", true,
		ParagraphAlignment.CENTER);
	table4.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table4.getRow(0).getCell(2),
		importoRediduo.toString(), true, ParagraphAlignment.RIGHT);
	table4.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	// ONERI CONCESSORI
	XWPFTable table5 = document.createTable(1, 3);
	addTableCellCenter(table5.getRow(0).getCell(0), "ONERI CONCESSORI",
		true, ParagraphAlignment.LEFT);
	table5.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(7099));
	addTableCellCenter(table5.getRow(0).getCell(1), "", false,
		ParagraphAlignment.CENTER);
	table5.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(400));
	addTableCellCenter(table5.getRow(0).getCell(2), "", false,
		ParagraphAlignment.CENTER);
	table5.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(2500));

	XWPFTable table6 = document.createTable(1, 3);
	addTableCellCenter(table6.getRow(0).getCell(0), "Importo versato",
		false, ParagraphAlignment.LEFT);
	table6.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table6.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table6.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table6.getRow(0).getCell(2), "", false,
		ParagraphAlignment.RIGHT);
	table6.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table7 = document.createTable(1, 3);
	addTableCellCenter(table7.getRow(0).getCell(0), "Importo calcolato",
		false, ParagraphAlignment.LEFT);
	table7.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table7.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table7.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table7.getRow(0).getCell(2), "", false,
		ParagraphAlignment.RIGHT);
	table7.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table8 = document.createTable(1, 3);
	addTableCellCenter(table8.getRow(0).getCell(0),
		"Importo da versare a saldo comprensivo degli interessi ",
		true, ParagraphAlignment.LEFT);
	table8.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table8.getRow(0).getCell(1), "€", true,
		ParagraphAlignment.CENTER);
	table8.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table8.getRow(0).getCell(2), "", true,
		ParagraphAlignment.RIGHT);
	table8.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	// DIRITTI DI SEGRETERIA
	XWPFTable table9 = document.createTable(1, 3);
	addTableCellCenter(table9.getRow(0).getCell(0),
		"DIRITTI DI SEGRETERIA ", true, ParagraphAlignment.LEFT);
	table9.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table9.getRow(0).getCell(1), "", false,
		ParagraphAlignment.CENTER);
	table9.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table9.getRow(0).getCell(2), "", false,
		ParagraphAlignment.RIGHT);
	table9.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table10 = document.createTable(1, 3);
	addTableCellCenter(table10.getRow(0).getCell(0),
		"Diritti di istruttoria ", false, ParagraphAlignment.LEFT);
	table10.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table10.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table10.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table10.getRow(0).getCell(2), "", false,
		ParagraphAlignment.RIGHT);
	table10.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table11 = document.createTable(1, 3);
	addTableCellCenter(table11.getRow(0).getCell(0),
		"Diritti rilascio permesso di costruire ", false,
		ParagraphAlignment.LEFT);
	table11.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table11.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table11.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table11.getRow(0).getCell(2), "", false,
		ParagraphAlignment.RIGHT);
	table11.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table12 = document.createTable(1, 3);
	addTableCellCenter(table12.getRow(0).getCell(0),
		"Diritti istruttoria pareri sui vincoli ", false,
		ParagraphAlignment.LEFT);
	table12.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table12.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table12.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table12.getRow(0).getCell(2), "", false,
		ParagraphAlignment.RIGHT);
	table12.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table13 = document.createTable(1, 3);
	addTableCellCenter(table13.getRow(0).getCell(0), "Agibilità ", false,
		ParagraphAlignment.LEFT);
	table13.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table13.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table13.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table13.getRow(0).getCell(2), "", false,
		ParagraphAlignment.RIGHT);
	table13.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table14 = document.createTable(1, 3);
	addTableCellCenter(table14.getRow(0).getCell(0), "Importo da versare ",
		false, ParagraphAlignment.LEFT);
	table14.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table14.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table14.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table14.getRow(0).getCell(2), "", false,
		ParagraphAlignment.RIGHT);
	table14.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table15 = document.createTable(1, 3);
	addTableCellCenter(table15.getRow(0).getCell(0),
		"Totale diritti di segreteria ", true, ParagraphAlignment.LEFT);
	table15.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table15.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table15.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table15.getRow(0).getCell(2), "", false,
		ParagraphAlignment.RIGHT);
	table15.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table16 = document.createTable(1, 3);
	addTableCellCenter(table16.getRow(0).getCell(0),
		"Totale da versare al comune di Palombara Sabina ", true,
		ParagraphAlignment.LEFT);
	table16.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(5000));
	addTableCellCenter(table16.getRow(0).getCell(1), "€", false,
		ParagraphAlignment.CENTER);
	table16.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(499));
	addTableCellCenter(table16.getRow(0).getCell(2), "", false,
		ParagraphAlignment.RIGHT);
	table16.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	XWPFTable table17 = document.createTable(1, 1);

	creatTotaleRiepilogoInfo(table17);

	XWPFTable table18 = document.createTable(1, 3);
	addTableCellCenter(table18.getRow(0).getCell(0),
		"OBLAZIONE MINISTERIALE", true, ParagraphAlignment.LEFT);
	addTableCellCenter(table18.getRow(0).getCell(1), "", true,
		ParagraphAlignment.LEFT);
	addTableCellCenter(table18.getRow(0).getCell(2), "", true,
		ParagraphAlignment.LEFT);

	XWPFTable table19 = document.createTable(1, 3);
	addTableCellCenter(
		table19.getRow(0).getCell(0),
		"Importo da versare al Ministero LLPP a saldo comprensivo degli interessi su conto C/C255000 ",
		true, ParagraphAlignment.LEFT);
	addTableCellCenter(table19.getRow(0).getCell(1), "€", true,
		ParagraphAlignment.CENTER);
	addTableCellCenter(table19.getRow(0).getCell(2), "", true,
		ParagraphAlignment.RIGHT);

	createCircolareInfo(document);

	// NOTE INFORMATIVE
	XWPFParagraph p = document.createParagraph();
	p.createRun().addBreak();
	addTextBoldUnderlineBreak(
		p.createRun(),
		"In difetto si procederà a norma di legge con un provvedimento di diniego della concessione in sanatoria e conseguente rimozione / demolizione dell'abuso con costi a carico del richiedente ovvero con l'acquisizione del bene al patrimonio dell'amministrazione.",
		false);
    }

    private void createCircolareInfo(XWPFDocument document) {
	XWPFTable table = document.createTable(1, 1);
	XWPFParagraph p = table.getRow(0).getCell(0).addParagraph();
	addTextSimple(
		p.createRun(),
		"La circolare n. 1/DPF del 16.1.2004 del Ministero dell'Economia e delle Finanze ha stabilito che le somme dovute dovranno essere versate mediante il ");
	addTextUnderline(p.createRun(),
		"bollettino di conto corrente postale a tre sezioni");
	addTextSimple(p.createRun(), " (mod. CH8 ter) sul ");
	addTextBold(p.createRun(),
		"conto corrente postale n. 255000 intestato a Poste Italiane S.p.A.,");
	addTextSimpleBreak(p.createRun(), " indicando:");
	addTextSimple(p.createRun(), "•");
	addTab(p, 1);
	addTextSimpleBreak(p.createRun(), "l'importo;");

	addTextSimple(p.createRun(), "•");
	addTab(p, 1);
	addTextSimpleBreak(p.createRun(),
		"gli estremi identificativi e l'indirizzo del richiedente;");

	addTextSimple(p.createRun(), "•");
	addTab(p, 1);
	addTextSimpleBreak(p.createRun(),
		"nonché nello spazio riservato alla causale; ");

	addTab(p, 2);
	addTextSimpleBreak(p.createRun(),
		"1) il comune dove è ubicato l'immobile;");
	addTab(p, 2);
	addTextSimpleBreak(
		p.createRun(),
		"2) il numero progressivo indicato nella domanda relativa al versamento: il richiedente dovrà ");
	addTab(p, 2);
	addTextSimpleBreak(
		p.createRun(),
		"infatti  numerare” con numeri progressivi le domande di sanatoria presentate allo stesso comune ");
	addTab(p, 2);
	addTextSimpleBreak(
		p.createRun(),
		"e riportare il numero nella causale del versamento per consentirne l'abbinamento;");
	addTab(p, 2);
	addTextSimple(p.createRun(), "3)  il codice fiscale del richiedente.");
	table.getRow(0).getCell(0).removeParagraph(0);
	spanCellsAcrossRow(table, 0, 0, 3);
    }

    private void creatTotaleRiepilogoInfo(XWPFTable table17) {
	XWPFParagraph parag = table17.getRow(0).getCell(0).addParagraph();
	addTextSimpleBreak(
		parag.createRun(),
		"Il versamento va effettuato a favore del Comune di Palombara Sabina può essere effettuato con le seguenti modalità:");
	parag.createRun().addBreak();
	addTextSimple(parag.createRun(), "1) UFFICIO POSTALE sul CCP n. ");
	addTextBold(parag.createRun(), "1020723423 ");
	addTextSimple(parag.createRun(), "IBAN: ");
	addTextBold(parag.createRun(), "  IT 64 Z 07601 03200 001020723423 ");
	addTextSimpleBreak(parag.createRun(),
		"intestato a:  COMUNE DI PALOMBARA SABINA – ONERI CONCESS. IN SANATORIA");
	addTextSimple(parag.createRun(),
		"I versamenti delle somme dovute a saldo al cui causale dovrà riportare ");
	addTextBold(parag.createRun(),
		"il numero di pratica e il numero di protocollo di cui al punto A");
	addTextSimple(parag.createRun(),
		" della presente nota, dovranno essere effettuati ");
	addTextBoldUnderlineBreak(parag.createRun(),
		"entro e non oltre 60 gg. dal ricevimento della presente.",
		false);
	table17.getRow(0).getCell(0).removeParagraph(0);
	spanCellsAcrossRow(table17, 0, 0, 3);

    }

    private void spanCellsAcrossRow(XWPFTable table, int rowNum, int colNum,
	    int span) {
	XWPFTableCell cell = table.getRow(rowNum).getCell(colNum);
	cell.getCTTc().addNewTcPr().addNewGridSpan()
		.setVal(BigInteger.valueOf((long) span));
    }

    @SuppressWarnings("deprecation")
    private void addTextBoldBreakCenter(XWPFRun run, String testo) {
	run.setFontFamily("Times New Roman");
	run.getParagraph().setAlignment(ParagraphAlignment.CENTER);
	run.setText(testo);
	run.setFontSize(11);
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

	XWPFTable table = document.createTable(1, 1);
	addTableCellCenter(table.getRow(0).getCell(0),
		"Ubicazione dell'abuso: ", true, ParagraphAlignment.LEFT);
	table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(9999));
	XWPFTable table1 = document.createTable(1, 1);
	addTableCellCenter(table1.getRow(0).getCell(0), "Località "
		+ abusoDB.getLocalizzazione().getComune() + ", Indirizzo "
		+ abusoDB.getLocalizzazione().getIndirizzo() + ", cap "
		+ abusoDB.getLocalizzazione().getCap(), false,
		ParagraphAlignment.LEFT);

	XWPFTable table2 = document.createTable(1, 1);
	addTableCellCenter(table2.getRow(0).getCell(0), "Descrizione abuso: ",
		true, ParagraphAlignment.LEFT);
	table2.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(9999));

	XWPFTable table3 = document.createTable(2, 1);
	addTableCellCenter(table3.getRow(0).getCell(0),
		abusoDB.getDescrizione(), false, ParagraphAlignment.LEFT);
	table3.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(9999));
	addTableCellCenter(table3.getRow(1).getCell(0), "Dati Catastali", true,
		ParagraphAlignment.LEFT);

	spanCellsAcrossRow(table, 0, 0, 5);
	spanCellsAcrossRow(table1, 0, 0, 5);
	spanCellsAcrossRow(table2, 0, 0, 5);
	spanCellsAcrossRow(table3, 0, 0, 5);
	spanCellsAcrossRow(table3, 1, 0, 5);

	XWPFTable tableHeader = createHeaderTableDatiCatastali(document);
	for (DatiAlloggio datiAlloggio : alloggi) {
	    creaParagraphDatiCatastaliDB(datiAlloggio.getIddatiAlloggio(),
		    document, tableHeader);
	}
	XWPFParagraph paragDatiTecnici = document.createParagraph();
	paragDatiTecnici.createRun().addBreak();
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
	    testo1 = testo1.concat(s.getDescrizioneTipologia());
	}
	addTextSimpleBreak(paragraphDatiTecniciTable.createRun(), testo1);
	addTextSimple(paragraphDatiTecniciTable.createRun(),
		"Superficie Utile Mq: ");
	addTab(paragraphDatiTecniciTable, 2);
	addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
		abusoDB.getSuperficeUtile());
	addTextSimple(paragraphDatiTecniciTable.createRun(),
		"Non resid./accessori Mq: ");
	paragraphDatiTecniciTable.createRun().addTab();
	addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
		abusoDB.getNonresidenziale());
	addTextSimple(paragraphDatiTecniciTable.createRun(), "Mc VxP: ");
	addTab(paragraphDatiTecniciTable, 3);
	addTextSimpleBreak(paragraphDatiTecniciTable.createRun(),
		abusoDB.getVolumeTotale());
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
	XWPFTable table = document.createTable(1, 5);
	// create first row
	XWPFTableRow tableRowOne = table.getRow(0);
	addTableCellCenter(tableRowOne.getCell(0), "SEZIONE", true,
		ParagraphAlignment.CENTER);
	addTableCellCenter(tableRowOne.getCell(1), "FOGLIO", true,
		ParagraphAlignment.CENTER);
	addTableCellCenter(tableRowOne.getCell(2), "PARTICELLA", true,
		ParagraphAlignment.CENTER);
	addTableCellCenter(tableRowOne.getCell(3), "SUBALTERNO", true,
		ParagraphAlignment.CENTER);
	addTableCellCenter(tableRowOne.getCell(4), "ZONA", true,
		ParagraphAlignment.CENTER);

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
	    addTableCellCenter(tableRowTwo.getCell(0),
		    datiFabbricati.getSezione(), false,
		    ParagraphAlignment.CENTER);
	    addTableCellCenter(tableRowTwo.getCell(1),
		    datiFabbricati.getFoglio(), false,
		    ParagraphAlignment.CENTER);
	    addTableCellCenter(tableRowTwo.getCell(2),
		    datiFabbricati.getParticella(), false,
		    ParagraphAlignment.CENTER);
	    addTableCellCenter(tableRowTwo.getCell(3),
		    datiFabbricati.getSubalterno(), false,
		    ParagraphAlignment.CENTER);
	    addTableCellCenter(tableRowTwo.getCell(4), "E3", false,
		    ParagraphAlignment.CENTER);
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

	    addTableCellCenter(tableRowTwo.getCell(0),
		    datiTerreni.getSezione(), false, ParagraphAlignment.CENTER);
	    addTableCellCenter(tableRowTwo.getCell(1), datiTerreni.getFoglio(),
		    false, ParagraphAlignment.CENTER);
	    addTableCellCenter(tableRowTwo.getCell(2),
		    datiTerreni.getParticella(), false,
		    ParagraphAlignment.CENTER);
	    addTableCellCenter(tableRowTwo.getCell(3),
		    datiTerreni.getSubalterno(), false,
		    ParagraphAlignment.CENTER);
	    addTableCellCenter(tableRowTwo.getCell(4), "E3", false,
		    ParagraphAlignment.CENTER);
	}
    }

    private XWPFParagraph addTableCellCenter(XWPFTableCell tableCell,
	    String testo, boolean bold, ParagraphAlignment alignment) {
	// IBody body = tableCell.getBodyElements().get(0).getBody();
	// XWPFParagraph parCell = new XWPFParagraph(CTP.Factory.newInstance(),
	// body);
	XWPFParagraph parCell = tableCell.addParagraph();
	// tableCell.setVerticalAlignment(XWPFVertAlign.CENTER);
	parCell.setAlignment(alignment);
	XWPFRun run = parCell.createRun();
	run.setFontFamily("Times New Roman");
	run.setText(testo);
	run.setBold(bold);
	CTPPr ppr = parCell.getCTP().getPPr();
	if (ppr == null)
	    ppr = parCell.getCTP().addNewPPr();
	CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr
		.addNewSpacing();
	spacing.setAfter(BigInteger.valueOf(0));
	spacing.setBefore(BigInteger.valueOf(0));
	spacing.setLineRule(STLineSpacingRule.AUTO);
	spacing.setLine(BigInteger.valueOf(240));
	// tableCell.setParagraph(parCell);
	tableCell.removeParagraph(0);
	return parCell;
    }

    private void addTextBold(XWPFRun run, String testo) {
	run.setFontFamily("Times New Roman");
	run.setText(testo);
	run.setBold(true);
    }

    private void addTextBoldBreak(XWPFRun run, String testo) {
	run.setFontFamily("Times New Roman");
	run.setText(testo);
	run.setBold(true);
	run.addBreak();
    }

    private void addTextSimple(XWPFRun run, String testo) {
	run.setFontFamily("Times New Roman");
	run.setText(testo);
    }

    private void addTextSimpleBreak(XWPFRun run, String testo) {
	run.setFontFamily("Times New Roman");
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

	addTableCellCenter(table.getRow(0).getCell(0), "Numero interno", true,
		ParagraphAlignment.CENTER);
	table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4000));

	addTableCellCenter(table.getRow(0).getCell(1),
		"00" + praticaDB.getNumeroPratica(), true,
		ParagraphAlignment.CENTER);
	table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(1500));

	addTableCellCenter(table.getRow(0).getCell(2), "Sottonumero", true,
		ParagraphAlignment.CENTER);
	table.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4000));

	addTableCellCenter(table.getRow(0).getCell(3),
		abusoDB.getProgressivo(), true, ParagraphAlignment.CENTER);
	table.getRow(0).getCell(3).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(1000));

	addTableCellCenter(table.getRow(0).getCell(4), "Numero Protocollo",
		true, ParagraphAlignment.CENTER);
	table.getRow(0).getCell(4).getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(4500));

	addTableCellCenter(table.getRow(0).getCell(5),
		praticaDB.getNumeroProtocollo(), true,
		ParagraphAlignment.CENTER);
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
	addTab(paragraph, 7);
	String testo1 = "Protocollo Numero";
	addTextSimple(paragraph.createRun(), testo1);
	addTab(paragraph, 1);
	String testo2 = "del ";// + praticaDB.getNumeroProtocollo()+ praticaDB.getDataProtocollo();
	addTextSimpleBreak(paragraph.createRun(), testo2);
	paragraph.createRun().addBreak();
	for (RelSoggettoAbuso relSoggettoAbuso : listaSoggetti) {
	    addTab(paragraph, 7);
	    addTextSimpleBreak(paragraph.createRun(),
		    "COGOME E NOME: " + relSoggettoAbuso.getCognome() + " "
			    + relSoggettoAbuso.getNome());
	    addTab(paragraph, 7);
	    addTextSimpleBreak(paragraph.createRun(), "INDIRIZZO: "
		    + relSoggettoAbuso.getIndirizzo());
	    addTab(paragraph, 7);
	    addTextSimpleBreak(paragraph.createRun(),
		    relSoggettoAbuso.getComuneResidenza() + " ("
			    + relSoggettoAbuso.getProvinciaResidenza() + "), "
			    + relSoggettoAbuso.getCap());
	    paragraph.createRun().addBreak();
	}
    }

    private void createHeader(XWPFParagraph paragraph) {
	InputStream is = WordService.class
		.getResourceAsStream("logoCondono.png");
	try {
	    paragraph.createRun().addPicture(is, Document.PICTURE_TYPE_PNG,
		    "logoCondono.png", Units.toEMU(262), Units.toEMU(66));
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
	// paragraphInfoDoc.createRun().addBreak();
	addTextBoldBreak(paragraphInfoDoc.createRun(), "NOTA BENE: ");
	// paragraphInfoDoc.createRun().addBreak();
	addTextBoldUnderlineBreak(
		paragraphInfoDoc.createRun(),
		"In difetto si procederà a norma di legge con un provvedimento di diniego della concessione in sanatoria e conseguente rimozione/demolizione dell'abuso con costi a carico del richiedente ovvero dell'acquisizione del bene al patrimonio dell'amministrazione.",
		false);

    }

    private void addTextBoldUnderlineBreak(XWPFRun run, String testo,
	    boolean isbreak) {

	run.setFontFamily("Times New Roman");
	run.setUnderline(UnderlinePatterns.SINGLE);
	run.setText(testo);
	run.setBold(true);
	if (isbreak)
	    run.addBreak();

    }

    private void addTextUnderline(XWPFRun run, String testo) {
	run.setFontFamily("Times New Roman");
	run.setUnderline(UnderlinePatterns.SINGLE);
	run.setText(testo);
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
