package it.soft.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the soggetti_abuso database table.
 * 
 */
@Entity
@Table(name="soggetti_abuso")
public class SoggettiAbuso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idsoggetti_abuso", unique=true, nullable=false)
	private int idsoggettiAbuso;

	@Column(name="descrizione_soggetto", length=45)
	private String descrizioneSoggetto;
	
    public SoggettiAbuso() {
    }

	public int getIdsoggettiAbuso() {
		return this.idsoggettiAbuso;
	}

	public void setIdsoggettiAbuso(int idsoggettiAbuso) {
		this.idsoggettiAbuso = idsoggettiAbuso;
	}

	public String getDescrizioneSoggetto() {
		return this.descrizioneSoggetto;
	}

	public void setDescrizioneSoggetto(String descrizioneSoggetto) {
		this.descrizioneSoggetto = descrizioneSoggetto;
	}

}