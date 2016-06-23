package it.soft.service;

import it.soft.dao.DatiVersamentiHome;
import it.soft.domain.DatiVersamento;
import it.soft.domain.TabCalcOblazione;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiVersamentiPojo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

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
		if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getImporto()))
			versamenti.setImporto(new Double(pojo.getImporto()));
		if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getImportoEuro()))
			versamenti.setImportoEuro(new Double(pojo.getImportoEuro()));
		if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getImportoLire()))
			versamenti.setImportoLire(new Double(pojo.getImportoLire()));
		versamenti.setNome(pojo.getNome());
		versamenti.setNumeroBollettino(pojo.getNumeroBollettino());
		versamenti.setNumeroProtocollo(pojo.getNumeroProtocollo());
		versamenti.setRagioneSociale(pojo.getRagioneSociale());
		versamenti.setUfficioPostale(pojo.getUfficioPostale());
		if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getProgressivo_abuso()))
			versamenti.setProgressivo_abuso(Integer.parseInt(pojo
					.getProgressivo_abuso()));

		datiVersamentiHome.persist(versamenti);
	}

	public DatiVersamentiPojo findById(String idVersamento) {
		DatiVersamentiPojo pojo = new DatiVersamentiPojo();

		DatiVersamento source = datiVersamentiHome.findById(BigInteger
				.valueOf(Integer.valueOf(idVersamento)));

		pojo.setCausale(source.getCausale());
		pojo.setCcPostale(source.getCcPostale());
		pojo.setCodiceVersamento(source.getCodiceVersamento());
		pojo.setCognome(source.getCognome());
		pojo.setDataInserimento(source.getDataInserimento());
		pojo.setDataProtocollo(source.getDataProtocollo());
		pojo.setDataVersamento(source.getDataVersamento());
		pojo.setIban(source.getIban());
		pojo.setIddatipratica(String.valueOf(source.getIddatipratica()));
		pojo.setIdversamento(String.valueOf(source.getIddatiVersamento()));
		pojo.setImporto(String.valueOf(source.getImporto()));
		pojo.setImportoEuro(String.valueOf(source.getImportoEuro()));
		pojo.setImportoLire(String.valueOf(source.getImportoLire()));
		pojo.setNome(source.getNome());
		pojo.setNumeroBollettino(source.getNumeroBollettino());
		pojo.setNumeroProtocollo(source.getNumeroProtocollo());
		pojo.setProgressivo_abuso(String.valueOf(source.getProgressivo_abuso()));
		pojo.setRagioneSociale(source.getRagioneSociale());
		pojo.setUfficioPostale(source.getUfficioPostale());

		return pojo;
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

	public Double getAutodeterminaOblazione(String idAbuso,
			String progressivoAbuso) {

		DatiAbusoPojo abuso = datiAbusoService.findById(idAbuso,
				progressivoAbuso);
		return new Double(abuso.getAutodeterminata());
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

	public void remove(String id) {
		datiVersamentiHome.remove(datiVersamentiHome.findById(BigInteger
				.valueOf(Integer.parseInt(id))));
	}
}
