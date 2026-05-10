import java.util.Scanner;

public class TicTacToe2D {
    private enum Cell { X, O, EMPTY }
    private Cell[][] board = new Cell[3][3];
    private Cell currentPlayer;

    public TicTacToe2D() {
        currentPlayer = Cell.X;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameWon = false;

        while (!isDraw() && !gameWon) {
            printBoard();
            System.out.println("Player " + currentPlayer + "'s turn.");
            System.out.print("Enter row and column (0, 1, or 2): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != Cell.EMPTY) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            board[row][col] = currentPlayer;
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
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer)
                return true;
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
                return true;
        }

        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer)
            return true;
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
            return true;

        return false;
    }

    private boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Cell.EMPTY) System.out.print("-");
                else System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TicTacToe2D game = new TicTacToe2D();
        game.play();
    }
}
