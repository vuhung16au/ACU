namespace MongoDBDocker.Data;

/// <summary>
/// Strongly typed MongoDB connection settings.
/// </summary>
public class MongoDbSettings
{
    /// <summary>
    /// Gets or sets the MongoDB connection string.
    /// </summary>
    public string ConnectionString { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the logical database name.
    /// </summary>
    public string DatabaseName { get; set; } = "mongodb_docker_db";

    /// <summary>
    /// Gets or sets the collection name used for products.
    /// </summary>
    public string CollectionName { get; set; } = "products";
}
