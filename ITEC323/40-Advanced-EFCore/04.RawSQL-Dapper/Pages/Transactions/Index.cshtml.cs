using Microsoft.AspNetCore.Mvc.RazorPages;
using RawSQLDapper.Models;
using RawSQLDapper.Services;

namespace RawSQLDapper.Pages.Transactions;

/// <summary>
/// Displays and runs the shared transaction demo.
/// </summary>
public class IndexModel : PageModel
{
    private readonly TransactionDemoService _transactionDemoService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="transactionDemoService">The transaction demo service.</param>
    public IndexModel(TransactionDemoService transactionDemoService)
    {
        _transactionDemoService = transactionDemoService;
    }

    /// <summary>
    /// Gets the transaction result.
    /// </summary>
    public TransactionDemoResult? Result { get; private set; }

    /// <summary>
    /// Handles the GET request.
    /// </summary>
    public void OnGet()
    {
    }

    /// <summary>
    /// Runs the transaction demo.
    /// </summary>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task OnPostAsync()
    {
        Result = await _transactionDemoService.RunSharedTransactionAsync();
    }
}
