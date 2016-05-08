package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the caratteristiche_speciali database table.
 * 
 */
@Entity
@Table(name="caratteristiche_speciali")
public class CaratteristicheSpeciali implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idcaratteristiche_speciali", unique=true, nullable=false)
	private int idcaratteristicheSpeciali;

	@Column(length=145)
	private String descrizione;

    public CaratteristicheSpeciali() {
    }

	public int getIdcaratteristicheSpeciali() {
		return this.idcaratteristicheSpeciali;
	}

	public void setIdcaratteristicheSpeciali(int idcaratteristicheSpeciali) {
		this.idcaratteristicheSpeciali = idcaratteristicheSpeciali;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}