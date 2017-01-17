package it.soft.dao;

import it.soft.domain.Utenti;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Home object for domain model class Utenti.
 * 
 * @see .Utenti
 * @author Hibernate Tools
 */
public class UtentiHome {

    private static final Log log = LogFactory.getLog(UtentiHome.class);

    private static HibernateTemplate hibernateTemplate;

    public void setSessionFactory(SessionFactory sessionFactory) {
	UtentiHome.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public void persist(Utenti transientInstance) {
	log.debug("persisting Utenti instance");
	org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		.getCurrentSession();
	;
	try {
	    sess.beginTransaction();
	    sess.saveOrUpdate(transientInstance);
	    sess.getTransaction().commit();
	    log.debug("persist successful");
	} catch (ConstraintViolationException e) {
	    log.error("persist failed", e);
	    sess.close();
	    throw e;
	} catch (RuntimeException re) {
	    log.error("persist failed", re);
	    sess.close();
	    throw re;
	}
    }

    public void remove(Utenti persistentInstance) {
	log.debug("removing Utenti instance");
	org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		.getCurrentSession();
	try {
	    sess.beginTransaction();
	    sess.delete(hibernateTemplate.load(Utenti.class,
		    persistentInstance.getIdUtenti()));
	    sess.getTransaction().commit();
	    log.debug("remove successful");
	} catch (RuntimeException re) {
	    log.error("remove failed", re);
	    sess.close();
	    throw re;
	}
    }

    public Utenti merge(Utenti detachedInstance) {
	log.debug("merging Utenti instance");
	org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		.getCurrentSession();
	try {
	    sess.beginTransaction();
	    Utenti result = (Utenti) sess.merge(detachedInstance);
	    sess.getTransaction().commit();
	    log.debug("merge successful");
	    return result;
	} catch (RuntimeException re) {
	    log.error("merge failed", re);
	    sess.close();
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public Utenti findById(BigDecimal id) {
	log.debug("getting Utenti instance with id: " + id);
	org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	try {
	    sess.beginTransaction();
	    Utenti utenti = new Utenti();
	    utenti.setIdUtenti(id);
	    Criteria cr = sess.createCriteria(Utenti.class);
	    cr.add(Restrictions.eq("idUtenti", id));
	    List<Utenti> results = cr.list();
	    if (results != null && !results.isEmpty()) {
		for (Iterator<Utenti> iterator = results.iterator(); iterator
			.hasNext();) {
		    Utenti utenti2 = iterator.next();
		    BeanUtils.copyProperties(utenti2, utenti);
		}
	    }
	    log.debug("get successful");
	    sess.close();
	    return utenti;
	} catch (RuntimeException re) {
	    log.error("get failed", re);
	    sess.close();
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public Utenti findByUser(String user) {
	log.debug("getting Utenti instance with username: " + user);
	 org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .openSession();
	try {
	    Utenti utenti = new Utenti();
	    utenti.setUsername(user);
	    sess.beginTransaction();
	    Criteria cr = sess.createCriteria(Utenti.class);
	    cr.add(Restrictions.eq("username", user));
	    List<Utenti> results = cr.list();
	    if (results != null && !results.isEmpty()) {
		for (Iterator<Utenti> iterator = results.iterator(); iterator
			.hasNext();) {
		    Utenti utenti2 = iterator.next();
		    BeanUtils.copyProperties(utenti2, utenti);
		}
	    }
	    log.debug("get successful");
	    sess.close();
	    return utenti;
	} catch (RuntimeException re) {
	    log.error("get failed", re);
	    sess.close();
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public List<Utenti> findAll() {
	log.debug("getting Utenti instance");
	try {
	    return (List<Utenti>) hibernateTemplate.findByExample(
		    "it.soft.domain.Utenti", new Utenti());
	} catch (RuntimeException re) {
	    log.error("get failed", re);
	    throw re;
	}
    }
}
