package it.soft.web.validator;

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
		if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getImporto())
				 && !Converter.checkDoubleFormat(pojo.getImporto())) {
			arg1.rejectValue("importo", "double.format");
		}
		
	}

}