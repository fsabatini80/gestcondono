package it.soft.web;

import it.soft.dao.LeggiCondonoHome;
import it.soft.domain.Datipratica;
import it.soft.domain.LeggiCondono;
import it.soft.service.DatiPraticaService;
import it.soft.web.pojo.DatiPraticaPojo;
import it.soft.web.pojo.RicercaPraticaPojo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePraticaController extends BaseController {

	@Autowired
	LeggiCondonoHome leggiCondonoHome;
	@Autowired
	DatiPraticaService datiPraticaService;

	@RequestMapping(value = "/homePratica", method = RequestMethod.GET)
	public ModelAndView home(ModelMap model) {

		return new ModelAndView("homePratiche", model);
	}

	@RequestMapping(value = "/ricercaPratica", method = RequestMethod.GET)
	public ModelAndView ricercaPratica(ModelMap model) {
		RicercaPraticaPojo pojo = new RicercaPraticaPojo();
		model.addAttribute("leggiList", leggiCondonoHome.findAll());
		model.addAttribute("ricercaPraticaPojo", pojo);
		return new ModelAndView("form/formRicercaPratica", model);
	}

	@RequestMapping(value = "/eseguiRicerca", method = RequestMethod.GET)
	public ModelAndView eseguiRicercaPratiche(ModelMap model,
			RicercaPraticaPojo pojo, Errors errors) throws Exception {

		String view = "table/praticheList";
		LeggiCondono condono = null;
		if (pojo.getLeggeCondono() != null
				&& !"".equals(pojo.getLeggeCondono()))
			condono = leggiCondonoHome.findById(Integer.valueOf(pojo
					.getLeggeCondono()));
		List<Datipratica> list = datiPraticaService.findBy(
				pojo.getNumeroPratica(), condono, pojo.getDataDomanda(),
				pojo.getNumeroProtocollo());
		model.addAttribute("pratiche", list);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/nuovaPraticaHome", method = RequestMethod.GET)
	public ModelAndView nuovaPraticaHome(ModelMap model) throws Exception {

		String view = "form/formPratica";
		DatiPraticaPojo datiPraticaPojo = new DatiPraticaPojo();
		model.addAttribute("datiPraticaPojo", datiPraticaPojo);
		return new ModelAndView(view, model);
	}
}
