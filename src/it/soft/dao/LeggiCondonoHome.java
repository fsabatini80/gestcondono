package it.soft.dao;

import it.soft.domain.LeggiCondono;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Home object for domain model class LeggiCondono.
 * 
 * @see .LeggiCondono
 * @author Hibernate Tools
 */
public class LeggiCondonoHome {

	private static final Log log = LogFactory.getLog(LeggiCondonoHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		LeggiCondonoHome.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public LeggiCondono findById(Integer id) {
		log.debug("getting LeggiCondono instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria criteria = sess.createCriteria(LeggiCondono.class);
			criteria.add(Restrictions.eq("idleggiCondono", id));
			@SuppressWarnings("unchecked")
			List<LeggiCondono> l = criteria.list();
			log.debug("get successful");
			sess.close();
			return l.get(0);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<LeggiCondono> findAll() {
		log.debug("getting LeggiCondono instance");
		try {
			List<LeggiCondono> list = null;
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(LeggiCondono.class);
			list = cr.list();
			log.debug("get successful");
			sess.close();
			return list;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
