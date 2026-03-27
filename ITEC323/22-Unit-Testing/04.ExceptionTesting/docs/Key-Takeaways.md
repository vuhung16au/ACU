# Key Takeaways

## What Exception Testing Means

Exception testing checks that a method throws the right exception for the right reason.

This project uses exception assertions to show how failure paths can be tested clearly.

## What Changed From Project 03

Project 03 returned validation results from an async validator.

Project 04 keeps the same form and domain, but the submission service now:

- throws for invalid input
- throws for duplicate email
- can also surface dependency failures

## What To Verify In Exception Tests

This sample shows how to verify:

- the exception type
- the exception message
- the conditions that should trigger the exception

## What The Page Does With Exceptions

The Razor Page catches known exceptions and shows the message to the user.

This keeps the UI beginner-friendly while still letting the service use exception-based failure paths for teaching.

## When To Use This Pattern

This project uses exceptions because the teaching goal is exception testing.

It is not meant to say that all validation should always use exceptions. The earlier projects still show other styles of handling invalid input.
