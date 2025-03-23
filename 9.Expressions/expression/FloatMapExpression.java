package expression;

import java.util.Map;

public interface FloatMapExpression extends ToMiniString {
    float evaluateF(Map<String, Float> variables);
}
