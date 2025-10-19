@echo off
setlocal enabledelayedexpansion

REM ==============================
REM MELI-ecommerce startup script
REM ==============================
REM Usage:
REM   start.bat            -> defaults to dev
REM   start.bat dev
REM   start.bat test
REM   start.bat prod

set "PROFILE=%~1"
if "%PROFILE%"=="" (
    if not "%SPRING_PROFILES_ACTIVE%"=="" (
        set "PROFILE=%SPRING_PROFILES_ACTIVE%"
    ) else (
        set "PROFILE=dev"
    )
)

echo === MELI-ecommerce startup ===
echo Profile: %PROFILE%
set "SPRING_PROFILES_ACTIVE=%PROFILE%"

if not exist mvnw.cmd (
    echo [ERROR] mvnw.cmd not found in project root.
    exit /b 1
)

REM ===== TEST ENVIRONMENT =====
if /I "%PROFILE%"=="test" (
    echo Running tests only...
    call mvnw.cmd test -Dspring.profiles.active=%PROFILE%
    if errorlevel 1 (
        echo [ERROR] Tests failed.
        exit /b 1
    )
    echo Tests passed successfully!
    goto :eof
)

REM ===== DEVELOPMENT =====
if /I "%PROFILE%"=="dev" (
    echo Starting Spring Boot app with dev profile...
    call mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=%PROFILE%
    goto :eof
)

REM ===== PRODUCTION =====
if /I "%PROFILE%"=="prod" (
    echo Packaging for production...
    call mvnw.cmd -DskipTests package
    for %%f in (target\*.jar) do (
        set "JAR=%%f"
        goto foundJar
    )

    :foundJar
    if "!JAR!"=="" (
        echo [ERROR] Could not find JAR in target\ after build.
        exit /b 1
    )

    echo Running JAR: !JAR!
    java -jar "!JAR!" --spring.profiles.active=%PROFILE%
    goto :eof
)

REM ===== FALLBACK =====
echo Unknown profile '%PROFILE%', running default (dev)...
call mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
goto :eof
