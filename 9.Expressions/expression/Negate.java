package expression;

public class Negate extends UnaryOperation {
    protected final static String operator = "-";

    public Negate(ExpressionTemplate object) {
        super(object);
    }

    @Override
    protected String getOperator() {
        return operator;
    }

    @Override
    protected boolean isLeftOperator() {
        return true;
    }

    @Override
    protected int operate(int object) {
        return -object;
    }

    @Override
    protected float operateFloat(float object) {
        return -object;
    }

    @Override
    protected int getPriority() {
        return 4000;
    }
}