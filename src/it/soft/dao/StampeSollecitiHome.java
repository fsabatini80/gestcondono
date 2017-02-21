package it.soft.dao;

import it.soft.domain.Datipratica;
import it.soft.domain.StampeSolleciti;
import it.soft.util.Converter;

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
 * Home object for domain model class StampeSolleciti.
 * 
 * @see .StampeSolleciti
 * @author Hibernate Tools
 */
public class StampeSollecitiHome {

    private static final Log log = LogFactory.getLog(StampeSollecitiHome.class);

    private static HibernateTemplate hibernateTemplate;

    public void setSessionFactory(SessionFactory sessionFactory) {
	StampeSollecitiHome.hibernateTemplate = new HibernateTemplate(
		sessionFactory);
    }

    public void persist(StampeSolleciti transientInstance) {
	log.debug("persisting StampeSolleciti instance");
	org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		.getCurrentSession();
	try {
	    sess.beginTransaction();
	    if (transientInstance.getIdstampeSolleciti() != null)
		sess.merge(transientInstance);
	    else
		sess.saveOrUpdate(transientInstance);
	    sess.getTransaction().commit();
	    log.debug("persist successful");
	} catch (RuntimeException re) {
	    log.error("persist failed", re);
	    sess.close();
	    throw re;
	}
    }

    public void remove(StampeSolleciti persistentInstance) {
	log.debug("removing StampeSolleciti instance");
	org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		.getCurrentSession();
	try {
	    sess.beginTransaction();
	    sess.delete(persistentInstance);
	    sess.getTransaction().commit();
	    log.debug("remove successful");
	} catch (RuntimeException re) {

	    log.error("remove failed", re);
	    sess.close();
	    throw re;
	}
    }

    public StampeSolleciti merge(StampeSolleciti detachedInstance) {
	log.debug("merging StampeSolleciti instance");
	org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		.getCurrentSession();
	try {
	    sess.beginTransaction();
	    StampeSolleciti result = (StampeSolleciti) sess
		    .merge(detachedInstance);
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
    public List<StampeSolleciti> findAll() {
	log.debug("getting StampeSolleciti instance");
	try {
	    return (List<StampeSolleciti>) hibernateTemplate.findByExample(
		    "it.soft.domain.StampeSolleciti", new StampeSolleciti());
	} catch (RuntimeException re) {
	    log.error("get failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public StampeSolleciti findById(String id) {
	log.debug("getting StampeSolleciti instance with id: " + id);
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    StampeSolleciti stampeSolleciti = new StampeSolleciti();
	    stampeSolleciti.setIdstampeSolleciti(Converter
		    .stringToBigDdecimal(id));
	    Criteria cr = sess.createCriteria(StampeSolleciti.class);
	    cr.add(Restrictions.eq("idstampeSolleciti",
		    Converter.stringToBigDdecimal(id)));
	    List<StampeSolleciti> results = cr.list();
	    if (results != null && !results.isEmpty()) {
		for (Iterator<StampeSolleciti> iterator = results.iterator(); iterator
			.hasNext();) {
		    StampeSolleciti stampeSolleciti2 = iterator.next();
		    BeanUtils.copyProperties(stampeSolleciti2, stampeSolleciti);
		}
	    }
	    log.debug("get successful");
	    sess.close();
	    return stampeSolleciti;
	} catch (RuntimeException re) {
	    log.error("get failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public List<StampeSolleciti> findAll(Datipratica idPratica) {
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    Criteria cr = sess.createCriteria(StampeSolleciti.class);
	    cr.add(Restrictions.eq("datiPratica", idPratica));
	    List<StampeSolleciti> results = cr.list();
	    sess.close();
	    return results;
	} catch (RuntimeException re) {
	    log.error("count failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public List<StampeSolleciti> search(StampeSolleciti stampeSolleciti) {
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    Criteria cr = sess.createCriteria(StampeSolleciti.class);
	    if (stampeSolleciti.getDataInserimento() != null) {
		cr.add(Restrictions.eq("dataInserimento",
			stampeSolleciti.getDataInserimento()));
	    }
	    if (stampeSolleciti.getIdAbuso() != null) {
		cr.add(Restrictions.eq("idAbuso", stampeSolleciti.getIdAbuso()));
	    }
	    if (stampeSolleciti.getIdPratica() != null) {
		cr.add(Restrictions.eq("idPratica",
			stampeSolleciti.getIdPratica()));
	    }
	    if (stampeSolleciti.getIdstampeSolleciti() != null) {
		cr.add(Restrictions.eq("idstampeSolleciti",
			stampeSolleciti.getIdstampeSolleciti()));
	    }
	    if (stampeSolleciti.getNomeFileStampa() != null) {
		cr.add(Restrictions.eq("nomeFileStampa",
			stampeSolleciti.getNomeFileStampa()));
	    }
	    List<StampeSolleciti> results = cr.list();
	    sess.close();
	    return results;
	} catch (RuntimeException re) {
	    log.error("count failed", re);
	    throw re;
	}
    }
}
