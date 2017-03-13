package it.soft.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * The persistent class for the datiabuso database table.
 * 
 */

@Entity
@Table(name = "datiabuso")
public @Data
class Datiabuso implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private BigDecimal iddatiabuso;

    @Column(name = "data_ultimazione_lavori", length = 45)
    private String dataUltimazioneLavori;

    @Column(length = 500)
    private String descrizione;

    @OneToOne
    @JoinColumn(name = "destinazione_uso")
    private TipologiaDestinazioneUso destinazioneUso;

    @OneToOne
    @JoinColumn(name = "epoca_abuso")
    private EpocaAbuso epocaAbuso;

    @OneToOne
    @JoinColumn(name = "idtipologia_abuso")
    private TipologiaAbuso tipologiaAbuso;

    @Column(name = "esenzioni_pagamenti", length = 45)
    private String esenzioniPagamenti;

    @ManyToOne
    @JoinColumn(name = "idPratica")
    private Datipratica datiPratica;

    @Column(length = 45)
    private String nonresidenziale;

    @Column(name = "nonresidenziale_vuoto", length = 45)
    private String nonresidenzialeVuoto;

    @Column(name = "numero_addetti", length = 45)
    private String numeroAddetti;

    @Column(length = 45)
    private String reddito;

    @Column(length = 45)
    private String riduzioni;

    @Column(name = "superfice_totale", length = 45)
    private String superficeTotale;

    @Column(name = "superfice_utile", length = 45)
    private String superficeUtile;

    @OneToOne
    @JoinColumn(name = "tipo_opera")
    private TipoOpera tipoOpera;

    @Column(name = "tipo_reddito", length = 45)
    private String tipoReddito;

    @Column(name = "volume_direzionale", length = 45)
    private String volumeDirezionale;

    @Column(name = "volume_totale", length = 45)
    private String volumeTotale;

    @Column(name = "volume_utile", length = 45)
    private String volumeUtile;

    @OneToOne
    @JoinColumn(name = "iddati_localizzazione")
    private DatiLocalizzazione localizzazione;

    @Column(name = "idsoggettoabuso")
    private Integer idsoggettoAbuso;

    @Column
    private Integer progressivo;

    @Column(name = "autodetermina")
    private BigDecimal autodeterminata;

    @Column(name = "autodetermina_oneri")
    private BigDecimal autodeterminataOneri;

    @Column(name = "autodetermina_regione")
    private BigDecimal autodeterminataRegione;

}