package it.soft.web.pojo;

import it.soft.domain.DatiLocalizzazione;
import it.soft.domain.SoggettiAbuso;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data
class DatiAbusoPojo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String iddatiabuso;
    private String progressivo;
    private String dataUltimazioneLavori;
    private String tipologiaAbuso;
    private String descrizione;
    private String destinazioneUso;
    private String epocaAbuso;
    private String esenzioniPagamenti;
    private String idPratica;
    private String nonresidenziale;
    private String nonresidenzialeVuoto;
    private String numeroAddetti;
    private String reddito;
    private String riduzioni;
    private String superficeTotale;
    private String superficeUtile;
    private String tipoOpera;
    private String tipoReddito;
    private String volumeDirezionale;
    private String volumeTotale;
    private String volumeUtile;
    private DatiLocalizzazione localizzazione;
    private List<SoggettiAbuso> soggettiAbuso;
    private Double autodeterminata;
    private Double autodeterminataOneri;
    private Double autodeterminataRegione;
    private Double oblazioneCalcolata;

}
