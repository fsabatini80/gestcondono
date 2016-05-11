package it.soft.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the tipologia_documento database table.
 * 
 */
@Entity
@Table(name="tipologia_documento")
public class TipologiaDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idtipologia_documento", unique=true, nullable=false)
	private int idtipologiaDocumento;

	@Column(length=120)
	private String descrizione;

	@OneToOne
	@JoinColumn(name="id_leggi_condono")
	private LeggiCondono idLeggiCondono;

	private byte obbligatorio;

	private byte richiedibile;

    public TipologiaDocumento() {
    }

	public int getIdtipologiaDocumento() {
		return this.idtipologiaDocumento;
	}

	public void setIdtipologiaDocumento(int idtipologiaDocumento) {
		this.idtipologiaDocumento = idtipologiaDocumento;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LeggiCondono getIdLeggiCondono() {
		return this.idLeggiCondono;
	}

	public void setIdLeggiCondono(LeggiCondono idLeggiCondono) {
		this.idLeggiCondono = idLeggiCondono;
	}

	public byte getObbligatorio() {
		return this.obbligatorio;
	}

	public void setObbligatorio(byte obbligatorio) {
		this.obbligatorio = obbligatorio;
	}

	public byte getRichiedibile() {
		return this.richiedibile;
	}

	public void setRichiedibile(byte richiedibile) {
		this.richiedibile = richiedibile;
	}

}