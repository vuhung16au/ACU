using System.Text;
using GeminiTextAndRagRazorDemo.Data;
using GeminiTextAndRagRazorDemo.Models;
using Microsoft.Extensions.Options;

namespace GeminiTextAndRagRazorDemo.Services;

/// <summary>
/// Combines document retrieval and text generation to produce grounded answers.
/// </summary>
public class RagAnswerService
{
    private readonly KnowledgeBaseService _knowledgeBaseService;
    private readonly KeywordSearchService _keywordSearchService;
    private readonly ITextGenerationService _textGenerationService;
    private readonly RagOptions _options;

    /// <summary>
    /// Initializes a new instance of the <see cref="RagAnswerService"/> class.
    /// </summary>
    public RagAnswerService(
        KnowledgeBaseService knowledgeBaseService,
        KeywordSearchService keywordSearchService,
        ITextGenerationService textGenerationService,
        IOptions<RagOptions> options)
    {
        _knowledgeBaseService = knowledgeBaseService;
        _keywordSearchService = keywordSearchService;
        _textGenerationService = textGenerationService;
        _options = options.Value;
    }

    /// <summary>
    /// Answers a question by retrieving matching documents and sending grounded context to Gemini.
    /// </summary>
    /// <param name="question">The user's question.</param>
    /// <returns>A grounded answer and the sources used to create it.</returns>
    public async Task<RagQuestionResponse> AnswerQuestionAsync(string question)
    {
        var documents = _knowledgeBaseService.GetDocuments();
        var matches = _keywordSearchService.Search(documents, question, _options.MaxDocuments);
        var prompt = BuildPrompt(question, matches);
        var answer = await _textGenerationService.GenerateTextAsync(prompt);

        return new RagQuestionResponse
        {
            Question = question,
            Answer = answer,
            Sources = matches.Select(CreateSourceItem).ToList()
        };
    }

    /// <summary>
    /// Builds a grounded prompt that includes retrieved context and safe fallback guidance.
    /// </summary>
    /// <param name="question">The user's question.</param>
    /// <param name="documents">The documents selected for retrieval.</param>
    /// <returns>The final prompt sent to the model.</returns>
    public string BuildPrompt(string question, IReadOnlyList<KnowledgeDocument> documents)
    {
        var promptBuilder = new StringBuilder();
        promptBuilder.AppendLine("You are a helpful teaching assistant for beginner .NET students.");
        promptBuilder.AppendLine("Answer the question by using the provided context when it is relevant.");
        promptBuilder.AppendLine("If the context is not enough, clearly say that the local knowledge base is insufficient and then give a short general answer.");
        promptBuilder.AppendLine();
        promptBuilder.AppendLine("Question:");
        promptBuilder.AppendLine(question);
        promptBuilder.AppendLine();
        promptBuilder.AppendLine("Context:");

        if (documents.Count == 0)
        {
            promptBuilder.AppendLine("No matching local documents were found.");
        }
        else
        {
            foreach (var document in documents)
            {
                promptBuilder.AppendLine($"[{document.Id}] {document.Title} ({document.Category})");
                promptBuilder.AppendLine(document.Content);
                promptBuilder.AppendLine();
            }
        }

        promptBuilder.AppendLine("Write a clear answer for beginners.");
        return promptBuilder.ToString().Trim();
    }

    private RagSourceItem CreateSourceItem(KnowledgeDocument document)
    {
        var excerptLength = Math.Max(40, _options.MaxExcerptLength);
        var excerpt = document.Content.Length <= excerptLength
            ? document.Content
            : $"{document.Content[..excerptLength].TrimEnd()}...";

        return new RagSourceItem
        {
            DocumentId = document.Id,
            Title = document.Title,
            Category = document.Category,
            Excerpt = excerpt
        };
    }
}
