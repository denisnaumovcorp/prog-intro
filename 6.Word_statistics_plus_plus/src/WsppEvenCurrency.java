import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppEvenCurrency {
    public static void main(String[] args) {
        class WordMoney implements MyScanner.Suit {
            @Override
            public boolean isSuitableSymbol(char symbol) {
                return (Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION || Character.getType(symbol) == Character.CURRENCY_SYMBOL || symbol == '\'');
            }
        }

        final MyScanner.Suit WORD_MONEY = new WordMoney();
        Map<String, ArrayList<Integer>> allWords = new LinkedHashMap<>();
        try {
            MyScanner scanner = new MyScanner(new FileInputStream(args[0]), StandardCharsets.UTF_8);
            try {
                while (scanner.hasNextLine()) {
                    int counter = 0;
                    while (scanner.hasNextInLine(WORD_MONEY)) {
                        String word = scanner.next(WORD_MONEY).toLowerCase();
                        counter++;
                        if (!allWords.containsKey(word)) {
                            ArrayList<Integer> tList = new ArrayList<>();
                            tList.add(1);
                            tList.add(1);
                            allWords.put(word, tList);
                        } else {
                            ArrayList<Integer> cWord = allWords.get(word);
                            cWord.set(1, cWord.get(1) + 1);
                            cWord.set(0, cWord.getFirst() + 1);
                            if (cWord.get(0) % 2 == 0) {
                                cWord.add(counter);
                            }
                        }
                    }
                    for (Map.Entry<String, ArrayList<Integer>> word : allWords.entrySet()) {
                        word.getValue().set(0, 0);
                    }
                    scanner.goToNextLine();
                }
                scanner.close();
            } catch (IOException e) {
                System.err.println("File not found!");
            }
        } catch (FileNotFoundException e) {
            System.err.println("An error occurred while trying to read the input stream.");
            System.err.println(e.getMessage());
        }
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]), StandardCharsets.UTF_8
            ));
            try {
                for (Map.Entry<String, ArrayList<Integer>> word : allWords.entrySet()) {
                    ArrayList<Integer> tList = word.getValue();
                    writer.write(word.getKey() + " ");
                    for (int i = 1; i < tList.size(); i++) {
                        if (i < tList.size() - 1) {
                            writer.write(tList.get(i) + " ");
                        } else {
                            writer.write(Integer.toString(tList.get(i)));
                        }
                    }
                    writer.newLine();
                }
                writer.close();
            } catch (IOException e) {
                System.err.println("An error occurred while trying to write the output file.");
                System.err.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File for writing cannot be created");
        }
    }
}
