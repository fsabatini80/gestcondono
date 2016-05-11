package it.soft.dao;

import it.soft.domain.DatiFabbricati;

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
 * Home object for domain model class DatiFabbricati.
 * 
 * @see .DatiFabbricati
 * @author Hibernate Tools
 */
public class DatiFabbricatiHome {

	private static final Log log = LogFactory.getLog(DatiFabbricatiHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		DatiFabbricatiHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	public void persist(DatiFabbricati transientInstance) {
		log.debug("persisting DatiFabbricati instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			if (transientInstance.getIddatiFabbricati() != 0)
				sess.merge(transientInstance);
			else
				sess.saveOrUpdate(transientInstance);
			sess.getTransaction().commit();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(DatiFabbricati persistentInstance) {
		log.debug("removing DatiFabbricati instance");
		try {
			org.hibernate.Session sess2 = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess2.beginTransaction();
			sess2.delete(hibernateTemplate.load(DatiFabbricati.class,
					persistentInstance.getIddatiFabbricati()));
			sess2.getTransaction().commit();
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public DatiFabbricati merge(DatiFabbricati detachedInstance) {
		log.debug("merging DatiFabbricati instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiFabbricati result = (DatiFabbricati) sess
					.merge(detachedInstance);
			sess.getTransaction().commit();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public DatiFabbricati findById(Integer id) {
		log.debug("getting DatiFabbricati instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiFabbricati datiFabbricati = new DatiFabbricati();
			datiFabbricati.setIddatiFabbricati(id);
			Criteria cr = sess.createCriteria(DatiFabbricati.class);
			cr.add(Restrictions.eq("iddatiFabbricati", id));
			List<DatiFabbricati> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<DatiFabbricati> iterator = results.iterator(); iterator
						.hasNext();) {
					DatiFabbricati datiFabbricati2 = iterator.next();
					sess.refresh(datiFabbricati2);
					BeanUtils.copyProperties(datiFabbricati2, datiFabbricati);
				}
			}
			log.debug("get successful");
			return datiFabbricati;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiFabbricati> findAll() {
		log.debug("getting DatiFabbricati instance");
		try {
			return (List<DatiFabbricati>) hibernateTemplate.findByExample(
					"it.soft.domain.DatiFabbricati", new DatiFabbricati());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiFabbricati> findAll(Integer idAlloggio) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiFabbricati.class);
			cr.add(Restrictions.eq("idAlloggio", idAlloggio));
			List<DatiFabbricati> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("count failed", re);
			throw re;
		}
	}
}
