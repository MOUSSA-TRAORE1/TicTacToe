import java.util.Scanner;

public class TicTacToe3D {
    private enum Cell { X, O, EMPTY }
    private Cell[][][] board = new Cell[4][4][4];
    private Cell currentPlayer;

    public TicTacToe3D() {
        currentPlayer = Cell.X;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    board[i][j][k] = Cell.EMPTY;
                }
            }
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameWon = false;

        while (!isDraw() && !gameWon) {
            printBoard();
            System.out.println("Player " + currentPlayer + "'s turn.");
            System.out.print("Enter level, row, and column (0 to 3): ");
            int level = scanner.nextInt();
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (level < 0 || level > 3 || row < 0 || row > 3 || col < 0 || col > 3 || board[level][row][col] != Cell.EMPTY) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            board[level][row][col] = currentPlayer;
            gameWon = checkWinner();

            if (gameWon) {
                printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
            } else {
                currentPlayer = (currentPlayer == Cell.X) ? Cell.O : Cell.X;
            }
        }

        if (!gameWon) {
            printBoard();
            System.out.println("The game is a draw!");
        }

        scanner.close();
    }

    private boolean checkWinner() {
        for (int level = 0; level < 4; level++) {
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    if (checkLine(level, row, col, 1, 0, 0) || // Horizontal
                            checkLine(level, row, col, 0, 1, 0) || // Vertical
                            checkLine(level, row, col, 0, 0, 1) || // Depth
                            checkLine(level, row, col, 1, 1, 0) || // Diagonal on same level
                            checkLine(level, row, col, 1, 0, 1) || // Diagonal across levels
                            checkLine(level, row, col, 0, 1, 1) || // Vertical diagonal across levels
                            checkLine(level, row, col, 1, 1, 1) || // 3D diagonal
                            checkLine(level, row, col, 1, -1, 1))   // Opposite 3D diagonal
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkLine(int level, int row, int col, int dL, int dR, int dC) {
        try {
            Cell start = board[level][row][col];
            if (start == Cell.EMPTY) return false;

            for (int i = 1; i < 4; i++) {
                if (board[level + i * dL][row + i * dR][col + i * dC] != start)
                    return false;
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean isDraw() {
        for (int level = 0; level < 4; level++) {
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    if (board[level][row][col] == Cell.EMPTY) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void printBoard() {
        for (int level = 0; level < 4; level++) {
            System.out.println("Level " + level + ":");
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    if (board[level][row][col] == Cell.EMPTY) System.out.print("-");
                    else System.out.print(board[level][row][col]);
                    System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TicTacToe3D game = new TicTacToe3D();
        game.play();
    }
}

