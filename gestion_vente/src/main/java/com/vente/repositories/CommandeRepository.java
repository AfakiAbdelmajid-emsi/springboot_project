package com.vente.repositories;

import com.vente.entities.Commande;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
	 Optional<Commande> findByCodeCmd(Integer codeCmd);
}
