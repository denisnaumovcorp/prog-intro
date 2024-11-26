package game;

public class Player {
    public char sign;
    public String name;

    Player(char sign, String name) {
        this.sign = sign;
        this.name = name;
    }

    public boolean makeMove(int row, int col, Board board) {
        if (row < 0 || row >= board.m|| col < 0 || col >= board.n || board.board[row][col] != '·') {
            return false;
        }
        board.board[row][col] = sign;
        board.moveCount++;
        return true;
    }


}
