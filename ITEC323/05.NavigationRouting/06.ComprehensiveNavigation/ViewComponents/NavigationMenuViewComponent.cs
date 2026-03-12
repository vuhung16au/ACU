using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using ComprehensiveNavigation.Services;

namespace ComprehensiveNavigation.ViewComponents;

/// <summary>
/// Renders the main top navigation menu using data from INavigationService.
/// </summary>
public class NavigationMenuViewComponent : ViewComponent
{
    private readonly INavigationService _navigationService;

    public NavigationMenuViewComponent(INavigationService navigationService)
    {
        _navigationService = navigationService;
    }

    public IViewComponentResult Invoke()
    {
        var menuItems = _navigationService.GetMainMenu();
        return View(menuItems);
    }
}
