import java.io.IOException;
import java.util.Arrays;

import static java.lang.Math.abs;

public class ReverseMaxAbsModOctDec {
    public static void main(String[] args) {
        MyScanner scanner = new MyScanner(System.in);
        int[][] numbersArr = new int[1][];
        int counter = 0, counterI = 0, max = 0, mod = 1_000_000_007;
        try {
            while (scanner.hasNextLine()) {
                if (counter == numbersArr.length) {
                    numbersArr = Arrays.copyOf(numbersArr, numbersArr.length * 2);
                }
                if (scanner.hasNextInLine()) {
                    while (scanner.hasNextInLine()) {
                        int number = 0;
                        String tNumber = scanner.next();
                        if (tNumber.charAt(tNumber.length() - 1) == 'O'
                                || tNumber.charAt(tNumber.length() - 1) == 'o') {
                            number = Integer.parseUnsignedInt(tNumber.substring(0, tNumber.length() - 1), 8);
                        } else {
                            number = Integer.parseInt(tNumber);
                        }
                        if (numbersArr[counter] == null) {
                            numbersArr[counter] = Arrays.copyOf(new int[1], 1);
                        } else if (counterI == numbersArr[counter].length) {
                            numbersArr[counter] = Arrays.copyOf(numbersArr[counter], numbersArr[counter].length * 2);
                        }
                        numbersArr[counter][counterI] = number;
                        counterI++;
                    }
                    numbersArr[counter] = Arrays.copyOf(numbersArr[counter], counterI);
                    counterI = 0;
                    if (numbersArr[counter].length > max) {
                        max = numbersArr[counter].length;
                    }
                } else {
                    numbersArr[counter] = Arrays.copyOf(new int[0], 0);
                }
                scanner.goToNextLine();
                counter++;
            }
        } catch (IOException e) {
            System.err.println("An error occurred while trying to read the input stream.");
            System.err.println(e.getMessage());
        }
        try {
            scanner.close();
        } catch (IOException e) {
            System.err.println("An error occured while trying to close file.");
            System.err.println(e.getMessage());
        }
        numbersArr = Arrays.copyOf(numbersArr, counter);

        int[] xArr = new int[numbersArr.length];
        int[] yArr = new int[max];

        for (int i = 0; i < numbersArr.length; i++) {
            int xMax = 0;
            for (int j = 0; j < max; j++) {
                if (j >= numbersArr[i].length) {
                    break;
                } else {
                    int x = numbersArr[i][j];
                    int y = numbersArr[i][j];
                    if (abs(x) % mod > abs(xMax) % mod) {
                        xArr[i] = x;
                        xMax = x;
                    }
                    if (abs(y) % mod > abs(yArr[j]) % mod) {
                        yArr[j] = y;
                    }
                }
            }
        }
        for (int i = 0; i < numbersArr.length; i++) {
            for (int j = 0; j < numbersArr[i].length; j++) {
                if ((abs(xArr[i]) % mod) > (abs(yArr[j]) % mod)) {
                    System.out.print(Integer.toOctalString(xArr[i]));
                } else {
                    System.out.print(Integer.toOctalString(yArr[j]));
                }
                System.out.print("o ");
            }
            System.out.println();
        }
    }
}

