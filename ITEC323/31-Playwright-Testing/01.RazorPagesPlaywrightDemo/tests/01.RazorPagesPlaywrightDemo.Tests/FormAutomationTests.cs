using System.Diagnostics;
using FluentAssertions;
using Microsoft.Playwright;
using Xunit;

namespace RazorPagesPlaywrightDemo.Tests;

/// <summary>
/// Contains Playwright tests for the sample Razor Pages form.
/// </summary>
public class FormAutomationTests
{
    private const string ArtifactFileStem = "playwright-form-demo";
    private static readonly TimeSpan StepPause = TimeSpan.FromMilliseconds(900);
    private static readonly TimeSpan ResultPause = TimeSpan.FromMilliseconds(1400);
    private const float GifFramesPerSecond = 1.0f;

    /// <summary>
    /// Submits the form, checks the result, and saves test artifacts.
    /// </summary>
    /// <returns>A task that represents the asynchronous test.</returns>
    [Fact]
    public async Task SubmitForm_WithSampleInput_DisplaysSubmittedValuesAndCreatesArtifacts()
    {
        var artifactRoot = GetArtifactRootDirectory();
        var temporaryVideoDirectory = Path.Combine(artifactRoot, "temporary-videos");
        var screenshotPath = Path.Combine(artifactRoot, $"{ArtifactFileStem}.png");
        var savedVideoPath = Path.Combine(artifactRoot, $"{ArtifactFileStem}.webm");
        var savedGifPath = Path.Combine(artifactRoot, $"{ArtifactFileStem}.gif");

        Directory.CreateDirectory(artifactRoot);
        Directory.CreateDirectory(temporaryVideoDirectory);
        DeleteFileIfExists(screenshotPath);
        DeleteFileIfExists(savedVideoPath);
        DeleteFileIfExists(savedGifPath);

        await using var application = await RazorPagesApplication.StartAsync();
        using var playwright = await Playwright.CreateAsync();
        var browser = await playwright.Chromium.LaunchAsync(new BrowserTypeLaunchOptions
        {
            Headless = true
        });

        try
        {
            var context = await browser.NewContextAsync(new BrowserNewContextOptions
            {
                RecordVideoDir = temporaryVideoDirectory,
                RecordVideoSize = new RecordVideoSize
                {
                    Width = 1280,
                    Height = 720
                }
            });

            var page = await context.NewPageAsync();

            await page.GotoAsync(application.BaseUrl);
            await page.WaitForTimeoutAsync((float)StepPause.TotalMilliseconds);
            await page.GetByLabel("Name").PressSequentiallyAsync("Ada Lovelace", new LocatorPressSequentiallyOptions
            {
                Delay = 120
            });
            await page.WaitForTimeoutAsync((float)StepPause.TotalMilliseconds);
            await page.GetByLabel("Email").PressSequentiallyAsync("ada@example.com", new LocatorPressSequentiallyOptions
            {
                Delay = 110
            });
            await page.WaitForTimeoutAsync((float)StepPause.TotalMilliseconds);
            await page.GetByLabel("Favourite Language").SelectOptionAsync(new[] { "C#" });
            await page.WaitForTimeoutAsync((float)StepPause.TotalMilliseconds);
            await page.GetByRole(AriaRole.Button, new() { Name = "Submit" }).ClickAsync();

            var submittedValues = page.Locator("#submitted-values");
            await submittedValues.WaitForAsync();
            await page.WaitForTimeoutAsync((float)ResultPause.TotalMilliseconds);

            (await submittedValues.TextContentAsync()).Should().Contain("Ada Lovelace");
            (await submittedValues.TextContentAsync()).Should().Contain("ada@example.com");
            (await submittedValues.TextContentAsync()).Should().Contain("C#");

            await page.ScreenshotAsync(new PageScreenshotOptions
            {
                Path = screenshotPath,
                FullPage = true
            });
            await page.WaitForTimeoutAsync((float)ResultPause.TotalMilliseconds);

            if (page.Video is not null)
            {
                await page.CloseAsync();
                await context.CloseAsync();
                await page.Video.SaveAsAsync(savedVideoPath);
                await TryCreateGifAsync(savedVideoPath, savedGifPath);
            }
            else
            {
                await page.CloseAsync();
                await context.CloseAsync();
            }

            File.Exists(screenshotPath).Should().BeTrue();
            File.Exists(savedVideoPath).Should().BeTrue();
        }
        finally
        {
            await browser.CloseAsync();
        }
    }

    /// <summary>
    /// Converts a recorded webm file into a gif when ffmpeg is available.
    /// </summary>
    /// <param name="webmPath">The source webm video path.</param>
    /// <param name="gifPath">The destination gif path.</param>
    /// <returns>A task that represents the asynchronous conversion attempt.</returns>
    private static async Task TryCreateGifAsync(string webmPath, string gifPath)
    {
        if (!await IsToolAvailableAsync("ffmpeg"))
        {
            return;
        }

        var startInfo = new ProcessStartInfo
        {
            FileName = "ffmpeg",
            RedirectStandardOutput = true,
            RedirectStandardError = true,
            UseShellExecute = false
        };

        startInfo.ArgumentList.Add("-y");
        startInfo.ArgumentList.Add("-i");
        startInfo.ArgumentList.Add(webmPath);
        startInfo.ArgumentList.Add("-vf");
        startInfo.ArgumentList.Add($"fps={GifFramesPerSecond.ToString(System.Globalization.CultureInfo.InvariantCulture)},scale=960:-1:flags=lanczos");
        startInfo.ArgumentList.Add(gifPath);

        using var process = Process.Start(startInfo);
        if (process is null)
        {
            return;
        }

        await process.WaitForExitAsync();
    }

    /// <summary>
    /// Checks whether a command line tool is available on the current machine.
    /// </summary>
    /// <param name="toolName">The tool to search for.</param>
    /// <returns><c>true</c> when the tool is available; otherwise, <c>false</c>.</returns>
    private static async Task<bool> IsToolAvailableAsync(string toolName)
    {
        var lookupCommand = OperatingSystem.IsWindows() ? "where" : "which";
        var startInfo = new ProcessStartInfo
        {
            FileName = lookupCommand,
            RedirectStandardOutput = true,
            RedirectStandardError = true,
            UseShellExecute = false
        };

        startInfo.ArgumentList.Add(toolName);

        using var process = Process.Start(startInfo);
        if (process is null)
        {
            return false;
        }

        await process.WaitForExitAsync();
        return process.ExitCode == 0;
    }

    /// <summary>
    /// Gets the directory where screenshots and recordings are saved.
    /// </summary>
    /// <returns>The artifact output directory.</returns>
    private static string GetArtifactRootDirectory()
    {
        var configuredDirectory = Environment.GetEnvironmentVariable("PLAYWRIGHT_ARTIFACTS_DIR");
        if (!string.IsNullOrWhiteSpace(configuredDirectory))
        {
            return configuredDirectory;
        }

        return Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", "..", "artifacts"));
    }

    /// <summary>
    /// Deletes a file when it already exists.
    /// </summary>
    /// <param name="filePath">The file to remove.</param>
    private static void DeleteFileIfExists(string filePath)
    {
        if (File.Exists(filePath))
        {
            File.Delete(filePath);
        }
    }
}
