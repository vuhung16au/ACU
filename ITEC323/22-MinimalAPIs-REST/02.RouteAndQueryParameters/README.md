# 02.RouteAndQueryParameters

## Overview

This project teaches how Minimal APIs read values from the URL.

Students learn how to:

- read route values such as `/api/tasks/2`
- read query string values such as `?priority=High`
- return `404 Not Found` when a resource is missing
- keep endpoint code short and readable

## Learning Objectives

- understand route parameters and query parameters
- compare `/api/tasks/{id}` with `?priority=High`
- filter simple in-memory data
- build clear `GET` endpoints with JSON responses

## Main Features

- `GET /` returns an API overview
- `GET /api/tasks/{id}` finds a task by id
- `GET /api/tasks/search?priority=High` filters by priority
- `GET /api/tasks/filter?completed=true` filters by completion state

## Related Files

- [QUICKSTART.md](QUICKSTART.md)
- [FRD.md](FRD.md)
- [docs/RouteAndQueryNotes.md](docs/RouteAndQueryNotes.md)
