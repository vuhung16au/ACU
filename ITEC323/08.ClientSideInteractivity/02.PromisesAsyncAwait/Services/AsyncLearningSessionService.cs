using PromisesAsyncAwait.Models;

namespace PromisesAsyncAwait.Services;

/// <summary>
/// Provides in-memory learning session data for asynchronous API examples.
/// </summary>
public class AsyncLearningSessionService
{
    private readonly List<LearningSession> _sessions;
    private readonly object _syncLock = new();

    /// <summary>
    /// Initializes a new instance of the <see cref="AsyncLearningSessionService"/> class.
    /// </summary>
    public AsyncLearningSessionService()
    {
        _sessions =
        [
            new LearningSession { Id = 1, StudentName = "Ava", Topic = "Promises", MinutesStudied = 30, IsCompleted = true, LastUpdatedUtc = DateTime.UtcNow.AddMinutes(-55) },
            new LearningSession { Id = 2, StudentName = "Noah", Topic = "Fetch API", MinutesStudied = 20, IsCompleted = false, LastUpdatedUtc = DateTime.UtcNow.AddMinutes(-30) },
            new LearningSession { Id = 3, StudentName = "Liam", Topic = "Error Handling", MinutesStudied = 45, IsCompleted = true, LastUpdatedUtc = DateTime.UtcNow.AddMinutes(-12) }
        ];
    }

    /// <summary>
    /// Gets all learning sessions.
    /// </summary>
    /// <param name="cancellationToken">Token used to cancel the request.</param>
    /// <returns>A read-only list of learning sessions.</returns>
    public Task<IReadOnlyList<LearningSession>> GetSessionsAsync(CancellationToken cancellationToken)
    {
        cancellationToken.ThrowIfCancellationRequested();

        lock (_syncLock)
        {
            var orderedCopy = _sessions
                .OrderByDescending(session => session.LastUpdatedUtc)
                .Select(session => new LearningSession
                {
                    Id = session.Id,
                    StudentName = session.StudentName,
                    Topic = session.Topic,
                    MinutesStudied = session.MinutesStudied,
                    IsCompleted = session.IsCompleted,
                    LastUpdatedUtc = session.LastUpdatedUtc
                })
                .ToList();

            return Task.FromResult<IReadOnlyList<LearningSession>>(orderedCopy);
        }
    }

    /// <summary>
    /// Gets summary values for dashboard cards.
    /// </summary>
    /// <param name="cancellationToken">Token used to cancel the request.</param>
    /// <returns>A summary object for the current sessions.</returns>
    public Task<LearningSessionSummary> GetSummaryAsync(CancellationToken cancellationToken)
    {
        cancellationToken.ThrowIfCancellationRequested();

        lock (_syncLock)
        {
            var totalSessions = _sessions.Count;
            var completedSessions = _sessions.Count(session => session.IsCompleted);
            var pendingSessions = totalSessions - completedSessions;
            var averageMinutes = totalSessions == 0 ? 0 : Math.Round(_sessions.Average(session => session.MinutesStudied), 1);

            var summary = new LearningSessionSummary
            {
                TotalSessions = totalSessions,
                CompletedSessions = completedSessions,
                PendingSessions = pendingSessions,
                AverageMinutes = averageMinutes,
                GeneratedAtUtc = DateTime.UtcNow
            };

            return Task.FromResult(summary);
        }
    }

    /// <summary>
    /// Creates a new learning session and stores it in memory.
    /// </summary>
    /// <param name="request">The values used to create the learning session.</param>
    /// <param name="cancellationToken">Token used to cancel the request.</param>
    /// <returns>The created learning session.</returns>
    public Task<LearningSession> AddSessionAsync(CreateLearningSessionRequest request, CancellationToken cancellationToken)
    {
        cancellationToken.ThrowIfCancellationRequested();

        lock (_syncLock)
        {
            var nextId = _sessions.Count == 0 ? 1 : _sessions.Max(session => session.Id) + 1;
            var createdSession = new LearningSession
            {
                Id = nextId,
                StudentName = request.StudentName.Trim(),
                Topic = request.Topic.Trim(),
                MinutesStudied = request.MinutesStudied,
                IsCompleted = request.IsCompleted,
                LastUpdatedUtc = DateTime.UtcNow
            };

            _sessions.Add(createdSession);

            return Task.FromResult(createdSession);
        }
    }
}
