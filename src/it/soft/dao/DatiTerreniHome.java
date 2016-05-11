package it.soft.dao;

import it.soft.domain.DatiTerreni;

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
 * Home object for domain model class DatiTerreni.
 * 
 * @see .DatiTerreni
 * @author Hibernate Tools
 */
public class DatiTerreniHome {

	private static final Log log = LogFactory.getLog(DatiTerreniHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		DatiTerreniHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	public void persist(DatiTerreni transientInstance) {
		log.debug("persisting DatiTerreni instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			if (transientInstance.getIddatiTerreni() != 0)
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

	public void remove(DatiTerreni persistentInstance) {
		log.debug("removing DatiTerreni instance");
		try {
			org.hibernate.Session sess2 = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess2.beginTransaction();
			sess2.delete(hibernateTemplate.load(DatiTerreni.class,
					persistentInstance.getIddatiTerreni()));
			sess2.getTransaction().commit();
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public DatiTerreni merge(DatiTerreni detachedInstance) {
		log.debug("merging DatiTerreni instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiTerreni result = (DatiTerreni) sess.merge(detachedInstance);
			sess.getTransaction().commit();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public DatiTerreni findById(Integer id) {
		log.debug("getting DatiTerreni instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiTerreni datiTerreni = new DatiTerreni();
			datiTerreni.setIddatiTerreni(id);
			Criteria cr = sess.createCriteria(DatiTerreni.class);
			cr.add(Restrictions.eq("iddatiTerreni", id));
			List<DatiTerreni> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<DatiTerreni> iterator = results.iterator(); iterator
						.hasNext();) {
					DatiTerreni datiTerreni2 = iterator.next();
					sess.refresh(datiTerreni2);
					BeanUtils.copyProperties(datiTerreni2, datiTerreni);
				}
			}
			log.debug("get successful");
			return datiTerreni;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiTerreni> findAll() {
		log.debug("getting DatiTerreni instance");
		try {
			return (List<DatiTerreni>) hibernateTemplate.findByExample(
					"it.soft.domain.DatiTerreni", new DatiTerreni());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiTerreni> findAll(Integer idAlloggio) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiTerreni.class);
			cr.add(Restrictions.eq("idAlloggio", idAlloggio));
			List<DatiTerreni> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("count failed", re);
			throw re;
		}
	}
}
