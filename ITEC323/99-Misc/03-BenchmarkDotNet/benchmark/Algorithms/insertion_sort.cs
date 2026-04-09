namespace BenchmarkDotNetSortComparer.Algorithms;

public static class InsertionSort
{
    public static void Sort(int[] arr)
    {
        for (var i = 1; i < arr.Length; i++)
        {
            var key = arr[i];
            var j = i - 1;

            while (j >= 0 && arr[j] > key)
            {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }
}
