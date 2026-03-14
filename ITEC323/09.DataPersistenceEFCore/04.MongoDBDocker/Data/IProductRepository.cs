using MongoDBDocker.Models;

namespace MongoDBDocker.Data;

/// <summary>
/// Defines product persistence operations for MongoDB.
/// </summary>
public interface IProductRepository
{
    Task<List<Product>> GetAllAsync();

    Task<Product?> GetByIdAsync(string id);

    Task<long> CountAsync();

    Task CreateAsync(Product product);

    Task UpdateAsync(Product product);

    Task DeleteAsync(string id);

    Task SeedIfEmptyAsync();
}
