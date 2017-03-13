package it.soft.web.pojo;

import it.soft.util.Constants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
    private String causale;
    private String causaleDesc;
    private String progressivo_abuso;

    private Map<String, String> causali = new HashMap<String, String>();

    public DatiVersamentiPojo() {
	causali.put(Constants.OBLAZIONE_COMUNE_CAUSALE_SEL, "Oblazione Comune");
	causali.put(Constants.OBLAZIONE_MINISTERO_CAUSALE_SEL,
		"Oblazione Ministero");
	causali.put(Constants.OBLAZIONE_REGIONE_CAUSALE_SEL,
		"Oblazione Regione");
	causali.put(Constants.ONERI_CAUSALE_SEL, "Oneri");
	causali.put(Constants.DIRITTI_SEGRETERIA_CAUSALE_SEL,
		"Diritti Segreteria");
    }

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

    public String getCausale() {
	setCausaleDesc(causali.get(causale));
	return causale;
    }

    public void setCausale(String causale) {
	setCausaleDesc(causali.get(causale));
	this.causale = causale;
    }

    public String getProgressivo_abuso() {
	return progressivo_abuso;
    }

    public void setProgressivo_abuso(String progressivo_abuso) {
	this.progressivo_abuso = progressivo_abuso;
    }

    public String getCausaleDesc() {
	return causaleDesc;
    }

    public void setCausaleDesc(String causaleDesc) {
	this.causaleDesc = causaleDesc;
    }

}
