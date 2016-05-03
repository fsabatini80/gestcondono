package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the epoca_abuso database table.
 * 
 */
@Entity
@Table(name = "epoca_abuso")
public class EpocaAbuso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idepoca_abuso", unique = true, nullable = false)
	private int idepocaAbuso;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "epoca_a")
	private Date epocaA;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "epoca_da")
	private Date epocaDa;

	@OneToOne
	@JoinColumn(name = "id_legge_condono")
	private LeggiCondono leggeCondono;

	public EpocaAbuso() {
	}

	public int getIdepocaAbuso() {
		return this.idepocaAbuso;
	}

	public void setIdepocaAbuso(int idepocaAbuso) {
		this.idepocaAbuso = idepocaAbuso;
	}

	public Date getEpocaA() {
		return this.epocaA;
	}

	public void setEpocaA(Date epocaA) {
		this.epocaA = epocaA;
	}

	public Date getEpocaDa() {
		return this.epocaDa;
	}

	public void setEpocaDa(Date epocaDa) {
		this.epocaDa = epocaDa;
	}

	public LeggiCondono getLeggeCondono() {
		return this.leggeCondono;
	}

	public void setLeggeCondono(LeggiCondono leggeCondono) {
		this.leggeCondono = leggeCondono;
	}

}