import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

public class WordStatInput {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> allWords = new LinkedHashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            String line = "";
            try {
                line = reader.readLine();
                while (line != null) {
                    for (int i = 0; i < line.length(); i++) {
                        StringBuilder word = new StringBuilder();
                        String tWord;
                        while (i < line.length() && (Character.isLetter(line.charAt(i)) || line.charAt(i) == '\'' || Character.getType(line.charAt(i)) == Character.DASH_PUNCTUATION)) {
                            word.append(line.charAt(i));
                            i++;
                        }

                        if (!word.isEmpty()) {
                            tWord = word.toString().toLowerCase();
                            if (allWords.containsKey(tWord)) {
                                allWords.put(tWord, allWords.get(tWord) + 1);
                            } else {
                                allWords.put(tWord, 1);
                            }
                        }
                    }
                    line = reader.readLine();
                }
                for (String word : allWords.keySet()) {
                    writer.write(word + " " + allWords.get(word) + "\n");
                }
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error closing read file");
                }
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error closing created file");
                }
            } catch (IOException e) {
                System.out.println("Error reading file");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}

