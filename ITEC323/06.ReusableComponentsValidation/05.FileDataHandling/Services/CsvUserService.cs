using System.Globalization;
using FileDataHandlingDemo.Models;

namespace FileDataHandlingDemo.Services;

/// <summary>
/// Provides asynchronous CSV file operations for user data.
/// </summary>
public class CsvUserService
{
    private readonly IWebHostEnvironment _environment;
    private readonly ILogger<CsvUserService> _logger;

    /// <summary>
    /// Initializes a new instance of the <see cref="CsvUserService"/> class.
    /// </summary>
    /// <param name="environment">Provides the application content root path.</param>
    /// <param name="logger">Logs file handling errors.</param>
    public CsvUserService(IWebHostEnvironment environment, ILogger<CsvUserService> logger)
    {
        _environment = environment;
        _logger = logger;
    }

    /// <summary>
    /// Reads all user records from the CSV file.
    /// </summary>
    /// <returns>A list of users loaded from the file.</returns>
    public async Task<List<UserRecord>> GetUsersAsync()
    {
        string filePath = GetCsvFilePath();

        try
        {
            if (!File.Exists(filePath))
            {
                throw new FileNotFoundException("The users.csv file was not found.", filePath);
            }

            string[] lines = await File.ReadAllLinesAsync(filePath);
            List<UserRecord> users = [];

            foreach (string line in lines.Skip(1))
            {
                if (string.IsNullOrWhiteSpace(line))
                {
                    continue;
                }

                string[] fields = ParseCsvLine(line);

                if (fields.Length < 5)
                {
                    continue;
                }

                users.Add(new UserRecord
                {
                    Id = int.Parse(fields[0], CultureInfo.InvariantCulture),
                    Name = fields[1],
                    Email = fields[2],
                    Phone = fields[3],
                    DateOfBirth = DateTime.ParseExact(fields[4], "yyyy-MM-dd", CultureInfo.InvariantCulture)
                });
            }

            return users;
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
    /// Appends a new user to the CSV file.
    /// </summary>
    /// <param name="user">The user record to save.</param>
    /// <returns>A task representing the save operation.</returns>
    public async Task AddUserAsync(UserRecord user)
    {
        string filePath = GetCsvFilePath();

        try
        {
            List<UserRecord> existingUsers = await GetUsersAsync();
            user.Id = existingUsers.Count == 0 ? 1 : existingUsers.Max(existingUser => existingUser.Id) + 1;

            string csvLine = Environment.NewLine + string.Join(',',
                user.Id.ToString(CultureInfo.InvariantCulture),
                EscapeCsvField(user.Name),
                EscapeCsvField(user.Email),
                EscapeCsvField(user.Phone),
                user.DateOfBirth?.ToString("yyyy-MM-dd", CultureInfo.InvariantCulture));

            await File.AppendAllTextAsync(filePath, csvLine);
        }
        catch (IOException exception)
        {
            _logger.LogError(exception, "The user CSV file could not be updated because it is locked or unavailable.");
            throw;
        }
    }

    private string GetCsvFilePath()
    {
        return Path.Combine(_environment.ContentRootPath, "Data", "users.csv");
    }

    private static string EscapeCsvField(string value)
    {
        if (value.Contains(',') || value.Contains('"'))
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
