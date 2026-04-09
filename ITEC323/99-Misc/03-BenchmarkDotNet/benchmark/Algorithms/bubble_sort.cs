namespace BenchmarkDotNetSortComparer.Algorithms;

public static class BubbleSort
{
    public static void Sort(int[] arr)
    {
        for (var i = 0; i < arr.Length - 1; i++)
        {
            var swapped = false;
            for (var j = 0; j < arr.Length - i - 1; j++)
            {
                if (arr[j] <= arr[j + 1])
                {
                    continue;
                }

                (arr[j], arr[j + 1]) = (arr[j + 1], arr[j]);
                swapped = true;
            }

            if (!swapped)
            {
                break;
            }
        }
    }
}
