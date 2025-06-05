using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Linq;

class MergeSort
{
    public static void Sort(List<int> arr)
    {
        var temp = new int[arr.Count];
        MergeSortRecursive(arr, temp, 0, arr.Count - 1);
    }

    private static void MergeSortRecursive(List<int> arr, int[] temp, int left, int right)
    {
        if (left < right)
        {
            int mid = left + (right - left) / 2;
            MergeSortRecursive(arr, temp, left, mid);
            MergeSortRecursive(arr, temp, mid + 1, right);
            Merge(arr, temp, left, mid, right);
        }
    }

    private static void Merge(List<int> arr, int[] temp, int left, int mid, int right)
    {
        for (int i = left; i <= right; i++)
            temp[i] = arr[i];
        int iL = left, iR = mid + 1, k = left;
        while (iL <= mid && iR <= right)
        {
            if (temp[iL] <= temp[iR])
                arr[k++] = temp[iL++];
            else
                arr[k++] = temp[iR++];
        }
        while (iL <= mid)
            arr[k++] = temp[iL++];
        // No need to copy right half, already in place
    }
}

class MergeSortMain
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
        Console.WriteLine("C# Merge Sort Performance Test");
        Console.WriteLine("=============================");

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

        Console.WriteLine("Starting Merge Sort...");
        var stopwatch = Stopwatch.StartNew();
        MergeSort.Sort(dataCopy);
        stopwatch.Stop();
        double executionTime = stopwatch.Elapsed.TotalSeconds;

        bool sorted = IsSorted(dataCopy);

        Console.WriteLine($"Sorting completed: {(sorted ? "SUCCESS" : "FAILED")}");
        Console.WriteLine($"Execution time: {executionTime:F6} seconds");
        Console.WriteLine($"Elements per second: {(executionTime > 0 ? data.Count / executionTime : 0):F0}");

        using (var writer = new StreamWriter(resultFilename))
        {
            writer.WriteLine("C# Merge Sort Results");
            writer.WriteLine($"Data size: {data.Count}");
            writer.WriteLine($"Execution time: {executionTime:F6} seconds");
            writer.WriteLine($"Elements per second: {(executionTime > 0 ? data.Count / executionTime : 0):F0}");
            writer.WriteLine($"Sorted correctly: {(sorted ? "true" : "false")}");
        }
        Console.WriteLine($"Results saved to {resultFilename}");
    }
} 