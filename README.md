# Liverpool Backend Challenge

REST API for managing customers, delivery addresses, and orders, built with **Java 17 + Spring Boot 3.3** using **Hexagonal Architecture (Ports & Adapters)**.

---

## Architecture

The project follows Hexagonal Architecture, which organises the code into three concentric layers. The inner layers never depend on the outer ones.

```
┌─────────────────────────────────────────────────────────────────┐
│                     INFRASTRUCTURE                              │
│                                                                 │
│   ┌─────────────┐                       ┌────────────────────┐  │
│   │  REST API   │                       │  MongoDB Adapters  │  │
│   │ (Driving /  │                       │  HTTP Client       │  │
│   │  Primary)   │                       │  (Driven /         │  │
│   └──────┬──────┘                       │   Secondary)       │  │
│          │ calls                        └────────┬───────────┘  │
│   ┌──────▼──────────────────────────────────────▼───────────┐   │
│   │                   APPLICATION                           │   │
│   │                                                         │   │
│   │   ┌──────────────────┐   ┌──────────────────────────┐   │   │
│   │   │  CustomerService │   │  OrderSearchService      │   │   │
│   │   │  DataSyncService │   │                          │   │   │
│   │   └──────────────────┘   └──────────────────────────┘   │   │
│   │                                                         │   │
│   │   ┌─────────────────────────────────────────────────┐   │   │
│   │   │                   DOMAIN                        │   │   │
│   │   │                                                 │   │   │
│   │   │   Models: Customer, Order, Item, DeliveryAddress│   │   │
│   │   │   Ports IN:  CreateCustomerUseCase, ...         │   │   │
│   │   │   Ports OUT: CustomerRepositoryPort, ...        │   │   │
│   │   │   Exceptions: CustomerNotFoundException         │   │   │
│   │   └─────────────────────────────────────────────────┘   │   │
│   └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

---

## Layer Breakdown

### Domain
The core of the application. Contains business models and port interfaces. Has **zero framework dependencies**.

| Package | Contents |
|---|---|
| `domain/model` | `Customer`, `Order`, `Item`, `DeliveryAddress`, `OrderWithItems` |
| `domain/port/in` | Use case interfaces: `CreateCustomerUseCase`, `FindCustomerUseCase`, `UpdateCustomerUseCase`, `DeleteCustomerUseCase`, `SearchOrdersUseCase`, `SyncDataUseCase` |
| `domain/port/out` | Repository/client interfaces: `CustomerRepositoryPort`, `OrderCachePort`, `ItemCachePort`, `PedidosClientPort`, `ItemsClientPort` |
| `domain/exception` | `CustomerNotFoundException`, `DomainException` |

### Application
Implements the use case interfaces defined in the domain. Orchestrates ports to fulfil business operations.

| Class | Responsibility |
|---|---|
| `CustomerService` | CRUD operations for customers, syncs orders from cache |
| `OrderSearchService` | Flexible order search by multiple fields, enriches results with items |
| `DataSyncService` | Fetches from external APIs and stores data in MongoDB on startup and every 30 min |

### Infrastructure — Driving Adapters (IN)
Accept external requests and translate them into use case calls.

| Class | Responsibility |
|---|---|
| `CustomerController` | `POST /api/v1/customers`, `GET /{userId}`, `PUT`, `DELETE` endpoints |
| `OrderSearchController` | `GET /api/v1/orders/search`, `POST /api/v1/orders/sync` |
| `GlobalExceptionHandler` | Maps domain exceptions to HTTP responses (RFC 7807 ProblemDetail) |

### Infrastructure — Driven Adapters (OUT)
Implement the output ports defined in the domain. The domain never imports these.

| Class | Implements | Technology |
|---|---|---|
| `CustomerMongoAdapter` | `CustomerRepositoryPort` | Spring Data MongoDB |
| `OrderCacheMongoAdapter` | `OrderCachePort` | MongoTemplate + Spanish collation |
| `ItemCacheMongoAdapter` | `ItemCachePort` | MongoTemplate + regex search |
| `PedidosApiAdapter` | `PedidosClientPort` | Spring `RestClient` |
| `ItemsApiAdapter` | `ItemsClientPort` | Spring `RestClient` |

---

## Request Flow Example — Search Orders

```
GET /api/v1/orders/search?q=pantalon&storeName=santa

