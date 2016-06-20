import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class CreateParagraph {
	public static void main(String[] args) throws Exception {

		// Blank Document
		XWPFDocument document = new XWPFDocument();
		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File(
				"createparagraph.docx"));

//		WordService.createPage1(document);
//		WordService.createPage2(document);
//		WordService.createPage3(document);
//		WordService.createPage4(document);

		document.write(out);
		out.close();
		System.out.println("createparagraph.docx written successfully");
	}

}