@echo off
REM start.bat - start MELI-ecommerce on Windows (cmd)
REM Usage:
REM   start.bat            -> defaults to dev
REM   start.bat dev
REM   start.bat test
REM   start.bat prod

REM get profile from arg or env, default dev
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
set SPRING_PROFILES_ACTIVE=%PROFILE%

REM Ensure mvnw.cmd exists when needed
if exist mvnw.cmd (
  REM mvnw.cmd is present
) else (
  echo Warning: mvnw.cmd not found in project root. Make sure Maven or wrapper exists.
)

REM Run depending on profile
if /I "%PROFILE%"=="dev" (
  echo Running mvnw spring-boot:run -Dspring-boot.run.profiles=%PROFILE%
  call mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=%PROFILE%
  goto :eof
)

if /I "%PROFILE%"=="test" (
  echo Running mvnw spring-boot:run -Dspring-boot.run.profiles=%PROFILE%
  call mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=%PROFILE%
  goto :eof
)

if /I "%PROFILE%"=="prod" (
  REM locate jar
  set "JAR="
  for %%f in (target\*.jar) do set "JAR=%%f" & goto :foundJar
  :foundJar
  if "%JAR%"=="" (
    echo Packaged jar not found, building (skip tests)...
    call mvnw.cmd -DskipTests package
    for %%f in (target\*.jar) do set "JAR=%%f" & goto :foundJar2
    :foundJar2
  )
  if "%JAR%"=="" (
    echo ERROR: Could not find jar in target\ after build. Aborting.
    exit /b 1
  )
  echo Running jar: %JAR%
  java -jar "%JAR%" --spring.profiles.active=%PROFILE%
  goto :eof
)

REM fallback
echo Unknown profile '%PROFILE%', running with mvnw by default.
call mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=%PROFILE%
