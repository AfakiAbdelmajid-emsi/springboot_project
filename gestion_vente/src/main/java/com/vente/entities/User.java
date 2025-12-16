package com.vente.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeUser;

    private String login;
    private String pass;  
    
    public User() {}

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

	public Long getCodeUser() {
		return codeUser;
	}

	public void setCodeUser(Long codeUser) {
		this.codeUser = codeUser;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}


}
