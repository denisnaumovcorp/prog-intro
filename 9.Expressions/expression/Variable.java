package expression;

import java.util.Map;

public class Variable implements ExpressionTemplate {
    private final String id;
    private final String name;

    public Variable(String name) {
        this.id = "";
        this.name = name;
    }

    public Variable(String id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalStateException("Unexpected variable: " + name);
        };
    }

    @Override
    public float evaluateF(Map<String, Float> variables) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Variable " + name + " not found in the provided map");
        }
        return variables.get(name);
    }


    @Override
    public String toString() {
        return id + name;
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Variable variable = (Variable) obj;
        return name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}