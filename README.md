# LogistiQ — Enterprise Warehouse Management System (WMS)

LogistiQ is an enterprise-grade Warehouse Management System engineered for multi-facility supply chain orchestration, high-bay inventory tracking, directed wave picking, and floor execution with rugged handheld barcode scanners.

Built with a modular Spring Boot 3.4.2 REST backend and an Angular 19+ SPA conforming to the LogistiQ & DockSync design specifications.

---

## 1. Architectural Highlights

- **Multi-Facility Spatial Hierarchy**: 6-level physical location hierarchy (Warehouse -> Zone -> Aisle -> Rack -> Shelf -> Bin Coordinate).
- **Deterministic Stock Concurrency**: Optimistic locking (`@Version`) on physical inventory balances to eliminate race conditions during concurrent high-velocity pick/putaway scans.
- **Immutable Transaction Ledger**: Append-only `StockMovement` audit logs recording every stock delta with operator, timestamp, and source/destination location tracking.
- **Dynamic RBAC & Privilege Matrix**: Stateless JWT authentication with refresh token rotation, fine-grained action permissions, and warehouse facility data isolation.
- **Zero Fallback Architecture**: 100% live data binding from PostgreSQL database tables to frontend Angular Signals.
- **Rugged Scanner Workflow**: Dedicated floor specialist HUD with 3-step laser barcode verification (`Scan Bin` -> `Scan SKU` -> `Confirm Qty`) and hardware hotkeys (`F1`-`F4`).

---

## 2. Technology Stack

### Backend
- **Framework**: Spring Boot 3.4.2
- **Language**: Java 21
- **Security**: Spring Security 6 (Stateless JWT, BCrypt, Method-Level `@PreAuthorize`)
- **Persistence**: Spring Data JPA / Hibernate 6
- **Database**: PostgreSQL
- **API Documentation**: SpringDoc OpenAPI 2.8.3 / Swagger UI

### Frontend
- **Framework**: Angular 19+ (Standalone Components, Signals API)
- **Styling**: Vanilla CSS with LogistiQ Design System Tokens (Zero Tailwind dependency)
- **Build Tooling**: Angular CLI / Vite builder

---

## 3. Physical Spatial Hierarchy

```
Warehouse (Facility / DC)
 └── Zone (Ambient, Cold Storage 4°C, Bulk Pallets, Quarantine, Staging)
      └── Aisle (Corridor with sequence routing)
           └── Rack (Bay with weight limits)
                └── Shelf (Vertical tier level)
                     └── Location / Bin (e.g. WH1-Z01-A02-S1-B03)
```

---

## 4. Functional Modules

### 4.1 Security & Dynamic RBAC (`/security`)
- User provisioning with facility scoping.
- Role management (System Protected vs Custom Authored).
- 17 fine-grained permissions categorized across `ADMINISTRATION`, `INVENTORY`, `PROCUREMENT`, `OPERATIONS`, and `ANALYTICS`.

### 4.2 Spatial Facilities (`/warehouse`)
- Multi-warehouse directory.
- Zone definitions with temperature thresholds.
- Bin coordinates with maximum weight (kg) and volume ($m^3$) constraints.

### 4.3 Master SKU Catalog & Batches (`/product`, `/category`)
- Hierarchical category taxonomy tree.
- Master SKU catalog with barcode, unit of measure (`PCS`, `BOX`, `PALLET`, `KG`, `LITER`), dimensions, and reorder thresholds (`minReorderLevel`, `maxStockLevel`, `safetyStock`).
- Batch/Lot tracking with manufacture date, expiration date, and QC status (`PENDING_QC`, `APPROVED`, `REJECTED`, `EXPIRED`, `QUARANTINED`).

### 4.4 Inventory & Stock Audit Ledger (`/inventory`)
- Real-time stock levels with allocated vs available quantity distinction.
- Immutable audit ledger of all stock transactions (`INBOUND_RECEIPT`, `DIRECTED_PUTAWAY`, `WAVE_PICK`, `BIN_TRANSFER`, `STOCK_ADJUSTMENT`).
- Stock adjustment requests with mandatory reason codes.

