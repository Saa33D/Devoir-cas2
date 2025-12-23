package com.devoir.microserviceproduit.controller;

import com.devoir.microserviceproduit.model.Produit;
import com.devoir.microserviceproduit.repository.ProduitRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produits")
@Tag(name = "Produits", description = "API de gestion des produits")
public class ProduitController {
    private final ProduitRepository repo;
    public ProduitController(ProduitRepository repo) { this.repo = repo; }

    @GetMapping
    @Operation(summary = "Récupérer tous les produits", description = "Retourne la liste de tous les produits disponibles")
    @ApiResponse(responseCode = "200", description = "Liste des produits récupérée avec succès")
    public List<Produit> all() { 
        return repo.findAll(); 
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un produit par ID", description = "Retourne un produit spécifique par son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produit trouvé",
                     content = @Content(schema = @Schema(implementation = Produit.class))),
        @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    public ResponseEntity<Produit> get(
            @Parameter(description = "ID du produit", required = true) @PathVariable Long id) {
        Optional<Produit> p = repo.findById(id);
        return p.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau produit", description = "Crée un nouveau produit dans le système. L'ID sera généré automatiquement.")
    @ApiResponse(responseCode = "200", description = "Produit créé avec succès",
                 content = @Content(schema = @Schema(implementation = Produit.class)))
    public Produit create(@RequestBody Produit p) { 
        // Ignorer l'ID s'il est fourni (sera généré automatiquement)
        p.setId(null);
        return repo.save(p); 
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un produit", description = "Met à jour un produit existant par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produit modifié avec succès",
                     content = @Content(schema = @Schema(implementation = Produit.class))),
        @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    public ResponseEntity<Produit> update(
            @Parameter(description = "ID du produit", required = true) @PathVariable Long id, 
            @RequestBody Produit p) {
        return repo.findById(id).map(existing -> {
            existing.setDescription(p.getDescription());
            existing.setMontant(p.getMontant());
            existing.setQuantite(p.getQuantite());
            repo.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit", description = "Supprime un produit par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produit supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID du produit", required = true) @PathVariable Long id) {
        if (repo.existsById(id)) { 
            repo.deleteById(id); 
            return ResponseEntity.ok().build(); 
        }
        return ResponseEntity.notFound().build();
    }
}
