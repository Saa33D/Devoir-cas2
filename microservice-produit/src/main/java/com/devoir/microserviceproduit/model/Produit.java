package com.devoir.microserviceproduit.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Entité représentant un produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique du produit (généré automatiquement, ne pas fournir lors de la création)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @Schema(description = "Description du produit", example = "Produit A", required = true)
    private String description;
    
    @Schema(description = "Quantité en stock", example = "100", required = true)
    private Integer quantite;
    
    @Schema(description = "Prix unitaire en DHS", example = "50.10", required = true)
    private Double montant;
}
