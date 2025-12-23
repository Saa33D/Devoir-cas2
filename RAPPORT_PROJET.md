# RAPPORT DE PROJET
## Architecture Microservices avec Spring Cloud

**Auteur** : [Votre Nom]  
**Date** : Décembre 2025  
**Module** : JEE - Java Enterprise Edition  
**Projet** : Devoir Microservices N°1

---

# Table des Matières

1. [Introduction générale](#i-introduction-générale)
2. [Problématique et Objectifs du Projet](#ii-problématique-et-objectifs-du-projet)
   - 1. [Problématique](#1-problématique)
   - 2. [Objectifs spécifiques](#2-objectifs-spécifiques)
   - 3. [Architecture du projet](#3-architecture-du-projet)
3. [Réalisation](#iii-réalisation)
   - 1. [Outils et Technologies](#1-outils-et-technologies)
   - 2. [Développement](#2-développement)
   - 3. [Résultat](#3-résultat)
     - a. [Cas 1 : Gestion des Produits](#a-cas-1-gestion-des-produits)
     - b. [Cas 2 : Gestion des Commandes avec Interface Utilisateur](#b-cas-2-gestion-des-commandes-avec-interface-utilisateur)
4. [Conclusion](#iv-conclusion)

---

# I. Introduction générale

## Contexte

Dans le paysage actuel du développement logiciel, l'architecture monolithique traditionnelle présente de nombreuses limitations face aux exigences modernes des applications d'entreprise. Les applications monolithiques, bien qu'elles soient simples à développer initialement, deviennent rapidement difficiles à maintenir, à faire évoluer et à déployer à mesure qu'elles grandissent.

L'architecture microservices émerge comme une solution moderne et efficace pour répondre à ces défis. Cette approche architecturale décompose une application en un ensemble de services indépendants, faiblement couplés, chacun responsable d'une fonctionnalité métier spécifique. Chaque microservice peut être développé, déployé et mis à l'échelle indépendamment, offrant ainsi une flexibilité et une agilité accrues.

## Enjeux de l'Architecture Microservices

L'adoption d'une architecture microservices introduit cependant de nouveaux défis techniques :

1. **Service Discovery** : Comment les services se découvrent-ils et communiquent-ils entre eux ?
2. **Configuration Centralisée** : Comment gérer la configuration de multiples services de manière cohérente ?
3. **API Gateway** : Comment fournir un point d'entrée unique pour les clients tout en gérant le routage vers les services appropriés ?
4. **Résilience** : Comment garantir la disponibilité du système même en cas de défaillance d'un service ?
5. **Communication Inter-Services** : Comment faciliter la communication entre services de manière efficace et fiable ?

## Objectif du Projet

Ce projet vise à concevoir et implémenter une architecture microservices complète en utilisant l'écosystème **Spring Cloud**, qui fournit des outils et des patterns prêts à l'emploi pour résoudre ces défis. L'objectif est de créer un système de gestion de commandes et de produits, démontrant les concepts fondamentaux des microservices et leur mise en œuvre pratique.

---

# II. Problématique et Objectifs du Projet

## 1. Problématique

### 1.1. Limitations de l'Architecture Monolithique

Dans une architecture monolithique traditionnelle, une application est construite comme une unité unique et indivisible. Cette approche présente plusieurs limitations :

- **Couplage fort** : Toutes les fonctionnalités sont étroitement liées, rendant difficile la modification d'une partie sans affecter l'ensemble
- **Déploiement complexe** : Toute modification nécessite le redéploiement de l'application complète
- **Scalabilité limitée** : Impossible de mettre à l'échelle individuellement les composants qui nécessitent plus de ressources
- **Technologies figées** : Difficile d'adopter de nouvelles technologies pour des parties spécifiques de l'application
- **Risque de défaillance** : Une erreur dans un composant peut affecter l'ensemble du système

### 1.2. Besoins d'une Architecture Moderne

Pour répondre aux exigences des applications modernes, il est nécessaire de :

1. **Décomposer l'application** en services indépendants et spécialisés
2. **Faciliter la communication** entre ces services de manière transparente
3. **Centraliser la configuration** pour une gestion simplifiée
4. **Implémenter la résilience** pour garantir la disponibilité du système
5. **Fournir une interface unifiée** pour les clients externes

### 1.3. Défis Techniques à Résoudre

L'implémentation d'une architecture microservices nécessite de résoudre plusieurs défis techniques :

- **Découverte de services** : Mécanisme permettant aux services de se trouver et de communiquer
- **Routage intelligent** : Distribution des requêtes vers les instances appropriées
- **Gestion de la configuration** : Centralisation et externalisation de la configuration
- **Circuit Breaker** : Protection contre les cascades de défaillances
- **Documentation API** : Facilitation de l'intégration et de la compréhension des services

## 2. Objectifs spécifiques

### 2.1. Objectifs Techniques

1. **Implémenter un Service Discovery**
   - Mettre en place un serveur Eureka pour l'enregistrement et la découverte automatique des services
   - Permettre aux microservices de s'enregistrer et de découvrir d'autres services dynamiquement

2. **Créer un API Gateway**
   - Implémenter un point d'entrée unique pour toutes les requêtes client
   - Configurer le routage intelligent vers les microservices appropriés
   - Intégrer un circuit breaker pour la résilience

3. **Mettre en place un Config Server**
   - Centraliser la configuration de tous les microservices
   - Utiliser un dépôt Git comme source de configuration
   - Permettre la mise à jour dynamique de la configuration

4. **Développer des Microservices Métier**
   - Créer un microservice de gestion des produits
   - Créer un microservice de gestion des commandes
   - Implémenter des APIs REST complètes (CRUD)

5. **Créer une Interface Utilisateur**
   - Développer une interface web moderne pour visualiser les données
   - Intégrer la communication avec les microservices via l'API Gateway
   - Afficher des statistiques en temps réel

6. **Documenter les APIs**
   - Intégrer Swagger/OpenAPI dans chaque microservice
   - Aggréger la documentation dans l'API Gateway
   - Faciliter l'exploration et le test des APIs

### 2.2. Objectifs Pédagogiques

1. Comprendre les concepts fondamentaux de l'architecture microservices
2. Maîtriser l'écosystème Spring Cloud et ses composants
3. Apprendre à gérer la communication inter-services
4. Implémenter des patterns de résilience (Circuit Breaker)
5. Pratiquer le développement d'applications distribuées

### 2.3. Objectifs Fonctionnels

1. **Gestion des Produits**
   - Créer, lire, mettre à jour et supprimer des produits
   - Consulter la liste de tous les produits disponibles

2. **Gestion des Commandes**
   - Créer, lire, mettre à jour et supprimer des commandes
   - Consulter les commandes récentes
   - Associer des commandes à des produits

3. **Visualisation des Données**
   - Afficher toutes les commandes dans une interface moderne
   - Présenter des statistiques agrégées (total, moyenne, quantité)
   - Gérer les erreurs de manière élégante

## 3. Architecture du projet

### 3.1. Vue d'Ensemble de l'Architecture

L'architecture du projet suit le pattern microservices avec six composants principaux interconnectés. Le Client UI sert d'interface utilisateur web et communique avec l'API Gateway via des requêtes HTTP/REST. L'API Gateway agit comme point d'entrée unique, effectuant le routage intelligent vers les microservices appropriés en utilisant le service discovery Eureka. Les microservices Commandes et Produit sont des services métier indépendants, chacun possédant sa propre base de données H2 en mémoire. Tous les services s'enregistrent automatiquement dans le serveur Eureka pour la découverte de services. Le Config Server centralise la configuration de tous les microservices depuis un dépôt Git.

### 3.2. Description des Composants

#### 3.2.1. Eureka Server

Le serveur Eureka fonctionne sur le port 8761 et joue le rôle central de Service Discovery et Registration dans l'architecture. Il permet l'enregistrement automatique de tous les microservices au démarrage, facilitant ainsi leur découverte dynamique. Le serveur maintient un registre à jour de tous les services disponibles et surveille leur état de santé. Un dashboard web permet de visualiser en temps réel tous les services enregistrés, leurs instances et leur statut.

#### 3.2.2. Config Server

Le Config Server opère sur le port 8888 et centralise toute la configuration de l'écosystème microservices. Il récupère les fichiers de configuration depuis un dépôt Git distant, permettant ainsi une gestion versionnée et centralisée. Les microservices se connectent au Config Server au démarrage pour récupérer leur configuration spécifique. Cette approche facilite la gestion de différents environnements (développement, production) et permet la mise à jour dynamique de la configuration sans redémarrage des services.

#### 3.2.3. API Gateway

L'API Gateway, accessible sur le port 8080, constitue le point d'entrée unique pour toutes les requêtes client. Basé sur Spring Cloud Gateway, il effectue le routage intelligent vers les microservices appropriés en fonction du chemin de l'URL. Il intègre un mécanisme de load balancing automatique via Eureka, distribuant les requêtes entre plusieurs instances d'un même service si disponibles. Un Circuit Breaker basé sur Resilience4j protège le système contre les cascades de défaillances, avec un timeout configuré à 3 secondes. Le Gateway agrège également la documentation Swagger de tous les microservices, offrant une interface unifiée pour explorer et tester les APIs.

#### 3.2.4. Microservice Produit

Le microservice Produit écoute sur le port 8081 et est responsable de la gestion complète des produits dans le système. Il expose une API REST complète permettant les opérations CRUD (Create, Read, Update, Delete) sur les produits. Chaque produit contient une description, une quantité en stock et un montant unitaire. Le service utilise une base de données H2 en mémoire pour la persistance des données, isolée des autres microservices. L'API est entièrement documentée avec Swagger/OpenAPI, facilitant son intégration et son test. Le service s'enregistre automatiquement dans Eureka pour être découvert par les autres composants.

#### 3.2.5. Microservice Commandes

Le microservice Commandes fonctionne sur le port 8082 et gère toutes les opérations liées aux commandes. Il offre un CRUD complet pour les commandes, avec des fonctionnalités supplémentaires comme la récupération des commandes récentes. Chaque commande est associée à un produit via un identifiant, démontrant la communication inter-services. Le service possède également un endpoint de test pour valider le fonctionnement du Circuit Breaker. Comme le microservice Produit, il utilise H2 en mémoire et s'enregistre dans Eureka. La date de commande est automatiquement définie à la date du jour si non fournie lors de la création.

#### 3.2.6. Client UI

Le Client UI, accessible sur le port 9090, représente l'interface utilisateur web de l'application. Développé avec Thymeleaf et Bootstrap 5, il offre une expérience utilisateur moderne et responsive. L'application utilise Feign Client pour communiquer avec l'API Gateway, simplifiant les appels HTTP inter-services. L'interface affiche toutes les commandes dans un design attrayant avec des cartes individuelles, et calcule en temps réel des statistiques agrégées telles que le montant total, la quantité totale et la moyenne par commande. Une gestion d'erreurs robuste assure que l'utilisateur reçoit des messages clairs en cas de problème de communication avec les services backend.

### 3.3. Flux de Communication

#### 3.3.1. Flux de Requête Client

Lorsqu'un utilisateur accède à l'interface web, le Client UI envoie une requête via Feign Client à l'API Gateway. Le Gateway consulte ensuite Eureka pour découvrir le microservice approprié et route la requête vers celui-ci en utilisant le load balancing. Le microservice traite la requête, interroge sa base de données H2, et retourne la réponse. Cette réponse remonte ensuite via l'API Gateway jusqu'au Client UI, qui l'affiche à l'utilisateur final. Ce flux démontre la séparation des responsabilités et la communication découplée entre les composants.

#### 3.3.2. Flux de Configuration

Au démarrage, chaque microservice se connecte au Config Server pour récupérer sa configuration depuis le dépôt Git. Une fois la configuration chargée, le microservice s'enregistre dans Eureka avec ses informations de service. Eureka maintient alors un registre à jour de tous les services disponibles, permettant leur découverte dynamique par l'API Gateway et les autres services. Ce mécanisme assure une configuration centralisée et une découverte automatique des services.

### 3.4. Patterns Implémentés

L'architecture implémente plusieurs patterns essentiels des microservices. Le Service Discovery Pattern utilise Eureka pour permettre la découverte automatique des services sans configuration statique. L'API Gateway Pattern fournit un point d'entrée unique, simplifiant la communication client-serveur et centralisant les préoccupations transversales comme l'authentification et le routage. Le Circuit Breaker Pattern, implémenté avec Resilience4j, protège le système contre les cascades de défaillances en isolant les services défaillants. Le Configuration Server Pattern centralise la gestion de la configuration, facilitant la maintenance et le déploiement. Le Load Balancing Pattern distribue automatiquement les requêtes entre plusieurs instances d'un service pour améliorer les performances et la disponibilité. Enfin, le Repository Pattern abstrait l'accès aux données, simplifiant les opérations de persistance.

---

# III. Réalisation

## 1. Outils et Technologies

### 1.1. Stack Technique Principal

Le projet utilise Java 17 comme langage de programmation, une version LTS moderne offrant des fonctionnalités avancées telles que les Records, le Pattern Matching et les Text Blocks. Cette version apporte également des améliorations de performance significatives et un meilleur support des modules.

Spring Boot 3.4.0 constitue le framework principal, fournissant une auto-configuration intelligente qui réduit considérablement la configuration manuelle. Il intègre des serveurs embarqués comme Tomcat, permettant de déployer des applications autonomes sans serveur d'application externe. Les fonctionnalités production-ready comme Actuator et Metrics facilitent le monitoring et la gestion en production.

L'écosystème Spring Cloud 2024.0.0 fournit une suite complète d'outils spécialement conçus pour les microservices. Spring Cloud Netflix (Eureka) gère la découverte de services, Spring Cloud Gateway implémente le routage et la résilience, Spring Cloud Config centralise la configuration, Spring Cloud OpenFeign simplifie la communication inter-services, et Spring Cloud Load Balancer distribue la charge entre les instances.

### 1.2. Technologies de Persistance

Spring Data JPA offre une abstraction puissante de l'accès aux données, réduisant considérablement le code boilerplate nécessaire pour les opérations CRUD. Il génère automatiquement les requêtes basiques et fournit un support complet des transactions, simplifiant la gestion de la persistance.

Hibernate, en tant qu'ORM (Object-Relational Mapping), gère automatiquement le mapping entre les objets Java et les tables de base de données. Il prend en charge la gestion des relations entre entités et implémente un cache de premier niveau pour optimiser les performances.

H2 Database, une base de données relationnelle en mémoire, est parfaite pour le développement et les tests. Légère et rapide, elle permet un démarrage instantané et offre une console web pour inspecter les données en temps réel.

### 1.3. Outils de Build et Dépendances

Maven sert de gestionnaire de dépendances et d'outil de build, offrant une gestion centralisée des dépendances et un cycle de vie standardisé. Il fournit des plugins pour la compilation, les tests et le packaging, facilitant la construction et le déploiement des applications.

Lombok réduit drastiquement le code boilerplate en générant automatiquement les getters, setters, constructeurs et builders à la compilation. Cette approche améliore la lisibilité du code tout en réduisant la maintenance.

### 1.4. Outils de Documentation et Monitoring

SpringDoc OpenAPI génère automatiquement la documentation Swagger pour toutes les APIs REST. Il crée une interface Swagger UI interactive permettant d'explorer et de tester les endpoints directement depuis le navigateur, avec un support complet d'OpenAPI 3.0.

Spring Boot Actuator fournit des endpoints de monitoring et de métriques pour surveiller l'état de santé des applications, collecter des métriques de performance et exposer des informations sur l'environnement d'exécution.

### 1.5. Résilience et Communication

Resilience4j est une bibliothèque moderne de résilience offrant plusieurs mécanismes de protection : Circuit Breaker pour isoler les services défaillants, Time Limiter pour gérer les timeouts, Retry pour les nouvelles tentatives automatiques, et Rate Limiter pour contrôler le débit des requêtes.

Spring Cloud OpenFeign fournit un client REST déclaratif qui simplifie grandement les appels HTTP inter-services. Il s'intègre nativement avec Eureka pour la découverte de services et supporte le load balancing automatique.

Spring Cloud Gateway, basé sur Spring WebFlux, offre un API Gateway réactif et performant. Il permet de configurer des filtres et des prédicats de manière déclarative et s'intègre parfaitement avec le Circuit Breaker pour la résilience.

### 1.6. Technologies Frontend

Thymeleaf, moteur de template côté serveur, s'intègre nativement avec Spring et offre une syntaxe HTML naturelle. Il supporte les expressions Spring EL, permettant une intégration transparente entre le backend et le frontend.

Bootstrap 5 fournit un framework CSS moderne avec un design responsive par défaut. Il offre une large collection de composants UI prêts à l'emploi et une grille flexible pour créer des interfaces adaptatives.

Font Awesome apporte une bibliothèque complète d'icônes vectorielles, facilement intégrable et offrant une grande variété d'icônes pour enrichir l'interface utilisateur.

Les animations CSS3 personnalisées créent des transitions fluides et des effets visuels modernes, améliorant l'expérience utilisateur tout en maintenant des performances optimales grâce à l'accélération matérielle.

### 1.7. Outils de Développement

IntelliJ IDEA, en tant qu'IDE de développement, offre un support complet de Spring avec des fonctionnalités de débogage avancées et un refactoring intelligent qui facilite la maintenance du code.

Git permet le contrôle de version du code source, la gestion de l'historique des modifications et la collaboration en équipe.

Le Maven Wrapper intégré garantit que tous les développeurs utilisent la même version de Maven, éliminant les problèmes de compatibilité.

### 1.8. Architecture de Déploiement

Chaque microservice est un JAR exécutable indépendant, permettant un déploiement autonome. Chaque service écoute sur un port dédié, facilitant l'identification et la configuration. La configuration est externalisée via le Config Server, permettant des mises à jour sans redéploiement. Le Service Discovery via Eureka permet la découverte automatique des services sans configuration statique.

## 2. Développement

### 2.1. Phase 1 : Infrastructure de Base

#### 2.1.1. Création du Serveur Eureka

Le serveur Eureka a été créé comme premier composant de l'infrastructure, car il est essentiel pour la découverte des services. Son rôle principal est de maintenir un registre centralisé de tous les microservices disponibles dans l'écosystème. Lorsqu'un microservice démarre, il s'enregistre automatiquement auprès d'Eureka en fournissant son nom, son adresse et son port. Eureka maintient alors ce registre à jour en surveillant périodiquement l'état de santé de chaque service. Les autres composants, notamment l'API Gateway, consultent Eureka pour découvrir dynamiquement les services disponibles sans avoir besoin de configuration statique. Un dashboard web permet de visualiser en temps réel tous les services enregistrés, leurs instances et leur statut de santé.

#### 2.1.2. Création du Config Server

Le Config Server joue le rôle de centralisateur de configuration pour tous les microservices. Il récupère les fichiers de configuration depuis un dépôt Git distant, permettant ainsi une gestion versionnée et centralisée de toute la configuration. Lorsqu'un microservice démarre, il se connecte au Config Server pour récupérer sa configuration spécifique en fonction de son nom d'application et de son profil. Cette approche offre plusieurs avantages : la configuration est versionnée dans Git, elle peut être mise à jour sans redéploiement des services (avec l'annotation @RefreshScope), et elle permet de gérer facilement différents environnements (développement, production) en utilisant des branches ou des profils différents. Le Config Server supporte également le chiffrement des valeurs sensibles pour améliorer la sécurité.

#### 2.1.3. Création de l'API Gateway

L'API Gateway a été configuré pour servir de point d'entrée unique et intelligent pour toutes les requêtes client. Son rôle principal est de router les requêtes vers les microservices appropriés en fonction du chemin de l'URL. Par exemple, les requêtes vers `/commandes/**` sont routées vers le microservice Commandes, tandis que les requêtes vers `/produits/**` sont dirigées vers le microservice Produit. Le Gateway utilise Eureka pour découvrir dynamiquement les instances disponibles de chaque service et applique automatiquement le load balancing pour distribuer les requêtes. Un Circuit Breaker basé sur Resilience4j a été intégré pour protéger le système : si un service ne répond pas dans un délai de 3 secondes, le Circuit Breaker intercepte la requête et peut retourner une réponse de fallback. Le Gateway agrège également la documentation Swagger de tous les microservices, offrant une interface unifiée pour explorer et tester toutes les APIs disponibles.

### 2.2. Phase 2 : Développement des Microservices Métier

#### 2.2.1. Microservice Produit

Le microservice Produit a été développé pour gérer toutes les opérations liées aux produits dans le système. Son rôle est de fournir une API REST complète permettant de créer, lire, mettre à jour et supprimer des produits. Chaque produit est modélisé avec un identifiant unique généré automatiquement, une description, une quantité en stock et un montant unitaire. Le service utilise Spring Data JPA pour simplifier l'accès aux données, avec un repository qui étend JpaRepository pour bénéficier automatiquement des méthodes CRUD de base. Le contrôleur REST expose cinq endpoints principaux : GET pour récupérer tous les produits ou un produit spécifique, POST pour créer un nouveau produit (en ignorant l'ID fourni pour éviter les conflits), PUT pour mettre à jour un produit existant, et DELETE pour supprimer un produit. Le service s'enregistre automatiquement dans Eureka au démarrage et expose sa documentation Swagger pour faciliter l'intégration et les tests.

#### 2.2.2. Microservice Commandes

Le microservice Commandes gère toutes les opérations liées aux commandes dans le système. Son rôle est similaire au microservice Produit mais avec des fonctionnalités supplémentaires. Chaque commande contient un identifiant unique, une description, une quantité, une date de commande, un montant total et une référence à un produit via un identifiant. Le service expose une API REST complète avec les opérations CRUD standard, mais inclut également un endpoint spécial `/recent` qui retourne les commandes des N derniers jours, où N est configurable via le Config Server. Un endpoint de test `/test-timeout` simule une lenteur pour valider le fonctionnement du Circuit Breaker dans l'API Gateway. Lors de la création d'une commande, si aucune date n'est fournie, elle est automatiquement définie à la date du jour. Le service utilise également Spring Data JPA avec un repository personnalisé qui inclut une méthode pour rechercher les commandes par date. Comme le microservice Produit, il s'enregistre dans Eureka et expose sa documentation Swagger.

### 2.3. Phase 3 : Développement de l'Interface Utilisateur

#### 2.3.1. Configuration Feign Client

Le Client UI utilise Spring Cloud OpenFeign pour simplifier la communication avec l'API Gateway. Le rôle de Feign Client est de fournir une interface déclarative qui masque la complexité des appels HTTP. Au lieu d'écrire du code pour construire des requêtes HTTP manuellement, une interface Java simple est définie avec des annotations qui décrivent les endpoints à appeler. Feign génère automatiquement l'implémentation de ces appels HTTP, gère la sérialisation/désérialisation JSON, et s'intègre avec Eureka pour la découverte de services. Si Eureka n'est pas disponible, une URL de fallback directe vers l'API Gateway peut être configurée, assurant ainsi la résilience de l'application.

#### 2.3.2. Contrôleur Web

Le contrôleur web joue le rôle d'intermédiaire entre l'interface utilisateur et les services backend. Il reçoit les requêtes HTTP du navigateur, appelle les services appropriés via Feign Client, traite les données reçues, et prépare le modèle pour l'affichage dans la vue Thymeleaf. Le contrôleur implémente une gestion d'erreurs robuste qui capture différents types d'exceptions (service non trouvé, service indisponible, erreurs génériques) et fournit des messages appropriés à l'utilisateur. Il calcule également des statistiques agrégées en temps réel à partir des données reçues, telles que le montant total, la quantité totale et la moyenne par commande, avant de les transmettre à la vue.

#### 2.3.3. Interface Utilisateur Moderne

L'interface utilisateur a été conçue avec un focus sur l'expérience utilisateur et l'esthétique moderne. Le template Thymeleaf structure le contenu HTML avec des composants Bootstrap 5 pour un design responsive qui s'adapte automatiquement aux différentes tailles d'écran. Des cartes de statistiques avec des dégradés colorés présentent les métriques clés de manière visuellement attrayante. Une grille responsive affiche les commandes dans des cartes individuelles, chacune contenant toutes les informations pertinentes. Des animations CSS fluides améliorent l'expérience utilisateur en ajoutant des transitions lors du chargement et de l'interaction. Le CSS personnalisé définit une palette de couleurs moderne avec des dégradés, des ombres portées pour la profondeur, et une typographie soignée utilisant la police Poppins pour un rendu professionnel.

### 2.4. Phase 4 : Configuration et Documentation

#### 2.4.1. Configuration Swagger Complète

La documentation API a été intégrée dans chaque microservice en utilisant SpringDoc OpenAPI. Le rôle de cette configuration est de générer automatiquement une documentation interactive et à jour de toutes les APIs REST. Chaque contrôleur est annoté avec des annotations OpenAPI qui décrivent les endpoints, leurs paramètres, leurs réponses possibles et fournissent des exemples. Une configuration OpenAPI personnalisée définit les serveurs disponibles (API Gateway et accès direct), permettant aux utilisateurs de Swagger UI de choisir comment tester les APIs. Une configuration CORS permet également de tester les APIs directement depuis Swagger UI sans erreurs de cross-origin. Dans l'API Gateway, la documentation de tous les microservices est agrégée, offrant une vue unifiée de toutes les APIs disponibles dans l'écosystème.

#### 2.4.2. Configuration Actuator

Spring Boot Actuator a été configuré pour exposer des endpoints de monitoring et de gestion. Le rôle d'Actuator est de fournir des informations sur l'état de santé de l'application, ses métriques de performance, ses variables d'environnement et bien plus encore. Ces endpoints permettent de surveiller l'application en production, de diagnostiquer les problèmes et de comprendre le comportement du système. Les endpoints de santé sont particulièrement importants car Eureka les utilise pour déterminer si un service est disponible ou non.

## 3. Résultat

### a. Cas 1 : Gestion des Produits

#### Description du Cas

Le premier cas d'usage démontre la gestion complète des produits dans le système. Ce microservice illustre les fonctionnalités fondamentales d'un microservice : opérations CRUD complètes, persistance des données dans une base isolée, et exposition via une API REST documentée.

#### Fonctionnalités Implémentées

Le microservice Produit expose cinq endpoints principaux pour la gestion complète des produits. L'endpoint de création permet d'ajouter un nouveau produit en fournissant sa description, sa quantité et son montant, l'identifiant étant généré automatiquement. L'endpoint de consultation retourne soit la liste complète de tous les produits, soit un produit spécifique identifié par son ID. L'endpoint de modification permet de mettre à jour les informations d'un produit existant, tandis que l'endpoint de suppression permet de retirer un produit du système. Toutes ces opérations sont accessibles directement via le microservice ou via l'API Gateway, qui route automatiquement les requêtes et applique le load balancing si plusieurs instances sont disponibles.

#### Accès via API Gateway

Toutes les opérations sur les produits sont accessibles via l'API Gateway, qui agit comme point d'entrée unique. Le Gateway route automatiquement les requêtes vers le microservice Produit en utilisant la découverte de services via Eureka. Si plusieurs instances du microservice sont disponibles, le Gateway distribue les requêtes entre elles pour améliorer les performances et la disponibilité.

#### Documentation Swagger

La documentation Swagger est accessible à deux niveaux : directement sur le microservice pour une vue détaillée, et via l'API Gateway pour une vue agrégée de tous les services. L'interface Swagger UI permet d'explorer interactivement tous les endpoints, de voir leur documentation complète avec des exemples, et de tester directement les APIs depuis le navigateur.

#### Données de Test

Trois produits sont initialisés automatiquement au démarrage du microservice pour faciliter les tests et la démonstration. Ces produits représentent différents scénarios avec des quantités et des montants variés.

#### Résultats Obtenus

Le microservice Produit fonctionne de manière autonome et indépendante, avec sa propre base de données H2 isolée. Il s'intègre parfaitement avec l'écosystème en s'enregistrant automatiquement dans Eureka et en étant accessible via l'API Gateway. La configuration est centralisée via le Config Server, et la documentation Swagger facilite grandement l'intégration et les tests.

### b. Cas 2 : Gestion des Commandes avec Interface Utilisateur

#### Description du Cas

Le deuxième cas d'usage est plus complexe et démontre l'intégration complète de l'architecture microservices. Il combine un microservice de gestion des commandes, la communication via l'API Gateway, une interface utilisateur moderne pour la visualisation, et le calcul de statistiques en temps réel.

#### Fonctionnalités Implémentées

##### 1. API REST des Commandes

Le microservice Commandes expose une API REST complète avec les opérations CRUD standard, plus des fonctionnalités supplémentaires. L'endpoint de création permet d'ajouter une nouvelle commande, avec génération automatique de l'ID et définition automatique de la date si non fournie. L'endpoint de consultation retourne toutes les commandes ou une commande spécifique. L'endpoint de modification permet de mettre à jour une commande existante, et l'endpoint de suppression permet de retirer une commande. Un endpoint spécial `/recent` retourne les commandes des N derniers jours, où N est configurable via le Config Server. Un endpoint de test `/test-timeout` simule une lenteur pour valider le fonctionnement du Circuit Breaker.

##### 2. Interface Utilisateur Web

L'interface utilisateur web offre une expérience moderne et intuitive pour visualiser et gérer les commandes. La page d'accueil affiche des statistiques en temps réel calculées à partir des données : le nombre total de commandes, le montant total, la quantité totale et la moyenne par commande. Ces statistiques sont présentées dans des cartes colorées avec des dégradés pour un rendu visuellement attrayant. Les commandes sont affichées dans une grille responsive avec des cartes individuelles contenant toutes les informations pertinentes : identifiant, description, montant formaté, quantité, date formatée et référence au produit. Le design est entièrement responsive, s'adaptant parfaitement aux écrans mobiles, tablettes et desktop. Une gestion d'erreurs robuste assure que l'utilisateur reçoit des messages clairs et informatifs en cas de problème de communication avec les services backend.

##### 3. Communication Inter-Services

Le flux de communication démontre la puissance de l'architecture microservices. Le client (navigateur) envoie une requête au Client UI, qui utilise Feign Client pour appeler l'API Gateway. Le Gateway consulte Eureka pour découvrir le microservice Commandes, route la requête avec load balancing, et le microservice interroge sa base de données H2. La réponse remonte ensuite via le même chemin jusqu'au client. Ce flux illustre les avantages de l'architecture : découverte automatique des services, load balancing transparent, résilience avec Circuit Breaker, et point d'entrée unique simplifiant la communication.

##### 4. Données de Test

Quatre commandes sont initialisées automatiquement au démarrage pour faciliter les tests et la démonstration. Ces commandes représentent différents scénarios avec des quantités, des montants et des produits variés.

#### Résultats Obtenus

Le microservice Commandes fonctionne parfaitement avec un CRUD complet opérationnel et des endpoints supplémentaires. L'interface utilisateur offre un design moderne et attrayant avec des animations fluides, des statistiques calculées en temps réel et un design entièrement responsive. L'intégration complète fonctionne de manière fluide via l'API Gateway avec un Service Discovery fonctionnel et un Circuit Breaker testé et opérationnel. L'expérience utilisateur est optimale avec un chargement rapide des données, un feedback visuel immédiat et des messages d'erreur clairs et informatifs.

#### Captures d'Écran

*[Les captures d'écran seront insérées ici pour illustrer :]*

- **Page d'Accueil** : Interface utilisateur avec statistiques et liste des commandes
- **Swagger UI** : Documentation interactive des APIs
- **Eureka Dashboard** : Visualisation des services enregistrés
- **Interface de Gestion** : CRUD complet pour produits et commandes

#### Tests Effectués

Des tests complets ont été effectués pour valider toutes les fonctionnalités : création et consultation de commandes via l'API Gateway, affichage correct dans l'interface utilisateur, calcul précis des statistiques, fonctionnement du Circuit Breaker avec interception des timeouts, distribution automatique des requêtes via load balancing, découverte automatique des services via Eureka, et gestion appropriée des erreurs avec messages clairs pour l'utilisateur.

---

# IV. Conclusion

## 4.1. Bilan du Projet

Ce projet a permis de mettre en pratique les concepts fondamentaux de l'architecture microservices en utilisant l'écosystème Spring Cloud. L'objectif principal de créer un système de gestion de commandes et de produits basé sur une architecture microservices a été atteint avec succès.

### Réalisations Principales

L'architecture microservices complète comprend six services indépendants développés et fonctionnels, avec une séparation claire des responsabilités et des services déployables indépendamment. L'infrastructure de base est opérationnelle avec un Service Discovery (Eureka) fonctionnel, une configuration centralisée (Config Server) opérationnelle, et un API Gateway avec routage intelligent et résilience. Les microservices métier offrent un microservice Produit avec CRUD complet, un microservice Commandes avec fonctionnalités avancées, et des APIs REST documentées et testables. L'interface utilisateur est moderne et responsive avec des statistiques en temps réel et une gestion d'erreurs robuste. La documentation et le monitoring sont assurés par une documentation API automatique (Swagger), un monitoring via Actuator, et un dashboard Eureka pour la visualisation.

## 4.2. Compétences Acquises

### Techniques

Les compétences techniques acquises couvrent l'architecture microservices avec une compréhension des patterns et anti-patterns, la gestion de la complexité distribuée et la communication inter-services. La maîtrise de Spring Cloud inclut la configuration et l'utilisation des composants et l'intégration entre les services. Le développement Java Enterprise couvre Spring Boot avancé, Spring Data JPA et les APIs RESTful. Les outils et technologies maîtrisés incluent Maven pour la gestion de projet, Lombok pour la productivité et Thymeleaf pour les templates.

### Méthodologiques

Les compétences méthodologiques acquises incluent la conception architecturale avec la décomposition en microservices, le design d'APIs REST et la gestion de la configuration. Le développement couvre les bonnes pratiques de code, la gestion d'erreurs et la documentation du code. L'intégration comprend l'intégration de multiples services, la gestion des dépendances et les tests d'intégration.

## 4.3. Défis Rencontrés et Solutions

### Défis Techniques

Plusieurs défis techniques ont été rencontrés et résolus. La configuration du Circuit Breaker a nécessité l'utilisation de Resilience4j avec une configuration appropriée dans les fichiers de propriétés. Le routage dans l'API Gateway a été résolu en utilisant des filtres spécifiques pour les routes Swagger. La communication Feign Client a été améliorée avec une configuration d'URL de fallback directe si Eureka n'est pas disponible. L'initialisation des données dans H2 en mémoire a été gérée avec un mécanisme robuste d'initialisation au démarrage. La configuration Swagger a été complétée avec des annotations OpenAPI détaillées et une configuration CORS pour permettre les tests depuis le navigateur. Un problème d'optimistic locking lors de la création a été résolu en ignorant l'ID fourni pour permettre la génération automatique.

### Défis Architecturaux

Les défis architecturaux ont été résolus avec succès. La gestion de la configuration a été centralisée via un Config Server avec dépôt Git. Le Service Discovery a été implémenté avec Eureka Server et enregistrement automatique. La résilience a été assurée par un Circuit Breaker dans l'API Gateway.

## 4.4. Améliorations Futures

### Court Terme

Les améliorations à court terme incluent la sécurité avec l'implémentation de l'authentification (OAuth2, JWT), la sécurisation des endpoints sensibles et le chiffrement des données sensibles. La validation devrait être améliorée avec Bean Validation dans les contrôleurs, la validation des données d'entrée et des messages d'erreur standardisés. Les tests devraient être complétés avec des tests unitaires complets, des tests d'intégration et des tests de charge.

### Moyen Terme

Les améliorations à moyen terme incluent la communication inter-services avec l'implémentation de la communication réelle entre microservices, l'utilisation de Feign ou RestTemplate avec LoadBalancer et la gestion des transactions distribuées. L'observabilité devrait être améliorée avec Distributed Tracing (Zipkin, Sleuth), le logging centralisé (ELK Stack) et les métriques avancées (Prometheus, Grafana). La base de données devrait évoluer vers PostgreSQL ou MySQL avec la gestion des migrations (Flyway, Liquibase) et l'optimisation des requêtes.

### Long Terme

Les améliorations à long terme incluent une architecture Event-Driven avec l'implémentation d'événements (Kafka, RabbitMQ), la communication asynchrone et le pattern Saga pour les transactions distribuées. La containerisation devrait être implémentée avec Docker pour chaque microservice, Docker Compose pour l'orchestration locale et Kubernetes pour la production. Un pipeline CI/CD devrait être mis en place avec l'intégration continue, le déploiement automatique et les tests automatisés.

## 4.5. Leçons Apprises

### Points Positifs

Spring Cloud facilite grandement le développement des microservices en simplifiant l'implémentation des patterns avec une configuration déclarative qui réduit le code boilerplate. La documentation complète et la communauté active sont des atouts majeurs. L'architecture modulaire facilite la maintenance, permet le développement parallèle et simplifie les tests isolés. La scalabilité est améliorée avec la possibilité de mettre à l'échelle individuellement chaque service, le load balancing automatique et la résilience intégrée.

### Points d'Attention

La complexité opérationnelle augmente avec plus de services à gérer et déployer, nécessitant une infrastructure robuste et un monitoring et logging essentiels. La communication inter-services introduit de la latence réseau à considérer, une gestion d'erreurs plus complexe et nécessite une stratégie de retry et circuit breaker. La cohérence des données devient complexe avec des transactions distribuées qui nécessitent des patterns spécifiques (Saga, Event Sourcing) et la gestion de la cohérence éventuelle.

## 4.6. Conclusion Générale

Ce projet a été une expérience enrichissante qui a permis de mettre en pratique les concepts théoriques de l'architecture microservices. L'utilisation de Spring Cloud a démontré la puissance de cet écosystème pour simplifier le développement d'applications distribuées.

Les objectifs initiaux ont été atteints : une architecture microservices fonctionnelle, un Service Discovery opérationnel, un API Gateway avec résilience, une configuration centralisée, des microservices métier complets et une interface utilisateur moderne.

Le projet démontre une bonne compréhension des enjeux et des solutions de l'architecture microservices. Les améliorations suggérées permettront d'évoluer vers une solution de production robuste et scalable.

### Impact Pédagogique

Ce projet a permis d'acquérir une vision globale de l'architecture microservices, une maîtrise pratique de Spring Cloud, des compétences en développement d'applications distribuées et une compréhension des défis et solutions du monde réel.

### Perspectives

Les compétences acquises dans ce projet sont directement applicables dans un contexte professionnel. L'architecture microservices étant devenue un standard dans l'industrie, cette expérience constitue un atout significatif pour la carrière en développement logiciel.

---

**Fin du Rapport**

---

## Annexes

### A. URLs d'Accès

- **Eureka Dashboard** : http://localhost:8761
- **API Gateway** : http://localhost:8080
- **Swagger UI (Gateway)** : http://localhost:8080/swagger-ui.html
- **Microservice Produit** : http://localhost:8081
- **Swagger Produit** : http://localhost:8081/swagger-ui.html
- **Microservice Commandes** : http://localhost:8082
- **Swagger Commandes** : http://localhost:8082/swagger-ui.html
- **Client UI** : http://localhost:9090
- **H2 Console Produit** : http://localhost:8081/h2-console
- **H2 Console Commandes** : http://localhost:8082/h2-console

### B. Structure du Projet

Le projet est organisé en six modules principaux : eureka-server pour la découverte de services, config-server pour la configuration centralisée, api-gateway pour le routage et la résilience, microservice-produit pour la gestion des produits, microservice-commandes pour la gestion des commandes, et client-ui pour l'interface utilisateur web. Un dépôt de configuration séparé (config-repo) contient les fichiers de configuration versionnés dans Git.

### C. Technologies et Versions

- Java : 17
- Spring Boot : 3.4.0
- Spring Cloud : 2024.0.0
- Maven : 3.x
- H2 Database : 2.3.232
- Lombok : Latest
- SpringDoc OpenAPI : 2.8.4

---

**Rapport rédigé le : Décembre 2025**
