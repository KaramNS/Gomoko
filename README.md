# Gomoku

A Java implementation of the classic Gomoku (Five in a Row) strategy board game.

## Features

- **Game Modes**:
  - Player vs Player
  - Player vs Computer
- **Save & Load**: Save your current game state and resume later.
- **Customizable Rules**:
  - Configurable board size (default 15x15, max 30x30).
  - Configurable win condition (default 5 in a row).
- **Console Interface**: Interactive command-line interface.

## Project Structure

- `src/`: Source code files.
- `SavedGames/`: Directory for saved game files.
- `bin/`: Compiled bytecode (generated).

## How to Run

### Requirements
- Java Development Kit (JDK) installed.

### Using Makefile

To compile and run the application:

```bash
make run
```

To create a clickable executable JAR file:

```bash
make jar
```

To clean up compiled files:

```bash
make clean
```

### Manual Compilation

```bash
javac -d bin src/*.java
java -cp bin src.Main
```

## Authors

- Syrine BEN HASSINE
- ELNASORY Karam
