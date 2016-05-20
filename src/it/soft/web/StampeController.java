package it.soft.web;

import it.soft.service.DatiAbusoService;
import it.soft.service.DatiPraticaService;
import it.soft.service.WordService;

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
			HttpServletResponse response) {
		XWPFDocument document = new XWPFDocument();
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		try {
			document = wservice.createDoc(document, datiPraticaService,
					datiAbusoService, idpratica, idabuso);
			ServletOutputStream out = response.getOutputStream();
			document.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
