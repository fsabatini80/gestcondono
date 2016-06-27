package it.soft.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the datiabuso database table.
 * 
 */
@Entity
@Table(name = "datiabuso")
public class Datiabuso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private BigDecimal iddatiabuso;

	@Column(name = "data_ultimazione_lavori", length = 45)
	private String dataUltimazioneLavori;

	@Column(length = 500)
	private String descrizione;

	@OneToOne
	@JoinColumn(name = "destinazione_uso")
	private TipologiaDestinazioneUso destinazioneUso;

	@OneToOne
	@JoinColumn(name = "epoca_abuso")
	private EpocaAbuso epocaAbuso;

	@OneToOne
	@JoinColumn(name = "idtipologia_abuso")
	private TipologiaAbuso tipologiaAbuso;

	@Column(name = "esenzioni_pagamenti", length = 45)
	private String esenzioniPagamenti;

	@ManyToOne
	@JoinColumn(name = "idPratica")
	private Datipratica datiPratica;

	@Column(length = 45)
	private String nonresidenziale;

	@Column(name = "nonresidenziale_vuoto", length = 45)
	private String nonresidenzialeVuoto;

	@Column(name = "numero_addetti", length = 45)
	private String numeroAddetti;

	@Column(length = 45)
	private String reddito;

	@Column(length = 45)
	private String riduzioni;

	@Column(name = "superfice_totale", length = 45)
	private String superficeTotale;

	@Column(name = "superfice_utile", length = 45)
	private String superficeUtile;

	@OneToOne
	@JoinColumn(name = "tipo_opera")
	private TipoOpera tipoOpera;

	@Column(name = "tipo_reddito", length = 45)
	private String tipoReddito;

	@Column(name = "volume_direzionale", length = 45)
	private String volumeDirezionale;

	@Column(name = "volume_totale", length = 45)
	private String volumeTotale;

	@Column(name = "volume_utile", length = 45)
	private String volumeUtile;

	@OneToOne
	@JoinColumn(name = "iddati_localizzazione")
	private DatiLocalizzazione localizzazione;

	@Column(name = "idsoggettoabuso")
	private Integer idsoggettoAbuso;

	@Column
	private Integer progressivo;

	@Column(name = "autodetermina")
	private BigDecimal autodeterminata;

	@Column(name = "redenza_principale")
	private boolean isResidenzaPrincipale;

	public Datiabuso() {
	}

	public BigDecimal getIddatiabuso() {
		return this.iddatiabuso;
	}

	public void setIddatiabuso(BigDecimal iddatiabuso) {
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

	public EpocaAbuso getEpocaAbuso() {
		return this.epocaAbuso;
	}

	public void setEpocaAbuso(EpocaAbuso epocaAbuso) {
		this.epocaAbuso = epocaAbuso;
	}

	public String getEsenzioniPagamenti() {
		return this.esenzioniPagamenti;
	}

	public void setEsenzioniPagamenti(String esenzioniPagamenti) {
		this.esenzioniPagamenti = esenzioniPagamenti;
	}

	public Datipratica getDatiPratica() {
		return this.datiPratica;
	}

	public void setDatiPratica(Datipratica datipratica) {
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

	public Integer getIdsoggettoAbuso() {
		return idsoggettoAbuso;
	}

	public void setIdsoggettoAbuso(Integer idsoggettoAbuso) {
		this.idsoggettoAbuso = idsoggettoAbuso;
	}

	public TipologiaAbuso getTipologiaAbuso() {
		return tipologiaAbuso;
	}

	public void setTipologiaAbuso(TipologiaAbuso tipologiaAbuso) {
		this.tipologiaAbuso = tipologiaAbuso;
	}

	public Integer getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}

	public BigDecimal getAutodeterminata() {
		return autodeterminata;
	}

	public void setAutodeterminata(BigDecimal autodeterminata) {
		this.autodeterminata = autodeterminata;
	}

	public boolean getIsResidenzaPrincipale() {
		return isResidenzaPrincipale;
	}

	public void setIsResidenzaPrincipale(boolean isResidenzaPrincipale) {
		this.isResidenzaPrincipale = isResidenzaPrincipale;
	}
}