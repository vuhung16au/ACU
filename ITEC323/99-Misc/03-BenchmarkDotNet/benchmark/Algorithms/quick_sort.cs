namespace BenchmarkDotNetSortComparer.Algorithms;

public static class QuickSort
{
    private const int InsertionSortThreshold = 10;

    public static void Sort(int[] arr)
    {
        if (arr.Length <= 1)
        {
            return;
        }

        QuickSortRecursive(arr, 0, arr.Length - 1);
    }

    private static void QuickSortRecursive(int[] arr, int low, int high)
    {
        while (high - low > InsertionSortThreshold)
        {
            var pivotIndex = Partition(arr, low, high);

            if (pivotIndex - low < high - pivotIndex)
            {
                QuickSortRecursive(arr, low, pivotIndex - 1);
                low = pivotIndex + 1;
            }
            else
            {
                QuickSortRecursive(arr, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }

        InsertionSortRange(arr, low, high);
    }

    private static int Partition(int[] arr, int low, int high)
    {
        var pivot = arr[high];
        var i = low - 1;

        for (var j = low; j < high; j++)
        {
            if (arr[j] > pivot)
            {
                continue;
            }

            i++;
            (arr[i], arr[j]) = (arr[j], arr[i]);
        }

        (arr[i + 1], arr[high]) = (arr[high], arr[i + 1]);
        return i + 1;
    }

    private static void InsertionSortRange(int[] arr, int low, int high)
    {
        for (var i = low + 1; i <= high; i++)
        {
            var key = arr[i];
            var j = i - 1;

            while (j >= low && arr[j] > key)
            {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }
}
