package com.vente.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // PK technique

    private Integer codeCmd;
    private String client;
    private Integer codePdt;
    private Integer qteCmd;
    private LocalDate dateCmd;

    public Commande() {}

    public Commande(Integer codeCmd, String client, Integer codePdt, Integer qteCmd, LocalDate dateCmd) {
        this.codeCmd = codeCmd;
        this.client = client;
        this.codePdt = codePdt;
        this.qteCmd = qteCmd;
        this.dateCmd = dateCmd;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodeCmd() {
		return codeCmd;
	}

	public void setCodeCmd(Integer codeCmd) {
		this.codeCmd = codeCmd;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Integer getCodePdt() {
		return codePdt;
	}

	public void setCodePdt(Integer codePdt) {
		this.codePdt = codePdt;
	}

	public Integer getQteCmd() {
		return qteCmd;
	}

	public void setQteCmd(Integer qteCmd) {
		this.qteCmd = qteCmd;
	}

	public LocalDate getDateCmd() {
		return dateCmd;
	}

	public void setDateCmd(LocalDate dateCmd) {
		this.dateCmd = dateCmd;
	}

    
}
