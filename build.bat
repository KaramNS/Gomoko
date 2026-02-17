@echo off
REM ============================================
REM  Build script for Gomoku - Windows
REM ============================================

set SRC_DIR=src
set BIN_DIR=bin
set JAR_NAME=Gomoku.jar
set ENTRY=src.Main

if "%1"=="" goto compile
if "%1"=="compile" goto compile
if "%1"=="run" goto run
if "%1"=="jar" goto jar
if "%1"=="clean" goto clean
echo Unknown target: %1
echo Usage: build.bat [compile^|run^|jar^|clean]
goto end

:compile
echo [*] Compiling...
if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"
javac -d %BIN_DIR% %SRC_DIR%\*.java
if errorlevel 1 (
    echo [!] Compilation failed.
    goto end
)
echo [OK] Compilation successful.
goto end

:run
call :compile
echo [*] Running Gomoku...
java -cp %BIN_DIR% %ENTRY%
goto end

:jar
call :compile
echo [*] Creating JAR...
jar cfe %JAR_NAME% %ENTRY% -C %BIN_DIR% .
if errorlevel 1 (
    echo [!] JAR creation failed.
    goto end
)
echo [OK] Created %JAR_NAME% - double-click to run!
goto end

:clean
echo [*] Cleaning...
if exist "%BIN_DIR%" rmdir /s /q "%BIN_DIR%"
if exist "%JAR_NAME%" del "%JAR_NAME%"
echo [OK] Clean complete.
goto end

:end
