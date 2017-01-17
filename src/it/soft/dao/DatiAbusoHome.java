package it.soft.dao;

import it.soft.domain.Datiabuso;
import it.soft.domain.Datipratica;

import java.math.BigDecimal;
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
 * Home object for domain model class Datiabuso.
 * 
 * @see .Datiabuso
 * @author Hibernate Tools
 */
public class DatiAbusoHome {

    private static final Log log = LogFactory.getLog(DatiAbusoHome.class);

    private static HibernateTemplate hibernateTemplate;

    public void setSessionFactory(SessionFactory sessionFactory) {
	DatiAbusoHome.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public void persist(Datiabuso transientInstance) {
	log.debug("persisting Datiabuso instance");
	org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		.getCurrentSession();
	try {
	    sess.beginTransaction();
	    if (transientInstance.getIddatiabuso() != null)
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

    public void remove(Datiabuso persistentInstance) {
	log.debug("removing Datiabuso instance");
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

    public Datiabuso merge(Datiabuso detachedInstance) {
	log.debug("merging Datiabuso instance");
	 org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	try {
	    sess.beginTransaction();
	    Datiabuso result = (Datiabuso) sess.merge(detachedInstance);
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
    public Datiabuso findById(BigDecimal id) {
	log.debug("getting Datiabuso instance with id: " + id);
	 org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	try {
	    sess.beginTransaction();
	    Datiabuso datiabuso = new Datiabuso();
	    datiabuso.setIddatiabuso(id);
	    Criteria cr = sess.createCriteria(Datiabuso.class);
	    cr.add(Restrictions.eq("iddatiabuso", id));
	    List<Datiabuso> results = cr.list();
	    if (results != null && !results.isEmpty()) {
		for (Iterator<Datiabuso> iterator = results.iterator(); iterator
			.hasNext();) {
		    Datiabuso datiabuso2 = iterator.next();
		    sess.refresh(datiabuso2);
		    if (datiabuso2.getDestinazioneUso() != null)
			sess.refresh(datiabuso2.getDestinazioneUso());
		    if (datiabuso2.getEpocaAbuso() != null)
			sess.refresh(datiabuso2.getEpocaAbuso());
		    if (datiabuso2.getDatiPratica() != null)
			sess.refresh(datiabuso2.getDatiPratica());
		    if (datiabuso2.getLocalizzazione() != null)
			sess.refresh(datiabuso2.getLocalizzazione());
		    if (datiabuso2.getTipologiaAbuso() != null)
			sess.refresh(datiabuso2.getTipologiaAbuso());
		    if (datiabuso2.getTipoOpera() != null)
			sess.refresh(datiabuso2.getTipoOpera());
		    BeanUtils.copyProperties(datiabuso2, datiabuso);
		}
	    }
	    log.debug("get successful");
	    sess.close();
	    return datiabuso;
	} catch (RuntimeException re) {
	    log.error("get failed", re);
	    sess.close();
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public Datiabuso findById(BigDecimal id, Integer progressivo) {
	log.debug("getting Datiabuso instance with id: " + id);
	 org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	try {
	    sess.beginTransaction();
	    Datiabuso datiabuso = new Datiabuso();
	    datiabuso.setIddatiabuso(id);
	    Criteria cr = sess.createCriteria(Datiabuso.class);
	    cr.add(Restrictions.eq("iddatiabuso", id));
	    cr.add(Restrictions.eq("progressivo", progressivo));
	    List<Datiabuso> results = cr.list();
	    if (results != null && !results.isEmpty()) {
		for (Iterator<Datiabuso> iterator = results.iterator(); iterator
			.hasNext();) {
		    Datiabuso datiabuso2 = iterator.next();
		    sess.refresh(datiabuso2);
		    if (datiabuso2.getDestinazioneUso() != null)
			sess.refresh(datiabuso2.getDestinazioneUso());
		    if (datiabuso2.getEpocaAbuso() != null)
			sess.refresh(datiabuso2.getEpocaAbuso());
		    if (datiabuso2.getDatiPratica() != null)
			sess.refresh(datiabuso2.getDatiPratica());
		    if (datiabuso2.getLocalizzazione() != null)
			sess.refresh(datiabuso2.getLocalizzazione());
		    if (datiabuso2.getTipologiaAbuso() != null)
			sess.refresh(datiabuso2.getTipologiaAbuso());
		    if (datiabuso2.getTipoOpera() != null)
			sess.refresh(datiabuso2.getTipoOpera());
		    BeanUtils.copyProperties(datiabuso2, datiabuso);
		}
	    }
	    log.debug("get successful");
	    sess.close();
	    return datiabuso;
	} catch (RuntimeException re) {
	    log.error("get failed", re);
	    sess.close();
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public List<Datiabuso> findAll() {
	log.debug("getting Datiabuso instance");
	try {
	    return (List<Datiabuso>) hibernateTemplate.findByExample(
		    "it.soft.domain.Datiabuso", new Datiabuso());
	} catch (RuntimeException re) {
	    log.error("get failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public Integer countProg(Datipratica idPratica) {
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    Criteria cr = sess.createCriteria(Datiabuso.class);
	    cr.add(Restrictions.eq("datiPratica", idPratica));
	    List<Datiabuso> results = cr.list();
	    sess.close();
	    if (results.isEmpty()) {
		return 0;
	    }
	    return results.size();
	} catch (RuntimeException re) {
	    log.error("count failed", re);
	}
	return 0;
    }

    @SuppressWarnings("unchecked")
    public List<Datiabuso> findAll(Datipratica idPratica) {
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    Criteria cr = sess.createCriteria(Datiabuso.class);
	    cr.add(Restrictions.eq("datiPratica", idPratica));
	    List<Datiabuso> results = cr.list();
	    sess.close();
	    return results;
	} catch (RuntimeException re) {
	    log.error("count failed", re);
	    throw re;
	}
    }
}
