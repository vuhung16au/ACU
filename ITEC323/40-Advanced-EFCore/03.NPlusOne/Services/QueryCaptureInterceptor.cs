using System.Collections.Concurrent;
using System.Data.Common;
using Microsoft.EntityFrameworkCore.Diagnostics;

namespace NPlusOne.Services;

/// <summary>
/// Captures executed SQL statements during a teaching scenario.
/// </summary>
public class QueryCaptureInterceptor : DbCommandInterceptor
{
    private readonly AsyncLocal<Guid?> _activeSession = new();
    private readonly ConcurrentDictionary<Guid, List<string>> _capturedSql = new();

    /// <summary>
    /// Starts a new query capture session.
    /// </summary>
    /// <returns>The session identifier.</returns>
    public Guid BeginCapture()
    {
        var sessionId = Guid.NewGuid();
        _capturedSql[sessionId] = [];
        _activeSession.Value = sessionId;
        return sessionId;
    }

    /// <summary>
    /// Completes the active capture session and returns the SQL statements.
    /// </summary>
    /// <param name="sessionId">The session identifier.</param>
    /// <returns>The captured SQL statements.</returns>
    public IReadOnlyList<string> EndCapture(Guid sessionId)
    {
        _activeSession.Value = null;

        if (_capturedSql.TryRemove(sessionId, out var statements))
        {
            return statements;
        }

        return [];
    }

    /// <inheritdoc />
    public override DbDataReader ReaderExecuted(DbCommand command, CommandExecutedEventData eventData, DbDataReader result)
    {
        Capture(command.CommandText);
        return base.ReaderExecuted(command, eventData, result);
    }

    /// <inheritdoc />
    public override object? ScalarExecuted(DbCommand command, CommandExecutedEventData eventData, object? result)
    {
        Capture(command.CommandText);
        return base.ScalarExecuted(command, eventData, result);
    }

    /// <inheritdoc />
    public override int NonQueryExecuted(DbCommand command, CommandExecutedEventData eventData, int result)
    {
        Capture(command.CommandText);
        return base.NonQueryExecuted(command, eventData, result);
    }

    private void Capture(string sql)
    {
        if (_activeSession.Value is not Guid sessionId)
        {
            return;
        }

        if (_capturedSql.TryGetValue(sessionId, out var statements))
        {
            statements.Add(sql.Trim());
        }
    }
}
