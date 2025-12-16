package com.commercial.entities;


import jakarta.persistence.*;

@Entity
public class ProduitPrix {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codePdt;
    private String nomPdt;
    private String descPdt;
    private Integer prixPdt;
    
    
	public ProduitPrix() {
		super();
	}
	public ProduitPrix(Integer codePdt, String nomPdt, String descPdt, Integer prixPdt) {
		super();
		this.codePdt = codePdt;
		this.nomPdt = nomPdt;
		this.descPdt = descPdt;
		this.prixPdt = prixPdt;
	}
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
}

