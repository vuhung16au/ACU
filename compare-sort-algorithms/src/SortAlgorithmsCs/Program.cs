using System;
using System.Linq;

class Program
{
    public static void Main(string[] args)
    {
        if (args.Length == 0)
        {
            Console.WriteLine("Usage: dotnet run --project src/SortAlgorithmsCs -- <algorithm> [args...]");
            Console.WriteLine("Algorithms: bubble, insertion, selection, merge, quick, counting, radix");
            Environment.Exit(1);
        }
        string algo = args[0].ToLower();
        string[] passArgs = args.Skip(1).ToArray();
        switch (algo)
        {
            case "bubble":
                BubbleSortMain.Run(passArgs);
                break;
            case "insertion":
                InsertionSortMain.Run(passArgs);
                break;
            case "selection":
                SelectionSortMain.Run(passArgs);
                break;
            case "merge":
                MergeSortMain.Run(passArgs);
                break;
            case "quick":
                QuickSortMain.Run(passArgs);
                break;
            case "counting":
                CountingSortMain.Run(passArgs);
                break;
            case "radix":
                RadixSortMain.Run(passArgs);
                break;
            default:
                Console.WriteLine($"Unknown algorithm: {algo}");
                Environment.Exit(1);
                break;
        }
    }
}
