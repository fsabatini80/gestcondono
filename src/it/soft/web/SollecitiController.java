package it.soft.web;

import it.soft.dao.DatiAbusoHome;
import it.soft.dao.DatiPraticaHome;
import it.soft.dao.DatiSollecitoHome;
import it.soft.domain.DatiSollecito;
import it.soft.domain.Datiabuso;
import it.soft.domain.Utenti;
import it.soft.service.DatiAbusoService;
import it.soft.service.DatiPraticaService;
import it.soft.service.DatiSollecitiService;
import it.soft.service.WordService;
import it.soft.util.AuthenticationUtils;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiPraticaPojo;
import it.soft.web.pojo.DatiSollecitoPojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
public class SollecitiController extends BaseController {

    @Autowired
    DatiSollecitoHome datiSollecitoHome;
    @Autowired
    DatiPraticaHome datiPraticaHome;
    @Autowired
    DatiAbusoHome datiAbusoHome;

    @Autowired
    DatiSollecitiService datiSollecitiService;
    @Autowired
    DatiPraticaService datiPraticaService;
    @Autowired
    WordService wservice;
    @Autowired
    DatiAbusoService datiAbusoService;
    @Autowired
    AuthenticationUtils authenticationUtils;

    DatiSollecitoPojo datiSollecitoPojo;

    String idPratica;
    String idAbuso;

