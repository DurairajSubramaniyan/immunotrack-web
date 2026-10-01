#!/usr/bin/env bash
# ImmunoTrack Web - One-Command Smoke Test Runner & Auto-Deployer
# Usage:
#   ./run-smoke.sh                  # Runs smoke suite in standard mode & updates public report
#   ./run-smoke.sh -Dheadless=true  # Runs smoke suite headlessly & updates public report

set -e
REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "${REPO_ROOT}"

echo "=========================================================================="
echo "  1/2: EXECUTING FULL APPLICATION SMOKE TEST SUITE"
echo "=========================================================================="

mvn test -Dtest=SmokeTestRunner "$@" || true

echo "=========================================================================="
echo "  2/2: AUTO-DEPLOYING SMOKE REPORT TO GITHUB PAGES"
echo "=========================================================================="

./deploy-report.sh
