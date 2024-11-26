package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.abs;

public class MNKGame {

    private static void printBoard(Board board) {
        if (board.type == 'r')
        {
            int row, col;
            for (int i = 0; i < 2 * board.m + 1; i++) {
                int startLen = Integer.toString(board.m).length();
                int left = abs(board.m - i) + startLen;
                int right = 2 * board.m - 2 - abs(board.m - i) + startLen;
                col = Math.max(0, board.m - i);
                row = Math.max(0, -board.m + i);
                for (int j = 0; j < 2 * board.m + 1; j++) {
                    String number = Integer.toString(abs(board.m - i));
                    if (j == left - Integer.toString(board.m).length()) {
                        for (char c : number.toCharArray()) {
                            System.out.print(c);
                            j++;
                        }
                        while(j != left)
                        {
                            System.out.print(" ");
                            j++;
                        }
                        System.out.print("   ");
                    }
                    if (j >= left && j <= right && i > 0 && i < 2 * board.m) {
                        System.out.print(' ');
                        System.out.print(board.board[col][row]);
                        System.out.print(' ');
                        col += 1;
                        row += 1;
                        j++;
                    }
                    System.out.print("   ");
                }
                System.out.println();
            }
        } else {
            for (char[] row : board.board) {
                for (char cell : row) {
                    System.out.print(cell + " ");
                }
                System.out.println();
            }
        }
    }

     private static Player swapPlayers(Player currentPlayer, Player firstPlayer, Player secondPlayer) {
        if (currentPlayer.equals(firstPlayer)) {
            currentPlayer = secondPlayer;
        } else {
            currentPlayer = firstPlayer;
        }
        return currentPlayer;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose mode: (1) Regular Game, (2) Tournament");
        int mode = scanner.nextInt();
        if (mode == 1) {
            System.out.println("First player, enter your name, please: ");
            Player firstPlayer = new Player('X', scanner.next());
            System.out.println("Second player, enter your name, please: ");
            Player secondPlayer = new Player('O', scanner.next());
            playGame(firstPlayer, secondPlayer, scanner);
        } else if (mode == 2) {
            startTournament(scanner);
        } else {
            System.out.println("Invalid choice.");
        }
    }
    public static String playGame(Player firstPlayer, Player secondPlayer, Scanner scanner) {
        System.out.println("Enter board type(rhombus or square):");
        char type = scanner.next().charAt(0);
        int m = 0, n = 0, k = 0;
        if (type == 'r') {
            System.out.println("Enter board length and win length:");
            m = scanner.nextInt();
            n = m;
            k = scanner.nextInt();
        } else if (type == 's') {
            System.out.println("Enter board length, width, win length");
            m = scanner.nextInt();
            n = scanner.nextInt();
            k = scanner.nextInt();
        }
        Player currentPlayer = firstPlayer;
        Board board;
        board = new Board(m, n, k, type);

        System.out.print("Game started! Players: ");
        System.out.print(firstPlayer.sign);
        System.out.print(" and ");
        System.out.println(secondPlayer.sign);
        printBoard(board);

        boolean drawOfferedThisTurn = false;

        while (true) {
            System.out.println("Player " + currentPlayer.name + ", enter your move (row col) or a command:");
            System.out.println("Commands: 'stalemate' (offer stalemate), 'surrender' (give up)");
            String input = scanner.next();
            if (input.equals("surrender"))
            {
                System.out.println("Player " + currentPlayer.name + " surrenders!");
                currentPlayer = swapPlayers(currentPlayer, firstPlayer, secondPlayer);
                System.out.println("Player " + currentPlayer.name + " wins!");
                return currentPlayer.name;
            }
            if (input.equals("stalemate")) {
                if (drawOfferedThisTurn) {
                    System.out.println("You already offered a stalemate this turn.");
                    continue;
                }

                Player otherPlayer = swapPlayers(currentPlayer, firstPlayer, secondPlayer);
                System.out.println("Player " + otherPlayer.name + ", do you accept the stalemate? (yes/no)");
                String response = scanner.next();

                if (response.equalsIgnoreCase("yes")) {
                    System.out.println("The game ends in a stalemate!");
                    return "stalemate";
                } else {
                    System.out.println("Stalemate offer declined. Player " + currentPlayer.name + ", make your move.");
                    drawOfferedThisTurn = true;
                    continue;
                }
            }
            int row, col;
            row = Integer.parseInt(input);
            col = scanner.nextInt();
            while (row - 1 < 0 || row - 1 >= board.m|| col - 1 < 0 || col - 1 >= board.n || board.board[row - 1][col - 1] != '·') {
                System.out.println("Player " + currentPlayer.name + ", you make a mistake, enter your move again (row col):");
                row = scanner.nextInt();
                col = scanner.nextInt();
            }
            if (currentPlayer.makeMove(row - 1, col - 1, board)) {
                printBoard(board);
                if (board.checkWin(row - 1, col - 1, currentPlayer.sign)) {
                    System.out.println("Player " + currentPlayer.name + " win!");
                    return currentPlayer.name;
                } else if (board.checkEnd()) {
                    System.out.println("Stalemate!");
                    return "stalemate";
                }
                currentPlayer = swapPlayers(currentPlayer, firstPlayer, secondPlayer);
            } else {
                currentPlayer = swapPlayers(currentPlayer, firstPlayer, secondPlayer);
                System.out.println("Player " + currentPlayer.name + " win!");
                return currentPlayer.name;
            }
        }
    }

    private static void startTournament(Scanner scanner) {
        System.out.println("Enter the number of players:");
        int playerCount = scanner.nextInt();
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            System.out.println("Enter name for Player " + i + ":");
            players.add(new Player((char) ('A' + i - 1), scanner.next()));
        }
        Tournament tournament = new Tournament(players);
        tournament.startTournament(scanner);
    }
}
