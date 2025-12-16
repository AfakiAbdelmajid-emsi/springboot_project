package com.vente.dto;

public class CreateProduitDTO {

    private Integer codePdt;
    private String nomPdt;
    private String descPdt;
    private Integer prixPdt;
    private Integer qtePdt;
	public Integer getCodePdt() {
		return codePdt;
	}
	public void setCodePdt(Integer codePdt) {
		this.codePdt = codePdt;
	}
	public String getNomPdt() {
		return nomPdt;
	}
	public void setNomPdt(String nomPdt) {
		this.nomPdt = nomPdt;
	}
	public String getDescPdt() {
		return descPdt;
	}
	public void setDescPdt(String descPdt) {
		this.descPdt = descPdt;
	}
	public Integer getPrixPdt() {
		return prixPdt;
	}
	public void setPrixPdt(Integer prixPdt) {
		this.prixPdt = prixPdt;
	}
	public Integer getQtePdt() {
		return qtePdt;
	}
	public void setQtePdt(Integer qtePdt) {
		this.qtePdt = qtePdt;
	}

    // getters & setters
}
