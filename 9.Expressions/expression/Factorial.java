package expression;

import static java.lang.Math.abs;

public class Factorial extends UnaryOperation {
    protected final static String operator = "!";

    public Factorial(ExpressionTemplate object) {
        super(object);
    }

    @Override
    protected String getOperator() {
        return operator;
    }

    @Override
    protected boolean isLeftOperator() {
        return false;
    }

    @Override
    protected int operate(int object) {
        if (object < -32 || object > 32) {
            return 0;
        }
        object = abs(object);
        int result = 1;
        for (int i = 1; i <= object; i++) {
            result = result * i;
        }
        return result;
    }

    @Override
    protected float operateFloat(float object) {
        return object;
    }

    @Override
    protected int getPriority() {
        return 10000;
    }
}