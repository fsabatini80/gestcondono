package it.soft.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * The persistent class for the stampe_solleciti database table.
 * 
 */
@Entity
@Table(name = "stampe_solleciti")
public class StampeSolleciti implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idstampe_solleciti")
    private BigDecimal idstampeSolleciti;

    @Column(name = "data_inserimento")
    private String dataInserimento;

    @Column(name = "data_scadenza")
    private String dataScadenza;

    @Lob()
    private byte[] file;

    @Column(name = "giorni_validita_alert")
    private Integer giorniValiditaAlert;

    @Column(name = "id_abuso")
    private BigDecimal idAbuso;

    @Column(name = "id_pratica")
    private BigDecimal idPratica;

    @Column(name = "nome_file_stampa")
    private String nomeFileStampa;

    @Column(name = "pratica_evasa")
    private Boolean praticaEvasa;

    @Column(name = "progressivo_abuso")
    private Integer progressivoAbuso;

    public StampeSolleciti() {
    }

    public BigDecimal getIdstampeSolleciti() {
	return this.idstampeSolleciti;
    }

    public void setIdstampeSolleciti(BigDecimal idstampeSolleciti) {
	this.idstampeSolleciti = idstampeSolleciti;
    }

    public String getDataInserimento() {
	return this.dataInserimento;
    }

    public void setDataInserimento(String dataInserimento) {
	this.dataInserimento = dataInserimento;
    }

    public String getDataScadenza() {
	return this.dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {
	this.dataScadenza = dataScadenza;
    }

    public byte[] getFile() {
	return this.file;
    }

    public void setFile(byte[] file) {
	this.file = file;
    }

    public int getGiorniValiditaAlert() {
	return this.giorniValiditaAlert;
    }

    public void setGiorniValiditaAlert(int giorniValiditaAlert) {
	this.giorniValiditaAlert = giorniValiditaAlert;
    }

    public BigDecimal getIdAbuso() {
	return this.idAbuso;
    }

    public void setIdAbuso(BigDecimal idAbuso) {
	this.idAbuso = idAbuso;
    }

    public BigDecimal getIdPratica() {
	return this.idPratica;
    }

    public void setIdPratica(BigDecimal idPratica) {
	this.idPratica = idPratica;
    }

    public String getNomeFileStampa() {
	return this.nomeFileStampa;
    }

    public void setNomeFileStampa(String nomeFileStampa) {
	this.nomeFileStampa = nomeFileStampa;
    }

    public Boolean getPraticaEvasa() {
	return this.praticaEvasa;
    }

    public void setPraticaEvasa(Boolean praticaEvasa) {
	this.praticaEvasa = praticaEvasa;
    }

    public int getProgressivoAbuso() {
	return this.progressivoAbuso;
    }

    public void setProgressivoAbuso(int progressivoAbuso) {
	this.progressivoAbuso = progressivoAbuso;
    }

}