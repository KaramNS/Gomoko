# Gomoku

A Java implementation of the classic **Gomoku** (Five in a Row) strategy board game.

## Features

- **Game Modes**: Player vs Player, Player vs Computer.
- **Save & Load**: Save your current game and resume later.
- **Customizable Rules**: Configurable board size (default 15×15, max 30×30) and win condition (default 5 in a row).
- **Console Interface**: Interactive command-line menus.

## Project Structure

```
Gomoko/
├── src/            # Java source files
├── bin/            # Compiled .class files (generated)
├── SavedGames/     # Saved game files
├── Gomoku.jar      # Executable JAR (generated)
├── Makefile        # Build script (Linux/macOS or Windows with make)
├── build.bat       # Build script (Windows)
└── README.md
```

## How to Build & Run

### Requirements

- **Java Development Kit (JDK)** 17 or later.

### Windows (build.bat)

```cmd
build.bat compile     REM Compile sources
build.bat run         REM Compile + run the game
build.bat jar         REM Create clickable Gomoku.jar
build.bat clean       REM Remove bin/ and Gomoku.jar
```

### Linux / macOS (Makefile)

```bash
make compile   # Compile sources
make run       # Compile + run
make jar       # Create clickable JAR
make clean     # Clean up
```

### Manual Compilation

```bash
javac -d bin src/*.java
java -cp bin src.Main
```

### Running the JAR

Double-click `Gomoku.jar`, or from a terminal:

```bash
java -jar Gomoku.jar
```

## Authors

- **ELNASORY Karam**
- **Jean-Baptiste LARGERON**
- **Syrine BEN HASSINE**

