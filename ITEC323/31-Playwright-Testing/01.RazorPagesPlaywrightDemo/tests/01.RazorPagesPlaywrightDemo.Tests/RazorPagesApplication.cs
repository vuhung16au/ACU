using System.Diagnostics;
using System.Net.Http;
using System.Net.Sockets;

namespace RazorPagesPlaywrightDemo.Tests;

/// <summary>
/// Starts and stops the sample Razor Pages application for Playwright tests.
/// </summary>
public sealed class RazorPagesApplication : IAsyncDisposable
{
    private readonly Process _process;

    /// <summary>
    /// Initializes a new instance of the <see cref="RazorPagesApplication"/> class.
    /// </summary>
    /// <param name="baseUrl">The local URL used to start the application.</param>
    /// <param name="process">The running application process.</param>
    private RazorPagesApplication(string baseUrl, Process process)
    {
        BaseUrl = baseUrl;
        _process = process;
    }

    /// <summary>
    /// Gets the base URL of the running application.
    /// </summary>
    public string BaseUrl { get; }

    /// <summary>
    /// Starts the Razor Pages application and waits until it is reachable.
    /// </summary>
    /// <returns>A running application wrapper.</returns>
    public static async Task<RazorPagesApplication> StartAsync()
    {
        var port = FindAvailablePort();
        var baseUrl = $"http://127.0.0.1:{port}";
        var projectDirectory = Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", ".."));

        var startInfo = new ProcessStartInfo
        {
            FileName = GetDotNetCommand(),
            WorkingDirectory = projectDirectory,
            RedirectStandardOutput = true,
            RedirectStandardError = true,
            UseShellExecute = false
        };

        startInfo.ArgumentList.Add("run");
        startInfo.ArgumentList.Add("--project");
        startInfo.ArgumentList.Add("01.RazorPagesPlaywrightDemo.csproj");
        startInfo.ArgumentList.Add("--urls");
        startInfo.ArgumentList.Add(baseUrl);

        var process = Process.Start(startInfo)
            ?? throw new InvalidOperationException("Unable to start the Razor Pages application.");

        var application = new RazorPagesApplication(baseUrl, process);
        await application.WaitForServerAsync();

        return application;
    }

    /// <summary>
    /// Stops the running application.
    /// </summary>
    /// <returns>A task that represents the asynchronous dispose operation.</returns>
    public async ValueTask DisposeAsync()
    {
        if (_process.HasExited)
        {
            _process.Dispose();
            return;
        }

        try
        {
            _process.Kill(entireProcessTree: true);
        }
        catch (InvalidOperationException)
        {
        }

        await _process.WaitForExitAsync();
        _process.Dispose();
    }

    /// <summary>
    /// Waits until the web application responds to HTTP requests.
    /// </summary>
    /// <returns>A task that completes when the app is reachable.</returns>
    private async Task WaitForServerAsync()
    {
        using var client = new HttpClient();
        var startedAt = DateTime.UtcNow;

        while (DateTime.UtcNow - startedAt < TimeSpan.FromSeconds(30))
        {
            if (_process.HasExited)
            {
                var errorText = await _process.StandardError.ReadToEndAsync();
                throw new InvalidOperationException($"The Razor Pages application stopped unexpectedly.{Environment.NewLine}{errorText}");
            }

            try
            {
                using var response = await client.GetAsync(BaseUrl);
                if (response.IsSuccessStatusCode)
                {
                    return;
                }
            }
            catch (HttpRequestException)
            {
            }

            await Task.Delay(500);
        }

        throw new TimeoutException("The Razor Pages application did not start within 30 seconds.");
    }

    /// <summary>
    /// Finds a free local TCP port for the test server.
    /// </summary>
    /// <returns>An available local port number.</returns>
    private static int FindAvailablePort()
    {
        using var listener = new TcpListener(System.Net.IPAddress.Loopback, 0);
        listener.Start();
        return ((System.Net.IPEndPoint)listener.LocalEndpoint).Port;
    }

    /// <summary>
    /// Gets the dotnet command path for the current machine.
    /// </summary>
    /// <returns>The dotnet command path.</returns>
    private static string GetDotNetCommand()
    {
        var configuredCommand = Environment.GetEnvironmentVariable("DOTNET_CMD");
        if (!string.IsNullOrWhiteSpace(configuredCommand))
        {
            return configuredCommand;
        }

        if (OperatingSystem.IsMacOS() && File.Exists("/usr/local/share/dotnet/dotnet"))
        {
            return "/usr/local/share/dotnet/dotnet";
        }

        if (OperatingSystem.IsLinux() && File.Exists("/usr/share/dotnet/dotnet"))
        {
            return "/usr/share/dotnet/dotnet";
        }

        return "dotnet";
    }
}
