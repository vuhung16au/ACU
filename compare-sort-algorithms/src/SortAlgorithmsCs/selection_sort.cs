using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Linq;

class SelectionSort
{
    public static void Sort(List<int> arr)
    {
        int n = arr.Count;
        for (int i = 0; i < n - 1; i++)
        {
            int minIndex = i;
            for (int j = i + 1; j < n; j++)
            {
                if (arr[j] < arr[minIndex])
                    minIndex = j;
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
}

class SelectionSortMain
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
        Console.WriteLine("C# Selection Sort Performance Test");
        Console.WriteLine("===============================");

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

        Console.WriteLine("Starting Selection Sort...");
        var stopwatch = Stopwatch.StartNew();
        SelectionSort.Sort(dataCopy);
        stopwatch.Stop();
        double executionTime = stopwatch.Elapsed.TotalSeconds;

        bool sorted = IsSorted(dataCopy);

        Console.WriteLine($"Sorting completed: {(sorted ? "SUCCESS" : "FAILED")}");
        Console.WriteLine($"Execution time: {executionTime:F6} seconds");
        Console.WriteLine($"Elements per second: {(executionTime > 0 ? data.Count / executionTime : 0):F0}");

        using (var writer = new StreamWriter(resultFilename))
        {
            writer.WriteLine("C# Selection Sort Results");
            writer.WriteLine($"Data size: {data.Count}");
            writer.WriteLine($"Execution time: {executionTime:F6} seconds");
            writer.WriteLine($"Elements per second: {(executionTime > 0 ? data.Count / executionTime : 0):F0}");
            writer.WriteLine($"Sorted correctly: {(sorted ? "true" : "false")}");
        }
        Console.WriteLine($"Results saved to {resultFilename}");
    }
} 