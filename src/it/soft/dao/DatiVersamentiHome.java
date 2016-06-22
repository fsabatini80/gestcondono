package it.soft.dao;

import it.soft.domain.DatiVersamento;
import it.soft.domain.Datiabuso;
import it.soft.domain.TabCalcOblazione;

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
 * Home object for domain model class DatiVersamento.
 * 
 * @see .DatiVersamento
 * @author Hibernate Tools
 */
public class DatiVersamentiHome {

	private static final Log log = LogFactory.getLog(DatiVersamentiHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		DatiVersamentiHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	public void persist(DatiVersamento transientInstance) {
		log.debug("persisting DatiVersamento instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			if (transientInstance.getIddatiVersamento() != null)
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

	public void remove(DatiVersamento persistentInstance) {
		log.debug("removing DatiVersamento instance");
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

	public DatiVersamento merge(DatiVersamento detachedInstance) {
		log.debug("merging DatiVersamento instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiVersamento result = (DatiVersamento) sess
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
	public DatiVersamento findById(BigInteger id) {
		log.debug("getting DatiVersamento instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiVersamento datiVersamento = new DatiVersamento();
			datiVersamento.setIddatiVersamento(id);
			Criteria cr = sess.createCriteria(DatiVersamento.class);
			cr.add(Restrictions.eq("iddatiVersamento", id));
			List<DatiVersamento> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<DatiVersamento> iterator = results.iterator(); iterator
						.hasNext();) {
					DatiVersamento datiVersamento2 = iterator.next();
					sess.refresh(datiVersamento2);
					BeanUtils.copyProperties(datiVersamento2, datiVersamento);
				}
			}
			log.debug("findById successful");
			sess.close();
			return datiVersamento;
		} catch (RuntimeException re) {
			log.error("findById failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiVersamento> findAll() {
		log.debug("getting DatiVersamento instance");
		try {
			return (List<DatiVersamento>) hibernateTemplate.findByExample(
					"it.soft.domain.DatiVersamento", new DatiVersamento());
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiVersamento> findAll(BigInteger idPratica) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiVersamento.class);
			cr.add(Restrictions.eq("iddatipratica", idPratica));
			List<DatiVersamento> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("findAll(BigInteger idPratica)  failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiVersamento> findByIdAbuso(Datiabuso id) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiVersamento.class);
			cr.add(Restrictions.eq("idAbuso", id));
			List<DatiVersamento> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("findByIdAbuso failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TabCalcOblazione> findOblazione(Double dataAbuso,
			String leggeCondono, Integer tipoAbuso) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(TabCalcOblazione.class);
			cr.add(Restrictions.lt("dataInizio", dataAbuso));
			cr.add(Restrictions.ge("dataFine", dataAbuso));
			cr.add(Restrictions.eq("leggeCondono",
					Integer.valueOf(leggeCondono)));
			cr.add(Restrictions.eq("tipoabuso", tipoAbuso));
			List<TabCalcOblazione> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("findOblazione failed", re);
			throw re;
		}
	}
}
