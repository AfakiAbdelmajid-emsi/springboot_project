package com.vente.web;

import com.vente.entities.Commande;
import com.vente.repositories.CommandeRepository;
import com.vente.services.CommandeService;
import com.vente.services.PdfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/vente")
@CrossOrigin("*")
public class CommandeRestController {

    private final CommandeService commandeService;
    private final CommandeRepository commandeRepo;
    private final PdfService pdfService;

    public CommandeRestController(CommandeService commandeService,
                                  CommandeRepository commandeRepo,
                                  PdfService pdfService) {
        this.commandeService = commandeService;
        this.commandeRepo = commandeRepo;
        this.pdfService = pdfService;
    }

    @PostMapping("/commande")
    public ResponseEntity<String> createCommande(@RequestBody Commande cmd) {
        commandeService.createCommande(cmd);
        return ResponseEntity.ok("Commande créée avec succès");
    }

    @GetMapping("/facture/code/{codeCmd}")
    public ResponseEntity<byte[]> getFactureByCodeCmd(@PathVariable Integer codeCmd) throws Exception {

        Commande cmd = commandeRepo.findByCodeCmd(codeCmd)
                .orElseThrow(() -> new RuntimeException("Commande not found"));

        byte[] pdf = pdfService.generateInvoice(cmd);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition",
                        "attachment; filename=facture-" + codeCmd + ".pdf")
                .body(pdf);
    }
}
