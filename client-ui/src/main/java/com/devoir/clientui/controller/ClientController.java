package com.devoir.clientui.controller;

import com.devoir.clientui.beans.CommandeBean;
import com.devoir.clientui.beans.ProduitBean;
import com.devoir.clientui.proxies.MicroserviceProxy;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final MicroserviceProxy proxy;

    // ========== PAGE D'ACCUEIL ==========

    @GetMapping("/")
    public String accueil(Model model) {
        loadCommandes(model);
        loadProduits(model);
        return "accueil";
    }

    // ========== COMMANDES ==========
    
    @PostMapping("/commandes")
    public String createCommande(@ModelAttribute CommandeBean commande, RedirectAttributes redirectAttributes) {
        try {
            // Validation des champs obligatoires
            if (commande.getDescription() == null || commande.getDescription().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "La description est obligatoire.");
                return "redirect:/";
            }
            if (commande.getMontant() == null || commande.getMontant() <= 0) {
                redirectAttributes.addFlashAttribute("error", "Le montant doit être supérieur à 0.");
                return "redirect:/";
            }
            if (commande.getQuantite() == null || commande.getQuantite() <= 0) {
                redirectAttributes.addFlashAttribute("error", "La quantité doit être supérieure à 0.");
                return "redirect:/";
            }
            
            // Date automatique (toujours définie à la date du jour)
            commande.setDateCommande(LocalDate.now());
            
            // Création de la commande
            proxy.createCommande(commande);
            redirectAttributes.addFlashAttribute("success", "Commande créée avec succès !");
            log.info("Commande créée: {}", commande);
        } catch (FeignException.NotFound e) {
            log.error("Ressource non trouvée (404): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Le service n'a pas pu être trouvé. Vérifiez que les microservices sont démarrés.");
        } catch (FeignException.ServiceUnavailable e) {
            log.error("Service indisponible (503): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Le service est temporairement indisponible. Veuillez réessayer plus tard.");
        } catch (FeignException e) {
            log.error("Erreur Feign ({}): {}", e.status(), e.getMessage());
            redirectAttributes.addFlashAttribute("error", 
                String.format("Erreur lors de la création de la commande (code %d). Vérifiez que l'API Gateway est accessible.", e.status()));
        } catch (Exception e) {
            log.error("Erreur lors de la création de la commande: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Une erreur inattendue s'est produite lors de la création de la commande. Veuillez réessayer.");
        }
        return "redirect:/";
    }

    @PostMapping("/commandes/{id}")
    public String updateCommande(@PathVariable Long id, @ModelAttribute CommandeBean commande, RedirectAttributes redirectAttributes) {
        try {
            // Validation des champs obligatoires
            if (commande.getDescription() == null || commande.getDescription().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "La description est obligatoire.");
                return "redirect:/";
            }
            if (commande.getMontant() == null || commande.getMontant() <= 0) {
                redirectAttributes.addFlashAttribute("error", "Le montant doit être supérieur à 0.");
                return "redirect:/";
            }
            if (commande.getQuantite() == null || commande.getQuantite() <= 0) {
                redirectAttributes.addFlashAttribute("error", "La quantité doit être supérieure à 0.");
                return "redirect:/";
            }
            
            // Date automatique si non fournie
            if (commande.getDateCommande() == null) {
                commande.setDateCommande(LocalDate.now());
            }
            
            proxy.updateCommande(id, commande);
            redirectAttributes.addFlashAttribute("success", "Commande modifiée avec succès !");
            log.info("Commande modifiée: {}", id);
        } catch (FeignException.NotFound e) {
            log.error("Commande non trouvée (404): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "La commande à modifier n'existe pas.");
        } catch (FeignException.ServiceUnavailable e) {
            log.error("Service indisponible (503): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Le service est temporairement indisponible. Veuillez réessayer plus tard.");
        } catch (FeignException e) {
            log.error("Erreur Feign ({}): {}", e.status(), e.getMessage());
            redirectAttributes.addFlashAttribute("error", 
                String.format("Erreur lors de la modification de la commande (code %d).", e.status()));
        } catch (Exception e) {
            log.error("Erreur lors de la modification de la commande: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Une erreur inattendue s'est produite lors de la modification de la commande.");
        }
        return "redirect:/";
    }

    @PostMapping("/commandes/{id}/delete")
    public String deleteCommande(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            proxy.deleteCommande(id);
            redirectAttributes.addFlashAttribute("success", "Commande supprimée avec succès !");
            log.info("Commande supprimée: {}", id);
        } catch (FeignException.NotFound e) {
            log.error("Commande non trouvée (404): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "La commande à supprimer n'existe pas.");
        } catch (FeignException.ServiceUnavailable e) {
            log.error("Service indisponible (503): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Le service est temporairement indisponible. Veuillez réessayer plus tard.");
        } catch (FeignException e) {
            log.error("Erreur Feign ({}): {}", e.status(), e.getMessage());
            redirectAttributes.addFlashAttribute("error", 
                String.format("Erreur lors de la suppression de la commande (code %d).", e.status()));
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de la commande: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Une erreur inattendue s'est produite lors de la suppression de la commande.");
        }
        return "redirect:/";
    }

    // ========== PRODUITS ==========
    
    @PostMapping("/produits")
    public String createProduit(@ModelAttribute ProduitBean produit, RedirectAttributes redirectAttributes) {
        try {
            // Validation des champs obligatoires
            if (produit.getDescription() == null || produit.getDescription().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "La description est obligatoire.");
                return "redirect:/";
            }
            if (produit.getMontant() == null || produit.getMontant() <= 0) {
                redirectAttributes.addFlashAttribute("error", "Le prix unitaire doit être supérieur à 0.");
                return "redirect:/";
            }
            if (produit.getQuantite() == null || produit.getQuantite() < 0) {
                redirectAttributes.addFlashAttribute("error", "La quantité en stock doit être positive.");
                return "redirect:/";
            }
            
            proxy.createProduit(produit);
            redirectAttributes.addFlashAttribute("success", "Produit créé avec succès !");
            log.info("Produit créé: {}", produit);
        } catch (FeignException.NotFound e) {
            log.error("Ressource non trouvée (404): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Le service n'a pas pu être trouvé. Vérifiez que les microservices sont démarrés.");
        } catch (FeignException.ServiceUnavailable e) {
            log.error("Service indisponible (503): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Le service est temporairement indisponible. Veuillez réessayer plus tard.");
        } catch (FeignException e) {
            log.error("Erreur Feign ({}): {}", e.status(), e.getMessage());
            redirectAttributes.addFlashAttribute("error", 
                String.format("Erreur lors de la création du produit (code %d). Vérifiez que l'API Gateway est accessible.", e.status()));
        } catch (Exception e) {
            log.error("Erreur lors de la création du produit: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Une erreur inattendue s'est produite lors de la création du produit. Veuillez réessayer.");
        }
        return "redirect:/";
    }

    @PostMapping("/produits/{id}")
    public String updateProduit(@PathVariable Long id, @ModelAttribute ProduitBean produit, RedirectAttributes redirectAttributes) {
        try {
            // Validation des champs obligatoires
            if (produit.getDescription() == null || produit.getDescription().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "La description est obligatoire.");
                return "redirect:/";
            }
            if (produit.getMontant() == null || produit.getMontant() <= 0) {
                redirectAttributes.addFlashAttribute("error", "Le prix unitaire doit être supérieur à 0.");
                return "redirect:/";
            }
            if (produit.getQuantite() == null || produit.getQuantite() < 0) {
                redirectAttributes.addFlashAttribute("error", "La quantité en stock doit être positive.");
                return "redirect:/";
            }
            
            proxy.updateProduit(id, produit);
            redirectAttributes.addFlashAttribute("success", "Produit modifié avec succès !");
            log.info("Produit modifié: {}", id);
        } catch (FeignException.NotFound e) {
            log.error("Produit non trouvé (404): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Le produit à modifier n'existe pas.");
        } catch (FeignException.ServiceUnavailable e) {
            log.error("Service indisponible (503): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Le service est temporairement indisponible. Veuillez réessayer plus tard.");
        } catch (FeignException e) {
            log.error("Erreur Feign ({}): {}", e.status(), e.getMessage());
            redirectAttributes.addFlashAttribute("error", 
                String.format("Erreur lors de la modification du produit (code %d).", e.status()));
        } catch (Exception e) {
            log.error("Erreur lors de la modification du produit: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Une erreur inattendue s'est produite lors de la modification du produit.");
        }
        return "redirect:/";
    }

    @PostMapping("/produits/{id}/delete")
    public String deleteProduit(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            proxy.deleteProduit(id);
            redirectAttributes.addFlashAttribute("success", "Produit supprimé avec succès !");
            log.info("Produit supprimé: {}", id);
        } catch (FeignException.NotFound e) {
            log.error("Produit non trouvé (404): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Le produit à supprimer n'existe pas.");
        } catch (FeignException.ServiceUnavailable e) {
            log.error("Service indisponible (503): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Le service est temporairement indisponible. Veuillez réessayer plus tard.");
        } catch (FeignException e) {
            log.error("Erreur Feign ({}): {}", e.status(), e.getMessage());
            redirectAttributes.addFlashAttribute("error", 
                String.format("Erreur lors de la suppression du produit (code %d).", e.status()));
        } catch (Exception e) {
            log.error("Erreur lors de la suppression du produit: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Une erreur inattendue s'est produite lors de la suppression du produit.");
        }
        return "redirect:/";
    }

    // ========== MÉTHODES PRIVÉES ==========
    
    private void loadCommandes(Model model) {
        try {
            List<CommandeBean> commandes = proxy.getCommandes();
            
            // Calcul des statistiques
            if (commandes != null && !commandes.isEmpty()) {
                double totalMontant = commandes.stream()
                    .filter(c -> c.getMontant() != null)
                    .mapToDouble(CommandeBean::getMontant)
                    .sum();
                
                int totalQuantite = commandes.stream()
                    .filter(c -> c.getQuantite() != null)
                    .mapToInt(CommandeBean::getQuantite)
                    .sum();
                
                double moyenneMontant = totalMontant / commandes.size();
                
                model.addAttribute("totalMontant", totalMontant);
                model.addAttribute("totalQuantite", totalQuantite);
                model.addAttribute("moyenneMontant", moyenneMontant);
            } else {
                model.addAttribute("totalMontant", 0.0);
                model.addAttribute("totalQuantite", 0);
                model.addAttribute("moyenneMontant", 0.0);
            }
            
            model.addAttribute("commandes", commandes != null ? commandes : Collections.emptyList());
            log.info("Nombre de commandes récupérées: {}", commandes != null ? commandes.size() : 0);
        } catch (FeignException.NotFound e) {
            log.warn("Aucune ressource trouvée (404): {}", e.getMessage());
            model.addAttribute("commandes", Collections.emptyList());
            model.addAttribute("totalMontant", 0.0);
            model.addAttribute("totalQuantite", 0);
            model.addAttribute("moyenneMontant", 0.0);
        } catch (FeignException.ServiceUnavailable e) {
            log.error("Service indisponible (503): {}", e.getMessage());
            model.addAttribute("commandes", Collections.emptyList());
            model.addAttribute("totalMontant", 0.0);
            model.addAttribute("totalQuantite", 0);
            model.addAttribute("moyenneMontant", 0.0);
            model.addAttribute("error", "L'API Gateway ou les microservices ne sont pas disponibles.");
        } catch (Exception e) {
            log.error("Erreur lors de l'appel à l'API Gateway: {}", e.getMessage());
            model.addAttribute("commandes", Collections.emptyList());
            model.addAttribute("totalMontant", 0.0);
            model.addAttribute("totalQuantite", 0);
            model.addAttribute("moyenneMontant", 0.0);
            if (!model.containsAttribute("error")) {
                model.addAttribute("error", "Erreur lors de la récupération des données.");
            }
        }
    }

    private void loadProduits(Model model) {
        try {
            List<ProduitBean> produits = proxy.getProduits();
            
            // Calcul des statistiques produits
            if (produits != null && !produits.isEmpty()) {
                int totalStock = produits.stream()
                    .filter(p -> p.getQuantite() != null)
                    .mapToInt(ProduitBean::getQuantite)
                    .sum();
                
                double valeurStock = produits.stream()
                    .filter(p -> p.getMontant() != null && p.getQuantite() != null)
                    .mapToDouble(p -> p.getMontant() * p.getQuantite())
                    .sum();
                
                model.addAttribute("totalStock", totalStock);
                model.addAttribute("valeurStock", valeurStock);
            } else {
                model.addAttribute("totalStock", 0);
                model.addAttribute("valeurStock", 0.0);
            }
            
            model.addAttribute("produits", produits != null ? produits : Collections.emptyList());
            log.info("Nombre de produits récupérés: {}", produits != null ? produits.size() : 0);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des produits: {}", e.getMessage());
            model.addAttribute("produits", Collections.emptyList());
            model.addAttribute("totalStock", 0);
            model.addAttribute("valeurStock", 0.0);
        }
    }
}
