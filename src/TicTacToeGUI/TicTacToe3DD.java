import java.util.Random;
import java.util.Scanner;

// Enum to represent X, O, and EMPTY states
enum Cell {
    X, O, EMPTY
}

// TicTacToe3D class to handle game logic
public class TicTacToe3DD {
    private Cell[][][] board;
    private Cell currentPlayer;
    private static final int SIZE = 4;

    // Constructor initializes the game board
    public TicTacToe3DD() {
        board = new Cell[SIZE][SIZE][SIZE];
        initializeBoard();
    }

    // Initialize the board to EMPTY
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    board[i][j][k] = Cell.EMPTY;
                }
            }
        }
    }

    // Print the current state of the board
    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            System.out.println("Level " + i + ":");
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    System.out.print(board[i][j][k] == Cell.EMPTY ? " " : board[i][j][k]);
                    if (k < SIZE - 1) System.out.print(" | ");
                }
                System.out.println();
                if (j < SIZE - 1) System.out.println("---------");
            }
            System.out.println();
        }
    }

    // Make a move on the board
    public boolean makeMove(int level, int row, int col) {
        if (board[level][row][col] == Cell.EMPTY) {
            board[level][row][col] = currentPlayer;
            if (checkWinner(level, row, col)) {
                printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                return true;
            } else if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                return true;
            }
            currentPlayer = (currentPlayer == Cell.X) ? Cell.O : Cell.X;
        } else {
            System.out.println("Invalid move, try again.");
        }
        return false;
    }

    // Check if the current player has won
    private boolean checkWinner(int level, int row, int col) {
        // Check rows, columns, and levels
        if (checkLine(level, row, 0, 0, 0, 1) || // Row
                checkLine(level, 0, col, 0, 1, 0) || // Column
                checkLine(0, row, col, 1, 0, 0)) {  // Level
            return true;
        }
        // Check layer diagonals
        if (row == col && checkLine(level, 0, 0, 0, 1, 1)) return true;
        if (row + col == SIZE - 1 && checkLine(level, 0, SIZE - 1, 0, 1, -1)) return true;
        // Check vertical diagonals
        if (level == row && checkLine(0, 0, col, 1, 1, 0)) return true;
        if (level + row == SIZE - 1 && checkLine(0, SIZE - 1, col, 1, -1, 0)) return true;
        if (level == col && checkLine(0, row, 0, 1, 0, 1)) return true;
        if (level + col == SIZE - 1 && checkLine(0, row, SIZE - 1, 1, 0, -1)) return true;
        // Check space diagonals
        if (level == row && row == col && checkLine(0, 0, 0, 1, 1, 1)) return true;
        if (level + row == SIZE - 1 && row == col && checkLine(SIZE - 1, 0, 0, -1, 1, 1)) return true;
        if (level == row && row + col == SIZE - 1 && checkLine(0, 0, SIZE - 1, 1, 1, -1)) return true;
        if (level + row == SIZE - 1 && row + col == SIZE - 1 && checkLine(SIZE - 1, 0, SIZE - 1, -1, 1, -1)) return true;
        return false;
    }

    // Check a line for a win
    private boolean checkLine(int startLevel, int startRow, int startCol, int dLevel, int dRow, int dCol) {
        Cell first = board[startLevel][startRow][startCol];
        if (first == Cell.EMPTY) return false;
        for (int i = 1; i < SIZE; i++) {
            if (board[startLevel + i * dLevel][startRow + i * dRow][startCol + i * dCol] != first) {
                return false;
            }
        }
        return true;
    }

    // Check if the board is full
    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    if (board[i][j][k] == Cell.EMPTY) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Main method to run the game
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToe3DD game = new TicTacToe3DD();
        boolean gameOver = false;

        // Ask if the player wants to play against the computer
        System.out.println("Do you want to play against the computer? (yes/no): ");
        boolean playAgainstComputer = scanner.nextLine().equalsIgnoreCase("yes");

        // Ask which player goes first
        System.out.println("Who goes first? (X/O): ");
        game.currentPlayer = scanner.nextLine().equalsIgnoreCase("X") ? Cell.X : Cell.O;

        while (!gameOver) {
            game.printBoard();
            if (playAgainstComputer && game.currentPlayer == Cell.O) {
                // Simple computer move (random)
                Random rand = new Random();
                int level, row, col;
                do {
                    level = rand.nextInt(SIZE);
                    row = rand.nextInt(SIZE);
                    col = rand.nextInt(SIZE);
                } while (game.board[level][row][col] != Cell.EMPTY);
                System.out.println("Computer chooses: Level " + level + ", Row " + row + ", Column " + col);
                gameOver = game.makeMove(level, row, col);
            } else {
                try {
                    System.out.println("Player " + game.currentPlayer + ", enter level (0-3): ");
                    int level = scanner.nextInt();
                    System.out.println("Player " + game.currentPlayer + ", enter row (0-3): ");
                    int row = scanner.nextInt();
                    System.out.println("Player " + game.currentPlayer + ", enter column (0-3): ");
                    int col = scanner.nextInt();
                    gameOver = game.makeMove(level, row, col);
                } catch (Exception e) {
                    System.out.println("Invalid input, please enter numbers between 0 and 3.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }
        }
        scanner.close();
    }
}
