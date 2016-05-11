package it.soft.web.pojo;

import java.io.Serializable;


public class TipologiaDocumentoPojo implements Serializable {
	private static final long serialVersionUID = 1L;


	private String[] docToAdd;


	public String[] getDocToAdd() {
		return docToAdd;
	}


	public void setDocToAdd(String[] docToAdd) {
		this.docToAdd = docToAdd;
	}
}