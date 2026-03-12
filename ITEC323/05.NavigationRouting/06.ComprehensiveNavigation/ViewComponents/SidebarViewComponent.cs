using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using ComprehensiveNavigation.Services;

namespace ComprehensiveNavigation.ViewComponents;

/// <summary>
/// Renders the documentation sidebar navigation using data from INavigationService.
/// </summary>
public class SidebarViewComponent : ViewComponent
{
    private readonly INavigationService _navigationService;

    public SidebarViewComponent(INavigationService navigationService)
    {
        _navigationService = navigationService;
    }

    public IViewComponentResult Invoke()
    {
        var menuItems = _navigationService.GetDocsSidebarMenu();
        return View(menuItems);
    }
}
