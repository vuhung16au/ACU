using System.Collections.Generic;
using System.Linq;
using ComprehensiveNavigation.Models;

namespace ComprehensiveNavigation.Services;

/// <summary>
/// Service providing product catalog functionality.
/// </summary>
public interface IProductService
{
    IEnumerable<Product> GetAll();
    IEnumerable<Product> GetByCategory(string category);
    Product? GetById(int id);
    IEnumerable<Product> Search(string query);
}

public class ProductService : IProductService
{
    private readonly List<Product> _products;

    public ProductService()
    {
        _products = new List<Product>
        {
            new Product { Id = 1, Name = "MacBook Pro", Category = "Electronics", Price = 1999.99m, Description = "High-performance laptop." },
            new Product { Id = 2, Name = "C# in Depth", Category = "Books", Price = 45.00m, Description = "Deep dive into C#." },
            new Product { Id = 3, Name = "Running Shoes", Category = "Clothing", Price = 120.00m, Description = "Comfortable shoes." },
            new Product { Id = 4, Name = "Learn ASP.NET Core", Category = "Books", Price = 38.50m, Description = "Web building guide." },
            new Product { Id = 5, Name = "Wireless Mouse", Category = "Electronics", Price = 25.00m, Description = "Long battery life." }
        };
    }

    public IEnumerable<Product> GetAll() => _products;

    public IEnumerable<Product> GetByCategory(string category)
        => _products.Where(p => p.Category.Equals(category, System.StringComparison.OrdinalIgnoreCase));

    public Product? GetById(int id) => _products.FirstOrDefault(p => p.Id == id);

    public IEnumerable<Product> Search(string query)
        => _products.Where(p => p.Name.Contains(query, System.StringComparison.OrdinalIgnoreCase) 
                             || p.Description.Contains(query, System.StringComparison.OrdinalIgnoreCase));
}
