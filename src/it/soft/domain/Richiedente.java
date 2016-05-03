package it.soft.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the richiedente database table.
 * 
 */
@Entity
@Table(name = "richiedente")
public class Richiedente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private String idrichiedente;

	@Column(length = 45)
	private String cap;

	@Column(name = "codice_fiscale", length = 16)
	private String codiceFiscale;

	@Column(length = 45)
	private String cognome;

	@Column(name = "comune_nascita", length = 45)
	private String comuneNascita;

	@Column(name = "comune_residenza", length = 45)
	private String comuneResidenza;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_nascita")
	private Date dataNascita;

	@Column(length = 45)
	private String indirizzo;

	private byte isvalid;

	@Column(length = 45)
	private String nome;

	@Column(name = "partita_iva", length = 11)
	private String partitaIva;

	@Column(name = "provincia_nascita", length = 45)
	private String provinciaNascita;

	@Column(name = "provincia_residenza", length = 45)
	private String provinciaResidenza;

	@Column(name = "ragione_sociale", length = 45)
	private String ragioneSociale;

	@Column(length = 20)
	private String telefono;

	@Column(name = "stato_estero_res", length = 45)
	private String statoEsteroRes;

	@Column(name = "comune_estero_res", length = 45)
	private String comuneEsteroRes;

	@Column(name = "stato_estero_nas", length = 45)
	private String statoEsteroNas;

	@Column(name = "comune_estero_nas", length = 45)
	private String comuneEsteroNas;

	@Column(name = "email", length = 145)
	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idpratica")
	private Datipratica datiPratica;

	public Richiedente() {
	}

	public String getIdrichiedente() {
		return this.idrichiedente;
	}

	public void setIdrichiedente(String idrichiedente) {
		this.idrichiedente = idrichiedente;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComuneNascita() {
		return this.comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getComuneResidenza() {
		return this.comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public byte getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(byte isvalid) {
		this.isvalid = isvalid;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPartitaIva() {
		return this.partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getProvinciaNascita() {
		return this.provinciaNascita;
	}

	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public String getProvinciaResidenza() {
		return this.provinciaResidenza;
	}

	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public String getRagioneSociale() {
		return this.ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getComuneEsteroNas() {
		return comuneEsteroNas;
	}

	public void setComuneEsteroNas(String comuneEsteroNas) {
		this.comuneEsteroNas = comuneEsteroNas;
	}

	public String getStatoEsteroRes() {
		return statoEsteroRes;
	}

	public void setStatoEsteroRes(String statoEsteroRes) {
		this.statoEsteroRes = statoEsteroRes;
	}

	public String getComuneEsteroRes() {
		return comuneEsteroRes;
	}

	public void setComuneEsteroRes(String comuneEsteroRes) {
		this.comuneEsteroRes = comuneEsteroRes;
	}

	public String getStatoEsteroNas() {
		return statoEsteroNas;
	}

	public void setStatoEsteroNas(String statoEsteroNas) {
		this.statoEsteroNas = statoEsteroNas;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Datipratica getDatipratica() {
		return datiPratica;
	}

	public void setDatipratica(Datipratica datipratica) {
		this.datiPratica = datipratica;
	}

}