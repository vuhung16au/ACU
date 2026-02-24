# Week4Lab2Assignment – Quick Start

## 1. Prerequisites

- **.NET 10.0 SDK** installed  
  - You can check your version in a terminal with:
    - `dotnet --version`
- A code editor or IDE such as **Visual Studio**, **Visual Studio Code**, or **Rider**
- A web browser (Edge, Chrome, Firefox, etc.)

## 2. Getting the Code

1. Open a terminal.
2. Change into the repository folder (if you are not there already):
   - `cd ITEC323`
3. Change into the assignment folder:
   - `cd Week4Lab2Assignment`

All commands in this guide assume you are running them from the `Week4Lab2Assignment` folder.

## 3. Build the Project

Run:

```bash
dotnet build
```

You should see a `Build succeeded.` message. If there are errors, read them carefully and fix any typos in your code.

## 4. Run the Web Application

From the same folder, run:

```bash
dotnet run
```

After a few seconds, the console will show one or more URLs, for example:

```text
Now listening on: https://localhost:5001
Now listening on: http://localhost:5000
```

## 5. Use the Application in Your Browser

1. Open a browser.
2. Navigate to one of the URLs shown in the console (for example `https://localhost:5001`).
3. You should see the **Week 4 Lab 2 Assignment** page.
4. Try the following:
   - Type your name in the input box.
   - Choose **C#**, **Java**, or **Python** from the dropdown.
   - Click **Submit**.
   - Check that the page shows the sentence:

     > Your name is &lt;name&gt; and your favourite programming language is &lt;language&gt;

## 6. Run the Unit Tests (Optional but Recommended)

This assignment includes a small test project that checks the helper method used to build the message.

1. From the `Week4Lab2Assignment` folder, run:

   ```bash
   dotnet test
   ```

2. You should see a summary indicating that the tests passed.

If tests fail, read the error messages; they will tell you what value was expected and what was actually returned.

## 7. Stopping the Application

- To stop the running web application, go back to the terminal where `dotnet run` is running and press:
  - `Ctrl + C`

You can start it again any time by running `dotnet run` from the `Week4Lab2Assignment` folder.

