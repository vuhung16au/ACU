# Key Takeaways

- CI should reuse the same build commands students use locally whenever possible.
- A workflow artifact is the bridge between build automation and deployment.
- Manual deployment is useful for learning because it exposes the real Azure CLI commands and failure points.
- Troubleshooting becomes easier when students separate build, test, publish, and deploy into distinct stages.
- GitLab CI/CD and Docker use the same stages, even though the YAML file and runner setup look different.
