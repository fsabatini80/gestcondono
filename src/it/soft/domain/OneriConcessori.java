package it.soft.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Column(name = "destinazione_uso", length = 45)
    private String destinazioneUso;

    @Column(name = "costo")
    private BigDecimal costo;
    
    @Column(name = "addetti_min")
    private Integer addettiMin;
    
    @Column(name = "addetti_max")
    private Integer addettiMax;
    
    @Column(name = "id_tipoopera", length = 45)
    private String tipoopera;
    
    @Column(name = "up1")
    private BigDecimal up1;
    @Column(name = "up2")
    private BigDecimal up2;
    @Column(name = "us")
    private BigDecimal us;
    @Column(name = "uc")
    private BigDecimal uc;
    @Column(name = "d")
    private BigDecimal d;
    @Column(name = "c1")
    private BigDecimal c1;
    

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

    public Integer getAddettiMin() {
        return addettiMin;
    }

    public void setAddettiMin(Integer addettiMin) {
        this.addettiMin = addettiMin;
    }

    public Integer getAddettiMax() {
        return addettiMax;
    }

    public void setAddettiMax(Integer addettiMax) {
        this.addettiMax = addettiMax;
    }

	public String getTipoopera() {
		return tipoopera;
	}

	public void setTipoopera(String tipoopera) {
		this.tipoopera = tipoopera;
	}

	public BigDecimal getUp1() {
	    return up1;
	}

	public void setUp1(BigDecimal up1) {
	    this.up1 = up1;
	}

	public BigDecimal getUp2() {
	    return up2;
	}

	public void setUp2(BigDecimal up2) {
	    this.up2 = up2;
	}

	public BigDecimal getUs() {
	    return us;
	}

	public void setUs(BigDecimal us) {
	    this.us = us;
	}

	public BigDecimal getUc() {
	    return uc;
	}

	public void setUc(BigDecimal uc) {
	    this.uc = uc;
	}

	public BigDecimal getD() {
	    return d;
	}

	public void setD(BigDecimal d) {
	    this.d = d;
	}

	public BigDecimal getC1() {
	    return c1;
	}

	public void setC1(BigDecimal c1) {
	    this.c1 = c1;
	}

}