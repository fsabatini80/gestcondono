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
		if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getIdversamento()))
			versamenti.setIddatiVersamento(BigInteger.valueOf(Integer
					.valueOf(pojo.getIdversamento())));
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
		if (source.getImporto() != null)
			pojo.setImporto(String.valueOf(source.getImporto()));
		if (source.getImportoEuro() != null)
			pojo.setImportoEuro(String.valueOf(source.getImportoEuro()));
		if (source.getImportoLire() != null)
			pojo.setImportoLire(String.valueOf(source.getImportoLire()));
		pojo.setNome(source.getNome());
		pojo.setNumeroBollettino(source.getNumeroBollettino());
		pojo.setNumeroProtocollo(source.getNumeroProtocollo());
		pojo.setProgressivo_abuso(String.valueOf(source.getProgressivo_abuso()));
		pojo.setRagioneSociale(source.getRagioneSociale());
		pojo.setUfficioPostale(source.getUfficioPostale());

		return pojo;
	}

	public Double getImportoVersatoOblazione(String idPratica,
			String progressivo) {

		List<DatiVersamento> vers = datiVersamentiHome.findAll(
				BigInteger.valueOf(Integer.valueOf(idPratica)),
				Integer.valueOf(progressivo));

		Double answer = new Double(0.0);

		for (DatiVersamento datiVersamento : vers) {

			if (datiVersamento.getImporto() != null) {
				answer = Math.abs(answer + datiVersamento.getImporto());
			} else if (datiVersamento.getImportoEuro() != null) {
				answer = Math.abs(answer + datiVersamento.getImportoEuro());
			} else if (datiVersamento.getImportoLire() != null) {
				answer = Math.abs(answer
						+ Converter.convertLireEuro(datiVersamento
								.getImportoLire()));
			} else {
				answer = Math.abs(new Double(0.0));
			}
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
		Double supUtilDouble = new Double(0.0);
		if (abusoDB != null && abusoDB.getSuperficeUtile() != null
				&& !"".equals(abusoDB.getSuperficeUtile().trim())) {
			String superUtileString = abusoDB.getSuperficeUtile();

			supUtilDouble = new Double(superUtileString);
			importoObla = Converter.round(importoObla * supUtilDouble, 2);
		}

		if ("1".equals(leggeCondono)) {
			calcolaRiduzioni(importoObla, supUtilDouble);
		}

		/**
		 * Interessi di mora
		 * 
		 */
		return importoObla;
	}

	/**
	 * Se 400< abuso mq< 800 allora L. x 1.2 
	 * Se 800< abuso mq< 1200 allora L. x 2 
	 * Se abuso mq > 1200 allora L. x 3 
	 * Se abuso è prima casa e residente o non ancora abitabile allora oblazione = -1/3 (NO abitazioni di lusso,
	 * cat. A/1). 
	 * Agevolazione valida fino a 150 mq sup. complessiva 
	 * Se esiste una convenzione urbanistica o atto d’obbligo sull’abuso -50% • - 1/3
	 * costruzione industriale o artigianale fino a 3000 mq, ma se > 6000 mq
	 * allora x 1.5 • - 1/3 attività commerciale < 50 mq, ma se >500mq allora x
	 * 1.5 e se >1500 allora x2 • - 1/3 attività sportiva – culturale –
	 * sanitaria - culto • - 1/3 se attività turistica e se <500 mq, ma se > 800
	 * mq allora x1.5 • - 1/3 se in zona agricola
	 * 
	 * @param importoObla
	 */
	private void calcolaRiduzioni(Double importoObla, Double supUtilDouble) {
		// TODO Auto-generated method stub
		
		if(supUtilDouble > 400 && supUtilDouble <= 800){
			importoObla = importoObla * new Double(1.2);
		}else if(supUtilDouble > 800 && supUtilDouble <= 1200){
			importoObla = importoObla * new Double(2);
		}else if(supUtilDouble > 1200){
			importoObla = importoObla * new Double(3);
		}
	}

	public void remove(String id) {
		datiVersamentiHome.remove(datiVersamentiHome.findById(BigInteger
				.valueOf(Integer.parseInt(id))));
	}
}
