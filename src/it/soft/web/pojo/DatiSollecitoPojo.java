package it.soft.web.pojo;

import it.soft.domain.Datipratica;

import java.io.Serializable;

public class DatiSollecitoPojo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String iddatiSollecito;
    private String idAbuso;
    private String dataInvioSoll1;
    private String dataInvioSoll2;
    private String dataPagamento;
    private Datipratica iddatiPratica;
    private boolean pagato;
    private String progressivoAbuso;
    private String protocolloSoll1;
    private String protocolloSoll2;
    private String tecnicoIncaricato;
    private String dataStampa;
    private String dataRicevuta;

    public DatiSollecitoPojo() {
    }

    public String getIddatiSollecito() {
	return this.iddatiSollecito;
    }

    public void setIddatiSollecito(String iddatiSollecito) {
	this.iddatiSollecito = iddatiSollecito;
    }

    public String getDataInvioSoll1() {
	return this.dataInvioSoll1;
    }

    public void setDataInvioSoll1(String dataInvioSoll1) {
	this.dataInvioSoll1 = dataInvioSoll1;
    }

    public String getDataInvioSoll2() {
	return this.dataInvioSoll2;
    }

    public void setDataInvioSoll2(String dataInvioSoll2) {
	this.dataInvioSoll2 = dataInvioSoll2;
    }

    public String getDataPagamento() {
	return this.dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
	this.dataPagamento = dataPagamento;
    }

    public Datipratica getIddatiPratica() {
	return this.iddatiPratica;
    }

    public void setIddatiPratica(Datipratica iddatiPratica) {
	this.iddatiPratica = iddatiPratica;
    }

    public boolean getPagato() {
	return this.pagato;
    }

    public void setPagato(boolean pagato) {
	this.pagato = pagato;
    }

    public String getProgressivoAbuso() {
	return this.progressivoAbuso;
    }

    public void setProgressivoAbuso(String progressivoAbuso) {
	this.progressivoAbuso = progressivoAbuso;
    }

    public String getProtocolloSoll1() {
	return this.protocolloSoll1;
    }

    public void setProtocolloSoll1(String protocolloSoll1) {
	this.protocolloSoll1 = protocolloSoll1;
    }

    public String getProtocolloSoll2() {
	return this.protocolloSoll2;
    }

    public void setProtocolloSoll2(String protocolloSoll2) {
	this.protocolloSoll2 = protocolloSoll2;
    }

    public String getTecnicoIncaricato() {
	return this.tecnicoIncaricato;
    }

    public void setTecnicoIncaricato(String tecnicoIncaricato) {
	this.tecnicoIncaricato = tecnicoIncaricato;
    }

    public String getIdAbuso() {
	return idAbuso;
    }

    public void setIdAbuso(String idAbuso) {
	this.idAbuso = idAbuso;
    }

    public String getDataStampa() {
	return dataStampa;
    }

    public void setDataStampa(String dataStampa) {
	this.dataStampa = dataStampa;
    }

    public String getDataRicevuta() {
	return dataRicevuta;
    }

    public void setDataRicevuta(String dataRicevuta) {
	this.dataRicevuta = dataRicevuta;
    }

}