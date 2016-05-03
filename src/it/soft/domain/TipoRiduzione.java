package it.soft.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.math.BigDecimal;


/**
 * The persistent class for the tipo_riduzione database table.
 * 
 */
@Entity
@Table(name="tipo_riduzione")
public class TipoRiduzione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idtipo_riduzione", unique=true, nullable=false)
	private int idtipoRiduzione;

	@Column(name="coef_rid_oc", precision=10)
	private BigDecimal coefRidOc;

	@Column(name="coef_rid_ou", precision=10)
	private BigDecimal coefRidOu;

	@Column(length=145)
	private String descrizione;

	@Column(name="idlegge_condono")
	private BigInteger idleggeCondono;

    public TipoRiduzione() {
    }

	public int getIdtipoRiduzione() {
		return this.idtipoRiduzione;
	}

	public void setIdtipoRiduzione(int idtipoRiduzione) {
		this.idtipoRiduzione = idtipoRiduzione;
	}

	public BigDecimal getCoefRidOc() {
		return this.coefRidOc;
	}

	public void setCoefRidOc(BigDecimal coefRidOc) {
		this.coefRidOc = coefRidOc;
	}

	public BigDecimal getCoefRidOu() {
		return this.coefRidOu;
	}

	public void setCoefRidOu(BigDecimal coefRidOu) {
		this.coefRidOu = coefRidOu;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigInteger getIdleggeCondono() {
		return this.idleggeCondono;
	}

	public void setIdleggeCondono(BigInteger idleggeCondono) {
		this.idleggeCondono = idleggeCondono;
	}

}