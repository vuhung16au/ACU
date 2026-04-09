using System.Globalization;
using System.Net;
using System.Text;
using BenchmarkDotNet.Reports;

namespace BenchmarkDotNetSortComparer.Infrastructure;

public static class ReportPublisher
{
    public static string Publish(Summary summary, string workingDirectory)
    {
        var reportsDirectory = Path.Combine(workingDirectory, "reports");
        Directory.CreateDirectory(reportsDirectory);

        var timestamp = DateTimeOffset.Now.ToString("yyyyMMdd-HHmmss", CultureInfo.InvariantCulture);
        var reportPath = Path.Combine(reportsDirectory, $"report-{timestamp}.html");

        var rows = BuildRows(summary)
            .OrderBy(r => r.Algorithm, StringComparer.Ordinal)
            .ThenBy(r => r.Dataset, StringComparer.Ordinal)
            .ToList();

        var html = BuildHtml(rows, timestamp);
        File.WriteAllText(reportPath, html);

        var keyTakeawaysPath = Path.Combine(workingDirectory, "docs", "Key-Takeaways.md");
        Directory.CreateDirectory(Path.GetDirectoryName(keyTakeawaysPath)!);
        File.WriteAllText(keyTakeawaysPath, BuildTakeawaysMarkdown(rows, timestamp));

        return reportPath;
    }

    private static IEnumerable<ComparisonRow> BuildRows(Summary summary)
    {
        var flat = summary.Reports
            .Where(r => r.ResultStatistics is not null)
            .Select(r =>
            {
                var parameters = r.BenchmarkCase.Parameters?.Items;
                var datasetParameter = parameters?
                    .FirstOrDefault(p => p is not null && string.Equals(p.Name, "DatasetFileName", StringComparison.Ordinal));
                var dataset = datasetParameter?.Value?.ToString() ?? "unknown";

                var runtimeDisplay = r.BenchmarkCase.Job.Environment.Runtime.Name ?? string.Empty;
                var runtimeKey = runtimeDisplay.Contains("10", StringComparison.OrdinalIgnoreCase) ? "net10.0" : "net9.0";
                var workloadName = r.BenchmarkCase.Descriptor.WorkloadMethod.Name ?? string.Empty;

                return new
                {
                    Algorithm = workloadName.Replace("Benchmark", string.Empty, StringComparison.Ordinal),
                    Dataset = dataset,
                    RuntimeKey = runtimeKey,
                    RuntimeDisplay = runtimeDisplay,
                    MeanNs = r.ResultStatistics!.Mean
                };
            })
            .ToList();

        return flat
            .GroupBy(x => new { x.Algorithm, x.Dataset })
            .Select(group =>
            {
                var net9 = group.FirstOrDefault(g => g.RuntimeKey == "net9.0");
                var net10 = group.FirstOrDefault(g => g.RuntimeKey == "net10.0");

                var net9Mean = net9?.MeanNs ?? double.NaN;
                var net10Mean = net10?.MeanNs ?? double.NaN;

                var ratio = !double.IsNaN(net9Mean) && !double.IsNaN(net10Mean) && net10Mean > 0
                    ? net9Mean / net10Mean
                    : double.NaN;

                return new ComparisonRow(
                    group.Key.Algorithm,
                    group.Key.Dataset,
                    net9Mean,
                    net10Mean,
                    ratio);
            });
    }

