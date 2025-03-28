import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class WsppEvenCurrency {
    public static void main(String[] args) {
        class WordMoney implements MyScanner.Suit {
            @Override
            public boolean isSuitableSymbol(char symbol) {
                return (Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION || Character.getType(symbol) == Character.CURRENCY_SYMBOL || symbol == '\'');
            }
        }

        final MyScanner.Suit WORD_MONEY = new WordMoney();
        Map<String, IntList> allWords = new LinkedHashMap<>();
        int currentLine = 0;
        try {
            MyScanner scanner = new MyScanner(new FileInputStream(args[0]), StandardCharsets.UTF_8);
            try {
                while (scanner.hasNextLine()) {
                    int counter = 0;
                    currentLine += 1;
                    while (scanner.hasNextInLine(WORD_MONEY)) {
                        String word = scanner.next(WORD_MONEY).toLowerCase();
                        counter++;
                        IntList cWord = allWords.getOrDefault(word, new IntList(new int[]{0, currentLine, 0}, 3));
                        allWords.put(word, cWord);
                        cWord.set(2, cWord.get(2) + 1);
                        if (cWord.get(1) != currentLine) {
                            cWord.set(1, currentLine);
                            cWord.set(0, 1);
                        } else {
                            cWord.set(0, cWord.getFirst() + 1);
                        }
                        if (cWord.get(0) % 2 == 0) {
                            cWord.add(counter);
                        }
                    }
                    scanner.goToNextLine();
                }

            } catch (IOException e) {
                System.err.println("An error occurred while trying to read the input stream.");
                System.err.println(e.getMessage());

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Forgot to specify the name of the input file!");
        } catch (IOException e) {
            System.err.println("An error occured while trying to close file.");
            System.err.println(e.getMessage());
        }
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]), StandardCharsets.UTF_8
            ));
            try {
                for (Map.Entry<String, IntList> word : allWords.entrySet()) {
                    IntList tList = word.getValue();
                    writer.write(word.getKey() + " ");
                    for (int i = 2; i < tList.size(); i++) {
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
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Forgot to specify the name of the output file!");
        }
    }
}
