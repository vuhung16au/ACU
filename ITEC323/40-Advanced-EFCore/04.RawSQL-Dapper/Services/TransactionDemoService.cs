using Dapper;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage;
using RawSQLDapper.Data;
using RawSQLDapper.Models;

namespace RawSQLDapper.Services;

/// <summary>
/// Demonstrates a shared transaction across EF Core and Dapper.
/// </summary>
public class TransactionDemoService
{
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of the <see cref="TransactionDemoService"/> class.
    /// </summary>
    /// <param name="context">The database context.</param>
    public TransactionDemoService(AppDbContext context)
    {
        _context = context;
    }

    /// <summary>
    /// Runs the shared transaction demo.
    /// </summary>
    /// <returns>The transaction result.</returns>
    public async Task<TransactionDemoResult> RunSharedTransactionAsync()
    {
        var product = await _context.Products.OrderBy(item => item.Name).FirstAsync();
        var auditInsertSql = """
INSERT INTO "AuditEntries" ("EventName", "Details", "CreatedAtUtc")
VALUES (@EventName, @Details, @CreatedAtUtc);
""";

        await using var transaction = await _context.Database.BeginTransactionAsync();

        product.QuantityInStock += 5;
        await _context.SaveChangesAsync();

        var auditMessage = $"Restocked {product.Name} by 5 units using a shared EF Core + Dapper transaction.";

        await _context.Database.OpenConnectionAsync();
        try
        {
            var dbTransaction = _context.Database.CurrentTransaction?.GetDbTransaction();
            await _context.Database.GetDbConnection().ExecuteAsync(
                auditInsertSql,
                new
                {
                    EventName = "Restock",
                    Details = auditMessage,
                    CreatedAtUtc = DateTime.UtcNow
                },
                transaction: dbTransaction);
        }
        finally
        {
            await _context.Database.CloseConnectionAsync();
        }

        await transaction.CommitAsync();

        return new TransactionDemoResult
        {
            ProductName = product.Name,
            NewQuantityInStock = product.QuantityInStock,
            AuditMessage = auditMessage,
            AuditInsertSql = auditInsertSql
        };
    }
}
