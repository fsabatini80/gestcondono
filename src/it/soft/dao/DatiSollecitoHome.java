package it.soft.dao;

import it.soft.domain.DatiSollecito;
import it.soft.domain.Datiabuso;
import it.soft.domain.Datipratica;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Home object for domain model class DatiSollecito.
 * 
 * @see .DatiSollecito
 * @author Hibernate Tools
 */
public class DatiSollecitoHome {

	private static final Log log = LogFactory.getLog(DatiSollecitoHome.class);

	private static HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		DatiSollecitoHome.hibernateTemplate = new HibernateTemplate(
				sessionFactory);
	}

	public void persist(DatiSollecito transientInstance) {
		log.debug("persisting DatiSollecito instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			if (transientInstance.getIddatiSollecito() != null)
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

	public void remove(DatiSollecito persistentInstance) {
		log.debug("removing DatiSollecito instance");
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

	public DatiSollecito merge(DatiSollecito detachedInstance) {
		log.debug("merging DatiSollecito instance");
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiSollecito result = (DatiSollecito) sess.merge(detachedInstance);
			sess.getTransaction().commit();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public DatiSollecito findById(BigInteger id) {
		log.debug("getting DatiSollecito instance with id: " + id);
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			DatiSollecito datiSollecito = new DatiSollecito();
			datiSollecito.setIddatiSollecito(id);
			Criteria cr = sess.createCriteria(DatiSollecito.class);
			cr.add(Restrictions.eq("iddatiSollecito", id));
			List<DatiSollecito> results = cr.list();
			if (results != null && !results.isEmpty()) {
				for (Iterator<DatiSollecito> iterator = results.iterator(); iterator
						.hasNext();) {
					DatiSollecito datiSollecito2 = iterator.next();
					sess.refresh(datiSollecito2);
					BeanUtils.copyProperties(datiSollecito2, datiSollecito);
				}
			}
			log.debug("findById successful");
			sess.close();
			return datiSollecito;
		} catch (RuntimeException re) {
			log.error("findById failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiSollecito> findAll() {
		log.debug("getting DatiSollecito instance");
		try {
			return (List<DatiSollecito>) hibernateTemplate.findByExample(
					"it.soft.domain.DatiSollecito", new DatiSollecito());
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiSollecito> findAll(BigDecimal idPratica) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiSollecito.class);
			cr.add(Restrictions.eq("iddatiPratica.iddatipratica", idPratica));
			List<DatiSollecito> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("findAll(BigInteger idPratica)  failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiSollecito> findAll(Datipratica idPratica,
			Integer progressivo) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiSollecito.class);
			cr.add(Restrictions.eq("iddatiPratica", idPratica));
			cr.add(Restrictions.eq("progressivoAbuso", progressivo));
			List<DatiSollecito> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("findAll(BigInteger idPratica)  failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiSollecito> findBy(Datipratica idPratica,
			Integer progressivo, String tecnicoincaricato) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiSollecito.class);
			cr.add(Restrictions.eq("iddatiPratica", idPratica));
			cr.add(Restrictions.eq("progressivoAbuso", progressivo));
			cr.add(Restrictions.eq("tecnicoIncaricato", tecnicoincaricato));
			List<DatiSollecito> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("findAll(BigInteger idPratica)  failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiSollecito> findByIdAbuso(Datiabuso id) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiSollecito.class);
			cr.add(Restrictions.eq("idAbuso", id));
			List<DatiSollecito> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("findByIdAbuso failed", re);
			throw re;
		}
	}

	// SELECT * FROM db_condoni_guidonia.dati_sollecito,
	// db_condoni_guidonia.datipratica
	// where iddati_pratica=iddatipratica
	// and legge_condono = 1
	// and data_invio_soll1 =''
	// and pagato = 0;
	@SuppressWarnings("unchecked")
	public List<DatiSollecito> findBy(String data_invio_soll_1,
			int idLeggeCondono) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Query query = sess
					.createQuery("FROM DatiSollecito e INNER JOIN e.iddatiPratica WHERE e.dataInvioSoll1 !=:data_invio_soll_1  AND e.iddatiPratica.leggeCondono.idleggiCondono =:idLeggeCondono ");
			query.setParameter("data_invio_soll_1", data_invio_soll_1);
			query.setParameter("idLeggeCondono", idLeggeCondono);
			return query.list();
		} catch (RuntimeException re) {
			log.error("findBy failed", re);
			throw re;
		}
	}

	public List<DatiSollecito> findBy(String data_invio_soll_1, Boolean pagato,
			int idLeggeCondono) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Query query = sess
					.createQuery("FROM DatiSollecito e INNER JOIN e.iddatiPratica WHERE e.dataInvioSoll1 !=:data_invio_soll_1 AND e.pagato =:pagato AND e.iddatiPratica.leggeCondono.idleggiCondono =:idLeggeCondono ");
			query.setParameter("data_invio_soll_1", data_invio_soll_1);
			query.setParameter("pagato", pagato);
			query.setParameter("idLeggeCondono", idLeggeCondono);
			return query.list();
		} catch (RuntimeException re) {
			log.error("findBy failed", re);
			throw re;
		}
	}
	
	public List<DatiSollecito> findBy(String data_invio_soll_1, Boolean pagato) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Query query = sess
					.createQuery("FROM DatiSollecito e WHERE e.dataInvioSoll1 !=:data_invio_soll_1 AND e.pagato =:pagato ");
			query.setParameter("data_invio_soll_1", data_invio_soll_1);
			query.setParameter("pagato", pagato);
			return query.list();
		} catch (RuntimeException re) {
			log.error("findBy failed", re);
			throw re;
		}
	}

	public List<DatiSollecito> findBy(Boolean pagato) {
		try {
			org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			sess.beginTransaction();
			Criteria cr = sess.createCriteria(DatiSollecito.class);
			cr.add(Restrictions.eq("pagato", pagato));
			List<DatiSollecito> results = cr.list();
			return results;
		} catch (RuntimeException re) {
			log.error("findBy failed", re);
			throw re;
		}
	}
}
