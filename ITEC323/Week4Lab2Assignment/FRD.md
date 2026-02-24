# Week4Lab2Assignment – Functional Requirements Document (FRD)

## 1. Purpose

The purpose of this assignment is to help students learn the basics of building a **simple ASP.NET Core web application** using **Razor Pages**.  
The application collects a user's name and their favourite programming language, then displays this information back to the user in a clear sentence.

## 2. Scope

This FRD applies only to the **Week4Lab2Assignment** project located in the `ITEC323` repository.  
It covers the behaviour of the web page, form handling, and the output shown to the user.  
It does **not** include any database, authentication, or advanced UI requirements.

## 3. Functional Requirements

### FR-1: Name Input

- The application **shall provide** a text input box where the user can enter their **name**.
- The input box **shall be visible** when the page loads.

### FR-2: Programming Language Dropdown

- The application **shall provide** a dropdown list (HTML `<select>`) where the user can choose their **favourite programming language**.
- The dropdown list **shall contain exactly** the following options:
  - `C#`
  - `Java`
  - `Python`

### FR-3: Submit Button

- The application **shall provide** a button that the user can click to **submit** the form.
- When the button is clicked:
  - The form data (name and selected language) **shall be sent** to the server using an HTTP **POST** request.

### FR-4: Message Formatting

- After the form is submitted, the application **shall build** a message using the values entered by the user.
- The message **shall follow exactly** this format:

  > Your name is &lt;name&gt; and your favourite programming language is &lt;language&gt;

- `<name>` **shall be replaced** with the text entered in the name input box.
- `<language>` **shall be replaced** with the language selected in the dropdown list.

### FR-5: Displaying the Result

- After a successful submission:
  - The application **shall display** the full message on the web page.
  - The message **shall appear** beneath the form so the user can see both the form and the result at the same time.

### FR-6: Single-Page Interaction

- All of the above interactions **shall occur on a single page** (no navigation to other pages is required).
- Refreshing the page **shall clear** any previously displayed message.

## 4. Non-Functional Requirements

### NFR-1: Simplicity

- The code **shall be simple and beginner-friendly**:
  - Clear class and method names
  - Minimal number of files
  - No unnecessary abstractions or advanced patterns

### NFR-2: Technology

- The project **shall target** **.NET 10.0**.
- The project **shall use** C# and ASP.NET Core Razor Pages.

### NFR-3: Documentation

- The project **shall include** the following documentation files:
  - `README.md`
  - `QUICKSTART.md`
  - `FRD.md` (this document)
  - `ITEC323-Week4Lab2Assignment-Step-by-Step Guide.md`
- Documentation **shall be written** for students who are new to .NET.

### NFR-4: Testing (Optional but Recommended)

- The project **should include** at least one **unit test** demonstrating how to test a small piece of logic (for example, the message formatting).
- Tests **should be placed** in a separate `tests/` folder using **xUnit**.

## 5. Success Criteria

The assignment will be considered successful if:

1. The project builds successfully with **.NET 10.0**.  
2. Running the project starts a web server and displays the assignment page in a browser.  
3. Entering a name, selecting a language, and clicking **Submit** shows a message in the format:

   > Your name is &lt;name&gt; and your favourite programming language is &lt;language&gt;

4. The required documentation files are present and clearly written.  
5. (Optional) The unit tests run successfully with `dotnet test`.

