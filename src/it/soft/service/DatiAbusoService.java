package it.soft.service;

import it.soft.dao.DatiAbusoHome;
import it.soft.dao.DatiAlloggioHome;
import it.soft.dao.DatiFabbricatiHome;
import it.soft.dao.DatiPraticaHome;
import it.soft.dao.DatiTerreniHome;
import it.soft.dao.DestinazioneUsoHome;
import it.soft.dao.DocumentiAbusoHome;
import it.soft.dao.EpocaAbusoHome;
import it.soft.dao.TipoAlloggioHome;
import it.soft.dao.TipoOperaHome;
import it.soft.dao.TipologiaAbusoHome;
import it.soft.dao.TipologiaDocHome;
import it.soft.domain.DatiAlloggio;
import it.soft.domain.DatiFabbricati;
import it.soft.domain.DatiTerreni;
import it.soft.domain.Datiabuso;
import it.soft.domain.Datipratica;
import it.soft.domain.DocumentiAbuso;
import it.soft.domain.LeggiCondono;
import it.soft.domain.TipologiaDocumento;
import it.soft.web.pojo.DatiAbusoPojo;
import it.soft.web.pojo.DatiAlloggioPojo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatiAbusoService {

	@Autowired
	DatiAbusoHome datiAbusoHome;
	@Autowired
	DestinazioneUsoHome destinazioneUsoHome;
	@Autowired
	EpocaAbusoHome epocaAbusoHome;
	@Autowired
	TipoOperaHome tipoOperaHome;
	@Autowired
	TipologiaAbusoHome tipologiaAbusoHome;
	@Autowired
	DatiPraticaHome datiPraticaHome;
	@Autowired
	DatiAlloggioHome datiAlloggioHome;
	@Autowired
	TipoAlloggioHome tipoAlloggioHome;
	@Autowired
	DatiTerreniHome datiTerreniHome;
	@Autowired
	DatiFabbricatiHome datiFabbricatiHome;
	@Autowired
	DocumentiAbusoHome documentiAbusoHome;
	@Autowired
	TipologiaDocHome tipologiaDocHome;

	public void saveDatiAbuso(DatiAbusoPojo pojo) {
		Datiabuso datiabuso;
		if (pojo.getIddatiabuso() != null && !"".equals(pojo.getIddatiabuso()))
			datiabuso = datiAbusoHome.findById(BigDecimal.valueOf(Integer
					.parseInt(pojo.getIddatiabuso())));
		else
			datiabuso = new Datiabuso();
		datiabuso.setProgressivo(Integer.valueOf(pojo.getProgressivo()));
		datiabuso.setDataUltimazioneLavori(pojo.getDataUltimazioneLavori());
		datiabuso.setDescrizione(pojo.getDescrizione());
		if (pojo.getTipologiaAbuso() != null
				&& !"".equals(pojo.getTipologiaAbuso()))
			datiabuso.setTipologiaAbuso(tipologiaAbusoHome.findById(Integer
					.valueOf(pojo.getTipologiaAbuso())));
		if (pojo.getDestinazioneUso() != null
				&& !"".equals(pojo.getDestinazioneUso()))
			datiabuso.setDestinazioneUso(destinazioneUsoHome.findById(Integer
					.valueOf(pojo.getDestinazioneUso())));
		if (pojo.getEpocaAbuso() != null && !"".equals(pojo.getEpocaAbuso()))
			datiabuso.setEpocaAbuso(epocaAbusoHome.findById(Integer
					.valueOf(pojo.getEpocaAbuso())));
		datiabuso.setEsenzioniPagamenti(pojo.getEsenzioniPagamenti());
		if (pojo.getIdPratica() != null && !"".equals(pojo.getIdPratica()))
			datiabuso.setIdPratica(datiPraticaHome.findById(BigDecimal
					.valueOf(Integer.valueOf(pojo.getIdPratica()))));
		datiabuso.setLocalizzazione(pojo.getLocalizzazione());
		datiabuso.setNonresidenziale(pojo.getNonresidenziale());
		datiabuso.setNonresidenzialeVuoto(pojo.getNonresidenzialeVuoto());
		datiabuso.setNumeroAddetti(pojo.getNumeroAddetti());
		datiabuso.setReddito(pojo.getReddito());
		datiabuso.setRiduzioni(pojo.getRiduzioni());
		datiabuso.setSuperficeTotale(pojo.getSuperficeTotale());
		datiabuso.setSuperficeUtile(pojo.getSuperficeUtile());
		if (pojo.getTipoOpera() != null && !"".equals(pojo.getTipoOpera()))
			datiabuso.setTipoOpera(tipoOperaHome.findById(Integer.valueOf(pojo
					.getTipoOpera())));
		datiabuso.setTipoReddito(pojo.getTipoReddito());
		datiabuso.setVolumeDirezionale(pojo.getVolumeDirezionale());
		datiabuso.setVolumeUtile(pojo.getVolumeUtile());
		datiAbusoHome.persist(datiabuso);

	}

	public DatiAbusoPojo findById(String id) {
		Datiabuso source = datiAbusoHome.findById(BigDecimal.valueOf(Integer
				.parseInt(id)));
		DatiAbusoPojo target = new DatiAbusoPojo();
		target.setIddatiabuso(id);
		target.setProgressivo(String.valueOf(source.getProgressivo()));
		target.setDataUltimazioneLavori(source.getDataUltimazioneLavori());
		target.setDescrizione(source.getDescrizione());
		if (source.getTipologiaAbuso() != null)
			target.setTipologiaAbuso(String.valueOf(source.getTipologiaAbuso()
					.getIdtipologiaAbuso()));
		if (source.getDestinazioneUso() != null)
			target.setDestinazioneUso(String.valueOf(source
					.getDestinazioneUso().getIdtipologiaDestinazioneUso()));
		if (source.getEpocaAbuso() != null)
			target.setEpocaAbuso(String.valueOf(source.getEpocaAbuso()
					.getIdepocaAbuso()));
		target.setEsenzioniPagamenti(source.getEsenzioniPagamenti());
		if (source.getIdPratica() != null)
			target.setIdPratica(String.valueOf(source.getIdPratica()
					.getIddatipratica()));
		target.setLocalizzazione(source.getLocalizzazione());
		target.setNonresidenziale(source.getNonresidenziale());
		target.setNonresidenzialeVuoto(source.getNonresidenzialeVuoto());
		target.setNumeroAddetti(source.getNumeroAddetti());
		target.setReddito(source.getReddito());
		target.setRiduzioni(source.getRiduzioni());
		target.setSuperficeTotale(source.getSuperficeTotale());
		target.setSuperficeUtile(source.getSuperficeUtile());
		if (source.getTipoOpera() != null)
			target.setTipoOpera(String.valueOf(source.getTipoOpera()
					.getIdtipoOpera()));
		target.setTipoReddito(source.getTipoReddito());
		target.setVolumeDirezionale(source.getVolumeDirezionale());
		target.setVolumeUtile(source.getVolumeUtile());
		return target;
	}

	public Datiabuso findDatiAbusoById(String id) {
		return datiAbusoHome.findById(BigDecimal.valueOf(Integer.parseInt(id)));
	}

	public List<Datiabuso> findAll() {
		return datiAbusoHome.findAll();
	}

	public List<Datiabuso> findAll(String idPratica) {
		BigDecimal idPratBD = BigDecimal.valueOf(Integer.valueOf(idPratica));
		Datipratica datipratica = datiPraticaHome.findById(idPratBD);
		return datiAbusoHome.findAll(datipratica);
	}

	public String countProg(String idPratica) {
		BigDecimal idPratBD = BigDecimal.valueOf(Integer.valueOf(idPratica));
		Datipratica datipratica = datiPraticaHome.findById(idPratBD);
		return String.valueOf(datiAbusoHome.countProg(datipratica) + 1);
	}

	public void saveDatiAlloggio(DatiAlloggioPojo pojo) {
		DatiAlloggio datiAlloggio;
		if (pojo.getIddatiAlloggio() != null)
			datiAlloggio = datiAlloggioHome.findById(BigDecimal.valueOf(Integer
					.valueOf(pojo.getIddatiAlloggio())));
		else
			datiAlloggio = new DatiAlloggio();

		if (pojo.getDestinazioneUso() != null)
			datiAlloggio.setDestinazioneUso(destinazioneUsoHome
					.findById(Integer.valueOf(pojo.getDestinazioneUso())));
		if (pojo.getTipologiaAlloggio() != null)
			datiAlloggio.setTipologiaAlloggio(tipoAlloggioHome.findById(Integer
					.valueOf(pojo.getTipologiaAlloggio())));
		if (pojo.getCaratteriSpeciali() != null) {
			List<String> carSpecial = pojo.getCaratteriSpeciali();
			datiAlloggio.setCaratteriSpeciali(Arrays.toString(carSpecial
					.toArray()));
		}
		datiAlloggio.setIdAbuso(datiAbusoHome.findById(BigDecimal
				.valueOf(Integer.valueOf(pojo.getIdAbuso()))));
		datiAlloggio.setIdPratica(BigInteger.valueOf(Integer.valueOf(pojo
				.getIdPratica())));
		datiAlloggio.setSuperficieAccessoria(pojo.getSuperficieAccessoria());
		datiAlloggio.setSuperficieUtile(pojo.getSuperficieUtile());
		datiAlloggioHome.persist(datiAlloggio);

	}

	public List<DatiAlloggio> findAlloggi(String id) {
		BigDecimal idAbuso = BigDecimal.valueOf(Integer.valueOf(id));
		Datiabuso datiabuso = datiAbusoHome.findById(idAbuso);
		return datiAlloggioHome.findByIdAbuso(datiabuso);
	}

	public DatiAlloggioPojo findAlloggioById(String id) {
		BigDecimal idAlloggio = BigDecimal.valueOf(Integer.valueOf(id));
		DatiAlloggioPojo pojo = new DatiAlloggioPojo();
		DatiAlloggio datiAlloggio = datiAlloggioHome.findById(idAlloggio);
		if (datiAlloggio.getTipologiaAlloggio() != null)
			pojo.setTipologiaAlloggio(String.valueOf(datiAlloggio
					.getTipologiaAlloggio().getIdtipologiaAlloggio()));
		if (datiAlloggio.getDestinazioneUso() != null)
			pojo.setDestinazioneUso(String.valueOf(datiAlloggio
					.getDestinazioneUso().getIdtipologiaDestinazioneUso()));
		if (datiAlloggio.getCaratteriSpeciali() != null) {
			String s = datiAlloggio.getCaratteriSpeciali();
			s = s.replace("[", "");
			s = s.replace("]", "");
			pojo.setCaratteriSpeciali(Arrays.asList(s));
		}
		if (datiAlloggio.getIdAbuso() != null) {
			pojo.setIdAbuso(String.valueOf(datiAlloggio.getIdAbuso()));
		}
		pojo.setIddatiAlloggio(String.valueOf(datiAlloggio.getIddatiAlloggio()));
		pojo.setIdPratica(String.valueOf(datiAlloggio.getIdPratica()));
		pojo.setSuperficieAccessoria(datiAlloggio.getSuperficieAccessoria());
		pojo.setSuperficieUtile(datiAlloggio.getSuperficieUtile());
		return pojo;

	}

	public List<DatiTerreni> findAllTerreniById(String id) {
		return datiTerreniHome.findAll(Integer.valueOf(id));
	}

	public void saveTerreno(DatiTerreni datiTerreni) {
		datiTerreniHome.persist(datiTerreni);
	}

	public List<DatiFabbricati> findAllFabbricatiById(String id) {
		return datiFabbricatiHome.findAll(Integer.valueOf(id));
	}

	public void saveFabbricato(DatiFabbricati datiFabbricati) {
		datiFabbricatiHome.persist(datiFabbricati);
	}

	public void removeTerreno(String id) {
		datiTerreniHome.remove(datiTerreniHome.findById(Integer.valueOf(id)));
	}

	public void removeFabbricato(String id) {
		datiFabbricatiHome.remove(datiFabbricatiHome.findById(Integer
				.valueOf(id)));
	}

	public List<DocumentiAbuso> findAllDocById(String id,
			LeggiCondono leggiCondono) {
		List<DocumentiAbuso> abusos;
		Integer idAbuso = Integer.parseInt(id);
		abusos = documentiAbusoHome.findAll(datiAbusoHome.findById(BigDecimal
				.valueOf(idAbuso)));
		if (abusos == null || abusos.isEmpty()) {
			TipologiaDocumento documento = new TipologiaDocumento();
			documento.setIdLeggiCondono(leggiCondono);
			List<TipologiaDocumento> tipoDoc = tipologiaDocHome
					.findAll(leggiCondono);
			abusos = new ArrayList<DocumentiAbuso>();
			for (TipologiaDocumento tipologiaDocumento : tipoDoc) {
				DocumentiAbuso da = new DocumentiAbuso();
				if (tipologiaDocumento.getObbligatorio() != (byte) 0) {
					da.setIdTipoDocumento(tipologiaDocumento);
					da.setIdAbuso(datiAbusoHome.findById(BigDecimal
							.valueOf(idAbuso)));
					da.setPresente((byte) 0);
					da.setValido((byte) 0);
					abusos.add(da);
					documentiAbusoHome.persist(da);
				}
			}
		}
		return abusos;
	}

	public List<TipologiaDocumento> findAllDocToAdd() {
		List<TipologiaDocumento> tipologiaDocumentos;

		tipologiaDocumentos = tipologiaDocHome.findAll();
		for (TipologiaDocumento tipologiaDocumento : tipologiaDocumentos) {
			if (tipologiaDocumento.getObbligatorio() != 0)
				tipologiaDocumentos.remove(tipologiaDocumento);
		}
		return tipologiaDocumentos;
	}

	public void addDocList(String[] array, String id) {
		List<TipologiaDocumento> list = new ArrayList<TipologiaDocumento>();
		for (int i = 0; i < array.length; i++) {
			list.add(tipologiaDocHome.findById(Integer.valueOf(array[i])));
		}
		Integer idAbuso = Integer.parseInt(id);
		for (TipologiaDocumento tipologiaDocumento : list) {
			DocumentiAbuso da = new DocumentiAbuso();
			if (tipologiaDocumento.getObbligatorio() == (byte) 0) {
				da.setIdTipoDocumento(tipologiaDocumento);
				da.setIdAbuso(datiAbusoHome.findById(BigDecimal
						.valueOf(idAbuso)));
				da.setPresente((byte) 0);
				da.setValido((byte) 0);
				documentiAbusoHome.persist(da);
			}
		}
	}

	public DocumentiAbuso findDocById(String id) {
		Integer idInt = Integer.parseInt(id);
		return documentiAbusoHome.findById(idInt);
	}

	public void saveDocById(DocumentiAbuso doc) {
		documentiAbusoHome.persist(doc);
	}

}
