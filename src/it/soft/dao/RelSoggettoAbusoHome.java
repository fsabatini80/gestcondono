package it.soft.dao;

import it.soft.domain.Datipratica;
import it.soft.domain.RelSoggettoAbuso;

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

public class RelSoggettoAbusoHome {

	private static final Log log = LogFactory
			.getLog(RelSoggettoAbusoHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		RelSoggettoAbusoHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	public void persist(RelSoggettoAbuso transientInstance) {
		log.debug("persisting RelSoggettoAbuso instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			if (transientInstance.getIdrelSoggettoAbuso() != 0)
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

	public void remove(RelSoggettoAbuso persistentInstance) {
		log.debug("removing RelSoggettoAbuso instance");
		try {
			org.hibernate.Session sess2 = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess2.beginTransaction();
			try {
				sess2.delete(persistentInstance);
			} catch (RuntimeException e) {
				sess2.delete(hibernateTemplate.load(RelSoggettoAbuso.class,
						persistentInstance.getIdrelSoggettoAbuso()));
			}
			sess2.getTransaction().commit();
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public RelSoggettoAbuso merge(RelSoggettoAbuso detachedInstance) {
		log.debug("merging RelSoggettoAbuso instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			RelSoggettoAbuso result = (RelSoggettoAbuso) sess
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
	public RelSoggettoAbuso findById(Integer id) {
		log.debug("getting RelSoggettoAbuso instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			RelSoggettoAbuso relSoggettoAbuso = new RelSoggettoAbuso();
			relSoggettoAbuso.setIdrelSoggettoAbuso(id);
			Criteria cr = sess.createCriteria(RelSoggettoAbuso.class);
			cr.add(Restrictions.eq("idrelSoggettoAbuso", id));
			List<RelSoggettoAbuso> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<RelSoggettoAbuso> iterator = results.iterator(); iterator
						.hasNext();) {
					RelSoggettoAbuso relSoggettoAbuso2 = iterator.next();
					sess.refresh(relSoggettoAbuso2);
					BeanUtils.copyProperties(relSoggettoAbuso2,
							relSoggettoAbuso);
				}
			}
			log.debug("get successful");
			sess.close();
			return relSoggettoAbuso;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<RelSoggettoAbuso> findAll() {
		log.debug("getting RelSoggettoAbuso instance");
		try {
			return (List<RelSoggettoAbuso>) hibernateTemplate.findByExample(
					"it.soft.domain.RelSoggettoAbuso", new RelSoggettoAbuso());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public Integer countProg(Datipratica idPratica) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(RelSoggettoAbuso.class);
			cr.add(Restrictions.eq("datiPratica", idPratica));
			List<RelSoggettoAbuso> results = cr.list();
			sess.close();
			if (results.isEmpty()) {
				return 0;
			}
			return results.size();
		} catch (RuntimeException re) {
			log.error("count failed", re);
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List<RelSoggettoAbuso> findAll(BigInteger idAbuso) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(RelSoggettoAbuso.class);
			cr.add(Restrictions.eq("idAbuso", idAbuso));
			List<RelSoggettoAbuso> results = cr.list();
			sess.close();
			return results;
		} catch (RuntimeException re) {
			log.error("count failed", re);
			throw re;
		}
	}

}
