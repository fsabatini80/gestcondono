package it.soft.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the dati_fabbricati database table.
 * 
 */
@Entity
@Table(name = "dati_fabbricati")
public class DatiFabbricati implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "iddati_fabbricati", unique = true, nullable = false)
	private int iddatiFabbricati;

	@Column(length = 45)
	private String foglio;

	@Column(name = "id_alloggio", nullable = false)
	private Integer idAlloggio;

	@Column(length = 45)
	private String particella;

	@Column(length = 45)
	private String sezione;

	@Column(length = 45)
	private String subalterno;

	public DatiFabbricati() {
	}

	public int getIddatiFabbricati() {
		return this.iddatiFabbricati;
	}

	public void setIddatiFabbricati(int iddatiFabbricati) {
		this.iddatiFabbricati = iddatiFabbricati;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public int getIdAlloggio() {
		return this.idAlloggio;
	}

	public void setIdAlloggio(int idAlloggio) {
		this.idAlloggio = idAlloggio;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSezione() {
		return this.sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getSubalterno() {
		return this.subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

}