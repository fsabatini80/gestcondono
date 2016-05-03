package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipologia_riduzione_reddito database table.
 * 
 */
@Entity
@Table(name="tipologia_riduzione_reddito")
public class TipologiaRiduzioneReddito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idtipologia_riduzione_reddito", unique=true, nullable=false)
	private int idtipologiaRiduzioneReddito;

	@Column(length=45)
	private String descrizione;

    public TipologiaRiduzioneReddito() {
    }

	public int getIdtipologiaRiduzioneReddito() {
		return this.idtipologiaRiduzioneReddito;
	}

	public void setIdtipologiaRiduzioneReddito(int idtipologiaRiduzioneReddito) {
		this.idtipologiaRiduzioneReddito = idtipologiaRiduzioneReddito;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}