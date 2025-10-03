## Tools Overview

### Docker
Docker is a platform to build, ship, and run applications in lightweight, isolated containers. It packages code and its dependencies into portable images so apps run consistently across machines.

- Common commands:
  - `docker build -t app:dev .`
  - `docker run --rm -p 8080:8080 app:dev`
  - `docker ps`, `docker images`, `docker logs <container>`
  - `docker compose up -d`

### Homebrew (brew) on macOS
Homebrew is a popular package manager for macOS (and Linux) that installs CLI tools and apps cleanly and keeps them up to date.

- Examples:
  - `brew install wget`
  - `brew upgrade`
  - `brew search node`
  - `brew info python`

### curl
curl is a command-line tool to transfer data to/from servers using URLs (HTTP, HTTPS, FTP, and more). It’s widely used for testing APIs, downloading files, and debugging requests.

- Examples:
  - `curl -I https://example.com`  (fetch headers)
  - `curl -o file.zip https://example.com/file.zip`  (download)
  - `curl -X POST -H 'Content-Type: application/json' -d '{"k":"v"}' https://httpbin.org/post`


# Install `nessus`

ref. https://www.tenable.com/downloads/nessus

## Windows 

```bash
curl --request GET \
  --url 'https://www.tenable.com/downloads/api/v2/pages/nessus/files/Nessus-10.9.4-x64.msi' \
  --output 'Nessus-10.9.4-x64.msi'
```

## Linux 

```bash
curl --request GET \
  --url 'https://www.tenable.com/downloads/api/v2/pages/nessus/files/Nessus-10.9.4-ubuntu1604_amd64.deb' \
  --output 'Nessus-10.9.4-ubuntu1604_amd64.deb'
```

## MacOS

```bash
brew install --cask nessus
```

```bash
curl --request GET \
  --url 'https://www.tenable.com/downloads/api/v2/pages/nessus/files/Nessus-10.9.4.dmg' \
  --output 'Nessus-10.9.4.dmg'
```

## `docker` 

```bash 
docker pull tenable/nessus:(version-OS)
```

e.g. `version-OS` = `latest-ubuntu`
ref. https://hub.docker.com/r/tenable/nessus
