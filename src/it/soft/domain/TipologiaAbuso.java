package it.soft.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the tipologia_abuso database table.
 * 
 */
@Entity
@Table(name = "tipologia_abuso")
public class TipologiaAbuso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idtipologia_abuso", unique = true, nullable = false)
	private int idtipologiaAbuso;

	@Column
	private String descrizione;

	@Column(name = "descrizione_breve")
	private int descrizioneBreve;

	@ManyToOne
	@JoinColumn(name = "id_legge_condono")
	private LeggiCondono leggeCondono;

	public TipologiaAbuso() {
	}

	public int getIdtipologiaAbuso() {
		return this.idtipologiaAbuso;
	}

	public void setIdtipologiaAbuso(int idtipologiaAbuso) {
		this.idtipologiaAbuso = idtipologiaAbuso;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getDescrizioneBreve() {
		return this.descrizioneBreve;
	}

	public void setDescrizioneBreve(int descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}

	public LeggiCondono getIdLeggeCondono() {
		return this.leggeCondono;
	}

	public void setIdLeggeCondono(LeggiCondono leggeCondono) {
		this.leggeCondono = leggeCondono;
	}

}