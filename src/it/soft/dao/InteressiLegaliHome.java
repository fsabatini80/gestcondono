package it.soft.dao;

import it.soft.domain.InteressiLegali;

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
 * Home object for domain model class InteressiLegali.
 * 
 * @see .InteressiLegali
 * @author Hibernate Tools
 */
public class InteressiLegaliHome {

	private static final Log log = LogFactory.getLog(InteressiLegaliHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		InteressiLegaliHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public InteressiLegali findById(int id) {
		log.debug("getting InteressiLegali instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			InteressiLegali InteressiLegali = new InteressiLegali();
			InteressiLegali.setIdinteressiLegali(id);
			Criteria cr = sess.createCriteria(InteressiLegali.class);
			cr.add(Restrictions.eq("idinteressiLegali", id));
			List<InteressiLegali> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<InteressiLegali> iterator = results.iterator(); iterator
						.hasNext();) {
					InteressiLegali InteressiLegali2 = iterator.next();
					BeanUtils.copyProperties(InteressiLegali2, InteressiLegali);
				}
			}
			log.debug("get successful");
			sess.close();
			return InteressiLegali;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<InteressiLegali> findAll() {
		log.debug("getting InteressiLegali instance");
		try {
			return (List<InteressiLegali>) hibernateTemplate.findByExample(
					"it.soft.domain.InteressiLegali", new InteressiLegali());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<InteressiLegali> findByDtInizio(Double dataInizio) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(InteressiLegali.class);
			cr.add(Restrictions.eq("datainizio", dataInizio));
			List<InteressiLegali> results = cr.list();
			sess.close();
			return results;
		} catch (RuntimeException re) {
			log.error("count failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<InteressiLegali> findByDtFine(Double dataFine) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(InteressiLegali.class);
			cr.add(Restrictions.eq("datafine", dataFine));
			List<InteressiLegali> results = cr.list();
			sess.close();
			return results;
		} catch (RuntimeException re) {
			log.error("count failed", re);
			throw re;
		}
	}
}
