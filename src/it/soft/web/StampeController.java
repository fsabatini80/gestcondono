package it.soft.web;

import it.soft.service.DatiAbusoService;
import it.soft.service.DatiPraticaService;
import it.soft.service.WordService;
import it.soft.web.pojo.DatiPraticaPojo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StampeController extends BaseController {

    @Autowired
    WordService wservice;
    @Autowired
    DatiPraticaService datiPraticaService;
    @Autowired
    DatiAbusoService datiAbusoService;

    @RequestMapping(value = "/stampaLettera", method = RequestMethod.GET)
    public void stampaLettera(
	    @RequestParam(value = "idpratica") String idpratica,
	    @RequestParam(value = "idabuso") String idabuso,
	    @RequestParam(value = "progressivo") String progressivo,
	    HttpServletResponse response) {
	DatiPraticaPojo praticaDB = datiPraticaService.findById(idpratica);
	String docTitle = "Lett. Not. Prot. " + praticaDB.getNumeroProtocollo()
		+ " Int. " + idpratica + "Sot. " + progressivo;
	response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	try {
	    XWPFDocument document = new XWPFDocument();
	    document = wservice.createDoc(document, datiPraticaService,
		    datiAbusoService, idpratica, idabuso, progressivo);
	    ServletOutputStream out = response.getOutputStream();
	    document.write(out);
	    out.flush();
	    out.close();
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
}
