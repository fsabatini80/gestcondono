package it.soft.service;

import it.soft.dao.DatiAbusoHome;
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

	public void saveDatiAbuso(DatiAbusoPojo pojo) {
		Datiabuso datiabuso;
		if (pojo.getIddatiabuso() != null && !"".equals(pojo.getIddatiabuso()))
			datiabuso = datiAbusoHome.findById(BigDecimal.valueOf(Integer
					.parseInt(pojo.getIddatiabuso())));
		else
			datiabuso = new Datiabuso();
		datiabuso.setDataUltimazioneLavori(pojo.getDataUltimazioneLavori());
		datiabuso.setDescrizione(pojo.getDescrizione());
//		datiabuso.setDestinazioneUso(pojo.getDestinazioneUso());
//		datiabuso.setEpocaAbuso(pojo.getEpocaAbuso());
		datiabuso.setEsenzioniPagamenti(pojo.getEsenzioniPagamenti());
		datiabuso.setIdPratica(pojo.getIdPratica());
		datiabuso.setLocalizzazione(pojo.getLocalizzazione());
		datiabuso.setNonresidenziale(pojo.getNonresidenziale());
		datiabuso.setNonresidenzialeVuoto(pojo.getNonresidenzialeVuoto());
		datiabuso.setNumeroAddetti(pojo.getNumeroAddetti());
		datiabuso.setReddito(pojo.getReddito());
		datiabuso.setRiduzioni(pojo.getRiduzioni());
		datiabuso.setSuperficeTotale(pojo.getSuperficeTotale());
		datiabuso.setSuperficeUtile(pojo.getSuperficeUtile());
//		datiabuso.setTipoOpera(pojo.getTipoOpera());
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
//		target.setDestinazioneUso(source.getDestinazioneUso());
//		target.setEpocaAbuso(source.getEpocaAbuso());
		target.setEsenzioniPagamenti(source.getEsenzioniPagamenti());
		target.setIdPratica(source.getIdPratica());
		target.setLocalizzazione(source.getLocalizzazione());
		target.setNonresidenziale(source.getNonresidenziale());
		target.setNonresidenzialeVuoto(source.getNonresidenzialeVuoto());
		target.setNumeroAddetti(source.getNumeroAddetti());
		target.setReddito(source.getReddito());
		target.setRiduzioni(source.getRiduzioni());
		target.setSuperficeTotale(source.getSuperficeTotale());
		target.setSuperficeUtile(source.getSuperficeUtile());
//		target.setTipoOpera(source.getTipoOpera());
		target.setTipoReddito(source.getTipoReddito());
		target.setVolumeDirezionale(source.getVolumeDirezionale());
		target.setVolumeUtile(source.getVolumeUtile());
		return target;
	}

	public List<Datiabuso> findAll() {
		return datiAbusoHome.findAll();
	}
}
