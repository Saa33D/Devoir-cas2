package com.devoir.microservicecommandes.controller;

import com.devoir.microservicecommandes.model.Commande;
import com.devoir.microservicecommandes.repository.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeRepository service;

    @Value("${mes-config-ms.commandes-last:10}")
    private int commandesLast;

    @GetMapping
    public List<Commande> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> get(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Commande create(@RequestBody Commande c) {
        if (c.getDateCommande() == null) c.setDateCommande(LocalDate.now());
        return service.save(c);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> update(@PathVariable Long id, @RequestBody Commande c) {
        return service.findById(id).map(existing -> {
            existing.setDescription(c.getDescription());
            existing.setMontant(c.getMontant());
            existing.setQuantite(c.getQuantite());
            existing.setDateCommande(c.getDateCommande());
            existing.setIdProduit(c.getIdProduit());
            service.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isPresent()) { service.deleteById(id); return ResponseEntity.ok().build(); }
        return ResponseEntity.notFound().build();
    }

    // retourne commandes des 'commandesLast' derniers jours (valeur via config)
    @GetMapping("/recent")
    public List<Commande> recent() {
        return service.findByDateCommandeAfter(LocalDate.now().minusDays(commandesLast));
    }

    // endpoint pour test Hystrix: récupère info produit via rest (proxy call) avec fallback
    @GetMapping("/{id}/produit-info")
    public Map<String, Object> produitInfo(@PathVariable Long id) {
        // try to return sample data (in real: call produit service via RestTemplate)
        // For example purpose we simulate normal behavior:
        return Collections.singletonMap("message", "Produit info would be fetched from microservice-produit");
    }

    @GetMapping("/test-timeout")
    public Map<String, String> testTimeout() throws InterruptedException {
        System.out.println("⏳ Début de la simulation de lenteur (5 secondes)...");

        // On dort pendant 5000ms (5s)
        // Le Gateway est configuré pour couper après 3s
        Thread.sleep(5000);

        return Collections.singletonMap("message", "Réponse normale (Si vous voyez ceci, le Circuit Breaker n'a pas marché !)");
    }

    public Map<String, Object> fallbackProduitInfo(Long id) {
        return Collections.singletonMap("message", "fallback: produit service unreachable or timeout");
    }
}
