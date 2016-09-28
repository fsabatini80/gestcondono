package it.soft.web;

import it.soft.dao.CaratteristicheHome;
import it.soft.dao.ComuniHome;
import it.soft.dao.DatiPraticaHome;
import it.soft.dao.DatiVersamentiHome;
import it.soft.dao.DestinazioneUsoHome;
import it.soft.dao.EpocaAbusoHome;
import it.soft.dao.LeggiCondonoHome;
import it.soft.dao.OneriConcessoriHome;
import it.soft.dao.SoggettiAbusoHome;
import it.soft.dao.TipoAlloggioHome;
import it.soft.dao.TipoOperaHome;
import it.soft.dao.TipologiaAbusoHome;
import it.soft.dao.UtentiHome;
import it.soft.domain.CaratteristicheSpeciali;
import it.soft.domain.DatiAlloggio;
import it.soft.domain.DatiFabbricati;
import it.soft.domain.DatiTerreni;
import it.soft.domain.Datiabuso;
import it.soft.domain.Datipratica;
import it.soft.domain.DocumentiAbuso;
import it.soft.domain.EpocaAbuso;
import it.soft.domain.OneriConcessori;
import it.soft.domain.RelSoggettoAbuso;
import it.soft.domain.SoggettiAbuso;
import it.soft.domain.TipoEsenzioni;
import it.soft.domain.TipoOpera;
import it.soft.domain.TipoRiduzione;
import it.soft.domain.TipologiaAbuso;
import it.soft.domain.TipologiaAlloggio;
import it.soft.domain.TipologiaDestinazioneUso;
import it.soft.domain.TipologiaDocumento;
import it.soft.domain.TipologiaRiduzioneReddito;
import it.soft.domain.Utenti;
import it.soft.service.DatiAbusoService;
import it.soft.service.DatiPraticaService;
import it.soft.service.DatiVersamentiService;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiAlloggioPojo;
import it.soft.web.pojo.DatiPraticaPojo;
import it.soft.web.pojo.DocumentiAbusoPojo;
import it.soft.web.pojo.TipologiaDocumentoPojo;
import it.soft.web.validator.DatiAbusoValidator;
import it.soft.web.validator.DatiPraticaValidator;
import it.soft.web.validator.TerreniValidator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
    DatiVersamentiHome datiVersamentiHome;
    @Autowired
    OneriConcessoriHome oneriConcessoriHome;

    @Autowired
    DatiPraticaValidator validatorPratica;
    @Autowired
    DatiAbusoValidator datiAbusoValidator;
    @Autowired
    TerreniValidator terreniValidator;

    @Autowired
    DatiPraticaService datiPraticaService;
    @Autowired
    DatiAbusoService datiAbusoService;
    @Autowired
    DatiVersamentiService datiVersamentiService;

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
    List<TipoEsenzioni> esenzioniPagamentis;
    List<TipoRiduzione> riduzionis;
    List<TipologiaRiduzioneReddito> tipoRedditos;
    List<OneriConcessori> oneriConcessoris;

    @RequestMapping(value = "/pratiche", method = RequestMethod.GET)
    public ModelAndView praticheHome(ModelMap model, DatiPraticaPojo pojo,
	    Errors errors) throws Exception {

	String view = "table/praticheList";
	List<Datipratica> list = datiPraticaService.findAll();
	model.addAttribute("pratiche", list);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/pratica", method = RequestMethod.GET)
    public ModelAndView pratica(@RequestParam(value = "idpratica") String id,
	    ModelMap model) throws Exception {

	String view = "table/praticheList";
	Datipratica source = datiPraticaHome.findById(BigDecimal
		.valueOf(Integer.parseInt(id)));
	List<Datipratica> list = new ArrayList<Datipratica>();
	list.add(source);
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

	String view = "redirect:pratica.htm?idpratica=";
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
	    Datipratica datipratica = datiPraticaService.saveDatiPratica(pojo);
	    view = view.concat(String.valueOf(datipratica.getIddatipratica()));
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
	model.addAttribute("idpratica", this.praticaPojo.getIddatipratica());
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
	pojo.setIdPratica(this.praticaPojo.getIddatipratica());
	datiAbusoValidator.validate(pojo, errors);
	if (errors.hasFieldErrors()) {
	    initModelAbuso(model);
	    view = "form/formAbuso";
	} else {
	    if (abusoPojo.getProgressivo() == null
		    || "".equals(abusoPojo.getProgressivo()))
		abusoPojo.setProgressivo(datiAbusoService
			.countProg(this.praticaPojo.getIddatipratica()));
	    pojo.setIdPratica(this.praticaPojo.getIddatipratica());
	    datiAbusoService.saveDatiAbuso(pojo);
	}
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/calcolaOblazione", method = RequestMethod.POST)
    public ModelAndView calcolaOblazione(ModelMap model, DatiAbusoPojo pojo,
	    Errors errors) throws Exception {

	String view = "form/formAbuso";
	pojo.setIdPratica(this.praticaPojo.getIddatipratica());
	if (!StringUtils.isEmpty(pojo.getIddatiabuso())) {
	    this.abusoPojo = datiAbusoService.findById(pojo.getIddatiabuso());
	    EpocaAbuso epoca = epocaAbusoHome.findById(Integer.parseInt(pojo
		    .getEpocaAbuso()));
	    this.abusoPojo.setOblazioneCalcolata(datiVersamentiService
		    .getImportoCalcolatoOblazione(
			    tipologiaAbusoHome.findById(Integer.valueOf(pojo
				    .getTipologiaAbuso())), Converter
				    .dateToDouble(epoca.getEpocaDa()),
			    this.praticaPojo.getLeggeCondono(), pojo
				    .getIddatiabuso(), pojo
				    .getDestinazioneUso()));

	    model.addAttribute("datiAbusoPojo", this.abusoPojo);
	}
	initModelAbuso(model);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/modificaAbuso", method = RequestMethod.GET)
    public ModelAndView modificaAbuso(
	    @RequestParam(value = "idabuso") String id, ModelMap model)
	    throws Exception {
	this.abusoPojo = datiAbusoService.findById(id);
	this.praticaPojo = datiPraticaService.findById(this.abusoPojo
		.getIdPratica());
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
	model.addAttribute("idpratica", this.abusoPojo.getIdPratica());
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/nuovoAlloggio", method = RequestMethod.GET)
    public ModelAndView nuovoAlloggio(ModelMap model) throws Exception {

	String view = "form/formAlloggio";
	DatiAlloggioPojo datiAlloggio = new DatiAlloggioPojo();
	initModelAlloggio(model);
	model.addAttribute("datiAlloggio", datiAlloggio);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/modificaAlloggio", method = RequestMethod.GET)
    public ModelAndView modificaAlloggio(
	    @RequestParam(value = "idalloggio") String id, ModelMap model)
	    throws Exception {
	String view = "form/formAlloggio";
	initModelAlloggio(model);
	DatiAlloggioPojo datiAlloggio = datiAbusoService.findAlloggioById(id);
	model.addAttribute("datiAlloggio", datiAlloggio);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/salvaAlloggio", method = RequestMethod.POST)
    public ModelAndView salvaAlloggio(ModelMap model,
	    DatiAlloggioPojo datiAlloggio) throws Exception {
	String view = "redirect:alloggi.htm?idabuso=".concat(this.abusoPojo
		.getIddatiabuso());
	datiAlloggio.setIdAbuso(this.abusoPojo.getIddatiabuso());
	datiAlloggio.setIdPratica(abusoPojo.getIdPratica());
	datiAbusoService.saveDatiAlloggio(datiAlloggio);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/documenti", method = RequestMethod.GET)
    public ModelAndView documenti(ModelMap model,
	    @RequestParam(value = "idabuso") String id) throws Exception {
	String view = "table/documentiList";
	this.abusoPojo = datiAbusoService.findById(id);
	List<DocumentiAbuso> list = datiAbusoService.findAllDocById(id,
		leggiCondonoHome.findById(Integer.valueOf(this.praticaPojo
			.getLeggeCondono())));
	model.addAttribute("documenti", list);
	List<TipologiaDocumento> listAdd = datiAbusoService.findAllDocToAdd();
	model.addAttribute("documentiAdd", listAdd);
	model.addAttribute("tipologiaDocumentoPojo",
		new TipologiaDocumentoPojo());
	model.addAttribute("idpratica", this.abusoPojo.getIdPratica());
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/nuovoDocumento", method = RequestMethod.POST)
    public ModelAndView nuovoDocumento(ModelMap model,
	    TipologiaDocumentoPojo pojo) throws Exception {
	String view = "redirect:documenti.htm?idabuso=".concat(this.abusoPojo
		.getIddatiabuso());
	datiAbusoService.addDocList(pojo.getDocToAdd(),
		this.abusoPojo.getIddatiabuso());
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/modificaDocumento", method = RequestMethod.GET)
    public ModelAndView modificaDocumento(ModelMap model,
	    @RequestParam(value = "iddocumento") String id) throws Exception {
	String view = "form/formDocumento";
	DocumentiAbusoPojo documentiAbuso = datiAbusoService.findDocById(id);
	model.addAttribute("documentiAbuso", documentiAbuso);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/salvaDocumento", method = RequestMethod.POST)
    public ModelAndView salvaDocumento(ModelMap model,
	    DocumentiAbusoPojo documentiAbuso) throws Exception {
	String view = "redirect:documenti.htm?idabuso=".concat(this.abusoPojo
		.getIddatiabuso());
	documentiAbuso.setIdAbuso(this.abusoPojo.getIddatiabuso());
	datiAbusoService.saveDocById(documentiAbuso);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/eliminaDocumento", method = RequestMethod.GET)
    public ModelAndView eliminaDocumento(ModelMap model,
	    DocumentiAbusoPojo documentiAbuso,
	    @RequestParam(value = "iddocumento") String id) throws Exception {
	String view = "redirect:documenti.htm?idabuso=".concat(this.abusoPojo
		.getIddatiabuso());
	documentiAbuso.setIdAbuso(this.abusoPojo.getIddatiabuso());
	datiAbusoService.eliminaDocById(id);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/soggetti", method = RequestMethod.GET)
    public ModelAndView soggetti(@RequestParam(value = "idabuso") String id,
	    ModelMap model) throws Exception {
	String view = "table/soggettiList";
	this.abusoPojo = datiAbusoService.findById(id);
	initSoggetti(model);
	List<RelSoggettoAbuso> list = datiAbusoService.findAllSoggettiById(id);
	RelSoggettoAbuso soggettoNew = new RelSoggettoAbuso();
	soggettoNew.setIdAbuso(BigInteger.valueOf(Integer.valueOf(id)));
	model.addAttribute("soggettoNew", soggettoNew);
	model.addAttribute("soggetti", list);
	model.addAttribute("idpratica", this.abusoPojo.getIdPratica());
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/modificaSoggetto", method = RequestMethod.GET)
    public ModelAndView modificaSoggetto(ModelMap model,
	    @RequestParam(value = "idsoggetto") String id) throws Exception {
	String view = "form/formSoggetto";
	// this.abusoPojo = datiAbusoService.findById(id);
	initSoggetti(model);
	RelSoggettoAbuso soggettoNew = datiAbusoService.findSoggettoById(id);
	soggettoNew.setIdAbuso(BigInteger.valueOf(Integer
		.valueOf(this.abusoPojo.getIddatiabuso())));
	model.addAttribute("soggettoNew", soggettoNew);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/salvaSoggetto", method = RequestMethod.POST)
    public ModelAndView salvaSoggetto(ModelMap model,
	    RelSoggettoAbuso relSoggettoAbuso) throws Exception {
	String view = "redirect:soggetti.htm?idabuso=".concat(this.abusoPojo
		.getIddatiabuso());
	relSoggettoAbuso.setIdAbuso(BigInteger.valueOf(Integer
		.valueOf(this.abusoPojo.getIddatiabuso())));
	datiAbusoService.saveSoggettoAbuso(relSoggettoAbuso);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/removeSoggetto", method = RequestMethod.GET)
    public ModelAndView removeSoggetto(ModelMap model,
	    @RequestParam(value = "idsoggetto") String id) throws Exception {
	String view = "redirect:soggetti.htm?idabuso=".concat(this.abusoPojo
		.getIddatiabuso());
	datiAbusoService.removeSoggetto(id);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/fabbricati", method = RequestMethod.GET)
    public ModelAndView fabbricati(
	    @RequestParam(value = "idalloggio") String id, ModelMap model)
	    throws Exception {
	String view = "table/fabbricatiList";
	this.idDatiAlloggio = id;
	List<DatiFabbricati> list = datiAbusoService.findAllFabbricatiById(id);
	DatiFabbricati fabbricatoNew = new DatiFabbricati();
	fabbricatoNew.setIdAlloggio(Integer.valueOf(id));
	model.addAttribute("fabbricatoNew", fabbricatoNew);
	model.addAttribute("fabbricati", list);
	model.addAttribute("idabuso", this.abusoPojo.getIddatiabuso());
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/modificaFabbricato", method = RequestMethod.GET)
    public ModelAndView modificaFabbricato(ModelMap model,
	    DatiFabbricati datiFabbricati) throws Exception {
	model.addAttribute("datiPraticaPojo", this.praticaPojo);
	return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
    }

    @RequestMapping(value = "/salvaFabbricato", method = RequestMethod.POST)
    public ModelAndView salvaFabbricato(ModelMap model,
	    DatiFabbricati datiFabbricati) throws Exception {
	String view = "redirect:fabbricati.htm?idalloggio=".concat(String
		.valueOf(datiFabbricati.getIdAlloggio()));
	datiAbusoService.saveFabbricato(datiFabbricati);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/removeFabbricato", method = RequestMethod.GET)
    public ModelAndView removeFabbricato(ModelMap model,
	    DatiFabbricati datiFabbricati,
	    @RequestParam(value = "idfabbricato") String id) throws Exception {
	String view = "redirect:fabbricati.htm?idalloggio="
		.concat(this.idDatiAlloggio);
	datiAbusoService.removeFabbricato(id, this.idDatiAlloggio);
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/terreni", method = RequestMethod.GET)
    public ModelAndView terreni(@RequestParam(value = "idalloggio") String id,
	    ModelMap model) throws Exception {
	String view = "table/terreniList";
	this.idDatiAlloggio = id;
	List<DatiTerreni> list = datiAbusoService.findAllTerreniById(id);
	DatiTerreni terrenoNew = new DatiTerreni();
	terrenoNew.setIdAlloggio(Integer.valueOf(id));
	model.addAttribute("terrenoNew", terrenoNew);
	model.addAttribute("terreni", list);
	model.addAttribute("idabuso", this.abusoPojo.getIddatiabuso());
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/modificaTerreno", method = RequestMethod.GET)
    public ModelAndView modificaTerreno(ModelMap model) throws Exception {
	model.addAttribute("datiPraticaPojo", this.praticaPojo);
	return new ModelAndView("wizard/form/DatiNuovoAbusoForm", model);
    }

    @RequestMapping(value = "/salvaTerreno", method = RequestMethod.POST)
    public ModelAndView salvaTerreno(ModelMap model, DatiTerreni datiTerreni,
	    Errors errors) throws Exception {
	String view = "redirect:terreni.htm?idalloggio="
		.concat(this.idDatiAlloggio);
	terreniValidator.validate(datiTerreni, errors);
	if (!errors.hasFieldErrors()) {
	    datiAbusoService.saveTerreno(datiTerreni);
	}
	return new ModelAndView(view, model);
    }

    @RequestMapping(value = "/removeTerreno", method = RequestMethod.GET)
    public ModelAndView removeTerreno(ModelMap model, DatiTerreni datiTerreni,
	    @RequestParam(value = "idterreno") String id) throws Exception {
	String view = "redirect:terreni.htm?idalloggio=".concat(String
		.valueOf(this.idDatiAlloggio));
	datiAbusoService.removeTerreno(id);
	return new ModelAndView(view, model);
    }

    /**
     * carica la lista dei comuni, delle province e delle leggi condono
     * 
     * @param model
     */
    private void initModel(ModelMap model) {
	// if (!model.containsAttribute("comuniList"))
	// model.addAttribute("comuniList", getComuni());
	// if (!model.containsAttribute("provinceList"))
	// model.addAttribute("provinceList", getProvince());
	if (!model.containsAttribute("leggiList"))
	    model.addAttribute("leggiList", leggiCondonoHome.findAll());
    }

    // private List<String> getComuni() {
    // if (comuni != null)
    // return comuni;
    // comuni = new ArrayList<String>();
    // comuni.add("");
    // List<Comune> list = comuniHome.findAll();
    // for (Comune comune : list) {
    // comuni.add(comune.getComune());
    // }
    // return comuni;
    // }
    //
    // private List<String> getProvince() {
    // if (province != null)
    // return province;
    // province = new ArrayList<String>();
    // province.add("");
    // List<Comune> list = comuniHome.findAll();
    // for (Comune comune : list) {
    // if (!province.contains(comune.getProvincia()))
    // province.add(comune.getProvincia());
    // }
    // return province;
    // }

    private void initModelAbuso(ModelMap model) {
	this.tipologiaDestinazioneUsos = destinazioneUsoHome.findAll();
	this.epocaAbusos = epocaAbusoHome.findAll(leggiCondonoHome
		.findById(Integer.valueOf(this.praticaPojo.getLeggeCondono())));
	this.tipoOperas = tipoOperaHome.findAll();
	this.tipologiaAbusos = tipologiaAbusoHome.findAll(leggiCondonoHome
		.findById(Integer.valueOf(this.praticaPojo.getLeggeCondono())));
	model.addAttribute("tipologiaDestinazioneUsos",
		tipologiaDestinazioneUsos);
	this.esenzioniPagamentis = datiVersamentiHome.findEsenzioni(BigInteger
		.valueOf(Integer.valueOf(this.praticaPojo.getLeggeCondono())));
	this.riduzionis = datiVersamentiHome.findRiduzioni(BigInteger
		.valueOf(Integer.valueOf(this.praticaPojo.getLeggeCondono())));
	this.tipoRedditos = datiVersamentiHome
		.findAllTipologiaRiduzioneReddito();

	this.oneriConcessoris = oneriConcessoriHome.findAll();

	model.addAttribute("tipoRedditos", tipoRedditos);
	model.addAttribute("riduzionis", riduzionis);
	model.addAttribute("esenzioniPagamentis", esenzioniPagamentis);
	model.addAttribute("epocaAbusos", epocaAbusos);
	model.addAttribute("tipoOperas", tipoOperas);
	model.addAttribute("tipologiaAbusos", tipologiaAbusos);
	model.addAttribute("oneriConcessoris", oneriConcessoris);
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

    private void initSoggetti(ModelMap model) {
	this.soggettiAbusos = soggettiAbusoHome.findAll();
	model.addAttribute("tiposoggetti", soggettiAbusos);
    }
}
