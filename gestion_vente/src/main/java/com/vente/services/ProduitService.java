package com.vente.services;

import com.vente.dto.StockItemDTO;
import com.vente.dto.CreateProduitDTO;
import com.vente.dto.ProduitPrixDTO;
import com.vente.dto.ProduitViewDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ProduitService {

    private final RestTemplate restTemplate;

    public ProduitService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public void createProduit(CreateProduitDTO dto) {

        // 1️⃣ Create product in commercial
        ProduitPrixDTO produitPrix = new ProduitPrixDTO();
        produitPrix.setNomPdt(dto.getNomPdt());
        produitPrix.setDescPdt(dto.getDescPdt());
        produitPrix.setPrixPdt(dto.getPrixPdt());

        // 🔴 IMPORTANT: receive created product
        ProduitPrixDTO createdProduit = restTemplate.postForObject(
            "http://localhost:8081/api/commercial/produits",
            produitPrix,
            ProduitPrixDTO.class
        );

        // 2️⃣ Use GENERATED codePdt
        StockItemDTO stock = new StockItemDTO();
        stock.setCodePdt(createdProduit.getCodePdt());
        stock.setQtePdt(dto.getQtePdt());

        restTemplate.postForObject(
            "http://localhost:8082/api/stock",
            stock,
            Void.class
        );
    }


    public List<ProduitViewDTO> getProduitsPourAffichage() {

        StockItemDTO[] stockArray = restTemplate.getForObject(
                "http://localhost:8082/api/stock",
                StockItemDTO[].class
        );

        ProduitPrixDTO[] prixArray = restTemplate.getForObject(
                "http://localhost:8081/api/commercial/produits",
                ProduitPrixDTO[].class
        );

        if (stockArray == null) stockArray = new StockItemDTO[0];
        if (prixArray == null) prixArray = new ProduitPrixDTO[0];

        Map<Integer, ProduitPrixDTO> prixMap = new HashMap<>();
        for (ProduitPrixDTO p : prixArray) {
            prixMap.put(p.getCodePdt(), p);
        }

        List<ProduitViewDTO> result = new ArrayList<>();

        for (StockItemDTO s : stockArray) {
            ProduitPrixDTO info = prixMap.get(s.getCodePdt());
            if (info != null) {
                result.add(new ProduitViewDTO(
                        s.getCodePdt(),
                        info.getNomPdt(),
                        info.getPrixPdt(),
                        s.getQtePdt()
                ));
            }
        }
        return result;
    }
}