    @RequestMapping(value = "/solleciti", method = RequestMethod.GET)
    public ModelAndView solleciti(@RequestParam(value = "idpratica") String id,
	    @RequestParam(value = "idabuso") String idabuso, ModelMap model)
	    throws Exception {
	ResourceBundle.getBundle("it.soft.exception.error-messages_it_IT");
	String view = "table/sollecitiList";
	List<DatiSollecito> list = datiSollecitiService.findAll(id);
	this.idPratica = id;
	this.idAbuso = idabuso;
	initSollecito(model);
	model.addAttribute("solleciti", list);
	model.addAttribute("idpratica", this.idPratica);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/sollecitiListAll", method = RequestMethod.GET)
    public ModelAndView sollecitiListAll(ModelMap model) throws Exception {
	ResourceBundle.getBundle("it.soft.exception.error-messages_it_IT");
	String view = "table/sollecitiListAll";
	List<DatiSollecito> list = datiSollecitiService.findAll();
	model.addAttribute("solleciti", list);
	return new ModelAndView(view, model);
    }
    
    @RequestMapping(value = "/scadenzeListAll", method = RequestMethod.GET)
    public ModelAndView scadenzeListAll(ModelMap model) throws Exception {
	ResourceBundle.getBundle("it.soft.exception.error-messages_it_IT");
	String view = "table/scadenzeList";
	List<DatiSollecito> list = datiSollecitiService.getPraticheInScadenza();
	model.addAttribute("solleciti", list);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/nuovoSollecito", method = RequestMethod.GET)
    public ModelAndView nuovoSollecito(ModelMap model) throws Exception {
	String view = "form/formSollecito";
	DatiSollecitoPojo pojo = new DatiSollecitoPojo();
	initDatiSollecito(pojo);
	initSollecito(model);
	model.addAttribute("datiSollecitoPojo", pojo);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/salvaSollecito", method = RequestMethod.POST)
    public ModelAndView salvaSollecito(ModelMap model, DatiSollecitoPojo pojo,
	    Errors errors) throws Exception {

	String view = "redirect:solleciti.htm?idpratica="
		.concat(this.idPratica).concat("&idabuso=")
		.concat(this.idAbuso);
	this.datiSollecitoPojo = pojo;
	pojo.setIddatiPratica(datiPraticaHome.findById(Converter
		.stringToBigDdecimal(idPratica)));
	pojo.setIdAbuso(this.idAbuso);
	datiSollecitiService.save(pojo);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/removeSollecito", method = RequestMethod.GET)
    public ModelAndView removeSollecito(ModelMap model,
	    @RequestParam(value = "idSollecito") String id) throws Exception {

	String view = "redirect:solleciti.htm?idpratica="
		.concat(this.idPratica).concat("&idabuso=")
		.concat(this.idAbuso);
	datiSollecitiService.remove(id);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/modificaSollecito", method = RequestMethod.GET)
    public ModelAndView modificaSollecito(
	    @RequestParam(value = "idSollecito") String id, ModelMap model)
	    throws Exception {
	String view = "form/formSollecito";
	DatiSollecitoPojo pojo = datiSollecitiService.findById(id);
	initSollecito(model);
	model.addAttribute("datiSollecitoPojo", pojo);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/stampaSollecito1", method = RequestMethod.GET)
    public void stampaSollecito1(
	    @RequestParam(value = "idpratica") String idpratica,
	    @RequestParam(value = "idabuso") String idabuso,
	    @RequestParam(value = "progressivo") String progressivo,
	    HttpServletResponse response) {
	DatiPraticaPojo praticaDB = datiPraticaService.findById(idpratica);
	String docTitle = "Lett_Not_Prot_" + praticaDB.getNumeroProtocollo()
		+ "_Int_" + praticaDB.getNumeroPratica() + "_Sot_"
		+ progressivo + "_sollecito_1.docx";
	response.setHeader("Content-disposition", "attachment; filename="
		+ docTitle);
	response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	try {
	    XWPFDocument document = new XWPFDocument();
	    document = wservice.creaSollecito(document, true,
		    datiPraticaService, datiAbusoService, idpratica, idabuso,
		    progressivo);
	    document = wservice.createDoc(document, datiPraticaService,
		    datiAbusoService, idpratica, idabuso, progressivo,
		    datiSollecitiService.getDataStampa(idpratica, progressivo));
	    ServletOutputStream out = response.getOutputStream();
	    document.write(out);
	    out.flush();
	    out.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/stampaSollecito2", method = RequestMethod.GET)
    public void stampaSollecito2(
	    @RequestParam(value = "idpratica") String idpratica,
	    @RequestParam(value = "idabuso") String idabuso,
	    @RequestParam(value = "progressivo") String progressivo,
	    HttpServletResponse response) {
	DatiPraticaPojo praticaDB = datiPraticaService.findById(idpratica);
	String docTitle = "Lett_Not_Prot_" + praticaDB.getNumeroProtocollo()
		+ "_Int_" + praticaDB.getNumeroPratica() + "_Sot_"
		+ progressivo + "_sollecito_2.docx";
	response.setHeader("Content-disposition", "attachment; filename="
		+ docTitle);
	response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	try {
	    XWPFDocument document = new XWPFDocument();
	    document = wservice.creaSollecito(document, true,
		    datiPraticaService, datiAbusoService, idpratica, idabuso,
		    progressivo);
	    document = wservice.createDoc(document, datiPraticaService,
		    datiAbusoService, idpratica, idabuso, progressivo,
		    datiSollecitiService.getDataStampa(idpratica, progressivo));
	    ServletOutputStream out = response.getOutputStream();
	    document.write(out);
	    out.flush();
	    out.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private void initDatiSollecito(DatiSollecitoPojo pojo) {
	Utenti user = authenticationUtils.getUtente();
	pojo.setTecnicoIncaricato(user.getNome().concat(" ")
		.concat(user.getCognome()));
    }

    private void initSollecito(ModelMap model) {

	List<String> progressivi = new ArrayList<String>();
	List<Datiabuso> abusi = datiAbusoHome.findAll(datiPraticaHome
		.findById(BigDecimal.valueOf(Integer.parseInt(idPratica))));
	for (Datiabuso datiabuso : abusi) {
	    progressivi.add(String.valueOf(datiabuso.getProgressivo()));
	}
	model.addAttribute("progressivi", progressivi);
    }
}
