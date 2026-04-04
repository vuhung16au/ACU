# Workflow Anatomy

## What the YAML Teaches

The lesson workflow shows four ideas clearly:

1. `on.workflow_dispatch` keeps the workflow manual-only so students can study it without triggering automatic paid runs.
2. `actions/setup-dotnet` prepares the runner with the .NET SDK used by the course.
3. `make build-dotnet DOTNET=dotnet` reuses the repository's existing build command instead of inventing a CI-only command.
4. `actions/upload-artifact` shows the exact handoff point between CI and deployment.

## How to Run It Manually

Use the GitHub web UI:

1. Open the repository on GitHub.
2. Select **Actions**.
3. Choose **dotnet-ci-main-demo-disabled**.
4. Click **Run workflow**.
5. Choose the branch to run.
6. Start the workflow.

Students should be told that `workflow_dispatch` means manual trigger only, not automatic execution on push.

## Why Deployment Stays Manual

This lesson intentionally avoids a live deploy job in GitHub Actions.

That choice is educational:

- students can see every Azure CLI command directly
- students can debug packaging and runtime problems before automation hides the details
- the workflow stays small enough to understand in one lesson

## GitHub Secrets Discussion

The workflow does not need Azure secrets yet because it does not deploy.

This document should still introduce the idea of secrets so students understand what would be needed later:

- publish profiles
- service principal credentials
- app-specific configuration values

If the lesson is expanded later, secrets should be explained before an automated deployment job is added.

## Publish Artifacts

The workflow publishes the app output as a downloadable artifact.

That teaches students that a deployment target usually receives a built package, not source code. It also gives them a clear debugging checkpoint:

- if build fails, fix source or restore issues
- if tests fail, fix behavior
- if publish fails, fix packaging
- if manual deploy fails, troubleshoot Azure separately

## GitLab CI/CD and Docker Comparison

Students may also see this same pattern in GitLab:

- GitHub Actions uses `.github/workflows/*.yml`
- GitLab CI/CD uses one `.gitlab-ci.yml`
- GitLab jobs usually run inside Docker images, which makes the build environment explicit
- a .NET lesson pipeline in GitLab can use `mcr.microsoft.com/dotnet/sdk:10.0`
- the publish folder can be saved as an artifact or copied into a Docker image for later deployment

Brief teaching guidance:

1. Explain that the stages stay the same: restore, build, test, publish.
2. Show that Docker provides the runner environment in GitLab.
3. Emphasize that YAML syntax changes, but the underlying CI/CD workflow does not.
