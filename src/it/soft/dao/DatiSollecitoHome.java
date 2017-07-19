package it.soft.dao;

import it.soft.domain.DatiSollecito;
import it.soft.domain.Datiabuso;
import it.soft.domain.TabCalcOblazione;
import it.soft.domain.TipoEsenzioni;
import it.soft.domain.TipoRiduzione;
import it.soft.domain.TipologiaRiduzioneReddito;

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
    public List<DatiSollecito> findAll(BigInteger idPratica) {
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    Criteria cr = sess.createCriteria(DatiSollecito.class);
	    cr.add(Restrictions.eq("iddatiPratica", idPratica));
	    List<DatiSollecito> results = cr.list();
	    return results;
	} catch (RuntimeException re) {
	    log.error("findAll(BigInteger idPratica)  failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public List<DatiSollecito> findAll(BigInteger idPratica, Integer progressivo) {
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    Criteria cr = sess.createCriteria(DatiSollecito.class);
	    cr.add(Restrictions.eq("iddatipratica", idPratica));
	    cr.add(Restrictions.eq("progressivo_abuso", progressivo));
	    List<DatiSollecito> results = cr.list();
	    return results;
	} catch (RuntimeException re) {
	    log.error("findAll(BigInteger idPratica)  failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public List<DatiSollecito> findAll(BigInteger idPratica,
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

    @SuppressWarnings("unchecked")
    public List<TabCalcOblazione> findOblazione(String destinazioneUso,
	    String leggeCondono, Integer tipoAbuso) {
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    Criteria cr = sess.createCriteria(TabCalcOblazione.class);
	    cr.add(Restrictions.eq("destinazioneUso",
		    Integer.valueOf(destinazioneUso)));
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

    @SuppressWarnings("unchecked")
    public TipoEsenzioni findEsenzioneById(Integer idtipoEsenzioni) {

	log.debug("getting TipoEsenzioni instance with id: " + idtipoEsenzioni);
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    TipoEsenzioni tipoEsenzioni = new TipoEsenzioni();
	    tipoEsenzioni.setIdtipoEsenzioni(idtipoEsenzioni);
	    Criteria cr = sess.createCriteria(TipoEsenzioni.class);
	    cr.add(Restrictions.eq("idtipoEsenzioni", idtipoEsenzioni));
	    List<TipoEsenzioni> results = cr.list();
	    if (results != null && !results.isEmpty()) {
		for (Iterator<TipoEsenzioni> iterator = results.iterator(); iterator
			.hasNext();) {
		    TipoEsenzioni tipoEsenzioni2 = iterator.next();
		    sess.refresh(tipoEsenzioni2);
		    BeanUtils.copyProperties(tipoEsenzioni2, tipoEsenzioni);
		}
	    }
	    log.debug("findEsenzioneById successful");
	    sess.close();
	    return tipoEsenzioni;
	} catch (RuntimeException re) {
	    log.error("findEsenzioneById failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public List<TipoEsenzioni> findEsenzioni(BigInteger idLeggeCondono) {
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    Criteria cr = sess.createCriteria(TipoEsenzioni.class);
	    cr.add(Restrictions.eq("idleggeCondono", idLeggeCondono));
	    List<TipoEsenzioni> results = cr.list();
	    return results;
	} catch (RuntimeException re) {
	    log.error("findEsenzioni failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public TipoRiduzione findRiduzioneById(Integer idtipoRiduzione) {

	log.debug("getting TipoRiduzione instance with id: " + idtipoRiduzione);
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    TipoRiduzione tipoRiduzione = new TipoRiduzione();
	    tipoRiduzione.setIdtipoRiduzione(idtipoRiduzione);
	    Criteria cr = sess.createCriteria(TipoRiduzione.class);
	    cr.add(Restrictions.eq("idtipoRiduzione", idtipoRiduzione));
	    List<TipoRiduzione> results = cr.list();
	    if (results != null && !results.isEmpty()) {
		for (Iterator<TipoRiduzione> iterator = results.iterator(); iterator
			.hasNext();) {
		    TipoRiduzione tipoRiduzione2 = iterator.next();
		    sess.refresh(tipoRiduzione2);
		    BeanUtils.copyProperties(tipoRiduzione2, tipoRiduzione);
		}
	    }
	    log.debug("findRiduzioneById successful");
	    sess.close();
	    return tipoRiduzione;
	} catch (RuntimeException re) {
	    log.error("findRiduzioneById failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public List<TipoRiduzione> findRiduzioni(BigInteger idLeggeCondono) {
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    Criteria cr = sess.createCriteria(TipoRiduzione.class);
	    cr.add(Restrictions.eq("idleggeCondono", idLeggeCondono));
	    List<TipoRiduzione> results = cr.list();
	    return results;
	} catch (RuntimeException re) {
	    log.error("findEsenzioni failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public List<TipologiaRiduzioneReddito> findAllTipologiaRiduzioneReddito() {
	log.debug("getting TipologiaRiduzioneReddito instance");
	try {
	    return (List<TipologiaRiduzioneReddito>) hibernateTemplate
		    .findByExample("it.soft.domain.TipologiaRiduzioneReddito",
			    new TipologiaRiduzioneReddito());
	} catch (RuntimeException re) {
	    log.error("findAllTipologiaRiduzioneReddito failed", re);
	    throw re;
	}
    }

    @SuppressWarnings("unchecked")
    public List<DatiSollecito> findBy(String dataVersamento,
	    String ufficioPostale, String codiceVersamento) {

	log.debug("getting DatiSollecito instance with dataversamento: "
		+ dataVersamento);
	try {
	    org.hibernate.Session sess = hibernateTemplate.getSessionFactory()
		    .getCurrentSession();
	    sess.beginTransaction();
	    Criteria cr = sess.createCriteria(DatiSollecito.class);
	    cr.add(Restrictions.eq("dataVersamento", dataVersamento));
	    cr.add(Restrictions.eq("ufficioPostale", ufficioPostale));
	    cr.add(Restrictions.eq("codiceVersamento", codiceVersamento));
	    List<DatiSollecito> results = cr.list();
	    log.debug("search versamento successful");
	    sess.close();
	    return results;
	} catch (RuntimeException re) {
	    log.error("search versamento failed", re);
	    throw re;
	}

    }

}
