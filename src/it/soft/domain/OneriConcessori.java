package it.soft.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the dati_alloggio database table.
 * 
 */
@Entity
@Table(name = "oneri_concessori")
public class OneriConcessori implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idoneri_concessori", unique = true, nullable = false)
    private Integer idoneriConcessori;

    @Column(name = "zona_urbanizzazione", length = 45)
    private String zonaUrbanizzazione;

    @OneToOne
    @Column(name = "destinazione_uso", length = 45)
    private String destinazioneUso;

    @OneToOne
    @Column(name = "costo")
    private BigDecimal costo;

    public String getZonaUrbanizzazione() {
	return zonaUrbanizzazione;
    }

    public void setZonaUrbanizzazione(String zonaUrbanizzazione) {
	this.zonaUrbanizzazione = zonaUrbanizzazione;
    }

    public String getDestinazioneUso() {
	return destinazioneUso;
    }

    public void setDestinazioneUso(String destinazioneUso) {
	this.destinazioneUso = destinazioneUso;
    }

    public BigDecimal getCosto() {
	return costo;
    }

    public void setCosto(BigDecimal costo) {
	this.costo = costo;
    }

    public Integer getIdoneriConcessori() {
	return idoneriConcessori;
    }

    public void setIdoneriConcessori(Integer idoneriConcessori) {
	this.idoneriConcessori = idoneriConcessori;
    }

}