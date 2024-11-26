package game;

import java.util.Arrays;

public class Board {
    char[][] board;
    public final int m, n, k;
    public int moveCount;
    public char type;

    public Board(int m, int n, int k, char type) {
        if (m <= 0 || n <= 0 || k <= 0 || k > Math.max(m, n)) {
            throw new IllegalArgumentException("Invalid board dimensions or win condition.");
        }
        this.type = type;
        this.m = m;
        this.n = n;
        this.k = k;
            this.board = new char[m][n];
            for (char[] row : board) {
                Arrays.fill(row, '·');
        }

        this.moveCount = 0;
    }

    public boolean checkEnd() {
        return moveCount == n * m;
    }

    public boolean checkWin(int row, int col, char sign) {
        return countInDirection(sign, row, col, 0, 1) + countInDirection(sign,row, col, 0, -1) >= k - 1 ||
                countInDirection(sign,row, col, 1, 0) + countInDirection(sign,row, col, -1, 0) >= k - 1 ||
                countInDirection(sign,row, col, 1, 1) + countInDirection(sign,row, col, -1, -1) >= k - 1 ||
                countInDirection(sign,row, col, 1, -1) + countInDirection(sign,row, col, -1, 1) >= k - 1;
    }

    private int countInDirection(char sign, int row, int col, int dRow, int dCol) {
        int count = 0;
        int r = row + dRow;
        int c = col + dCol;
        while (r >= 0 && r < m && c >= 0 && c < n && board[r][c] == sign) {
            count++;
            r += dRow;
            c += dCol;
        }
        return count;
    }


}
