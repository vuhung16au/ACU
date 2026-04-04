# 03.NPlusOne

This lesson teaches students how the N+1 query problem appears in EF Core and how to replace repeated related-data queries with better loading strategies.

## Learning Objectives

- Recognize how a seemingly simple loop can trigger many SQL queries.
- Compare a naive repeated-query workflow with eager loading and projection.
- Count executed SQL commands during each approach.
- Explain when `Include` is useful and when projection is a better fit.

## What This Project Demonstrates

- PostgreSQL-backed Razor Pages app
- Seeded orders, products, and order items
- A query comparison lab at `/QueryLab`
- A realistic orders page at `/Orders`
- Query counting with an EF Core command interceptor
- Side-by-side SQL samples and result previews

## Key Pages

- `/` lesson overview
- `/QueryLab` naive vs improved comparison page
- `/Orders` realistic order summaries page

## Why This Matters

N+1 problems often hide inside otherwise readable code. The page still “works”, but the database receives one query for the parent rows and then many extra queries for related rows. This sample helps students see the hidden cost clearly.
