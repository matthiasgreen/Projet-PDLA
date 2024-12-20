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

## Diagramme de classes

```mermaid
classDiagram
    class User {
        +int id
        +String username
        +String password
        +UserRole role
    }
    UserInNeed --|> User
    Volunteer --|> User
    Validator --|> User

    class Post {
        +int id
        +String title
        +String content
        +String location
        +String refusal_reason
        +Date createdAt
        +String type
        +String status
        +int user_id
    }


    Mission --|> Post
    Offer --|> Post