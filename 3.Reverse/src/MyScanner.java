import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class MyScanner {
    private final int DEFAULT_BUFFER_SIZE = 1024;
    private final Reader reader;
    private final Suit DEFAULT = new Default();
    private final Suit DEFAULT_INT = new DefaultInt();
    private char[] buffer;
    private boolean eof;
    private boolean closed;
    private int currentIndex;
    private int startIndex;
    private int bufferSize;

    private void ensureOpen() throws IllegalStateException {
        if (closed) {
            throw new IllegalStateException("You can't use scanner, it's already closed!");
        }
    }

    private boolean isLineSeparator(int index) throws IOException {
        for (int i = 0; i < System.lineSeparator().length(); i++) {
            if (index == bufferSize && !eof) {
                getInput();
            } else if (index == bufferSize || System.lineSeparator().charAt(i) != buffer[index]) {
                return false;
            }
            index++;
        }
        return true;
    }

    public interface Suit {
        boolean isSuitableSymbol(char symbol);
    }

    private static final class Default implements Suit {
        @Override
        public boolean isSuitableSymbol(char symbol) {
            return !(Character.isWhitespace(symbol) || symbol == 0);
        }
    }

    private static final class DefaultInt implements Suit {
        @Override
        public boolean isSuitableSymbol(char symbol) {
            return Character.isDigit(symbol) || symbol == '-';
        }
    }

    public MyScanner(String string) {
        reader = new StringReader(string);
        eof = false;
        closed = false;
    }

    public MyScanner(InputStream stream) {
        reader = new InputStreamReader(stream);
        eof = false;
        closed = false;
    }

    public MyScanner(FileInputStream stream, Charset encoding) {
        reader = new InputStreamReader(stream, encoding);
        eof = false;
        closed = false;
    }

    private void getInput() throws IOException {
        int symbol, index = bufferSize;
        if (buffer == null || currentIndex == bufferSize) {
            buffer = new char[DEFAULT_BUFFER_SIZE];
            bufferSize = DEFAULT_BUFFER_SIZE;
            currentIndex = 0;
            startIndex = 0;
        } else {
            buffer = Arrays.copyOf(buffer, bufferSize * 2);
            bufferSize = bufferSize * 2;
        }
        while(index < bufferSize)  {
                symbol = reader.read();
            if (symbol == -1) {
                eof = true;
                break;
            }
            buffer[index] = (char) symbol;
            index++;
        }
        bufferSize = index;
        buffer = Arrays.copyOf(buffer, bufferSize);
    }

    private boolean hasNextSymbol() throws IOException {
        if (currentIndex == bufferSize && !eof) {
            getInput();
        }
        return currentIndex != bufferSize;
    }

    public boolean hasNextLine() throws IOException {
        ensureOpen();
        return hasNextSymbol();
    }

    public boolean hasNext(Suit suit) throws IOException {
        ensureOpen();
        int index = currentIndex;
        while (true) {
            if (index == bufferSize && !eof) {
                getInput();
            } else if (index == bufferSize) {
                return false;
            } else if (suit.isSuitableSymbol(buffer[index])) {
                startIndex = index;
                return true;
            } else {
                index++;
            }
        }
    }

    public boolean hasNextInLine(Suit suit) throws IOException {
        ensureOpen();
        int index = currentIndex;
        while (true) {
            if (index == bufferSize && !eof) {
                getInput();
            } else if (index == bufferSize || (buffer[index] == System.lineSeparator().charAt(0) && isLineSeparator(index))) {
                return false;
            } else if (suit.isSuitableSymbol(buffer[index])) {
                startIndex = index;
                return true;
            } else {
                index++;
            }
        }
    }

    public boolean hasNextInLine() throws IOException {
        return hasNextInLine(DEFAULT);
    }

    public boolean hasNext() throws IOException {
        return hasNext(DEFAULT);
    }

    public boolean hasNextInt() throws IOException {
        int index = currentIndex;
        if (hasNext(DEFAULT_INT)) {
            try {
                Integer.parseInt(next(DEFAULT_INT))
            catch (NumberFormatException e) {
                currentIndex = index;
                return false;
            }
            currentIndex = index;
            startIndex = index;
            return true;
        }
    }

    public String next(Suit suit) throws IOException, NoSuchElementException {
        ensureOpen();
        int index = startIndex;
        boolean wordExist = false;
        StringBuilder result = new StringBuilder();
        while (true) {
            if (index != bufferSize && suit.isSuitableSymbol(buffer[index])) {
                wordExist = true;
                result.append(buffer[index]);
                index++;
            } else {
                if (index == bufferSize && !eof) {
                    getInput();
                } else {
                    if (wordExist) {
                        startIndex = index;
                        currentIndex = index;
                        return result.toString();
                    } else if (index == bufferSize){
                        throw new NoSuchElementException();
                    } else {
                        index++;
                    }
                }
            }
        }
    }

    public String next() throws IOException {
        return next(DEFAULT);
    }

    public int nextInt() throws IOException, NumberFormatException {
        return Integer.parseInt(next(DEFAULT_INT));
    }
    
    public int nextInt(int radix) throws IOException {
        if (radix != 10) {
            return Integer.parseUnsignedInt(next(DEFAULT_INT), radix);
        } else {
            return Integer.parseInt(next(DEFAULT_INT));
        }
    }

    public String nextLine() throws IOException, NoSuchElementException {
        ensureOpen();
        int index = currentIndex;
        boolean stringExist = false;
        StringBuilder result = new StringBuilder();
        while (true) {
            if (index == bufferSize && !eof) {
                getInput();
            } else if (index == bufferSize) {
                if (stringExist) {
                    startIndex = index;
                    currentIndex = index;
                    return result.toString();
                } else {
                    throw new NoSuchElementException();
                }
            } else if (buffer[index] == System.lineSeparator().charAt(0) && isLineSeparator(index)) {
                index += System.lineSeparator().length();
                currentIndex = index;
                return result.toString();
            } else {
                stringExist = true;
                result.append(buffer[index]);
                index++;
            }
        }
    }

    public void goToNextLine() throws IOException {
        ensureOpen();
        int index = currentIndex;
        while (true) {
            if (index == bufferSize && !eof) {
                getInput();
            } else if (index == bufferSize) {
                currentIndex = index;
                return;
            } else if (buffer[index] == System.lineSeparator().charAt(0) && isLineSeparator(index))  {
                index += System.lineSeparator().length();
                currentIndex = index;
                return;
            } else {
                index++;
            }
        }
    }

    public void close() throws IOException {
        if (!closed) {
            reader.close();
            closed = true;
        }
    }
}
