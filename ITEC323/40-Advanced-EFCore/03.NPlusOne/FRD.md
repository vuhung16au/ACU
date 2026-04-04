# Functional Requirements Document (FRD)
## 03.NPlusOne

## Purpose

Teach students how to detect and fix the N+1 query problem in EF Core.

## Functional Requirements

1. The app must show a naive workflow that issues repeated related-data queries.
2. The app must show an improved workflow that reduces the number of SQL commands.
3. The app must count and display the SQL command count for each workflow.
4. The app must show example SQL so students can see why the query count grows.
5. The app must include a realistic order summary page using the improved approach.

## Success Criteria

- Students can explain why the naive loop causes repeated queries.
- Students can compare query counts and see a measurable improvement.
- Students can explain why projection can sometimes be better than simply adding more `Include` calls.
