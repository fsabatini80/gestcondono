package it.soft.web;

import it.soft.domain.StampeSolleciti;
import it.soft.service.DatiAbusoService;
import it.soft.service.DatiPraticaService;
import it.soft.service.StampeSollecitiService;
import it.soft.service.WordService;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiPraticaPojo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StampeController extends BaseController {

    @Autowired
    WordService wservice;
    @Autowired
    DatiPraticaService datiPraticaService;
    @Autowired
    DatiAbusoService datiAbusoService;
    @Autowired
    StampeSollecitiService stampeSollecitiService;

    @RequestMapping(value = "/stampaLettera", method = RequestMethod.GET)
    public void stampaLettera(
	    @RequestParam(value = "idpratica") String idpratica,
	    @RequestParam(value = "idabuso") String idabuso,
	    @RequestParam(value = "progressivo") String progressivo,
	    HttpServletResponse response) {
	DatiPraticaPojo praticaDB = datiPraticaService.findById(idpratica);
	String docTitle = "Lett_Not_Prot_" + praticaDB.getNumeroProtocollo()
		+ "_Int_" + praticaDB.getNumeroPratica() + "_Sot_" + progressivo + ".docx";
	response.setHeader("Content-disposition", "attachment; filename="
		+ docTitle);
	response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	try {
	    XWPFDocument document = new XWPFDocument();
	    document = wservice.createDoc(document, datiPraticaService,
		    datiAbusoService, idpratica, idabuso, progressivo);
	    ServletOutputStream out = response.getOutputStream();
	    document.write(out);
	    out.flush();
	    out.close();

	    saveToDB(idpratica, idabuso, progressivo, document);
	} catch (Exception e) {
	    e.printStackTrace();
	    String view = "abusiError.htm?idpratica=".concat(idpratica)
		    .concat("&idabuso=").concat(idabuso);
	    try {
		response.sendRedirect(view);
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }
	}
    }

    @RequestMapping(value = "/stampe", method = RequestMethod.GET)
    public ModelAndView stampeList(ModelMap model, DatiPraticaPojo pojo,
	    Errors errors) {
	String view = "homeStampe";
	List<StampeSolleciti> list = stampeSollecitiService.findAll();
	model.addAttribute("stampe", list);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(@RequestParam(value = "idfile") String idfile,
	    HttpServletResponse response) {
	response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	try {
	    StampeSolleciti stampeSolleciti = stampeSollecitiService
		    .findById(idfile);
	    byte[] bytearray = stampeSolleciti.getFile();
	    byte[] decompressedFile = decompress(bytearray);
	    ServletOutputStream out = response.getOutputStream();
	    out.write(decompressedFile);
	    out.flush();
	    out.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    String view = "stampe.htm";
	    try {
		response.sendRedirect(view);
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }
	}
    }

    private void saveToDB(String idpratica, String idabuso, String progressivo,
	    XWPFDocument document) {
	DatiPraticaPojo praticaDB = datiPraticaService.findById(idpratica);
	String docTitle = "Lett. Not. Prot. " + praticaDB.getNumeroProtocollo()
		+ " Int. " + idpratica + " Sot. " + progressivo;
	StampeSolleciti stampeSolleciti = new StampeSolleciti();
	stampeSolleciti.setNomeFileStampa(docTitle);
	List<StampeSolleciti> list = stampeSollecitiService
		.search(stampeSolleciti);
	if (list == null || list.isEmpty()) {
	    try {
		bind(idpratica, idabuso, progressivo, stampeSolleciti, document);
		stampeSollecitiService.save(stampeSolleciti);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    stampeSollecitiService.save(stampeSolleciti);
	}
    }

    private void bind(String idpratica, String idabuso, String progressivo,
	    StampeSolleciti stampeSolleciti, XWPFDocument document)
	    throws IOException {
	stampeSolleciti.setDataInserimento(Converter.dateToString(new Date()));
	stampeSolleciti.setFile(zipByteArray(document));
	stampeSolleciti.setIdAbuso(Converter.stringToBigDdecimal(idabuso));
	stampeSolleciti.setIdPratica(Converter.stringToBigDdecimal(idpratica));
	stampeSolleciti.setProgressivoAbuso(Converter.stringToint(progressivo));
    }

    private byte[] zipByteArray(XWPFDocument document) throws IOException {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	document.write(baos);
	return compress(baos.toByteArray());
    }

    private byte[] compress(byte[] data) throws IOException {
	Deflater deflater = new Deflater();
	deflater.setInput(data);
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
		data.length);
	deflater.finish();
	byte[] buffer = new byte[1024];
	while (!deflater.finished()) {
	    int count = deflater.deflate(buffer); // returns the generated
						  // code... index
	    outputStream.write(buffer, 0, count);
	}
	outputStream.close();
	byte[] output = outputStream.toByteArray();
	System.out.println("Original: " + data.length / 1024 + " Kb");
	System.out.println("Compressed: " + output.length / 1024 + " Kb");
	return output;
    }

    private byte[] decompress(byte[] data) throws IOException,
	    DataFormatException {
	Inflater inflater = new Inflater();
	inflater.setInput(data);
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
		data.length);
	byte[] buffer = new byte[2048];
	while (!inflater.finished()) {
	    int count = inflater.inflate(buffer);
	    outputStream.write(buffer, 0, count);
	}
	outputStream.close();
	byte[] output = outputStream.toByteArray();
	System.out.println("Original: " + data.length);
	System.out.println("Compressed: " + output.length);
	return output;
    }
}
