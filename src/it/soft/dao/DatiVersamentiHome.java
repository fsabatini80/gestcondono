package it.soft.dao;

import it.soft.domain.DatiVersamenti;
import it.soft.domain.Datiabuso;
import it.soft.domain.Datipratica;

import java.math.BigInteger;
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
 * Home object for domain model class DatiVersamenti.
 * 
 * @see .DatiVersamenti
 * @author Hibernate Tools
 */
public class DatiVersamentiHome {

	private static final Log log = LogFactory.getLog(DatiVersamentiHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		DatiVersamentiHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	public void persist(DatiVersamenti transientInstance) {
		log.debug("persisting DatiVersamenti instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			if (transientInstance.getIddati_versamento() != null)
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

	public void remove(DatiVersamenti persistentInstance) {
		log.debug("removing DatiVersamenti instance");
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

	public DatiVersamenti merge(DatiVersamenti detachedInstance) {
		log.debug("merging DatiVersamenti instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiVersamenti result = (DatiVersamenti) sess.merge(detachedInstance);
			sess.getTransaction().commit();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public DatiVersamenti findById(BigInteger id) {
		log.debug("getting DatiVersamenti instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiVersamenti datiVersamenti = new DatiVersamenti();
			datiVersamenti.setIddati_versamento(id);
			Criteria cr = sess.createCriteria(DatiVersamenti.class);
			cr.add(Restrictions.eq("iddati_versamento", id));
			List<DatiVersamenti> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<DatiVersamenti> iterator = results.iterator(); iterator
						.hasNext();) {
					DatiVersamenti datiVersamenti2 = iterator.next();
					sess.refresh(datiVersamenti2);
					BeanUtils.copyProperties(datiVersamenti2, datiVersamenti);
				}
			}
			log.debug("get successful");
			sess.close();
			return datiVersamenti;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiVersamenti> findAll() {
		log.debug("getting DatiVersamenti instance");
		try {
			return (List<DatiVersamenti>) hibernateTemplate.findByExample(
					"it.soft.domain.DatiVersamenti", new DatiVersamenti());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiVersamenti> findAll(Datipratica idPratica) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiVersamenti.class);
			cr.add(Restrictions.eq("datiPratica", idPratica));
			List<DatiVersamenti> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("count failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiVersamenti> findByIdAbuso(Datiabuso id) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiVersamenti.class);
			cr.add(Restrictions.eq("idAbuso", id));
			List<DatiVersamenti> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("count failed", re);
			throw re;
		}
	}
}
