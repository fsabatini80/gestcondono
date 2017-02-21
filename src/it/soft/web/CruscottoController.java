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
import it.soft.domain.Datiabuso;
import it.soft.domain.Datipratica;
import it.soft.domain.EpocaAbuso;
import it.soft.domain.SoggettiAbuso;
import it.soft.domain.TipoOpera;
import it.soft.domain.TipologiaAbuso;
import it.soft.domain.TipologiaAlloggio;
import it.soft.domain.TipologiaDestinazioneUso;
import it.soft.service.DatiAbusoService;
import it.soft.service.DatiPraticaService;
import it.soft.util.Constants;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiPraticaPojo;
import it.soft.web.validator.DatiAbusoValidator;
import it.soft.web.validator.DatiPraticaValidator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CruscottoController extends BaseController {

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

    @RequestMapping(value = "/cruscotto", method = RequestMethod.GET)
    public ModelAndView cruscottoHome(ModelMap model, DatiPraticaPojo pojo,
	    Errors errors) throws Exception {

	String view = "table/cruscottoiList";

	List<Datipratica> list47_85 = datiPraticaService
		.findBy(leggiCondonoHome.findById(Integer
			.valueOf(Constants.ID_LEGGE_47_85)));
	List<Datipratica> list724_94 = datiPraticaService
		.findBy(leggiCondonoHome.findById(Integer
			.valueOf(Constants.ID_LEGGE_724_94)));
	List<Datipratica> list326_ = datiPraticaService.findBy(leggiCondonoHome
		.findById(Integer.valueOf(Constants.ID_LEGGE_326_)));

	List<Datiabuso> abusi = datiAbusoService.findAll();

	model.addAttribute("presentiL47", list47_85.size());
	model.addAttribute("presentiL724", list724_94.size());
	model.addAttribute("presentiL326", list326_.size());
	model.addAttribute("presentiLTot", list47_85.size() + list724_94.size()
		+ list326_.size());
	model.addAttribute("istruiteL47", "0");
	model.addAttribute("istruiteL724", "0");
	model.addAttribute("istruiteL326", "0");
	model.addAttribute("istruiteLTot", "0");
	model.addAttribute("daistruireL47", "0");
	model.addAttribute("daistruireL724", "0");
	model.addAttribute("daistruireL326", "0");
	model.addAttribute("daistruireTot", "0");
	model.addAttribute("abusiL47", abusi.size());
	model.addAttribute("abusiL724", abusi.size());
	model.addAttribute("abusiL326", abusi.size());
	model.addAttribute("abusiTot",
		abusi.size() + abusi.size() + abusi.size());

	return new ModelAndView(view, model);
    }

}
