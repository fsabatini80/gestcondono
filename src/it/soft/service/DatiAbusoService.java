package it.soft.service;

import it.soft.dao.DatiAbusoHome;
import it.soft.dao.DatiAlloggioHome;
import it.soft.dao.DatiPraticaHome;
import it.soft.dao.DestinazioneUsoHome;
import it.soft.dao.EpocaAbusoHome;
import it.soft.dao.TipoAlloggioHome;
import it.soft.dao.TipoOperaHome;
import it.soft.dao.TipologiaAbusoHome;
import it.soft.domain.DatiAlloggio;
import it.soft.domain.Datiabuso;
import it.soft.domain.Datipratica;
import it.soft.web.pojo.DatiAbusoPojo;

import java.math.BigDecimal;
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

	public void saveDatiAlloggio(DatiAlloggio datiAlloggio) {
		if (datiAlloggio.getDestinazioneUso() != null)
			datiAlloggio.setDestinazioneUso(destinazioneUsoHome
					.findById(Integer.valueOf(datiAlloggio.getDestinazioneUso()
							.getIdtipologiaDestinazioneUso())));
		if (datiAlloggio.getTipologiaAlloggio() != null)
			datiAlloggio.setTipologiaAlloggio(tipoAlloggioHome
					.findById(datiAlloggio.getTipologiaAlloggio()
							.getIdtipologiaAlloggio()));
		datiAlloggioHome.persist(datiAlloggio);

	}

	public List<DatiAlloggio> findAlloggi(String id) {
		BigDecimal idAbuso = BigDecimal.valueOf(Integer.valueOf(id));
		Datiabuso datiabuso = datiAbusoHome.findById(idAbuso);
		return datiAlloggioHome.findByIdAbuso(datiabuso);
	}

	public DatiAlloggio findAlloggioById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
