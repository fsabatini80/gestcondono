package it.soft.web;

import it.soft.dao.CaratteristicheHome;
import it.soft.dao.ComuniHome;
import it.soft.dao.DestinazioneUsoHome;
import it.soft.dao.EpocaAbusoHome;
import it.soft.dao.LeggiCondonoHome;
import it.soft.dao.TipoAlloggioHome;
import it.soft.dao.TipoOperaHome;
import it.soft.dao.TipologiaAbusoHome;
import it.soft.dao.UtentiHome;
import it.soft.domain.CaratteristicheSpeciali;
import it.soft.domain.Comune;
import it.soft.domain.DatiAlloggio;
import it.soft.domain.Datiabuso;
import it.soft.domain.Datipratica;
import it.soft.domain.EpocaAbuso;
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

import java.math.BigInteger;
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
public class PraticheController extends BaseController {

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
	DatiPraticaValidator validatorPratica;
	@Autowired
	DatiAbusoValidator datiAbusoValidator;

	@Autowired
	DatiPraticaService datiPraticaService;
	@Autowired
	DatiAbusoService datiAbusoService;

	DatiPraticaPojo praticaPojo;
	DatiAbusoPojo abusoPojo;
	List<String> comuni;
	List<String> province;
	List<TipologiaDestinazioneUso> tipologiaDestinazioneUsos;
	List<EpocaAbuso> epocaAbusos;
	List<TipoOpera> tipoOperas;
	List<TipologiaAbuso> tipologiaAbusos;
	List<TipologiaAlloggio> tipologiaAlloggios;
	List<CaratteristicheSpeciali> caratteristicheSpecialis;

