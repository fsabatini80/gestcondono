package it.soft.web.validator;

import it.soft.web.pojo.DatiAbusoPojo;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class DatiAbusoValidator implements Validator {

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
	}

}