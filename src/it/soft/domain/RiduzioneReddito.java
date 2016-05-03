package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the riduzione_reddito database table.
 * 
 */
@Entity
@Table(name="riduzione_reddito")
public class RiduzioneReddito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idriduzione_reddito", unique=true, nullable=false)
	private int idriduzioneReddito;

	@Column(name="idtipo_riduzione")
	private int idtipoRiduzione;

	@Column(name="reddito_a", precision=10)
	private BigDecimal redditoA;

	@Column(name="reddito_da", precision=10)
	private BigDecimal redditoDa;

	@Column(precision=10)
	private BigDecimal riduzione;

	@Column(name="superfice_a", precision=10)
	private BigDecimal superficeA;

	@Column(name="superfice_da", precision=10)
	private BigDecimal superficeDa;

    public RiduzioneReddito() {
    }

	public int getIdriduzioneReddito() {
		return this.idriduzioneReddito;
	}

	public void setIdriduzioneReddito(int idriduzioneReddito) {
		this.idriduzioneReddito = idriduzioneReddito;
	}

	public int getIdtipoRiduzione() {
		return this.idtipoRiduzione;
	}

	public void setIdtipoRiduzione(int idtipoRiduzione) {
		this.idtipoRiduzione = idtipoRiduzione;
	}

	public BigDecimal getRedditoA() {
		return this.redditoA;
	}

	public void setRedditoA(BigDecimal redditoA) {
		this.redditoA = redditoA;
	}

	public BigDecimal getRedditoDa() {
		return this.redditoDa;
	}

	public void setRedditoDa(BigDecimal redditoDa) {
		this.redditoDa = redditoDa;
	}

	public BigDecimal getRiduzione() {
		return this.riduzione;
	}

	public void setRiduzione(BigDecimal riduzione) {
		this.riduzione = riduzione;
	}

	public BigDecimal getSuperficeA() {
		return this.superficeA;
	}

	public void setSuperficeA(BigDecimal superficeA) {
		this.superficeA = superficeA;
	}

	public BigDecimal getSuperficeDa() {
		return this.superficeDa;
	}

	public void setSuperficeDa(BigDecimal superficeDa) {
		this.superficeDa = superficeDa;
	}

}