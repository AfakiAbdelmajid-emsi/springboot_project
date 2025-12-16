package com.vente.web;

import com.vente.dto.CreateProduitDTO;
import com.vente.dto.ProduitViewDTO;
import com.vente.services.ProduitService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vente")
@CrossOrigin("*")
public class ProduitRestController {

    private final ProduitService produitService;

    public ProduitRestController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/produits")
    public List<ProduitViewDTO> getProduits() {
        return produitService.getProduitsPourAffichage();
    }
    @PostMapping("/produits")
    public ResponseEntity<String> createProduit(@RequestBody CreateProduitDTO dto) {
        produitService.createProduit(dto);
        return ResponseEntity.ok("Produit créé avec succès");
    }

}
