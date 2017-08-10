package it.soft.web;

import it.soft.exception.CustomException;
import it.soft.service.DatiSollecitiService;
import it.soft.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class BaseController {

    @Autowired
    MailService mailService;
    @Autowired
    DatiSollecitiService datiSollecitiService;

    @ExceptionHandler(CustomException.class)
    public ModelAndView handleCustomException(CustomException ex) {

	mailService.sendMail(MailService.from, MailService.to,
		MailService.oggetto, ex.getMessage());

	ModelAndView model = new ModelAndView("error/generic_error");
	model.addObject("exception", ex);
	return model;

    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex) {

	mailService.sendMail(MailService.from, MailService.to,
		MailService.oggetto, ex.getMessage());
	ModelAndView model = new ModelAndView("error/exception_error");
	model.addObject("exception", ex);
	return model;

    }
    
    public boolean existScadenze(){
	return datiSollecitiService.existPraticheInScadenza();
    }
}
