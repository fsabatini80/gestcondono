package it.soft.dao;

import it.soft.domain.LeggiCondono;
import it.soft.domain.TipologiaDocumento;

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
 * Home object for domain model class TipologiaDocumento.
 * 
 * @see .TipologiaDocumento
 * @author Hibernate Tools
 */
public class TipologiaDocHome {

	private static final Log log = LogFactory.getLog(TipologiaDocHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		TipologiaDocHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public TipologiaDocumento findById(int id) {
		log.debug("getting TipologiaDocumento instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			TipologiaDocumento tipologiaDocumento = new TipologiaDocumento();
			tipologiaDocumento.setIdtipologiaDocumento(id);
			Criteria cr = sess.createCriteria(TipologiaDocumento.class);
			cr.add(Restrictions.eq("idtipologiaDocumento", id));
			List<TipologiaDocumento> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<TipologiaDocumento> iterator = results.iterator(); iterator
						.hasNext();) {
					TipologiaDocumento tipologiaDocumento2 = iterator.next();
					BeanUtils.copyProperties(tipologiaDocumento2, tipologiaDocumento);
				}
			}
			log.debug("get successful");
			sess.close();
			return tipologiaDocumento;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TipologiaDocumento> findAll() {
		log.debug("getting TipologiaDocumento instance");
		try {
			return (List<TipologiaDocumento>) hibernateTemplate.findByExample(
					"it.soft.domain.TipologiaDocumento", new TipologiaDocumento());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TipologiaDocumento> findAll(LeggiCondono leggiCondono) {
		log.debug("getting TipologiaDocumento instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(TipologiaDocumento.class);
			cr.add(Restrictions.eq("idLeggiCondono", leggiCondono));
			List<TipologiaDocumento> results = cr.list();
			log.debug("get successful");
			sess.close();
			return results;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
