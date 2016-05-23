package it.soft.web.pojo;

import java.io.Serializable;

public class DatiVersamentiPojo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idversamento;
	private String iddatipratica;
	private String codiceVersamento;
	private String ccPostale;
	private String iban;
	private String dataVersamento;
	private String importoLire;
	private String importoEuro;
	private String importo;
	private String ufficioPostale;
	private String numeroBollettino;
	private String dataProtocollo;
	private String numeroProtocollo;
	private String dataInserimento;
	private String nome;
	private String cognome;
	private String ragioneSociale;

	public String getIddatipratica() {
		return iddatipratica;
	}

	public void setIddatipratica(String iddatipratica) {
		this.iddatipratica = iddatipratica;
	}

	public String getIdversamento() {
		return idversamento;
	}

	public void setIdversamento(String idversamento) {
		this.idversamento = idversamento;
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

	public String getCodiceVersamento() {
		return codiceVersamento;
	}

	public void setCodiceVersamento(String codiceVersamento) {
		this.codiceVersamento = codiceVersamento;
	}

}
