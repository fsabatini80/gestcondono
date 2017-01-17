package it.soft.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import it.soft.domain.Comune;

/**
 * Home object for domain model class Comune.
 * 
 * @see .Comune
 * @author Hibernate Tools
 */
public class ComuniHome {

	private static final Log log = LogFactory.getLog(ComuniHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		ComuniHome.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public void persist(Comune transientInstance) {
		log.debug("persisting Comune instance");
		org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
			.getCurrentSession();
		try {
			sess.beginTransaction();
			sess.saveOrUpdate(transientInstance);
			sess.getTransaction().commit();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			sess.close();
			throw re;
		}
	}

	public void remove(Comune persistentInstance) {
		log.debug("removing Comune instance");
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

	public Comune merge(Comune detachedInstance) {
		log.debug("merging Comune instance");
		org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
			.getCurrentSession();
		try {
			sess.beginTransaction();
			Comune result = (Comune) sess.merge(detachedInstance);
			sess.getTransaction().commit();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			sess.close();
			throw re;
		}
	}

	public Comune findById(Integer id) {
		log.debug("getting Comune instance with id: " + id);
		org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
			.getCurrentSession();
		try {
			sess.beginTransaction();
			Criteria criteria = sess.createCriteria(Comune.class);
			criteria.add(Restrictions.eq("idcomuni", id));
			@SuppressWarnings("unchecked")
			List<Comune> l = criteria.list();
			log.debug("get successful");
			return l.get(0);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			sess.close();
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public Comune findByDescrizione(String comuneStr) {
		log.debug("getting Comune instance with user: " + comuneStr);
		org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
			.getCurrentSession();
		try {
			Comune comune = new Comune();
			comune.setComune(comuneStr);
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(Comune.class);
			cr.add(Restrictions.eq("comune", comuneStr));
			List<Comune> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<Comune> iterator = results.iterator(); iterator
						.hasNext();) {
					Comune comune2 = (Comune) iterator.next();
					comune.setIdcomuni(comune2.getIdcomuni());
					comune.setComune(comune2.getComune());
					comune.setProvincia(comune2.getProvincia());
					comune.setRegione(comune2.getRegione());
				}
			}
			log.debug("get successful");
			return comune;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			sess.close();
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Comune> findAll() {
		log.debug("getting Legenda instance");
		org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
			.getCurrentSession();
		try {
			List<Comune> list = null;
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(Comune.class);
			list = cr.list();
			log.debug("get successful");
			return list;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			sess.close();
			throw re;
		}
	}
}
