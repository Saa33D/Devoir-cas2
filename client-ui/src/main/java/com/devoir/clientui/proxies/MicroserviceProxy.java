package com.devoir.clientui.proxies;

import com.devoir.clientui.beans.CommandeBean;
import com.devoir.clientui.beans.ProduitBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// On appelle le GATEWAY, pas les microservices directement !
// Si Eureka n'est pas disponible, utilise l'URL directe http://localhost:8080
@FeignClient(name = "api-gateway", url = "${api.gateway.url:}")
public interface MicroserviceProxy {

    // ========== COMMANDES ==========
    
    @GetMapping("/commandes")
    List<CommandeBean> getCommandes();

    @GetMapping("/commandes/{id}")
    CommandeBean getCommande(@PathVariable Long id);

    @PostMapping("/commandes")
    CommandeBean createCommande(@RequestBody CommandeBean commande);

    @PutMapping("/commandes/{id}")
    CommandeBean updateCommande(@PathVariable Long id, @RequestBody CommandeBean commande);

    @DeleteMapping("/commandes/{id}")
    void deleteCommande(@PathVariable Long id);

    // ========== PRODUITS ==========
    
    @GetMapping("/produits")
    List<ProduitBean> getProduits();

    @GetMapping("/produits/{id}")
    ProduitBean getProduit(@PathVariable Long id);

    @PostMapping("/produits")
    ProduitBean createProduit(@RequestBody ProduitBean produit);

    @PutMapping("/produits/{id}")
    ProduitBean updateProduit(@PathVariable Long id, @RequestBody ProduitBean produit);

    @DeleteMapping("/produits/{id}")
    void deleteProduit(@PathVariable Long id);
}
