package it.soft.web.validator;

import it.soft.dao.DatiPraticaHome;
import it.soft.dao.EpocaAbusoHome;
import it.soft.dao.LeggiCondonoHome;
import it.soft.domain.Datipratica;
import it.soft.domain.LeggiCondono;
import it.soft.web.pojo.DatiPraticaPojo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class DatiPraticaValidator implements Validator {

	@Autowired
	EpocaAbusoHome epocaAbusoHome;
	@Autowired
	LeggiCondonoHome leggiCondonoHome;
	@Autowired
	DatiPraticaHome datiPraticaHome;

	public boolean supports(Class<?> arg0) {
		return DatiPraticaPojo.class.equals(arg0);
	}

	public void validate(Object arg0, Errors arg1) {
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "numeroPratica",
				"obbligatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "numeroProtocollo",
				"obbligatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "dataProtocollo",
				"obbligatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "dataDomanda",
				"obbligatorio");
		DatiPraticaPojo pojo = (DatiPraticaPojo) arg0;
		if ("0".equals(pojo.getLeggeCondono()))
			arg1.rejectValue("leggeCondono", "obbligatorio");

		// date.legge.not.valid
//		if (pojo.getLeggeCondono() != null
//				&& !"0".equals(pojo.getLeggeCondono())
//				&& !StringUtils.isEmptyOrWhitespaceOnly(pojo.getDataDomanda())) {
//			validaDataDomanda(arg1, pojo);
//		}

		verificaPraitcheDuplicate(arg1, pojo);
		
		

	}

	private void verificaPraitcheDuplicate(Errors arg1, DatiPraticaPojo pojo) {
		List<Datipratica> praticheList = datiPraticaHome.findBy(pojo
				.getNumeroPratica(), pojo.getNumeroProtocollo(), pojo
				.getDataDomanda(), leggiCondonoHome.findById(Integer
				.valueOf(pojo.getLeggeCondono())));
		for (Datipratica datipratica : praticheList) {
			if (!String.valueOf(datipratica.getIddatipratica()).equals(
					pojo.getIddatipratica())) {
				LeggiCondono lc = datipratica.getLeggeCondono();
				String np = datipratica.getNumeroProtocollo();
				String dp = datipratica.getDataProtocollo();
				String dd = datipratica.getDataDomanda();
				if (pojo.getLeggeCondono().equals(
						String.valueOf(lc.getIdleggiCondono()))
						&& pojo.getNumeroProtocollo().equals(np)
						&& pojo.getDataProtocollo().equals(dp)
						&& pojo.getDataDomanda().equals(dd))
					arg1.rejectValue("leggeCondono", "datipratica.duplicati");
			}
		}
	}

    // private void validaDataDomanda(Errors arg1, DatiPraticaPojo pojo) {
    // String dataDomandaString = pojo.getDataDomanda();
    // SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    // try {
    // Date dataDomanda = dateFormat.parse(dataDomandaString);
    // List<EpocaAbuso> epocaDB = epocaAbusoHome.findAll(leggiCondonoHome
    // .findById(Integer.valueOf(pojo.getLeggeCondono())));
    // boolean rangeValid = false;
    // Object[] errorParam = new String[2];
    // for (EpocaAbuso epocaAbuso : epocaDB) {
    // rangeValid = validDateForRange(dataDomanda,
    // dateFormat.parse(epocaAbuso.getEpocaDa()),
    // dateFormat.parse(epocaAbuso.getEpocaA()), rangeValid);
    // if (!rangeValid) {
    // errorParam[0] = epocaAbuso.getEpocaDa();
    // errorParam[1] = epocaAbuso.getEpocaA();
    // }
    // }
    // if (!rangeValid)
    // arg1.rejectValue("dataDomanda", "date.legge.not.valid",
    // errorParam, null);
    // } catch (ParseException e1) {
    // e1.printStackTrace();
    // }
    // }
    //
    // private boolean validDateForRange(Date data, Date dataafter,
    // Date databefore, boolean rangeValid) {
    // if (data.after(dataafter) && data.before(databefore))
    // rangeValid = true;
    // return rangeValid;
    // }

}