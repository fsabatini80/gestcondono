package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the documenti_abuso database table.
 * 
 */
@Entity
@Table(name="documenti_abuso")
public class DocumentiAbuso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="iddocumenti_abuso", unique=true, nullable=false)
	private int iddocumentiAbuso;

    @Lob()
	private byte[] allegato;

    @Temporal( TemporalType.DATE)
	@Column(name="data_protocollo")
	private Date dataProtocollo;

	@Column(name="id_abuso")
	private BigInteger idAbuso;

	@Column(name="id_tipo_documento")
	private int idTipoDocumento;

	@Column(name="numero_protocollo", length=45)
	private String numeroProtocollo;

	private byte presente;

	private byte valido;

    public DocumentiAbuso() {
    }

	public int getIddocumentiAbuso() {
		return this.iddocumentiAbuso;
	}

	public void setIddocumentiAbuso(int iddocumentiAbuso) {
		this.iddocumentiAbuso = iddocumentiAbuso;
	}

	public byte[] getAllegato() {
		return this.allegato;
	}

	public void setAllegato(byte[] allegato) {
		this.allegato = allegato;
	}

	public Date getDataProtocollo() {
		return this.dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public BigInteger getIdAbuso() {
		return this.idAbuso;
	}

	public void setIdAbuso(BigInteger idAbuso) {
		this.idAbuso = idAbuso;
	}

	public int getIdTipoDocumento() {
		return this.idTipoDocumento;
	}

	public void setIdTipoDocumento(int idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public byte getPresente() {
		return this.presente;
	}

	public void setPresente(byte presente) {
		this.presente = presente;
	}

	public byte getValido() {
		return this.valido;
	}

	public void setValido(byte valido) {
		this.valido = valido;
	}

}