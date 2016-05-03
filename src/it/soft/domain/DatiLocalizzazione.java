package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigInteger;

/**
 * The persistent class for the dati_localizzazione database table.
 * 
 */
@Entity
@Table(name = "dati_localizzazione")
public class DatiLocalizzazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "iddati_localizzazione", unique = true, nullable = false)
	private String iddatiLocalizzazione;

	@Column(length = 45)
	private String cap;

	@Column(length = 45)
	private String comune;

	@Column(name = "id_pratica")
	private BigInteger idPratica;

	@Column(length = 45)
	private String indirizzo;

	@Column(length = 45)
	private String provincia;

	@Column(name = "vincoli_tutela", length = 45)
	private String vincoliTutela;

	@Column(name = "zona_urbanizzazione", length = 45)
	private String zonaUrbanizzazione;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_abuso")
	private Datiabuso datiabuso;

	public DatiLocalizzazione() {
	}

	public String getIddatiLocalizzazione() {
		return this.iddatiLocalizzazione;
	}

	public void setIddatiLocalizzazione(String iddatiLocalizzazione) {
		this.iddatiLocalizzazione = iddatiLocalizzazione;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getComune() {
		return this.comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public BigInteger getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(BigInteger idPratica) {
		this.idPratica = idPratica;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getVincoliTutela() {
		return this.vincoliTutela;
	}

	public void setVincoliTutela(String vincoliTutela) {
		this.vincoliTutela = vincoliTutela;
	}

	public String getZonaUrbanizzazione() {
		return this.zonaUrbanizzazione;
	}

	public void setZonaUrbanizzazione(String zonaUrbanizzazione) {
		this.zonaUrbanizzazione = zonaUrbanizzazione;
	}

	public Datiabuso getDatiabuso() {
		return datiabuso;
	}

	public void setDatiabuso(Datiabuso datiabuso) {
		this.datiabuso = datiabuso;
	}

}