using System;
using System.Text;
using System.Xml;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ComprehensiveNavigation.Pages;

public class SitemapModel : PageModel
{
    public IActionResult OnGet()
    {
        string baseUrl = $"{Request.Scheme}://{Request.Host}{Request.PathBase}";

        var sb = new StringBuilder();
        var settings = new XmlWriterSettings
        {
            Indent = true,
            Encoding = Encoding.UTF8
        };

        using (var writer = XmlWriter.Create(sb, settings))
        {
            writer.WriteStartElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");

            // Static Pages
            AddUrl(writer, baseUrl, "/");
            AddUrl(writer, baseUrl, "/Docs");
            AddUrl(writer, baseUrl, "/Docs/GettingStarted");
            AddUrl(writer, baseUrl, "/Docs/Installation");
            AddUrl(writer, baseUrl, "/Products");
            AddUrl(writer, baseUrl, "/Account/Login");

            writer.WriteEndElement();
        }

        // Return XML content
        return Content(sb.ToString(), "application/xml");
    }

    private void AddUrl(XmlWriter writer, string baseUrl, string relativePath)
    {
        writer.WriteStartElement("url");
        writer.WriteElementString("loc", $"{baseUrl}{relativePath}");
        writer.WriteElementString("lastmod", DateTime.UtcNow.ToString("yyyy-MM-dd"));
        writer.WriteElementString("changefreq", "weekly");
        writer.WriteElementString("priority", relativePath == "/" ? "1.0" : "0.8");
        writer.WriteEndElement();
    }
}
