# 01.RazorPagesCICDDemo

## Overview

This project demonstrates a simple ASP.NET Core Razor Pages lesson app for teaching CI/CD with GitHub Actions and manual Azure App Service deployment.

## Learning Objectives

By working through this project, students will learn how to:

- explain the stages of a GitHub Actions workflow
- explain why this workflow is run manually instead of on every push
- identify which steps belong to CI and which steps belong to deployment
- understand why a publish artifact matters
- rehearse Azure CLI deployment commands before automating them
- troubleshoot common App Service deployment failures

## Project Structure

```text
01.RazorPagesCICDDemo/
├── 01.RazorPagesCICDDemo.csproj
├── Program.cs
├── Models/
├── Services/
├── Pages/
├── Properties/
├── wwwroot/
├── docs/
├── tests/
├── README.md
├── QUICKSTART.md
└── FRD.md
```

## Main Features

- overview page that maps out the GitHub Actions workflow
- Azure CLI command list for manual App Service deployment
- troubleshooting page for common deployment failures
- unit-tested service classes that keep the teaching logic easy to follow

## Related Files

- [QUICKSTART.md](QUICKSTART.md)
- [FRD.md](FRD.md)
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md)
