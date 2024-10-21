import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class WordStatWordsMiddle {


    public static void main(String[] args) {
        class Word implements MyScanner.Suit {
            @Override
            public boolean isSuitableSymbol(char symbol) {
                return (Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION || symbol == '\'');
            }
        }

        final MyScanner.Suit WORD = new Word();
        Map<String, Integer> allWords = new TreeMap<>(Collections.reverseOrder());
        try {
            MyScanner scanner = new MyScanner(new FileInputStream(args[0]), StandardCharsets.UTF_8);
            try {
                while (scanner.hasNext(WORD)) {
                    String word = scanner.next(WORD).toLowerCase();
                    if (word.length() >= 7) {
                        word = word.substring(3, word.length() - 3);
                    }
                    allWords.merge(word, 1, Integer::sum);
                }
                scanner.close();
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(args[1]), StandardCharsets.UTF_8
                    ));
                    for (Map.Entry<String, Integer> word : allWords.entrySet()) {
                        writer.write(word.getKey() + " " + word.getValue());
                        writer.newLine();
                    }
                    writer.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File for writing cannot be created");
                }
            } catch (IOException e) {
                System.out.println("Error reading input file");
                System.err.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
