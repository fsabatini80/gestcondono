package it.soft.service;

import it.soft.dao.DatiPraticaHome;
import it.soft.dao.DatiSollecitoHome;
import it.soft.domain.DatiSollecito;
import it.soft.domain.Utenti;
import it.soft.util.AuthenticationUtils;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiSollecitoPojo;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatiSollecitiService {

    @Autowired
    DatiSollecitoHome datiSollecitoHome;
    @Autowired
    AuthenticationUtils authenticationUtils;
    @Autowired
    DatiPraticaHome datiPraticaHome;

    public DatiSollecito save(DatiSollecitoPojo pojo) {
	DatiSollecito datiSollecito = new DatiSollecito();
	try {
	    ConvertUtilsBean convertUtilsBean = BeanUtilsBean.getInstance()
		    .getConvertUtils();
	    convertUtilsBean.register(false, true, -1);
	    org.apache.commons.beanutils.BeanUtils.copyProperties(
		    datiSollecito, pojo);
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	datiSollecitoHome.persist(datiSollecito);
	return datiSollecito;
    }

    public DatiSollecitoPojo findById(String id) {
	DatiSollecito source = datiSollecitoHome.findById(BigInteger
		.valueOf(Integer.parseInt(id)));
	DatiSollecitoPojo target = new DatiSollecitoPojo();
	try {
	    BeanUtils.copyProperties(target, source);
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return target;
    }

    public List<DatiSollecito> findAll(String idPratica) {
	BigDecimal idPratBD = BigDecimal.valueOf(Integer.valueOf(idPratica));
	return datiSollecitoHome.findAll(idPratBD);
    }

    public List<DatiSollecito> findAll() {
	return datiSollecitoHome.findAll();
    }

    public void remove(String id) {
	DatiSollecito source = datiSollecitoHome.findById(BigInteger
		.valueOf(Integer.parseInt(id)));
	datiSollecitoHome.remove(source);

    }

    public DatiSollecito findAll(String idpratica, String progressivo) {
	BigDecimal idPratBD = BigDecimal.valueOf(Integer.valueOf(idpratica));
	Integer progressivoInt = Integer.valueOf(progressivo);
	List<DatiSollecito> lista = datiSollecitoHome.findAll(
		datiPraticaHome.findById(idPratBD), progressivoInt);
	DatiSollecito datiSollecito = new DatiSollecito();
	if (lista != null && !lista.isEmpty()) {
	    datiSollecito = lista.get(0);
	}
	return datiSollecito;

    }

    public Date getDataStampa(String idpratica, String progressivo) {
	DatiSollecito sollecito = findAll(idpratica, progressivo);
	if (StringUtils.isEmpty(sollecito.getDataStampa())) {
	    return new Date();
	}
	return Converter.convertData(sollecito.getDataStampa());
    }

    public void saveDataStampa(String idpratica, String idabuso,
	    String progressivo) {
	DatiSollecito sollecito = findAll(idpratica, progressivo);
	sollecito.setDataStampa(Converter.dateToString(new Date()));
	if (sollecito.getIddatiSollecito() == null) {
	    sollecito.setIddatiPratica(datiPraticaHome.findById(BigDecimal
		    .valueOf(Integer.parseInt(idpratica))));
	    sollecito.setProgressivoAbuso(Converter.stringToint(progressivo));
	    sollecito.setIdAbuso(Converter.stringToBigInteger(idabuso));
	    sollecito.setPagato(false);
	    Utenti user = authenticationUtils.getUtente();
	    sollecito.setTecnicoIncaricato(user.getNome().concat(" ")
		    .concat(user.getCognome()));
	    datiSollecitoHome.persist(sollecito);
	} else {
	    datiSollecitoHome.merge(sollecito);
	}

    }

    public List<DatiSollecito> getPraticheInScadenza() {
	List<DatiSollecito> solleciti = datiSollecitoHome.findBy(false);
	List<DatiSollecito> sollecitiAnswer = new ArrayList<DatiSollecito>();
	for (DatiSollecito sollecito : solleciti) {
	    String dataStampaString = sollecito.getDataRicevuta();
	    if (dataStampaString != null) {
		Date dataStampa = Converter.convertData(dataStampaString);
		Calendar gc = GregorianCalendar.getInstance();
		gc.add(Calendar.DAY_OF_MONTH, -60);
		if (dataStampa.before(gc.getTime())) {
		    sollecitiAnswer.add(sollecito);
		}
	    }
	}
	return sollecitiAnswer;
    }

    public boolean existPraticheInScadenza() {
	List<DatiSollecito> solleciti = datiSollecitoHome.findBy(false);
	for (DatiSollecito sollecito : solleciti) {
	    String dataStampaString = sollecito.getDataRicevuta();
	    if (dataStampaString != null) {
		Date dataStampa = Converter.convertData(dataStampaString);
		Calendar gc = GregorianCalendar.getInstance();
		gc.add(Calendar.DAY_OF_MONTH, -60);
		if (dataStampa.before(gc.getTime())) {
		    System.out.println("pratiche in scadenza");
		    return true;
		}
	    }
	}
	return false;
    }
}
