package it.soft.service;

import it.soft.dao.DatiSollecitoHome;
import it.soft.domain.DatiSollecito;
import it.soft.web.pojo.DatiSollecitoPojo;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatiSollecitiService {

    @Autowired
    DatiSollecitoHome datiSollecitoHome;

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
	BigInteger idPratBD = BigInteger.valueOf(Integer.valueOf(idPratica));
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
	BigInteger idPratBD = BigInteger.valueOf(Integer.valueOf(idpratica));
	Integer progressivoInt = Integer.valueOf(progressivo);
	List<DatiSollecito> lista = datiSollecitoHome.findAll(idPratBD,
		progressivoInt);
	DatiSollecito datiSollecito = new DatiSollecito();
	if (lista != null && !lista.isEmpty()) {
	    datiSollecito = lista.get(0);
	}
	return datiSollecito;

    }
}
