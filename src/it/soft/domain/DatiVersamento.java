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
 * The persistent class for the dati_versamento database table.
 * 
 */
@Entity
@Table(name = "dati_versamento")
public class DatiVersamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "iddati_versamento", unique = true, nullable = false)
	private BigInteger iddatiVersamento;

	@Column(length = 120)
	private String causale;

	@Column(length = 45)
	private String ccPostale;

	@Column(length = 45)
	private String codiceVersamento;

	@Column(length = 45)
	private String cognome;

	@Column(length = 10)
	private String dataInserimento;

	@Column(length = 10)
	private String dataProtocollo;

	@Column(length = 10)
	private String dataVersamento;

	@Column(length = 45)
	private String iban;

	private BigInteger iddatipratica;

	@Column(length = 45)
	private Double importo;

	@Column(length = 45)
	private Double importoEuro;

	@Column(length = 45)
	private Double importoLire;

	@Column(length = 45)
	private String nome;

	@Column(length = 45)
	private String numeroBollettino;

	@Column(length = 45)
	private String numeroProtocollo;

	@Column(length = 45)
	private String ragioneSociale;

	@Column(length = 45)
	private String ufficioPostale;

	public DatiVersamento() {
	}

	public BigInteger getIddatiVersamento() {
		return this.iddatiVersamento;
	}

	public void setIddatiVersamento(BigInteger iddatiVersamento) {
		this.iddatiVersamento = iddatiVersamento;
	}

	public String getCausale() {
		return this.causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	public String getCcPostale() {
		return this.ccPostale;
	}

	public void setCcPostale(String ccPostale) {
		this.ccPostale = ccPostale;
	}

	public String getCodiceVersamento() {
		return this.codiceVersamento;
	}

	public void setCodiceVersamento(String codiceVersamento) {
		this.codiceVersamento = codiceVersamento;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getDataInserimento() {
		return this.dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getDataProtocollo() {
		return this.dataProtocollo;
	}

	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public String getDataVersamento() {
		return this.dataVersamento;
	}

	public void setDataVersamento(String dataVersamento) {
		this.dataVersamento = dataVersamento;
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public BigInteger getIddatipratica() {
		return this.iddatipratica;
	}

	public void setIddatipratica(BigInteger iddatipratica) {
		this.iddatipratica = iddatipratica;
	}

	public Double getImporto() {
		return this.importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public Double getImportoEuro() {
		return this.importoEuro;
	}

	public void setImportoEuro(Double importoEuro) {
		this.importoEuro = importoEuro;
	}

	public Double getImportoLire() {
		return this.importoLire;
	}

	public void setImportoLire(Double importoLire) {
		this.importoLire = importoLire;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroBollettino() {
		return this.numeroBollettino;
	}

	public void setNumeroBollettino(String numeroBollettino) {
		this.numeroBollettino = numeroBollettino;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getRagioneSociale() {
		return this.ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getUfficioPostale() {
		return this.ufficioPostale;
	}

	public void setUfficioPostale(String ufficioPostale) {
		this.ufficioPostale = ufficioPostale;
	}

}