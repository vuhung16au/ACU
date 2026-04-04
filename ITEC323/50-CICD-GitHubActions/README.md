# 50-CICD-GitHubActions

## Overview

`50-CICD-GitHubActions` teaches students how a GitHub Actions workflow can build .NET code, run tests, and publish a deployable artifact as a manual demo run.

This module intentionally stops short of automated Azure deployment. Students deploy manually with Azure CLI so they can understand the underlying commands and troubleshoot failures before those steps are hidden inside automation.

## Learning Objectives

Students will learn how to:

- read and explain a beginner-friendly GitHub Actions YAML workflow
- trigger CI manually from the GitHub Actions UI
- connect repository commands like `make build-dotnet` to a hosted CI runner
- recognize how the same stages map to GitLab CI/CD and Docker
- run unit tests in a focused teaching scope
- publish a Razor Pages app as a workflow artifact
- deploy the published output manually to Azure App Service with Azure CLI
- troubleshoot common deployment failures using logs and runtime checks

## Module Structure

- `01.RazorPagesCICDDemo` - Razor Pages sample app, tests, and lesson-specific documentation
- `50-CICD-GitHubActions.sln` - solution for the lesson app and tests
- `docs/WorkflowAnatomy.md` - notes on workflow structure, secrets, artifacts, and the manual deployment boundary

## Why This Module Matters

CI/CD experience is highly visible on resumes and in interviews because it shows students can work beyond local development.

This lesson keeps the example approachable:

- CI is real and repo-connected
- the workflow is manual-only to avoid accidental GitHub Actions charges
- test coverage stays focused on the lesson sample
- deployment is manual on purpose
- troubleshooting is part of the teaching goal, not an afterthought

## Documentation

- [QUICKSTART.md](QUICKSTART.md)
- [FRD.md](FRD.md)
- [Workflow Anatomy](docs/WorkflowAnatomy.md)
- [Project README](01.RazorPagesCICDDemo/README.md)

## GitLab CI/CD and Docker

This module uses GitHub Actions for the main demo, but the same CI/CD idea also works in GitLab:

- place the pipeline in a `.gitlab-ci.yml` file at the repo root
- use a .NET SDK Docker image such as `mcr.microsoft.com/dotnet/sdk:10.0`
- run the same restore, build, test, and publish commands inside GitLab jobs
- store the published app as a GitLab artifact
- optionally build a Docker image and push it to GitLab Container Registry for deployment

That means students can transfer the workflow thinking from GitHub Actions to GitLab without learning a completely different process.
