package it.soft.dao;

import it.soft.domain.RiduzioneReddito;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Home object for domain model class RiduzioneReddito.
 * 
 * @see .RiduzioneReddito
 * @author Hibernate Tools
 */
public class RiduzioneRedditoHome {

    private static final Log log = LogFactory
	    .getLog(RiduzioneRedditoHome.class);

    private static HibernateTemplate hibernateTemplate;

    public void setSessionFactory(SessionFactory sessionFactory) {
	RiduzioneRedditoHome.hibernateTemplate = new HibernateTemplate(
		sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public RiduzioneReddito findById(int id) {
	log.debug("getting RiduzioneReddito instance with id: " + id);
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    RiduzioneReddito riduzioneReddito = new RiduzioneReddito();
	    riduzioneReddito.setIdriduzioneReddito(id);
	    Criteria cr = sess.createCriteria(RiduzioneReddito.class);
	    cr.add(Restrictions.eq("idriduzioneReddito", id));
	    List<RiduzioneReddito> results = cr.list();
	    if (results != null && !results.isEmpty()) {
		for (Iterator<RiduzioneReddito> iterator = results.iterator(); iterator
			.hasNext();) {
		    RiduzioneReddito riduzioneReddito2 = iterator.next();
		    BeanUtils.copyProperties(riduzioneReddito2,
			    riduzioneReddito);
		}
	    }
	    log.debug("get successful");
	    sess.close();
	    return riduzioneReddito;
	} catch (RuntimeException re) {
	    log.error("get failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public List<RiduzioneReddito> findAll() {
	log.debug("getting RiduzioneReddito instance");
	try {
	    return (List<RiduzioneReddito>) hibernateTemplate.findByExample(
		    "it.soft.domain.RiduzioneReddito", new RiduzioneReddito());
	} catch (RuntimeException re) {
	    log.error("get failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public RiduzioneReddito findByRedditoSuperfice(int id, Double reddito,
	    Double superfice) {
	log.debug("getting RiduzioneReddito instance with id: " + id);
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    RiduzioneReddito riduzioneReddito = new RiduzioneReddito();
	    riduzioneReddito.setIdriduzioneReddito(id);
	    Criteria cr = sess.createCriteria(RiduzioneReddito.class);
	    cr.add(Restrictions.eq("idriduzioneReddito", id));
	    cr.add(Restrictions.ge("redditoDa", reddito));
	    cr.add(Restrictions.le("redditoA", reddito));
	    cr.add(Restrictions.ge("superficeDa", superfice));
	    cr.add(Restrictions.le("superficeA", superfice));
	    List<RiduzioneReddito> results = cr.list();
	    if (results != null && !results.isEmpty()) {
		for (Iterator<RiduzioneReddito> iterator = results.iterator(); iterator
			.hasNext();) {
		    RiduzioneReddito riduzioneReddito2 = iterator.next();
		    BeanUtils.copyProperties(riduzioneReddito2,
			    riduzioneReddito);
		}
	    }
	    log.debug("get successful");
	    sess.close();
	    return riduzioneReddito;
	} catch (RuntimeException re) {
	    log.error("get failed", re);
	    throw re;
	}
    }

}
