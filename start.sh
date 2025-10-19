#!/usr/bin/env bash
set -euo pipefail

PROFILE="${1:-${SPRING_PROFILES_ACTIVE:-dev}}"
PROFILE=$(echo "$PROFILE" | tr '[:upper:]' '[:lower:]')

echo "=== MELI-ecommerce startup ==="
echo "Profile: $PROFILE"
export SPRING_PROFILES_ACTIVE="$PROFILE"

ensure_mvnw_exec() {
  if [ -f "./mvnw" ] && [ ! -x "./mvnw" ]; then
    chmod +x ./mvnw
  fi
}

ensure_mvnw_exec

case "$PROFILE" in
  test)
    echo "Running tests only..."
    ./mvnw test -Dspring.profiles.active="$PROFILE"
    echo "Tests finished successfully."
    ;;
  dev)
    echo "Starting Spring Boot app (dev profile)..."
    ./mvnw spring-boot:run -Dspring-boot.run.profiles="$PROFILE"
    ;;
  prod)
    echo "Packaging for production..."
    ./mvnw -DskipTests package
    jar_file=$(ls target/*.jar 2>/dev/null | head -n 1 || true)
    if [ -z "$jar_file" ]; then
      echo "ERROR: Could not find JAR in target/"
      exit 1
    fi
    echo "Running JAR: $jar_file"
    java -jar "$jar_file" --spring.profiles.active="$PROFILE"
    ;;
  *)
    echo "Unknown profile '$PROFILE', defaulting to dev..."
    ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
    ;;
esac
