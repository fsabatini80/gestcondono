package it.soft.dao;

import it.soft.domain.DatiAlloggio;
import it.soft.domain.Datiabuso;
import it.soft.domain.Datipratica;

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
 * Home object for domain model class DatiAlloggio.
 * 
 * @see .DatiAlloggio
 * @author Hibernate Tools
 */
public class DatiAlloggioHome {

	private static final Log log = LogFactory.getLog(DatiAlloggioHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		DatiAlloggioHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	public void persist(DatiAlloggio transientInstance) {
		log.debug("persisting DatiAlloggio instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			if (transientInstance.getIddatiAlloggio() != null)
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

	public void remove(DatiAlloggio persistentInstance) {
		log.debug("removing DatiAlloggio instance");
		try {
			org.hibernate.Session sess2 = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess2.beginTransaction();
			sess2.delete(persistentInstance);
			sess2.getTransaction().commit();
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public DatiAlloggio merge(DatiAlloggio detachedInstance) {
		log.debug("merging DatiAlloggio instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiAlloggio result = (DatiAlloggio) sess.merge(detachedInstance);
			sess.getTransaction().commit();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public DatiAlloggio findById(BigDecimal id) {
		log.debug("getting DatiAlloggio instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiAlloggio datiAlloggio = new DatiAlloggio();
			datiAlloggio.setIddatiAlloggio(id);
			Criteria cr = sess.createCriteria(DatiAlloggio.class);
			cr.add(Restrictions.eq("iddatiAlloggio", id));
			List<DatiAlloggio> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<DatiAlloggio> iterator = results.iterator(); iterator
						.hasNext();) {
					DatiAlloggio datiAlloggio2 = iterator.next();
					sess.refresh(datiAlloggio2);
					BeanUtils.copyProperties(datiAlloggio2, datiAlloggio);
				}
			}
			log.debug("get successful");
			return datiAlloggio;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiAlloggio> findAll() {
		log.debug("getting DatiAlloggio instance");
		try {
			return (List<DatiAlloggio>) hibernateTemplate.findByExample(
					"it.soft.domain.DatiAlloggio", new DatiAlloggio());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiAlloggio> findAll(Datipratica idPratica) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiAlloggio.class);
			cr.add(Restrictions.eq("datiPratica", idPratica));
			List<DatiAlloggio> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("count failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiAlloggio> findByIdAbuso(Datiabuso id) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiAlloggio.class);
			cr.add(Restrictions.eq("idAbuso", id));
			List<DatiAlloggio> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("count failed", re);
			throw re;
		}
	}
}