OrderSearchController
    │
    ▼
SearchOrdersUseCase (interface)  ←── Spring injects ──► OrderSearchService
                                                               │
                                          ┌────────────────────┤
                                          ▼                    ▼
                                   ItemCachePort         OrderCachePort
                                          │                    │
                                          ▼                    ▼
                                  ItemCacheMongoAdapter  OrderCacheMongoAdapter
                                          │                    │
                                          └────────┬───────────┘
                                                   ▼
                                              MongoDB (liverpool_db)
                                                   │
                                                   ▼
                                        SearchOrdersResponse (JSON)
```

---

## Data Sync Flow

On startup (and every 30 minutes), `DataSyncService` pulls fresh data from the external Liverpool mock API and stores it locally:

```
ApplicationReadyEvent / @Scheduled
         │
         ▼
   DataSyncService.syncAll()
         │
         ├──► PedidosClientPort → mockapi.io/pedidos → saves to orders collection
         │
         └──► ItemsClientPort  → mockapi.io/items  → saves to items collection
```

This makes order/item search fast and independent of external API availability.

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Language |
| Spring Boot | 3.3.5 | Framework |
| Spring Data MongoDB | managed | Persistence |
| MongoDB | 7.0 | Database |
| Springdoc OpenAPI | 2.6.0 | Swagger UI |
| Lombok | 1.18.36 | Boilerplate reduction |
| JUnit 5 + Mockito | managed | Unit tests |
| CheckStyle | 3.3.1 | Code style validation |
| Docker Compose | - | Local MongoDB container |

---

## Project Structure

```
src/main/java/com/liverpool/challenge/
├── LiverpoolChallengeApplication.java
├── domain/
│   ├── model/                  # Business models
│   ├── port/
│   │   ├── in/                 # Use case interfaces (driving ports)
│   │   └── out/                # Repository/client interfaces (driven ports)
│   └── exception/
├── application/
│   └── service/                # Use case implementations
├── infrastructure/
│   ├── adapter/
│   │   ├── in/rest/            # REST controllers + DTOs
│   │   └── out/
│   │       ├── persistence/    # MongoDB adapters + documents
│   │       └── client/         # HTTP client adapters
│   └── config/                 # Spring configuration beans
└── shared/
    ├── mapper/                 # Domain ↔ Document ↔ DTO mappers
    └── util/                   # TextNormalizer (accent-insensitive search)
```

---

## Running Locally

### Prerequisites
- Docker, Java 17, Maven, git

### Option 1 — Docker (recommended)

```bash
# 1. Clone the repository
git clone https://github.com/cruzcid/liverpool-challenge/
cd liverpool-dev

# 2. Build and start the full stack (app + MongoDB)
docker compose up --build
```

The app will be available at `http://localhost:8088`.  
Swagger UI: `http://localhost:8088/swagger-ui/index.html`

```bash
# Stop the stack
docker compose down

# Stop and remove all data volumes
docker compose down -v
```

> **Note:** The first `--build` downloads dependencies and compiles the app inside Docker — this may take a few minutes. Subsequent runs without `--build` are fast.

---

### Option 2 — Local (Java 17 required)

```bash
# 1. Start only MongoDB
docker compose up mongodb -d

# 2. Run the app
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home ./mvnw spring-boot:run
```

---

## API Endpoints

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/v1/customers` | Create a customer |
| `GET` | `/api/v1/customers/{userId}` | Get customer with their orders |
| `PUT` | `/api/v1/customers/{userId}` | Update customer info |
| `PUT` | `/api/v1/customers/{userId}/delivery-address` | Update delivery address |
| `PUT` | `/api/v1/customers/{userId}/sync-orders` | Link orders from cache to customer |
| `DELETE` | `/api/v1/customers/{userId}` | Delete a customer |
| `GET` | `/api/v1/orders/search` | Search orders (accent/case/typo tolerant) |
| `POST` | `/api/v1/orders/sync` | Manually trigger external API sync |

### Search Query Parameters

| Param | Description |
|---|---|
| `q` | Free-text search (accent-insensitive, typo-tolerant) |
| `orderRef` | Filter by order reference number |
| `orderStatus` | Filter by delivery status |
| `storeName` | Filter by store name |
| `displayName` | Filter by item name |
