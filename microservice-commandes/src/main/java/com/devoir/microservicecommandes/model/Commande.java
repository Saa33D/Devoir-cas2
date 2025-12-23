package com.devoir.microservicecommandes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Entité représentant une commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique de la commande (généré automatiquement, ne pas fournir lors de la création)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @Schema(description = "Description de la commande", example = "Commande de matériel informatique", required = true)
    private String description;
    
    @Schema(description = "Quantité commandée", example = "5", required = true)
    private Integer quantite;
    
    @Schema(description = "Date de la commande", example = "2025-12-20")
    private LocalDate dateCommande;
    
    @Schema(description = "Montant total de la commande en DHS", example = "250.50", required = true)
    private Double montant;
    
    @Schema(description = "Identifiant du produit associé", example = "1")
    private Long idProduit; // version 2
}
