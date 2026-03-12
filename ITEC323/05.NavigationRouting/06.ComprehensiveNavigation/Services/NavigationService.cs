using System.Collections.Generic;
using ComprehensiveNavigation.Models;

namespace ComprehensiveNavigation.Services;

/// <summary>
/// Service responsible for providing navigation structure Data.
/// </summary>
public interface INavigationService
{
    /// <summary>
    /// Gets the primary top-level menu hierarchy.
    /// </summary>
    IEnumerable<MenuItem> GetMainMenu();

    /// <summary>
    /// Gets the sidebar menu hierarchy specifically for documentation pages.
    /// </summary>
    IEnumerable<MenuItem> GetDocsSidebarMenu();
}

/// <summary>
/// Provides predefined navigation structure menus. In a real-world scenario, this might fetch from a DB.
/// </summary>
public class NavigationService : INavigationService
{
    public IEnumerable<MenuItem> GetMainMenu()
    {
        return new List<MenuItem>
        {
            new MenuItem { Title = "Home", Url = "/", ExactMatch = true, Icon = "bi-house" },
            new MenuItem
            {
                Title = "Products",
                Url = "/Products",
                Icon = "bi-box",
                Children = new List<MenuItem>
                {
                    new MenuItem { Title = "All Products", Url = "/Products" },
                    new MenuItem { Title = "Electronics", Url = "/Products/Category/Electronics" },
                    new MenuItem { Title = "Books", Url = "/Products/Category/Books" },
                    new MenuItem { Title = "Clothing", Url = "/Products/Category/Clothing" }
                }
            },
            new MenuItem { Title = "Docs", Url = "/Docs", Icon = "bi-journal-code" },
            new MenuItem { Title = "Account", Url = "/Account/Orders", Icon = "bi-person" }
        };
    }

    public IEnumerable<MenuItem> GetDocsSidebarMenu()
    {
        return new List<MenuItem>
        {
            new MenuItem
            {
                Title = "Introduction",
                Url = "#",
                Children = new List<MenuItem>
                {
                    new MenuItem { Title = "Overview", Url = "/Docs" },
                    new MenuItem { Title = "Getting Started", Url = "/Docs/GettingStarted" },
                    new MenuItem { Title = "Installation", Url = "/Docs/Installation" }
                }
            },
            new MenuItem
            {
                Title = "Guides",
                Url = "#",
                Children = new List<MenuItem>
                {
                    new MenuItem { Title = "Basic Routing", Url = "/Docs/Guides/BasicRouting" },
                    new MenuItem { Title = "Advanced Navigation", Url = "/Docs/Guides/Advanced" }
                }
            }
        };
    }
}
