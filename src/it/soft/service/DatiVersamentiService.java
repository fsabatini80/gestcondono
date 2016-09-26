package it.soft.service;

import it.soft.dao.DatiVersamentiHome;
import it.soft.dao.InteressiLegaliHome;
import it.soft.dao.OneriConcessoriHome;
import it.soft.dao.RiduzioneRedditoHome;
import it.soft.domain.DatiVersamento;
import it.soft.domain.InteressiLegali;
import it.soft.domain.OneriConcessori;
import it.soft.domain.RiduzioneReddito;
import it.soft.domain.TabCalcOblazione;
import it.soft.domain.TipologiaAbuso;
import it.soft.util.Constants;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiPraticaPojo;
import it.soft.web.pojo.DatiVersamentiPojo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

@Service
public class DatiVersamentiService {

    public static final Double PERC_ZONA_E = 0.85;
    public static final Double PERC_ZONA_B = 1.20;
    public static final Double PERC_ZONA_A = 1.30;

    @Autowired
    DatiVersamentiHome datiVersamentiHome;

    @Autowired
    DatiPraticaService datiPraticaService;

    @Autowired
    DatiAbusoService datiAbusoService;

    @Autowired
    InteressiLegaliHome interessiLegaliHome;

    @Autowired
    RiduzioneRedditoHome riduzioneRedditoHome;

    @Autowired
    OneriConcessoriHome oneriConcessoriHome;

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

