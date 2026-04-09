using BenchmarkDotNet.Configs;
using BenchmarkDotNet.Diagnosers;
using BenchmarkDotNet.Exporters;
using BenchmarkDotNet.Loggers;

namespace BenchmarkDotNetSortComparer;

public sealed class SortBenchmarkConfig : ManualConfig
{
    public SortBenchmarkConfig(string workingDirectory)
    {
        ArtifactsPath = Path.Combine(workingDirectory, "artifacts", "benchmarkdotnet");

        AddLogger(ConsoleLogger.Default);
        AddExporter(MarkdownExporter.GitHub);
        AddDiagnoser(MemoryDiagnoser.Default);
    }
}
