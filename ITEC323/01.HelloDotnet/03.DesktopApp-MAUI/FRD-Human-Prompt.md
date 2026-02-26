
### Step 1: Install the MAUI Workload

Ensure your .NET 10 SDK has the MAUI tools installed.

```bash
dotnet workload install maui

```

### Step 2: Create the Project

Generate your new application using the `maui` template.

```bash
dotnet new maui -n HelloWorldMaui
cd HelloWorldMaui

```

### Step 3: Simplify the UI (The XAML File)

Open the project in your code editor, navigate to `MainPage.xaml`, and replace the boilerplate with this minimal layout:

```xml
<?xml version="1.0" encoding="utf-8" ?>
<ContentPage xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             x:Class="HelloWorldMaui.MainPage">

    <VerticalStackLayout Spacing="25" Padding="30" VerticalOptions="Center">

        <Label Text="Hello, .NET 10 Desktop World!"
               FontSize="32"
               HorizontalOptions="Center" />

        <Label Text="Welcome to my first .NET 10 MAUI app."
               FontSize="18"
               HorizontalOptions="Center" />

    </VerticalStackLayout>

</ContentPage>

```

### Step 4: Run the .NET 10 Desktop Application

Here is where the .NET 10 specific changes happen. When compiling, you must target the `net10.0` frameworks.

Run the command that matches your current operating system:

**For Windows:**

```bash
dotnet build -t:Run -f net10.0-windows10.0.19041.0

```

**For macOS (Mac Catalyst):**

```bash
dotnet build -t:Run -f net10.0-maccatalyst

```

The compiler will build the native .NET 10 desktop executable and launch the window directly on your screen!

