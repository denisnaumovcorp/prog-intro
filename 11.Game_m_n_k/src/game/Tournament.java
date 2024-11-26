package game;

import java.util.*;

public class Tournament {
    private final List<Player> players;
    private final int rounds;
    private final Map<String, Integer> scores;
    private final Set<String> playedMatches;

    public Tournament(List<Player> players) {
        this.players = new ArrayList<>(players);
        this.scores = new HashMap<>();
        this.playedMatches = new HashSet<>();
        for (Player player : players) {
            scores.put(player.name, 0);
        }
        this.rounds = (int) Math.ceil(Math.log(players.size()) / Math.log(2));
    }

    public void startTournament(Scanner scanner) {
        System.out.print("Tournament started! Total rounds: ");
        System.out.println(rounds);
        for (int round = 1; round <= rounds; round++) {
            System.out.print("\n--- Round ");
            System.out.print(round);
            System.out.println(" ---");
            List<Pair<Player, Player>> pairings = generatePairings();
            for (Pair<Player, Player> pairing : pairings) {
                Player player1 = pairing.first;
                Player player2 = pairing.second;
                playMatch(player1, player2, scanner);
            }
            displayScores();
        }
        determineWinners();
    }

    private List<Pair<Player, Player>> generatePairings() {
        players.sort((p1, p2) -> scores.get(p2.name) - scores.get(p1.name));
        List<Pair<Player, Player>> pairings = new ArrayList<>();
        Set<Player> paired = new HashSet<>();
        for (int i = 0; i < players.size(); i++) {
            Player player1 = players.get(i);
            if (paired.contains(player1)) continue;
            for (int j = i + 1; j < players.size(); j++) {
                Player player2 = players.get(j);
                if (paired.contains(player2)) continue;
                String matchKey = generateMatchKey(player1, player2);
                if (!playedMatches.contains(matchKey)) {
                    paired.add(player1);
                    paired.add(player2);
                    playedMatches.add(matchKey);
                    pairings.add(new Pair<>(player1, player2));
                    break;
                }
            }
        }

        for (Player player : players) {
            if (!paired.contains(player)) {
                System.out.print("Player ");
                System.out.print(player.name);
                System.out.println(" gets a bye this round.");
                scores.put(player.name, scores.get(player.name) + 1);
                paired.add(player);
            }
        }
        return pairings;
    }


    private void playMatch(Player player1, Player player2, Scanner scanner) {
        System.out.print("Match: ");
        System.out.print(player1.name);
        System.out.print(" vs ");
        System.out.println(player2.name);
        String result =  MNKGame.playGame(player1, player2, scanner);
        if (result.equals("stalemate")) {
            System.out.println("Stalemate!");
            scores.put(player1.name, scores.get(player1.name) + 1);
            scores.put(player2.name, scores.get(player2.name) + 1);
        } else {
            System.out.println("Winner: " + result);
            scores.put(result, scores.get(result) + 2);
        }
    }

    private String generateMatchKey(Player player1, Player player2) {
        return player1.name.compareTo(player2.name) < 0
                ? player1.name + ":" + player2.name
                : player2.name + ":" + player1.name;
    }

    private void displayScores() {
        System.out.println("\nCurrent Standings:");
        players.sort((p1, p2) -> scores.get(p2.name) - scores.get(p1.name));
        for (Player player : players) {
            System.out.print(player.name);
            System.out.print(": ");
            System.out.print(scores.get(player.name));
            System.out.println(" points");
        }
    }

    private void determineWinners() {
        System.out.println("\nTournament Over!");
        int maxScore = Collections.max(scores.values());
        List<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (scores.get(player.name) == maxScore) {
                winners.add(player);
            }
        }

        System.out.println("Winners:");
        for (Player winner : winners) {
            System.out.print(winner.name);
            System.out.print(" with ");
            System.out.print(maxScore);
            System.out.println(" points!");
        }
    }

    private static class Pair<F, S> {
        F first;
        S second;
        Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }
}