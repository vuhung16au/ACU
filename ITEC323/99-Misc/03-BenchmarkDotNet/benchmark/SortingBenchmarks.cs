using BenchmarkDotNet.Attributes;
using BenchmarkDotNet.Jobs;
using BenchmarkDotNetSortComparer.Algorithms;
using BenchmarkDotNetSortComparer.Infrastructure;

namespace BenchmarkDotNetSortComparer;

[MemoryDiagnoser]
[SimpleJob(RuntimeMoniker.Net90, baseline: true)]
[SimpleJob(RuntimeMoniker.Net10_0)]
public class SortingBenchmarks
{
    private int[] _source = [];

    [ParamsSource(nameof(DatasetFiles))]
    public string DatasetFileName { get; set; } = string.Empty;

    public IEnumerable<string> DatasetFiles => DatasetLoader.GetDatasetFileNames(DatasetLoader.ResolveProjectRoot());

    [GlobalSetup]
    public void Setup()
    {
        var projectRoot = DatasetLoader.ResolveProjectRoot();
        _source = DatasetLoader.LoadByName(projectRoot, DatasetFileName);
    }

    [Benchmark]
    public void BubbleSortBenchmark()
    {
        if (_source.Length > 10000) return;
        var copy = (int[])_source.Clone();
        BubbleSort.Sort(copy);
    }

    [Benchmark]
    public void CountingSortBenchmark()
    {
        var copy = (int[])_source.Clone();
        CountingSort.Sort(copy);
    }

    [Benchmark]
    public void InsertionSortBenchmark()
    {
        if (_source.Length > 10000) return;
        var copy = (int[])_source.Clone();
        InsertionSort.Sort(copy);
    }

    [Benchmark]
    public void MergeSortBenchmark()
    {
        var copy = (int[])_source.Clone();
        MergeSort.Sort(copy);
    }

    [Benchmark]
    public void QuickSortBenchmark()
    {
        var copy = (int[])_source.Clone();
        QuickSort.Sort(copy);
    }

    [Benchmark]
    public void RadixSortBenchmark()
    {
        var copy = (int[])_source.Clone();
        RadixSort.Sort(copy);
    }

    [Benchmark]
    public void SelectionSortBenchmark()
    {
        if (_source.Length > 10000) return;
        var copy = (int[])_source.Clone();
        SelectionSort.Sort(copy);
    }
}
