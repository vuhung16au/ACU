using System.Net.Http.Json;
using System.Text.Json.Serialization;

const string endpoint = "http://localhost:1234/v1/chat/completions";
var modelName = Environment.GetEnvironmentVariable("LMSTUDIO_MODEL") ?? "local-model";

using var httpClient = new HttpClient
{
    Timeout = TimeSpan.FromMinutes(2)
};

Console.WriteLine("Hello World AI Chat (.NET CLI + LM Studio)");
Console.WriteLine($"Endpoint: {endpoint}");
Console.WriteLine($"Model: {modelName}");
Console.WriteLine("Type your message and press Enter.");
Console.WriteLine("Type 'exit' to quit.");
Console.WriteLine();

while (true)
{
    Console.Write("You: ");
    var userInput = Console.ReadLine();

    if (string.IsNullOrWhiteSpace(userInput))
    {
        continue;
    }

    if (userInput.Equals("exit", StringComparison.OrdinalIgnoreCase))
    {
        Console.WriteLine("Goodbye.");
        break;
    }

    var requestBody = new ChatCompletionRequest
    {
        Model = modelName,
        Messages =
        [
            new ChatMessage
            {
                Role = "user",
                Content = userInput
            }
        ],
        Temperature = 0.7
    };

    try
    {
        using var response = await httpClient.PostAsJsonAsync(endpoint, requestBody);

        if (!response.IsSuccessStatusCode)
        {
            var errorBody = await response.Content.ReadAsStringAsync();
            Console.WriteLine($"Assistant (error): HTTP {(int)response.StatusCode} {response.ReasonPhrase}");
            Console.WriteLine(errorBody);
            Console.WriteLine();
            continue;
        }

        var completion = await response.Content.ReadFromJsonAsync<ChatCompletionResponse>();
        var assistantText = completion?.Choices?.FirstOrDefault()?.Message?.Content;

        if (string.IsNullOrWhiteSpace(assistantText))
        {
            Console.WriteLine("Assistant: (No response text returned)");
        }
        else
        {
            Console.WriteLine($"Assistant: {assistantText}");
        }

        Console.WriteLine();
    }
    catch (TaskCanceledException)
    {
        Console.WriteLine("Assistant (error): Request timed out. Ensure LM Studio is running and model is loaded.");
        Console.WriteLine();
    }
    catch (HttpRequestException ex)
    {
        Console.WriteLine("Assistant (error): Could not connect to LM Studio endpoint.");
        Console.WriteLine($"Details: {ex.Message}");
        Console.WriteLine();
    }
}

internal sealed class ChatCompletionRequest
{
    [JsonPropertyName("model")]
    public required string Model { get; init; }

    [JsonPropertyName("messages")]
    public required List<ChatMessage> Messages { get; init; }

    [JsonPropertyName("temperature")]
    public double Temperature { get; init; }
}

internal sealed class ChatMessage
{
    [JsonPropertyName("role")]
    public required string Role { get; init; }

    [JsonPropertyName("content")]
    public required string Content { get; init; }
}

internal sealed class ChatCompletionResponse
{
    [JsonPropertyName("choices")]
    public List<ChatChoice>? Choices { get; init; }
}

internal sealed class ChatChoice
{
    [JsonPropertyName("message")]
    public ChatMessage? Message { get; init; }
}
