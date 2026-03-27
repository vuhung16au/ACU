# 03.RequestBodiesAndValidation

## Overview

This project teaches how Minimal APIs accept JSON request bodies and validate simple input before creating or updating a resource.

## Learning Objectives

- accept JSON in a `POST` request
- accept JSON in a `PUT` request
- validate required fields
- return `400 Bad Request` for invalid input
- return `201 Created` and `200 OK` for valid input

## Main Features

- `GET /` returns an overview
- `GET /api/tasks` lists the sample tasks
- `GET /api/tasks/{id}` gets one task
- `GET /api/priorities` lists the allowed priorities
- `POST /api/tasks` creates a task from JSON
- `PUT /api/tasks/{id}` updates a task from JSON
- `DELETE /api/tasks/{id}` deletes a task

## Related Files

- [QUICKSTART.md](QUICKSTART.md)
- [FRD.md](FRD.md)
- [docs/RequestBodyValidationNotes.md](docs/RequestBodyValidationNotes.md)
