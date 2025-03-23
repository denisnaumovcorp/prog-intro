package expression;

import java.util.Map;
import java.util.Objects;

public abstract class BinaryOperation extends Operation {
    private final ExpressionTemplate left, right;

    public BinaryOperation(ExpressionTemplate left, ExpressionTemplate right) {
        this.left = left;
        this.right = right;
    }

    protected abstract int operate(int left, int right);

    protected abstract float operateFloat(float left, float right);

    @Override
    public int evaluate(int x, int y, int z) {
        return operate(
                left.evaluate(x, y, z),
                right.evaluate(x, y, z)
        );
    }

    @Override
    public int evaluate(int x) {

        return operate(
                left.evaluate(x),
                right.evaluate(x)
        );
    }

    @Override
    public float evaluateF(Map<String, Float> variables) {
        return operateFloat(
                left.evaluateF(variables),
                right.evaluateF(variables)
        );
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + getOperator() + " " + right.toString() + ")";
    }

    @Override
    public String toMiniString() {
        String leftStr = addParentheses(left, false);
        String rightStr = addParentheses(right, true);
        return leftStr + " " + getOperator() + " " + rightStr;
    }

    private String addParentheses(ExpressionTemplate expr, boolean isRight) {
        if (expr instanceof Operation binaryExpr) {
            boolean needParentheses = binaryExpr.getPriority() < this.getPriority() ||
                    (isRight && (this.getPriority() == binaryExpr.getPriority() && isCommutative(this) ||
                            binaryExpr.getOperator().equals("/") && this.getOperator().equals("*")));
            if (needParentheses) {
                return "(" + expr.toMiniString() + ")";
            }
        }
        return expr.toMiniString();
    }

    private boolean isCommutative(ExpressionTemplate expr) {
        if (expr instanceof BinaryOperation binaryExpr) {
            return binaryExpr.getOperator().equals("-") || binaryExpr.getOperator().equals("/") || binaryExpr.getOperator().equals("**") || binaryExpr.getOperator().equals("//");
        } else return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BinaryOperation that = (BinaryOperation) obj;
        return left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, getOperator());
    }
}