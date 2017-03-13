package it.soft.web.validator;

import java.util.List;

import it.soft.domain.DatiVersamento;
import it.soft.service.DatiVersamentiService;
import it.soft.util.Constants;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiVersamentiPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mysql.jdbc.StringUtils;

@Component
public class DatiVersamentiValidator implements Validator {

    @Autowired
    DatiVersamentiService service;

    public boolean supports(Class<?> arg0) {
	return DatiVersamentiPojo.class.equals(arg0);
    }

    public void validate(Object arg0, Errors arg1) {
	ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "progressivo_abuso",
		"obbligatorio");

	DatiVersamentiPojo pojo = (DatiVersamentiPojo) arg0;
	if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getImportoLire())
		&& !Converter.checkDoubleFormat(pojo.getImportoLire())) {
	    arg1.rejectValue("importoLire", "double.format");
	}
	if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getImportoEuro())
		&& !Converter.checkDoubleFormat(pojo.getImportoEuro())) {
	    arg1.rejectValue("importoEuro", "double.format");
	}

	if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getCausale())
		&& !StringUtils.isEmptyOrWhitespaceOnly(pojo.getCcPostale())
		&& ((Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL.equals(pojo
			.getCausale()) && !Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL
			.equals(pojo.getCcPostale())) || (!Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL
			.equals(pojo.getCausale()) && Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL
			.equals(pojo.getCcPostale())))) {
	    arg1.rejectValue("causale", "cc.error.causale");
	    arg1.rejectValue("ccPostale", "cc.error.causale");
	}
	if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getCausale())
		&& !StringUtils.isEmptyOrWhitespaceOnly(pojo.getCcPostale())
		&& ((Constants.OBLAZIONE_REGIONE_CAUSALE_SEL.equals(pojo
			.getCausale()) && !Constants.OBLAZIONE_REGIONE_CAUSALE_SEL
			.equals(pojo.getCcPostale())) || (!Constants.OBLAZIONE_REGIONE_CAUSALE_SEL
			.equals(pojo.getCausale()) && Constants.OBLAZIONE_REGIONE_CAUSALE_SEL
			.equals(pojo.getCcPostale())))) {
	    arg1.rejectValue("causale", "cc.error.causale");
	    arg1.rejectValue("ccPostale", "cc.error.causale");
	}

	/*
	 * lanciare allert nel caso in cui ci sono dei versamenti con stessa
	 * data, ufficio postale, codice
	 */
	if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getDataVersamento())
		&& !StringUtils.isEmptyOrWhitespaceOnly(pojo
			.getUfficioPostale())
		&& !StringUtils.isEmptyOrWhitespaceOnly(pojo
			.getCodiceVersamento())) {

	    List<DatiVersamento> list = service.findBy(
		    pojo.getDataVersamento(), pojo.getUfficioPostale(),
		    pojo.getCodiceVersamento());
	    if (list != null && !list.isEmpty()) {
		if (pojo.getIdversamento() != null
			&& !StringUtils.isEmptyOrWhitespaceOnly(pojo
				.getIdversamento())) {
		    String idversPojo = pojo.getIdversamento();
		    for (DatiVersamento datiVersamento : list) {
			String idversDB = String.valueOf(datiVersamento
				.getIddatiVersamento());
			if (!idversDB.equals(idversPojo)) {
			    arg1.rejectValue("dataVersamento",
				    "versamenti.duplicati.error");
			    arg1.rejectValue("ufficioPostale",
				    "versamenti.duplicati.error");
			    arg1.rejectValue("codiceVersamento",
				    "versamenti.duplicati.error");
			}
		    }
		} else {
		    arg1.rejectValue("dataVersamento",
			    "versamenti.duplicati.error");
		    arg1.rejectValue("ufficioPostale",
			    "versamenti.duplicati.error");
		    arg1.rejectValue("codiceVersamento",
			    "versamenti.duplicati.error");
		}
	    }

	}
    }
}