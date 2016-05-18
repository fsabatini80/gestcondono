package it.soft.dao;

import it.soft.domain.TipologiaDestinazioneUso;

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
 * Home object for domain model class TipologiaDestinazioneUso.
 * 
 * @see .TipologiaDestinazioneUso
 * @author Hibernate Tools
 */
public class DestinazioneUsoHome {

	private static final Log log = LogFactory.getLog(DestinazioneUsoHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		DestinazioneUsoHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public TipologiaDestinazioneUso findById(int id) {
		log.debug("getting TipologiaDestinazioneUso instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			TipologiaDestinazioneUso tipologiaDestinazioneUso = new TipologiaDestinazioneUso();
			tipologiaDestinazioneUso.setIdtipologiaDestinazioneUso(id);
			Criteria cr = sess.createCriteria(TipologiaDestinazioneUso.class);
			cr.add(Restrictions.eq("idtipologiaDestinazioneUso", id));
			List<TipologiaDestinazioneUso> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<TipologiaDestinazioneUso> iterator = results.iterator(); iterator
						.hasNext();) {
					TipologiaDestinazioneUso tipologiaDestinazioneUso2 = iterator.next();
					BeanUtils.copyProperties(tipologiaDestinazioneUso2, tipologiaDestinazioneUso);
				}
			}
			log.debug("get successful");
			sess.close();
			return tipologiaDestinazioneUso;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TipologiaDestinazioneUso> findAll() {
		log.debug("getting TipologiaDestinazioneUso instance");
		try {
			return (List<TipologiaDestinazioneUso>) hibernateTemplate.findByExample(
					"it.soft.domain.TipologiaDestinazioneUso", new TipologiaDestinazioneUso());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
