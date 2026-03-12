using Microsoft.AspNetCore.Mvc;
using ViewComponentsDemo.Models;
using ViewComponentsDemo.Services;

namespace ViewComponentsDemo.ViewComponents;

/// <summary>
/// Displays a shopping cart summary with totals calculated in C#.
/// </summary>
public class ShoppingCartViewComponent : ViewComponent
{
    private readonly SampleDataService _sampleDataService;

    /// <summary>
    /// Initializes a new instance of the <see cref="ShoppingCartViewComponent"/> class.
    /// </summary>
    /// <param name="sampleDataService">Provides sample cart data.</param>
    public ShoppingCartViewComponent(SampleDataService sampleDataService)
    {
        _sampleDataService = sampleDataService;
    }

    /// <summary>
    /// Builds the shopping cart summary before rendering the view.
    /// </summary>
    /// <returns>The rendered shopping cart component.</returns>
    public Task<IViewComponentResult> InvokeAsync()
    {
        List<CartItem> cartItems = _sampleDataService.GetCartItems();

        ShoppingCartSummary summary = new()
        {
            TotalItems = cartItems.Sum(item => item.Quantity),
            TotalCost = cartItems.Sum(item => item.Quantity * item.UnitPrice),
            HighestValueItem = cartItems
                .OrderByDescending(item => item.Quantity * item.UnitPrice)
                .First()
                .ProductName
        };

        return Task.FromResult<IViewComponentResult>(View(summary));
    }
}
