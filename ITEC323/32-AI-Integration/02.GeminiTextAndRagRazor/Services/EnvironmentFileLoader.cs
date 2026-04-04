namespace GeminiTextAndRagRazorDemo.Services;

/// <summary>
/// Loads simple key-value pairs from a local environment file.
/// </summary>
public class EnvironmentFileLoader
{
    /// <summary>
    /// Loads environment variables from the supplied file path when the file exists.
    /// </summary>
    /// <param name="filePath">The path to the local environment file.</param>
    public void Load(string filePath)
    {
        if (!File.Exists(filePath))
        {
            return;
        }

        foreach (var line in File.ReadAllLines(filePath))
        {
            var trimmedLine = line.Trim();
            if (string.IsNullOrWhiteSpace(trimmedLine) || trimmedLine.StartsWith('#'))
            {
                continue;
            }

            var separatorIndex = trimmedLine.IndexOf('=');
            if (separatorIndex <= 0)
            {
                continue;
            }

            var key = trimmedLine[..separatorIndex].Trim();
            var value = trimmedLine[(separatorIndex + 1)..].Trim();

            if (!string.IsNullOrWhiteSpace(key))
            {
                Environment.SetEnvironmentVariable(key, value);
            }
        }
    }
}
