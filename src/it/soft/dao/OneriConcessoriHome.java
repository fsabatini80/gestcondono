package it.soft.dao;

import it.soft.domain.OneriConcessori;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Home object for domain model class OneriConcessori.
 * 
 * @see .OneriConcessori
 * @author Hibernate Tools
 */
public class OneriConcessoriHome {

	private static final Log log = LogFactory.getLog(OneriConcessoriHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		OneriConcessoriHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	public OneriConcessori findById(Integer id) {
		log.debug("getting OneriConcessori instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria criteria = sess.createCriteria(OneriConcessori.class);
			criteria.add(Restrictions.eq("idoneriConcessori", id));
			@SuppressWarnings("unchecked")
			List<OneriConcessori> l = criteria.list();
			log.debug("get successful");
			sess.close();
			return l.get(0);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<OneriConcessori> findAll() {
		log.debug("getting OneriConcessori instance");
		try {
			List<OneriConcessori> list = null;
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(OneriConcessori.class);
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = cr.list();
			log.debug("get successful");
			sess.close();
			return list;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<OneriConcessori> findBy(String zonaUrbanizzazione,
			String destinazioneUso, String numeroAddetti, String tipoOpera) {
		log.debug("getting OneriConcessori instance");
		try {
			List<OneriConcessori> list = null;
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(OneriConcessori.class);
			if (zonaUrbanizzazione != null)
				cr.add(Restrictions
						.eq("zonaUrbanizzazione", zonaUrbanizzazione));
			cr.add(Restrictions.eq("destinazioneUso", destinazioneUso));
			if (numeroAddetti != null) {
				cr.add(Restrictions.ge("addettiMin", numeroAddetti));
				cr.add(Restrictions.le("addettiMax", numeroAddetti));
			}
			if (tipoOpera != null)
				cr.add(Restrictions.eq("tipoopera", tipoOpera));
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
