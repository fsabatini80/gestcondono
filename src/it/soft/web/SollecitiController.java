package it.soft.web;

import it.soft.dao.DatiAbusoHome;
import it.soft.dao.DatiPraticaHome;
import it.soft.dao.DatiSollecitoHome;
import it.soft.domain.DatiSollecito;
import it.soft.domain.Datiabuso;
import it.soft.service.DatiSollecitiService;
import it.soft.web.pojo.DatiSollecitoPojo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

    // @Autowired
    // DatiVersamentiValidator versamentiValidator;

    DatiSollecitoPojo datiSollecitoPojo;

    String idPratica;

    @RequestMapping(value = "/solleciti", method = RequestMethod.GET)
    public ModelAndView solleciti(@RequestParam(value = "idpratica") String id,
	    ModelMap model) throws Exception {

	String view = "table/sollecitiList";
	// ricerca versamenti
	List<DatiSollecito> list = datiSollecitoHome.findAll(BigInteger
		.valueOf(Integer.valueOf(id)));
	this.idPratica = id;
	initSollecito(model);
	model.addAttribute("solleciti", list);
	model.addAttribute("idpratica", this.idPratica);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/nuovoSollecito", method = RequestMethod.GET)
    public ModelAndView nuovoSollecito(ModelMap model) throws Exception {
	initSollecito(model);
	String view = "form/formSollecito";
	DatiSollecitoPojo pojo = new DatiSollecitoPojo();
	model.addAttribute("datiSollecitoPojo", pojo);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/salvaSollecito", method = RequestMethod.POST)
    public ModelAndView salvaSollecito(ModelMap model, DatiSollecitoPojo pojo,
	    Errors errors) throws Exception {

	String view = "redirect:solleciti.htm?idpratica="
		.concat(this.idPratica);
	this.datiSollecitoPojo = pojo;
	pojo.setIddatiPratica(this.idPratica);
	// versamentiValidator.validate(pojo, errors);
	// if (errors.hasFieldErrors()) {
	// initSollecito(model);
	// view = "form/formSollecito";
	// } else {
	// pojo.setIddatipratica(this.idPratica);
	// versamentiService.persist(pojo);
	// view = view.concat(pojo.getIddatipratica());
	// }
	datiSollecitiService.save(pojo);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/removeSollecito", method = RequestMethod.GET)
    public ModelAndView removeSollecito(ModelMap model,
	    @RequestParam(value = "idSollecito") String id) throws Exception {

	String view = "redirect:solleciti.htm?idpratica="
		.concat(this.idPratica);
	datiSollecitiService.remove(id);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/modificaSollecito", method = RequestMethod.GET)
    public ModelAndView modificaSollecito(
	    @RequestParam(value = "idSollecito") String id, ModelMap model)
	    throws Exception {
	String view = "form/formSollecito";
	initSollecito(model);
	DatiSollecitoPojo pojo = datiSollecitiService.findById(id);
	model.addAttribute("datiSollecitoPojo", pojo);
	return new ModelAndView(view, model);
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
