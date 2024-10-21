import java.io.IOException;
import java.util.Arrays;
import static java.lang.Math.abs;

public class ReverseSumAbsMod {
    public static void main(String[] args) {
        MyScanner scanner = new MyScanner(System.in);
        int[][] numbersArr = new int[1][];
        int counter = 0, counterI = 0, max = 0;
        try {
            while(scanner.hasNextLine()) {
                if (counter == numbersArr.length) {
                    numbersArr = Arrays.copyOf(numbersArr, numbersArr.length * 2);
                }
                if (scanner.hasNextInLine()) {
                    while (scanner.hasNextInLine()) {
                        int number = scanner.nextInt();
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
            scanner.close();
        } catch (IOException e) {
            System.err.println("An error occurred while trying to read the input stream.");
            System.err.println(e.getMessage());
        }
        numbersArr = Arrays.copyOf(numbersArr, counter);

        int[] xArr = new int[numbersArr.length];
        int[] yArr = new int[max];
        int mod = 1_000_000_007;

        for (int i = 0; i < numbersArr.length; i++) {
            for (int j = 0; j < max; j++) {
                if (j >= numbersArr[i].length) {
                    break;
                }
                else {
                    xArr[i] += abs(numbersArr[i][j]) % mod;
                    yArr[j] += abs(numbersArr[i][j]) % mod;
                    xArr[i] %= mod;
                    yArr[j] %= mod;
                }
            }
        }

        for (int i = 0; i < numbersArr.length; i++) {
            for (int j = 0; j < numbersArr[i].length; j++) {
                System.out.print((((((xArr[i] + yArr[j]) % mod) + mod) - (abs(numbersArr[i][j])) % mod)) % mod + " ");
            }
            System.out.println();
        }
    }
}

