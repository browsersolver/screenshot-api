#!/usr/bin/env bash
# Website Screenshot API – cURL example
# Usage: BROWSERSOLVER_API_KEY=your-key bash screenshot.sh
set -euo pipefail

curl -sS --fail-with-body -G "https://api.browsersolver.com/v1/website/screenshot" \
  -H "x-api-key: ${BROWSERSOLVER_API_KEY}" \
  --data-urlencode 'url=https://www.amazon.com/s?k=pokemon' \
  -o screenshot.png

echo "Saved screenshot.png"
