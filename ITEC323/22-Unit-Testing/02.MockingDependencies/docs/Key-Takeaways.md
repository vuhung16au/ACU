# Key Takeaways

## What A Mock Is

A mock is a stand-in object used in a test.

It lets a test control how a dependency behaves without using the real implementation.

## Why Interfaces Help

The validator depends on `IEmailRegistry` instead of a concrete class.

That makes it possible to:

- use `InMemoryEmailRegistry` in the running app
- use a mock in unit tests

## What This Project Adds Beyond Project 01

Project 01 tests plain logic in small classes.

Project 02 adds:

- one dependency interface
- one real implementation for the app
- mocked interaction tests in the unit test project

## What To Verify In Advanced Unit Tests

This sample shows two kinds of assertions:

- result assertions, such as checking returned error messages
- interaction assertions, such as checking whether the email registry was called

## When Not To Mock

Do not mock everything by default.

In this sample:

- `FormSubmissionService` stays a plain class
- `IEmailRegistry` is mocked because it represents an external dependency to the validator
