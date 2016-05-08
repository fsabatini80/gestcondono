package it.soft.dao;

import it.soft.domain.TipologiaAlloggio;

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
 * Home object for domain model class TipologiaAlloggio.
 * 
 * @see .TipologiaAlloggio
 * @author Hibernate Tools
 */
public class TipoAlloggioHome {

	private static final Log log = LogFactory.getLog(TipoAlloggioHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		TipoAlloggioHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public TipologiaAlloggio findById(int id) {
		log.debug("getting TipologiaAlloggio instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			TipologiaAlloggio tipologiaAlloggio = new TipologiaAlloggio();
			tipologiaAlloggio.setIdtipologiaAlloggio(id);
			Criteria cr = sess.createCriteria(TipologiaAlloggio.class);
			cr.add(Restrictions.eq("idtipologiaAlloggio", id));
			List<TipologiaAlloggio> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<TipologiaAlloggio> iterator = results.iterator(); iterator
						.hasNext();) {
					TipologiaAlloggio tipologiaAlloggio2 = iterator.next();
					BeanUtils.copyProperties(tipologiaAlloggio2,
							tipologiaAlloggio);
				}
			}
			log.debug("get successful");
			return tipologiaAlloggio;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TipologiaAlloggio> findAll() {
		log.debug("getting TipologiaAlloggio instance");
		try {
			return (List<TipologiaAlloggio>) hibernateTemplate
					.findByExample("it.soft.domain.TipologiaAlloggio",
							new TipologiaAlloggio());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
