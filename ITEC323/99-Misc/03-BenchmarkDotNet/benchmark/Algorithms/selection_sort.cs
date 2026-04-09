namespace BenchmarkDotNetSortComparer.Algorithms;

public static class SelectionSort
{
    public static void Sort(int[] arr)
    {
        for (var i = 0; i < arr.Length - 1; i++)
        {
            var minIndex = i;

            for (var j = i + 1; j < arr.Length; j++)
            {
                if (arr[j] < arr[minIndex])
                {
                    minIndex = j;
                }
            }

            (arr[i], arr[minIndex]) = (arr[minIndex], arr[i]);
        }
    }
}
