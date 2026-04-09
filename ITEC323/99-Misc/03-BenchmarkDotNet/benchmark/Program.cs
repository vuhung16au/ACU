using BenchmarkDotNet.Running;
using BenchmarkDotNetSortComparer;
using BenchmarkDotNetSortComparer.Algorithms;
using BenchmarkDotNetSortComparer.Infrastructure;

var command = args.Length == 0 ? "benchmark" : args[0].ToLowerInvariant();
var workingDirectory = Directory.GetCurrentDirectory();
var datasetsDirectory = DatasetLoader.ResolveDatasetsDirectory(workingDirectory);

switch (command)
{
    case "dataset":
        {
            var files = DatasetGenerator.GenerateDefaultDatasets(datasetsDirectory);
            Console.WriteLine("Datasets created:");
            foreach (var file in files)
            {
                Console.WriteLine($"- {file}");
            }

            break;
        }
    case "run":
        {
            EnsureDatasetsExist(datasetsDirectory);
            ValidateAlgorithms(workingDirectory);
            break;
        }
    case "benchmark":
        {
            EnsureDatasetsExist(datasetsDirectory);
            Environment.SetEnvironmentVariable("BENCHMARK_PROJECT_ROOT", workingDirectory);
            var config = new SortBenchmarkConfig(workingDirectory);
            var summary = BenchmarkRunner.Run<SortingBenchmarks>(config);
            var reportPath = ReportPublisher.Publish(summary, workingDirectory);
            Console.WriteLine($"HTML report generated: {reportPath}");
            break;
        }
    default:
        {
            Console.WriteLine("Usage:");
            Console.WriteLine("  dotnet run --project benchmark -- dataset");
            Console.WriteLine("  dotnet run --project benchmark -- run");
            Console.WriteLine("  dotnet run --project benchmark -- benchmark");
            Environment.ExitCode = 1;
            break;
        }
}

static void EnsureDatasetsExist(string datasetsDirectory)
{
    var hasDatasets = Directory.Exists(datasetsDirectory)
        && Directory.EnumerateFiles(datasetsDirectory, "random_*.txt").Any();

    if (hasDatasets)
    {
        return;
    }

    DatasetGenerator.GenerateDefaultDatasets(datasetsDirectory);
}

static void ValidateAlgorithms(string workingDirectory)
{
    var data = DatasetLoader.LoadByName(workingDirectory, "random_1000.txt");

    var cases = new Dictionary<string, Action<int[]>>
    {
        ["bubble_sort"] = BubbleSort.Sort,
        ["counting_sort"] = CountingSort.Sort,
        ["insertion_sort"] = InsertionSort.Sort,
        ["merge_sort"] = MergeSort.Sort,
        ["quick_sort"] = QuickSort.Sort,
        ["radix_sort"] = RadixSort.Sort,
        ["selection_sort"] = SelectionSort.Sort
    };

    foreach (var (name, action) in cases)
    {
        var copy = (int[])data.Clone();
        action(copy);

        if (!IsSorted(copy))
        {
            throw new InvalidOperationException($"Validation failed for {name}");
        }

        Console.WriteLine($"Validation passed: {name}");
    }
}

static bool IsSorted(int[] arr)
{
    for (var i = 1; i < arr.Length; i++)
    {
        if (arr[i - 1] > arr[i])
        {
            return false;
        }
    }

    return true;
}
