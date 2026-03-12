using System.Globalization;
using System.Text;
using ComprehensiveAppDemo.Models;

namespace ComprehensiveAppDemo.Services;

/// <summary>
/// Provides asynchronous CRUD operations for user data stored in a CSV file.
/// </summary>
public class CsvUserService
{
    private static readonly SemaphoreSlim WriteLock = new(1, 1);
    private readonly IWebHostEnvironment _environment;
    private readonly ILogger<CsvUserService> _logger;

    /// <summary>
    /// Initializes a new instance of the <see cref="CsvUserService"/> class.
    /// </summary>
    /// <param name="environment">Provides the application content root path.</param>
    /// <param name="logger">Logs file operation errors.</param>
    public CsvUserService(IWebHostEnvironment environment, ILogger<CsvUserService> logger)
    {
        _environment = environment;
        _logger = logger;
    }

    /// <summary>
    /// Reads all users from the CSV file.
    /// </summary>
    /// <returns>A list of users.</returns>
    public async Task<List<User>> GetUsersAsync()
    {
        string filePath = GetCsvFilePath();

        try
        {
            if (!File.Exists(filePath))
            {
                throw new FileNotFoundException("The users.csv file was not found.", filePath);
            }

            string[] lines = await File.ReadAllLinesAsync(filePath);
            List<User> users = [];

            foreach (string line in lines.Skip(1))
            {
                if (string.IsNullOrWhiteSpace(line))
                {
                    continue;
                }

                string[] fields = ParseCsvLine(line);

                if (fields.Length < 8)
                {
                    continue;
                }

                users.Add(new User
                {
                    Id = int.Parse(fields[0], CultureInfo.InvariantCulture),
                    Name = fields[1],
                    Email = fields[2],
                    Phone = fields[3],
                    DateOfBirth = DateTime.ParseExact(fields[4], "yyyy-MM-dd", CultureInfo.InvariantCulture),
                    Password = fields[5],
                    ConfirmPassword = fields[5],
                    Bio = fields[6],
                    RecentActivity = fields[7]
                });
            }

            return users.OrderBy(user => user.Id).ToList();
        }
        catch (FileNotFoundException exception)
        {
            _logger.LogError(exception, "User CSV file could not be found at {FilePath}", filePath);
            throw;
        }
        catch (IOException exception)
        {
            _logger.LogError(exception, "The user CSV file could not be read because it is unavailable.");
            throw;
        }
    }

    /// <summary>
    /// Reads a single user by identifier.
    /// </summary>
    /// <param name="id">The user identifier.</param>
    /// <returns>The matching user, or null if not found.</returns>
    public async Task<User?> GetUserByIdAsync(int id)
    {
        List<User> users = await GetUsersAsync();
        return users.FirstOrDefault(user => user.Id == id);
    }

    /// <summary>
    /// Adds a new user to the CSV file.
    /// </summary>
    /// <param name="user">The user to add.</param>
    /// <returns>A task representing the save operation.</returns>
    public async Task AddUserAsync(User user)
    {
        await WriteLock.WaitAsync();

        try
        {
            List<User> users = await GetUsersAsync();
            user.Id = users.Count == 0 ? 1 : users.Max(existingUser => existingUser.Id) + 1;
            user.RecentActivity = $"Created on {DateTime.Today:dd MMM yyyy}";

            users.Add(user);
            await WriteUsersAsync(users);
        }
        catch (IOException exception)
        {
            _logger.LogError(exception, "The user CSV file could not be updated because it is locked or unavailable.");
            throw;
        }
        finally
        {
            WriteLock.Release();
        }
    }

    /// <summary>
    /// Updates an existing user in the CSV file.
    /// </summary>
    /// <param name="user">The updated user.</param>
    /// <returns>A task representing the update operation.</returns>
    public async Task UpdateUserAsync(User user)
    {
        await WriteLock.WaitAsync();

        try
        {
            List<User> users = await GetUsersAsync();
            User existingUser = users.First(existingUser => existingUser.Id == user.Id);

            existingUser.Name = user.Name;
            existingUser.Email = user.Email;
            existingUser.Phone = user.Phone;
            existingUser.DateOfBirth = user.DateOfBirth;
            existingUser.Password = user.Password;
            existingUser.ConfirmPassword = user.Password;
            existingUser.Bio = user.Bio;
            existingUser.RecentActivity = $"Updated on {DateTime.Today:dd MMM yyyy}";

            await WriteUsersAsync(users);
        }
        catch (IOException exception)
        {
            _logger.LogError(exception, "The user CSV file could not be updated because it is locked or unavailable.");
            throw;
        }
        finally
        {
            WriteLock.Release();
        }
    }

    /// <summary>
    /// Deletes a user from the CSV file.
    /// </summary>
    /// <param name="id">The identifier of the user to delete.</param>
    /// <returns>A task representing the delete operation.</returns>
    public async Task DeleteUserAsync(int id)
    {
        await WriteLock.WaitAsync();

        try
        {
            List<User> users = await GetUsersAsync();
            User? userToDelete = users.FirstOrDefault(user => user.Id == id);

            if (userToDelete is null)
            {
                return;
            }

            users.Remove(userToDelete);
            await WriteUsersAsync(users);
        }
        catch (IOException exception)
        {
            _logger.LogError(exception, "The user CSV file could not be updated because it is locked or unavailable.");
            throw;
        }
        finally
        {
            WriteLock.Release();
        }
    }

    private async Task WriteUsersAsync(List<User> users)
    {
        string filePath = GetCsvFilePath();
        StringBuilder builder = new();
        builder.AppendLine("Id,Name,Email,Phone,DateOfBirth,Password,Bio,RecentActivity");

        foreach (User user in users.OrderBy(existingUser => existingUser.Id))
        {
            builder.AppendLine(string.Join(',',
                user.Id.ToString(CultureInfo.InvariantCulture),
                EscapeCsvField(user.Name),
                EscapeCsvField(user.Email),
                EscapeCsvField(user.Phone),
                user.DateOfBirth?.ToString("yyyy-MM-dd", CultureInfo.InvariantCulture),
                EscapeCsvField(user.Password),
                EscapeCsvField(user.Bio),
                EscapeCsvField(user.RecentActivity)));
        }

        await File.WriteAllTextAsync(filePath, builder.ToString());
    }

    private string GetCsvFilePath()
    {
        return Path.Combine(_environment.ContentRootPath, "Data", "users.csv");
    }

    private static string EscapeCsvField(string value)
    {
        if (value.Contains(',') || value.Contains('"') || value.Contains('\n'))
        {
            return $"\"{value.Replace("\"", "\"\"", StringComparison.Ordinal)}\"";
        }

        return value;
    }

    private static string[] ParseCsvLine(string line)
    {
        List<string> fields = [];
        bool insideQuotes = false;
        int fieldStart = 0;

        for (int index = 0; index < line.Length; index++)
        {
            if (line[index] == '"')
            {
                insideQuotes = !insideQuotes;
            }
            else if (line[index] == ',' && !insideQuotes)
            {
                fields.Add(UnescapeCsvField(line[fieldStart..index]));
                fieldStart = index + 1;
            }
        }

        fields.Add(UnescapeCsvField(line[fieldStart..]));
        return [.. fields];
    }

    private static string UnescapeCsvField(string value)
    {
        string trimmed = value.Trim();

        if (trimmed.StartsWith('"') && trimmed.EndsWith('"'))
        {
            trimmed = trimmed[1..^1].Replace("\"\"", "\"", StringComparison.Ordinal);
        }

        return trimmed;
    }
}
