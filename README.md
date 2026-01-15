# dealflow-rbac-platform

Production-style Spring Boot 3.5.9 + JPA + Flyway (PostgreSQL) + GraphQL SPQR (annotation-driven schema generation) + OAuth2 Resource Server + RBAC (workspace-scoped).

This project **refactors and re-models** the ideas from the provided `tables.sql` into a cleaner, tenant-safe domain model:
- `df_workspaces` = tenant boundary
- workspace members + roles + permissions (RBAC)
- customers, listings, opportunities (3 GraphQL APIs) with **Specifications-based filtering**

## Tech
- Java 21
- Spring Boot 3.5.9
- Spring Data JPA + `JpaSpecificationExecutor`
- Flyway (each table in a separate migration file)
- GraphQL SPQR Spring Boot starter (schema generated from annotations) citeturn11search0
- OAuth2 Resource Server (JWT)

## Quick start

### 1) Start PostgreSQL
```bash
docker compose up -d
```

### 2) Run app
```bash
./gradlew bootRun
```

Flyway will create schema and seed:
- workspace `demo`
- member `subject=user-1` with `ADMIN` role

### 3) Call GraphQL
Endpoint: `POST /graphql`

> Auth: configure `spring.security.oauth2.resourceserver.jwt.issuer-uri` for your IdP (Keycloak/Azure AD/Auth0).  
> For local dev you can also run Keycloak and issue a JWT where `sub=user-1`.

## GraphQL examples

### A) Customers (Specifications filtering) + RBAC
**Query**
```graphql
query Customers($ws: UUID!, $filter: CustomerFilterInput, $page: Int!, $size: Int!) {
  customers(workspaceId: $ws, filter: $filter, page: $page, size: $size) {
    content { id displayName status email phone }
    totalElements
    totalPages
  }
}
```

**Variables**
```json
{
  "ws": "PUT-DEMO-WORKSPACE-ID-HERE",
  "filter": { "text": "acme", "status": "QUALIFIED" },
  "page": 0,
  "size": 20
}
```

### B) Listings (Specifications filtering) + RBAC
```graphql
query Listings($ws: UUID!, $filter: ListingFilterInput, $page: Int!, $size: Int!) {
  listings(workspaceId: $ws, filter: $filter, page: $page, size: $size) {
    content { id title city askingPrice status }
    totalElements
  }
}
```

### C) Opportunities pipeline + RBAC
```graphql
mutation MoveStage($input: MoveOpportunityStageInput!) {
  moveOpportunityStage(input: $input) { id title stage amount }
}
```

## RBAC
Resolvers use method-security:
- `CUSTOMER_READ / CUSTOMER_WRITE`
- `LISTING_READ / LISTING_WRITE`
- `PIPELINE_READ / PIPELINE_WRITE`
- `WORKSPACE_ADMIN` (super-permission)

Implementation: `@PreAuthorize("@wsSec.hasPermission(workspaceId, PermissionCode.X)")`

## Migrations layout
`src/main/resources/db/migration/`
- `V1_010__create_df_workspaces.sql`
- `V1_020__create_df_roles.sql`
- ...
- `V1_900__seed_rbac.sql`
- `V1_910__seed_demo_workspace.sql`

## Notes on dependencies
- SPQR Spring Boot starter version is pinned to `1.0.1` citeturn11search0
- Testcontainers is pinned via BOM `2.0.3` citeturn12search4
