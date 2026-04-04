namespace RawSQLDapper.Models;

/// <summary>
/// Holds the reports page content.
/// </summary>
public class ReportsPageViewModel
{
    /// <summary>
    /// Gets or sets the EF Core results.
    /// </summary>
    public IReadOnlyList<SalesReportRow> EfCoreRows { get; set; } = [];

    /// <summary>
    /// Gets or sets the Dapper results.
    /// </summary>
    public IReadOnlyList<SalesReportRow> DapperRows { get; set; } = [];

    /// <summary>
    /// Gets or sets the EF Core generated SQL.
    /// </summary>
    public string EfCoreSql { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the Dapper SQL.
    /// </summary>
    public string DapperSql { get; set; } = string.Empty;
}