	@RequestMapping(value = "/pratiche", method = RequestMethod.GET)
	public ModelAndView praticheHome(ModelMap model, DatiPraticaPojo pojo,
			Errors errors) throws Exception {

		String view = "table/praticheList";
		List<Datipratica> list = datiPraticaService.findAll();
		model.addAttribute("pratiche", list);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/nuovaPratica", method = RequestMethod.GET)
	public ModelAndView nuovaPratica(ModelMap model) throws Exception {

		String view = "form/formPratica";
		DatiPraticaPojo datiPraticaPojo = new DatiPraticaPojo();
		initModel(model);
		model.addAttribute("datiPraticaPojo", datiPraticaPojo);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/salvaPratica", method = RequestMethod.POST)
	public ModelAndView salvaPratica(ModelMap model, DatiPraticaPojo pojo,
			Errors errors) throws Exception {

		String view = "redirect:pratiche.htm";
		validatorPratica.validate(pojo, errors);
		this.praticaPojo = pojo;
		if (errors.hasFieldErrors()) {
			initModel(model);
			view = "form/formPratica";
		} else {
			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String name = user.getUsername();
			Utenti utenti = utentiHome.findByUser(name);
			pojo.setIdutente(String.valueOf(utenti.getIdUtenti()));
			datiPraticaService.saveDatiPratica(pojo);
		}
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/modificaPratica", method = RequestMethod.GET)
	public ModelAndView modificaPratica(
			@RequestParam(value = "idpratica") String id, ModelMap model)
			throws Exception {
		initModel(model);
		this.praticaPojo = datiPraticaService.findById(id);
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("form/formPratica", model);
	}

	@RequestMapping(value = "/abusi", method = RequestMethod.GET)
	public ModelAndView abusi(@RequestParam(value = "idpratica") String id,
			ModelMap model) throws Exception {
		String view = "table/abusiList";
		List<Datiabuso> list = datiAbusoService.findAll(id);
		this.praticaPojo = datiPraticaService.findById(id);
		model.addAttribute("abusi", list);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/nuovoAbuso", method = RequestMethod.GET)
	public ModelAndView nuovoAbuso(ModelMap model) throws Exception {
		String view = "form/formAbuso";
		DatiAbusoPojo datiAbusoPojo = new DatiAbusoPojo();
		initModelAbuso(model);
		model.addAttribute("datiAbusoPojo", datiAbusoPojo);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/salvaAbuso", method = RequestMethod.POST)
	public ModelAndView salvaAbuso(ModelMap model, DatiAbusoPojo pojo,
			Errors errors) throws Exception {

		String view = "redirect:abusi.htm?idpratica=".concat(this.praticaPojo
				.getIddatipratica());
		this.abusoPojo = pojo;
		datiAbusoValidator.validate(pojo, errors);
		if (errors.hasFieldErrors()) {
			initModelAbuso(model);
			view = "form/formAbuso";
		} else {
			if (abusoPojo.getProgressivo() == null)
				abusoPojo.setProgressivo(datiAbusoService
						.countProg(this.praticaPojo.getIddatipratica()));
			pojo.setIdPratica(this.praticaPojo.getIddatipratica());
			datiAbusoService.saveDatiAbuso(pojo);
		}
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/modificaAbuso", method = RequestMethod.GET)
	public ModelAndView modificaAbuso(
			@RequestParam(value = "idabuso") String id, ModelMap model)
			throws Exception {
		this.abusoPojo = datiAbusoService.findById(id);
		model.addAttribute("datiAbusoPojo", this.abusoPojo);
		initModelAbuso(model);
		return new ModelAndView("form/formAbuso", model);
	}

	@RequestMapping(value = "/alloggi", method = RequestMethod.GET)
	public ModelAndView alloggi(@RequestParam(value = "idabuso") String id,
			ModelMap model) throws Exception {
		String view = "table/alloggioList";
		this.abusoPojo = datiAbusoService.findById(id);
		List<DatiAlloggio> list = datiAbusoService.findAlloggi(id);
		model.addAttribute("alloggi", list);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/nuovoAlloggio", method = RequestMethod.GET)
	public ModelAndView nuovoAlloggio(ModelMap model) throws Exception {

		String view = "form/formAlloggio";
		DatiAlloggio datiAlloggio = new DatiAlloggio();
		initModelAlloggio(model);
		model.addAttribute("datiAlloggio", datiAlloggio);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/modificaAlloggio", method = RequestMethod.GET)
	public ModelAndView modificaAlloggio(
			@RequestParam(value = "idalloggio") String id, ModelMap model) throws Exception {
		String view = "form/formAlloggio";
		initModelAlloggio(model);
		DatiAlloggio datiAlloggio = datiAbusoService.findAlloggioById(id);
		model.addAttribute("datiAlloggio", datiAlloggio);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/salvaAlloggio", method = RequestMethod.POST)
	public ModelAndView salvaAlloggio(ModelMap model, DatiAlloggio datiAlloggio)
			throws Exception {
		String view = "redirect:alloggi.htm?idabuso=".concat(this.abusoPojo
				.getIddatiabuso());
		datiAlloggio.setIdAbuso(datiAbusoService
				.findDatiAbusoById(this.abusoPojo.getIddatiabuso()));
		datiAlloggio.setIdPratica(BigInteger.valueOf(Integer.valueOf(abusoPojo
				.getDatiPratica())));
		datiAbusoService.saveDatiAlloggio(datiAlloggio);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/documenti", method = RequestMethod.GET)
	public ModelAndView documenti(ModelMap model) throws Exception {
		String view = "table/documentiList";
		List<Datipratica> list = datiPraticaService.findAll();
		model.addAttribute("pratiche", list);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/nuovoDocumento", method = RequestMethod.GET)
	public ModelAndView nuovoDocumento(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	@RequestMapping(value = "/modificaDocumento", method = RequestMethod.GET)
	public ModelAndView modificaDocumento(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	@RequestMapping(value = "/salvaDocumento", method = RequestMethod.GET)
	public ModelAndView salvaDocumento(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	@RequestMapping(value = "/fabbricati", method = RequestMethod.GET)
	public ModelAndView fabbricati(ModelMap model) throws Exception {
		String view = "table/fabbricatiList";
		List<Datipratica> list = datiPraticaService.findAll();
		model.addAttribute("pratiche", list);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/nuovoFabbricato", method = RequestMethod.GET)
	public ModelAndView nuovoFabbricato(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	@RequestMapping(value = "/modificaFabbricato", method = RequestMethod.GET)
	public ModelAndView modificaFabbricato(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	@RequestMapping(value = "/salvaFabbricato", method = RequestMethod.GET)
	public ModelAndView salvaFabbricato(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	@RequestMapping(value = "/terreni", method = RequestMethod.GET)
	public ModelAndView terreni(ModelMap model) throws Exception {
		String view = "table/praticheList";
		List<Datipratica> list = datiPraticaService.findAll();
		model.addAttribute("pratiche", list);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/nuovoTerreno", method = RequestMethod.GET)
	public ModelAndView nuovoTerreno(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	@RequestMapping(value = "/modificaTerreno", method = RequestMethod.GET)
	public ModelAndView modificaTerreno(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	@RequestMapping(value = "/salvaTerreno", method = RequestMethod.GET)
	public ModelAndView salvaTerreno(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	@RequestMapping(value = "/soggetti", method = RequestMethod.GET)
	public ModelAndView soggetti(ModelMap model) throws Exception {
		String view = "table/praticheList";
		List<Datipratica> list = datiPraticaService.findAll();
		model.addAttribute("pratiche", list);
		return new ModelAndView(view, model);
	}

	@RequestMapping(value = "/nuovoSoggetto", method = RequestMethod.GET)
	public ModelAndView nuovoSoggetto(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	@RequestMapping(value = "/modificaSoggetto", method = RequestMethod.GET)
	public ModelAndView modificaSoggetto(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	@RequestMapping(value = "/salvaSoggetto", method = RequestMethod.GET)
	public ModelAndView salvaSoggetto(ModelMap model) throws Exception {
		model.addAttribute("datiPraticaPojo", this.praticaPojo);
		return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
	}

	/**
	 * carica la lista dei comuni, delle province e delle leggi condono
	 * 
	 * @param model
	 */
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

	private void initModelAbuso(ModelMap model) {
		this.tipologiaDestinazioneUsos = destinazioneUsoHome.findAll();
		this.epocaAbusos = epocaAbusoHome.findAll(this.praticaPojo
				.getLeggeCondono());
		this.tipoOperas = tipoOperaHome.findAll();
		this.tipologiaAbusos = tipologiaAbusoHome.findAll(this.praticaPojo
				.getLeggeCondono());
		model.addAttribute("tipologiaDestinazioneUsos",
				tipologiaDestinazioneUsos);
		model.addAttribute("epocaAbusos", epocaAbusos);
		model.addAttribute("tipoOperas", tipoOperas);
		model.addAttribute("tipologiaAbusos", tipologiaAbusos);
	}

	private void initModelAlloggio(ModelMap model) {
		this.tipologiaDestinazioneUsos = destinazioneUsoHome.findAll();
		this.tipologiaAlloggios = tipoAlloggioHome.findAll();
		this.caratteristicheSpecialis = caratteristicheHome.findAll();
		model.addAttribute("tipologiaDestinazioneUsos",
				tipologiaDestinazioneUsos);
		model.addAttribute("tipologiaAlloggios", tipologiaAlloggios);
		model.addAttribute("caratteristicheSpecialis", caratteristicheSpecialis);
	}
}
