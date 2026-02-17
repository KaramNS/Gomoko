package src;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a computer player in the game.
 * It uses the Minimax algorithm with Alpha-Beta pruning to choose the best
 * move.
 *
 * @author ELNASORY Karam
 */
public class Computer extends User {
    /** Maximum search depth for the Minimax algorithm */
    private static final int MAX_DEPTH = 3;

    /** Radius around existing stones to consider as candidate moves */
    private static final int NEIGHBOR_RADIUS = 2;

    /** Scoring constants for the heuristic evaluation */
    private static final int SCORE_WIN = 100_000;
    private static final int SCORE_OPEN_FOUR = 10_000;
    private static final int SCORE_FOUR = 1_000;
    private static final int SCORE_OPEN_THREE = 1_000;
    private static final int SCORE_THREE = 100;
    private static final int SCORE_OPEN_TWO = 100;
    private static final int SCORE_TWO = 10;

    /**
     * Constructor for Computer class.
     * 
     * @param color The Color of the player.
     */
    public Computer(Color color) {
        super("Computer", 15, color);
    }

    /**
     * Constructor for Computer class with custom score.
     * 
     * @param score initial score of the player.
     * @param color The Color of the player tokens.
     */
    public Computer(int score, Color color) {
        super("Computer", score, color);
    }

    /**
     * Chooses the best placement on the matrix using Minimax with Alpha-Beta
     * pruning.
     * 
     * @param matrix the game board.
     * @return Coordonates of the best move.
     */
    @Override
    public Coordonates chosePlacement(Matrix matrix) {
        System.out.println("\nComputer is thinking...");

        int boardSize = matrix.getLength();
        List<Coordonates> moves = getFeasibleMoves(matrix, boardSize);

        // Fallback: if no feasible moves found (empty board), play center
        if (moves.isEmpty()) {
            int center = boardSize / 2;
            return new Coordonates(center, center);
        }

        Coordonates bestMove = moves.get(0);
        int bestValue = Integer.MIN_VALUE;

        for (Coordonates move : moves) {
            // Simulate the move
            placeToken(matrix, move, this.color());

            int value = minimax(matrix, MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false, boardSize);

            // Undo the move
            undoToken(matrix, move);

            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }

        return bestMove;
    }

    // ======================== Minimax with Alpha-Beta ========================

