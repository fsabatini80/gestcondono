package it.soft.dao;

import it.soft.domain.Utenti;

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
 * Home object for domain model class Utenti.
 * 
 * @see .Utenti
 * @author Hibernate Tools
 */
public class UtentiHome {

	private static final Log log = LogFactory.getLog(UtentiHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		UtentiHome.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public void persist(Utenti transientInstance) {
		log.debug("persisting Utenti instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			sess.saveOrUpdate(transientInstance);
			sess.getTransaction().commit();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Utenti persistentInstance) {
		log.debug("removing Utenti instance");
		try {
			org.hibernate.Session sess2 = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess2.beginTransaction();
			sess2.delete(hibernateTemplate.load(Utenti.class,
					persistentInstance.getIdUtenti()));
			sess2.getTransaction().commit();
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Utenti merge(Utenti detachedInstance) {
		log.debug("merging Utenti instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Utenti result = (Utenti) sess.merge(detachedInstance);
			sess.getTransaction().commit();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public Utenti findById(BigDecimal id) {
		log.debug("getting Utenti instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Utenti utenti = new Utenti();
			utenti.setIdUtenti(id);
			Criteria cr = sess.createCriteria(Utenti.class);
			cr.add(Restrictions.eq("idUtenti", id));
			List<Utenti> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<Utenti> iterator = results.iterator(); iterator
						.hasNext();) {
					Utenti utenti2 = iterator.next();
					BeanUtils.copyProperties(utenti2, utenti);
				}
			}
			log.debug("get successful");
			return utenti;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public Utenti findByUser(String user) {
		log.debug("getting Utenti instance with username: " + user);
		try {
			Utenti utenti = new Utenti();
			utenti.setUsername(user);
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.openSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(Utenti.class);
			cr.add(Restrictions.eq("username", user));
			List<Utenti> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<Utenti> iterator = results.iterator(); iterator
						.hasNext();) {
					Utenti utenti2 = iterator.next();
					BeanUtils.copyProperties(utenti2, utenti);
				}
			}
			log.debug("get successful");
			return utenti;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Utenti> findAll() {
		log.debug("getting Utenti instance");
		try {
			return (List<Utenti>) hibernateTemplate.findByExample(
					"it.soft.domain.Utenti", new Utenti());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
