package it.soft.web.validator;

import it.soft.dao.DatiFabbricatiHome;
import it.soft.domain.DatiFabbricati;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FabbricatiValidator implements Validator {
	
	@Autowired
	DatiFabbricatiHome datiFabbricatiHome;

	@Override
	public boolean supports(Class<?> arg0) {
		return DatiFabbricati.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub

	}

}
