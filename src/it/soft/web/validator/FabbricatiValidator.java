package it.soft.web.validator;

import it.soft.domain.DatiFabbricati;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FabbricatiValidator implements Validator {

    // @Autowired
    // DatiFabbricatiHome datiFabbricatiHome;

	public boolean supports(Class<?> arg0) {
		return DatiFabbricati.class.equals(arg0);
	}

	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub

	}

}
