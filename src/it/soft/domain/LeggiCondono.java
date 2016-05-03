package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the leggi_condono database table.
 * 
 */
@Entity
@Table(name="leggi_condono")
public class LeggiCondono implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idleggi_condono", unique=true, nullable=false)
	private int idleggiCondono;

	@Column(name="legge_numero", length=45)
	private String leggeNumero;

    public LeggiCondono() {
    }

	public int getIdleggiCondono() {
		return this.idleggiCondono;
	}

	public void setIdleggiCondono(int idleggiCondono) {
		this.idleggiCondono = idleggiCondono;
	}

	public String getLeggeNumero() {
		return this.leggeNumero;
	}

	public void setLeggeNumero(String leggeNumero) {
		this.leggeNumero = leggeNumero;
	}

	@Override
	public String toString() {
		return leggeNumero;
	}

}