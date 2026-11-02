// Website Screenshot API – C# example (.NET 6+, top-level statements)
// Usage: dotnet new console -n Example && copy this file to Program.cs, then BROWSERSOLVER_API_KEY=your-key dotnet run
using System.Text;
using System.Text.Json;

var apiKey = Environment.GetEnvironmentVariable("BROWSERSOLVER_API_KEY") ?? throw new InvalidOperationException("Set BROWSERSOLVER_API_KEY");
using var http = new HttpClient { Timeout = TimeSpan.FromSeconds(120) };
http.DefaultRequestHeaders.Add("x-api-key", apiKey);

var query = "url=" + Uri.EscapeDataString("https://www.amazon.com/s?k=pokemon");
var response = await http.GetAsync("https://api.browsersolver.com/v1/website/screenshot" + (query.Length > 0 ? "?" + query : ""));
if (!response.IsSuccessStatusCode)
{
    Console.Error.WriteLine($"HTTP {(int)response.StatusCode}: {await response.Content.ReadAsStringAsync()}");
    return;
}

await File.WriteAllBytesAsync("screenshot.png", await response.Content.ReadAsByteArrayAsync());
Console.WriteLine("Saved screenshot.png");
