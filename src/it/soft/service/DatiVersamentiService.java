package it.soft.service;

import it.soft.dao.DatiAlloggioHome;
import it.soft.dao.DatiVersamentiHome;
import it.soft.dao.InteressiLegaliHome;
import it.soft.dao.OneriConcessoriHome;
import it.soft.dao.RiduzioneRedditoHome;
import it.soft.domain.DatiAlloggio;
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
import java.util.GregorianCalendar;
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

    @Autowired
    RiduzioneRedditoHome riduzioneRedditoHome;

    @Autowired
    OneriConcessoriHome oneriConcessoriHome;

    @Autowired
    DatiAlloggioHome datiAlloggioHome;

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
	return getVersamentiValidi(vers, causali);
    }

    public Double getImportoVersatoOblazioneRegione(String idPratica,
	    String progressivo) {

	List<DatiVersamento> vers = datiVersamentiHome.findAll(
		BigInteger.valueOf(Integer.valueOf(idPratica)),
		Integer.valueOf(progressivo));

	List<String> causali = new ArrayList<String>();
	causali.add(Constants.OBLAZIONE_REGIONE_CAUSALE_SEL);
	return getVersamentiValidi(vers, causali);
    }

    public Double getAutodeterminaOblazione(String idAbuso,
	    String progressivoAbuso) {
	DatiAbusoPojo abuso = datiAbusoService.findById(idAbuso,
		progressivoAbuso);
	return new Double(abuso.getAutodeterminata() != null ? new Double(
		abuso.getAutodeterminata()) : new Double(0.0));
    }

    public Double getAutodeterminaRegione(String idAbuso,
	    String progressivoAbuso) {
	DatiAbusoPojo abuso = datiAbusoService.findById(idAbuso,
		progressivoAbuso);
	return new Double(
		abuso.getAutodeterminataRegione() != null ? new Double(
			abuso.getAutodeterminataRegione()) : new Double(0.0));
    }

    public Double getOblazioneCalcolata(boolean applicaDefault,
	    TipologiaAbuso tipologiaAbuso, Double dataAbuso,
	    String leggeCondono, String idAbuso, String destinazioneUso) {

	Integer tipoAbuso = tipologiaAbuso.getDescrizioneBreve();
	Double importoObla = new Double(0.0);
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

	// legge n. 326/03
	if (Constants.ID_LEGGE_326_03.equals(leggeCondono)) {
	    List<TabCalcOblazione> tabOblList = datiVersamentiHome
		    .findOblazione(abusoDB.getDestinazioneUso(), leggeCondono,
			    tipoAbuso);
	    if (tabOblList != null && !tabOblList.isEmpty()) {
		for (TabCalcOblazione tabCalcOblazione : tabOblList) {
		    importoObla = Double.valueOf(tabCalcOblazione
			    .getImportoOblazione());
		}
	    }
	    importoObla = calcolaLegge3(tipoAbuso, importoObla, supUtilDouble,
		    supUtilNRDouble);
	    importoObla = importoObla / 11 * 10;
	    return Converter.round(importoObla, 2);
	}

	List<TabCalcOblazione> tabOblList = datiVersamentiHome.findOblazione(
		dataAbuso, leggeCondono, tipoAbuso);
	List<TabCalcOblazione> tabOblListDefault;
	Double importoOblaDefault = new Double(0.0);
	TabCalcOblazione calcOblazioneDefaul = null;
	if (applicaDefault) {
	    tabOblListDefault = datiVersamentiHome.findOblazione(dataAbuso,
		    leggeCondono, 7);
	    for (TabCalcOblazione element : tabOblListDefault) {
		calcOblazioneDefaul = element;
		importoOblaDefault = Converter.convertLireEuro(new Double(
			calcOblazioneDefaul.getImportoOblazione()));
	    }
	}

	TabCalcOblazione calcOblazione = null;
	for (TabCalcOblazione tabCalcOblazione : tabOblList) {
	    calcOblazione = tabCalcOblazione;
	}

	if (calcOblazione != null
		&& (Constants.ID_LEGGE_47_85.equals(leggeCondono) || Constants.ID_LEGGE_724_94
			.equals(leggeCondono))) {
	    importoObla = Converter.convertLireEuro(new Double(calcOblazione
		    .getImportoOblazione()));
	    // se la destinazione uso è diversa da RESIDENZIALE, gli importi in
	    // tabella vanno divisi per 2
	    if (Constants.ID_LEGGE_47_85.equals(leggeCondono)
		    && !Constants.DEST_USO_RESIDENZIALE.equals(destinazioneUso)) {
		importoObla = importoObla / 2;
	    }
	}

	// legge n. 47/85
	if (Constants.ID_LEGGE_47_85.equals(leggeCondono)) {
	    importoObla = calcolaLegge1(tipoAbuso, importoObla, abusoDB,
		    supUtilDouble + (supUtilNRDouble * 0.6));
	    if (importoObla < importoOblaDefault) {
		importoObla = importoOblaDefault;
	    }
	    return Converter.round(importoObla, 2);
	}
	// legge n. 724/94
	if (Constants.ID_LEGGE_724_94.equals(leggeCondono)) {
	    importoObla = calcolaLegge2(tipoAbuso, importoObla, abusoDB,
		    supUtilDouble, supUtilNRDouble);
	    if (importoObla < importoOblaDefault) {
		importoObla = importoOblaDefault;
	    }
	}

	return Converter.round(importoObla, 2);
    }

    private Double calcolaLegge3(Integer tipoAbuso, Double importoObla,
	    Double supUtilDouble, Double supUtilNRDouble) {

	if (tipoAbuso == 4 || tipoAbuso == 5 || tipoAbuso == 6) {
	    return importoObla;
	}
	importoObla = importoObla * (supUtilDouble + (supUtilNRDouble * 0.6));
	return importoObla;
    }

    private Double calcolaLegge2(Integer tipoAbuso, Double importoObla,
	    DatiAbusoPojo abusoDB, Double supUtilDouble, Double supUtilNRDouble) {
	// tipo reddito 10 dipendente 20 non dipendente
	String reddito = abusoDB.getReddito();
	Double redditoDouble = Double.valueOf(0);
	if (!org.apache.commons.lang.StringUtils.isEmpty(reddito)) {
	    redditoDouble = new Double(reddito);
	}
	RiduzioneReddito riduzioneRedditoBean = riduzioneRedditoHome
		.findByRedditoSuperfice(
			Integer.valueOf(abusoDB.getTipoReddito()),
			redditoDouble);

	if (tipoAbuso == 4 || tipoAbuso == 5 || tipoAbuso == 6
		|| tipoAbuso == 7) {
	    // importoObla = calcolaRiduzioniLegge2(importoObla, supUtilDouble,
	    // abusoDB.getLocalizzazione().isIsprimaCasa(), abusoDB
	    // .getLocalizzazione().getAbitazioneLusso(), abusoDB
	    // .getLocalizzazione().getConvenzione_urbanistica(),
	    // abusoDB.getDestinazioneUso(), riduzioneRedditoBean, abusoDB
	    // .getLocalizzazione().getZonaUrbanizzazione(),
	    // abusoDB.getLocalizzazione().isIscrizioneCamera());
	    return importoObla;
	} else {
	    importoObla = importoObla
		    * (supUtilDouble + (supUtilNRDouble * 0.6));
	    importoObla = calcolaRiduzioniLegge2(importoObla, supUtilDouble,
		    abusoDB.getLocalizzazione().isIsprimaCasa(), abusoDB
			    .getLocalizzazione().getAbitazioneLusso(), abusoDB
			    .getLocalizzazione().getConvenzione_urbanistica(),
		    abusoDB.getDestinazioneUso(), riduzioneRedditoBean, abusoDB
			    .getLocalizzazione().getZonaUrbanizzazione(),
		    abusoDB.getLocalizzazione().isIscrizioneCamera());
	}
	return importoObla;
    }

    private Double calcolaLegge1(Integer tipoAbuso, Double importoObla,
	    DatiAbusoPojo abusoDB, Double supUtilDouble) {

	if (tipoAbuso == 7) {
	    return importoObla;
	}
	importoObla = importoObla * supUtilDouble;
	importoObla = Converter.round(importoObla, 2);
	System.out
		.println("oblazione al netto delle riduzioni/maggiorazioni = "
			+ importoObla.doubleValue());
	importoObla = calcolaRiduzioniLegge1(importoObla, supUtilDouble,
		abusoDB.getLocalizzazione().isIsprimaCasa(), abusoDB
			.getLocalizzazione().getAbitazioneLusso(), abusoDB
			.getLocalizzazione().getConvenzione_urbanistica(),
		abusoDB.getDestinazioneUso(), abusoDB.getLocalizzazione()
			.isIscrizioneCamera());

	System.out
		.println("oblazione calcolata per la legge 1: " + importoObla);
	return importoObla;
    }

    private Double calcolaRiduzioniLegge2(Double importoObla,
	    Double supUtilDouble, boolean primacasa, boolean abitazioneLusso,
	    boolean convenzioneUrbanistica, String destinazioneUso,
	    RiduzioneReddito riduzioneRedditoBean, String zonaUrbanizzazione,
	    boolean iscrizioneCamera) {

	// importoObla = calcolaRiduzioniLegge2(importoObla, supUtilDouble,
	// primacasa, abitazioneLusso, convenzioneUrbanistica,
	// destinazioneUso, iscrizioneCamera);

	// RIMANGONO LE STESSE RIDUZIONI/MAGGIORAZIONI DELLA 47.85
	importoObla = calcolaRiduzioniLegge1(importoObla, supUtilDouble,
		primacasa, abitazioneLusso, convenzioneUrbanistica,
		destinazioneUso, iscrizioneCamera);

	// riduzione per reddito
	if (riduzioneRedditoBean != null) {
	    BigDecimal riduzioneRedditoPerc = riduzioneRedditoBean
		    .getRiduzione();
	    if (riduzioneRedditoPerc != null) {
		if (supUtilDouble <= 150) {
		    importoObla = importoObla
			    - (importoObla * riduzioneRedditoPerc.doubleValue() / 100);
		} else {
		    Double costoUnitarioOblaz = importoObla / supUtilDouble;
		    Double partePrezzoPieno = costoUnitarioOblaz
			    * (supUtilDouble - 150);
		    Double parteSconto = (costoUnitarioOblaz * 150);
		    importoObla = partePrezzoPieno
			    + (parteSconto - (parteSconto
				    * riduzioneRedditoPerc.doubleValue() / 100));
		}
	    }
	}
	// riduzione/maggiorazione per zona urbanistica
	if (Constants.ZONA_E.equals(zonaUrbanizzazione)) {
	    if (supUtilDouble <= 150) {
		importoObla = importoObla * Constants.PERC_ZONA_E;
	    } else {
		Double costoUnitarioOblaz = importoObla / supUtilDouble;
		Double partePrezzoPieno = costoUnitarioOblaz
			* (supUtilDouble - 150);
		Double parteSconto = (costoUnitarioOblaz * 150);
		importoObla = partePrezzoPieno
			+ (parteSconto * Constants.PERC_ZONA_E);
	    }
	} else if (Constants.ZONA_B.equals(zonaUrbanizzazione)) {
	    if (supUtilDouble <= 150) {
		importoObla = importoObla * Constants.PERC_ZONA_B;
	    } else {
		Double costoUnitarioOblaz = importoObla / supUtilDouble;
		Double partePrezzoPieno = costoUnitarioOblaz
			* (supUtilDouble - 150);
		Double parteSconto = (costoUnitarioOblaz * 150);
		importoObla = partePrezzoPieno
			+ (parteSconto * Constants.PERC_ZONA_B);
	    }
	} else if (Constants.ZONA_A.equals(zonaUrbanizzazione)) {
	    if (supUtilDouble <= 150) {
		importoObla = importoObla * Constants.PERC_ZONA_A;
	    } else {
		Double costoUnitarioOblaz = importoObla / supUtilDouble;
		Double partePrezzoPieno = costoUnitarioOblaz
			* (supUtilDouble - 150);
		Double parteSconto = (costoUnitarioOblaz * 150);
		importoObla = partePrezzoPieno
			+ (parteSconto * Constants.PERC_ZONA_A);
	    }
	}

	return importoObla;
    }

    public Double getOblazioneDovuta(Double oblazioneCalcolata,
	    DatiAbusoPojo abusoDB, Double dataAbuso, String leggeCondono) {

	List<DatiVersamento> vers = datiVersamentiHome.findAll(
		BigInteger.valueOf(Integer.valueOf(abusoDB.getIdPratica())),
		Integer.valueOf(abusoDB.getProgressivo()));
	List<String> causali = new ArrayList<String>();
	causali.add(Constants.OBLAZIONE_COMUNE_CAUSALE_SEL);
	causali.add(Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL);
	Double importoVersValidi = getImportoVersatoOblazione(
		abusoDB.getIdPratica(), abusoDB.getProgressivo());
	Double autoDetermina = new Double(0.0);
	if (abusoDB.getAutodeterminata() != null) {
	    autoDetermina = new Double(abusoDB.getAutodeterminata());
	}

	if (Constants.ID_LEGGE_47_85.equals(leggeCondono)) {
	    return getOblazione47_85(importoVersValidi, autoDetermina,
		    oblazioneCalcolata, abusoDB, dataAbuso, leggeCondono, vers);
	} else if (Constants.ID_LEGGE_724_94.equals(leggeCondono)) {
	    return getOblazione724_94(importoVersValidi, autoDetermina,
		    oblazioneCalcolata, abusoDB, dataAbuso, leggeCondono, vers);
	} else if (Constants.ID_LEGGE_326_03.equals(leggeCondono)) {
	    return getOblazione326_03(importoVersValidi, autoDetermina,
		    oblazioneCalcolata, abusoDB, dataAbuso, leggeCondono, vers);
	}

	return 0.0;

    }

    private Double getOblazione47_85(Double importoVersValidi,
	    Double autoDetermina, Double oblazioneCalcolata,
	    DatiAbusoPojo abusoDB, Double dataAbuso, String leggeCondono,
	    List<DatiVersamento> vers) {
	Double im = new Double(0.0);
	Double oblazioneEIM = new Double(0.0);
	Date dtOdierna = new Date();
	List<String> causali = new ArrayList<String>();
	causali.add(Constants.OBLAZIONE_COMUNE_CAUSALE_SEL);
	causali.add(Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL);
	Double delta = autoDetermina - importoVersValidi;
	/*
	 * versato<calcolato<autodeterminata calcoliamo gli interessi di mora
	 * sulla differenza tra calcolato e versato .
	 */
	if (importoVersValidi < oblazioneCalcolata
		&& oblazioneCalcolata < autoDetermina) {
	    delta = oblazioneCalcolata - importoVersValidi;
	}
	/*
	 * versato>calcolato non calcoliamo gli interessi di mora
	 */
	if (importoVersValidi > oblazioneCalcolata) {
	    delta = 0.0;
	    System.out
		    .println("versato>calcolato non calcoliamo gli interessi di mora ");
	}

	// calcolo interessi di mora
	if (delta > 0) {
	    im = calcolaInteressiMoraL1(delta);
	    oblazioneEIM = oblazioneCalcolata + im;
	    System.out
		    .println("oblazione calcolata + interessi di mora per la legge 1: "
			    + oblazioneEIM);
	}
	// cambiato in data 08-02-2017 a seguito chat con Luigi
	Double t = oblazioneCalcolata - autoDetermina;
	if (importoVersValidi > autoDetermina) {
	    t = oblazioneCalcolata - importoVersValidi;
	}
	// rimosso in data 21-02-2017 a seguito chat con Luigi
	// if (importoVersValidi < autoDetermina
	// && autoDetermina <= oblazioneCalcolata) {
	// t = oblazioneCalcolata - importoVersValidi;
	// }
	// entro solo se il calcolato maggiore del autoderminata
	if (t > 0) {
	    Double dataInizioIL = getDataUltimoVersamento(dataAbuso, vers,
		    causali);
	    // calcolo interessi legali
	    String dtOdiernastr = Converter.dateToString(dtOdierna);
	    Double dtOdiernadbl = Converter.dateToDouble(dtOdiernastr,
		    "dd-MM-yyyy");
	    Double il = calcolaIL(dataInizioIL, dtOdiernadbl, t);
	    System.out.println("interessi legali: " + il);
	    t = oblazioneCalcolata - importoVersValidi + il + im;
	    return new Double(t);
	}

	if (importoVersValidi > autoDetermina) {
	    Double b = oblazioneCalcolata - importoVersValidi;
	    if (im + b < 0) {
		return new Double(0.0);
	    }
	}
	Double oblazioneDovuta = im + (oblazioneCalcolata - importoVersValidi);
	System.out.println("oblazioneCalcolata: " + oblazioneCalcolata);
	System.out.println("importoVersato: " + importoVersValidi);
	System.out.println("oblazione dovuta: "
		+ Converter.round(oblazioneDovuta, 2));
	return oblazioneDovuta > 0 ? oblazioneDovuta : 0;

    }

    private Double getOblazione724_94(Double importoVersValidi,
	    Double autoDetermina, Double oblazioneCalcolata,
	    DatiAbusoPojo abusoDB, Double dataAbuso, String leggeCondono,
	    List<DatiVersamento> vers) {
	Date dtOdierna = new Date();
	List<String> causali = new ArrayList<String>();
	causali.add(Constants.OBLAZIONE_COMUNE_CAUSALE_SEL);
	causali.add(Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL);
	// cambiato in data 08-02-2017 a seguito chat con Luigi
	Double t = oblazioneCalcolata - autoDetermina;
	if (importoVersValidi > autoDetermina) {
	    t = oblazioneCalcolata - importoVersValidi;
	}
	if (importoVersValidi < autoDetermina
		&& autoDetermina <= oblazioneCalcolata) {
	    t = oblazioneCalcolata - importoVersValidi;
	}
	if (importoVersValidi < oblazioneCalcolata
		&& oblazioneCalcolata < autoDetermina) {
	    t = oblazioneCalcolata - importoVersValidi;
	}
	// entro solo se il calcolato maggiore del autoderminata
	if (t > 0) {// && importoVersValidi < autoDetermina
	    Double dataInizioIL = getDataUltimoVersamento(dataAbuso, vers,
		    causali);
	    // calcolo interessi legali
	    String dtOdiernastr = Converter.dateToString(dtOdierna);
	    Double dtOdiernadbl = Converter.dateToDouble(dtOdiernastr,
		    "dd-MM-yyyy");
	    Double il = calcolaIL(dataInizioIL, dtOdiernadbl, t);
	    System.out.println("interessi legali: " + il);
	    t = t + il;
	    return new Double(t);
	}
	Double oblazioneDovuta = oblazioneCalcolata - importoVersValidi;
	System.out.println("oblazioneCalcolata: " + oblazioneCalcolata);
	System.out.println("importoVersato: " + importoVersValidi);
	System.out.println("oblazione dovuta: "
		+ Converter.round(oblazioneDovuta, 2));
	return oblazioneDovuta > 0 ? oblazioneDovuta : 0;
    }

    private Double getOblazione326_03(Double importoVersValidi,
	    Double autoDetermina, Double oblazioneCalcolata,
	    DatiAbusoPojo abusoDB, Double dataAbuso, String leggeCondono,
	    List<DatiVersamento> vers) {
	Date dtOdierna = new Date();
	List<String> causali = new ArrayList<String>();
	causali.add(Constants.OBLAZIONE_COMUNE_CAUSALE_SEL);
	causali.add(Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL);
	// cambiato in data 08-02-2017 a seguito chat con Luigi
	Double t = oblazioneCalcolata - autoDetermina;
	if (importoVersValidi > autoDetermina) {
	    t = oblazioneCalcolata - importoVersValidi;
	}
	if (importoVersValidi < autoDetermina
		&& autoDetermina <= oblazioneCalcolata) {
	    t = oblazioneCalcolata - importoVersValidi;
	}
	if (importoVersValidi < oblazioneCalcolata
		&& oblazioneCalcolata < autoDetermina) {
	    t = oblazioneCalcolata - importoVersValidi;
	}
	// entro solo se il calcolato maggiore del autoderminata
	if (t > 0) {// && importoVersValidi < autoDetermina
	    Double dataInizioIL = getDataUltimoVersamento(dataAbuso, vers,
		    causali);
	    // calcolo interessi legali
	    String dtOdiernastr = Converter.dateToString(dtOdierna);
	    Double dtOdiernadbl = Converter.dateToDouble(dtOdiernastr,
		    "dd-MM-yyyy");
	    Double il = calcolaIL(dataInizioIL, dtOdiernadbl, t);
	    System.out.println("interessi legali: " + il);
	    t = t + il;
	    return new Double(t);
	}
	Double oblazioneDovuta = oblazioneCalcolata - importoVersValidi;
	System.out.println("oblazioneCalcolata: " + oblazioneCalcolata);
	System.out.println("importoVersato: " + importoVersValidi);
	System.out.println("oblazione dovuta: "
		+ Converter.round(oblazioneDovuta, 2));
	return oblazioneDovuta > 0 ? oblazioneDovuta : 0;
    }

    public Double getOblazioneSaldo(Double importoVersValidi,
	    Double autoDetermina, Double oblazioneCalcolata, Double dataAbuso,
	    BigInteger idPratica, Integer progressivo) {
	Date dtOdierna = new Date();
	List<DatiVersamento> vers = datiVersamentiHome.findAll(idPratica,
		progressivo);
	List<String> causali = new ArrayList<String>();
	causali.add(Constants.OBLAZIONE_COMUNE_CAUSALE_SEL);
	causali.add(Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL);
	// cambiato in data 08-02-2017 a seguito chat con Luigi
	Double t = oblazioneCalcolata - autoDetermina;
	if (importoVersValidi > autoDetermina) {
	    t = oblazioneCalcolata - importoVersValidi;
	}
	if (importoVersValidi < autoDetermina
		&& autoDetermina <= oblazioneCalcolata) {
	    t = oblazioneCalcolata - importoVersValidi;
	}
	if (importoVersValidi < oblazioneCalcolata
		&& oblazioneCalcolata < autoDetermina) {
	    t = oblazioneCalcolata - importoVersValidi;
	}
	// entro solo se il calcolato maggiore del autoderminata
	if (t > 0) {// && importoVersValidi < autoDetermina
	    Double dataInizioIL = getDataUltimoVersamento(dataAbuso, vers,
		    causali);
	    // calcolo interessi legali
	    String dtOdiernastr = Converter.dateToString(dtOdierna);
	    Double dtOdiernadbl = Converter.dateToDouble(dtOdiernastr,
		    "dd-MM-yyyy");
	    Double il = calcolaIL(dataInizioIL, dtOdiernadbl, t);
	    System.out.println("interessi legali: " + il);
	    t = t + il;
	    return new Double(t);
	}
	Double oblazioneDovuta = oblazioneCalcolata - importoVersValidi;
	System.out.println("oblazioneCalcolata: " + oblazioneCalcolata);
	System.out.println("importoVersato: " + importoVersValidi);
	System.out.println("oblazione dovuta: "
		+ Converter.round(oblazioneDovuta, 2));
	return oblazioneDovuta > 0 ? oblazioneDovuta : 0;
    }

    private Double getDataUltimoVersamento(Double dataAbuso,
	    List<DatiVersamento> vers, List<String> causali) {
	// se esiste prendo la data dell'ultimo versamento
	Date dataConfronto = null;
	Date dataUltimoVersamento = null;
	for (DatiVersamento datiVersamento : vers) {
	    if (causali.contains(datiVersamento.getCausale())) {
		String dataVersamento = datiVersamento.getDataVersamento();
		if (dataConfronto == null) {
		    dataConfronto = Converter.stringToDate(dataVersamento);
		}
		dataUltimoVersamento = Converter.stringToDate(dataVersamento);
		if (dataConfronto.before(dataUltimoVersamento)) {
		    dataConfronto = dataUltimoVersamento;
		}
	    }
	}
	Double dataInizioIL = null;
	if (dataUltimoVersamento == null) {
	    dataInizioIL = new Double(dataAbuso);
	} else {
	    dataInizioIL = new Double(Converter.dateToDouble(Converter
		    .dateToString(dataConfronto)));
	}
	return dataInizioIL;
    }

    private Double calcolaInteressiMoraL1(Double delta) {
	Double interessi = new Double(0.0);
	interessi = delta * 3;
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
	answer = filtraPerCausali(causali, versamentiValidi, answer);
	return answer;
    }

    public Double getVersamentiValidiPerAnno(Integer anno,
	    List<DatiVersamento> vers, List<String> causali) {
	List<DatiVersamento> versamentiValidi = new ArrayList<DatiVersamento>();
	for (DatiVersamento versamento : vers) {
	    String dataVersa = versamento.getDataVersamento();
	    if (!StringUtils.isEmptyOrWhitespaceOnly(dataVersa)) {
		Integer annodv = getAnnoFromDataDouble(Converter
			.dateToDouble(dataVersa));
		if (annodv.intValue() == anno.intValue()) {
		    versamentiValidi.add(versamento);
		}
	    }
	}
	Double answer = new Double(0.0);
	answer = filtraPerCausali(causali, versamentiValidi, answer);
	return answer;
    }

    private Double filtraPerCausali(List<String> causali,
	    List<DatiVersamento> versamentiValidi, Double answer) {
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

    public Double getVersamentiValidi(List<DatiVersamento> vers,
	    List<String> causali) {
	Double answer = new Double(0.0);
	answer = filtraPerCausali(causali, vers, answer);
	return answer;
    }

    /**
     * @param importoObla
     */
    private Double calcolaRiduzioniLegge1(Double importoObla,
	    Double supUtilDouble, boolean primacasa, boolean abitazioneLusso,
	    boolean convenzioneUrbanistica, String destinazioneUso,
	    boolean iscrizioneCamera) {

	if (Constants.DEST_USO_RESIDENZIALE.equals(destinazioneUso)) {
	    // Se 400< abuso mq< 800 allora L. x 1.2 Se 800< abuso mq< 1200
	    // allora
	    // L. x
	    // 2 Se abuso mq > 1200 allora L. x 3
	    if (supUtilDouble > 400 && supUtilDouble <= 800) {
		importoObla = importoObla * new Double(1.2);
	    } else if (supUtilDouble > 800 && supUtilDouble <= 1200) {
		importoObla = importoObla * new Double(2);
	    } else if (supUtilDouble > 1200) {
		importoObla = importoObla * new Double(3);
	    }

	    // Se esiste
	    // una convenzione urbanistica o atto d’obbligo sull’abuso -50%
	    if (convenzioneUrbanistica) {
		importoObla = importoObla / 2;
	    }

	    // Se abuso è prima casa e residente o
	    // non ancora abitabile allora oblazione = -1/3 (NO abitazioni di
	    // lusso,
	    // cat. A/1). Agevolazione valida fino a 150 mq sup. complessiva
	    if (primacasa && !abitazioneLusso) {
		if (supUtilDouble <= 150) {
		    importoObla = (importoObla / 3) * 2;
		    System.out
			    .println("applico sconto prima casa = (importoObla / 3) * 2 =  "
				    + importoObla.doubleValue());
		} else {
		    Double costoUnitarioOblaz = importoObla / supUtilDouble;
		    Double partePrezzoPieno = costoUnitarioOblaz
			    * (supUtilDouble - 150);
		    Double parteScontoPrimaCasa = (costoUnitarioOblaz * 150);
		    importoObla = partePrezzoPieno
			    + ((parteScontoPrimaCasa / 3) * 2);
		    System.out
			    .println("applico sconto prima casa sui primi 150 mq = partePrezzoPieno  + ((parteScontoPrimaCasa / 3) * 2) = "
				    + importoObla.doubleValue());
		}
	    }
	}

	// INSERIMENTO SCONTO Iscrizione camera di commercio
	// DESTINAZIONE NON RESIDENZIALE 0.5
	if (!Constants.DEST_USO_RESIDENZIALE.equals(destinazioneUso)
		&& iscrizioneCamera) {
	    importoObla = importoObla * new Double(0.5);
	    System.out
		    .println("applico sconto iscrizione alla camera di commercio = "
			    + importoObla.doubleValue());
	}

	// • - 1/3 costruzione industriale o artigianale fino a 3000 mq, ma se >
	// 6000
	// mq allora x 1.5
	if (destinazioneUso.equals(Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE)
		&& supUtilDouble < 3000) {
	    importoObla = (importoObla / 3) * 2;
	}
	if (destinazioneUso.equals(Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE)
		&& supUtilDouble > 6000) {
	    importoObla = importoObla * new Double(1.5);
	}

	// • - 1/3 attività commerciale < 50 mq, ma se >500mq allora x*
	// * 1.5 e se >1500 allora x2
	if (destinazioneUso.equals(Constants.DEST_USO_COMMERCIALE)
		&& supUtilDouble < 50) {
	    importoObla = (importoObla / 3) * 2;
	    System.out.println("applico riduzione per uso commerciale -1/3 = "
		    + importoObla.doubleValue());
	} else if (destinazioneUso.equals(Constants.DEST_USO_COMMERCIALE)
		&& supUtilDouble > 500 && supUtilDouble <= 1500) {
	    importoObla = importoObla * new Double(1.5);
	} else if (destinazioneUso.equals(Constants.DEST_USO_COMMERCIALE)
		&& supUtilDouble > 1500) {
	    importoObla = importoObla * new Double(2.0);
	}
	// • - 1/3 attività sportiva – culturale –
	// sanitaria - culto
	// if (scontoAttivita) {
	if (destinazioneUso.equals(Constants.DEST_USO_SPORTIVO)
		|| destinazioneUso.equals(Constants.DEST_USO_CULTURALE)
		|| destinazioneUso.equals(Constants.DEST_USO_SANITARIA)
		|| destinazioneUso.equals(Constants.DEST_USO_CULTO)) {
	    importoObla = (importoObla / 3) * 2;
	    System.out
		    .println("- 1/3 attività sportiva – culturale – sanitaria - culto = "
			    + importoObla.doubleValue());
	}
	// }
	// • - 1/3 se attività turistica e se <500 mq, ma se > 800
	// * mq allora x1.5
	if (destinazioneUso.equals(Constants.DEST_USO_TURISTICO)
		&& supUtilDouble < 500) {
	    importoObla = (importoObla / 3) * 2;
	} else if (destinazioneUso.equals(Constants.DEST_USO_TURISTICO)
		&& supUtilDouble > 800) {
	    importoObla = importoObla * new Double(1.5);
	}
	// • - 1/3 se in zona agricola
	if (destinazioneUso.equals(Constants.DEST_USO_AGRICOLO)) {
	    importoObla = importoObla * new Double(0.5);
	}

	return importoObla;
    }

    private Double calcolaIL(Double dataInizioIM, Double dataOdierna,
	    Double delta) {

	Double sommaimportoDeltaOblaCalcAut = new Double(0.0);

	String dataInizioIMString = String.valueOf(dataInizioIM);
	String dataOdiernaString = String.valueOf(dataOdierna);

	dataInizioIMString = dataInizioIMString.replace(".", "");
	dataOdiernaString = dataOdiernaString.replace(".", "");

	Integer annoInizioIM = Integer.parseInt(dataInizioIMString.substring(0,
		4));
	Integer annoOdierna = Integer.parseInt(dataOdiernaString
		.substring(0, 4));

	long ggInteressiLegali = 0;

	Double percinteressiAnno = new Double(0.0);
	Double sommainteressiAnno = new Double(0.0);

	Integer annoStart = annoInizioIM;
	Integer annoEnd = annoOdierna;
	for (annoStart.intValue(); annoStart < annoEnd; annoStart++) {

	    String dataInizioAnno = annoStart + "1231";
	    List<InteressiLegali> list = interessiLegaliHome
		    .findByDtFine(new Double(dataInizioAnno));
	    long ggInteroAnno = getGiorniAnno(annoStart);

	    for (InteressiLegali interessiLegali : list) {
		ggInteressiLegali = interessiLegali.getGiorni();
		percinteressiAnno = interessiLegali.getPercentuale();
	    }
	    sommainteressiAnno = applicaPercentuale(delta, ggInteroAnno,
		    ggInteressiLegali, percinteressiAnno);

	    sommaimportoDeltaOblaCalcAut = sommaimportoDeltaOblaCalcAut
		    + sommainteressiAnno;
	    stampa(delta, sommaimportoDeltaOblaCalcAut, ggInteressiLegali,
		    percinteressiAnno, sommainteressiAnno, annoStart);
	    dataInizioAnno = annoStart + "1215";
	    list = interessiLegaliHome.findByDtFine(new Double(dataInizioAnno));
	    if (list != null && !list.isEmpty()) {
		ggInteroAnno = getGiorniAnno(annoStart);

		for (InteressiLegali interessiLegali : list) {
		    ggInteressiLegali = interessiLegali.getGiorni();
		    percinteressiAnno = interessiLegali.getPercentuale();
		}
		sommainteressiAnno = applicaPercentuale(delta, ggInteroAnno,
			ggInteressiLegali, percinteressiAnno);

		sommaimportoDeltaOblaCalcAut = sommaimportoDeltaOblaCalcAut
			+ sommainteressiAnno;
		stampa(delta, sommaimportoDeltaOblaCalcAut, ggInteressiLegali,
			percinteressiAnno, sommainteressiAnno, annoStart);
	    }
	}
	return sommaimportoDeltaOblaCalcAut;
    }

    private void stampa(Double delta, Double sommaimportoDeltaOblaCalcAut,
	    long ggInteressiLegali, Double percinteressiAnno,
	    Double sommainteressiAnno, Integer annoStart) {
	System.out.println("interessi applicati a " + delta + " perc "
		+ percinteressiAnno + " per giorni " + ggInteressiLegali
		+ " dell'anno " + annoStart + " sommati euro: "
		+ sommainteressiAnno);

	System.out.println("sommaimportoDeltaOblaCalcAut: "
		+ sommaimportoDeltaOblaCalcAut + " sommainteressiAnno: "
		+ sommainteressiAnno);
    }

    private long getGiorniAnno(Integer annoStart) {
	Date dateInizioAnno = Converter.convertData("01-01-" + annoStart);
	GregorianCalendar gdtInizio = new GregorianCalendar();
	gdtInizio.setTime(dateInizioAnno);
	int ggAnno = gdtInizio.getActualMaximum(GregorianCalendar.DAY_OF_YEAR);
	System.out.println("giorni anno = " + ggAnno);
	return ggAnno;
    }

    private static Double applicaPercentuale(Double delta, long ggInteroAnno,
	    long ggInteressiLegali, Double percinteressiAnno) {

	Double importoAnnoIntero = delta * (percinteressiAnno / 100);
	Double percAnnoFraz = (((new Double(ggInteressiLegali) * new Double(100)) / new Double(
		ggInteroAnno)) / new Double(100));
	if (percAnnoFraz != 1) {
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
	return getVersamentiValidi(vers, causali);
    }

    public Double getOneriCalcolati(TipologiaAbuso tipologiaAbuso,
	    DatiAbusoPojo abusoDB, DatiPraticaPojo praticaDB, String idabuso,
	    String destinazioneUso) {
	Double answer = Double.valueOf(0);
	// calcolo oneri 47/85
	if (Constants.ID_LEGGE_47_85.equals(praticaDB.getLeggeCondono())) {
	    /**
	     * calcolo per destinazioni: DEST_USO_RESIDENZIALE = "1";
	     * DEST_USO_SPORTIVO = "4"; DEST_USO_ALTRE_ATTIVITA = "6";
	     * DEST_USO_CULTURALE = "8"; DEST_USO_SANITARIA = "9";
	     * DEST_USO_CULTO = "10"; DEST_USO_ATTIVITA_SENZA_SCOPO_LUCRO =
	     * "11";
	     */
	    if (Constants.DEST_USO_RESIDENZIALE.equals(destinazioneUso)
		    || !(Constants.DEST_USO_TURISTICO.equals(destinazioneUso)
			    || Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE
				    .equals(destinazioneUso)
			    || Constants.DEST_USO_AGRICOLO
				    .equals(destinazioneUso) || Constants.DEST_USO_COMMERCIALE
				.equals(destinazioneUso))) {
		answer = calcolaOneriLegge1Residenziale(abusoDB);
		// if (Constants.DEST_USO_RESIDENZIALE.equals(destinazioneUso)
		// && abusoDB.getLocalizzazione().isIsprimaCasa())
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }
	    if (Constants.DEST_USO_TURISTICO.equals(destinazioneUso)
		    || Constants.DEST_USO_COMMERCIALE.equals(destinazioneUso)) {
		answer = calcolaOneriLegge1Residenziale(abusoDB);
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }
	    if (Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE
		    .equals(destinazioneUso)) {
		answer = calcoloOneriDaTabAddetti(abusoDB, answer);
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }
	    if (Constants.DEST_USO_AGRICOLO.equals(destinazioneUso)) {
		answer = calcolaOneriLegge1Agricolo(abusoDB);
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }

	}

	// calcolo oneri 724/94
	if (Constants.ID_LEGGE_724_94.equals(praticaDB.getLeggeCondono())) {

	    if (Constants.DEST_USO_RESIDENZIALE.equals(destinazioneUso)
		    || !(Constants.DEST_USO_TURISTICO.equals(destinazioneUso)
			    || Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE
				    .equals(destinazioneUso)
			    || Constants.DEST_USO_AGRICOLO
				    .equals(destinazioneUso) || Constants.DEST_USO_COMMERCIALE
				.equals(destinazioneUso))) {
		answer = calcolaOneriLegge2Residenziale(abusoDB);
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }
	    if (Constants.DEST_USO_TURISTICO.equals(destinazioneUso)
		    || Constants.DEST_USO_COMMERCIALE.equals(destinazioneUso)) {
		List<DatiAlloggio> listaAlloggi = datiAlloggioHome
			.findByIdAbuso(datiAbusoService
				.findDatiAbusoById(abusoDB.getIddatiabuso()));
		answer = calcoloOneriDaTab(abusoDB)
			+ calcolaCostiCostruzione(listaAlloggi, abusoDB);
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }
	    if (Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE
		    .equals(destinazioneUso)) {
		answer = calcoloOneriDaTabAddetti(abusoDB, answer);
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }
	    if (Constants.DEST_USO_AGRICOLO.equals(destinazioneUso)) {
		answer = calcolaOneriLegge2Agricolo(abusoDB);
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }
	}

	// calcolo oneri 326/03
	if (Constants.ID_LEGGE_326_03.equals(praticaDB.getLeggeCondono())) {

	    if (Constants.DEST_USO_RESIDENZIALE.equals(destinazioneUso)
		    || !(Constants.DEST_USO_TURISTICO.equals(destinazioneUso)
			    || Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE
				    .equals(destinazioneUso)
			    || Constants.DEST_USO_AGRICOLO
				    .equals(destinazioneUso) || Constants.DEST_USO_COMMERCIALE
				.equals(destinazioneUso))) {
		answer = calcolaOneriLegge2Residenziale(abusoDB);
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }
	    if (Constants.DEST_USO_TURISTICO.equals(destinazioneUso)
		    || Constants.DEST_USO_COMMERCIALE.equals(destinazioneUso)) {
		List<DatiAlloggio> listaAlloggi = datiAlloggioHome
			.findByIdAbuso(datiAbusoService
				.findDatiAbusoById(abusoDB.getIddatiabuso()));
		answer = calcoloOneriDaTab(abusoDB)
			+ calcolaCostiCostruzione(listaAlloggi, abusoDB);
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }
	    if (Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE
		    .equals(destinazioneUso)) {
		answer = calcoloOneriDaTabAddetti(abusoDB, answer);
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }
	    if (Constants.DEST_USO_AGRICOLO.equals(destinazioneUso)) {
		answer = calcolaOneriLegge2Agricolo(abusoDB);
		answer = calcolaRiduzioniOneriLegge(abusoDB, answer);
		return answer;
	    }
	}

	return answer;
    }

    private Double calcolaRiduzioniOneriLegge(DatiAbusoPojo abusoDB,
	    Double answer) {
	String destUso = abusoDB.getDestinazioneUso();
	Double su = new Double(abusoDB.getSuperficeUtile());
	String idepocaabuso = abusoDB.getEpocaAbuso();
	// if (Constants.DEST_USO_RESIDENZIALE.equals(destinazioneUso)
	// && abusoDB.getLocalizzazione().isIsprimaCasa())
	boolean scontoattivita = abusoDB.getLocalizzazione()
		.isIscrizioneCamera();
	if ((Constants.DEST_USO_RESIDENZIALE.equals(destUso) && abusoDB
		.getLocalizzazione().isIsprimaCasa())
		|| Constants.DEST_USO_AGRICOLO.equals(destUso)
		|| (scontoattivita && (Constants.DEST_USO_COMMERCIALE
			.equals(destUso)
			|| Constants.DEST_USO_TURISTICO.equals(destUso)
			|| Constants.DEST_USO_ALTRE_ATTIVITA.equals(destUso)
			|| Constants.DEST_USO_ATTIVITA_SENZA_SCOPO_LUCRO
				.equals(destUso)
			|| Constants.DEST_USO_CULTO.equals(destUso)
			|| Constants.DEST_USO_CULTURALE.equals(destUso)
			|| Constants.DEST_USO_SANITARIA.equals(destUso) || Constants.DEST_USO_SPORTIVO
			    .equals(destUso)))) {
	    if (su < 400) {
		if (Constants.PERIODO_2_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.1;
		} else if (Constants.PERIODO_3_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.5;
		} else if (Constants.PERIODO_1_724_.equals(idepocaabuso)
			|| Constants.PERIODO_2_724_.equals(idepocaabuso)) {
		    answer = answer * 0.7;
		}
	    } else if (su >= 400 && su < 800) {
		if (Constants.PERIODO_2_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.3;
		} else if (Constants.PERIODO_3_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.65;
		} else if (Constants.PERIODO_1_724_.equals(idepocaabuso)
			|| Constants.PERIODO_2_724_.equals(idepocaabuso)) {
		    answer = answer * 0.85;
		}
	    } else if (su > 800) {
		if (Constants.PERIODO_2_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.5;
		} else if (Constants.PERIODO_3_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.8;
		} else if (Constants.PERIODO_1_724_.equals(idepocaabuso)
			|| Constants.PERIODO_2_724_.equals(idepocaabuso)) {
		    answer = answer * 1;
		}
	    }

	} else if (Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE.equals(destUso)
		&& scontoattivita) {
	    if (su < 1000) {
		if (Constants.PERIODO_2_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.1;
		} else if (Constants.PERIODO_3_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.5;
		} else if (Constants.PERIODO_1_724_.equals(idepocaabuso)
			|| Constants.PERIODO_2_724_.equals(idepocaabuso)) {
		    answer = answer * 0.7;
		}
	    } else if (su >= 1000 && su < 3000) {
		if (Constants.PERIODO_2_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.3;
		} else if (Constants.PERIODO_3_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.65;
		} else if (Constants.PERIODO_1_724_.equals(idepocaabuso)
			|| Constants.PERIODO_2_724_.equals(idepocaabuso)) {
		    answer = answer * 0.85;
		}
	    } else if (su > 3000) {
		if (Constants.PERIODO_2_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.5;
		} else if (Constants.PERIODO_3_47_85.equals(idepocaabuso)) {
		    answer = answer * 0.8;
		} else if (Constants.PERIODO_1_724_.equals(idepocaabuso)
			|| Constants.PERIODO_2_724_.equals(idepocaabuso)) {
		    answer = answer * 1;
		}
	    }
	}

	return answer;
    }

    private Double calcolaOneriLegge1Residenziale(DatiAbusoPojo abusoDB) {
	Double answer = Double.valueOf(0);
	String idepocaabuso = abusoDB.getEpocaAbuso();
	/**
	 * - ABUSI 1° PERIODO NON SONO PREVISTI ONERI
	 */
	if (Constants.PERIODO_1_47_85.equals(idepocaabuso)) {
	    return answer;
	}
	/**
	 * - ABUSI 2° PERIODO SONO PREVISTI GLI ONERI DI URBANIZZAZIONE PRIMARIA
	 * E SECONDARIA E SI CALCOLANO MOLTIPLICANDO LA SOMMATORIA DEI MC UTILI
	 * (Volume utile vuoto per pieno mc: + Non redenziale/accessori vuoto
	 * per pieno mc:) ED ACCESSORI PER UN PARAMETRO DA TABELLA (ALLEGATO 2),
	 * CHE SI DIVIDE PER ZONA URBANISTICA ED INTERVENTO E NON CI SONO
	 * INTERESSI
	 */
	if (Constants.PERIODO_2_47_85.equals(idepocaabuso)) {
	    answer = calcoloOneriDaTab(abusoDB);
	    return calcoloOneriDaTab(abusoDB);
	}
	/**
	 * - ABUSI 3° PERIODO SONO PREVISTI GLI ONERI COME SOPRA, PIU' IL COSTO
	 * DI COSTRUZIONE DELLE OPERE OGGETTO DELLA RICHIESTA CALCOLATI COME DA
	 * ALLEGATO 3
	 */
	if (Constants.PERIODO_3_47_85.equals(idepocaabuso)) {
	    answer = calcoloOneriDaTab(abusoDB);
	    List<DatiAlloggio> listaAlloggi = datiAlloggioHome
		    .findByIdAbuso(datiAbusoService.findDatiAbusoById(abusoDB
			    .getIddatiabuso()));
	    System.out.println("oneri calcolati da tabella: " + answer);
	    answer = answer + calcolaCostiCostruzione(listaAlloggi, abusoDB);
	    System.out
		    .println("oneri calcolati da tabella + costi di cotruzione: "
			    + answer);
	    return answer;
	}

	if (Constants.PERIODO_1_724_.equals(idepocaabuso)
		|| Constants.PERIODO_2_724_.equals(idepocaabuso)) {
	    answer = calcoloOneriDaTab(abusoDB);
	    List<DatiAlloggio> listaAlloggi = datiAlloggioHome
		    .findByIdAbuso(datiAbusoService.findDatiAbusoById(abusoDB
			    .getIddatiabuso()));
	    System.out.println("oneri calcolati da tabella: " + answer);
	    answer = answer + calcolaCostiCostruzione(listaAlloggi, abusoDB);
	    System.out
		    .println("oneri calcolati da tabella + costi di cotruzione: "
			    + answer);
	    return answer;
	}
	return answer;
    }

    private Double calcolaOneriLegge2Residenziale(DatiAbusoPojo abusoDB) {
	Double answer = Double.valueOf(0);
	answer = calcoloOneriDaTab(abusoDB);
	List<DatiAlloggio> listaAlloggi = datiAlloggioHome
		.findByIdAbuso(datiAbusoService.findDatiAbusoById(abusoDB
			.getIddatiabuso()));
	System.out.println("oneri calcolati da tabella: " + answer);
	answer = answer + calcolaCostiCostruzione(listaAlloggi, abusoDB);
	System.out.println("oneri calcolati da tabella + costi di cotruzione: "
		+ answer);
	return answer;
    }

    private Double calcolaOneriLegge1Agricolo(DatiAbusoPojo abusoDB) {
	Double answer = Double.valueOf(0);
	String idepocaabuso = abusoDB.getEpocaAbuso();
	if (Constants.PERIODO_1_47_85.equals(idepocaabuso)
		|| Constants.PERIODO_2_47_85.equals(idepocaabuso)) {
	    return answer;
	}
	if (Constants.PERIODO_3_47_85.equals(idepocaabuso)) {
	    List<DatiAlloggio> listaAlloggi = datiAlloggioHome
		    .findByIdAbuso(datiAbusoService.findDatiAbusoById(abusoDB
			    .getIddatiabuso()));
	    answer = answer + calcolaCostiCostruzione(listaAlloggi, abusoDB);
	    System.out
		    .println("oneri calcolati da tabella + costi di cotruzione: "
			    + answer);
	    return answer;
	}
	return answer;
    }

    private Double calcolaOneriLegge2Agricolo(DatiAbusoPojo abusoDB) {
	Double answer = Double.valueOf(0);
	List<DatiAlloggio> listaAlloggi = datiAlloggioHome
		.findByIdAbuso(datiAbusoService.findDatiAbusoById(abusoDB
			.getIddatiabuso()));
	System.out.println("oneri calcolati da tabella: " + answer);
	answer = answer + calcolaCostiCostruzione(listaAlloggi, abusoDB);
	System.out.println("oneri calcolati da tabella + costi di cotruzione: "
		+ answer);
	return answer;
    }

    private Double calcoloOneriDaTabAddetti(DatiAbusoPojo abusoDB, Double answer) {

	Double superficeUtile = Double.valueOf(0);
	Double superficeTotaleInsediamento = Double.valueOf(0);
	Double volumeDirezionale = Double.valueOf(0);

	Double ks = Double.valueOf(0);
	Double k1 = Double.valueOf(0);
	Double kc = Double.valueOf(0);
	Double k2 = Double.valueOf(0);
	Double d = Double.valueOf(0);
	Double c1 = Double.valueOf(0);

	if (abusoDB.getSuperficeUtile() != null) {
	    superficeUtile = new Double(abusoDB.getSuperficeUtile());
	}
	if (abusoDB.getSuperficeTotale() != null) {
	    superficeTotaleInsediamento = new Double(
		    abusoDB.getSuperficeTotale());
	}
	if (abusoDB.getVolumeDirezionale() != null) {
	    volumeDirezionale = new Double(abusoDB.getVolumeDirezionale());
	}

	List<OneriConcessori> oneriTab = oneriConcessoriHome.findBy(null,
		abusoDB.getDestinazioneUso(),
		Integer.valueOf(abusoDB.getNumeroAddetti()), null);
	for (OneriConcessori oneriConcessori : oneriTab) {
	    d = oneriConcessori.getD().doubleValue();
	    c1 = oneriConcessori.getC1().doubleValue();
	    k1 = oneriConcessori.getUp1().doubleValue() * d * c1
		    * superficeUtile;
	    k2 = oneriConcessori.getUp2().doubleValue() * d * c1
		    * superficeTotaleInsediamento;
	    ks = oneriConcessori.getUs().doubleValue() * d * c1
		    * superficeTotaleInsediamento;
	    kc = oneriConcessori.getUc().doubleValue() * d * c1
		    * volumeDirezionale;
	}

	answer = k1 + k2 + ks + kc;
	return answer;
    }

    /**
     * Per i parametri di urbanizzazione tranne che per le destinazioni
     * residenziali commerciale turistiche idustriale artigianale che hanno
     * delle tabelle specifiche utilizzare i parametri della tabella
     * residenziale
     * 
     * @param abusoDB
     * @return
     */

    private Double calcoloOneriDaTab(DatiAbusoPojo abusoDB) {

	Double mcUtili = Double.valueOf(0);
	Double mcAccessori = Double.valueOf(0);
	Double answer = Double.valueOf(0);
	String destinazioneUso = abusoDB.getDestinazioneUso();
	destinazioneUso = Converter
		.parseDestinazioneUsoPerUrbanizzazione(destinazioneUso);
	if (abusoDB.getVolumeUtile() != null)
	    mcUtili = Double.valueOf(abusoDB.getVolumeUtile());
	if (abusoDB.getNonresidenzialeVuoto() != null)
	    mcAccessori = Double.valueOf(abusoDB.getNonresidenzialeVuoto());

	List<OneriConcessori> oneriTab = oneriConcessoriHome.findBy(abusoDB
		.getLocalizzazione().getZonaUrbanizzazione(), destinazioneUso,
		null, abusoDB.getTipoOpera());
	for (OneriConcessori oneriConcessori : oneriTab) {
	    Double parametroTab = oneriConcessori.getCosto().doubleValue();
	    System.out.println("costo urbanizzazione per mc = " + parametroTab
		    + " zona urbanistica = "
		    + abusoDB.getLocalizzazione().getZonaUrbanizzazione());
	    answer = (mcUtili + mcAccessori) * parametroTab;
	    System.out.println("calcoloOneriDaTab = " + answer);
	}
	return answer;
    }

    /**
     * DECRETO MINISTERIALE 31 MAGGIO 1977 Art. 5 - Incremento relativo alla
     * superficie utile abitabile (i1) L'incremento percentuale in funzione
     * della superficie è stabilito in rapporto alle seguenti classi di
     * superficie utile abitabile: 1) oltre 95 metri quadrati e fino a 110 metri
     * quadrati inclusi: 5%; 2) oltre 110 metri quadrati e fino a 130 metri
     * quadrati inclusi: 15%; 3) oltre 130 metri quadrati e fino a 160 metri
     * quadrati inclusi: 30%; 4) oltre 160 metri quadrati: 50%. Per ciascun
     * fabbricato l'incremento percentuale relativo alla superficie utile
     * abitabile, è dato dalla somma dei valori ottenuti moltiplicando gli
     * incrementi percentuali di cui al precedente comma per i rapporti tra la
     * superficie utile abitabile degli alloggi compresi nelle rispettive classi
     * e la superficie utile abitabile dell'intero edificio.
     * 
     * @param abusoDB
     */
    private Double calcolaCostiCostruzione(List<DatiAlloggio> listaAlloggi,
	    DatiAbusoPojo abusoDB) {
	// Double answer = Double.valueOf(0);
	Double superficeUtileTotale = Double.valueOf(0);
	Double superficeUtile = Double.valueOf(0);
	Double superficeAccessoriaTotale = Double.valueOf(0);
	Double superficeAccessoria = Double.valueOf(0);
	Double percTotale = Double.valueOf(0);
	Double perc = Double.valueOf(0);
	Double percAccessori = Double.valueOf(1);
	Integer caratteristicheSpecialiTotale = new Integer(0);
	String[] caratteristicheSpecialiTotaleArray = null;
	Double costoR1 = Double.valueOf(0);
	Double costoR2 = Double.valueOf(0);
	Double costoR3 = Double.valueOf(0);

	Double costoCostruzioneMQ = new Double(260.81);
	Double costoCostruzioneMQMagg = Double.valueOf(0);
	Double costoCostruzioneNuovoEdificio = Double.valueOf(0);

	Double suAbuso = Double.valueOf(abusoDB.getSuperficeUtile());
	Double snrAbuso = Double.valueOf(abusoDB.getNonresidenziale());

	for (DatiAlloggio datiAlloggio : listaAlloggi) {
	    superficeUtileTotale = superficeUtileTotale
		    + Double.valueOf(datiAlloggio.getSuperficieUtile());
	    superficeAccessoriaTotale = superficeAccessoriaTotale
		    + Double.valueOf(datiAlloggio.getSuperficieAccessoria());

	    String csList = datiAlloggio.getCaratteriSpeciali();

	    if (csList != null) {
		csList = csList.replace("[", "");
		csList = csList.replace("]", "");
		caratteristicheSpecialiTotaleArray = csList.split(",");
		caratteristicheSpecialiTotale = caratteristicheSpecialiTotale
			+ csList.split(",").length;
	    }
	}

	for (DatiAlloggio datiAlloggio : listaAlloggi) {
	    superficeUtile = Double.valueOf(datiAlloggio.getSuperficieUtile());
	    superficeAccessoria = Double.valueOf(datiAlloggio
		    .getSuperficieAccessoria());
	    // recupero percentuale incremento superfice abitabile per ogni
	    // alloggio
	    perc = getincrementoSUAbitabile(superficeUtileTotale,
		    superficeUtile);
	    System.out.println("superficeUtile: "
		    .concat((superficeUtile != null ? String
			    .valueOf(superficeUtile) : "")));
	    System.out.println("perc: ".concat((perc != null ? String
		    .valueOf(perc) : "")));
	    System.out.println("superficeAccessoria: "
		    .concat((superficeAccessoria != null ? String
			    .valueOf(superficeAccessoria) : "")));
	    percTotale = percTotale + perc;

	    costoR2 = getCostoR2(datiAlloggio.getTipologiaAlloggio()
		    .getIdtipologiaAlloggio());
	}

	System.out
		.println("percTotale getincrementoSUAbitabile: " + percTotale);

	if (superficeUtileTotale > 0)
	    percAccessori = (superficeAccessoriaTotale / superficeUtileTotale) * 100;
	System.out.println("superficeAccessoriaTotale : " + percAccessori);
	System.out
		.println("percTotale getincrementoSUAbitabile: " + percTotale);
	// recupero percentuale incremento superfice non abitabile
	percTotale = getPercIncrementoSUNonAbitabile(percTotale, percAccessori);

	// recupero percentuale incremento caratteristiche speciali
	if (caratteristicheSpecialiTotaleArray != null) {
	    for (int i = 0; i < caratteristicheSpecialiTotaleArray.length; i++) {
		if (caratteristicheSpecialiTotaleArray[i].equals("1")) {
		    percTotale = percTotale + 10;
		} else if (caratteristicheSpecialiTotaleArray[i].equals("2")) {
		    percTotale = percTotale + 20;
		} else if (caratteristicheSpecialiTotaleArray[i].equals("3")) {
		    percTotale = percTotale + 30;
		} else if (caratteristicheSpecialiTotaleArray[i].equals("4")) {
		    percTotale = percTotale + 40;
		} else if (caratteristicheSpecialiTotaleArray[i].equals("5")) {
		    percTotale = percTotale + 50;
		}
	    }
	}

	// recupero costo R3 + percentuale incremento in base alla classe
	if (percTotale <= 5) {
	    percTotale = 0.0;
	    costoR3 = 2.25;
	} else if (percTotale > 5 && percTotale <= 10) {
	    percTotale = 5.0;
	    costoR3 = 2.25;
	} else if (percTotale > 10 && percTotale <= 15) {
	    percTotale = 10.0;
	    costoR3 = 2.25;
	} else if (percTotale > 15 && percTotale <= 20) {
	    percTotale = 15.0;
	    costoR3 = 2.25;
	} else if (percTotale > 20 && percTotale <= 25) {
	    percTotale = 20.0;
	    costoR3 = 2.0;
	} else if (percTotale > 25 && percTotale <= 30) {
	    percTotale = 25.0;
	    costoR3 = 2.0;
	} else if (percTotale > 30 && percTotale <= 35) {
	    percTotale = 30.0;
	    costoR3 = 2.0;
	} else if (percTotale > 35 && percTotale <= 40) {
	    percTotale = 35.0;
	    costoR3 = 2.0;
	} else if (percTotale > 40 && percTotale <= 45) {
	    percTotale = 40.0;
	    costoR3 = 1.25;
	} else if (percTotale > 45 && percTotale <= 50) {
	    percTotale = 45.0;
	    costoR3 = 1.25;
	} else if (percTotale > 50) {
	    percTotale = 50.0;
	    costoR3 = 1.25;
	}

	costoCostruzioneMQMagg = costoCostruzioneMQ * (percTotale / 100);
	if (costoCostruzioneMQMagg > 0) {
	    costoCostruzioneMQMagg = costoCostruzioneMQMagg
		    + costoCostruzioneMQ;
	    costoCostruzioneNuovoEdificio = costoCostruzioneMQMagg
		    * (suAbuso + (snrAbuso * 0.6));
	} else {
	    costoCostruzioneNuovoEdificio = costoCostruzioneMQ
		    * (suAbuso + (snrAbuso * 0.6));
	}
	System.out.println("costoCostruzioneMQ: " + costoCostruzioneMQ);
	System.out.println("costoCostruzioneMQMagg: " + costoCostruzioneMQMagg);
	System.out.println("percTotale: " + percTotale);
	System.out.println("costoCostruzioneNuovoEdificio: "
		+ costoCostruzioneNuovoEdificio);
	costoR1 = getCostoR1(abusoDB.getLocalizzazione()
		.getZonaUrbanizzazione());
	System.out.println("costoR1: " + costoR1);
	System.out.println("costoR2: " + costoR2);
	System.out.println("costoR3: " + costoR3);
	// answer = ((costoR1 + costoR2 + costoR3) / 100)
	// * costoCostruzioneNuovoEdificio;
	// System.out.println("answer: " + answer);
	// answer = Converter.round(answer, 2);
	// return answer;

	return Converter.round(((costoR1 + costoR2 + costoR3) / 100)
		* costoCostruzioneNuovoEdificio, 2);
    }

    private Double getCostoR2(int idtipologiaAlloggio) {

	if (Constants.UNIFAMILIARI_SINGOLE.equals(String
		.valueOf(idtipologiaAlloggio))) {
	    return 2.25;
	} else if (Constants.UNIFAMILIARI_AGGREGATE_FINO_A_2_PIANI_MAX_4
		.equals(String.valueOf(idtipologiaAlloggio))) {
	    return 1.75;
	} else if (Constants.UNIFAMILIARI_AGGREGATE_FINO_A_2_PIANI_A_SCHIERA
		.equals(String.valueOf(idtipologiaAlloggio))) {
	    return 1.50;
	} else if (Constants.PLURIFAMILIARI_FINO_A_3_PIANI_ABITABILI
		.equals(String.valueOf(idtipologiaAlloggio))) {
	    return 1.25;
	} else if (Constants.PLURIFAMILIARI_OLTRE_3_PIANI_ABITABILI
		.equals(String.valueOf(idtipologiaAlloggio))) {
	    return 1.75;
	}

	return 0.0;
    }

    /**
     * negli allegati 2 e 4 ci sono le seguenti classi: E C5 C4 C1 C12C3 C123 B
     * B3 B2 B1 A no classi scusa ZONE PRG mentre nell'excel nel riquadro R1 ci
     * sono solo le seguenti: A, B, C2, C1, E Luigi Acanfora
     * (geom.acanforaluigi@gmail.com) Nell excel la zona b comprende tutte le b
     * dell allegato 2 e 4 X la c La c1 gli dai il parametro c1 Dalla c2 in poi
     * Gli dai il patametto c 2
     * 
     * @param zonaUrbanizzazione
     * @return
     */
    private Double getCostoR1(String zonaUrbanizzazione) {
	if (zonaUrbanizzazione.indexOf("A") >= 0)
	    return 2.25;
	if (zonaUrbanizzazione.indexOf("B") >= 0)
	    return 2.0;
	if (zonaUrbanizzazione.contains("C1")
		|| zonaUrbanizzazione.indexOf("E") >= 0)
	    return 1.25;
	if (zonaUrbanizzazione.contains("C2"))
	    return 2.0;
	return 0.0;
    }

    private Double getPercIncrementoSUNonAbitabile(Double percTotale,
	    Double percAccessori) {
	if (percAccessori < 50) {
	    percTotale = percTotale + 0;
	} else if (percAccessori > 50 && percAccessori <= 75) {
	    percTotale = percTotale + 10;
	} else if (percAccessori > 75 && percAccessori <= 100) {
	    percTotale = percTotale + 20;
	} else if (percAccessori > 100) {
	    percTotale = percTotale + 30;
	}
	return percTotale;
    }

    private Double getincrementoSUAbitabile(Double superficeUtileTotale,
	    Double superficeUtile) {
	Double perc = Double.valueOf(0);
	if (superficeUtile < 95
		&& (superficeUtile > 0 || superficeUtileTotale > 0)) {
	    perc = (superficeUtile / superficeUtileTotale) * 0;
	} else if (superficeUtile > 95 && superficeUtile <= 110) {
	    perc = (superficeUtile / superficeUtileTotale) * 5;
	} else if (superficeUtile > 110 && superficeUtile <= 130) {
	    perc = (superficeUtile / superficeUtileTotale) * 15;
	} else if (superficeUtile > 130 && superficeUtile <= 160) {
	    perc = (superficeUtile / superficeUtileTotale) * 30;
	} else if (superficeUtile > 160) {
	    perc = (superficeUtile / superficeUtileTotale) * 50;
	}
	return perc;
    }

    public Double getDirittiRilPerm(DatiAbusoPojo abusoDB) {
	Double answer = 150.0;
	Double superficeUtile = Double.valueOf(0);
	Double superficeUtileNR = Double.valueOf(0);
	Double superficeTot = Double.valueOf(0);
	if (abusoDB.getVolumeUtile() != null)
	    superficeUtile = new Double(abusoDB.getVolumeUtile());
	if (abusoDB.getNonresidenzialeVuoto() != null)
	    superficeUtileNR = new Double(abusoDB.getNonresidenzialeVuoto());
	superficeTot = superficeUtile + superficeUtileNR;
	if (superficeTot > 1 && superficeTot <= 99) {
	    answer = 150.0;
	} else if (superficeTot >= 100 && superficeTot <= 499) {
	    answer = 200.0;
	} else if (superficeTot >= 500 && superficeTot <= 999) {
	    answer = 250.0;
	} else if (superficeTot >= 1000 && superficeTot <= 1499) {
	    answer = 400.0;
	} else if (superficeTot >= 1500) {
	    answer = 550.0;
	}

	return answer;

    }

    public Double getOneriConcessSaldoLegge2(Double oneriConcessVersato,
	    Double oneriConcessCalcolato, DatiAbusoPojo abusoDB,
	    DatiPraticaPojo praticaDB) {
	Double autodeterminataOnere = abusoDB.getAutodeterminataOneri();

	List<DatiVersamento> vers = datiVersamentiHome.findAll(
		BigInteger.valueOf(Integer.valueOf(abusoDB.getIdPratica())),
		Integer.valueOf(abusoDB.getProgressivo()));
	List<String> causali = new ArrayList<String>();
	causali.add(Constants.ONERI_CAUSALE_SEL);
	Double versValidi1995 = getVersamentiValidi(new Double(19951231), vers,
		causali);
	// versValidi1995 = getVersamentiValidi(vers, causali);
	if (versValidi1995.doubleValue() == autodeterminataOnere.doubleValue()) {
	    oneriConcessCalcolato = oneriConcessCalcolato
		    - autodeterminataOnere;
	}
	if (versValidi1995 < autodeterminataOnere) {
	    Double delta = new Double(0.0);
	    if (autodeterminataOnere > oneriConcessCalcolato) {
		delta = oneriConcessCalcolato - versValidi1995;
	    } else {
		delta = autodeterminataOnere - versValidi1995;
	    }
	    Double interessiMora = calcolaInteressiMoraOneri(abusoDB,
		    praticaDB, autodeterminataOnere, oneriConcessCalcolato);
	    System.out.println("interessi di mora per gli oneri: "
		    + interessiMora);
	    if (autodeterminataOnere <= oneriConcessCalcolato) {
		if (oneriConcessVersato > 0) {

		    oneriConcessCalcolato = (interessiMora + (autodeterminataOnere - oneriConcessVersato))
			    + (oneriConcessCalcolato - autodeterminataOnere);
		} else {
		    oneriConcessCalcolato = (interessiMora + delta)
			    + (oneriConcessCalcolato - autodeterminataOnere);
		}
	    } else if (autodeterminataOnere > oneriConcessCalcolato
		    && oneriConcessCalcolato > oneriConcessVersato) {
		oneriConcessCalcolato = interessiMora + delta
			- oneriConcessVersato;
	    } else {
		oneriConcessCalcolato = interessiMora + delta;
	    }
	}
	if (oneriConcessCalcolato < 0)
	    oneriConcessCalcolato = new Double(0.0);
	return oneriConcessCalcolato;
    }

    public Double getOneriConcessSaldoLegge3(Double oneriConcessVersato,
	    Double oneriConcessCalcolato, DatiAbusoPojo abusoDB,
	    DatiPraticaPojo praticaDB) {
	Double autodeterminataOnere = abusoDB.getAutodeterminataOneri();
	if (oneriConcessVersato.doubleValue() == autodeterminataOnere
		.doubleValue()) {
	    oneriConcessCalcolato = oneriConcessCalcolato
		    - autodeterminataOnere;
	} else if (oneriConcessVersato < autodeterminataOnere) {
	    Double delta = new Double(0.0);
	    if (autodeterminataOnere > oneriConcessCalcolato) {
		delta = oneriConcessCalcolato - oneriConcessVersato;
	    } else {
		delta = autodeterminataOnere - oneriConcessVersato;
	    }
	    Double interessiMora = Double.valueOf(0);
	    // calcolato < versato< autodeterminata
	    if (delta > 0) {
		interessiMora = calcolaInteressiMoraOneriLegge3(abusoDB,
			praticaDB, autodeterminataOnere, oneriConcessCalcolato);
	    }
	    System.out.println("interessi di mora per gli oneri: "
		    + interessiMora);
	    if (autodeterminataOnere <= oneriConcessCalcolato) {
		if (oneriConcessVersato > 0) {
		    List<DatiVersamento> vers = datiVersamentiHome.findAll(
			    BigInteger.valueOf(Integer.valueOf(abusoDB
				    .getIdPratica())), Integer.valueOf(abusoDB
				    .getProgressivo()));
		    List<String> causali = new ArrayList<String>();
		    causali.add(Constants.ONERI_CAUSALE_SEL);
		    Double versValidi = getVersamentiValidi(vers, causali);
		    oneriConcessCalcolato = (interessiMora + (autodeterminataOnere - versValidi))
			    + (oneriConcessCalcolato - autodeterminataOnere);
		} else {
		    oneriConcessCalcolato = (interessiMora + delta)
			    + (oneriConcessCalcolato - autodeterminataOnere);
		}
	    } else {
		oneriConcessCalcolato = interessiMora + delta;
	    }
	} else if (oneriConcessVersato.doubleValue() > oneriConcessCalcolato
		.doubleValue()) {
	    oneriConcessCalcolato = oneriConcessCalcolato - oneriConcessVersato;
	}
	if (oneriConcessCalcolato < 0)
	    oneriConcessCalcolato = new Double(0.0);
	return oneriConcessCalcolato;
    }

    /*
     * gli interessi di mora si pagano per l'importo dell'autodeterminata dalla
     * data presentazione domanda se non sono presenti versamenti se sono
     * presenti versamenti si paga sull'importo dell'autodeterminata fino alla
     * data del versamento dalla data del versamento fino ad un successivo
     * versamento si paga sulla differenza tra autodeterminata e versamento e
     * cosi via per tutti i versamenti possibili
     */

    private Double calcolaInteressiMoraOneri(DatiAbusoPojo abusoDB,
	    DatiPraticaPojo praticaDB, Double autodeterminataOnere,
	    double oneriCalcolati) {
	Date dtOdierna = new Date();
	Double interessiMora = new Double(0.0);
	String dtOdiernastr = Converter.dateToString(dtOdierna);

	Double dataOdierna = Converter.dateToDouble(dtOdiernastr, "dd-MM-yyyy");
	List<DatiVersamento> vers = datiVersamentiHome.findAll(BigInteger
		.valueOf(Integer.parseInt(praticaDB.getIddatipratica())),
		Integer.valueOf(abusoDB.getProgressivo()),
		Constants.ONERI_CAUSALE_SEL);
	if (vers.isEmpty()) {
	    if (0 < oneriCalcolati && oneriCalcolati < autodeterminataOnere) {
		autodeterminataOnere = oneriCalcolati;
	    }
	    interessiMora = calcolaMoraOneriSenzaVersamenti(praticaDB,
		    autodeterminataOnere, dataOdierna);
	} else {
	    Double dataInizioIM;
	    Double datafine = null;
	    Double importoIntMora = autodeterminataOnere;
	    List<String> causali = new ArrayList<String>();
	    causali.add(Constants.ONERI_CAUSALE_SEL);
	    double importoVersValidi = getVersamentiValidi(
		    new Double(19951231), vers, causali);
	    boolean flagSottrazioneEseguita = false;
	    if (importoVersValidi < oneriCalcolati
		    && oneriCalcolati < autodeterminataOnere) {
		autodeterminataOnere = oneriCalcolati - importoVersValidi;
		flagSottrazioneEseguita = true;
	    }
	    for (DatiVersamento datiVersamento : vers) {
		if (vers.indexOf(datiVersamento) == 0) {
		    dataInizioIM = Converter.dateToDouble(praticaDB
			    .getDataDomanda());
		    datafine = Converter.dateToDouble(datiVersamento
			    .getDataVersamento());

		    Integer annoInizioIM = getAnnoFromDataDouble(dataInizioIM);
		    Integer annoFine = getAnnoFromDataDouble(datafine);
		    Integer totaleAnniIM = annoFine - annoInizioIM;

		    interessiMora = (autodeterminataOnere * 0.10)
			    * totaleAnniIM;
		    // versamento contestuale alla data domanda se totaleAnniIM
		    // = 0
		    double totaleVersamentiAnno = getVersamentiValidiPerAnno(
			    annoFine, vers, causali);
		    if (totaleAnniIM > 0) {
			importoIntMora = autodeterminataOnere
				- totaleVersamentiAnno;
		    } else {
			importoIntMora = autodeterminataOnere;
			if (!flagSottrazioneEseguita)
			    importoIntMora = autodeterminataOnere
				    - totaleVersamentiAnno;
		    }
		} else {

		    dataInizioIM = datafine.doubleValue();
		    datafine = Converter.dateToDouble(datiVersamento
			    .getDataVersamento());
		    Integer annoInizioIM = getAnnoFromDataDouble(datafine
			    .doubleValue());
		    Integer annoFine = getAnnoFromDataDouble(datafine);
		    Integer totaleAnniIM = annoFine - annoInizioIM;
		    if (importoIntMora > 0)
			interessiMora = interessiMora
				+ ((importoIntMora * 0.10) * totaleAnniIM);
		    double totaleVersamentiAnno = getVersamentiValidiPerAnno(
			    annoFine, vers, causali);
		    importoIntMora = autodeterminataOnere
			    - totaleVersamentiAnno;
		}
	    }

	    Integer annoInizioIM = getAnnoFromDataDouble(datafine.doubleValue());
	    Integer annoFine = getAnnoFromDataDouble(dataOdierna);
	    Integer totaleAnniIM = annoFine - annoInizioIM;
	    if (importoIntMora > 0)
		interessiMora = interessiMora
			+ ((importoIntMora * 0.10) * totaleAnniIM);
	}

	return interessiMora;
    }

    private Double calcolaInteressiMoraOneriLegge3(DatiAbusoPojo abusoDB,
	    DatiPraticaPojo praticaDB, Double autodeterminataOnere,
	    double oneriCalcolati) {
	Date dtOdierna = new Date();
	Double interessiMora = new Double(0.0);
	String dtOdiernastr = Converter.dateToString(dtOdierna);

	Double dataOdierna = Converter.dateToDouble(dtOdiernastr, "dd-MM-yyyy");
	List<DatiVersamento> vers = datiVersamentiHome.findAll(BigInteger
		.valueOf(Integer.parseInt(praticaDB.getIddatipratica())),
		Integer.valueOf(abusoDB.getProgressivo()),
		Constants.ONERI_CAUSALE_SEL);
	List<String> causali = new ArrayList<String>();
	causali.add(Constants.ONERI_CAUSALE_SEL);
	double importoVersValidi = getVersamentiValidi(vers, causali);
	if (vers.isEmpty()) {
	    if (importoVersValidi < oneriCalcolati
		    && oneriCalcolati < autodeterminataOnere) {
		autodeterminataOnere = oneriCalcolati - importoVersValidi;
	    }
	    interessiMora = calcolaMoraOneriSenzaVersamenti(praticaDB,
		    autodeterminataOnere, dataOdierna);
	} else {
	    Double dataInizioIM;
	    Double datafine = null;
	    Double importoIntMora = autodeterminataOnere;
	    boolean flagSottrazioneEseguita = false;
	    if (importoVersValidi < oneriCalcolati
		    && oneriCalcolati < autodeterminataOnere) {
		autodeterminataOnere = oneriCalcolati - importoVersValidi;
		flagSottrazioneEseguita = true;
	    }
	    for (DatiVersamento datiVersamento : vers) {
		if (vers.indexOf(datiVersamento) == 0) {
		    dataInizioIM = Converter.dateToDouble(praticaDB
			    .getDataDomanda());
		    datafine = Converter.dateToDouble(datiVersamento
			    .getDataVersamento());

		    Integer annoInizioIM = getAnnoFromDataDouble(dataInizioIM);
		    Integer annoFine = getAnnoFromDataDouble(datafine);
		    Integer totaleAnniIM = annoFine - annoInizioIM;

		    interessiMora = (autodeterminataOnere * 0.10)
			    * totaleAnniIM;
		    // versamento contestuale alla data domanda se totaleAnniIM
		    // = 0
		    if (totaleAnniIM > 0) {
			importoIntMora = autodeterminataOnere
				- datiVersamento.getImportoEuro();
		    } else {
			importoIntMora = autodeterminataOnere;
			if (!flagSottrazioneEseguita)
			    importoIntMora = autodeterminataOnere
				    - importoVersValidi;
		    }
		} else {

		    dataInizioIM = datafine.doubleValue();
		    datafine = Converter.dateToDouble(datiVersamento
			    .getDataVersamento());
		    Integer annoInizioIM = getAnnoFromDataDouble(datafine
			    .doubleValue());
		    Integer annoFine = getAnnoFromDataDouble(datafine);
		    Integer totaleAnniIM = annoFine - annoInizioIM;
		    if (importoIntMora > 0)
			interessiMora = interessiMora
				+ ((importoIntMora * 0.10) * totaleAnniIM);
		    importoIntMora = autodeterminataOnere
			    - datiVersamento.getImportoEuro();
		}
	    }

	    Integer annoInizioIM = getAnnoFromDataDouble(datafine.doubleValue());
	    Integer annoFine = getAnnoFromDataDouble(dataOdierna);
	    Integer totaleAnniIM = annoFine - annoInizioIM;
	    if (importoIntMora > 0)
		interessiMora = interessiMora
			+ ((importoIntMora * 0.10) * totaleAnniIM);
	}

	return interessiMora;
    }

    private Integer getAnnoFromDataDouble(Double dataInizioIM) {
	String dataInizioIMString = String.valueOf(dataInizioIM);
	dataInizioIMString = dataInizioIMString.replace(".", "");
	Integer annoInizioIM = Integer.parseInt(dataInizioIMString.substring(0,
		4));
	return annoInizioIM;
    }

    private Double calcolaMoraOneriSenzaVersamenti(DatiPraticaPojo praticaDB,
	    Double autodeterminataOnere, Double dataOdierna) {
	Double interessiMora;
	Double dataInizioIM = Converter
		.dateToDouble(praticaDB.getDataDomanda());
	String dataInizioIMString = String.valueOf(dataInizioIM);
	String dataOdiernaString = String.valueOf(dataOdierna);

	dataInizioIMString = dataInizioIMString.replace(".", "");
	dataOdiernaString = dataOdiernaString.replace(".", "");

	Integer annoInizioIM = Integer.parseInt(dataInizioIMString.substring(0,
		4));
	Integer annoOdierna = Integer.parseInt(dataOdiernaString
		.substring(0, 4));

	Integer totaleAnniIM = annoOdierna - annoInizioIM;
	interessiMora = (autodeterminataOnere * 0.10) * totaleAnniIM;
	return interessiMora;
    }

    public List<DatiVersamento> findBy(String dataVersamento,
	    String ufficioPostale, String codiceVersamento) {

	List<DatiVersamento> list = datiVersamentiHome.findBy(dataVersamento,
		ufficioPostale, codiceVersamento);
	return list;

    }

}
