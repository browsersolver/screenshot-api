// Website Screenshot API – Node.js example (Node 18+, ES module)
// Usage: BROWSERSOLVER_API_KEY=your-key node screenshot.mjs
import { writeFile } from "node:fs/promises";

const headers = { "x-api-key": process.env.BROWSERSOLVER_API_KEY, "Content-Type": "application/json" };

const params = new URLSearchParams({
  "url": "https://www.amazon.com/s?k=pokemon",
});
const response = await fetch(`https://api.browsersolver.com/v1/website/screenshot?${params}`, { headers });
if (!response.ok) {
  console.error(`HTTP ${response.status}: ${await response.text()}`);
  process.exit(1);
}

await writeFile("screenshot.png", Buffer.from(await response.arrayBuffer()));
console.log("Saved screenshot.png");
