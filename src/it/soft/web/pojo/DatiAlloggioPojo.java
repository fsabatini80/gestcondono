package it.soft.web.pojo;

import java.io.Serializable;
import java.util.List;

public class DatiAlloggioPojo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String iddatiAlloggio;
	private List<String> caratteriSpeciali;
	private String destinazioneUso;
	private String idAbuso;
	private String idPratica;
	private String superficieAccessoria;
	private String superficieUtile;
	private String tipologiaAlloggio;

	public DatiAlloggioPojo() {
	}

	public String getIddatiAlloggio() {
		return this.iddatiAlloggio;
	}

	public void setIddatiAlloggio(String iddatiAlloggio) {
		this.iddatiAlloggio = iddatiAlloggio;
	}

	public List<String> getCaratteriSpeciali() {
		return this.caratteriSpeciali;
	}

	public void setCaratteriSpeciali(List<String> caratteriSpeciali) {
		this.caratteriSpeciali = caratteriSpeciali;
	}

	public String getDestinazioneUso() {
		return this.destinazioneUso;
	}

	public void setDestinazioneUso(String destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}

	public String getIdAbuso() {
		return this.idAbuso;
	}

	public void setIdAbuso(String idAbuso) {
		this.idAbuso = idAbuso;
	}

	public String getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(String idPratica) {
		this.idPratica = idPratica;
	}

	public String getSuperficieAccessoria() {
		return this.superficieAccessoria;
	}

	public void setSuperficieAccessoria(String superficieAccessoria) {
		this.superficieAccessoria = superficieAccessoria;
	}

	public String getSuperficieUtile() {
		return this.superficieUtile;
	}

	public void setSuperficieUtile(String superficieUtile) {
		this.superficieUtile = superficieUtile;
	}

	public String getTipologiaAlloggio() {
		return this.tipologiaAlloggio;
	}

	public void setTipologiaAlloggio(String tipologiaAlloggio) {
		this.tipologiaAlloggio = tipologiaAlloggio;
	}

}