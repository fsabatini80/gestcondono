package it.soft.service;

import it.soft.dao.DatiVersamentiHome;
import it.soft.dao.InteressiLegaliHome;
import it.soft.domain.DatiVersamento;
import it.soft.domain.InteressiLegali;
import it.soft.domain.TabCalcOblazione;
import it.soft.domain.TipologiaAbuso;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiVersamentiPojo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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

	@Autowired
	InteressiLegaliHome interessiLegaliHome;

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
		return new Double(abuso.getAutodeterminata() != null ? new Double(
				abuso.getAutodeterminata()) : new Double(0.0));
	}

	public Double getImportoCalcolatoOblazione(TipologiaAbuso tipologiaAbuso,
			Double dataAbuso, String leggeCondono, String idAbuso,
			String destinazioneUso) {

		Integer tipoAbuso = tipologiaAbuso.getDescrizioneBreve();
		// TODO calcolo legge 2 e calcolo legge 3
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
			// se la destinazione uso è diversa da RESIDENZIALE, gli importi in
			// tabella vanno divisi per 2
			if ("1".equals(leggeCondono) && !"1".equals(destinazioneUso)) {
				importoObla = importoObla / 2;
			}
			System.out.println(importoObla.doubleValue());
		}

		DatiAbusoPojo abusoDB = datiAbusoService.findById(idAbuso);
		Double supUtilDouble = new Double(0.0);
		if (abusoDB != null && abusoDB.getSuperficeUtile() != null
				&& !"".equals(abusoDB.getSuperficeUtile().trim())) {
			String superUtileString = abusoDB.getSuperficeUtile();

			supUtilDouble = new Double(superUtileString);
		}

		if ("1".equals(leggeCondono)) {
			importoObla = Converter.round(importoObla * supUtilDouble, 2);
			calcolaRiduzioniLegge1(importoObla, supUtilDouble,
					"10".equals(abusoDB.getRiduzioni()), abusoDB
							.getLocalizzazione().getAbitazioneLusso(), false,
					false, abusoDB.getDestinazioneUso());

			System.out.println("oblazione calcolata per la legge 1: "
					+ importoObla);
			return importoObla;
		}
		if ("2".equals(leggeCondono)) {
			if (tipoAbuso == 4 || tipoAbuso == 5 || tipoAbuso == 6
					|| tipoAbuso == 7) {
				return importoObla;
			}
			importoObla = calcolaRiduzioniLegge2(importoObla, tipoAbuso);
		}

		return importoObla;
	}

	private Double calcolaRiduzioniLegge2(Double importoObla,
			Integer destinazioneUso) {
		return 0.0;
	}

	public Double getOblazioneDovuta(Double oblazioneCalcolata,
			DatiAbusoPojo abusoDB, Double dataAbuso) {

		Double im = new Double(0.0);
		Double oblazioneEIM = new Double(0.0);

		List<DatiVersamento> vers = datiVersamentiHome.findAll(
				BigInteger.valueOf(Integer.valueOf(abusoDB.getIdPratica())),
				Integer.valueOf(abusoDB.getProgressivo()));
		Double importoVersValidi = getVersamentiValidi(dataAbuso, vers);
		Double autoDetermina = new Double(0.0);
		if (!StringUtils.isEmptyOrWhitespaceOnly(abusoDB.getAutodeterminata())) {
			autoDetermina = new Double(abusoDB.getAutodeterminata());
		}
		Double delta = autoDetermina - importoVersValidi;

		// calcolo interessi di mora
		im = calcolaInteressiMoraL1(dataAbuso, new Date(), delta);
		oblazioneEIM = oblazioneCalcolata + im;
		System.out
				.println("oblazione calcolata + interessi di mora per la legge 1: "
						+ oblazioneEIM);
		// calcolo interessi legali
		Double il = calcolaIL(dataAbuso, Converter.dateToDouble(
				Converter.dateToString(new Date()), "yyyyMMdd"),
				oblazioneCalcolata);
		Double t = oblazioneCalcolata - autoDetermina;
		if (t > 0) {
			t = delta + il + im;
		}
		Double importoVersato = new Double(0.0);
		for (DatiVersamento datiVersamento : vers) {

			if (datiVersamento.getImporto() != null) {
				importoVersato = Math.abs(importoVersato
						+ datiVersamento.getImporto());
			} else if (datiVersamento.getImportoEuro() != null) {
				importoVersato = Math.abs(importoVersato
						+ datiVersamento.getImportoEuro());
			} else if (datiVersamento.getImportoLire() != null) {
				importoVersato = Math.abs(importoVersato
						+ Converter.convertLireEuro(datiVersamento
								.getImportoLire()));
			} else {
				importoVersato = Math.abs(new Double(0.0));
			}
		}
		if (importoVersValidi > autoDetermina) {
			Double b = oblazioneCalcolata - importoVersato;
			if (im + b < 0) {
				return new Double(0.0);
			}
		}
		System.out.println("oblazioneCalcolata: " + oblazioneCalcolata);
		System.out.println("importoVersato: " + importoVersato);
		return Converter.round(
				(im + il + (oblazioneCalcolata - importoVersato)), 2);
	}

	private Double calcolaInteressiMoraL1(Double dataAbuso, Date dataOdierna,
			Double delta) {

		Double interessi = new Double(0.0);
		Double oblaIM = delta * 3;
		String dataOdiernaString = Converter.dateToString(dataOdierna);
		interessi = oblaIM
				+ calcolaIL(dataAbuso,
						Converter.dateToDouble(dataOdiernaString, "yyyyMMdd"),
						oblaIM);

		return interessi;
	}

	public Double getVersamentiValidi(Double dataAbuso,
			List<DatiVersamento> vers) {
		List<DatiVersamento> versamentiValidi = new ArrayList<DatiVersamento>();
		for (DatiVersamento versamento : vers) {
			String dataVersa = versamento.getDataVersamento();
			if (dataVersa != null && !"".equals(dataVersa)) {
				Double dv = Converter.dateToDouble(dataVersa);
				if (dv <= dataAbuso) {
					versamentiValidi.add(versamento);
				}
			}
		}

		Double answer = new Double(0.0);

		for (DatiVersamento datiVersamento : versamentiValidi) {

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

	/**
	 * @param importoObla
	 */
	private void calcolaRiduzioniLegge1(Double importoObla,
			Double supUtilDouble, boolean primacasa, boolean abitazioneLusso,
			boolean convenzioneUrbanistica, boolean attoObbligo,
			String destinazioneUso) {
		// Se 400< abuso mq< 800 allora L. x 1.2 Se 800< abuso mq< 1200 allora
		// L. x
		// 2 Se abuso mq > 1200 allora L. x 3
		if (supUtilDouble > 400 && supUtilDouble <= 800) {
			importoObla = importoObla * new Double(1.2);
		} else if (supUtilDouble > 800 && supUtilDouble <= 1200) {
			importoObla = importoObla * new Double(2);
		} else if (supUtilDouble > 1200) {
			importoObla = importoObla * new Double(3);
		}
		// Se abuso è prima casa e residente o
		// non ancora abitabile allora oblazione = -1/3 (NO abitazioni di
		// lusso,
		// cat. A/1). Agevolazione valida fino a 150 mq sup. complessiva
		if (supUtilDouble <= 150 && ((primacasa)) && !abitazioneLusso) {
			importoObla = importoObla - (importoObla * new Double(0.3));
		}
		// Se esiste
		// una convenzione urbanistica o atto d’obbligo sull’abuso -50%
		if (convenzioneUrbanistica || attoObbligo) {
			importoObla = importoObla / 2;
		}

		// • - 1/3 costruzione industriale o artigianale fino a 3000 mq, ma se >
		// 6000
		// mq allora x 1.5
		if (destinazioneUso.equals("3") && supUtilDouble <= 3000) {
			importoObla = importoObla - (importoObla * new Double(0.3));
		} else if (destinazioneUso.equals("3") && supUtilDouble > 6000) {
			importoObla = importoObla * new Double(1.5);
		}

		// • - 1/3 attività commerciale < 50 mq, ma se >500mq allora x*
		// * 1.5 e se >1500 allora x2
		if (destinazioneUso.equals("2") && supUtilDouble <= 50) {
			importoObla = importoObla - (importoObla * new Double(0.3));
		} else if (destinazioneUso.equals("2") && supUtilDouble > 500
				&& supUtilDouble <= 1500) {
			importoObla = importoObla * new Double(1.5);
		} else if (destinazioneUso.equals("2") && supUtilDouble > 500
				&& supUtilDouble > 1500) {
			importoObla = importoObla * new Double(2.0);
		}
		// • - 1/3 attività sportiva – culturale –
		// * sanitaria - culto
		if (destinazioneUso.equals("4") || destinazioneUso.equals("8")
				|| destinazioneUso.equals("9") || destinazioneUso.equals("10")) {
			importoObla = importoObla - (importoObla * new Double(0.3));
		}

		// • - 1/3 se attività turistica e se <500 mq, ma se > 800
		// * mq allora x1.5
		if (destinazioneUso.equals("7") && supUtilDouble <= 500) {
			importoObla = importoObla - (importoObla * new Double(0.3));
		} else if (destinazioneUso.equals("7") && supUtilDouble > 800) {
			importoObla = importoObla * new Double(1.5);
		}
		// • - 1/3 se in zona agricola
		if (destinazioneUso.equals("5")) {
			importoObla = importoObla - (importoObla * new Double(0.3));
		}
	}

	private Double calcolaIL(Double dataInizioIM, Double dataOdierna,
			Double delta) {

		Double sommaimportoDeltaOblaCalcAut = new Double(delta);

		String dataInizioIMString = String.valueOf(dataInizioIM);
		String dataOdiernaString = String.valueOf(dataOdierna);

		dataInizioIMString = dataInizioIMString.replace(".", "");
		dataOdiernaString = dataOdiernaString.replace(".", "");

		Integer annoInizioIM = Integer.parseInt(dataInizioIMString.substring(0,
				4));
		Integer annoOdierna = Integer.parseInt(dataOdiernaString
				.substring(0, 4));

		long ggInteroAnno = 0;

		Double interessiAnno = new Double(0.0);

		Integer annoStart = annoInizioIM;
		Integer annoEnd = annoOdierna;
		for (int i = annoStart; annoStart < annoEnd; annoStart++) {

			String dataInizioAnno = annoStart + "1231";
			List<InteressiLegali> list = interessiLegaliHome
					.findByDtFine(new Double(dataInizioAnno));

			for (InteressiLegali interessiLegali : list) {
				ggInteroAnno = interessiLegali.getGiorni();
				interessiAnno = interessiLegali.getPercentuale();
			}
			sommaimportoDeltaOblaCalcAut = sommaimportoDeltaOblaCalcAut
					+ applicaPercentuale(delta, ggInteroAnno, ggInteroAnno,
							interessiAnno);
		}
		return sommaimportoDeltaOblaCalcAut;
	}

	private static Double applicaPercentuale(Double importoDeltaOblaCalcAut,
			long ggInteroAnno, long ggPrimoAnno, Double interessiAnno) {

		Double importoAnnoIntero = importoDeltaOblaCalcAut
				* (interessiAnno / 100);
		Double percAnnoFraz = (((new Double(ggPrimoAnno) * new Double(100)) / new Double(
				ggInteroAnno)) / new Double(100));
		if (percAnnoFraz.intValue() != 1) {
			return Converter.round((importoAnnoIntero * percAnnoFraz), 2);
		}
		return Converter.round(importoAnnoIntero, 2);
	}

	public void remove(String id) {
		datiVersamentiHome.remove(datiVersamentiHome.findById(BigInteger
				.valueOf(Integer.parseInt(id))));
	}
}
