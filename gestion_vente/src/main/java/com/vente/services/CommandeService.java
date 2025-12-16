package com.vente.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vente.entities.Commande;
import com.vente.repositories.CommandeRepository;

@Service
public class CommandeService {

    private final RestTemplate restTemplate;
    private final CommandeRepository commandeRepo;

    public CommandeService(RestTemplate restTemplate,
                           CommandeRepository commandeRepo) {
        this.restTemplate = restTemplate;
        this.commandeRepo = commandeRepo;
    }

    public void createCommande(Commande cmd) {

        // 1️⃣ subtract stock
        restTemplate.put(
            "http://localhost:8082/api/stock/subtract?codePdt=" +
            cmd.getCodePdt() + "&qteCmd=" + cmd.getQteCmd(),
            null
        );

        // 2️⃣ add to commercial Tous_commandes
        restTemplate.postForObject(
            "http://localhost:8081/api/commercial/commandes",
            cmd,
            Void.class
        );

        // 3️⃣ save locally
        commandeRepo.save(cmd);
    }
}
