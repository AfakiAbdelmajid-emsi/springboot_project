package com.vente.dto;

public class ProduitViewDTO {

    private Integer codePdt;
    private String nomPdt;
    private Integer prixPdt;
    private Integer qteStock;

    // ✅ REQUIRED constructor
    public ProduitViewDTO(Integer codePdt, String nomPdt,
                          Integer prixPdt, Integer qteStock) {
        this.codePdt = codePdt;
        this.nomPdt = nomPdt;
        this.prixPdt = prixPdt;
        this.qteStock = qteStock;
    }

    // Default constructor (needed by Jackson)
    public ProduitViewDTO() {}

    public Integer getCodePdt() { return codePdt; }
    public void setCodePdt(Integer codePdt) { this.codePdt = codePdt; }

    public String getNomPdt() { return nomPdt; }
    public void setNomPdt(String nomPdt) { this.nomPdt = nomPdt; }

    public Integer getPrixPdt() { return prixPdt; }
    public void setPrixPdt(Integer prixPdt) { this.prixPdt = prixPdt; }

    public Integer getQteStock() { return qteStock; }
    public void setQteStock(Integer qteStock) { this.qteStock = qteStock; }
}
