# 📦 Flux de Stockage et Création des Produits et Commandes

## 🔄 Vue d'Ensemble du Flux

Le système utilise une architecture microservices où chaque entité (Produit, Commande) est gérée par un microservice indépendant avec sa propre base de données H2 en mémoire.

```
┌─────────────────────────────────────────────────────────────────┐
│                    FLUX COMPLET DE CRÉATION                      │
└─────────────────────────────────────────────────────────────────┘

1. INTERFACE UTILISATEUR (Client UI - Port 9090)
   │
   │ Utilisateur remplit le formulaire et clique sur "Créer"
   │
   ▼
2. CLIENT CONTROLLER (ClientController.java)
   │
   │ @PostMapping("/commandes") ou @PostMapping("/produits")
   │ - Reçoit les données du formulaire (@ModelAttribute)
   │ - Valide et prépare les données
   │
   ▼
3. FEIGN CLIENT (MicroserviceProxy.java)
   │
   │ proxy.createCommande(commande) ou proxy.createProduit(produit)
   │ - Fait un appel HTTP REST vers l'API Gateway
   │
   ▼
4. API GATEWAY (Port 8080)
   │
   │ Route configurée dans application.properties:
   │ - /commandes/** → lb://MICROSERVICE-COMMANDES
   │ - /produits/** → lb://MICROSERVICE-PRODUIT
   │ - Load balancing via Eureka
   │
   ▼
5. MICROSERVICE (Port 8081 ou 8082)
   │
   │ @PostMapping dans CommandeController ou ProduitController
   │ - Reçoit le JSON via @RequestBody
   │ - Appelle le Repository
   │
   ▼
6. SPRING DATA JPA (Repository)
   │
   │ service.save(commande) ou repo.save(produit)
   │ - Hibernate génère le SQL INSERT
   │
   ▼
7. BASE DE DONNÉES H2 (In-Memory)
   │
   │ - Table: COMMANDE ou PRODUIT
   │ - ID auto-généré
   │ - Données persistées en mémoire
   │
   ▼
8. RÉPONSE
   │
   │ Retourne l'entité créée avec son ID
   │ Remonte jusqu'à l'interface utilisateur
   │ Affiche un message de succès
```

---

## 📋 Détail du Flux pour une COMMANDE

### Étape 1 : Interface Utilisateur (Frontend)

**Fichier** : `accueil.html`

```html
<!-- Modal de création -->
<form th:action="@{/commandes}" method="post">
    <input type="text" name="description" required>
    <input type="number" name="montant" required>
    <input type="number" name="quantite" required>
    <input type="number" name="idProduit">
    <input type="date" name="dateCommande">
    <button type="submit">Créer</button>
</form>
```

**Action** : L'utilisateur remplit le formulaire et soumet → POST `/commandes`

---

### Étape 2 : Client Controller

**Fichier** : `ClientController.java`

```java
@PostMapping("/commandes")
public String createCommande(@ModelAttribute CommandeBean commande, 
                             RedirectAttributes redirectAttributes) {
    try {
        // Si la date n'est pas fournie, on utilise la date du jour
        if (commande.getDateCommande() == null) {
            commande.setDateCommande(LocalDate.now());
        }
        
        // Appel au microservice via Feign Client
        proxy.createCommande(commande);
        
        redirectAttributes.addFlashAttribute("success", 
            "Commande créée avec succès !");
        log.info("Commande créée: {}", commande);
    } catch (Exception e) {
        log.error("Erreur lors de la création: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("error", 
            "Erreur lors de la création de la commande");
    }
    return "redirect:/";
}
```

**Transformation** :
- `@ModelAttribute` convertit les paramètres du formulaire en `CommandeBean`
- Les champs du formulaire sont mappés automatiquement aux propriétés du bean

---

### Étape 3 : Feign Client (Proxy)

**Fichier** : `MicroserviceProxy.java`

```java
@FeignClient(name = "api-gateway", url = "${api.gateway.url:}")
public interface MicroserviceProxy {
    
    @PostMapping("/commandes")
    CommandeBean createCommande(@RequestBody CommandeBean commande);
}
```

**Action** :
- Feign génère automatiquement un client HTTP
- Fait un POST vers `http://localhost:8080/commandes`
- Convertit `CommandeBean` en JSON
- Envoie la requête HTTP

**Requête HTTP générée** :
```http
POST http://localhost:8080/commandes
Content-Type: application/json

{
  "description": "Commande de test",
  "montant": 250.50,
  "quantite": 5,
  "dateCommande": "2025-12-20",
  "idProduit": 1
}
```

---

### Étape 4 : API Gateway

**Fichier** : `api-gateway/src/main/resources/application.properties`

