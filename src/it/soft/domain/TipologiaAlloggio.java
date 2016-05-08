package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipologia_alloggio database table.
 * 
 */
@Entity
@Table(name="tipologia_alloggio")
public class TipologiaAlloggio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idtipologia_alloggio", unique=true, nullable=false)
	private int idtipologiaAlloggio;

	@Column(length=45)
	private String descrizione;

    public TipologiaAlloggio() {
    }

	public int getIdtipologiaAlloggio() {
		return this.idtipologiaAlloggio;
	}

	public void setIdtipologiaAlloggio(int idtipologiaAlloggio) {
		this.idtipologiaAlloggio = idtipologiaAlloggio;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}