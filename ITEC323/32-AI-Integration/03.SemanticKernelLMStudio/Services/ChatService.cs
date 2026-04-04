using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.ChatCompletion;
using Microsoft.SemanticKernel.Connectors.OpenAI;

namespace SemanticKernelLMStudio.Services;

/// <summary>
/// Provides direct chat completions through Semantic Kernel.
/// </summary>
public class ChatService(Kernel kernel)
{
    /// <summary>
    /// Generates a response for the provided prompt.
    /// </summary>
    /// <param name="prompt">The user prompt to answer.</param>
    /// <param name="maxTokens">The maximum number of tokens to generate.</param>
    /// <param name="temperature">The sampling temperature.</param>
    /// <returns>The generated response text.</returns>
    /// <exception cref="InvalidOperationException">Thrown when the local model request fails.</exception>
    public async Task<string> GenerateAsync(string prompt, int maxTokens = 500, float temperature = 0.7f)
    {
        try
        {
            var chatCompletionService = kernel.GetRequiredService<IChatCompletionService>();
            var history = new ChatHistory();
            history.AddSystemMessage("You are a helpful teaching assistant. Answer clearly, accurately, and concisely.");
            history.AddUserMessage(prompt);

            var settings = new OpenAIPromptExecutionSettings
            {
                MaxTokens = maxTokens,
                Temperature = temperature
            };

            var response = await chatCompletionService.GetChatMessageContentAsync(history, settings);
            return response.Content ?? "(empty response)";
        }
        catch (Exception ex)
        {
            throw new InvalidOperationException(
                "Failed to generate a chat response. Make sure LM Studio is running and a chat model is loaded.",
                ex);
        }
    }
}
