public class SumLongPunctHex {
    public static void main(String[] args) {
        long sum = 0;
        for (String sym : args) {
            for (int i = 0; i < sym.length(); i++) {
                StringBuilder number = new StringBuilder();
                while (i < sym.length() && !Character.isWhitespace(sym.charAt(i)) && Character.getType(sym.charAt(i)) != Character.START_PUNCTUATION && Character.getType(sym.charAt(i)) != Character.END_PUNCTUATION) {
                    number.append(sym.charAt(i++));
                }
                String sNumber = number.toString();
                if (number.length() >= 2 && (number.charAt(1) == 'x' || number.charAt(1) == 'X')) {
                    sNumber = sNumber.substring(2);
                    sum += Long.parseUnsignedLong(sNumber, 16);
                } else if (sNumber.length() > 0) {
                    sum += Long.parseLong(sNumber);
                }
            }
        }
        System.out.println(sum);
    }
}