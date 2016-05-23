package it.soft.web.validator;

import it.soft.dao.EpocaAbusoHome;
import it.soft.dao.LeggiCondonoHome;
import it.soft.domain.EpocaAbuso;
import it.soft.web.pojo.DatiPraticaPojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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
		if (pojo.getLeggeCondono() != null
				&& !"0".equals(pojo.getLeggeCondono())
				&& pojo.getDataDomanda() != null
				&& !"".equals(pojo.getDataDomanda())) {
			String dataDomandaString = pojo.getDataDomanda();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				Date dataDomanda = dateFormat.parse(dataDomandaString);
				GregorianCalendar dataDomandaG = new GregorianCalendar();
				dataDomandaG.setTime(dataDomanda);
				List<EpocaAbuso> epocaDB = epocaAbusoHome
						.findAll(leggiCondonoHome.findById(Integer.valueOf(pojo
								.getLeggeCondono())));
				GregorianCalendar dataDaG = new GregorianCalendar();
				GregorianCalendar dataAG = new GregorianCalendar();
				boolean rangeValid = false;
				for (EpocaAbuso epocaAbuso : epocaDB) {
					String da = epocaAbuso.getEpocaDa();
					String a = epocaAbuso.getEpocaA();
					Date dataDa = dateFormat.parse(da);
					Date dataA = dateFormat.parse(a);
					dataDaG.setTime(dataDa);
					dataAG.setTime(dataA);
					if (dataDomandaG.after(dataDaG)
							&& dataDomandaG.before(dataAG))
						rangeValid = true;
				}
				if (!rangeValid)
					arg1.rejectValue("dataDomanda", "date.legge.not.valid");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

	}

}