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
 * The persistent class for the datipratica database table.
 * 
 */
@Entity
@Table(name = "dati_versamento")
public class DatiVersamenti implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * `iddati_versamento` BIGINT(20) NOT NULL, `iddatipratica` BIGINT(20) NULL,
	 * `codiceVersamento` VARCHAR(45) NULL, `ccPostale` VARCHAR(45) NULL, `iban`
	 * VARCHAR(45) NULL, `dataVersamento` VARCHAR(10) NULL, `importoLire`
	 * VARCHAR(45) NULL, `importoEuro` VARCHAR(45) NULL, `importo` VARCHAR(45)
	 * NULL, `ufficioPostale` VARCHAR(45) NULL, `numeroBollettino` VARCHAR(45)
	 * NULL, `dataProtocollo` VARCHAR(10) NULL, `numeroProtocollo` VARCHAR(45)
	 * NULL, `dataInserimento` VARCHAR(10) NULL, `nome` VARCHAR(45) NULL,
	 * `cognome` VARCHAR(45) NULL, `ragioneSociale` VARCHAR(45) NULL, `causale`
	 * VARCHAR(120) NULL, PRIMARY KEY (`iddati_versamento`));
	 **/

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "iddati_versamento", unique = true, nullable = false)
	private BigInteger iddati_versamento;

	@Column(name = "iddatipratica")
	private BigInteger iddatipratica;

	@Column(name = "codiceVersamento")
	private String codiceVersamento;

	@Column(name = "ccPostale")
	private String ccPostale;

	@Column(name = "iban")
	private String iban;

	@Column(name = "dataVersamento")
	private String dataVersamento;

	@Column(name = "importoLire")
	private String importoLire;

	@Column(name = "importoEuro")
	private String importoEuro;

	@Column(name = "importo")
	private String importo;

	@Column(name = "ufficioPostale")
	private String ufficioPostale;

	@Column(name = "numeroBollettino")
	private String numeroBollettino;

	@Column(name = "dataProtocollo")
	private String dataProtocollo;

	@Column(name = "numeroProtocollo")
	private String numeroProtocollo;

	@Column(name = "dataInserimento")
	private String dataInserimento;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cognome")
	private String cognome;

	@Column(name = "ragioneSociale")
	private String ragioneSociale;

	@Column(name = "causale")
	private String causale;

	public BigInteger getIddati_versamento() {
		return iddati_versamento;
	}

	public void setIddati_versamento(BigInteger iddati_versamento) {
		this.iddati_versamento = iddati_versamento;
	}

	public BigInteger getIddatipratica() {
		return iddatipratica;
	}

	public void setIddatipratica(BigInteger iddatipratica) {
		this.iddatipratica = iddatipratica;
	}

	public String getCodiceVersamento() {
		return codiceVersamento;
	}

	public void setCodiceVersamento(String codiceVersamento) {
		this.codiceVersamento = codiceVersamento;
	}

	public String getCcPostale() {
		return ccPostale;
	}

	public void setCcPostale(String ccPostale) {
		this.ccPostale = ccPostale;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getDataVersamento() {
		return dataVersamento;
	}

	public void setDataVersamento(String dataVersamento) {
		this.dataVersamento = dataVersamento;
	}

	public String getImportoLire() {
		return importoLire;
	}

	public void setImportoLire(String importoLire) {
		this.importoLire = importoLire;
	}

	public String getImportoEuro() {
		return importoEuro;
	}

	public void setImportoEuro(String importoEuro) {
		this.importoEuro = importoEuro;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getUfficioPostale() {
		return ufficioPostale;
	}

	public void setUfficioPostale(String ufficioPostale) {
		this.ufficioPostale = ufficioPostale;
	}

	public String getNumeroBollettino() {
		return numeroBollettino;
	}

	public void setNumeroBollettino(String numeroBollettino) {
		this.numeroBollettino = numeroBollettino;
	}

	public String getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

}