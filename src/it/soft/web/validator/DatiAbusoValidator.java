package it.soft.web.validator;

import it.soft.web.pojo.DatiPraticaPojo;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DatiAbusoValidator implements Validator {

	public boolean supports(Class<?> arg0) {
		return DatiPraticaPojo.class.equals(arg0);
	}

	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		// ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "numeroPratica",
		// "obbligatorio");
	}

}