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

REM Get profile from argument or environment
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

REM Check for mvnw.cmd
if not exist mvnw.cmd (
    echo [Warning] mvnw.cmd not found in project root. Make sure Maven or wrapper exists.
)

REM ===== DEVELOPMENT / TEST =====
if /I "%PROFILE%"=="dev" (
    echo Running: mvnw spring-boot:run -Dspring-boot.run.profiles=%PROFILE%
    call mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=%PROFILE%
    goto :eof
)

if /I "%PROFILE%"=="test" (
    echo Running: mvnw spring-boot:run -Dspring-boot.run.profiles=%PROFILE%
    call mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=%PROFILE%
    goto :eof
)

REM ===== PRODUCTION =====
if /I "%PROFILE%"=="prod" (
    echo Packaging for production...
    set "JAR="
    for %%f in (target\*.jar) do (
        set "JAR=%%f"
        goto foundJar
    )

    :foundJar
    if "!JAR!"=="" (
        echo No JAR found, building project...
        call mvnw.cmd -DskipTests package
        for %%f in (target\*.jar) do (
            set "JAR=%%f"
            goto foundJar2
        )
    )

    :foundJar2
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
