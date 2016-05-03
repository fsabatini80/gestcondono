package it.soft.web;

import it.soft.dao.ComuniHome;
import it.soft.dao.LeggiCondonoHome;
import it.soft.dao.UtentiHome;
import it.soft.domain.Comune;
import it.soft.domain.Utenti;
import it.soft.service.DatiPraticaService;
import it.soft.web.pojo.DatiPraticaPojo;
import it.soft.web.validator.DatiPraticaValidator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DatiPraticaWizardController extends BaseController {

	@Autowired
	LeggiCondonoHome leggiCondonoHome;
	@Autowired
	DatiPraticaValidator validatorPratica;
	@Autowired
	DatiPraticaService datiPraticaService;
	@Autowired
	UtentiHome utentiHome;
	@Autowired
	ComuniHome comuniHome;

	DatiPraticaPojo d;
	List<String> comuni;
	List<String> province;

	@RequestMapping(value = "/insPraticawzd", method = RequestMethod.GET)
	public ModelAndView page1(
			@RequestParam(value = "_target1", required = false) String next,
			@RequestParam(value = "_cancel", required = false) String cancel,
			ModelMap model, DatiPraticaPojo pojo, Errors errors)
			throws Exception {

		String view = "redirect:welcome.htm";
		if (next != null) {
			view = "wizard/DatiAbusoForm";
			validatorPratica.validate(d, errors);
			if (errors.hasFieldErrors()) {
				view = "wizard/DatiPraticaForm";
			} else {
				User user = (User) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				String name = user.getUsername();
				Utenti utenti = utentiHome.findByUser(name);
				pojo.setIdutente(String.valueOf(utenti.getIdUtenti()));
				datiPraticaService.saveDatiPratica(pojo);
			}
			this.d = pojo;
			initModel(model);
		}
		if (next == null && cancel == null) {
			view = "wizard/DatiPraticaForm";
			initModel(model);
		}
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/page2", method = RequestMethod.GET)
	public ModelAndView page2(
			@RequestParam(value = "_target1", required = false) String previous,
			@RequestParam(value = "_target2", required = false) String next,
			@RequestParam(value = "_cancel", required = false) String cancel,
			ModelMap model, DatiPraticaPojo d) throws Exception {
		initModel(model);
		String view = "redirect:welcome.htm";
		if (previous != null) {
			view = "wizard/DatiPraticaForm";
			model.addAttribute("datiPraticaPojo", this.d);
		} else if (next != null) {
			view = "wizard/VersamentiForm";
			model.addAttribute("datiPraticaPojo", this.d);
		}
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/page3", method = RequestMethod.GET)
	public ModelAndView page3(
			@RequestParam(value = "_target2", required = false) String previous,
			@RequestParam(value = "_target3", required = false) String next,
			@RequestParam(value = "_cancel", required = false) String cancel,
			ModelMap model, DatiPraticaPojo d) throws Exception {
		String view = "redirect:welcome.htm";
		initModel(model);
		if (previous != null) {
			view = "wizard/DatiAbusoForm";
			model.addAttribute("datiPraticaPojo", this.d);
		} else if (next != null) {
			view = "wizard/TipiDocumentoForm";
			model.addAttribute("datiPraticaPojo", this.d);
		}
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/page4", method = RequestMethod.GET)
	public ModelAndView page6(
			@RequestParam(value = "_target4", required = false) String previous,
			@RequestParam(value = "_finish", required = false) String next,
			@RequestParam(value = "_cancel", required = false) String cancel,
			ModelMap model, DatiPraticaPojo d) throws Exception {
		String view = "redirect:welcome.htm";
		if (previous != null) {
			view = "wizard/VersamentiForm";
		} else if (next != null) {
			view = "wizard/ResultForm";
		}
		model.put("datiPratica", d);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/finish", method = RequestMethod.GET)
	public ModelAndView finish(ModelMap model) throws Exception {

		return new ModelAndView("wizard/ResultForm", model);
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(ModelMap model) {

		return new ModelAndView("redirect:welcome.htm", model);
	}

	private void initModel(ModelMap model) {
		if (!model.containsAttribute("comuniList"))
			model.addAttribute("comuniList", getComuni());
		if (!model.containsAttribute("provinceList"))
			model.addAttribute("provinceList", getProvince());
		if (!model.containsAttribute("leggiList"))
			model.addAttribute("leggiList", leggiCondonoHome.findAll());
	}

	private List<String> getComuni() {
		if (comuni != null)
			return comuni;
		comuni = new ArrayList<String>();
		comuni.add("");
		List<Comune> list = comuniHome.findAll();
		for (Comune comune : list) {
			comuni.add(comune.getComune());
		}
		return comuni;
	}

	private List<String> getProvince() {
		if (province != null)
			return province;
		province = new ArrayList<String>();
		province.add("");
		List<Comune> list = comuniHome.findAll();
		for (Comune comune : list) {
			if (!province.contains(comune.getProvincia()))
				province.add(comune.getProvincia());
		}
		return province;
	}
}