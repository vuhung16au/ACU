using GeminiTextAndRagRazorDemo.Models;
using GeminiTextAndRagRazorDemo.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace GeminiTextAndRagRazorDemo.Pages;

/// <summary>
/// Displays and processes the Gemini RAG chat page.
/// </summary>
public class IndexModel : PageModel
{
    private readonly ChatInputValidator _chatInputValidator;
    private readonly ChatHistorySessionService _chatHistorySessionService;
    private readonly RagAnswerService _ragAnswerService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="chatInputValidator">Validates the user question.</param>
    /// <param name="chatHistorySessionService">Loads and stores chat history in session.</param>
    /// <param name="ragAnswerService">Generates grounded answers.</param>
    public IndexModel(
        ChatInputValidator chatInputValidator,
        ChatHistorySessionService chatHistorySessionService,
        RagAnswerService ragAnswerService)
    {
        _chatInputValidator = chatInputValidator;
        _chatHistorySessionService = chatHistorySessionService;
        _ragAnswerService = ragAnswerService;
    }

    /// <summary>
    /// Gets or sets the current user input.
    /// </summary>
    [BindProperty]
    public ChatInput Input { get; set; } = new();

    /// <summary>
    /// Gets the current conversation history.
    /// </summary>
    public List<ChatExchange> Conversation { get; private set; } = new();

    /// <summary>
    /// Gets the friendly error message shown on the page when submission fails.
    /// </summary>
    public string ErrorMessage { get; private set; } = string.Empty;

    /// <summary>
    /// Handles GET requests for the page.
    /// </summary>
    public void OnGet()
    {
        LoadConversation();
    }

    /// <summary>
    /// Handles question submission requests.
    /// </summary>
    /// <returns>The current page with the updated conversation.</returns>
    public async Task<IActionResult> OnPostAskAsync()
    {
        LoadConversation();

        var validationMessage = _chatInputValidator.Validate(Input);
        if (!string.IsNullOrWhiteSpace(validationMessage))
        {
            ErrorMessage = validationMessage;
            return Page();
        }

        try
        {
            var ragResponse = await _ragAnswerService.AnswerQuestionAsync(Input.Question.Trim());
            var exchange = new ChatExchange
            {
                Question = ragResponse.Question,
                Answer = ragResponse.Answer,
                Sources = ragResponse.Sources
            };

            Conversation = _chatHistorySessionService.AddExchange(HttpContext.Session, exchange);
            Input = new ChatInput();
        }
        catch (Exception ex)
        {
            ErrorMessage = $"The chat request could not be completed: {ex.Message}";
        }

        return Page();
    }

    /// <summary>
    /// Handles requests to clear the current conversation.
    /// </summary>
    /// <returns>The current page with an empty conversation.</returns>
    public IActionResult OnPostClear()
    {
        _chatHistorySessionService.Clear(HttpContext.Session);
        Conversation = new List<ChatExchange>();
        Input = new ChatInput();
        ErrorMessage = string.Empty;
        return Page();
    }

    private void LoadConversation()
    {
        Conversation = _chatHistorySessionService.GetConversation(HttpContext.Session);
    }
}
