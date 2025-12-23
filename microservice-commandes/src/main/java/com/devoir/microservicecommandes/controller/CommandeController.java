package com.devoir.microservicecommandes.controller;

import com.devoir.microservicecommandes.model.Commande;
import com.devoir.microservicecommandes.repository.CommandeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Commandes", description = "API de gestion des commandes")
public class CommandeController {

    private final CommandeRepository service;

    @Value("${mes-config-ms.commandes-last:10}")
    private int commandesLast;

    @GetMapping
    @Operation(summary = "Récupérer toutes les commandes", description = "Retourne la liste de toutes les commandes")
    @ApiResponse(responseCode = "200", description = "Liste des commandes récupérée avec succès")
    public List<Commande> all() { 
        return service.findAll(); 
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une commande par ID", description = "Retourne une commande spécifique par son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Commande trouvée", 
                     content = @Content(schema = @Schema(implementation = Commande.class))),
        @ApiResponse(responseCode = "404", description = "Commande non trouvée")
    })
    public ResponseEntity<Commande> get(
            @Parameter(description = "ID de la commande", required = true) @PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle commande", description = "Crée une nouvelle commande. L'ID sera généré automatiquement. La date sera automatiquement définie si non fournie.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Commande créée avec succès",
                     content = @Content(schema = @Schema(implementation = Commande.class)))
    })
    public Commande create(@RequestBody Commande c) {
        // Ignorer l'ID s'il est fourni (sera généré automatiquement)
        c.setId(null);
        if (c.getDateCommande() == null) c.setDateCommande(LocalDate.now());
        return service.save(c);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une commande", description = "Met à jour une commande existante par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Commande modifiée avec succès",
                     content = @Content(schema = @Schema(implementation = Commande.class))),
        @ApiResponse(responseCode = "404", description = "Commande non trouvée")
    })
    public ResponseEntity<Commande> update(
            @Parameter(description = "ID de la commande", required = true) @PathVariable Long id, 
            @RequestBody Commande c) {
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
    @Operation(summary = "Supprimer une commande", description = "Supprime une commande par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Commande supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Commande non trouvée")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la commande", required = true) @PathVariable Long id) {
        if (service.findById(id).isPresent()) { 
            service.deleteById(id); 
            return ResponseEntity.ok().build(); 
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/recent")
    @Operation(summary = "Récupérer les commandes récentes", description = "Retourne les commandes des N derniers jours (configurable)")
    @ApiResponse(responseCode = "200", description = "Liste des commandes récentes")
    public List<Commande> recent() {
        return service.findByDateCommandeAfter(LocalDate.now().minusDays(commandesLast));
    }

    @GetMapping("/{id}/produit-info")
    @Operation(summary = "Récupérer les informations du produit associé", description = "Retourne les informations du produit associé à la commande")
    @ApiResponse(responseCode = "200", description = "Informations du produit")
    public Map<String, Object> produitInfo(
            @Parameter(description = "ID de la commande", required = true) @PathVariable Long id) {
        return Collections.singletonMap("message", "Produit info would be fetched from microservice-produit");
    }

    @GetMapping("/test-timeout")
    @Operation(summary = "Test du Circuit Breaker", description = "Endpoint de test pour vérifier le fonctionnement du Circuit Breaker (simule une lenteur)")
    @ApiResponse(responseCode = "200", description = "Test du timeout")
    public Map<String, String> testTimeout() throws InterruptedException {
        System.out.println("⏳ Début de la simulation de lenteur (5 secondes)...");
        Thread.sleep(5000);
        return Collections.singletonMap("message", "Réponse normale (Si vous voyez ceci, le Circuit Breaker n'a pas marché !)");
    }

    public Map<String, Object> fallbackProduitInfo(Long id) {
        return Collections.singletonMap("message", "fallback: produit service unreachable or timeout");
    }
}
