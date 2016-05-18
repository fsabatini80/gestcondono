package it.soft.dao;

import it.soft.domain.Datiabuso;
import it.soft.domain.DocumentiAbuso;

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
 * Home object for domain model class DocumentiAbuso.
 * 
 * @see .DocumentiAbuso
 * @author Hibernate Tools
 */
public class DocumentiAbusoHome {

	private static final Log log = LogFactory.getLog(DocumentiAbusoHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		DocumentiAbusoHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	public void persist(DocumentiAbuso transientInstance) {
		log.debug("persisting DocumentiAbuso instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			if (transientInstance.getIddocumentiAbuso() != 0)
				sess.merge(transientInstance);
			else
				sess.saveOrUpdate(transientInstance);
			sess.getTransaction().commit();
			log.debug("persist successful");
			sess.close();
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(DocumentiAbuso persistentInstance) {
		log.debug("removing DocumentiAbuso instance");
		try {
			org.hibernate.Session sess2 = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess2.beginTransaction();
			sess2.delete(hibernateTemplate.load(DocumentiAbuso.class,
					persistentInstance.getIddocumentiAbuso()));
			sess2.getTransaction().commit();
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public DocumentiAbuso merge(DocumentiAbuso detachedInstance) {
		log.debug("merging DocumentiAbuso instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DocumentiAbuso result = (DocumentiAbuso) sess
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
	public DocumentiAbuso findById(int id) {
		log.debug("getting DocumentiAbuso instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DocumentiAbuso documentiAbuso = new DocumentiAbuso();
			documentiAbuso.setIddocumentiAbuso(id);
			Criteria cr = sess.createCriteria(DocumentiAbuso.class);
			cr.add(Restrictions.eq("iddocumentiAbuso", id));
			List<DocumentiAbuso> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<DocumentiAbuso> iterator = results.iterator(); iterator
						.hasNext();) {
					DocumentiAbuso documentiAbuso2 = iterator.next();
					sess.refresh(documentiAbuso2);
					BeanUtils.copyProperties(documentiAbuso2, documentiAbuso);
				}
			}
			log.debug("get successful");
			sess.close();
			return documentiAbuso;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DocumentiAbuso> findAll() {
		log.debug("getting DocumentiAbuso instance");
		try {
			return (List<DocumentiAbuso>) hibernateTemplate.findByExample(
					"it.soft.domain.DocumentiAbuso", new DocumentiAbuso());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DocumentiAbuso> findAll(Datiabuso iddocumentiAbuso) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DocumentiAbuso.class);
			cr.add(Restrictions.eq("idAbuso", iddocumentiAbuso));
			List<DocumentiAbuso> results = cr.list();
			sess.close();
			return results;
		} catch (RuntimeException re) {
			log.error("count failed", re);
			throw re;
		}
	}
}
