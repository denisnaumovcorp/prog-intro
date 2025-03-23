package expression;

public class Subtract extends BinaryOperation {
    public Subtract(ExpressionTemplate left, ExpressionTemplate right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "-";
    }

    @Override
    protected int operate(int left, int right) {
        return left - right;
    }

    @Override
    protected float operateFloat(float left, float right) {
        return left - right;
    }

    @Override
    protected int getPriority() {
        return 1000;
    }
}