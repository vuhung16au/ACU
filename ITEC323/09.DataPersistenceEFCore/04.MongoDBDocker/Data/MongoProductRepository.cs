using Microsoft.Extensions.Options;
using MongoDB.Bson;
using MongoDB.Driver;
using MongoDBDocker.Models;

namespace MongoDBDocker.Data;

/// <summary>
/// MongoDB implementation of product persistence operations.
/// </summary>
public class MongoProductRepository : IProductRepository
{
    private readonly IMongoCollection<Product> _products;

    /// <summary>
    /// Initializes a new instance of the <see cref="MongoProductRepository"/> class.
    /// </summary>
    /// <param name="settings">MongoDB configuration settings.</param>
    public MongoProductRepository(IOptions<MongoDbSettings> settings)
    {
        var mongoSettings = settings.Value;
        var client = new MongoClient(mongoSettings.ConnectionString);
        var database = client.GetDatabase(mongoSettings.DatabaseName);
        _products = database.GetCollection<Product>(mongoSettings.CollectionName);
    }

    /// <inheritdoc/>
    public Task<List<Product>> GetAllAsync()
    {
        return _products
            .Find(FilterDefinition<Product>.Empty)
            .SortBy(p => p.Name)
            .ToListAsync();
    }

    /// <inheritdoc/>
    public async Task<Product?> GetByIdAsync(string id)
    {
        var product = await _products.Find(p => p.Id == id).FirstOrDefaultAsync();
        return product;
    }

    /// <inheritdoc/>
    public Task<long> CountAsync()
    {
        return _products.CountDocumentsAsync(FilterDefinition<Product>.Empty);
    }

    /// <inheritdoc/>
    public Task CreateAsync(Product product)
    {
        if (string.IsNullOrWhiteSpace(product.Id))
        {
            product.Id = ObjectId.GenerateNewId().ToString();
        }

        product.CreatedAtUtc = DateTime.SpecifyKind(product.CreatedAtUtc, DateTimeKind.Utc);
        return _products.InsertOneAsync(product);
    }

    /// <inheritdoc/>
    public Task UpdateAsync(Product product)
    {
        product.CreatedAtUtc = DateTime.SpecifyKind(product.CreatedAtUtc, DateTimeKind.Utc);
        return _products.ReplaceOneAsync(p => p.Id == product.Id, product);
    }

    /// <inheritdoc/>
    public Task DeleteAsync(string id)
    {
        return _products.DeleteOneAsync(p => p.Id == id);
    }

    /// <inheritdoc/>
    public async Task SeedIfEmptyAsync()
    {
        if (await CountAsync() > 0)
        {
            return;
        }

        var now = DateTime.UtcNow;
        var seedProducts = new List<Product>
        {
            new() { Id = ObjectId.GenerateNewId().ToString(), Name = "Coffee Beans", Description = "Single-origin Arabica beans", Price = 18.90m, StockQuantity = 55, CreatedAtUtc = now },
            new() { Id = ObjectId.GenerateNewId().ToString(), Name = "French Press", Description = "1L glass press", Price = 29.00m, StockQuantity = 24, CreatedAtUtc = now },
            new() { Id = ObjectId.GenerateNewId().ToString(), Name = "Electric Kettle", Description = "Variable temperature kettle", Price = 69.00m, StockQuantity = 18, CreatedAtUtc = now },
            new() { Id = ObjectId.GenerateNewId().ToString(), Name = "Ceramic Mug", Description = "350ml matte finish mug", Price = 12.50m, StockQuantity = 90, CreatedAtUtc = now }
        };

        await _products.InsertManyAsync(seedProducts);
    }
}
