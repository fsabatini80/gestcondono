package it.soft.dao;

import it.soft.domain.EpocaAbuso;
import it.soft.domain.LeggiCondono;

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
public class EpocaAbusoHome {

	private static final Log log = LogFactory.getLog(EpocaAbusoHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		EpocaAbusoHome.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public EpocaAbuso findById(int id) {
		log.debug("getting EpocaAbuso instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			EpocaAbuso epocaAbuso = new EpocaAbuso();
			epocaAbuso.setIdepocaAbuso(id);
			Criteria cr = sess.createCriteria(EpocaAbuso.class);
			cr.add(Restrictions.eq("idepocaAbuso", id));
			List<EpocaAbuso> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<EpocaAbuso> iterator = results.iterator(); iterator
						.hasNext();) {
					EpocaAbuso epocaAbuso2 = iterator.next();
					BeanUtils.copyProperties(epocaAbuso2, epocaAbuso);
				}
			}
			log.debug("get successful");
			sess.close();
			return epocaAbuso;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<EpocaAbuso> findAll() {
		log.debug("getting EpocaAbuso instance");
		try {
			return (List<EpocaAbuso>) hibernateTemplate.findByExample(
					"it.soft.domain.EpocaAbuso", new EpocaAbuso());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<EpocaAbuso> findAll(LeggiCondono leggiCondono) {
		log.debug("getting EpocaAbuso instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(EpocaAbuso.class);
			cr.add(Restrictions.eq("leggeCondono", leggiCondono));
			List<EpocaAbuso> results = cr.list();
			log.debug("get successful");
			sess.close();
			return results;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
