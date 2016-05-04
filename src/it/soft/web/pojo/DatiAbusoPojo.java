package it.soft.web.pojo;

import it.soft.domain.DatiLocalizzazione;
import it.soft.domain.EpocaAbuso;
import it.soft.domain.SoggettiAbuso;
import it.soft.domain.TipoOpera;
import it.soft.domain.TipologiaAbuso;
import it.soft.domain.TipologiaDestinazioneUso;

import java.io.Serializable;
import java.util.List;

public class DatiAbusoPojo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String iddatiabuso;
	private String dataUltimazioneLavori;
	private TipologiaAbuso tipologiaAbuso;
	private String descrizione;
	private TipologiaDestinazioneUso destinazioneUso;
	private EpocaAbuso epocaAbuso;
	private String esenzioniPagamenti;
	private String datiPratica;
	private String nonresidenziale;
	private String nonresidenzialeVuoto;
	private String numeroAddetti;
	private String reddito;
	private String riduzioni;
	private String superficeTotale;
	private String superficeUtile;
	private TipoOpera tipoOpera;
	private String tipoReddito;
	private String volumeDirezionale;
	private String volumeTotale;
	private String volumeUtile;
	private DatiLocalizzazione localizzazione;
	private List<SoggettiAbuso> soggettiAbuso;

	public String getIddatiabuso() {
		return this.iddatiabuso;
	}

	public void setIddatiabuso(String iddatiabuso) {
		this.iddatiabuso = iddatiabuso;
	}

	public String getDataUltimazioneLavori() {
		return this.dataUltimazioneLavori;
	}

	public void setDataUltimazioneLavori(String dataUltimazioneLavori) {
		this.dataUltimazioneLavori = dataUltimazioneLavori;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public TipologiaDestinazioneUso getDestinazioneUso() {
		return this.destinazioneUso;
	}

	public void setDestinazioneUso(TipologiaDestinazioneUso destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}

	public String getEsenzioniPagamenti() {
		return this.esenzioniPagamenti;
	}

	public void setEsenzioniPagamenti(String esenzioniPagamenti) {
		this.esenzioniPagamenti = esenzioniPagamenti;
	}

	public String getIdPratica() {
		return this.datiPratica;
	}

	public void setIdPratica(String datipratica) {
		this.datiPratica = datipratica;
	}

	public String getNonresidenziale() {
		return this.nonresidenziale;
	}

	public void setNonresidenziale(String nonresidenziale) {
		this.nonresidenziale = nonresidenziale;
	}

	public String getNonresidenzialeVuoto() {
		return this.nonresidenzialeVuoto;
	}

	public void setNonresidenzialeVuoto(String nonresidenzialeVuoto) {
		this.nonresidenzialeVuoto = nonresidenzialeVuoto;
	}

	public String getNumeroAddetti() {
		return this.numeroAddetti;
	}

	public void setNumeroAddetti(String numeroAddetti) {
		this.numeroAddetti = numeroAddetti;
	}

	public String getReddito() {
		return this.reddito;
	}

	public void setReddito(String reddito) {
		this.reddito = reddito;
	}

	public String getRiduzioni() {
		return this.riduzioni;
	}

	public void setRiduzioni(String riduzioni) {
		this.riduzioni = riduzioni;
	}

	public String getSuperficeTotale() {
		return this.superficeTotale;
	}

	public void setSuperficeTotale(String superficeTotale) {
		this.superficeTotale = superficeTotale;
	}

	public String getSuperficeUtile() {
		return this.superficeUtile;
	}

	public void setSuperficeUtile(String superficeUtile) {
		this.superficeUtile = superficeUtile;
	}

	public TipoOpera getTipoOpera() {
		return this.tipoOpera;
	}

	public void setTipoOpera(TipoOpera tipoOpera) {
		this.tipoOpera = tipoOpera;
	}

	public String getTipoReddito() {
		return this.tipoReddito;
	}

	public void setTipoReddito(String tipoReddito) {
		this.tipoReddito = tipoReddito;
	}

	public String getVolumeDirezionale() {
		return this.volumeDirezionale;
	}

	public void setVolumeDirezionale(String volumeDirezionale) {
		this.volumeDirezionale = volumeDirezionale;
	}

	public String getVolumeTotale() {
		return this.volumeTotale;
	}

	public void setVolumeTotale(String volumeTotale) {
		this.volumeTotale = volumeTotale;
	}

	public String getVolumeUtile() {
		return this.volumeUtile;
	}

	public void setVolumeUtile(String volumeUtile) {
		this.volumeUtile = volumeUtile;
	}

	public DatiLocalizzazione getLocalizzazione() {
		return localizzazione;
	}

	public void setLocalizzazione(DatiLocalizzazione localizzazione) {
		this.localizzazione = localizzazione;
	}

	public List<SoggettiAbuso> getSoggettiAbuso() {
		return soggettiAbuso;
	}

	public void setSoggettiAbuso(List<SoggettiAbuso> soggettiAbuso) {
		this.soggettiAbuso = soggettiAbuso;
	}

	public TipologiaAbuso getTipologiaAbuso() {
		return tipologiaAbuso;
	}

	public void setTipologiaAbuso(TipologiaAbuso tipologiaAbuso) {
		this.tipologiaAbuso = tipologiaAbuso;
	}

	public void setEpocaAbuso(EpocaAbuso epocaAbuso) {
		this.epocaAbuso = epocaAbuso;
	}

	public EpocaAbuso getEpocaAbuso() {
		return epocaAbuso;
	}

}
