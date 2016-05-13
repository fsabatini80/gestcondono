package it.soft.web.pojo;

import java.io.Serializable;

public class DocumentiAbusoPojo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int iddocumentiAbuso;
	private byte[] allegato;
	private String dataProtocollo;
	private String idAbuso;
	private String idTipoDocumento;
	private String tipo;
	private String numeroProtocollo;
	private boolean presente;
	private boolean valido;

	public int getIddocumentiAbuso() {
		return iddocumentiAbuso;
	}

	public void setIddocumentiAbuso(int iddocumentiAbuso) {
		this.iddocumentiAbuso = iddocumentiAbuso;
	}

	public byte[] getAllegato() {
		return allegato;
	}

	public void setAllegato(byte[] allegato) {
		this.allegato = allegato;
	}

	public String getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public String getIdAbuso() {
		return idAbuso;
	}

	public void setIdAbuso(String idAbuso) {
		this.idAbuso = idAbuso;
	}

	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public boolean isPresente() {
		return presente;
	}

	public void setPresente(boolean presente) {
		this.presente = presente;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
