package it.soft.web.validator;

import it.soft.dao.DatiPraticaHome;
import it.soft.dao.EpocaAbusoHome;
import it.soft.domain.Datipratica;
import it.soft.domain.EpocaAbuso;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiAbusoPojo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mysql.jdbc.StringUtils;

@Component
public class DatiAbusoValidator implements Validator {

    @Autowired
    DatiPraticaHome datiPraticaHome;
    @Autowired
    EpocaAbusoHome epocaAbusoHome;

    // @Autowired
    // LeggiCondonoHome leggiCondonoHome;

    public boolean supports(Class<?> arg0) {
	return DatiAbusoPojo.class.equals(arg0);
    }

    public void validate(Object arg0, Errors arg1) {
	ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "destinazioneUso",
		"obbligatorio");
	ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "epocaAbuso",
		"obbligatorio");
	ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "tipologiaAbuso",
		"obbligatorio");
	ValidationUtils.rejectIfEmptyOrWhitespace(arg1,
		"dataUltimazioneLavori", "obbligatorio");

	DatiAbusoPojo pojo = (DatiAbusoPojo) arg0;

	Datipratica dp = datiPraticaHome.findById(BigDecimal.valueOf(Integer
		.parseInt(pojo.getIdPratica())));

	if (!StringUtils.isEmptyOrWhitespaceOnly(pojo
		.getDataUltimazioneLavori())) {
	    validaDataUltimazioneLavori(arg1, pojo, dp);
	}

	// if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getAutodeterminata())
	// && !Converter.checkDoubleFormat(pojo.getAutodeterminata())) {
	if (pojo.getAutodeterminata() != null
		&& !Converter.checkDoubleFormat(pojo.getAutodeterminata())) {
	    arg1.rejectValue("autodeterminata", "double.format");
	}
	if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getVolumeTotale())
		&& !Converter.checkDoubleFormat(pojo.getVolumeTotale())) {
	    arg1.rejectValue("volumeTotale", "double.format");
	}

	if ("2".equals(String.valueOf(dp.getLeggeCondono().getIdleggiCondono()))) {
	    if (abusoNonSanabile(dp, pojo)) {
		arg1.rejectValue("volumeTotale", "abuso.non.sanabile");
		arg1.rejectValue("destinazioneUso", "abuso.non.sanabile");
		arg1.rejectValue("tipoOpera", "abuso.non.sanabile");
	    }
	}
    }

    /**
     * Nuove costruzioni con destinazione d’uso residenziale che superano i 750
     * mc o ampliamenti con destinazione d’uso residenziale che superano il 30%
     * del volume totale
     * 
     * @param dp
     * @param pojo
     * @return
     */

    private boolean abusoNonSanabile(Datipratica dp, DatiAbusoPojo pojo) {

	String tipoOpera = pojo.getTipoOpera();
	String destinazioneUso = pojo.getDestinazioneUso();
	// destinazione uso diverso da residenziale
	if (!"1".equals(destinazioneUso))
	    return false;
	String volumeTotateMC = pojo.getVolumeTotale();
	Double volumeTotDouble = new Double(0.0);
	if (!StringUtils.isEmptyOrWhitespaceOnly(volumeTotateMC))
	    volumeTotDouble = new Double(volumeTotateMC);
	Double volumeMax = new Double(750);
	Double volumePerc = volumeTotDouble * 0.3;

	// apliamento con volume totale maggiore del 30% del volume
	return ("1".equals(tipoOpera) && volumeTotDouble > volumePerc)
		|| ("2".equals(tipoOpera) && volumeTotDouble > volumeMax);
    }

    private void validaDataUltimazioneLavori(Errors arg1, DatiAbusoPojo pojo,
	    Datipratica dp) {
	String dataDomandaString = pojo.getDataUltimazioneLavori();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	try {
	    Date data = dateFormat.parse(dataDomandaString);
	    // List<EpocaAbuso> epocaDB =
	    // epocaAbusoHome.findAll(leggiCondonoHome
	    // .findById(Integer.valueOf(String.valueOf(dp
	    // .getLeggeCondono().getIdleggiCondono()))));
	    EpocaAbuso epocaDB = epocaAbusoHome.findById(Integer.valueOf(pojo
		    .getEpocaAbuso()));
	    boolean rangeValid = false;
	    rangeValid = validDateForRange(data,
		    dateFormat.parse(epocaDB.getEpocaDa()),
		    dateFormat.parse(epocaDB.getEpocaA()), rangeValid);
	    Object[] errorParam = new String[1];
	    errorParam[0] = epocaDB.toString();
	    // for (EpocaAbuso epocaAbuso : epocaDB) {
	    // rangeValid = validDateForRange(data,
	    // dateFormat.parse(epocaAbuso.getEpocaDa()),
	    // dateFormat.parse(epocaAbuso.getEpocaA()), rangeValid);
	    // if (!rangeValid) {
	    // errorParam[0] = epocaAbuso.getEpocaDa();
	    // errorParam[1] = epocaAbuso.getEpocaA();
	    // }
	    // }
	    if (!rangeValid)
		arg1.rejectValue("dataUltimazioneLavori",
			"date.legge.not.valid", errorParam, null);
	} catch (ParseException e1) {
	    e1.printStackTrace();
	}
    }

    private boolean validDateForRange(Date data, Date dataafter,
	    Date databefore, boolean rangeValid) {
	if (data.compareTo(dataafter) >= 0 && data.compareTo(databefore) <= 0)
	    rangeValid = true;
	return rangeValid;
    }

}