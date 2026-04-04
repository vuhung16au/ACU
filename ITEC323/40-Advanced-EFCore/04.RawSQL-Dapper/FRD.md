# Functional Requirements Document (FRD)
## 04.RawSQL-Dapper

## Purpose

Teach students when to keep using EF Core and when a parameterized SQL query with Dapper is a better fit.

## Functional Requirements

1. The app must compare an EF Core reporting query with an equivalent Dapper SQL query.
2. The app must show the Dapper SQL and explain why it is parameterized.
3. The app must demonstrate a shared transaction that uses both EF Core and Dapper on the same connection.
4. The app must include a realistic EF Core page so students can see that Dapper is an addition, not a replacement.

## Success Criteria

- Students can explain when Dapper is a sensible choice.
- Students can identify the parameterized parts of the SQL.
- Students can explain how EF Core and Dapper share the same transaction in the demo.
