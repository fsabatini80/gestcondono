package it.soft.web.validator;

import it.soft.web.pojo.DatiVersamentiPojo;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class DatiVersamentiValidator implements Validator {

	public boolean supports(Class<?> arg0) {
		return DatiVersamentiPojo.class.equals(arg0);
	}

	public void validate(Object arg0, Errors arg1) {
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "progressivo_abuso",
				"obbligatorio");
	}

}