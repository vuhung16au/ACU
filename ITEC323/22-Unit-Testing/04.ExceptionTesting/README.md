# 04.ExceptionTesting

## Overview

This project builds on `03.AsyncUnitTesting` and introduces **exception testing**.

The form still collects:

1. name
2. email
3. favourite language

After the user clicks **Submit**, the page displays the submitted values, but the submission service now throws exceptions for failure paths.

## Learning Objectives

By working through this project, students will learn how to:

- test methods that throw exceptions
- verify different failure paths with clear exception assertions
- understand the difference between a successful result and a thrown error
- see how the page can catch known exceptions and show friendly messages

## Main Features

- same simple form structure as projects 01, 02, and 03
- exception-based validation and duplicate email checking in one service
- async email registry for the running web app
- unit tests for successful results, thrown exceptions, and dependency failure paths

## What Gets Unit Tested

- missing name throws `ArgumentException`
- missing email throws `ArgumentException`
- invalid email throws `FormatException`
- duplicate email throws `InvalidOperationException`
- a registry failure is propagated as an exception
- valid input returns a submission result
- an unselected language is displayed as `Unselected`

## Related Files

- [QUICKSTART.md](QUICKSTART.md) for setup and run steps
- [FRD.md](FRD.md) for functional requirements
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for teaching notes
