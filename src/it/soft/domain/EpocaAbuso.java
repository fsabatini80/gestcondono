package it.soft.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the epoca_abuso database table.
 * 
 */
@Entity
@Table(name = "epoca_abuso")
public class EpocaAbuso implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idepoca_abuso", unique = true, nullable = false)
    private int idepocaAbuso;

    @Column(name = "epoca_a", length = 10)
    private String epocaA;

    @Column(name = "epoca_da", length = 10)
    private String epocaDa;

    @OneToOne
    @JoinColumn(name = "id_legge_condono")
    private LeggiCondono leggeCondono;

    public EpocaAbuso() {
    }

    public int getIdepocaAbuso() {
	return this.idepocaAbuso;
    }

    public void setIdepocaAbuso(int idepocaAbuso) {
	this.idepocaAbuso = idepocaAbuso;
    }

    public String getEpocaA() {
	return this.epocaA;
    }

    public void setEpocaA(String epocaA) {
	this.epocaA = epocaA;
    }

    public String getEpocaDa() {
	return this.epocaDa;
    }

    public void setEpocaDa(String epocaDa) {
	this.epocaDa = epocaDa;
    }

    public LeggiCondono getLeggeCondono() {
	return this.leggeCondono;
    }

    public void setLeggeCondono(LeggiCondono leggeCondono) {
	this.leggeCondono = leggeCondono;
    }

    @Override
    public String toString() {
	String string = "";
	if (epocaDa.equals("01-01-1900"))
	    string = ("Fino al ").concat("01-01-1967");
	else
	    string = "Da ".concat(epocaDa).concat(" a ").concat(epocaA);
	return string;
    }

}