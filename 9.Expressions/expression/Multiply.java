package expression;

public class Multiply extends BinaryOperation {
    public Multiply(ExpressionTemplate left, ExpressionTemplate right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "*";
    }

    @Override
    protected int operate(int left, int right) {
        return left * right;
    }

    @Override
    protected float operateFloat(float left, float right) {
        return left * right;
    }

    @Override
    protected int getPriority() {
        return 2000;
    }
}