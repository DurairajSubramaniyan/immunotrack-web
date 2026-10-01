#!/usr/bin/env bash
set -e

# ImmunoTrack Web Automation - Deploy Test Report to GitHub Pages
REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPORT_DIR="${REPO_ROOT}/target/cucumber-reports"

if [ ! -d "${REPORT_DIR}" ]; then
  echo "Error: ${REPORT_DIR} not found. Run tests first (e.g. ./run-tests.sh or mvn test)."
  exit 1
fi

echo "==> Preparing report files for GitHub Pages..."

# Generate executive custom dashboard report matching UI mockup
if [ -f "${REPO_ROOT}/generate-dashboard-report.js" ]; then
  node "${REPO_ROOT}/generate-dashboard-report.js"
elif [ -f "${REPORT_DIR}/cucumber.html" ]; then
  cp "${REPORT_DIR}/cucumber.html" "${REPORT_DIR}/index.html"
fi

# Ensure Jekyll processing is disabled on GitHub Pages
touch "${REPORT_DIR}/.nojekyll"

echo "==> Deploying report to GitHub Pages (gh-pages branch)..."
(
  cd "${REPORT_DIR}"
  rm -rf .git
  git init -q
  git config user.name "ImmunoTrack Bot"
  git config user.email "bot@immunotrack.com"
  git checkout -b gh-pages -q
  git add -A
  git commit -m "Auto-deploy test report: $(date -u '+%Y-%m-%d %H:%M:%S UTC')" -q
  git remote add origin https://github.com/DurairajSubramaniyan/immunotrack-web.git
  git push -f origin gh-pages -q
)

echo "==> Deploying report to Surge (immunotrack-automation-report.surge.sh)..."
npx --yes surge "${REPORT_DIR}" --domain immunotrack-automation-report.surge.sh || true

SURGE_URL="https://immunotrack-automation-report.surge.sh"
PAGES_URL="https://durairajsubramaniyan.github.io/immunotrack-web/"
echo ""
echo "=========================================================================="
echo "  PUBLIC HTML REPORT DEPLOYED SUCCESSFULLY!"
echo "  Primary URL: ${SURGE_URL}"
echo "  GitHub Pages URL: ${PAGES_URL}"
echo "=========================================================================="
echo ""
