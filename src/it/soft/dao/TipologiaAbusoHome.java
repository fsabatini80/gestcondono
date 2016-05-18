package it.soft.dao;

import it.soft.domain.LeggiCondono;
import it.soft.domain.TipologiaAbuso;

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
 * Home object for domain model class TipologiaAbuso.
 * 
 * @see .TipologiaAbuso
 * @author Hibernate Tools
 */
public class TipologiaAbusoHome {

	private static final Log log = LogFactory.getLog(TipologiaAbusoHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		TipologiaAbusoHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public TipologiaAbuso findById(int id) {
		log.debug("getting TipologiaAbuso instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			TipologiaAbuso tipologiaAbuso = new TipologiaAbuso();
			tipologiaAbuso.setIdtipologiaAbuso(id);
			Criteria cr = sess.createCriteria(TipologiaAbuso.class);
			cr.add(Restrictions.eq("idtipologiaAbuso", id));
			List<TipologiaAbuso> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<TipologiaAbuso> iterator = results.iterator(); iterator
						.hasNext();) {
					TipologiaAbuso tipologiaAbuso2 = iterator.next();
					BeanUtils.copyProperties(tipologiaAbuso2, tipologiaAbuso);
				}
			}
			log.debug("get successful");
			sess.close();
			return tipologiaAbuso;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TipologiaAbuso> findAll() {
		log.debug("getting TipologiaAbuso instance");
		try {
			return (List<TipologiaAbuso>) hibernateTemplate.findByExample(
					"it.soft.domain.TipologiaAbuso", new TipologiaAbuso());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TipologiaAbuso> findAll(LeggiCondono leggiCondono) {
		log.debug("getting TipologiaAbuso instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(TipologiaAbuso.class);
			cr.add(Restrictions.eq("leggeCondono", leggiCondono));
			List<TipologiaAbuso> results = cr.list();
			log.debug("get successful");
			sess.close();
			return results;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