    private static string BuildHtml(IReadOnlyList<ComparisonRow> rows, string timestamp)
    {
        var sb = new StringBuilder();
        sb.AppendLine("<!DOCTYPE html>");
        sb.AppendLine("<html lang=\"en\">");
        sb.AppendLine("<head>");
        sb.AppendLine("  <meta charset=\"UTF-8\" />");
        sb.AppendLine("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
        sb.AppendLine("  <title>Benchmark Report - .NET 9 vs .NET 10</title>");
        sb.AppendLine("  <script src=\"https://cdn.jsdelivr.net/npm/chart.js\"></script>");
        sb.AppendLine("  <style>");
        sb.AppendLine("    :root { --bg:#f3efe7; --card:#fffdf8; --ink:#1f2b31; --muted:#5f6f76; --line:#ddd2c3; --accent:#155f49; --accent-dark:#114d3b; --accent-soft:#edf3f0; --good:#155f49; --warn:#ac6e18; }");
        sb.AppendLine("    body { margin:0; font-family: 'Iowan Old Style', 'Palatino Linotype', serif; background: radial-gradient(circle at top right, rgba(21, 95, 73, 0.14), transparent 30%), linear-gradient(180deg, #fbf8f3 0%, var(--bg) 100%); color: var(--ink); }");
        sb.AppendLine("    .wrap { max-width: 1120px; margin: 0 auto; padding: 2rem 1rem 3rem; }");
        sb.AppendLine("    .hero { background: radial-gradient(circle at 10% 10%, rgba(21, 95, 73, 0.14), #fffdf8 42%), #fff; border: 1px solid var(--line); border-radius: 18px; padding: 1.25rem 1.5rem; box-shadow: 0 14px 30px rgba(25, 39, 44, 0.08); }");
        sb.AppendLine("    h1 { margin: 0; font-size: clamp(1.5rem, 2.3vw, 2.2rem); letter-spacing: .01em; }");
        sb.AppendLine("    p { color: var(--muted); }");
        sb.AppendLine("    .chart-card { margin-top: 1rem; background: var(--card); border: 1px solid var(--line); border-radius: 18px; padding: 1rem 1rem 1.25rem; box-shadow: 0 14px 30px rgba(25, 39, 44, 0.08); }");
        sb.AppendLine("    .chart-toolbar { display: flex; flex-wrap: wrap; align-items: center; justify-content: space-between; gap: .75rem; margin-bottom: 1rem; }");
        sb.AppendLine("    .chart-title { margin: 0; font-size: 1.1rem; }");
        sb.AppendLine("    .chart-note { margin: 0; font-size: .92rem; color: var(--muted); }");
        sb.AppendLine("    .dataset-picker { display: flex; align-items: center; gap: .65rem; }");
        sb.AppendLine("    .dataset-picker label { font-weight: 700; color: var(--accent-dark); }");
        sb.AppendLine("    .dataset-picker select { appearance: none; border: 1px solid var(--line); border-radius: 999px; background: var(--accent-soft); color: var(--ink); padding: .6rem .9rem; font: inherit; }");
        sb.AppendLine("    .chart-shell { position: relative; min-height: 420px; }");
        sb.AppendLine("    table { width: 100%; border-collapse: collapse; margin-top: 1rem; background: var(--card); border: 1px solid var(--line); border-radius: 12px; overflow: hidden; }");
        sb.AppendLine("    th, td { padding: .75rem .65rem; text-align: left; border-bottom: 1px solid #e7dfd4; font-size: .95rem; }");
        sb.AppendLine("    th { background: var(--accent-soft); color: var(--accent-dark); font-weight: 700; }");
        sb.AppendLine("    tr:hover td { background: #f8fafc; }");
        sb.AppendLine("    .num { text-align: right; font-variant-numeric: tabular-nums; }");
        sb.AppendLine("    .good { color: var(--good); font-weight: 700; }");
        sb.AppendLine("    .warn { color: var(--warn); font-weight: 700; }");
        sb.AppendLine("    @media (max-width: 720px) { .chart-shell { min-height: 340px; } th, td { font-size: .83rem; padding: .55rem .45rem; } }");
        sb.AppendLine("  </style>");
        sb.AppendLine("</head>");
        sb.AppendLine("<body>");
        sb.AppendLine("  <main class=\"wrap\">");
        sb.AppendLine("    <section class=\"hero\">");
        sb.AppendLine("      <h1>.NET 9 vs .NET 10 Sort Benchmarks</h1>");
        sb.AppendLine($"      <p>Generated at {WebUtility.HtmlEncode(timestamp)}. Lower mean time is better. Ratio is computed as .NET9 / .NET10.</p>");
        sb.AppendLine("    </section>");
        sb.AppendLine("    <section class=\"chart-card\">");
        sb.AppendLine("      <div class=\"chart-toolbar\">");
        sb.AppendLine("        <div>");
        sb.AppendLine("          <h2 class=\"chart-title\">Grouped Runtime Chart</h2>");
        sb.AppendLine("          <p class=\"chart-note\">X axis shows sorting algorithms. Y axis shows mean runtime in milliseconds for .NET 9 and .NET 10.</p>");
        sb.AppendLine("        </div>");
        sb.AppendLine("        <div class=\"dataset-picker\">");
        sb.AppendLine("          <label for=\"datasetSelect\">Dataset</label>");
        sb.AppendLine("          <select id=\"datasetSelect\"></select>");
        sb.AppendLine("        </div>");
        sb.AppendLine("      </div>");
        sb.AppendLine("      <div class=\"chart-shell\">");
        sb.AppendLine("        <canvas id=\"benchmarkChart\"></canvas>");
        sb.AppendLine("      </div>");
        sb.AppendLine("    </section>");
        sb.AppendLine("    <table>");
        sb.AppendLine("      <thead>");
        sb.AppendLine("        <tr><th>Algorithm</th><th>Dataset</th><th class=\"num\">.NET 9 Mean (ms)</th><th class=\"num\">.NET 10 Mean (ms)</th><th class=\"num\">Speedup Ratio</th></tr>");
        sb.AppendLine("      </thead>");
        sb.AppendLine("      <tbody>");

        foreach (var row in rows)
        {
            var ratioClass = row.SpeedupRatio >= 1.0 ? "good" : "warn";
            var ratioText = double.IsNaN(row.SpeedupRatio) ? "n/a" : row.SpeedupRatio.ToString("F3", CultureInfo.InvariantCulture) + "x";
            sb.AppendLine("        <tr>");
            sb.AppendLine($"          <td>{WebUtility.HtmlEncode(row.Algorithm)}</td>");
            sb.AppendLine($"          <td>{WebUtility.HtmlEncode(row.Dataset)}</td>");
            sb.AppendLine($"          <td class=\"num\">{FormatNsAsMs(row.Net9MeanNs)}</td>");
            sb.AppendLine($"          <td class=\"num\">{FormatNsAsMs(row.Net10MeanNs)}</td>");
            sb.AppendLine($"          <td class=\"num {ratioClass}\">{ratioText}</td>");
            sb.AppendLine("        </tr>");
        }

        sb.AppendLine("      </tbody>");
        sb.AppendLine("    </table>");
        sb.AppendLine("  </main>");
        sb.AppendLine("  <script>");
        sb.AppendLine("    const tableRows = Array.from(document.querySelectorAll('tbody tr')).map((row) => {");
        sb.AppendLine("      const cells = row.querySelectorAll('td');");
        sb.AppendLine("      const parseValue = (text) => {");
        sb.AppendLine("        const value = Number.parseFloat(text);");
        sb.AppendLine("        return Number.isFinite(value) && value > 0 ? value : null;");
        sb.AppendLine("      };");
        sb.AppendLine("      return {");
        sb.AppendLine("        algorithm: cells[0].textContent.trim(),");
        sb.AppendLine("        dataset: cells[1].textContent.trim(),");
        sb.AppendLine("        net9: parseValue(cells[2].textContent.trim()),");
        sb.AppendLine("        net10: parseValue(cells[3].textContent.trim())");
        sb.AppendLine("      };");
        sb.AppendLine("    });");
        sb.AppendLine("    const datasets = [...new Set(tableRows.map((row) => row.dataset))].sort((left, right) => {");
        sb.AppendLine("      const extractSize = (name) => Number.parseInt(name.replace(/\\D/g, ''), 10);");
        sb.AppendLine("      return extractSize(left) - extractSize(right);");
        sb.AppendLine("    });");
        sb.AppendLine("    const datasetSelect = document.getElementById('datasetSelect');");
        sb.AppendLine("    datasets.forEach((dataset) => {");
        sb.AppendLine("      const option = document.createElement('option');");
        sb.AppendLine("      option.value = dataset;");
        sb.AppendLine("      option.textContent = dataset;");
        sb.AppendLine("      datasetSelect.appendChild(option);");
        sb.AppendLine("    });");
        sb.AppendLine("    const defaultDataset = datasets.includes('random_10000.txt') ? 'random_10000.txt' : datasets[0];");
        sb.AppendLine("    datasetSelect.value = defaultDataset;");
        sb.AppendLine("    const chart = new Chart(document.getElementById('benchmarkChart'), {");
        sb.AppendLine("      type: 'bar',");
        sb.AppendLine("      data: {");
        sb.AppendLine("        labels: [],");
        sb.AppendLine("        datasets: [");
        sb.AppendLine("          {");
        sb.AppendLine("            label: '.NET 9 Mean (ms)',");
        sb.AppendLine("            data: [],");
        sb.AppendLine("            backgroundColor: 'rgba(21, 95, 73, 0.88)',");
        sb.AppendLine("            borderColor: '#114d3b',");
        sb.AppendLine("            borderWidth: 1.5,");
        sb.AppendLine("            borderRadius: 8");
        sb.AppendLine("          },");
        sb.AppendLine("          {");
        sb.AppendLine("            label: '.NET 10 Mean (ms)',");
        sb.AppendLine("            data: [],");
        sb.AppendLine("            backgroundColor: 'rgba(102, 171, 141, 0.9)',");
        sb.AppendLine("            borderColor: '#3f7f68',");
        sb.AppendLine("            borderWidth: 1.5,");
        sb.AppendLine("            borderRadius: 8");
        sb.AppendLine("          }");
        sb.AppendLine("        ]");
        sb.AppendLine("      },");
        sb.AppendLine("      options: {");
        sb.AppendLine("        responsive: true,");
        sb.AppendLine("        maintainAspectRatio: false,");
        sb.AppendLine("        interaction: { mode: 'index', intersect: false },");
        sb.AppendLine("        plugins: {");
        sb.AppendLine("          legend: {");
        sb.AppendLine("            labels: {");
        sb.AppendLine("              color: '#1f2b31',");
        sb.AppendLine("              font: { size: 13, family: 'Palatino Linotype, Iowan Old Style, serif' }");
        sb.AppendLine("            }");
        sb.AppendLine("          },");
        sb.AppendLine("          tooltip: {");
        sb.AppendLine("            callbacks: {");
        sb.AppendLine("              label: (context) => `${context.dataset.label}: ${context.raw === null ? 'not available' : context.raw.toFixed(4) + ' ms'}`");
        sb.AppendLine("            }");
        sb.AppendLine("          }");
        sb.AppendLine("        },");
        sb.AppendLine("        scales: {");
        sb.AppendLine("          x: { ticks: { color: '#1f2b31' }, grid: { display: false } },");
        sb.AppendLine("          y: {");
        sb.AppendLine("            beginAtZero: true,");
        sb.AppendLine("            ticks: { color: '#5f6f76', callback: (value) => `${value} ms` },");
        sb.AppendLine("            title: {");
        sb.AppendLine("              display: true,");
        sb.AppendLine("              text: 'Mean Runtime (ms)',");
        sb.AppendLine("              color: '#114d3b',");
        sb.AppendLine("              font: { weight: 'bold' }");
        sb.AppendLine("            },");
        sb.AppendLine("            grid: { color: 'rgba(221, 210, 195, 0.8)' }");
        sb.AppendLine("          }");
        sb.AppendLine("        }");
        sb.AppendLine("      }");
        sb.AppendLine("    });");
        sb.AppendLine("    const renderDataset = (datasetName) => {");
        sb.AppendLine("      const rows = tableRows.filter((row) => row.dataset === datasetName);");
        sb.AppendLine("      chart.data.labels = rows.map((row) => row.algorithm);");
        sb.AppendLine("      chart.data.datasets[0].data = rows.map((row) => row.net9);");
        sb.AppendLine("      chart.data.datasets[1].data = rows.map((row) => row.net10);");
        sb.AppendLine("      chart.update();");
        sb.AppendLine("    };");
        sb.AppendLine("    datasetSelect.addEventListener('change', (event) => {");
        sb.AppendLine("      renderDataset(event.target.value);");
        sb.AppendLine("    });");
        sb.AppendLine("    renderDataset(defaultDataset);");
        sb.AppendLine("  </script>");
        sb.AppendLine("</body>");
        sb.AppendLine("</html>");

        return sb.ToString();
    }

    private static string BuildTakeawaysMarkdown(IReadOnlyList<ComparisonRow> rows, string timestamp)
    {
        var comparableRows = rows
            .Where(r => !double.IsNaN(r.Net9MeanNs) && !double.IsNaN(r.Net10MeanNs) && !double.IsNaN(r.SpeedupRatio))
            .OrderByDescending(r => r.SpeedupRatio)
            .ToList();

        var averageRatio = comparableRows.Count == 0
            ? double.NaN
            : comparableRows.Average(r => r.SpeedupRatio);

        var sb = new StringBuilder();
        sb.AppendLine("# Key Takeaways");
        sb.AppendLine();
        sb.AppendLine($"- Generated: {timestamp}");
        sb.AppendLine($"- Comparable benchmark rows: {comparableRows.Count}");
        sb.AppendLine($"- Average speedup ratio (.NET9/.NET10): {(double.IsNaN(averageRatio) ? "n/a" : averageRatio.ToString("F3", CultureInfo.InvariantCulture) + "x")}");
        sb.AppendLine();
        sb.AppendLine("## Fastest Relative Wins for .NET 10");
        sb.AppendLine();

        foreach (var row in comparableRows.Take(5))
        {
            sb.AppendLine($"- {row.Algorithm} on {row.Dataset}: {row.SpeedupRatio.ToString("F3", CultureInfo.InvariantCulture)}x");
        }

        if (comparableRows.Count == 0)
        {
            sb.AppendLine("- No comparable rows were found. Run make benchmark first.");
        }

        sb.AppendLine();
        sb.AppendLine("## Notes");
        sb.AppendLine();
        sb.AppendLine("- Lower mean time is better.");
        sb.AppendLine("- Ratio above 1.000x means .NET 10 is faster.");
        sb.AppendLine("- Ratio below 1.000x means .NET 9 is faster for that row.");

        return sb.ToString();
    }

    private static string FormatNsAsMs(double ns)
    {
        if (double.IsNaN(ns))
        {
            return "n/a";
        }

        return (ns / 1_000_000d).ToString("F4", CultureInfo.InvariantCulture);
    }

    private sealed record ComparisonRow(
        string Algorithm,
        string Dataset,
        double Net9MeanNs,
        double Net10MeanNs,
        double SpeedupRatio);
}
