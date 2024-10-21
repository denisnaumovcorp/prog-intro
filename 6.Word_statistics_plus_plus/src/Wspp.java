import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Wspp {
    public static void main(String[] args) {
        class Word implements MyScanner.Suit {
            @Override
            public boolean isSuitableSymbol(char symbol) {
                return (Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION || symbol == '\'');
            }
        }

        final MyScanner.Suit WORD = new Word();
        Map<String, ArrayList<Integer>> allWords = new LinkedHashMap<>();
        int counter = 0;
        try {
            MyScanner scanner = new MyScanner(new FileInputStream(args[0]), StandardCharsets.UTF_8);
            try {
                while (scanner.hasNext(WORD)) {
                    String word = scanner.next(WORD).toLowerCase();
                    counter++;
                    if (!allWords.containsKey(word)) {
                        ArrayList<Integer> tList = new ArrayList<>();
                        tList.add(1);
                        tList.add(counter);
                        allWords.put(word, tList);
                    } else {
                        allWords.get(word).set(0, allWords.get(word).getFirst() + 1);
                        allWords.get(word).add(counter);
                    }
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
                    for (int i = 0; i < tList.size(); i++) {
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
