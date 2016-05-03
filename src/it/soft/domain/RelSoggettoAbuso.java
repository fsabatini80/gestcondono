package it.soft.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
 * The persistent class for the rel_soggetto_abuso database table.
 * 
 */
@Entity
@Table(name = "rel_soggetto_abuso")
public class RelSoggettoAbuso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idrel_soggetto_abuso", unique = true, nullable = false)
	private int idrelSoggettoAbuso;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_soggetto")
	private SoggettiAbuso soggetto;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_abuso")
	private Datiabuso datiabuso;

	public RelSoggettoAbuso() {
	}

	public int getIdrelSoggettoAbuso() {
		return this.idrelSoggettoAbuso;
	}

	public void setIdrelSoggettoAbuso(int idrelSoggettoAbuso) {
		this.idrelSoggettoAbuso = idrelSoggettoAbuso;
	}

	public SoggettiAbuso getSoggettiAbuso() {
		return soggetto;
	}

	public void setSoggettiAbuso(SoggettiAbuso soggetto) {
		this.soggetto = soggetto;
	}

	public Datiabuso getDatiabuso() {
		return datiabuso;
	}

	public void setDatiabuso(Datiabuso datiabuso) {
		this.datiabuso = datiabuso;
	}

}