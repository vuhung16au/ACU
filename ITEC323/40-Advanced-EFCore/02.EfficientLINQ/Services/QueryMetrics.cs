namespace EfficientLINQ.Services;

/// <summary>
/// Provides simple teaching-friendly metrics for comparing query shapes.
/// </summary>
public static class QueryMetrics
{
    /// <summary>
    /// Calculates an estimated payload cell count.
    /// </summary>
    /// <param name="rowCount">The number of rows.</param>
    /// <param name="columnCount">The number of selected columns.</param>
    /// <returns>The estimated number of cells moved from the database.</returns>
    public static int EstimatePayloadCells(int rowCount, int columnCount)
    {
        return rowCount * columnCount;
    }
}
