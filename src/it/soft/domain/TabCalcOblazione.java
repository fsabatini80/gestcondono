package it.soft.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the tab_calc_oblazione database table.
 * 
 */
@Entity
@Table(name = "tab_calc_oblazione")
public class TabCalcOblazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idtab_calc_oblazione", unique = true, nullable = false)
	private int idtabCalcOblazione;

	@Column(name = "data_fine", length = 10)
	private Double dataFine;

	@Column(name = "data_inizio", length = 10)
	private Double dataInizio;

	@Column(name = "destinazione_uso")
	private int destinazioneUso;

	@Column(name = "importo_oblazione", length = 45)
	private String importoOblazione;

	@Column(name = "legge_condono")
	private int leggeCondono;

	private int tipoabuso;

	public TabCalcOblazione() {
	}

	public int getIdtabCalcOblazione() {
		return this.idtabCalcOblazione;
	}

	public void setIdtabCalcOblazione(int idtabCalcOblazione) {
		this.idtabCalcOblazione = idtabCalcOblazione;
	}

	public Double getDataFine() {
		return this.dataFine;
	}

	public void setDataFine(Double dataFine) {
		this.dataFine = dataFine;
	}

	public Double getDataInizio() {
		return this.dataInizio;
	}

	public void setDataInizio(Double dataInizio) {
		this.dataInizio = dataInizio;
	}

	public int getDestinazioneUso() {
		return this.destinazioneUso;
	}

	public void setDestinazioneUso(int destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}

	public String getImportoOblazione() {
		return this.importoOblazione;
	}

	public void setImportoOblazione(String importoOblazione) {
		this.importoOblazione = importoOblazione;
	}

	public int getLeggeCondono() {
		return this.leggeCondono;
	}

	public void setLeggeCondono(int leggeCondono) {
		this.leggeCondono = leggeCondono;
	}

	public int getTipoabuso() {
		return this.tipoabuso;
	}

	public void setTipoabuso(int tipoabuso) {
		this.tipoabuso = tipoabuso;
	}

}