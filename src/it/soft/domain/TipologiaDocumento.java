package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;


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

	@Column(name="id_leggi_condono")
	private int idLeggiCondono;

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

	public int getIdLeggiCondono() {
		return this.idLeggiCondono;
	}

	public void setIdLeggiCondono(int idLeggiCondono) {
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