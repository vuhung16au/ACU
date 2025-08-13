# Day 8 - Spring MVC (classic Spring Web MVC)

## Goals
- Understand Spring MVC architecture: DispatcherServlet, HandlerMapping, Controller, ViewResolver, Model
- Build server-rendered pages with Thymeleaf (controllers returning view names)
- Form binding and validation with `@ModelAttribute`, `BindingResult`, and Jakarta Validation
- Serve static resources and implement file upload with `MultipartFile`
- Apply MVC cross-cutting: `HandlerInterceptor` and user-friendly error pages with `@ControllerAdvice`

## Features
- Server-side web application with Thymeleaf templates
- Form handling with validation
- File upload functionality
- Custom error pages
- Request/response logging with interceptors
- Static resource serving

## How to Run

### Prerequisites
- Java 17+
- Maven 3.9+

### Run the Application
```bash
mvn spring-boot:run
```

### Access the Application
- Home page: http://localhost:8089
- About page: http://localhost:8089/about
- Contact form: http://localhost:8089/contact
- File upload: http://localhost:8089/upload
- Error pages: http://localhost:8089/nonexistent (404)

## Pages and Features

### Home Page
- Welcome message with dynamic content
- Navigation menu
- Current date/time display

### Contact Form
- Form with validation
- Name, email, and message fields
- Error message display
- Success confirmation

### File Upload
- File upload form
- File validation (size, type)
- Uploaded files listing
- File download functionality

### Error Pages
- Custom 404 page
- Custom 500 page
- User-friendly error messages

## Architecture
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Browser       │    │  DispatcherServlet │  │  Controllers    │
│                 │◄──►│                 │◄──►│                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │                       │
                                ▼                       ▼
                       ┌─────────────────┐    ┌─────────────────┐
                       │  ViewResolver   │    │  Services       │
                       │                 │    │                 │
                       └─────────────────┘    └─────────────────┘
                                │
                                ▼
                       ┌─────────────────┐
                       │  Thymeleaf      │
                       │  Templates      │
                       └─────────────────┘
```

## Links
- [Spring MVC (GeeksforGeeks)](https://www.geeksforgeeks.org/java/spring-mvc/)
- [Spring Framework Reference: Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Serving Web Content with Spring MVC (Guide)](https://spring.io/guides/gs/serving-web-content/)
