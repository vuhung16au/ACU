# PostgreSQL Database

Version: 15 (Docker image `postgres:15`).

## Schema and Seed

`data/sample_data.sql` creates tables (`users`, `posts`, `comments`), indexes, and inserts sample data. It is mounted into `/docker-entrypoint-initdb.d/` and runs automatically on first container start with an empty data directory.

## Running

`docker compose up -d database`

Connect with any client:

```
host: localhost
port: 5432
db: blogdb
user: bloguser
password: blogpassword
```

Health check uses `pg_isready`.


