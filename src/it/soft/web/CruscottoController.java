package it.soft.web;

import it.soft.dao.DatiSollecitoHome;
import it.soft.dao.LeggiCondonoHome;
import it.soft.domain.DatiSollecito;
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

    @Autowired
    LeggiCondonoHome leggiCondonoHome;

    @Autowired
    DatiPraticaService datiPraticaService;
    @Autowired
    DatiAbusoService datiAbusoService;
    @Autowired
    DatiSollecitoHome datiSollecitoHome;

    private int abusi47;
    private int abusi724;
    private int abusi326;

    private int sollecitate47;
    private int sollecitate724;
    private int sollecitate326;
    private int sollecitateInsolute47;
    private int sollecitateInsolute724;
    private int sollecitateInsolute326;

    // DatiPraticaPojo praticaPojo;

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
	int count = 0;
	for (Datipratica datipratica : list) {
	    count = count
		    + Integer.parseInt(datiAbusoService.countProg(String
			    .valueOf(datipratica.getIddatipratica()))) - 1;
	}
	return count;
    }

    private void setFakeData(ModelMap model) {

	List<DatiSollecito> sollecitateL47 = datiSollecitoHome.findBy("", 1);
	List<DatiSollecito> sollecitateL724 = datiSollecitoHome.findBy("", 2);
	List<DatiSollecito> sollecitateL326 = datiSollecitoHome.findBy("", 3);
	model.addAttribute("sollecitateL47", sollecitateL47.size());
	model.addAttribute("sollecitateL724", sollecitateL724.size());
	model.addAttribute("sollecitateL326", sollecitateL326.size());
	model.addAttribute("sollecitateTot", sollecitateL47.size()
		+ sollecitateL724.size() + sollecitateL326.size());

	List<DatiSollecito> sollecitateInsoluteL47 = datiSollecitoHome.findBy(
		"", false, 1);
	List<DatiSollecito> sollecitateInsoluteL724 = datiSollecitoHome.findBy(
		"", false, 2);
	List<DatiSollecito> sollecitateInsoluteL326 = datiSollecitoHome.findBy(
		"", false, 3);

	model.addAttribute("sollecitateInsoluteL47",
		sollecitateInsoluteL47.size());
	model.addAttribute("sollecitateInsoluteL724",
		sollecitateInsoluteL724.size());
	model.addAttribute("sollecitateInsoluteL326",
		sollecitateInsoluteL326.size());
	model.addAttribute("sollecitateInsoluteTot",
		sollecitateInsoluteL47.size() + sollecitateInsoluteL724.size()
			+ sollecitateInsoluteL326.size());
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

    public int getSollecitateInsolute326() {
	return sollecitateInsolute326;
    }

    public void setSollecitateInsolute326(int sollecitateInsolute326) {
	this.sollecitateInsolute326 = sollecitateInsolute326;
    }

    public int getSollecitate724() {
	return sollecitate724;
    }

    public void setSollecitate724(int sollecitate724) {
	this.sollecitate724 = sollecitate724;
    }

    public int getSollecitateInsolute724() {
	return sollecitateInsolute724;
    }

    public void setSollecitateInsolute724(int sollecitateInsolute724) {
	this.sollecitateInsolute724 = sollecitateInsolute724;
    }

    public int getSollecitateInsolute47() {
	return sollecitateInsolute47;
    }

    public void setSollecitateInsolute47(int sollecitateInsolute47) {
	this.sollecitateInsolute47 = sollecitateInsolute47;
    }

    public int getSollecitate326() {
	return sollecitate326;
    }

    public void setSollecitate326(int sollecitate326) {
	this.sollecitate326 = sollecitate326;
    }

    public int getSollecitate47() {
	return sollecitate47;
    }

    public void setSollecitate47(int sollecitate47) {
	this.sollecitate47 = sollecitate47;
    }

}
