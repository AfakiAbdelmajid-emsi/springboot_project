package com.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stock.entities.ProduitStock;

public interface ProduitStockRepository extends JpaRepository<ProduitStock, Long> {
    ProduitStock findByCodePdt(Integer codePdt);
}
