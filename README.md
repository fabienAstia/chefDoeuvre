# Personalites - Installation & Configuration

Plateforme de test de personnalité (MBTI) composée de :  
- **Frontend** : Vue 3 + Vite
- **Backend (API)** : Spring Boot (Java 21)
- **Base de données** : PostgreSQL

Les **secrets** (France Travail, BDD, JWT, etc.) sont gérés via variables d'environnement et fichiers .env non versionnés.

## 1. Prérequis
- Git
- Java 21
- Maven 3.9+
- Node.js 18+ et npm
- PostgreSQL 14+

## 2. Arborescence du projet (vue d'ensemble)
```
personalities/
├─ personalities-business/   # Spring Boot (Java 21)
│  ├─ src/main/java/…
│  ├─ src/main/resources/application.yml
│  └─ pom.xml
├─ personalities-vue/        # Vue 3 + Vite
│  ├─ src/
│  ├─ index.html
│  └─ package.json
├─ Scripts/                  # SQL 
│  ├─ with_strength_and_weakness.ddl.sql
│  ├─ 1-order_question.dml.sql
│  ├─ 2-professions.dml.sql
|  └─ 3-eval_and_traits.dml.sql
├─ .github/workflows/        # CI GitHub Actions
│  └─ ci.yml
└─ README.md
```
