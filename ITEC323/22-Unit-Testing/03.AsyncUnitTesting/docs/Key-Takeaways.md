# Key Takeaways

## What Async Unit Testing Means

Async unit testing is used when the method under test returns a `Task` or `Task<T>`.

The test should also use `async Task` so it can `await` the method properly.

## What Changed From Project 02

Project 02 mocked a synchronous dependency.

Project 03 keeps the same form and rule, but changes the duplicate-email check to:

- `Task<bool> EmailExistsAsync(string email)`

That small change is enough to teach:

- async app code
- async unit tests
- async mock setup with `ReturnsAsync`

## Why Await Matters In Tests

If a test does not await an async method correctly, it may:

- miss exceptions
- assert too early
- give misleading results

## What To Verify In Async Tests

This sample shows two kinds of async assertions:

- the returned validation result is correct
- the async dependency was called, or not called, in the right scenarios

## What Stays Simple

The submission service remains synchronous on purpose.

This keeps the lesson focused on one new idea:

- testing an async dependency without adding unrelated complexity
