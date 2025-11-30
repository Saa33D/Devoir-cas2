package com.devoir.microserviceproduit.controller;

import com.devoir.microserviceproduit.model.Produit;
import com.devoir.microserviceproduit.repository.ProduitRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produits")
public class ProduitController {
    private final ProduitRepository repo;
    public ProduitController(ProduitRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Produit> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> get(@PathVariable Long id) {
        Optional<Produit> p = repo.findById(id);
        return p.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Produit create(@RequestBody Produit p) { return repo.save(p); }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> update(@PathVariable Long id, @RequestBody Produit p) {
        return repo.findById(id).map(existing -> {
            existing.setDescription(p.getDescription());
            existing.setMontant(p.getMontant());
            existing.setQuantite(p.getQuantite());
            repo.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) { repo.deleteById(id); return ResponseEntity.ok().build(); }
        return ResponseEntity.notFound().build();
    }
}