	List<String> causali = new ArrayList<String>();
	causali.add(Constants.OBLAZIONE_COMUNE_CAUSALE_SEL);
	causali.add(Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL);
	return getVersamentiValidi(Constants.DATA_ZERO_VERSAMENTI, vers,
		causali);
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
	    // se la destinazione uso � diversa da RESIDENZIALE, gli importi in
	    // tabella vanno divisi per 2
	    if ("1".equals(leggeCondono) && !"1".equals(destinazioneUso)) {
		importoObla = importoObla / 2;
	    }
	}

	DatiAbusoPojo abusoDB = datiAbusoService.findById(idAbuso);
	Double supUtilDouble = new Double(0.0);
	Double supUtilNRDouble = new Double(0.0);
	if (abusoDB != null && abusoDB.getSuperficeUtile() != null
		&& !"".equals(abusoDB.getSuperficeUtile().trim())) {
	    String superUtileString = abusoDB.getSuperficeUtile();
	    supUtilDouble = new Double(superUtileString);
	    if (abusoDB.getNonresidenziale() != null
		    && !"".equals(abusoDB.getNonresidenziale().trim()))
		supUtilNRDouble = new Double(abusoDB.getNonresidenziale());
	}
	// legge n. 47/85
	if ("1".equals(leggeCondono)) {
	    return calcolaLegge1(importoObla, abusoDB, supUtilDouble);
	}
	// legge n. 724/94
	if ("2".equals(leggeCondono)) {

	    // tipo reddito 10 dipendente 20 non dipendente
	    String reddito = abusoDB.getReddito();
	    Double redditoDouble = new Double(0);
	    if (!org.apache.commons.lang.StringUtils.isEmpty(reddito)) {
		redditoDouble = new Double(reddito);
	    }
	    RiduzioneReddito riduzioneRedditoBean = riduzioneRedditoHome
		    .findByRedditoSuperfice(
			    Integer.valueOf(abusoDB.getTipoReddito()),
			    redditoDouble, supUtilDouble);

	    if (tipoAbuso == 4 || tipoAbuso == 5 || tipoAbuso == 6
		    || tipoAbuso == 7) {
		importoObla = calcolaRiduzioniLegge2(importoObla,
			supUtilDouble, "20".equals(abusoDB.getRiduzioni()),
			abusoDB.getLocalizzazione().getAbitazioneLusso(),
			abusoDB.getLocalizzazione()
				.getConvenzione_urbanistica(),
			abusoDB.getDestinazioneUso(), riduzioneRedditoBean,
			abusoDB.getLocalizzazione().getZonaUrbanizzazione());
		return importoObla;
	    }
	    importoObla = importoObla
		    * (supUtilDouble + (supUtilNRDouble * 0.6));
	    importoObla = calcolaRiduzioniLegge2(importoObla, supUtilDouble,
		    "10".equals(abusoDB.getRiduzioni()), abusoDB
			    .getLocalizzazione().getAbitazioneLusso(), abusoDB
			    .getLocalizzazione().getConvenzione_urbanistica(),
		    abusoDB.getDestinazioneUso(), riduzioneRedditoBean, abusoDB
			    .getLocalizzazione().getZonaUrbanizzazione());
	}

	return importoObla;
    }

    private Double calcolaLegge1(Double importoObla, DatiAbusoPojo abusoDB,
	    Double supUtilDouble) {
	importoObla = importoObla * supUtilDouble;
	importoObla = Converter.round(importoObla, 2);
	calcolaRiduzioniLegge1(importoObla, supUtilDouble, "10".equals(abusoDB
		.getRiduzioni()), abusoDB.getLocalizzazione()
		.getAbitazioneLusso(), abusoDB.getLocalizzazione()
		.getConvenzione_urbanistica(), abusoDB.getDestinazioneUso());

	System.out
		.println("oblazione calcolata per la legge 1: " + importoObla);
	return importoObla;
    }

    private Double calcolaRiduzioniLegge2(Double importoObla,
	    Double supUtilDouble, boolean primacasa, boolean abitazioneLusso,
	    boolean convenzioneUrbanistica, String destinazioneUso,
	    RiduzioneReddito riduzioneRedditoBean, String zonaUrbanizzazione) {

	calcolaRiduzioniLegge1(importoObla, supUtilDouble, primacasa,
		abitazioneLusso, convenzioneUrbanistica, destinazioneUso);

	// riduzione per reddito
	if (riduzioneRedditoBean != null) {
	    BigDecimal riduzioneRedditoPerc = riduzioneRedditoBean
		    .getRiduzione();
	    if (riduzioneRedditoPerc != null)
		importoObla = importoObla * riduzioneRedditoPerc.doubleValue()
			/ 100;
	}
	// riduzione/maggiorazione per zona urbanistica
	if (supUtilDouble <= 150) {
	    if ("ZONA E".equals(zonaUrbanizzazione)) {
		importoObla = importoObla * 0.85;
	    } else if ("ZONA B".equals(zonaUrbanizzazione)) {
		importoObla = importoObla * 1.20;
	    } else if ("ZONA A".equals(zonaUrbanizzazione)) {
		importoObla = importoObla * 1.30;
	    }
	}

	return importoObla;
    }

    public Double getOblazioneDovuta(Double oblazioneCalcolata,
	    DatiAbusoPojo abusoDB, Double dataAbuso, String leggeCondono) {

	Double im = new Double(0.0);
	Double oblazioneEIM = new Double(0.0);

	List<DatiVersamento> vers = datiVersamentiHome.findAll(
		BigInteger.valueOf(Integer.valueOf(abusoDB.getIdPratica())),
		Integer.valueOf(abusoDB.getProgressivo()));
	List<String> causali = new ArrayList<String>();
	causali.add(Constants.OBLAZIONE_COMUNE_CAUSALE_SEL);
	causali.add(Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL);
	Double importoVersValidi = getVersamentiValidi(dataAbuso, vers, causali);
	Double autoDetermina = new Double(0.0);
	// if
	// (!StringUtils.isEmptyOrWhitespaceOnly(abusoDB.getAutodeterminata()))
	// {
	if (abusoDB.getAutodeterminata() != null) {
	    autoDetermina = new Double(abusoDB.getAutodeterminata());
	}
	Double delta = autoDetermina - importoVersValidi;

	// calcolo interessi di mora
	if ("1".equals(leggeCondono)) {
	    if (delta > 0)
		im = calcolaInteressiMoraL1(Constants.DATA_ZERO_VERSAMENTI,
			new Date(), delta);
	    oblazioneEIM = oblazioneCalcolata + im;
	    System.out
		    .println("oblazione calcolata + interessi di mora per la legge 1: "
			    + oblazioneEIM);
	}
	Double t = oblazioneCalcolata - autoDetermina;
	if (t > 0 && importoVersValidi < autoDetermina) {

	    // se esiste prendo la data dell'ultimo versamento
	    Date dataConfronto = null;
	    Date dataUltimoVersamento = null;
	    for (DatiVersamento datiVersamento : vers) {
		String dataVersamento = datiVersamento.getDataVersamento();
		if (dataConfronto == null) {
		    dataConfronto = Converter.stringToDate(dataVersamento);
		    dataUltimoVersamento = Converter
			    .stringToDate(dataVersamento);
		}
		if (dataConfronto.before(dataUltimoVersamento)) {
		    dataUltimoVersamento = dataConfronto;
		}

	    }
	    Double dataInizioIL = null;
	    if (dataUltimoVersamento == null) {
		dataInizioIL = new Double(dataAbuso);
	    } else {
		dataInizioIL = new Double(Converter.dateToDouble(Converter
			.dateToString(dataUltimoVersamento)));
	    }

	    // calcolo interessi legali

	    Double il = calcolaIL(dataInizioIL, Converter.dateToDouble(
		    Converter.dateToString(new Date()), "yyyyMMdd"), t);
	    t = delta + il + im;
	    return new Double(t);
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
		return new Double(importoVersato);
	    }
	}
	System.out.println("oblazioneCalcolata: " + oblazioneCalcolata);
	System.out.println("importoVersato: " + importoVersato);
	System.out.println("oblazione dovuta: "
		+ Converter.round((im + (oblazioneCalcolata - importoVersato)),
			2));
	return Converter.round((im + (oblazioneCalcolata - importoVersato)), 2);

    }

    private Double calcolaInteressiMoraL1(Double dataInizioMora,
	    Date dataOdierna, Double delta) {

	Double interessi = new Double(0.0);
	Double oblaIM = delta * 3;
	String dataOdiernaString = Converter.dateToString(dataOdierna);
	interessi = oblaIM
		+ calcolaIL(dataInizioMora,
			Converter.dateToDouble(dataOdiernaString, "yyyyMMdd"),
			oblaIM);

	return interessi;
    }

    public Double getVersamentiValidi(Double dataAbuso,
	    List<DatiVersamento> vers, List<String> causali) {
	List<DatiVersamento> versamentiValidi = new ArrayList<DatiVersamento>();
	for (DatiVersamento versamento : vers) {
	    String dataVersa = versamento.getDataVersamento();
	    if (!StringUtils.isEmptyOrWhitespaceOnly(dataVersa)) {
		Double dv = Converter.dateToDouble(dataVersa);
		if (dv <= dataAbuso) {
		    versamentiValidi.add(versamento);
		}
	    }
	}

	Double answer = new Double(0.0);

	for (String causale : causali) {
	    for (DatiVersamento datiVersamento : versamentiValidi) {
		if (causale.equals(datiVersamento.getCausale()))
		    if (datiVersamento.getImporto() != null) {
			answer = Math.abs(answer + datiVersamento.getImporto());
		    } else if (datiVersamento.getImportoEuro() != null) {
			answer = Math.abs(answer
				+ datiVersamento.getImportoEuro());
		    } else if (datiVersamento.getImportoLire() != null) {
			answer = Math.abs(answer
				+ Converter.convertLireEuro(datiVersamento
					.getImportoLire()));
		    } else {
			answer = Math.abs(new Double(0.0));
		    }
	    }
	}

	return answer;
    }

    /**
     * @param importoObla
     */
    private void calcolaRiduzioniLegge1(Double importoObla,
	    Double supUtilDouble, boolean primacasa, boolean abitazioneLusso,
	    boolean convenzioneUrbanistica, String destinazioneUso) {
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
	// Se abuso � prima casa e residente o
	// non ancora abitabile allora oblazione = -1/3 (NO abitazioni di
	// lusso,
	// cat. A/1). Agevolazione valida fino a 150 mq sup. complessiva
	if (supUtilDouble <= 150 && ((primacasa)) && !abitazioneLusso) {
	    importoObla = importoObla - (importoObla * new Double(0.3));
	}
	// Se esiste
	// una convenzione urbanistica o atto d�obbligo sull�abuso -50%
	if (convenzioneUrbanistica) {
	    importoObla = importoObla / 2;
	}

	// � - 1/3 costruzione industriale o artigianale fino a 3000 mq, ma se >
	// 6000
	// mq allora x 1.5
	if (destinazioneUso.equals("3") && supUtilDouble <= 3000) {
	    importoObla = importoObla - (importoObla * new Double(0.3));
	} else if (destinazioneUso.equals("3") && supUtilDouble > 6000) {
	    importoObla = importoObla * new Double(1.5);
	}

	// � - 1/3 attivit� commerciale < 50 mq, ma se >500mq allora x*
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
	// � - 1/3 attivit� sportiva � culturale �
	// * sanitaria - culto
	if (destinazioneUso.equals("4") || destinazioneUso.equals("8")
		|| destinazioneUso.equals("9") || destinazioneUso.equals("10")) {
	    importoObla = importoObla - (importoObla * new Double(0.3));
	}

	// � - 1/3 se attivit� turistica e se <500 mq, ma se > 800
	// * mq allora x1.5
	if (destinazioneUso.equals("7") && supUtilDouble <= 500) {
	    importoObla = importoObla - (importoObla * new Double(0.3));
	} else if (destinazioneUso.equals("7") && supUtilDouble > 800) {
	    importoObla = importoObla * new Double(1.5);
	}
	// � - 1/3 se in zona agricola
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
	for (annoStart.intValue(); annoStart < annoEnd; annoStart++) {

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

    public Double getOneriVersati(String idpratica, String progressivo) {
	List<DatiVersamento> vers = datiVersamentiHome.findAll(
		BigInteger.valueOf(Integer.valueOf(idpratica)),
		Integer.valueOf(progressivo));

	List<String> causali = new ArrayList<String>();
	causali.add(Constants.ONERI_CAUSALE_SEL);

	return getVersamentiValidi(Constants.DATA_ZERO_VERSAMENTI, vers,
		causali);
    }

    public Double getOneriCalcolati(TipologiaAbuso tipologiaAbuso,
	    DatiAbusoPojo abusoDB, DatiPraticaPojo praticaDB, String idabuso,
	    String destinazioneUso) {
	// FIXME
	Double answer = new Double(0);
	// calcolo oneri 47/85
	if (Constants.ID_LEGGE_47_85.equals(praticaDB.getLeggeCondono())) {
	    if (Constants.DEST_USO_RESIDENZIALE.equals(destinazioneUso)
		    || !(Constants.DEST_USO_TURISTICO.equals(destinazioneUso)
			    && Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE
				    .equals(destinazioneUso) && Constants.DEST_USO_AGRICOLO
				.equals(destinazioneUso))) {
		return calcolaOneriLegge1Residenziale(abusoDB);
	    }
	    if (Constants.DEST_USO_TURISTICO.equals(destinazioneUso)) {
		return calcoloOneriDaTab(abusoDB, answer)
			+ calcolaCostiCostruzione();
	    }
	    if (Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE
		    .equals(destinazioneUso)) {
		return calcoloOneriDaTab(abusoDB, answer);
	    }

	}

	return answer;
    }

    private Double calcolaOneriLegge1Residenziale(DatiAbusoPojo abusoDB) {
	// FIXME
	Double answer = new Double(0);
	String idepocaabuso = abusoDB.getEpocaAbuso();
	/**
	 * - ABUSI 1� PERIODO NON SONO PREVISTI ONERI
	 */
	if (Constants.PERIODO_1_47_85.equals(idepocaabuso)) {
	    return answer;
	}
	/**
	 * - ABUSI 2� PERIODO SONO PREVISTI GLI ONERI DI URBANIZZAZIONE PRIMARIA
	 * E SECONDARIA E SI CALCOLANO MOLTIPLICANDO LA SOMMATORIA DEI MC UTILI
	 * ED ACCESSORI PER UN PARAMETRO DA TABELLA (ALLEGATO 2), CHE SI DIVIDE
	 * PER ZONA URBANISTICA ED INTERVENTO E NON CI SONO INTERESSI
	 */
	if (Constants.PERIODO_2_47_85.equals(idepocaabuso)) {
	    return calcoloOneriDaTab(abusoDB, answer);
	}
	/**
	 * - ABUSI 3� PERIODO SONO PREVISTI GLI ONERI COME SOPRA, PIU' IL COSTO
	 * DI COSTRUZIONE DELLE OPERE OGGETTO DELLA RICHIESTA CALCOLATI COME DA
	 * ALLEGATO 3
	 */
	if (Constants.PERIODO_3_47_85.equals(idepocaabuso)) {
	    calcoloOneriDaTab(abusoDB, answer);
	    calcolaCostiCostruzione();
	    return answer;
	}
	return answer;
    }

    private Double calcoloOneriDaTab(DatiAbusoPojo abusoDB, Double answer) {
	Double mcUtili = new Double(0);
	Double mcAccessori = new Double(0);
	if (abusoDB.getVolumeUtile() != null)
	    mcUtili = Double.valueOf(abusoDB.getVolumeUtile());
	if (abusoDB.getNonresidenzialeVuoto() != null)
	    mcAccessori = Double.valueOf(abusoDB.getNonresidenzialeVuoto());

	List<OneriConcessori> oneriTab = oneriConcessoriHome.findBy(abusoDB
		.getLocalizzazione().getZonaUrbanizzazione(), abusoDB
		.getDestinazioneUso());
	for (OneriConcessori oneriConcessori : oneriTab) {
	    Double parametroTab = oneriConcessori.getCosto().doubleValue();
	    answer = (mcUtili + mcAccessori) * parametroTab;
	}
	return answer;
    }

    /**
     * DECRETO MINISTERIALE 31 MAGGIO 1977 Art. 5 - Incremento relativo alla
     * superficie utile abitabile (i1) L'incremento percentuale in funzione
     * della superficie � stabilito in rapporto alle seguenti classi di
     * superficie utile abitabile: 1) oltre 95 metri quadrati e fino a 110 metri
     * quadrati inclusi: 5%; 2) oltre 110 metri quadrati e fino a 130 metri
     * quadrati inclusi: 15%; 3) oltre 130 metri quadrati e fino a 160 metri
     * quadrati inclusi: 30%; 4) oltre 160 metri quadrati: 50%. Per ciascun
     * fabbricato l'incremento percentuale relativo alla superficie utile
     * abitabile, � dato dalla somma dei valori ottenuti moltiplicando gli
     * incrementi percentuali di cui al precedente comma per i rapporti tra la
     * superficie utile abitabile degli alloggi compresi nelle rispettive classi
     * e la superficie utile abitabile dell'intero edificio.
     */
    private Double calcolaCostiCostruzione(Double superficeUtile) {
	// TODO Auto-generated method stub
	Double answer = new Double(0);
	if (superficeUtile > 95 && superficeUtile <= 110) {

	} else if (superficeUtile > 110 && superficeUtile <= 130) {

	} else if (superficeUtile > 130 && superficeUtile <= 160) {

	} else if (superficeUtile > 160) {

	} else {
	    return answer;
	}

    }
}
