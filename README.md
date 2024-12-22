Binome :
Matthias Green
Yassine Iben-brahim

# Projet-PDLA
Mini-projet application d’aide aux personnes vulnérables en Java.


## Description
Ce projet est une application Java conçue pour aider les personnes vulnérables. Il permet aux utilisateurs de créer, visualiser et gérer des offres et des missions. Les utilisateurs peuvent avoir différents rôles : utilisateur en besoin, volontaire et validateur.


## Fonctionnalités
- **Connexion et inscription** : Les utilisateurs peuvent se connecter et s'inscrire avec différents rôles.
- **Création de posts** : Les utilisateurs peuvent créer des offres et des missions.
- **Validation des missions** : Les validateurs peuvent approuver ou refuser des missions.
- **Gestion des statuts** : Les utilisateurs en besoin peuvent changer le statut des missions vers DONE, ou supprimer les missions.

## Structure
Le code est structuré avec le design pattern MVC:
- **com.projet.models**: Structures de données comme User, Review, Post, etc... Ces classes exposent également des méthodes pour faire des opérations sur la base de données.
- **com.projet.views**: Eléments graphiques de l'application, qui présentent des données aux utilisateurs et leur permettent d'interagir avec l'application.
- **com.projet.controllers**: Classes qui gèrent les interactions entre les modèles et les vues.
- **com.projet.database.DatabaseConnection**: Permet de gérer une connexion à la base de données.
- **com.projet.Main**: Point d'entrée de l'application. Crée des instances des classes Controller et View pour lancer l'application.