    /**
     * Minimax algorithm with Alpha-Beta pruning.
     * 
     * @param matrix    the game board
     * @param depth     remaining search depth
     * @param alpha     best value the maximizer can guarantee
     * @param beta      best value the minimizer can guarantee
     * @param isMax     true if it's the maximizer's (computer's) turn
     * @param boardSize size of the board
     * @return the heuristic value of the board position
     */
    private int minimax(Matrix matrix, int depth, int alpha, int beta, boolean isMax, int boardSize) {
        // Terminal conditions
        if (depth == 0 || matrix.isBoardFull()) {
            return evaluateBoard(matrix, boardSize);
        }

        List<Coordonates> moves = getFeasibleMoves(matrix, boardSize);
        if (moves.isEmpty()) {
            return evaluateBoard(matrix, boardSize);
        }

        if (isMax) {
            int maxEval = Integer.MIN_VALUE;
            for (Coordonates move : moves) {
                placeToken(matrix, move, this.color());

                // Check if this move wins
                if (matrix.getCell(move).isWon(5)) {
                    undoToken(matrix, move);
                    return SCORE_WIN;
                }

                int eval = minimax(matrix, depth - 1, alpha, beta, false, boardSize);
                undoToken(matrix, move);

                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha)
                    break; // Beta cutoff
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            Color opponentColor = getOpponentColor(matrix, boardSize);
            for (Coordonates move : moves) {
                placeToken(matrix, move, opponentColor);

                // Check if opponent wins
                if (matrix.getCell(move).isWon(5)) {
                    undoToken(matrix, move);
                    return -SCORE_WIN;
                }

                int eval = minimax(matrix, depth - 1, alpha, beta, true, boardSize);
                undoToken(matrix, move);

                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha)
                    break; // Alpha cutoff
            }
            return minEval;
        }
    }

    // ======================== Move generation ========================

    /**
     * Returns a list of feasible moves: empty cells within NEIGHBOR_RADIUS of any
     * occupied cell.
     * This drastically reduces the branching factor compared to checking all empty
     * cells.
     */
    private List<Coordonates> getFeasibleMoves(Matrix matrix, int boardSize) {
        boolean[][] considered = new boolean[boardSize][boardSize];
        List<Coordonates> moves = new ArrayList<>();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Coordonates coord = new Coordonates(i, j);
                if (matrix.getCell(coord).getColor() != Color.WHITE) {
                    // Look at neighbors around this occupied cell
                    for (int di = -NEIGHBOR_RADIUS; di <= NEIGHBOR_RADIUS; di++) {
                        for (int dj = -NEIGHBOR_RADIUS; dj <= NEIGHBOR_RADIUS; dj++) {
                            int ni = i + di;
                            int nj = j + dj;
                            if (ni >= 0 && ni < boardSize && nj >= 0 && nj < boardSize && !considered[ni][nj]) {
                                considered[ni][nj] = true;
                                Coordonates neighbor = new Coordonates(ni, nj);
                                if (matrix.getCell(neighbor).getColor() == Color.WHITE) {
                                    moves.add(neighbor);
                                }
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    // ======================== Board evaluation ========================

    /**
     * Evaluates the entire board from the computer's perspective.
     * Positive scores favor the computer, negative scores favor the opponent.
     */
    private int evaluateBoard(Matrix matrix, int boardSize) {
        int score = 0;
        Color myColor = this.color();
        Color oppColor = getOpponentColor(matrix, boardSize);

        // Check all 4 directions: horizontal, vertical, diagonal-down-right,
        // diagonal-down-left
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, -1 } };

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                for (int[] dir : directions) {
                    score += evaluateLine(matrix, i, j, dir[0], dir[1], boardSize, myColor, oppColor);
                }
            }
        }
        return score;
    }

    /**
     * Evaluates a line of up to 5 cells starting at (row, col) in direction (dRow,
     * dCol).
     * Returns a positive score if the window favors the computer, negative if it
     * favors the opponent.
     */
    private int evaluateLine(Matrix matrix, int row, int col, int dRow, int dCol,
            int boardSize, Color myColor, Color oppColor) {
        int windowSize = 5; // Win condition

        // Check if the window fits on the board
        int endRow = row + dRow * (windowSize - 1);
        int endCol = col + dCol * (windowSize - 1);
        if (endRow < 0 || endRow >= boardSize || endCol < 0 || endCol >= boardSize) {
            return 0;
        }

        int myCount = 0;
        int oppCount = 0;

        for (int k = 0; k < windowSize; k++) {
            Color cellColor = matrix.getCell(new Coordonates(row + dRow * k, col + dCol * k)).getColor();
            if (cellColor == myColor)
                myCount++;
            else if (cellColor != Color.WHITE)
                oppCount++;
        }

        // If both players have stones in this window, it's contested — no value
        if (myCount > 0 && oppCount > 0) {
            return 0;
        }

        // Score my patterns
        if (myCount > 0) {
            return scorePattern(myCount, windowSize - myCount);
        }

        // Score opponent patterns (negative for the computer)
        if (oppCount > 0) {
            return -scorePattern(oppCount, windowSize - oppCount);
        }

        return 0;
    }

    /**
     * Scores a pattern based on the count of stones and empty spaces.
     * 
     * @param count  number of same-color stones in the window
     * @param blanks number of empty cells in the window
     */
    private int scorePattern(int count, int blanks) {
        if (count == 5)
            return SCORE_WIN;
        if (count == 4 && blanks == 1)
            return SCORE_OPEN_FOUR;
        if (count == 3 && blanks == 2)
            return SCORE_OPEN_THREE;
        if (count == 3 && blanks == 1)
            return SCORE_THREE;
        if (count == 2 && blanks == 3)
            return SCORE_OPEN_TWO;
        if (count == 2 && blanks == 2)
            return SCORE_TWO;
        return count; // Single stone or other minor patterns
    }

    // ======================== Helpers ========================

    /**
     * Places a token of the given color on the board at the specified coordinates.
     * This does NOT decrement any player's score — used for simulation only.
     */
    private void placeToken(Matrix matrix, Coordonates coord, Color color) {
        matrix.getCell(coord).setToken(new Token(color));
    }

    /**
     * Removes a token from the board (resets to WHITE).
     */
    private void undoToken(Matrix matrix, Coordonates coord) {
        matrix.getCell(coord).setToken(new Token(Color.WHITE));
    }

    /**
     * Determines the opponent color by finding the first non-WHITE, non-computer
     * color on the board.
     * Falls back to YELLOW or PURPLE if no opponent stone is found yet.
     */
    private Color getOpponentColor(Matrix matrix, int boardSize) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Color c = matrix.getCell(new Coordonates(i, j)).getColor();
                if (c != Color.WHITE && c != this.color()) {
                    return c;
                }
            }
        }
        // Fallback: guess based on computer color
        return (this.color() == Color.YELLOW) ? Color.PURPLE : Color.YELLOW;
    }
}
