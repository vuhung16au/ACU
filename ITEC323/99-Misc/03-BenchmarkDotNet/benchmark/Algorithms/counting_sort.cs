namespace BenchmarkDotNetSortComparer.Algorithms;

public static class CountingSort
{
    public static void Sort(int[] arr)
    {
        if (arr.Length == 0)
        {
            return;
        }

        var min = arr[0];
        var max = arr[0];

        for (var i = 1; i < arr.Length; i++)
        {
            if (arr[i] < min)
            {
                min = arr[i];
            }

            if (arr[i] > max)
            {
                max = arr[i];
            }
        }

        var range = max - min + 1;
        var count = new int[range];

        foreach (var value in arr)
        {
            count[value - min]++;
        }

        var index = 0;
        for (var i = 0; i < range; i++)
        {
            while (count[i] > 0)
            {
                arr[index++] = i + min;
                count[i]--;
            }
        }
    }
}
