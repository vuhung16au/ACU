using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Linq;

class RadixSort
{
    private static int GetMax(List<int> arr)
    {
        int max = arr[0];
        for (int i = 1; i < arr.Count; i++)
            if (arr[i] > max) max = arr[i];
        return max;
    }
    private static int GetMin(List<int> arr)
    {
        int min = arr[0];
        for (int i = 1; i < arr.Count; i++)
            if (arr[i] < min) min = arr[i];
        return min;
    }
    private static void CountSort(List<int> arr, int exp, int offset = 0)
    {
        int n = arr.Count;
        int[] output = new int[n];
        int[] count = new int[10];
        for (int i = 0; i < n; i++)
        {
            int adjusted = arr[i] + offset;
            count[(adjusted / exp) % 10]++;
        }
        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];
        for (int i = n - 1; i >= 0; i--)
        {
            int adjusted = arr[i] + offset;
            output[count[(adjusted / exp) % 10] - 1] = arr[i];
            count[(adjusted / exp) % 10]--;
        }
        for (int i = 0; i < n; i++)
            arr[i] = output[i];
    }
    public static void Sort(List<int> arr)
    {
        if (arr.Count == 0) return;
        int min = GetMin(arr);
        int offset = min < 0 ? -min : 0;
        int max = GetMax(arr) + offset;
        for (int exp = 1; max / exp > 0; exp *= 10)
            CountSort(arr, exp, offset);
    }
}

class RadixSortMain
{
    static List<int> ReadNumbersFromFile(string filename = "random_list.txt")
    {
        var numbers = new List<int>();
        if (!File.Exists(filename))
        {
            Console.Error.WriteLine($"Error: {filename} not found. Please run generate_data.py first.");
            Environment.Exit(1);
        }
        foreach (var line in File.ReadLines(filename))
        {
            if (int.TryParse(line, out int number))
                numbers.Add(number);
        }
        return numbers;
    }

    static bool IsSorted(List<int> arr)
    {
        for (int i = 1; i < arr.Count; i++)
        {
            if (arr[i - 1] > arr[i])
                return false;
        }
        return true;
    }

    public static void Run(string[] args)
    {
        Console.WriteLine("C# Radix Sort Performance Test");
        Console.WriteLine("============================");

        string filename = "random_list.txt";
        string resultFilename = "results_cs.txt";
        if (args.Length > 0)
        {
            filename = args[0];
            Console.WriteLine($"Using dataset: {filename}");
        }
        if (args.Length > 1)
            resultFilename = args[1];

        Console.WriteLine("Reading data from file...");
        var data = ReadNumbersFromFile(filename);
        Console.WriteLine($"Data size: {data.Count} integers");
        var dataCopy = new List<int>(data);

        Console.WriteLine("Starting Radix Sort...");
        var stopwatch = Stopwatch.StartNew();
        RadixSort.Sort(dataCopy);
        stopwatch.Stop();
        double executionTime = stopwatch.Elapsed.TotalSeconds;

        bool sorted = IsSorted(dataCopy);

        Console.WriteLine($"Sorting completed: {(sorted ? "SUCCESS" : "FAILED")}");
        Console.WriteLine($"Execution time: {executionTime:F6} seconds");
        Console.WriteLine($"Elements per second: {(executionTime > 0 ? data.Count / executionTime : 0):F0}");

        using (var writer = new StreamWriter(resultFilename))
        {
            writer.WriteLine("C# Radix Sort Results");
            writer.WriteLine($"Data size: {data.Count}");
            writer.WriteLine($"Execution time: {executionTime:F6} seconds");
            writer.WriteLine($"Elements per second: {(executionTime > 0 ? data.Count / executionTime : 0):F0}");
            writer.WriteLine($"Sorted correctly: {(sorted ? "true" : "false")}");
        }
        Console.WriteLine($"Results saved to {resultFilename}");
    }
} 