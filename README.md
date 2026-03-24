# 📚 Library API

API REST per la gestione di una biblioteca, sviluppata con **Spring Boot 4** e protetta tramite autenticazione **JWT**. Permette di gestire autori e libri con operazioni CRUD complete, accessibili solo agli utenti autenticati.

---

## 🛠️ Tecnologie utilizzate

- **Java 21**
- **Spring Boot 4.0.4**
- **Spring Security 7** — protezione degli endpoint tramite JWT
- **Spring Data JPA / Hibernate 7** — persistenza dei dati
- **MySQL 8** — database relazionale
- **JJWT 0.12.6** — generazione e validazione dei token JWT
- **Lombok** — riduzione del codice boilerplate
- **Bean Validation** — validazione degli input

---

## 📁 Struttura del progetto

```
src/main/java/com/hefebo/library/
├── controller/
│   ├── AuthController.java       # Endpoint di registrazione e login
│   ├── AuthorController.java     # CRUD autori
│   └── BookController.java       # CRUD libri
├── dto/
│   ├── request/                  # RegisterRequest, LoginRequest, AuthorRequest, BookRequest
│   └── response/                 # AuthResponse, AuthorResponse, BookResponse
├── exception/
│   ├── AuthorNotFoundException.java
│   ├── BookNotFoundException.java
│   ├── EmailAlreadyExistsException.java
│   └── GlobalExceptionHandler.java
├── mapper/
│   ├── AuthorMapper.java
│   └── BookMapper.java
├── model/
│   ├── Author.java
│   ├── Book.java
│   ├── User.java                 # Implementa UserDetails
│   └── Role.java                 # Enum: USER, ADMIN
├── repository/
│   ├── AuthorRepository.java
│   ├── BookRepository.java
│   └── UserRepository.java
├── security/
│   ├── ApplicationConfig.java    # UserDetailsService, PasswordEncoder, AuthenticationManager
│   ├── JwtAuthFilter.java        # Filtro JWT per ogni richiesta
│   ├── JwtService.java           # Generazione e validazione token
│   └── SecurityConfig.java       # Regole di accesso e configurazione Spring Security
└── service/
    ├── AuthService.java / impl/AuthServiceImpl.java
    ├── AuthorService.java / impl/AuthorServiceImpl.java
    └── BookService.java / impl/BookServiceImpl.java
```

---

## ⚙️ Configurazione

### Prerequisiti

- Java 21
- Maven
- MySQL 8

### Clona il repository

```bash
git clone https://github.com/tuousername/library.git
cd library
```

### Configura le variabili locali

Crea il file `src/main/resources/application-local.properties` (già incluso nel `.gitignore`):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library
spring.datasource.username=il_tuo_utente
spring.datasource.password=la_tua_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

jwt.secret=5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437
jwt.expiration=86400000
```

> ⚠️ Non committare mai questo file. Contiene credenziali sensibili.

### Avvia l'applicazione

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=local
```

Oppure in VS Code con `F5` usando la configurazione in `launch.json`.

Il database verrà creato automaticamente grazie a `spring.jpa.hibernate.ddl-auto=update`.

---

## 🔐 Autenticazione JWT

Tutti gli endpoint ad eccezione di `/api/auth/**` sono protetti. Per accedervi è necessario includere il token JWT nell'header della richiesta:

```
Authorization: Bearer <token>
```

### Come ottenere il token

**Registrazione** — crea un nuovo utente e restituisce il token:
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "utente@esempio.com",
  "password": "password123"
}
```

**Login** — autentica un utente esistente e restituisce il token:
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "utente@esempio.com",
  "password": "password123"
}
```

**Risposta** (in entrambi i casi):
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Il token ha una validità di **24 ore**. Alla scadenza è necessario effettuare nuovamente il login.

---

## 📖 Endpoint API

### Autori — `/api/authors`

| Metodo | Endpoint | Descrizione | Risposta |
|--------|----------|-------------|----------|
| `GET` | `/api/authors` | Lista tutti gli autori | `200 OK` |
| `GET` | `/api/authors/{id}` | Recupera un autore per ID | `200 OK` |
| `POST` | `/api/authors` | Crea un nuovo autore | `201 Created` |
| `PUT` | `/api/authors/{id}` | Aggiorna un autore esistente | `200 OK` |
| `DELETE` | `/api/authors/{id}` | Elimina un autore | `204 No Content` |

**Body per POST e PUT:**
```json
{
  "name": "Gabriel García Márquez"
}
```

**Validazioni:** il nome deve contenere tra 2 e 20 caratteri.

---

### Libri — `/api/books`

| Metodo | Endpoint | Descrizione | Risposta |
|--------|----------|-------------|----------|
| `GET` | `/api/books` | Lista tutti i libri | `200 OK` |
| `GET` | `/api/books/{id}` | Recupera un libro per ID | `200 OK` |
| `POST` | `/api/books` | Crea un nuovo libro | `201 Created` |
| `PUT` | `/api/books/{id}` | Aggiorna un libro esistente | `200 OK` |
| `DELETE` | `/api/books/{id}` | Elimina un libro | `204 No Content` |

**Body per POST e PUT:**
```json
{
  "title": "Cent'anni di solitudine",
  "authorId": 1
}
```

**Validazioni:** il titolo deve contenere tra 2 e 20 caratteri. L'`authorId` deve essere un ID valido presente nel database.

---

## ⚠️ Gestione degli errori

| Codice | Significato |
|--------|-------------|
| `400 Bad Request` | Dati non validi (validazione fallita) |
| `403 Forbidden` | Token mancante, non valido o scaduto |
| `404 Not Found` | Autore o libro non trovato |
| `409 Conflict` | Email già registrata |

---

## 🗄️ Modello del database

```
users
├── id          BIGINT (PK)
├── email       VARCHAR (UNIQUE)
├── password    VARCHAR (hash BCrypt)
└── role        ENUM('USER', 'ADMIN')

author
├── id          BIGINT (PK)
└── name        VARCHAR

book
├── id          BIGINT (PK)
├── title       VARCHAR
└── author_id   BIGINT (FK → author.id)
```

> La relazione tra `author` e `book` è **uno-a-molti**: un autore può avere più libri. Eliminando un autore, tutti i suoi libri vengono eliminati automaticamente (`CascadeType.ALL`).

---

## 🌐 Interfaccia web

Il progetto include un'interfaccia web integrata accessibile all'indirizzo `http://localhost:8080` una volta avviata l'applicazione. Permette di testare tutti gli endpoint direttamente dal browser senza strumenti esterni.

---

## 🚀 Possibili miglioramenti futuri

- Implementazione dei **Refresh Token** per rinnovare il token senza effettuare nuovamente il login
- Endpoint per la gestione dei ruoli (`ADMIN` / `USER`) con accesso differenziato
- Paginazione e ricerca per nome negli endpoint di lista
- Liste di libri personali per utente
- Documentazione API con **Swagger / OpenAPI**

---

## 👤 Autore

Sviluppato da **hefebo** come progetto di portfolio Backend con Java e Spring Boot.