package it.soft.web;

import it.soft.dao.UtentiHome;
import it.soft.domain.Utenti;
import it.soft.exception.CustomException;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class HomeController extends BaseController {

    @Autowired
    private UtentiHome utentiHome;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home(ModelMap model) throws Exception {

	User user = (User) SecurityContextHolder.getContext()
		.getAuthentication().getPrincipal();
	String name = user.getUsername();
	Utenti utenti = utentiHome.findByUser(name);
	List<Utenti> utentiAll = utentiHome.findAll();

	model.addAttribute("utenti", utenti);
	model.addAttribute("utentiAll", utentiAll);
	model.addAttribute("utenteDel", new Utenti());
	model.addAttribute("utenteNew", new Utenti());
	model.addAttribute("existScadenze", existScadenze());

	return new ModelAndView("home", model);
    }

    @RequestMapping(value = "/modificaUtente", method = RequestMethod.POST)
    public ModelAndView modificaDatiUtente(HttpServletRequest request,
	    HttpServletResponse response, Utenti utente) throws Exception {

	ModelMap model = new ModelMap();

	User user = (User) SecurityContextHolder.getContext()
		.getAuthentication().getPrincipal();
	String name = user.getUsername();
	Utenti utenti = utentiHome.findByUser(name);
	utente.setIdUtenti(utenti.getIdUtenti());
	utentiHome.merge(utente);

	return new ModelAndView("redirect:home.htm", model);
    }

    @RequestMapping(value = "/addUtente", method = RequestMethod.POST)
    public ModelAndView addUtente(HttpServletRequest request,
	    HttpServletResponse response, Utenti u) throws Exception {
	ModelMap model = new ModelMap();

	utentiHome.persist(u);

	return new ModelAndView("redirect:home.htm", model);
    }

    @RequestMapping(value = "/removeUtente", method = RequestMethod.GET)
    public ModelAndView removeUtente(HttpServletRequest request,
	    HttpServletResponse response,
	    @RequestParam(value = "user") String id) throws Exception {
	ModelMap model = new ModelMap();
	try {
	    utentiHome.remove(utentiHome.findById(new BigDecimal(id)));
	} catch (Exception e) {
	    throw new CustomException("99", e.getMessage());
	}

	return new ModelAndView("redirect:home.htm", model);
    }

}
