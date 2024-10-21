import java.io.IOException;
import java.util.ArrayList;

public class Reverse {
    public static void main(String[] args) {
        ArrayList<String> numbersArr = new ArrayList<>();
        MyScanner scan = new MyScanner(System.in);
        try {
            while (scan.hasNextLine()) {
                if (scan.hasNextInLine()) {
                    numbersArr.add("\n");
                    while (scan.hasNextInLine()) {
                        String number = scan.next();
                        numbersArr.add(number);
                    }
                } else {
                    numbersArr.add("\n");
                }
                scan.goToNextLine();
            }
            scan.close();
        } catch (IOException e) {
            System.err.println("An error occurred while trying to read the input stream.");
            System.err.println(e.getMessage());
        }
        for (int i = numbersArr.size() - 1; i > 0 ; i--) {
            System.out.print(numbersArr.get(i) + ' ');
        }
    }
}
