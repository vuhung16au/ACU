using ViewComponentsDemo.Models;

namespace ViewComponentsDemo.Services;

/// <summary>
/// Provides simple in-memory sample data for the View Components examples.
/// </summary>
public class SampleDataService
{
    /// <summary>
    /// Gets the sample shopping cart items.
    /// </summary>
    /// <returns>A list of shopping cart items.</returns>
    public List<CartItem> GetCartItems()
    {
        return
        [
            new CartItem { ProductName = "Laptop Stand", Quantity = 1, UnitPrice = 39.00m },
            new CartItem { ProductName = "USB-C Hub", Quantity = 2, UnitPrice = 24.50m },
            new CartItem { ProductName = "Notebook Pack", Quantity = 3, UnitPrice = 5.25m }
        ];
    }

    /// <summary>
    /// Gets the sample user profiles.
    /// </summary>
    /// <returns>A list of user profiles.</returns>
    public List<UserProfile> GetUserProfiles()
    {
        return
        [
            new UserProfile
            {
                UserId = 101,
                Name = "Ava Thompson",
                Role = "Student",
                CourseProgressPercent = 72,
                NextTask = "Complete the View Components workshop"
            },
            new UserProfile
            {
                UserId = 202,
                Name = "Noah Patel",
                Role = "Tutor",
                CourseProgressPercent = 88,
                NextTask = "Review student submissions"
            }
        ];
    }

    /// <summary>
    /// Gets the sample recent activity items.
    /// </summary>
    /// <returns>A list of recent items.</returns>
    public List<RecentItem> GetRecentItems()
    {
        return
        [
            new RecentItem { UserId = 101, Title = "Submitted Partial Views task", Category = "Assessment", HoursAgo = 2 },
            new RecentItem { UserId = 101, Title = "Watched Razor Pages recap", Category = "Study", HoursAgo = 5 },
            new RecentItem { UserId = 101, Title = "Updated shopping cart example", Category = "Practice", HoursAgo = 8 },
            new RecentItem { UserId = 202, Title = "Prepared feedback notes", Category = "Teaching", HoursAgo = 1 },
            new RecentItem { UserId = 202, Title = "Marked validation exercises", Category = "Teaching", HoursAgo = 6 }
        ];
    }
}
