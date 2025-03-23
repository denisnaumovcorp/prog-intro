package expression;

public interface ExpressionTemplate extends Expression, TripleExpression, FloatMapExpression {
    String toString();

    boolean equals(Object obj);

    int hashCode();
}
