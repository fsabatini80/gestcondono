package it.soft.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the epoca_abuso database table.
 * 
 */
@Entity
@Table(name = "interessi_legali")
public class InteressiLegali implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idinteressi_legali", unique = true, nullable = false)
	private int idinteressiLegali;

	@Column(name = "datainizio")
	private Double datainizio;

	@Column(name = "datafine")
	private Double datafine;

	@Column(name = "percentuale")
	private Double percentuale;

	@Column(name = "giorni")
	private int giorni;

	public InteressiLegali() {
	}

	public int getIdinteressiLegali() {
		return idinteressiLegali;
	}

	public void setIdinteressiLegali(int idinteressiLegali) {
		this.idinteressiLegali = idinteressiLegali;
	}

	public Double getDatainizio() {
		return datainizio;
	}

	public void setDatainizio(Double datainizio) {
		this.datainizio = datainizio;
	}

	public Double getDatafine() {
		return datafine;
	}

	public void setDatafine(Double datafine) {
		this.datafine = datafine;
	}

	public Double getPercentuale() {
		return percentuale;
	}

	public void setPercentuale(Double percentuale) {
		this.percentuale = percentuale;
	}

	public int getGiorni() {
		return giorni;
	}

	public void setGiorni(int giorni) {
		this.giorni = giorni;
	}

}