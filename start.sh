#!/usr/bin/env bash
set -euo pipefail

# start.sh - start the MELI-ecommerce Spring Boot app with a profile
# Usage:
#   ./start.sh                # defaults to 'dev'
#   ./start.sh dev            # same
#   ./start.sh test
#   ./start.sh prod           # attempts to run packaged jar; builds if missing
#
# The script exports SPRING_PROFILES_ACTIVE and either runs ./mvnw spring-boot:run
# with the profile or runs the packaged jar when profile=prod.

PROFILE="${1:-${SPRING_PROFILES_ACTIVE:-dev}}"
PROFILE=$(echo "$PROFILE" | tr '[:upper:]' '[:lower:]')

echo "=== MELI-ecommerce startup script ==="
echo "Selected profile: $PROFILE"
export SPRING_PROFILES_ACTIVE="$PROFILE"

# helper: ensure mvnw is executable
ensure_mvnw_exec() {
  if [ -f "./mvnw" ] && [ ! -x "./mvnw" ]; then
    echo "Making ./mvnw executable..."
    chmod +x ./mvnw
  fi
}

run_with_mvnw() {
  ensure_mvnw_exec
  echo "Running using Maven wrapper with profile '$PROFILE'..."
  # Pass the profile to the mvn plugin and also export the env var so both ways are covered
  ./mvnw spring-boot:run -Dspring-boot.run.profiles="$PROFILE"
}

run_packaged_jar() {
  # find jar in target/
  jar_file=$(ls target/*.jar 2>/dev/null | head -n 1 || true)
  if [ -z "$jar_file" ]; then
    echo "Packaged jar not found in target/. Building project (skip tests)..."
    ensure_mvnw_exec
    ./mvnw -DskipTests package
    jar_file=$(ls target/*.jar 2>/dev/null | head -n 1 || true)
    if [ -z "$jar_file" ]; then
      echo "ERROR: jar build failed or no jar produced. Aborting."
      exit 1
    fi
  fi

  echo "Running jar: $jar_file with profile '$PROFILE'..."
  java -jar "$jar_file" --spring.profiles.active="$PROFILE"
}

case "$PROFILE" in
  dev|development|test)
    # For dev & test we prefer the fast mvn spring-boot:run path (H2 console available)
    run_with_mvnw
    ;;
  prod|production)
    # For production, run packaged jar if possible (safer)
    run_packaged_jar
    ;;
  *)
    echo "Unknown profile '$PROFILE' — falling back to running with mvnw."
    run_with_mvnw
    ;;
esac