### 4.5 Inbound & Outbound Order Fulfillment (`/order`)
- **Inbound**: Supplier Purchase Orders (PO) and Goods Receipt Notes (GRN) for receiving dock inspections.
- **Outbound**: Customer Sales Orders (SO), wave pick lists, directed picker missions, and carrier shipment dispatch manifests.

---

## 5. Operations Dashboards

| Dashboard | Target Role | Key Capabilities |
| :--- | :--- | :--- |
| **Admin Telemetry** (`/dashboard/admin`) | `ROLE_ADMIN` | Central node status, dynamic role allocation, IoT scanner mesh health, HikariCP connection telemetry, dense staff table. |
| **Manager Supply Chain** (`/dashboard/manager`) | `ROLE_MANAGER` | Total inventory valuation, active wave picks, dock utilization, capacity fill rates by zone, 6-month activity heatmap, reorder alerts. |
| **Floor Operations** (`/dashboard/employee`) | `ROLE_EMPLOYEE` | Handheld laser barcode scanner panel, 3-step pick workflow, mission queue, picks/hour velocity metric, keyboard shortcuts (`F1`-`F4`). |

---

## 6. Repository Layout

```
warehouse-management-system/
├── backend/                                   # Spring Boot REST API
│   ├── src/main/java/Warehousemanagement/project/
│   │   ├── common/                            # Shared DTOs, exceptions, master data seed
│   │   ├── security/                          # Auth, JWT, Users, Roles, Permissions
│   │   ├── category/                          # SKU Category hierarchy
│   │   ├── warehouse/                         # Facilities, Zones, Aisles, Racks, Bins
│   │   ├── product/                           # Master SKU catalog & Lot tracking
│   │   ├── inventory/                         # Inventory items, Stock movements ledger
│   │   ├── order/                             # POs, GRN, Sales Orders, Pick Tasks, Shipments
│   │   └── dashboard/                         # Admin, Manager, Employee dashboard services
│   └── src/main/resources/
│       └── application.properties             # PostgreSQL & Hibernate configuration
│
└── frontend/                                  # Angular 19+ SPA
    ├── src/app/
    │   ├── core/                              # Auth, Interceptors, Models, Services
    │   ├── shared/                            # Layout, Navigation, Reusable UI widgets
    │   └── features/
    │       ├── dashboard/                     # Admin, Manager, Employee dashboards
    │       ├── users/                         # Operator directory & user provisioning
    │       ├── roles/                         # RBAC permissions matrix
    │       └── categories/                    # SKU category taxonomy
    └── angular.json
```

---

## 7. Getting Started

### Prerequisites
- **Java**: OpenJDK 21 or higher
- **Node.js**: v20.x or higher (with npm)
- **Database**: PostgreSQL 15+ running on `localhost:5432`
- **Build Tool**: Maven 3.9+

### Database Configuration
Ensure PostgreSQL is running and create the database:
```sql
CREATE DATABASE "Warehouse";
```

Verify backend settings in `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Warehouse
spring.datasource.username=postgres
spring.datasource.password=tene
spring.jpa.hibernate.ddl-auto=update
```

### Running the Backend
```bash
cd backend
mvn spring-boot:run
```
The REST API will start on `http://localhost:8080`.
- OpenAPI / Swagger UI: `http://localhost:8080/swagger-ui.html`

### Running the Frontend
```bash
cd frontend
npm install
npm start
```
The application will launch on `http://localhost:4200/`.

---

## 8. Default Seed Credentials

Upon startup, `DataInitializer` and `WmsMasterDataInitializer` automatically bootstrap initial facilities, zones, SKUs, inventory, and default user accounts:

| Username | Password | Role | Primary Scope |
| :--- | :--- | :--- | :--- |
| `admin` | `Admin@12345` (or `password123`) | `ROLE_ADMIN` | Platform configuration & RBAC policies |
| `manager` | `Manager@12345` | `ROLE_MANAGER` | Supply chain analytics & PO approvals |
| `employee` | `Employee@12345` | `ROLE_EMPLOYEE` | Handheld floor scanning & wave picking |

---

## 9. License

Proprietary enterprise software developed for industrial supply chain and logistics operations.
