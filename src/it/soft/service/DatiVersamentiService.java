package it.soft.service;

import it.soft.dao.DatiVersamentiHome;
import it.soft.domain.DatiVersamento;
import it.soft.domain.TabCalcOblazione;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiPraticaPojo;
import it.soft.web.pojo.DatiVersamentiPojo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatiVersamentiService {

	@Autowired
	DatiVersamentiHome datiVersamentiHome;

	@Autowired
	DatiPraticaService datiPraticaService;

	@Autowired
	DatiAbusoService datiAbusoService;

	public void persist(DatiVersamentiPojo pojo) {
		DatiVersamento versamenti = new DatiVersamento();

		versamenti.setCausale(pojo.getCausale());
		versamenti.setCcPostale(pojo.getCcPostale());
		versamenti.setCodiceVersamento(pojo.getCodiceVersamento());
		versamenti.setCognome(pojo.getCognome());
		versamenti.setDataInserimento(pojo.getDataInserimento());
		versamenti.setDataProtocollo(pojo.getDataProtocollo());
		versamenti.setDataVersamento(pojo.getDataVersamento());
		versamenti.setIban(pojo.getIban());
		// versamenti.setIddati_versamento(iddati_versamento)
		versamenti.setIddatipratica(BigInteger.valueOf(Integer.parseInt(pojo
				.getIddatipratica())));
		versamenti.setImporto(new Double(pojo.getImporto()));
		versamenti.setImportoEuro(new Double(pojo.getImportoEuro()));
		versamenti.setImportoLire(new Double(pojo.getImportoLire()));
		versamenti.setNome(pojo.getNome());
		versamenti.setNumeroBollettino(pojo.getNumeroBollettino());
		versamenti.setNumeroProtocollo(pojo.getNumeroProtocollo());
		versamenti.setRagioneSociale(pojo.getRagioneSociale());
		versamenti.setUfficioPostale(pojo.getUfficioPostale());

		datiVersamentiHome.persist(versamenti);
	}

	public Double getImportoVersatoOblazione(String idPratica) {

		List<DatiVersamento> vers = datiVersamentiHome.findAll(BigInteger
				.valueOf(Integer.valueOf(idPratica)));

		Double answer = new Double(0.0);

		for (DatiVersamento datiVersamento : vers) {
			answer = Math
					.abs(datiVersamento.getImporto() != null ? datiVersamento
							.getImporto() : new Double(0.0));
		}
		return answer;
	}

	public Double getAutodeterminaOblazione(String idPratica) {

		DatiPraticaPojo pratica = datiPraticaService.findById(idPratica);
		return new Double(pratica.getAutodeterminata());
	}

	public Double getImportoCalcolatoOblazione(Integer tipoAbuso,
			Double dataAbuso, String leggeCondono, String idAbuso) {

		// TODO
		List<TabCalcOblazione> tabOblList = datiVersamentiHome.findOblazione(
				dataAbuso, leggeCondono, tipoAbuso);
		TabCalcOblazione calcOblazione = null;
		Double importoObla = new Double(0.0);
		for (TabCalcOblazione tabCalcOblazione : tabOblList) {
			calcOblazione = tabCalcOblazione;
		}

		if (calcOblazione != null
				&& ("1".equals(leggeCondono) || "2".equals(leggeCondono))) {
			importoObla = Converter.convertLireEuro(new Double(calcOblazione
					.getImportoOblazione()));
			System.out.println(importoObla.doubleValue());
		}

		DatiAbusoPojo abusoDB = datiAbusoService.findById(idAbuso);
		if (abusoDB != null && abusoDB.getSuperficeUtile() != null
				&& !"".equals(abusoDB.getSuperficeUtile().trim())) {
			String superUtile = abusoDB.getSuperficeUtile();

			Double supUtilDouble = new Double(superUtile);
			importoObla = Converter.round(importoObla * supUtilDouble, 2);
		}

		/**
		 * Interessi di mora
		 * 
		 */
		return importoObla;
	}
}
