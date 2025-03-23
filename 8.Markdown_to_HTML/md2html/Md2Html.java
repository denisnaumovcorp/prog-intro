package md2html;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

public class Md2Html {
    private static final Map<String, String> tagMap = Map.of(
            "*", "em",
            "_", "em",
            "**", "strong",
            "__", "strong",
            "--", "s",
            "`", "code",
            "<<", "ins",
            ">>", "ins",
            "}}", "del",
            "{{", "del");

    private static final Map<String, String> matches = Map.of(
            "*", "*",
            "_", "_",
            "**", "**",
            "__", "__",
            "--", "--",
            "`", "`",
            "<<", ">>",
            "}}", "{{");

    private static final Map<String, String> specialCharacters = Map.of(
            "<", "&lt;",
            ">", "&gt;",
            "&", "&amp;");

    private static class Tag {
        private final String name;
        private final int start;

        public Tag(final String name, final int start) {
            this.name = name;
            this.start = start;
        }

        public String getName() {
            return name;
        }

        public int getStart() {
            return start;
        }
    }

    public static void main(final String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java Md2Html <INPUT> <OUTPUT>");
            return;
        }

        ArrayList<String> lines = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (final FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        } catch (final IOException e) {
            System.err.println("IOError on input: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))) {
            final StringBuilder paragraph = new StringBuilder();
            for (String line : lines) {
                if (line.isEmpty()) {
                    if (!paragraph.isEmpty()) {
                        writeParagraph(writer, paragraph);
                        paragraph.setLength(0);
                    }
                } else {
                    paragraph.append(line).append(System.lineSeparator());
                }
            }
            if (!paragraph.isEmpty()) {
                writeParagraph(writer, paragraph);
            }
        } catch (final FileNotFoundException e) {
            System.err.println("Output file not found: " + e.getMessage());
        } catch (final IOException e) {
            System.err.println("IOError on output: " + e.getMessage());
        }
    }

    private static void writeParagraph(final BufferedWriter writer, final StringBuilder paragraph) throws IOException {
        if (paragraph.isEmpty())
            return;

        int headerRank = 0;
        while (headerRank < paragraph.length() && paragraph.charAt(headerRank) == '#') {
            headerRank++;
        }

        String paragraphTag;

        if (headerRank > 0 && headerRank < paragraph.length() &&
                Character.isWhitespace(paragraph.charAt(headerRank))) {
            paragraphTag = "h" + headerRank;
            headerRank++;
        } else {
            headerRank = 0;
            paragraphTag = "p";
        }

        writer.write("<" + paragraphTag + ">");

        writer.write(getHtml(paragraph, headerRank, getTagList(paragraph, headerRank)));

        writer.write("</" + paragraphTag + ">" + System.lineSeparator());
    }

    static private int[] getTagList(final StringBuilder paragraph, final int begin) {
        final int[] tagList = new int[paragraph.length()];

        final Stack<Tag> tagStack = new Stack<Tag>();
        for (int i = begin; i < paragraph.length(); i++) {
            if (paragraph.charAt(i) == '\\' && i + 1 < paragraph.length()) {
                i++;
                continue;
            }
            String tag = i + 1 < paragraph.length() ? paragraph.substring(i, i + 2) : "";
            if (!tagMap.containsKey(tag)) {
                tag = paragraph.substring(i, i + 1);
                if (!tagMap.containsKey(tag)) {
                    tag = "";
                }
            }
            if (tag.isEmpty()) {
                continue;
            }
            if (tagStack.isEmpty() || !matches.get(tagStack.peek().getName()).equals(tag)) {
                tagStack.push(new Tag(tag, i));
            } else {
                tagList[tagStack.pop().getStart()] = tag.length();
                tagList[i] = -tag.length();
            }
            i++;
        }
        return tagList;
    }

    static private String getHtml(final StringBuilder paragraph, final int begin, final int[] tagList) {
        final StringBuilder html = new StringBuilder();
        int i = begin;
        while (i < paragraph.length()) {
            if (paragraph.charAt(i) == '\\' && i + 1 < paragraph.length()) {
                i++;
            } else if (tagList[i] != 0) {
                html.append("<")
                        .append(tagList[i] > 0 ? "" : "/")
                        .append(tagMap.get(paragraph.substring(i, i + Math.abs(tagList[i]))))
                        .append(">");
                i += Math.abs(tagList[i]);
            } else {
                final String character = paragraph.substring(i, ++i);
                // :NOTE: getOrDefault
                html.append(specialCharacters.getOrDefault(character, character));
            }
        }
        return html.substring(0, html.length() - System.lineSeparator().length());
    }
}
