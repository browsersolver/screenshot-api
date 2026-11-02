# Website Screenshot API – Ruby example (stdlib only)
# Usage: BROWSERSOLVER_API_KEY=your-key ruby screenshot.rb
require "json"
require "net/http"
require "uri"

def call(method, url, body = nil)
  uri = URI(url)
  klass = { "GET" => Net::HTTP::Get, "POST" => Net::HTTP::Post, "PUT" => Net::HTTP::Put, "PATCH" => Net::HTTP::Patch }[method]
  req = klass.new(uri)
  req["x-api-key"] = ENV.fetch("BROWSERSOLVER_API_KEY")
  if body
    req["Content-Type"] = "application/json"
    req.body = body
  end
  res = Net::HTTP.start(uri.host, uri.port, use_ssl: true, read_timeout: 120) { |http| http.request(req) }
  abort "HTTP #{res.code}: #{res.body}" unless res.is_a?(Net::HTTPSuccess)
  res.body
end

uri = URI("https://api.browsersolver.com/v1/website/screenshot")
uri.query = URI.encode_www_form('url' => 'https://www.amazon.com/s?k=pokemon')
response = call("GET", uri.to_s)

File.binwrite("screenshot.png", response)
puts "Saved screenshot.png"
