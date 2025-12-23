package com.devoir.clientui.beans;

import lombok.*;

import java.time.LocalDate;

/**
 * DTO pour recevoir les données des commandes depuis l'API Gateway
 * Note: Ce n'est pas une entité JPA, juste un DTO pour Feign
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommandeBean {
    private Long id;
    private String description;
    private Integer quantite;
    private LocalDate dateCommande;
    private Double montant;
    private Long idProduit;
}
