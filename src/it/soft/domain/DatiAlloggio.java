package it.soft.domain;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the dati_alloggio database table.
 * 
 */
@Entity
@Table(name="dati_alloggio")
public class DatiAlloggio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="iddati_alloggio", unique=true, nullable=false)
	private String iddatiAlloggio;

	@Column(name="caratteri_speciali", length=45)
	private String caratteriSpeciali;

	@Column(name="destinazione_uso", length=45)
	private String destinazioneUso;

	@Column(name="id_abuso")
	private BigInteger idAbuso;

	@Column(name="id_pratica")
	private BigInteger idPratica;

	@Column(name="superficie_accessoria", length=45)
	private String superficieAccessoria;

	@Column(name="superficie_utile", length=45)
	private String superficieUtile;

	@Column(name="tipologia_alloggio", length=45)
	private String tipologiaAlloggio;

    public DatiAlloggio() {
    }

	public String getIddatiAlloggio() {
		return this.iddatiAlloggio;
	}

	public void setIddatiAlloggio(String iddatiAlloggio) {
		this.iddatiAlloggio = iddatiAlloggio;
	}

	public String getCaratteriSpeciali() {
		return this.caratteriSpeciali;
	}

	public void setCaratteriSpeciali(String caratteriSpeciali) {
		this.caratteriSpeciali = caratteriSpeciali;
	}

	public String getDestinazioneUso() {
		return this.destinazioneUso;
	}

	public void setDestinazioneUso(String destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}

	public BigInteger getIdAbuso() {
		return this.idAbuso;
	}

	public void setIdAbuso(BigInteger idAbuso) {
		this.idAbuso = idAbuso;
	}

	public BigInteger getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(BigInteger idPratica) {
		this.idPratica = idPratica;
	}

	public String getSuperficieAccessoria() {
		return this.superficieAccessoria;
	}

	public void setSuperficieAccessoria(String superficieAccessoria) {
		this.superficieAccessoria = superficieAccessoria;
	}

	public String getSuperficieUtile() {
		return this.superficieUtile;
	}

	public void setSuperficieUtile(String superficieUtile) {
		this.superficieUtile = superficieUtile;
	}

	public String getTipologiaAlloggio() {
		return this.tipologiaAlloggio;
	}

	public void setTipologiaAlloggio(String tipologiaAlloggio) {
		this.tipologiaAlloggio = tipologiaAlloggio;
	}

}