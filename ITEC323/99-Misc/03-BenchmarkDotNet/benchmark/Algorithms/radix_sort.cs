namespace BenchmarkDotNetSortComparer.Algorithms;

public static class RadixSort
{
    public static void Sort(int[] arr)
    {
        if (arr.Length <= 1)
        {
            return;
        }

        var negatives = arr.Where(n => n < 0).Select(n => -n).ToArray();
        var positives = arr.Where(n => n >= 0).ToArray();

        RadixSortNonNegative(negatives);
        RadixSortNonNegative(positives);

        var index = 0;
        for (var i = negatives.Length - 1; i >= 0; i--)
        {
            arr[index++] = -negatives[i];
        }

        for (var i = 0; i < positives.Length; i++)
        {
            arr[index++] = positives[i];
        }
    }

    private static void RadixSortNonNegative(int[] arr)
    {
        if (arr.Length == 0)
        {
            return;
        }

        var max = arr.Max();
        for (var exp = 1; max / exp > 0; exp *= 10)
        {
            CountingSortByDigit(arr, exp);
        }
    }

    private static void CountingSortByDigit(int[] arr, int exp)
    {
        var output = new int[arr.Length];
        var count = new int[10];

        for (var i = 0; i < arr.Length; i++)
        {
            count[(arr[i] / exp) % 10]++;
        }

        for (var i = 1; i < count.Length; i++)
        {
            count[i] += count[i - 1];
        }

        for (var i = arr.Length - 1; i >= 0; i--)
        {
            var digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        Array.Copy(output, arr, arr.Length);
    }
}
