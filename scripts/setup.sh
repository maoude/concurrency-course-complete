#!/usr/bin/env bash
set -euo pipefail

echo "=== SETUP (MINIMAL) ==="
echo "User: $(whoami)"
echo "PWD: $(pwd)"
echo "Workspace content:"
ls -la /workspace | head -n 20
echo "=== OK ==="
