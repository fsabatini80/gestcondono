package it.soft.dao;

import it.soft.domain.Datipratica;
import it.soft.domain.LeggiCondono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mysql.jdbc.StringUtils;

/**
 * Home object for domain model class Datipratica.
 * 
 * @see .Datipratica
 * @author Hibernate Tools
 */
public class DatiPraticaHome {

	private static final Log log = LogFactory.getLog(DatiPraticaHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		DatiPraticaHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	public void persist(Datipratica transientInstance) {
		log.debug("persisting Datipratica instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			if (transientInstance.getIddatipratica() != null)
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

	public void remove(Datipratica persistentInstance) {
		log.debug("removing Datipratica instance");
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

	public Datipratica merge(Datipratica detachedInstance) {
		log.debug("merging Datipratica instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Datipratica result = (Datipratica) sess.merge(detachedInstance);
			sess.getTransaction().commit();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public Datipratica findById(BigDecimal id) {
		log.debug("getting Datipratica instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Datipratica datipratica = new Datipratica();
			datipratica.setIddatipratica(id);
			Criteria cr = sess.createCriteria(Datipratica.class);
			cr.add(Restrictions.eq("iddatipratica", id));
			List<Datipratica> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<Datipratica> iterator = results.iterator(); iterator
						.hasNext();) {
					Datipratica datipratica2 = iterator.next();
					sess.refresh(datipratica2);
					if (datipratica2.getRichiedente() != null)
						sess.refresh(datipratica2.getRichiedente());
					BeanUtils.copyProperties(datipratica2, datipratica);
				}
			}
			log.debug("get successful");
			sess.close();
			return datipratica;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Datipratica> findAll() {
		log.debug("getting Datipratica instance");
		try {
			return (List<Datipratica>) hibernateTemplate.findByExample(
					"it.soft.domain.Datipratica", new Datipratica());
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Datipratica> findBy(String numeroPratica,
			String numeroProtocollo, String dataDomanda,
			LeggiCondono leggeCondono, String cognome) {
		log.debug("getting Datipratica instance with id: " + leggeCondono);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(Datipratica.class);
			if (!StringUtils.isEmptyOrWhitespaceOnly(numeroPratica))
				cr.add(Restrictions.eq("numeroPratica", numeroPratica));
			if (!StringUtils.isEmptyOrWhitespaceOnly(numeroProtocollo))
				cr.add(Restrictions.eq("numeroProtocollo", numeroProtocollo));
			if (!StringUtils.isEmptyOrWhitespaceOnly(dataDomanda))
				cr.add(Restrictions.eq("dataDomanda", dataDomanda));
			if (leggeCondono != null && !"".equals(leggeCondono))
				cr.add(Restrictions.eq("leggeCondono", leggeCondono));

			List<Datipratica> results = cr.list();
			List<Datipratica> answDatipraticas = new ArrayList<Datipratica>();
			if (!StringUtils.isEmptyOrWhitespaceOnly(cognome)) {
				for (Datipratica datipratica : results) {
					if (datipratica.getRichiedente() != null
							&& cognome.equalsIgnoreCase(datipratica
									.getRichiedente().getCognome())) {
						answDatipraticas.add(datipratica);
					}
				}
			} else {
				answDatipraticas.addAll(results);
			}
			log.debug("get successful");
			sess.close();
			return answDatipraticas;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Datipratica> findBy(String numeroPratica,
			String numeroProtocollo, String dataDomanda,
			LeggiCondono leggeCondono) {
		log.debug("getting Datipratica instance with id: " + leggeCondono);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(Datipratica.class);
			if (!StringUtils.isEmptyOrWhitespaceOnly(numeroPratica))
				cr.add(Restrictions.eq("numeroPratica", numeroPratica));
			if (!StringUtils.isEmptyOrWhitespaceOnly(numeroProtocollo))
				cr.add(Restrictions.eq("numeroProtocollo", numeroProtocollo));
			if (!StringUtils.isEmptyOrWhitespaceOnly(dataDomanda))
				cr.add(Restrictions.eq("dataDomanda", dataDomanda));
			if (leggeCondono != null
					&& !"0".equals(String.valueOf(leggeCondono
							.getIdleggiCondono())))
				cr.add(Restrictions.eq("leggeCondono", leggeCondono));

			List<Datipratica> results = cr.list();
			log.debug("get successful");
			sess.close();
			return results;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
