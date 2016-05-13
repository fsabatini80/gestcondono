package it.soft.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the documenti_abuso database table.
 * 
 */
@Entity
@Table(name = "documenti_abuso")
public class DocumentiAbuso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "iddocumenti_abuso", unique = true, nullable = false)
	private int iddocumentiAbuso;

	@Lob()
	private byte[] allegato;

	@Column(name = "data_protocollo")
	private String dataProtocollo;

	@OneToOne
	@JoinColumn(name = "id_abuso")
	private Datiabuso idAbuso;

	@OneToOne
	@JoinColumn(name = "id_tipo_documento")
	private TipologiaDocumento idTipoDocumento;

	@Column(name = "numero_protocollo", length = 45)
	private String numeroProtocollo;

	private boolean presente;

	private boolean valido;

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

	public String getDataProtocollo() {
		return this.dataProtocollo;
	}

	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public Datiabuso getIdAbuso() {
		return this.idAbuso;
	}

	public void setIdAbuso(Datiabuso idAbuso) {
		this.idAbuso = idAbuso;
	}

	public TipologiaDocumento getIdTipoDocumento() {
		return this.idTipoDocumento;
	}

	public void setIdTipoDocumento(TipologiaDocumento idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public boolean getPresente() {
		return this.presente;
	}

	public void setPresente(boolean presente) {
		this.presente = presente;
	}

	public boolean getValido() {
		return this.valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

}