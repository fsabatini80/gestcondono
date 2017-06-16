package it.soft.web;

import it.soft.dao.LeggiCondonoHome;
import it.soft.domain.Datipratica;
import it.soft.service.DatiAbusoService;
import it.soft.service.DatiPraticaService;
import it.soft.util.Constants;
import it.soft.web.pojo.DatiPraticaPojo;

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

    // @Autowired
    // DatiPraticaHome datiPraticaHome;
    @Autowired
    LeggiCondonoHome leggiCondonoHome;
    // @Autowired
    // DestinazioneUsoHome destinazioneUsoHome;
    // @Autowired
    // EpocaAbusoHome epocaAbusoHome;
    // @Autowired
    // TipoOperaHome tipoOperaHome;
    // @Autowired
    // TipologiaAbusoHome tipologiaAbusoHome;
    // @Autowired
    // UtentiHome utentiHome;
    // @Autowired
    // ComuniHome comuniHome;
    // @Autowired
    // TipoAlloggioHome tipoAlloggioHome;
    // @Autowired
    // CaratteristicheHome caratteristicheHome;
    // @Autowired
    // SoggettiAbusoHome soggettiAbusoHome;

    // @Autowired
    // DatiPraticaValidator validatorPratica;
    // @Autowired
    // DatiAbusoValidator datiAbusoValidator;

    @Autowired
    DatiPraticaService datiPraticaService;
    @Autowired
    DatiAbusoService datiAbusoService;

    private int abusi47;
    private int abusi724;
    private int abusi326;

    // DatiPraticaPojo praticaPojo;

    // DatiAbusoPojo abusoPojo;
    // String idDatiAlloggio;
    // List<String> comuni;
    // List<String> province;
    // List<TipologiaDestinazioneUso> tipologiaDestinazioneUsos;
    // List<EpocaAbuso> epocaAbusos;
    // List<TipoOpera> tipoOperas;
    // List<TipologiaAbuso> tipologiaAbusos;
    // List<TipologiaAlloggio> tipologiaAlloggios;
    // List<CaratteristicheSpeciali> caratteristicheSpecialis;
    // List<SoggettiAbuso> soggettiAbusos;

    @RequestMapping(value = "/cruscotto", method = RequestMethod.GET)
    public ModelAndView cruscottoHome(ModelMap model, DatiPraticaPojo pojo,
	    Errors errors) throws Exception {

	String view = "table/cruscottoiList";

	List<Datipratica> list47_85 = datiPraticaService
		.findBy(leggiCondonoHome.findById(Integer
			.valueOf(Constants.ID_LEGGE_47_85)));
	setAbusi47(contaAbusi(list47_85));
	List<Datipratica> list724_94 = datiPraticaService
		.findBy(leggiCondonoHome.findById(Integer
			.valueOf(Constants.ID_LEGGE_724_94)));
	setAbusi724(contaAbusi(list724_94));
	List<Datipratica> list326_04 = datiPraticaService
		.findBy(leggiCondonoHome.findById(Integer
			.valueOf(Constants.ID_LEGGE_326_03)));
	setAbusi326(contaAbusi(list326_04));

	model.addAttribute("presentiL47", list47_85.size());
	model.addAttribute("presentiL724", list724_94.size());
	model.addAttribute("presentiL326", list326_04.size());
	model.addAttribute("presentiLTot", list47_85.size() + list724_94.size()
		+ list326_04.size());

	model.addAttribute("abusiL47", getAbusi47());
	model.addAttribute("abusiL724", getAbusi724());
	model.addAttribute("abusiL326", getAbusi326());
	model.addAttribute("abusiTot", getAbusi47() + getAbusi724()
		+ getAbusi326());

	setFakeData(model);

	return new ModelAndView(view, model);
    }

    private int contaAbusi(List<Datipratica> list) {
	// TODO Auto-generated method stub
	int count = 0;
	for (Datipratica datipratica : list) {
	    count = count
		    + Integer.parseInt(datiAbusoService.countProg(String
			    .valueOf(datipratica.getIddatipratica()))) - 1;
	}
	return count;
    }

    private void setFakeData(ModelMap model) {
	model.addAttribute("istruiteL47", "0");
	model.addAttribute("istruiteL724", "0");
	model.addAttribute("istruiteL326", "0");
	model.addAttribute("istruiteLTot", "0");
	model.addAttribute("daistruireL47", "0");
	model.addAttribute("daistruireL724", "0");
	model.addAttribute("daistruireL326", "0");
	model.addAttribute("daistruireTot", "0");
    }

    public int getAbusi47() {
	return abusi47;
    }

    public void setAbusi47(int abusi47) {
	this.abusi47 = abusi47;
    }

    public int getAbusi724() {
	return abusi724;
    }

    public void setAbusi724(int abusi724) {
	this.abusi724 = abusi724;
    }

    public int getAbusi326() {
	return abusi326;
    }

    public void setAbusi326(int abusi326) {
	this.abusi326 = abusi326;
    }

}
