package it.soft.service;

import it.soft.dao.DatiAbusoHome;
import it.soft.dao.DatiPraticaHome;
import it.soft.dao.DestinazioneUsoHome;
import it.soft.dao.EpocaAbusoHome;
import it.soft.dao.TipoOperaHome;
import it.soft.dao.TipologiaAbusoHome;
import it.soft.domain.Datiabuso;
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

	public void saveDatiAbuso(DatiAbusoPojo pojo) {
		Datiabuso datiabuso;
		if (pojo.getIddatiabuso() != null && !"".equals(pojo.getIddatiabuso()))
			datiabuso = datiAbusoHome.findById(BigDecimal.valueOf(Integer
					.parseInt(pojo.getIddatiabuso())));
		else
			datiabuso = new Datiabuso();
		datiabuso.setDataUltimazioneLavori(pojo.getDataUltimazioneLavori());
		datiabuso.setDescrizione(pojo.getDescrizione());
		if (pojo.getTipologiaAbuso() != null)
			datiabuso.setTipologiaAbuso(pojo.getTipologiaAbuso());
		if (pojo.getDestinazioneUso() != null
				&& !"".equals(pojo.getDestinazioneUso()))
			datiabuso.setDestinazioneUso(pojo.getDestinazioneUso());
		if (pojo.getEpocaAbuso() != null && !"".equals(pojo.getEpocaAbuso()))
			datiabuso.setEpocaAbuso(pojo.getEpocaAbuso());
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
			datiabuso.setTipoOpera(pojo.getTipoOpera());
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
		target.setDataUltimazioneLavori(source.getDataUltimazioneLavori());
		target.setDescrizione(source.getDescrizione());
		if (source.getTipologiaAbuso() != null)
			target.setTipologiaAbuso(source.getTipologiaAbuso());
		if (source.getDestinazioneUso() != null)
			target.setDestinazioneUso(source.getDestinazioneUso());
		if (source.getEpocaAbuso() != null)
			target.setEpocaAbuso(source.getEpocaAbuso());
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
			target.setTipoOpera(source.getTipoOpera());
		target.setTipoReddito(source.getTipoReddito());
		target.setVolumeDirezionale(source.getVolumeDirezionale());
		target.setVolumeUtile(source.getVolumeUtile());
		return target;
	}

	public List<Datiabuso> findAll() {
		return datiAbusoHome.findAll();
	}
}
