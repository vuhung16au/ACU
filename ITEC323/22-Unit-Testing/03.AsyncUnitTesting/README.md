# 03.AsyncUnitTesting

## Overview

This project builds on `02.MockingDependencies` and introduces **async unit testing**.

The form still collects:

1. name
2. email
3. favourite language

After the user clicks **Submit**, the page displays the submitted values, but only if the asynchronous duplicate-email check passes.

## Learning Objectives

By working through this project, students will learn how to:

- test `Task`-based methods with xUnit
- use `async` and `await` inside unit tests
- configure mocks with `ReturnsAsync`
- verify async dependency calls
- understand why an async dependency changes both app code and tests

## Main Features

- same simple form structure as projects 01 and 02
- asynchronous duplicate email check through `IAsyncEmailRegistry`
- in-memory async registry for the running web app
- Moq-based async unit tests for validator behavior and interaction verification

## What Gets Unit Tested

- valid input passes async validation
- missing name returns an error
- missing email returns an error
- invalid email returns an error
- duplicate email returns an error
- the validator calls the async email registry only when appropriate
- processed results trim values and display them clearly
- an unselected language is displayed as `Unselected`

## Related Files

- [QUICKSTART.md](QUICKSTART.md) for setup and run steps
- [FRD.md](FRD.md) for functional requirements
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for teaching notes
