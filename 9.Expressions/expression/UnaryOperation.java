package expression;

import java.util.Map;
import java.util.Objects;

abstract public class UnaryOperation extends Operation {
    private final ExpressionTemplate object;

    public UnaryOperation(ExpressionTemplate object) {
        this.object = object;
    }

    protected abstract boolean isLeftOperator();

    protected abstract int operate(int object);

    protected abstract float operateFloat(float object);

    @Override
    public int evaluate(int x, int y, int z) {
        return operate(object.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return operate(object.evaluate(x));
    }

    @Override
    public float evaluateF(Map<String, Float> variables) {
        return operateFloat(object.evaluateF(variables));
    }

    @Override
    public String toString() {
        if (isLeftOperator()) {
            return getOperator() + '(' + object.toString() + ')';
        } else {
            return '(' + object.toString() + ')' + getOperator();
        }
    }

    @Override
    public String toMiniString() {
        if (isLeftOperator()) {
            return getOperator() + addParentheses(object);
        } else {
            return addParentheses(object) + getOperator();
        }
    }

    private String addParentheses(ExpressionTemplate expr) {
        if (expr instanceof BinaryOperation binaryExpr) {
            boolean needParentheses = this.getPriority() > binaryExpr.getPriority();
            if (needParentheses) {
                return "(" + expr.toMiniString() + ")";
            }
        } else if (expr instanceof UnaryOperation unaryExpr) {
            boolean needParentheses = this.getPriority() < unaryExpr.getPriority();
            if (needParentheses) {
                return "(" + expr.toMiniString() + ")";
            }
        }
        if (isLeftOperator()) {
            return " " + expr.toMiniString();
        }
        return expr.toMiniString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UnaryOperation that = (UnaryOperation) obj;
        return object.equals(that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object);
    }
}
