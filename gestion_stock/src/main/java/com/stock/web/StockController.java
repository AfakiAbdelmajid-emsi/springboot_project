package com.stock.web;

import org.springframework.web.bind.annotation.*;

import com.stock.entities.ProduitStock;
import com.stock.repositories.ProduitStockRepository;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin("*")
public class StockController {

    private final ProduitStockRepository repo;

    public StockController(ProduitStockRepository repo) {
        this.repo = repo;
    }

    // 🔹 Return all stock items
    @GetMapping
    public List<ProduitStock> getAll() {
        return repo.findAll();
    }

    // 🔹 Subtract quantity (called by gestion_vente)
    @PutMapping("/subtract")
    public ProduitStock subtract(@RequestParam int codePdt,
                                 @RequestParam int qteCmd) {

        ProduitStock p = repo.findByCodePdt(codePdt);

        if (p == null) {
            throw new RuntimeException("Produit not found");
        }

        if (p.getQtePdt() < qteCmd) {
            throw new RuntimeException("Stock insuffisant");
        }

        p.setQtePdt(p.getQtePdt() - qteCmd);
        return repo.save(p);
    }
    @PostMapping
    public ProduitStock createStock(@RequestBody ProduitStock stock) {
        return repo.save(stock);
    }

}
