package it.soft.web;

import it.soft.dao.ComuniHome;
import it.soft.domain.Comune;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class ComuniController extends BaseController{

	@Autowired
	private ComuniHome home;

	@RequestMapping(value = "/listaComuni", method = RequestMethod.GET)
	public ModelAndView welcome(ModelMap model) {

		List<Comune> comuni = home.findAll();

		model.addAttribute("comuni", comuni);
		model.addAttribute("comuneDel", new Comune());

		return new ModelAndView("indexNew", model);
	}

	@RequestMapping(value = "/modificaComune", method = RequestMethod.GET)
	public ModelAndView modificaDatiUtente(HttpServletRequest request, HttpServletResponse response, Comune comune) {

		ModelMap model = new ModelMap();

		Comune com = home.findById(Integer.valueOf((String) request.getParameter("id")));
		model.addAttribute("comuneMod", com);

		return new ModelAndView("formModificaComune", model);
	}
	
	@RequestMapping(value = "/modificaComuneForm", method = RequestMethod.POST)
	public ModelAndView modificaDatiUtenteForm(HttpServletRequest request, HttpServletResponse response, Comune comune) {

		ModelMap model = new ModelMap();

		home.merge(comune);

		return new ModelAndView("redirect:welcome.htm", model);
	}

	@RequestMapping(value = "/addComune", method = RequestMethod.POST)
	public ModelAndView addUtente(HttpServletRequest request, HttpServletResponse response, Comune comune) {
		ModelMap model = new ModelMap();

		home.persist(comune);

		return new ModelAndView("redirect:welcome.htm", model);
	}

	@RequestMapping(value = "/removeComune", method = RequestMethod.GET)
	public ModelAndView removeUtente(HttpServletRequest request, HttpServletResponse response, Comune comune) {
		ModelMap model = new ModelMap();

		home.remove(home.findById(Integer.valueOf((String) request.getParameter("id"))));

		return new ModelAndView("redirect:welcome.htm", model);
	}

}