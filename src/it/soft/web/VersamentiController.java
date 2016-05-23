package it.soft.web;

import it.soft.dao.CaratteristicheHome;
import it.soft.dao.ComuniHome;
import it.soft.dao.DatiPraticaHome;
import it.soft.dao.DestinazioneUsoHome;
import it.soft.dao.EpocaAbusoHome;
import it.soft.dao.LeggiCondonoHome;
import it.soft.dao.SoggettiAbusoHome;
import it.soft.dao.TipoAlloggioHome;
import it.soft.dao.TipoOperaHome;
import it.soft.dao.TipologiaAbusoHome;
import it.soft.dao.UtentiHome;
import it.soft.domain.CaratteristicheSpeciali;
import it.soft.domain.Datipratica;
import it.soft.domain.EpocaAbuso;
import it.soft.domain.SoggettiAbuso;
import it.soft.domain.TipoOpera;
import it.soft.domain.TipologiaAbuso;
import it.soft.domain.TipologiaAlloggio;
import it.soft.domain.TipologiaDestinazioneUso;
import it.soft.domain.Utenti;
import it.soft.service.DatiAbusoService;
import it.soft.service.DatiPraticaService;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiPraticaPojo;
import it.soft.web.validator.DatiAbusoValidator;
import it.soft.web.validator.DatiPraticaValidator;

import java.math.BigDecimal;
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
public class VersamentiController extends BaseController {

	@Autowired
	DatiPraticaHome datiPraticaHome;
	@Autowired
	LeggiCondonoHome leggiCondonoHome;
	@Autowired
	DestinazioneUsoHome destinazioneUsoHome;
	@Autowired
	EpocaAbusoHome epocaAbusoHome;
	@Autowired
	TipoOperaHome tipoOperaHome;
	@Autowired
	TipologiaAbusoHome tipologiaAbusoHome;
	@Autowired
	UtentiHome utentiHome;
	@Autowired
	ComuniHome comuniHome;
	@Autowired
	TipoAlloggioHome tipoAlloggioHome;
	@Autowired
	CaratteristicheHome caratteristicheHome;
	@Autowired
	SoggettiAbusoHome soggettiAbusoHome;

	@Autowired
	DatiPraticaValidator validatorPratica;
	@Autowired
	DatiAbusoValidator datiAbusoValidator;

	@Autowired
	DatiPraticaService datiPraticaService;
	@Autowired
	DatiAbusoService datiAbusoService;

	DatiPraticaPojo praticaPojo;
	DatiAbusoPojo abusoPojo;
	String idDatiAlloggio;
	List<String> comuni;
	List<String> province;
	List<TipologiaDestinazioneUso> tipologiaDestinazioneUsos;
	List<EpocaAbuso> epocaAbusos;
	List<TipoOpera> tipoOperas;
	List<TipologiaAbuso> tipologiaAbusos;
	List<TipologiaAlloggio> tipologiaAlloggios;
	List<CaratteristicheSpeciali> caratteristicheSpecialis;
	List<SoggettiAbuso> soggettiAbusos;

	@RequestMapping(value = "/versamenti", method = RequestMethod.GET)
	public ModelAndView versamenti(@RequestParam(value = "idpratica") String id,
			ModelMap model) throws Exception {

		String view = "table/praticheList";
		Datipratica source = datiPraticaHome.findById(BigDecimal
				.valueOf(Integer.parseInt(id)));
		List<Datipratica> list = new ArrayList<Datipratica>();
		list.add(source);
		model.addAttribute("pratiche", list);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/nuovoVersamento", method = RequestMethod.GET)
	public ModelAndView nuovaVersamento(ModelMap model) throws Exception {

		String view = "form/formPratica";
		DatiPraticaPojo datiPraticaPojo = new DatiPraticaPojo();
		model.addAttribute("datiPraticaPojo", datiPraticaPojo);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/salvaVersamento", method = RequestMethod.POST)
	public ModelAndView salvaPratica(ModelMap model, DatiPraticaPojo pojo,
			Errors errors) throws Exception {

		String view = "redirect:pratica.htm?idpratica=";
		validatorPratica.validate(pojo, errors);
		this.praticaPojo = pojo;
		if (errors.hasFieldErrors()) {
			view = "form/formPratica";
		} else {
			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String name = user.getUsername();
			Utenti utenti = utentiHome.findByUser(name);
			pojo.setIdutente(String.valueOf(utenti.getIdUtenti()));
			datiPraticaService.saveDatiPratica(pojo);
			view = view.concat(pojo.getIddatipratica());
		}
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/modificaVersamento", method = RequestMethod.GET)
	public ModelAndView modificaPratica(
			@RequestParam(value = "idpratica") String id, ModelMap model)
			throws Exception {
		this.praticaPojo = datiPraticaService.findById(id);
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("form/formPratica", model);
	}

}