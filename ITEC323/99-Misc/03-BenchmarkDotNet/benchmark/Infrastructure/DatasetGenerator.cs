using System.Globalization;

namespace BenchmarkDotNetSortComparer.Infrastructure;

public static class DatasetGenerator
{
    private static readonly int[] DefaultSizes = [1000, 3000, 5000, 10000, 50000, 100000, 500000, 1000000];

    public static IReadOnlyList<string> GenerateDefaultDatasets(string datasetsDirectory, int seed = 323)
    {
        return GenerateDatasets(datasetsDirectory, DefaultSizes, -100_000, 100_000, seed);
    }

    public static IReadOnlyList<string> GenerateDatasets(
        string datasetsDirectory,
        IEnumerable<int> sizes,
        int minValue,
        int maxValue,
        int seed)
    {
        Directory.CreateDirectory(datasetsDirectory);

        var random = new Random(seed);
        var files = new List<string>();

        foreach (var size in sizes)
        {
            var filePath = Path.Combine(datasetsDirectory, $"random_{size}.txt");
            using var writer = new StreamWriter(filePath, false);

            for (var i = 0; i < size; i++)
            {
                var value = random.Next(minValue, maxValue + 1);
                writer.WriteLine(value.ToString(CultureInfo.InvariantCulture));
            }

            files.Add(filePath);
        }

        var manifestPath = Path.Combine(datasetsDirectory, "manifest.txt");
        File.WriteAllLines(manifestPath, files.Select(Path.GetFileName)!);

        return files;
    }
}
