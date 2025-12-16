package com.stock.entities;

import jakarta.persistence.*;

@Entity
public class ProduitStock {
    @Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Integer codePdt;
    private Integer qtePdt;
	public ProduitStock(Long id, Integer codePdt, Integer qtePdt) {
		super();
		this.id = id;
		this.codePdt = codePdt;
		this.qtePdt = qtePdt;
	}
	public ProduitStock() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCodePdt() {
		return codePdt;
	}
	public void setCodePdt(Integer codePdt) {
		this.codePdt = codePdt;
	}
	public Integer getQtePdt() {
		return qtePdt;
	}
	public void setQtePdt(Integer qtePdt) {
		this.qtePdt = qtePdt;
	}
	
}
	