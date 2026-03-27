# 05.ErrorHandlingAndStatusCodes

## Overview

This project teaches how Minimal APIs return meaningful status codes for validation errors, missing resources, conflicts, and unexpected failures.

## Main Features

- `GET /api/tasks/{id}` returns `200` or `404`
- `POST /api/tasks` returns `201`, `400`, or `409`
- `POST /api/tasks/fail` triggers the global `500` example

## Learning Objectives

- understand why status codes matter
- compare validation errors with conflict errors
- handle unexpected exceptions safely
- keep error messages clear for clients

## Related Files

- [QUICKSTART.md](QUICKSTART.md)
- [FRD.md](FRD.md)
- [docs/ErrorHandlingNotes.md](docs/ErrorHandlingNotes.md)
