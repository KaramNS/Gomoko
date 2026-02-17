# Makefile for Gomoku Project

# Variables
SRC_DIR = src
BIN_DIR = bin
ENTRY_POINT = src.Main
JAR_NAME = Gomoku.jar
SOURCES = $(wildcard $(SRC_DIR)/*.java)

# Default target
all: compile

# Compile source files
compile:
	@if not exist "$(BIN_DIR)" mkdir "$(BIN_DIR)"
	javac -d $(BIN_DIR) $(SOURCES)

# Run the application
run: compile
	java -cp $(BIN_DIR) $(ENTRY_POINT)

# Create executable JAR
jar: compile
	jar cfe $(JAR_NAME) $(ENTRY_POINT) -C $(BIN_DIR) .

# Clean compiled files
clean:
	@if exist "$(BIN_DIR)" rmdir /s /q "$(BIN_DIR)"
	@if exist "$(JAR_NAME)" del "$(JAR_NAME)"

.PHONY: all compile run jar clean
