package expression;

import java.util.Map;

public class Const implements ExpressionTemplate {
    private final int value;
    private boolean isFloat = false;

    public Const(int value) {
        this.value = value;
    }

    public Const(float value) {
        this.value = Float.floatToIntBits(value);
        this.isFloat = true;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    @Override
    public float evaluateF(Map<String, Float> variables) {
        return Float.intBitsToFloat(value);
    }

    @Override
    public String toString() {
        if (isFloat) {
            return Float.toString(Float.intBitsToFloat(value));
        } else {
            return Integer.toString(value);
        }
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Const aConst = (Const) obj;
        return value == aConst.value;
    }

    @Override
    public int hashCode() {
        return Float.hashCode(value);
    }

}
