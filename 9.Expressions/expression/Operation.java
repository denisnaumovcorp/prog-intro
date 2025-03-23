package expression;

abstract public class Operation implements ExpressionTemplate {
    protected abstract int getPriority();

    protected abstract String getOperator();
}
