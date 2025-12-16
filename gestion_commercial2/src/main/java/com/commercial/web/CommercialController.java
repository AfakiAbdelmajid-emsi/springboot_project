package com.commercial.web;

import com.commercial.entities.ProduitPrix;
import com.commercial.entities.TousCommandes;
import com.commercial.repositories.ProduitPrixRepository;
import com.commercial.repositories.TousCommandesRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/commercial")
@CrossOrigin("*")
public class CommercialController {

    private final ProduitPrixRepository prixRepo;
    private final TousCommandesRepository cmdRepo;

    public CommercialController(ProduitPrixRepository prixRepo,
                                TousCommandesRepository cmdRepo) {
        this.prixRepo = prixRepo;
        this.cmdRepo = cmdRepo;
    }

    // 🔹 Return all products with prices
    @GetMapping("/produits")
    public List<ProduitPrix> getProduits() {
        return prixRepo.findAll();
    }
    @PostMapping("/produits")
    public ProduitPrix createProduit(@RequestBody ProduitPrix produit) {
        return prixRepo.save(produit);
    }


    // 🔹 Add a command to Tous_commandes (called by gestion_vente)
    @PostMapping("/commandes")
    public TousCommandes addCommande(@RequestBody TousCommandes cmd) {
        return cmdRepo.save(cmd);
    }
}

