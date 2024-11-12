package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Md2Html {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: Md2Html <input file> <output file>");
            return;
        }
        try {
        Scanner sc = new Scanner(new FileInputStream(args[0]), StandardCharsets.UTF_8);
        List<String> lines = new ArrayList<>();
        StringBuilder text = new StringBuilder();
        int lineSeparatorLength = System.lineSeparator().length();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.isEmpty()) {
                text.append(line).append(System.lineSeparator());
            }
            if (line.isEmpty() || !sc.hasNextLine()) {
                if (!text.isEmpty()) {
                    text.setLength(text.length() - lineSeparatorLength);
                    lines.add(text.toString());
                    text.setLength(0);
                }
            }
        }
        sc.close();
        String htmlContent = convertToHtml(lines);
        writeToFile(args[1], htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readLines(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static void writeToFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }

    private static String convertToHtml(List<String> lines) {
        StringBuilder html = new StringBuilder();

        for (String line : lines) {
            if (!line.isEmpty()) {
                if (isHeader(line)) {
                    html.append(wrapWithHeader(line));
                } else {
                    html.append(wrapWithParagraph(line));
                }
            }
        }

        return html.toString();
    }

    private static boolean isHeader(String line) {
        if (line.startsWith("#")) {
            int level = 0;
            while (level < line.length() && line.charAt(level) == '#') {
                level++;
            }
            return Character.isWhitespace(line.charAt(level)) && level <= 6;
        } else {
            return false;
        }
    }

    private static String wrapWithHeader(String line) {
        int level = 0;
        while (level < line.length() && line.charAt(level) == '#') {
            level++;
        }
        String content = line.substring(level + (line.charAt(level) == ' ' ? 1 : 0));
        return "<h" + level + ">" + processInline(content) + "</h" + level + ">" + System.lineSeparator();
    }

    private static String wrapWithParagraph(String content) {
        return "<p>" + processInline(content) + "</p>" + System.lineSeparator();
    }

    private static String processInline(String line) {
        StringBuilder result = new StringBuilder();
        boolean em = false, strong = false, strike = false, code = false, ins = false, del = false;
        for (int i = 0; i < line.length(); i++) {

            char ch = line.charAt(i);

            if (ch == '\\') {
                if (i + 1 < line.length() && (line.charAt(i + 1) == '*' || line.charAt(i + 1) == '_' || line.charAt(i + 1) == '<' | line.charAt(i + 1) == '}')) {
                    if (line.charAt(i + 1) == '<') {
                        result.append("&lt;");
                    } else {
                        result.append(line.charAt(i + 1));
                    }
                    i++;
                    continue;
                }
            }
            if (ch == '*' || ch == '_') {
                boolean doubleChar = (i + 1 < line.length() && line.charAt(i + 1) == ch);
                if (doubleChar) {
                    if (!strong) {
                        result.append("<strong>");
                        strong = true;
                    } else {
                        result.append("</strong>");
                        strong = false;
                    }
                    i++;
                } else {
                    if (!em && i + 1 < line.length() && Character.isWhitespace(line.charAt(i + 1))) {
                        result.append(ch);
                    } else {
                        if (!em) {
                            result.append("<em>");
                            em = true;
                        } else {
                            result.append("</em>");
                            em = false;
                        }
                    }
                }
            } else if (ch == '-') {
                boolean doubleChar = (i + 1 < line.length() && line.charAt(i + 1) == '-');
                if (doubleChar) {
                    if (!strike) {
                        result.append("<s>");
                        strike = true;
                    } else {
                        result.append("</s>");
                        strike = false;
                    }
                    i++;
                } else {
                    result.append(ch);
                }
            } else if (ch == '`') {
                if (!code) {
                    result.append("<code>");
                    code = true;
                } else {
                    result.append("</code>");
                    code = false;
                }
            } else if (ch =='<'){
                if (i + 1 < line.length() && line.charAt(i + 1) == ch) {
                    result.append("<ins>");
                    ins = true;
                    i++;
                } else {
                    result.append("&lt;");
                }
            } else if (ch =='>') {
                if (i + 1 < line.length() && line.charAt(i + 1) == ch && ins) {
                    result.append("</ins>");
                    ins = false;
                    i++;
                } else {
                    result.append("&gt;");
                }
            } else if (ch =='}') {
                if (i + 1 < line.length() && line.charAt(i + 1) == ch) {
                    result.append("<del>");
                    del = true;
                    i++;
                } else {
                    result.append(ch);
                }
            } else if (ch =='{') {
                if (i + 1 < line.length() && line.charAt(i + 1) == ch && del) {
                    result.append("</del>");
                    del = false;
                    i++;
                } else {
                    result.append(ch);
                }
            } else {
                if (ch == '&') {
                    result.append("&amp;");
                } else {
                    result.append(ch);
                }
            }
        }

        if (em) result.append("</em>");
        if (strong) result.append("</strong>");
        if (strike) result.append("</s>");
        if (code) result.append("</code>");

        return result.toString();
    }
}
