# Key Takeaways

- A local in-memory API is enough to teach complete CRUD workflows.
- RESTful endpoints should return clear status codes such as `200 OK`, `201 Created`, `404 Not Found`, and `400 Bad Request`.
- A same-origin Razor Pages frontend can call the local API without external dependencies.
- Loading panels, disabled buttons, and success or error messages make asynchronous CRUD easier to understand.
- Simple polling can refresh summary data automatically while the application is running.
