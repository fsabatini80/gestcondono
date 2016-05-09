package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rel_soggetto_abuso database table.
 * 
 */
@Entity
@Table(name="rel_soggetto_abuso")
public class RelSoggettoAbuso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idrel_soggetto_abuso", unique=true, nullable=false)
	private int idrelSoggettoAbuso;

	@Column(length=45)
	private String cap;

	@Column(name="codice_fiscale", length=16)
	private String codiceFiscale;

	@Column(length=45)
	private String cognome;

	@Column(name="comune_estero_nas", length=45)
	private String comuneEsteroNas;

	@Column(name="comune_estero_res", length=45)
	private String comuneEsteroRes;

	@Column(name="comune_nascita", length=45)
	private String comuneNascita;

	@Column(name="comune_residenza", length=45)
	private String comuneResidenza;

	@Column(name="data_nascita", length=10)
	private String dataNascita;

	@Column(length=145)
	private String email;

	@Column(name="id_abuso")
	private java.math.BigInteger idAbuso;

	@Column(name="id_soggetto")
	private int idSoggetto;

	@Column(length=45)
	private String indirizzo;

	private byte isvalid;

	@Column(name="partita_iva", length=11)
	private String partitaIva;

	@Column(name="provincia_nascita", length=45)
	private String provinciaNascita;

	@Column(name="provincia_residenza", length=45)
	private String provinciaResidenza;

	@Column(name="ragione_sociale", length=45)
	private String ragioneSociale;

	@Column(name="stato_estero_nas", length=45)
	private String statoEsteroNas;

	@Column(name="stato_estero_res", length=45)
	private String statoEsteroRes;

	@Column(length=20)
	private String telefono;

    public RelSoggettoAbuso() {
    }

	public int getIdrelSoggettoAbuso() {
		return this.idrelSoggettoAbuso;
	}

	public void setIdrelSoggettoAbuso(int idrelSoggettoAbuso) {
		this.idrelSoggettoAbuso = idrelSoggettoAbuso;
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

	public String getComuneEsteroNas() {
		return this.comuneEsteroNas;
	}

	public void setComuneEsteroNas(String comuneEsteroNas) {
		this.comuneEsteroNas = comuneEsteroNas;
	}

	public String getComuneEsteroRes() {
		return this.comuneEsteroRes;
	}

	public void setComuneEsteroRes(String comuneEsteroRes) {
		this.comuneEsteroRes = comuneEsteroRes;
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

	public String getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public java.math.BigInteger getIdAbuso() {
		return this.idAbuso;
	}

	public void setIdAbuso(java.math.BigInteger idAbuso) {
		this.idAbuso = idAbuso;
	}

	public int getIdSoggetto() {
		return this.idSoggetto;
	}

	public void setIdSoggetto(int idSoggetto) {
		this.idSoggetto = idSoggetto;
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

	public String getStatoEsteroNas() {
		return this.statoEsteroNas;
	}

	public void setStatoEsteroNas(String statoEsteroNas) {
		this.statoEsteroNas = statoEsteroNas;
	}

	public String getStatoEsteroRes() {
		return this.statoEsteroRes;
	}

	public void setStatoEsteroRes(String statoEsteroRes) {
		this.statoEsteroRes = statoEsteroRes;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}