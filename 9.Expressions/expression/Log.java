package expression;

public class Log extends BinaryOperation {
    public Log(ExpressionTemplate left, ExpressionTemplate right) {
        super(left, right);
    }

    @Override
    protected int operate(int left, int right) {
        if (right <= 0 || left < 0) {
            return 0;
        } else {
            return (int) (Math.log(left) / Math.log(right));
        }
    }

    @Override
    protected float operateFloat(float left, float right) {
        return 0;
    }

    @Override
    protected int getPriority() {
        return 3000;
    }

    @Override
    protected String getOperator() {
        return "//";
    }

    /*int ten_log(int x) {;
        if(x == 0) {
            return Integer.MIN_VALUE;
        } else {
            int number = 1;
            int power = 0;
            while (number < x) {
                if (number * number < x && number != 1) {
                    number *= number;
                    power += power;
                } else {
                    number *= 10;
                    power++;
                }
            }
            return power;
        }
    }*/
}
