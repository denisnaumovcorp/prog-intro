package expression;

public class Pow extends BinaryOperation {
    public Pow(ExpressionTemplate left, ExpressionTemplate right) {
        super(left, right);
    }

    private int pow(int b, int e) {
        int result = 1;
        while (e > 0) {
            if ((e & 1) == 0) {
                b *= b;
                e >>>= 1;
            } else {
                result *= b;
                e--;
            }
        }
        return result;
    }

    @Override
    protected int operate(int left, int right) {
        return pow(left, right);
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
        return "**";
    }
}
