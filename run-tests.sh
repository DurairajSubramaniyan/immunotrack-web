#!/usr/bin/env bash
# ImmunoTrack Web - One-command Test Runner & Auto-Deployer
# Usage:
#   ./run-tests.sh                                 # Runs all tests and auto-refreshes public report
#   ./run-tests.sh -Dcucumber.filter.tags="@login" # Runs specific tag and auto-refreshes public report

set -e
REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "${REPO_ROOT}"

echo "=========================================================================="
echo "  1/2: RUNNING IMMUNOTRACK TESTS"
echo "=========================================================================="

mvn test "$@" || true

echo "=========================================================================="
echo "  2/2: AUTO-DEPLOYING TO GITHUB PAGES"
echo "=========================================================================="

./deploy-report.sh
