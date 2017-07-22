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
 * The persistent class for the dati_sollecito database table.
 * 
 */
@Entity
@Table(name = "dati_sollecito")
public class DatiSollecito implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigInteger iddatiSollecito;
    private BigInteger idAbuso;
    private String dataInvioSoll1;
    private String dataInvioSoll2;
    private String dataPagamento;
    private BigInteger iddatiPratica;
    private byte pagato;
    private int progressivoAbuso;
    private String protocolloSoll1;
    private String protocolloSoll2;
    private String tecnicoIncaricato;

    public DatiSollecito() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iddati_sollecito")
    public BigInteger getIddatiSollecito() {
	return this.iddatiSollecito;
    }

    public void setIddatiSollecito(BigInteger iddatiSollecito) {
	this.iddatiSollecito = iddatiSollecito;
    }

    @Column(name = "data_invio_soll1")
    public String getDataInvioSoll1() {
	return this.dataInvioSoll1;
    }

    public void setDataInvioSoll1(String dataInvioSoll1) {
	this.dataInvioSoll1 = dataInvioSoll1;
    }

    @Column(name = "data_invio_soll2")
    public String getDataInvioSoll2() {
	return this.dataInvioSoll2;
    }

    public void setDataInvioSoll2(String dataInvioSoll2) {
	this.dataInvioSoll2 = dataInvioSoll2;
    }

    @Column(name = "data_pagamento")
    public String getDataPagamento() {
	return this.dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
	this.dataPagamento = dataPagamento;
    }

    @Column(name = "iddati_pratica")
    public BigInteger getIddatiPratica() {
	return this.iddatiPratica;
    }

    public void setIddatiPratica(BigInteger iddatiPratica) {
	this.iddatiPratica = iddatiPratica;
    }

    public byte getPagato() {
	return this.pagato;
    }

    public void setPagato(byte pagato) {
	this.pagato = pagato;
    }

    @Column(name = "progressivo_abuso")
    public int getProgressivoAbuso() {
	return this.progressivoAbuso;
    }

    public void setProgressivoAbuso(int progressivoAbuso) {
	this.progressivoAbuso = progressivoAbuso;
    }

    @Column(name = "protocollo_soll1")
    public String getProtocolloSoll1() {
	return this.protocolloSoll1;
    }

    public void setProtocolloSoll1(String protocolloSoll1) {
	this.protocolloSoll1 = protocolloSoll1;
    }

    @Column(name = "protocollo_soll2")
    public String getProtocolloSoll2() {
	return this.protocolloSoll2;
    }

    public void setProtocolloSoll2(String protocolloSoll2) {
	this.protocolloSoll2 = protocolloSoll2;
    }

    @Column(name = "tecnico_incaricato")
    public String getTecnicoIncaricato() {
	return this.tecnicoIncaricato;
    }

    public void setTecnicoIncaricato(String tecnicoIncaricato) {
	this.tecnicoIncaricato = tecnicoIncaricato;
    }

    @Column(name = "id_abuso")
    public BigInteger getIdAbuso() {
	return idAbuso;
    }

    public void setIdAbuso(BigInteger idAbuso) {
	this.idAbuso = idAbuso;
    }

}