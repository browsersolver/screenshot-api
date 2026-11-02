// Website Screenshot API – Java example (Java 15+)
// Usage: BROWSERSOLVER_API_KEY=your-key java ScreenshotExample.java
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScreenshotExample {
    static final HttpClient CLIENT = HttpClient.newHttpClient();
    static final String KEY = System.getenv("BROWSERSOLVER_API_KEY");

    static String enc(String v) {
        return URLEncoder.encode(v, StandardCharsets.UTF_8);
    }

    static HttpResponse<byte[]> call(String method, String url, String body) throws Exception {
        HttpRequest.Builder b = HttpRequest.newBuilder().uri(URI.create(url)).header("x-api-key", KEY)
            .timeout(Duration.ofSeconds(120));
        b = body == null ? b.method(method, HttpRequest.BodyPublishers.noBody())
            : b.header("Content-Type", "application/json").method(method, HttpRequest.BodyPublishers.ofString(body));
        HttpResponse<byte[]> r = CLIENT.send(b.build(), HttpResponse.BodyHandlers.ofByteArray());
        if (r.statusCode() >= 400) {
            System.err.println("HTTP " + r.statusCode() + ": " + new String(r.body(), StandardCharsets.UTF_8));
            System.exit(1);
        }
        return r;
    }

    static String field(String json, String name) {
        Matcher m = Pattern.compile("\"" + name + "\"\\s*:\\s*\"([^\"]*)\"").matcher(json);
        return m.find() ? m.group(1) : "";
    }

    public static void main(String[] args) throws Exception {
        String query = "url=" + enc("https://www.amazon.com/s?k=pokemon");
        HttpResponse<byte[]> response = call("GET", "https://api.browsersolver.com/v1/website/screenshot" + (query.isEmpty() ? "" : "?" + query), null);
        Files.write(Path.of("screenshot.png"), response.body());
        System.out.println("Saved screenshot.png");
    }
}