```properties
# Route pour Microservice Commandes
spring.cloud.gateway.routes[1].id=route-commandes
spring.cloud.gateway.routes[1].uri=lb://MICROSERVICE-COMMANDES
spring.cloud.gateway.routes[1].predicates[0]=Path=/commandes/**
```

**Action** :
1. Reçoit la requête POST `/commandes`
2. Consulte Eureka pour trouver une instance de `MICROSERVICE-COMMANDES`
3. Route la requête vers `http://localhost:8082/commandes` (via load balancing)
4. Applique le Circuit Breaker si configuré

**Load Balancing** :
- Si plusieurs instances du microservice sont disponibles, Eureka distribue la charge
- Utilise Spring Cloud Load Balancer

---

### Étape 5 : Microservice Commandes

**Fichier** : `CommandeController.java`

```java
@RestController
@RequestMapping("/commandes")
@RequiredArgsConstructor
public class CommandeController {
    
    private final CommandeRepository service;
    
    @PostMapping
    public Commande create(@RequestBody Commande c) {
        // Si la date n'est pas fournie, on utilise la date du jour
        if (c.getDateCommande() == null) {
            c.setDateCommande(LocalDate.now());
        }
        
        // Sauvegarde dans la base de données via JPA
        return service.save(c);
    }
}
```

**Transformation** :
- `@RequestBody` désérialise le JSON en objet `Commande`
- Spring Boot fait automatiquement la conversion JSON → Java Object

**Mapping JSON → Java** :
```json
{
  "description": "Commande de test",
  "montant": 250.50,
  "quantite": 5,
  "dateCommande": "2025-12-20",
  "idProduit": 1
}
```
↓
```java
Commande {
    id: null,  // Sera généré par la base de données
    description: "Commande de test",
    montant: 250.50,
    quantite: 5,
    dateCommande: LocalDate.of(2025, 12, 20),
    idProduit: 1L
}
```

---

### Étape 6 : Repository (Spring Data JPA)

**Fichier** : `CommandeRepository.java`

```java
@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    // Méthodes héritées automatiquement:
    // - save(Commande) → INSERT ou UPDATE
    // - findById(Long) → SELECT
    // - findAll() → SELECT *
    // - deleteById(Long) → DELETE
}
```

**Action** :
- `service.save(c)` déclenche Hibernate
- Hibernate génère automatiquement le SQL INSERT

---

### Étape 7 : Hibernate ORM

**Configuration** : `application.properties`

