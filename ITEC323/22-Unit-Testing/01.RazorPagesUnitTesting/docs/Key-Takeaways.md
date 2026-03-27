# Key Takeaways

## What A Unit Test Is

A unit test checks a small piece of code in isolation.

In this sample, the unit tests focus on:

- validation rules
- submission processing
- expected output values

## Why The Logic Was Moved Out Of The PageModel

The PageModel is responsible for web requests and page flow.

The validator and service classes are easier to test because they:

- do not depend on a browser
- do not depend on HTML rendering
- return clear results for given inputs

## Fact Vs Theory

- Use `[Fact]` when a test has one scenario.
- Use `[Theory]` when the same behavior should be tested with multiple inputs.

This project uses both so students can compare them.

## AAA Pattern

The tests follow the Arrange-Act-Assert pattern:

1. Arrange the input values
2. Act by calling the method
3. Assert the expected result

## What Not To Unit Test Here

This sample does not unit test:

- the `.cshtml` markup
- the browser UI itself
- framework internals in ASP.NET Core

Those are better covered with other kinds of testing, such as UI tests or integration tests.
