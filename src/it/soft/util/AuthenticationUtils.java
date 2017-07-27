package it.soft.util;

import it.soft.dao.UtentiHome;
import it.soft.domain.Utenti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUtils {

    @Autowired
    UtentiHome utentiHome;

    public Utenti getUtente() {
	User user = (User) SecurityContextHolder.getContext()
		.getAuthentication().getPrincipal();
	String name = user.getUsername();
	return getUtente(name);
    }

    private Utenti getUtente(String name) {
	return utentiHome.findByUser(name);
    }

}
