package it.soft.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the datipratica database table.
 * 
 */
@Entity
@Table(name = "datipratica")
public class Datipratica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "iddatipratica", unique = true, nullable = false)
	private BigDecimal iddatipratica;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_domanda")
	private Date dataDomanda;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_protocollo")
	private Date dataProtocollo;

	private byte isvalid;

	@Column(name = "legge_condono", nullable = false, length = 45)
	private String leggeCondono;

	@Column(name = "numero_pratica", nullable = false, length = 45)
	private String numeroPratica;

	@Column(name = "numero_protocollo", nullable = false, length = 45)
	private String numeroProtocollo;

	@Column(name = "idUtente")
	private BigDecimal idUtente;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "idrichiedente")
	private Richiedente richiedente;

	public Datipratica() {
	}

	public BigDecimal getIddatipratica() {
		return this.iddatipratica;
	}

	public void setIddatipratica(BigDecimal iddatipratica) {
		this.iddatipratica = iddatipratica;
	}

	public Date getDataDomanda() {
		return this.dataDomanda;
	}

	public void setDataDomanda(Date dataDomanda) {
		this.dataDomanda = dataDomanda;
	}

	public Date getDataProtocollo() {
		return this.dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public byte getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(byte isvalid) {
		this.isvalid = isvalid;
	}

	public String getLeggeCondono() {
		return this.leggeCondono;
	}

	public void setLeggeCondono(String leggeCondono) {
		this.leggeCondono = leggeCondono;
	}

	public String getNumeroPratica() {
		return this.numeroPratica;
	}

	public void setNumeroPratica(String numeroPratica) {
		this.numeroPratica = numeroPratica;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public BigDecimal getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}

	public Richiedente getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(Richiedente richiedente) {
		this.richiedente = richiedente;
	}

}