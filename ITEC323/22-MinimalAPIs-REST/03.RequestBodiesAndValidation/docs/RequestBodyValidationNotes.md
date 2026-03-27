# Request Body Validation Notes

Minimal APIs can read JSON directly into a C# class when a request body is sent to a `POST` or `PUT` endpoint.

This project keeps validation explicit in a small service so students can see the decision-making clearly, even when the API also supports list, details, update, and delete routes.
