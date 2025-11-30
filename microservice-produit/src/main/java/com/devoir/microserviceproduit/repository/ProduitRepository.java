package com.devoir.microserviceproduit.repository;

import com.devoir.microserviceproduit.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long>{
}
