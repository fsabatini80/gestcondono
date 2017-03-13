package it.soft.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * The persistent class for the dati_alloggio database table.
 * 
 */
@Entity
@Table(name = "dati_alloggio")
public @Data
class DatiAlloggio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iddati_alloggio", unique = true, nullable = false)
    private BigDecimal iddatiAlloggio;

    @Column(name = "caratteri_speciali", length = 45)
    private String caratteriSpeciali;

    @OneToOne
    @JoinColumn(name = "destinazione_uso")
    private TipologiaDestinazioneUso destinazioneUso;

    @OneToOne
    @JoinColumn(name = "id_abuso")
    private Datiabuso idAbuso;

    @Column(name = "id_pratica")
    private BigInteger idPratica;

    @Column(name = "superficie_accessoria", length = 45)
    private String superficieAccessoria;

    @Column(name = "superficie_utile", length = 45)
    private String superficieUtile;

    @OneToOne
    @JoinColumn(name = "tipologia_alloggio")
    private TipologiaAlloggio tipologiaAlloggio;

}