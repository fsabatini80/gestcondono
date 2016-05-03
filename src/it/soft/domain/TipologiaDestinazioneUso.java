package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipologia_destinazione_uso database table.
 * 
 */
@Entity
@Table(name="tipologia_destinazione_uso")
public class TipologiaDestinazioneUso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idtipologia_destinazione_uso", unique=true, nullable=false)
	private int idtipologiaDestinazioneUso;

	@Column(name="descrizione_tipologia", length=45)
	private String descrizioneTipologia;

    public TipologiaDestinazioneUso() {
    }

	public int getIdtipologiaDestinazioneUso() {
		return this.idtipologiaDestinazioneUso;
	}

	public void setIdtipologiaDestinazioneUso(int idtipologiaDestinazioneUso) {
		this.idtipologiaDestinazioneUso = idtipologiaDestinazioneUso;
	}

	public String getDescrizioneTipologia() {
		return this.descrizioneTipologia;
	}

	public void setDescrizioneTipologia(String descrizioneTipologia) {
		this.descrizioneTipologia = descrizioneTipologia;
	}

}