namespace BenchmarkDotNetSortComparer.Algorithms;

public static class MergeSort
{
    public static void Sort(int[] arr)
    {
        if (arr.Length <= 1)
        {
            return;
        }

        var temp = new int[arr.Length];
        MergeSortRecursive(arr, temp, 0, arr.Length - 1);
    }

    private static void MergeSortRecursive(int[] arr, int[] temp, int left, int right)
    {
        if (left >= right)
        {
            return;
        }

        var mid = left + (right - left) / 2;
        MergeSortRecursive(arr, temp, left, mid);
        MergeSortRecursive(arr, temp, mid + 1, right);
        Merge(arr, temp, left, mid, right);
    }

    private static void Merge(int[] arr, int[] temp, int left, int mid, int right)
    {
        Array.Copy(arr, left, temp, left, right - left + 1);

        var iLeft = left;
        var iRight = mid + 1;
        var current = left;

        while (iLeft <= mid && iRight <= right)
        {
            if (temp[iLeft] <= temp[iRight])
            {
                arr[current++] = temp[iLeft++];
            }
            else
            {
                arr[current++] = temp[iRight++];
            }
        }

        while (iLeft <= mid)
        {
            arr[current++] = temp[iLeft++];
        }
    }
}
