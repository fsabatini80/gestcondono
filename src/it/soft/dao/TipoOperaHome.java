package it.soft.dao;

import it.soft.domain.TipoOpera;

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
 * Home object for domain model class TipoOpera.
 * 
 * @see .TipoOpera
 * @author Hibernate Tools
 */
public class TipoOperaHome {

	private static final Log log = LogFactory.getLog(TipoOperaHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		TipoOperaHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public TipoOpera findById(int id) {
		log.debug("getting TipoOpera instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			TipoOpera tipoOpera = new TipoOpera();
			tipoOpera.setIdtipoOpera(id);
			Criteria cr = sess.createCriteria(TipoOpera.class);
			cr.add(Restrictions.eq("idtipoOpera", id));
			List<TipoOpera> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<TipoOpera> iterator = results.iterator(); iterator
						.hasNext();) {
					TipoOpera tipoOpera2 = iterator.next();
					BeanUtils.copyProperties(tipoOpera2, tipoOpera);
				}
			}
			log.debug("get successful");
			sess.close();
			return tipoOpera;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TipoOpera> findAll() {
		log.debug("getting TipoOpera instance");
		try {
			return (List<TipoOpera>) hibernateTemplate.findByExample(
					"it.soft.domain.TipoOpera", new TipoOpera());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
