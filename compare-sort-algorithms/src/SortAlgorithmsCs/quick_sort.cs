using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Linq;

class QuickSort
{
    private const int INSERTION_SORT_THRESHOLD = 10;

    private static void InsertionSort(List<int> arr, int low, int high)
    {
        for (int i = low + 1; i <= high; i++)
        {
            int key = arr[i];
            int j = i - 1;
            while (j >= low && arr[j] > key)
            {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static int ChoosePivot(List<int> arr, int low, int high)
    {
        int mid = low + (high - low) / 2;
        if (arr[low] > arr[mid]) Swap(arr, low, mid);
        if (arr[low] > arr[high]) Swap(arr, low, high);
        if (arr[mid] > arr[high]) Swap(arr, mid, high);
        Swap(arr, mid, high - 1);
        return arr[high - 1];
    }

    private static void Swap(List<int> arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static (int, int) Partition(List<int> arr, int low, int high)
    {
        int pivot = ChoosePivot(arr, low, high);
        int lt = low;
        int gt = high - 1;
        int i = low;
        while (i <= gt)
        {
            if (arr[i] < pivot)
                Swap(arr, lt++, i++);
            else if (arr[i] > pivot)
                Swap(arr, i, gt--);
            else
                i++;
        }
        return (lt, gt);
    }

    private static void QuickSortRecursive(List<int> arr, int low, int high)
    {
        while (high - low > INSERTION_SORT_THRESHOLD)
        {
            var (lt, gt) = Partition(arr, low, high);
            if (lt - low < high - gt)
            {
                QuickSortRecursive(arr, low, lt - 1);
                low = gt + 1;
            }
            else
            {
                QuickSortRecursive(arr, gt + 1, high);
                high = lt - 1;
            }
        }
        InsertionSort(arr, low, high);
    }

    public static void Sort(List<int> arr)
    {
        if (arr.Count <= 1) return;
        QuickSortRecursive(arr, 0, arr.Count - 1);
    }
}

class QuickSortMain
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
        Console.WriteLine("C# Quick Sort Performance Test");
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

        Console.WriteLine("Starting Quick Sort...");
        var stopwatch = Stopwatch.StartNew();
        QuickSort.Sort(dataCopy);
        stopwatch.Stop();
        double executionTime = stopwatch.Elapsed.TotalSeconds;

        bool sorted = IsSorted(dataCopy);

        Console.WriteLine($"Sorting completed: {(sorted ? "SUCCESS" : "FAILED")}");
        Console.WriteLine($"Execution time: {executionTime:F6} seconds");
        Console.WriteLine($"Elements per second: {(executionTime > 0 ? data.Count / executionTime : 0):F0}");

        using (var writer = new StreamWriter(resultFilename))
        {
            writer.WriteLine("C# Quick Sort Results");
            writer.WriteLine($"Data size: {data.Count}");
            writer.WriteLine($"Execution time: {executionTime:F6} seconds");
            writer.WriteLine($"Elements per second: {(executionTime > 0 ? data.Count / executionTime : 0):F0}");
            writer.WriteLine($"Sorted correctly: {(sorted ? "true" : "false")}");
        }
        Console.WriteLine($"Results saved to {resultFilename}");
    }
} 