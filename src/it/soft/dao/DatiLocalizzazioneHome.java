package it.soft.dao;

import it.soft.domain.DatiLocalizzazione;

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
 * Home object for domain model class DatiLocalizzazione.
 * 
 * @see .DatiLocalizzazione
 * @author Hibernate Tools
 */
public class DatiLocalizzazioneHome {

	private static final Log log = LogFactory
			.getLog(DatiLocalizzazioneHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		DatiLocalizzazioneHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	public void persist(DatiLocalizzazione transientInstance) {
		log.debug("persisting DatiLocalizzazione instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			if (transientInstance.getIddatiLocalizzazione() != null)
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

	public void remove(DatiLocalizzazione persistentInstance) {
		log.debug("removing DatiLocalizzazione instance");
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

	public DatiLocalizzazione merge(DatiLocalizzazione detachedInstance) {
		log.debug("merging DatiLocalizzazione instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiLocalizzazione result = (DatiLocalizzazione) sess
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
	public DatiLocalizzazione findById(BigInteger id) {
		log.debug("getting DatiLocalizzazione instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiLocalizzazione datiLocalizzazione = new DatiLocalizzazione();
			datiLocalizzazione.setIddatiLocalizzazione(id);
			Criteria cr = sess.createCriteria(DatiLocalizzazione.class);
			cr.add(Restrictions.eq("iddatiLocalizzazione", id));
			List<DatiLocalizzazione> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<DatiLocalizzazione> iterator = results.iterator(); iterator
						.hasNext();) {
					DatiLocalizzazione datiLocalizzazione2 = iterator.next();
					sess.refresh(datiLocalizzazione2);
					BeanUtils.copyProperties(datiLocalizzazione2,
							datiLocalizzazione);
				}
			}
			log.debug("get successful");
			return datiLocalizzazione;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiLocalizzazione> findAll() {
		log.debug("getting DatiLocalizzazione instance");
		try {
			return (List<DatiLocalizzazione>) hibernateTemplate.findByExample(
					"it.soft.domain.DatiLocalizzazione",
					new DatiLocalizzazione());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

}
