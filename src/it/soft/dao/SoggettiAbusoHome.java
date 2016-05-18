package it.soft.dao;

import it.soft.domain.SoggettiAbuso;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class SoggettiAbusoHome {

	private static final Log log = LogFactory.getLog(SoggettiAbusoHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		SoggettiAbusoHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public SoggettiAbuso findById(Integer id) {
		log.debug("getting SoggettiAbuso instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			SoggettiAbuso soggettiAbuso = new SoggettiAbuso();
			soggettiAbuso.setIdsoggettiAbuso(id);
			Criteria cr = sess.createCriteria(SoggettiAbuso.class);
			cr.add(Restrictions.eq("idsoggettiAbuso", id));
			List<SoggettiAbuso> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<SoggettiAbuso> iterator = results.iterator(); iterator
						.hasNext();) {
					SoggettiAbuso soggettiAbuso2 = iterator.next();
					sess.refresh(soggettiAbuso2);
					BeanUtils.copyProperties(soggettiAbuso2, soggettiAbuso);
				}
			}
			log.debug("get successful");
			sess.close();
			return soggettiAbuso;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<SoggettiAbuso> findAll() {
		log.debug("getting SoggettiAbuso instance");
		try {
			return (List<SoggettiAbuso>) hibernateTemplate.findByExample(
					"it.soft.domain.SoggettiAbuso", new SoggettiAbuso());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