```properties
spring.datasource.url=jdbc:h2:mem:commandesdb
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**Action de Hibernate** :

1. **Création de la table** (au premier démarrage) :
   ```sql
   CREATE TABLE IF NOT EXISTS commande (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       description VARCHAR(255),
       quantite INTEGER,
       date_commande DATE,
       montant DOUBLE,
       id_produit BIGINT
   );
   ```

2. **Insertion des données** :
   ```sql
   INSERT INTO commande (description, quantite, date_commande, montant, id_produit)
   VALUES ('Commande de test', 5, '2025-12-20', 250.50, 1);
   ```

3. **Récupération de l'ID généré** :
   ```sql
   SELECT id FROM commande WHERE ...;  -- Hibernate récupère l'ID auto-généré
   ```

**Mapping Objet-Relationnel** :
- `@Entity` → Table `commande`
- `@Id @GeneratedValue` → Colonne `id` avec AUTO_INCREMENT
- `String description` → Colonne `description VARCHAR(255)`
- `LocalDate dateCommande` → Colonne `date_commande DATE`
- Hibernate convertit automatiquement les noms camelCase en snake_case

---

### Étape 8 : Base de Données H2

**Type** : Base de données en mémoire (In-Memory)

**Configuration** :
```properties
spring.datasource.url=jdbc:h2:mem:commandesdb
```

**Caractéristiques** :
- ✅ **En mémoire** : Les données sont stockées en RAM
- ✅ **Rapide** : Accès très rapide (pas d'I/O disque)
- ✅ **Éphémère** : Les données sont perdues au redémarrage de l'application
- ✅ **Parfaite pour le développement** : Pas besoin de configuration externe

**Structure de la table** :
```sql
COMMANDE
├── id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
├── description (VARCHAR(255))
├── quantite (INTEGER)
├── date_commande (DATE)
├── montant (DOUBLE)
└── id_produit (BIGINT)
```

**Données stockées** :
```
id | description        | quantite | date_commande | montant | id_produit
---|-------------------|----------|---------------|---------|------------
1  | Commande de test  | 5        | 2025-12-20    | 250.50  | 1
2  | Commande urgente  | 2        | 2025-12-20    | 99.99   | 3
```

---

### Étape 9 : Retour de la Réponse

**Flux de retour** :

```
H2 Database
    ↓ (Retourne l'entité avec ID)
Hibernate
    ↓ (Retourne Commande avec id=1)
Repository.save()
    ↓ (Retourne Commande)
CommandeController.create()
    ↓ (Retourne JSON)
API Gateway
    ↓ (Retourne JSON)
Feign Client
    ↓ (Désérialise en CommandeBean)
ClientController
    ↓ (Redirige avec message de succès)
Interface Utilisateur
    ↓ (Affiche "Commande créée avec succès !")
```

**Réponse JSON** :
```json
{
  "id": 1,
  "description": "Commande de test",
  "montant": 250.50,
  "quantite": 5,
  "dateCommande": "2025-12-20",
  "idProduit": 1
}
```

---

## 📋 Détail du Flux pour un PRODUIT

Le flux est **identique** à celui des commandes, mais avec :

1. **Microservice différent** : `microservice-produit` (Port 8081)
2. **Base de données différente** : `jdbc:h2:mem:produitdb`
3. **Table différente** : `produit` au lieu de `commande`
4. **Champs différents** : Pas de `dateCommande` ni `idProduit`

**Structure de la table PRODUIT** :
```sql
PRODUIT
├── id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
├── description (VARCHAR(255))
├── quantite (INTEGER)
└── montant (DOUBLE)
```

---

## 🔍 Points Importants à Comprendre

### 1. **Séparation des Bases de Données**

Chaque microservice a **sa propre base de données H2** :

- **Microservice Commandes** → `commandesdb` (Port 8082)
- **Microservice Produit** → `produitdb` (Port 8081)

**Avantages** :
- ✅ Isolation complète des données
- ✅ Chaque service peut évoluer indépendamment
- ✅ Pas de couplage entre les bases de données

**Inconvénient** :
- ⚠️ Les données sont perdues au redémarrage (H2 en mémoire)

### 2. **Génération Automatique des IDs**

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

- Hibernate génère automatiquement l'ID lors de l'insertion
- L'ID est retourné dans l'objet après `save()`

### 3. **Mapping Automatique**

Spring Boot fait automatiquement :
- **Formulaire HTML** → `CommandeBean` (via `@ModelAttribute`)
- **CommandeBean** → **JSON** (via Feign Client)
- **JSON** → **Commande** (via `@RequestBody`)
- **Commande** → **SQL INSERT** (via Hibernate)
- **SQL Result** → **Commande avec ID** (via Hibernate)

### 4. **Persistance en Mémoire**

**H2 In-Memory** signifie :
- Les données sont stockées en RAM
- **Perdues au redémarrage** de l'application
- Parfait pour le développement et les tests
- Pour la production, utiliser PostgreSQL ou MySQL

### 5. **Initialisation des Données**

**Au démarrage** :
- `DataInitializer.java` crée 4 commandes de test
- `data.sql` crée 3 produits de test

**Ces données sont créées automatiquement** si la base est vide.

---

## 🧪 Comment Vérifier le Stockage

### 1. Via l'Interface Web

1. Créer une commande/produit via le formulaire
2. Rafraîchir la page
3. La nouvelle entité apparaît dans la liste

### 2. Via la Console H2

**Pour Commandes** :
```
URL: http://localhost:8082/h2-console
JDBC URL: jdbc:h2:mem:commandesdb
Username: sa
Password: (vide)
```

**Pour Produits** :
```
URL: http://localhost:8081/h2-console
JDBC URL: jdbc:h2:mem:produitdb
Username: sa
Password: (vide)
```

**Requête SQL** :
```sql
SELECT * FROM commande;
SELECT * FROM produit;
```

### 3. Via les Logs

Avec `spring.jpa.show-sql=true`, vous verrez dans les logs :
```sql
Hibernate: insert into commande (date_commande, description, id_produit, montant, quantite) 
           values (?, ?, ?, ?, ?)
```

---

## 📊 Résumé du Flux

| Étape | Composant | Action | Format |
|-------|-----------|--------|--------|
| 1 | Interface HTML | Formulaire soumis | HTML Form |
| 2 | ClientController | Reçoit les données | CommandeBean |
| 3 | Feign Client | Appel HTTP | JSON |
| 4 | API Gateway | Routage | JSON |
| 5 | Microservice Controller | Reçoit la requête | Commande (Java) |
| 6 | Repository | Appelle JPA | Commande (Java) |
| 7 | Hibernate | Génère SQL | SQL INSERT |
| 8 | H2 Database | Stocke les données | Table SQL |
| 9 | Retour | Remonte la réponse | Commande avec ID |

---

## ✅ Conclusion

Les produits et commandes sont stockés dans des **bases de données H2 en mémoire** séparées :

1. **Création** : Via formulaire HTML → API Gateway → Microservice → H2
2. **Stockage** : Tables SQL créées automatiquement par Hibernate
3. **Persistance** : En mémoire (perdue au redémarrage)
4. **ID** : Généré automatiquement par la base de données

Le système est **entièrement fonctionnel** et les données sont **correctement persistées** pendant l'exécution de l'application.

