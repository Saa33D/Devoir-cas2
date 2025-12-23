# Projet Microservices Spring Cloud - Devoir JEE N°1

## Architecture
- eureka-server (8761)
- config-server (8888) -> lit config depuis `../config-repo`
- api-gateway (8080)
- microservice-produit (8081)
- microservice-commandes (8082)
- h2-console: accessible sur 8081/8082 si nécessaire

## Pré-requis
- Java 11 (ou 17 si tu veux adapter)
- Maven
- Git (pour le repo config local)
- IntelliJ IDEA

## Préparation
1. Cloner le dépôt avec les 5 dossiers (ou copier les dossiers fournis).
2. Dans `config-repo/`, initialiser git:
   ```bash
   cd config-repo
   git init
   git add .
   git commit -m "initial config"

