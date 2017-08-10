package it.soft.web;

import it.soft.dao.DatiAbusoHome;
import it.soft.dao.DatiPraticaHome;
import it.soft.dao.DatiVersamentiHome;
import it.soft.domain.DatiVersamento;
import it.soft.domain.Datiabuso;
import it.soft.service.DatiVersamentiService;
import it.soft.web.pojo.DatiVersamentiPojo;
import it.soft.web.validator.DatiVersamentiValidator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class VersamentiController extends BaseController {

	@Autowired
	DatiVersamentiHome datiVersamentiHome;
	@Autowired
	DatiPraticaHome datiPraticaHome;
	@Autowired
	DatiAbusoHome datiAbusoHome;

	@Autowired
	DatiVersamentiService versamentiService;

	@Autowired
	DatiVersamentiValidator versamentiValidator;

	DatiVersamentiPojo datiVersamentiPojo;

	String idPratica;

	@RequestMapping(value = "/versamenti", method = RequestMethod.GET)
	public ModelAndView versamenti(
			@RequestParam(value = "idpratica") String id, ModelMap model)
			throws Exception {

		String view = "table/versamentiList";
		// ricerca versamenti
		List<DatiVersamento> list = datiVersamentiHome.findAll(BigInteger
				.valueOf(Integer.valueOf(id)));
		this.idPratica = id;
		initVersamento(model);
		model.addAttribute("versamenti", list);
		model.addAttribute("idpratica", this.idPratica);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/nuovoVersamento", method = RequestMethod.GET)
	public ModelAndView nuovaVersamento(ModelMap model) throws Exception {
		initVersamento(model);
		String view = "form/formVersamento";
		DatiVersamentiPojo pojo = new DatiVersamentiPojo();
		model.addAttribute("datiVersamentiPojo", pojo);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/salvaVersamento", method = RequestMethod.POST)
	public ModelAndView salvaVersamento(ModelMap model,
			DatiVersamentiPojo pojo, Errors errors) throws Exception {

		String view = "redirect:versamenti.htm?idpratica=";
		this.datiVersamentiPojo = pojo;
		versamentiValidator.validate(pojo, errors);
		if (errors.hasFieldErrors()) {
			initVersamento(model);
			view = "form/formVersamento";
		} else {
			pojo.setIddatipratica(this.idPratica);
			versamentiService.persist(pojo);
			view = view.concat(pojo.getIddatipratica());
		}
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/removeVersamento", method = RequestMethod.GET)
	public ModelAndView removeVersamento(ModelMap model,
			@RequestParam(value = "idVersamento") String id) throws Exception {

		String view = "redirect:versamenti.htm?idpratica="
				.concat(this.idPratica);
		versamentiService.remove(id);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/modificaVersamento", method = RequestMethod.GET)
	public ModelAndView modificaVersamento(
			@RequestParam(value = "idVersamento") String id, ModelMap model)
			throws Exception {
		String view = "form/formVersamento";
		initVersamento(model);
		DatiVersamentiPojo pojo = versamentiService.findById(id);
		model.addAttribute("datiVersamentiPojo", pojo);
		return new ModelAndView(view, model);
	}

	private void initVersamento(ModelMap model) {

		List<String> progressivi = new ArrayList<String>();
		List<Datiabuso> abusi = datiAbusoHome.findAll(datiPraticaHome
				.findById(BigDecimal.valueOf(Integer.parseInt(idPratica))));
		for (Datiabuso datiabuso : abusi) {
			progressivi.add(String.valueOf(datiabuso.getProgressivo()));
		}
		model.addAttribute("progressivi", progressivi);
	}

}