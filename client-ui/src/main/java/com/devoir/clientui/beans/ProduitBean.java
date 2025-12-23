package com.devoir.clientui.beans;

import lombok.*;

/**
 * DTO pour recevoir les données des produits depuis l'API Gateway
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProduitBean {
    private Long id;
    private String description;
    private Integer quantite;
    private Double montant;
}

