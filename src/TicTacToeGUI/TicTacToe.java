import java.util.Random;
import java.util.Scanner;

// Enum to represent X, O, and EMPTY states
enum Cell1 {
    X, O, EMPTY
}

// TicTacToe class to handle game logic
public class TicTacToe {
    private Cell[][] board;
    private Cell currentPlayer;
    private static final int SIZE = 3;

    // Constructor initializes the game board
    public TicTacToe() {
        board = new Cell[SIZE][SIZE];
        initializeBoard();
    }

    // Initialize the board to EMPTY
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
    }

    // Print the current state of the board
    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] == Cell.EMPTY ? " " : board[i][j]);
                if (j < SIZE - 1) System.out.print(" | ");
            }
            System.out.println();
            if (i < SIZE - 1) System.out.println("---------");
        }
    }

    // Make a move on the board
    public boolean makeMove(int row, int col) {
        if (board[row][col] == Cell.EMPTY) {
            board[row][col] = currentPlayer;
            if (checkWinner(row, col)) {
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
    private boolean checkWinner(int row, int col) {
        // Check row
        if (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer) {
            return true;
        }
        // Check column
        if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer) {
            return true;
        }
        // Check diagonal
        if (row == col && board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        // Check anti-diagonal
        if (row + col == 2 && board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }
        return false;
    }

    // Check if the board is full
    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // Main method to run the game
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToe game = new TicTacToe();
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
                int row, col;
                do {
                    row = rand.nextInt(SIZE);
                    col = rand.nextInt(SIZE);
                } while (game.board[row][col] != Cell.EMPTY);
                System.out.println("Computer chooses: " + row + ", " + col);
                gameOver = game.makeMove(row, col);
            } else {
                try {
                    System.out.println("Player " + game.currentPlayer + ", enter row (0-2): ");
                    int row = scanner.nextInt();
                    System.out.println("Player " + game.currentPlayer + ", enter column (0-2): ");
                    int col = scanner.nextInt();
                    gameOver = game.makeMove(row, col);
                } catch (Exception e) {
                    System.out.println("Invalid input, please enter numbers between 0 and 2.");
                    scanner.next(); // Clear the invalid input
                }
            }
        }
        scanner.close();
    }
}
