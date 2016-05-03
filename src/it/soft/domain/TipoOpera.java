package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipo_opera database table.
 * 
 */
@Entity
@Table(name="tipo_opera")
public class TipoOpera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idtipo_opera", unique=true, nullable=false)
	private int idtipoOpera;

	@Column(length=145)
	private String descrizione;

    public TipoOpera() {
    }

	public int getIdtipoOpera() {
		return this.idtipoOpera;
	}

	public void setIdtipoOpera(int idtipoOpera) {
		this.idtipoOpera = idtipoOpera;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}