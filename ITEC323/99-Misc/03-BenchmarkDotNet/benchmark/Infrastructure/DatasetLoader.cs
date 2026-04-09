using System.Globalization;

namespace BenchmarkDotNetSortComparer.Infrastructure;

public static class DatasetLoader
{
    private const string BenchmarkRootEnvironmentVariable = "BENCHMARK_PROJECT_ROOT";

    public static string ResolveProjectRoot(string? fallbackWorkingDirectory = null)
    {
        var envRoot = Environment.GetEnvironmentVariable(BenchmarkRootEnvironmentVariable);
        if (!string.IsNullOrWhiteSpace(envRoot) && Directory.Exists(envRoot))
        {
            return envRoot;
        }

        var current = new DirectoryInfo(fallbackWorkingDirectory ?? Directory.GetCurrentDirectory());
        while (current is not null)
        {
            var artifactsDirectory = Path.Combine(current.FullName, "artifacts", "datasets");
            var makefilePath = Path.Combine(current.FullName, "Makefile");
            if (Directory.Exists(artifactsDirectory) || File.Exists(makefilePath))
            {
                return current.FullName;
            }

            current = current.Parent;
        }

        return fallbackWorkingDirectory ?? Directory.GetCurrentDirectory();
    }

    public static string ResolveDatasetsDirectory(string workingDirectory)
    {
        return Path.Combine(workingDirectory, "artifacts", "datasets");
    }

    public static IReadOnlyList<string> GetDatasetFileNames(string workingDirectory)
    {
        var datasetsDirectory = ResolveDatasetsDirectory(workingDirectory);
        if (!Directory.Exists(datasetsDirectory))
        {
            return [];
        }

        return Directory
            .EnumerateFiles(datasetsDirectory, "*.txt", SearchOption.TopDirectoryOnly)
            .Select(Path.GetFileName)
            .Where(name => !string.Equals(name, "manifest.txt", StringComparison.OrdinalIgnoreCase))
            .OrderBy(name => name, StringComparer.OrdinalIgnoreCase)
            .ToArray()!;
    }

    public static int[] LoadByName(string workingDirectory, string fileName)
    {
        var path = Path.Combine(ResolveDatasetsDirectory(workingDirectory), fileName);
        if (!File.Exists(path))
        {
            throw new FileNotFoundException($"Dataset file was not found: {path}");
        }

        var values = new List<int>();
        foreach (var line in File.ReadLines(path))
        {
            if (int.TryParse(line, NumberStyles.Integer, CultureInfo.InvariantCulture, out var value))
            {
                values.Add(value);
            }
        }

        return values.ToArray();
    }
}
