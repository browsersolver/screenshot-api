<?php
// Website Screenshot API – PHP example (ext-curl)
// Usage: BROWSERSOLVER_API_KEY=your-key php screenshot.php

function call(string $method, string $url, ?string $body = null): string
{
    $ch = curl_init($url);
    $headers = ['x-api-key: ' . getenv('BROWSERSOLVER_API_KEY')];
    if ($body !== null) {
        $headers[] = 'Content-Type: application/json';
        curl_setopt($ch, CURLOPT_POSTFIELDS, $body);
    }
    curl_setopt_array($ch, [
        CURLOPT_CUSTOMREQUEST => $method,
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_TIMEOUT => 120,
        CURLOPT_HTTPHEADER => $headers,
    ]);
    $res = curl_exec($ch);
    $status = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);
    if ($status >= 400) {
        fwrite(STDERR, "HTTP $status: $res\n");
        exit(1);
    }
    return $res;
}

$query = http_build_query([
    'url' => 'https://www.amazon.com/s?k=pokemon'
]);
$response = call('GET', 'https://api.browsersolver.com/v1/website/screenshot' . ($query ? '?' . $query : ''));

file_put_contents('screenshot.png', $response);
echo 'Saved screenshot.png', PHP_EOL;
