package it.soft.util;

import java.math.BigInteger;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

public class UtilityWord {

    private static String FONT_FAMILY = "Times New Roman";// "Times New Roman";

    public static void addTextBold(XWPFRun run, String testo) {
	run.setFontFamily(FONT_FAMILY);
	run.setText(testo);
	run.setBold(true);
    }

    public static void addTextBoldBreak(XWPFRun run, String testo) {
	run.setFontFamily(FONT_FAMILY);
	run.setText(testo);
	run.setBold(true);
	run.addBreak();
    }

    public static void addTextSimple(XWPFRun run, String testo) {
	run.setFontFamily(FONT_FAMILY);
	run.setText(testo);
    }

    public static void addTextSimpleBreak(XWPFRun run, String testo) {
	run.setFontFamily(FONT_FAMILY);
	run.setText(testo);
	run.addBreak();
    }

    public static void addTab(XWPFParagraph parag, int numeroTab) {
	for (int i = 0; i < numeroTab; i++) {
	    parag.createRun().addTab();
	}
    }

    public static XWPFParagraph addTableCell(XWPFTableCell tableCell,
	    String testo, boolean bold, ParagraphAlignment alignment,
	    Integer cellWidth, Integer size) {
	// IBody body = tableCell.getBodyElements().get(0).getBody();
	// XWPFParagraph parCell = new XWPFParagraph(CTP.Factory.newInstance(),
	// body);
	XWPFParagraph parCell = tableCell.addParagraph();
	// tableCell.setVerticalAlignment(XWPFVertAlign.CENTER);
	parCell.setAlignment(alignment);
	XWPFRun run = parCell.createRun();
	run.setFontFamily(FONT_FAMILY);
	run.setFontSize(size);
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
	tableCell.getCTTc().addNewTcPr().addNewTcW()
		.setW(BigInteger.valueOf(cellWidth));
	return parCell;
    }

    public static XWPFParagraph addTableCellCenter(XWPFTableCell tableCell,
	    String testo, boolean bold, ParagraphAlignment alignment) {
	// IBody body = tableCell.getBodyElements().get(0).getBody();
	// XWPFParagraph parCell = new XWPFParagraph(CTP.Factory.newInstance(),
	// body);
	XWPFParagraph parCell = tableCell.addParagraph();
	// tableCell.setVerticalAlignment(XWPFVertAlign.CENTER);
	parCell.setAlignment(alignment);
	XWPFRun run = parCell.createRun();
	run.setFontFamily(FONT_FAMILY);
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

    public static void spanCellsAcrossRow(XWPFTable table, int rowNum,
	    int colNum, int span) {
	XWPFTableCell cell = table.getRow(rowNum).getCell(colNum);
	cell.getCTTc().addNewTcPr().addNewGridSpan()
		.setVal(BigInteger.valueOf((long) span));
    }

    public static void spanCellsAcrossCell(XWPFTable table, int rowNum,
	    int colNum, int numberCellToMerge) {
	XWPFTableCell cell = table.getRow(rowNum).getCell(colNum);
	for (int i = 0; i < numberCellToMerge; i++) {
	    cell = table.getRow(i).getCell(colNum);
	    if (i == 0)
		cell.getCTTc().addNewTcPr().addNewVMerge()
			.setVal(STMerge.RESTART);
	    else
		cell.getCTTc().addNewTcPr().addNewVMerge()
			.setVal(STMerge.CONTINUE);
	}
    }

    @SuppressWarnings("deprecation")
    public static void addTextBoldBreakCenter(XWPFRun run, String testo) {
	run.setFontFamily(FONT_FAMILY);
	run.getParagraph().setAlignment(ParagraphAlignment.CENTER);
	run.setText(testo);
	run.setFontSize(11);
	run.setBold(true);
	// run.addBreak();
    }

    @SuppressWarnings("deprecation")
    public static void addText(XWPFRun run, String testo, boolean bold,
	    ParagraphAlignment alignP, boolean breakR) {
	run.setFontFamily(FONT_FAMILY);
	run.getParagraph().setAlignment(alignP);
	run.setText(testo);
	run.setFontSize(11);
	run.setBold(bold);
	if (breakR)
	    run.addBreak();
    }

    @SuppressWarnings("deprecation")
    public static void addText(XWPFRun run, String testo, boolean bold,
	    ParagraphAlignment alignP, boolean breakR, boolean isItalic,
	    int size) {
	run.setFontFamily(FONT_FAMILY);
	run.getParagraph().setAlignment(alignP);
	run.setText(testo);
	run.setFontSize(11);
	run.setBold(bold);
	if (breakR)
	    run.addBreak();
	run.setItalic(isItalic);
	run.setFontSize(size);
    }

    public static void addTextBoldUnderlineBreak(XWPFRun run, String testo,
	    boolean isbreak) {
	run.setFontFamily(FONT_FAMILY);
	run.setUnderline(UnderlinePatterns.SINGLE);
	run.setText(testo);
	run.setBold(true);
	if (isbreak)
	    run.addBreak();
    }

    public static void addTextUnderline(XWPFRun run, String testo) {
	run.setFontFamily(FONT_FAMILY);
	run.setUnderline(UnderlinePatterns.SINGLE);
	run.setText(testo);
    }

    public static void adjustTableWidth(XWPFTable table) {
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

    public static String concatStringVirgola(String string, boolean last) {
	if (string != null && string.length() > 0) {
	    if (!last)
		return string + ",";
	    else
		return string;
	}
	return "";
    }
}
