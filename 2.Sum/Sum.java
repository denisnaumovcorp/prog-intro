public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (String sym : args) {
            String number = "";
            for (int i = 0; i < sym.length(); i++) {
                if (sym.charAt(i) == '-' && number.equals("-"))
                    number = "";
                else if (sym.charAt(i) == '-')
                    number = "-";
                else if (Character.isDigit(sym.charAt(i))) {
                    while (i < sym.length() && sym.charAt(i) != '+' && !Character.isWhitespace(sym.charAt(i)))
                        number += sym.charAt(i++);
                    sum += Integer.parseInt(number);
                    number = "";
                }
            }
        }
        System.out.println(sum);
    }
}