package it.soft.web.validator;

import it.soft.util.Constants;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiVersamentiPojo;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mysql.jdbc.StringUtils;

@Component
public class DatiVersamentiValidator implements Validator {

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
	}

}