package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dati_terreni database table.
 * 
 */
@Entity
@Table(name="dati_terreni")
public class DatiTerreni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="iddati_terreni", unique=true, nullable=false)
	private int iddatiTerreni;

	@Column(length=45)
	private String foglio;

	@Column(name="id_alloggio", nullable=false)
	private Integer idAlloggio;

	@Column(length=45)
	private String particella;

	@Column(length=45)
	private String sezione;

	@Column(length=45)
	private String subalterno;

    public DatiTerreni() {
    }

	public int getIddatiTerreni() {
		return this.iddatiTerreni;
	}

	public void setIddatiTerreni(int iddatiTerreni) {
		this.iddatiTerreni = iddatiTerreni;
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