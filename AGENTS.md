# Warehouse Management System (WMS) — Project Directives

This repository contains an enterprise platform for warehouse managers and floor employees to manage inventories, warehouses, stock, orders, and operations.

## Core Directives & Hard Constraints

1. **Design System & Visual Consistency (LogistiQ & DockSync Standard)**:
   - Strictly follow the enterprise logistics design language specified in `wms-ui-ux-guidelines`.
   - Use crisp white surface cards (`#FFFFFF`) with 1px border (`#E5E7EB`) over soft canvas background (`#F4F5F7`).
   - Implement fixed sidebar navigation with rounded active blue pills (`#2563EB`), top metric cards with sparkline curves, activity heatmap grids, and dense data tables with avatar badges.
   - Integrate 2D/Isometric warehouse spatial visualizers (Warehouse -> Zone -> Aisle -> Rack -> Shelf -> Bin) and ecosystem hub maps (ERP, TMS, OMS, WCS).
2. **Mandatory Data Pagination & Bounded Limits**:
   - Never expose unpaginated list endpoints or execute unpaginated `findAll()` collection queries.
   - Enforce a strict maximum page size ceiling (`max: 100`, `default: 20`) on all API endpoints.
   - Use keyset (cursor) pagination for high-volume append-only ledgers (`StockMovement`).
3. **Comprehensive Security & Warehouse Facility Isolation**:
   - Enforce stateless JWT authentication with refresh token rotation and explicit `@PreAuthorize` RBAC on every endpoint.
   - Strictly isolate warehouse facility data: all queries and mutations must be scoped to the caller's authorized `warehouseId`.
   - Never log secrets or credentials; enforce BCrypt hashing (strength 12) and rate limiting on login/scanning endpoints.
4. **Continuous Critical Questioning & Optimization Justification**:
   - Always question assumptions, analyze trade-offs, and justify technical choices before implementation.
   - Relentlessly optimize database queries (prevent N+1, use composite indexing, batching), concurrency models (minimize lock contention), pick routing algorithms, and scanner roundtrip latency (<100ms budget).
5. **Zero Decorative Emojis & AI Clichés**:
   - Never use emojis in documentation, code comments, commit messages, or responses.
   - Prohibit generic AI-generated templates, purple gradient blobs, and superficial design clichés.
   - Build authentic, high-density industrial interfaces and robust, human-written Java/Spring Boot code.
6. **Max File Size Constraint (< 300 LOC Rule)**:
   - Never write single source files with 300+ lines of code.
   - Proactively decompose classes approaching 200–250 lines using Single Responsibility Principle (SRP).
7. **Strict 8-Pillar Package Structure**:
   - Every module must be partitioned into: `controller`, `dto`, `mapper`, `exceptions`, `model`, `service`, `repository`, `enum`.
8. **No Direct Entity Exposure**:
   - Controllers never accept or return JPA entities; all interactions happen through DTOs converted by dedicated Mappers.
9. **Deterministic Concurrency & Auditability**:
   - All stock mutations use `@Version` optimistic locking, safe atomic updates, and write to the immutable `StockMovement` ledger.

## Skills Available in This Project

When working on this project, adhere to the established skills located in `.agents/skills/`:

- **[wms-ui-ux-guidelines](file:///.agents/skills/wms-ui-ux-guidelines/SKILL.md)**: Enterprise logistics design system (LogistiQ & DockSync layouts, metrics cards, activity heatmap grids, dense tables, 2D/3D warehouse rack visualizer, floor scanner view).
- **[wms-security-and-pagination-standards](file:///.agents/skills/wms-security-and-pagination-standards/SKILL.md)**: Mandatory pagination, page size ceilings, keyset cursors, warehouse facility isolation, RBAC, and threat protection.
- **[wms-critical-optimization-analysis](file:///.agents/skills/wms-critical-optimization-analysis/SKILL.md)**: Rigorous critical questioning, decision justification, performance profiling, N+1 query elimination, indexing, lock contention reduction, and latency budgets.
- **[wms-production-craftsmanship](file:///.agents/skills/wms-production-craftsmanship/SKILL.md)**: Zero emojis, authentic industrial UI design, complete production-grade implementations, and realistic domain modeling.
- **[wms-clean-code-modularity](file:///.agents/skills/wms-clean-code-modularity/SKILL.md)**: Strict <300 LOC rule per file, SRP decomposition, and 8-pillar package organization (`controller`, `dto`, `mapper`, `exceptions`, `model`, `service`, `repository`, `enum`).
- **[wms-domain-architecture](file:///.agents/skills/wms-domain-architecture/SKILL.md)**: Physical location hierarchy (Warehouse -> Zone -> Aisle -> Rack -> Shelf -> Bin), product master, batch/lot tracking, and role-based permissions (`ROLE_MANAGER`, `ROLE_EMPLOYEE`, `ROLE_ADMIN`, `ROLE_SUPERVISOR`, `ROLE_AUDITOR`).
- **[wms-backend-standards](file:///.agents/skills/wms-backend-standards/SKILL.md)**: Clean Spring Boot architecture, REST API formatting, DTO encapsulation, transactional boundaries, optimistic locking (`@Version`), and security.
- **[wms-inventory-workflows](file:///.agents/skills/wms-inventory-workflows/SKILL.md)**: Inbound dock receiving, directed putaway, wave/zone picking, packing, shipping, bin transfers, cycle counting, and immutable audit logs.
- **[wms-testing-and-qa](file:///.agents/skills/wms-testing-and-qa/SKILL.md)**: Testing standards, concurrency test patterns, negative stock prevention, and edge case test suites.
