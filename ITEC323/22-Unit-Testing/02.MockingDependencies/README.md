# 02.MockingDependencies

## Overview

This project builds on `01.RazorPagesUnitTesting` and introduces **mocking dependencies** in unit tests.

The form still collects:

1. name
2. email
3. favourite language

After the user clicks **Submit**, the page displays the submitted values, but only if the email is not already registered.

## Learning Objectives

By working through this project, students will learn how to:

- reuse a simple Razor Pages example while adding richer testable behavior
- introduce an interface for a dependency
- use a mock instead of a real dependency in unit tests
- verify both returned errors and dependency interactions
- understand why advanced unit testing often uses interfaces

## Main Features

- same simple form structure as project 01
- duplicate email check through `IEmailRegistry`
- in-memory email registry for the running web app
- Moq-based unit tests for validator behavior and interaction verification

## What Gets Unit Tested

- valid input passes validation
- missing name returns an error
- missing email returns an error
- invalid email returns an error
- duplicate email returns an error
- the validator calls the email registry only when appropriate
- processed results trim values and display them clearly
- an unselected language is displayed as `Unselected`

## Related Files

- [QUICKSTART.md](QUICKSTART.md) for setup and run steps
- [FRD.md](FRD.md) for functional requirements
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for teaching notes
