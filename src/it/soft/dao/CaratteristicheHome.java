package it.soft.dao;

import it.soft.domain.CaratteristicheSpeciali;

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
 * Home object for domain model class CaratteristicheSpeciali.
 * 
 * @see .CaratteristicheSpeciali
 * @author Hibernate Tools
 */
public class CaratteristicheHome {

	private static final Log log = LogFactory.getLog(CaratteristicheHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		CaratteristicheHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public CaratteristicheSpeciali findById(int id) {
		log.debug("getting CaratteristicheSpeciali instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			CaratteristicheSpeciali caratteristicheSpeciali = new CaratteristicheSpeciali();
			caratteristicheSpeciali.setIdcaratteristicheSpeciali(id);
			Criteria cr = sess.createCriteria(CaratteristicheSpeciali.class);
			cr.add(Restrictions.eq("idcaratteristicheSpeciali", id));
			List<CaratteristicheSpeciali> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<CaratteristicheSpeciali> iterator = results
						.iterator(); iterator.hasNext();) {
					CaratteristicheSpeciali caratteristicheSpeciali2 = iterator
							.next();
					BeanUtils.copyProperties(caratteristicheSpeciali2,
							caratteristicheSpeciali);
				}
			}
			log.debug("get successful");
			sess.close();
			return caratteristicheSpeciali;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<CaratteristicheSpeciali> findAll() {
		log.debug("getting CaratteristicheSpeciali instance");
		try {
			return (List<CaratteristicheSpeciali>) hibernateTemplate
					.findByExample("it.soft.domain.CaratteristicheSpeciali",
							new CaratteristicheSpeciali());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
