# Personalites - Installation & Configuration

Plateforme de test de personnalité (MBTI) composée de :  
- **Frontend** : Vue 3 + Vite
- **Backend (API)** : Spring Boot (Java 21, Maven)
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
│  ├─ src/main/resources/application.properties (code générique)
|  ├─ src/main/resources/application-prod.properties (config prod)
|  ├─ (application-dev.properties à créer en local, ignoré du repo)
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
├─ deployment/               # Shell 
│  ├─ deploy-db.sh
│  ├─ deploy-api.sh
│  ├─ deploy-front.sh
├─ JenkinsFile               # Groovy 
└─ README.md
```

## 3. Installation & Configuration

### 3.1. Cloner le projet
```bash
git clone https://github.com/fabienAstia/personalities.git
```

### 3.2. Base de données
1. Créer une base PostgreSQL : 
CREATE DATABASE personalities

2. Importe les scripts SQL depuis lde dossier Scripts/:
```
psql -U <username> -d personalities -f Scripts/with_strength_and_weakness.ddl.sql
psql -U <username> -d personalities -f Scripts/1-order_question.dml.sql
psql -U <username> -d personalities -f Scripts/2-professions.dml.sql
psql -U <username> -d personalities -f Scripts/3-eval_and_traits.dml.sql
```

4. Configuration backend
Créer un fichier src/main/resources/application-dev.properties avec vos paramètres locaux : 
```properties
# Database configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/personalities_db
spring.datasource.username=postgres
spring.datasource.password=<mot_de_passe_postgres>

# Security and JWT settings
token.secret=<clé_secrète_jwt>
token.expiration=3600
token.issuer=http://localhost:8080
spring.security.oauth2.resourceserver.jwt.authorities-claim-name=role
spring.security.oauth2.resourceserver.jwt.authority-prefix=

# CORS
cors.allowedOrigins=http://localhost:5173

# Admin
admin.username=<email_admin>

# Pole Emploi / France Travail API
francetravail.url=https://francetravail.io/connexion/oauth2/access_token
francetravail.client.id=<client_id>
francetravail.secret.key=<secret_key>
francetravail.offersJobs.uri=https://api.francetravail.io/partenaire/offresdemploi/v2/offres/search

# Reverse Geocoding (Geoapify)
Geoapify.token=<geoapify_token>

# Errors
server.error.include-message=always
server.error.include-binding-errors=always
```

## 4. Lancement en local

### 4.1. Backend (Spring Boot)
Ouvrir le projet `personalities-business` dans votre **IDE** puis lancer la classe principale :  
`src/main/java/.../Application.java`

L’API est alors disponible sur http://localhost:8080

---

### 4.2. Frontend (Vue.js)
Depuis le dossier `personalities-vue/` :
```bash
npm install
npm run dev
```
Accessible sur http://localhost:5173   

