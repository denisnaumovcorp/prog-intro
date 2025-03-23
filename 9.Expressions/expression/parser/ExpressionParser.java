package expression.parser;

import expression.*;

import java.util.Map;

public class ExpressionParser implements TripleParser {

    @Override
    public TripleExpression parse(String expression) {
        return new lolParser(new StringSource(expression)).parseTriple();
    }

    private static class lolParser extends BaseParser {
        private final Map<String, Integer> BINARY_OPERATIONS = Map.of("+", 1000, "-", 1000, "*", 2000, "/", 2000, "**", 3000, "//", 3000);
        private final Map<String, Integer> UNARY_OPERATIONS = Map.of("!", 10000);

        public lolParser(final CharSource source) {
            super(source);
        }

        public TripleExpression parseTriple() {
            return parseOperation(parseData(), -1);
        }

        public ExpressionTemplate parseData() {
            skipWhitespace();
            if (test('-')) {
                take();
                return parseNegated();
            } else if (Character.isDigit(getCharacter())) {
                return parseConstant(false);
            } else if (test('(')) {
                take();
                ExpressionTemplate expression = parseOperation(parseData(), -1);
                expect(')');
                skipWhitespace();
                return expression;
            } else if (Character.isAlphabetic(getCharacter())) {
                StringBuilder id = new StringBuilder();
                while (!test('x') && !test('y') && !test('z')) {
                    id.append(take());
                }
                return new Variable(id.toString(), Character.toString(take()));
            } else {
                throw error("Unexpected element: " + getCharacter());
            }
        }

        public ExpressionTemplate parseOperation(ExpressionTemplate expression, int lastPriority) {
            skipWhitespace();
            char next = peek();
            String operator = Character.toString(getCharacter());
            if (isBinaryOperation(operator + next)) {
                operator += next;
            }
            if (isBinaryOperation(operator)) {
                if (lastPriority >= BINARY_OPERATIONS.getOrDefault(operator, -1)) {
                    return expression;
                } else {
                    for (int i = 0; i < operator.length(); i++) {
                        take();
                    }
                    return parseOperation(getBinaryOperator(operator, expression,
                                    parseOperation(parseData(), BINARY_OPERATIONS.getOrDefault(operator, -1))),
                            lastPriority);
                }
            } else if (isUnaryOperation(operator)) {
                take();
                return parseOperation(new Factorial(expression), lastPriority);
            } else {
                return expression;
            }
        }


        private ExpressionTemplate parseConstant(boolean isNegative) {
            StringBuilder number = new StringBuilder();
            number.append(isNegative ? "-" : "");
            while (Character.isDigit(getCharacter())) {
                number.append(take());
            }
            return new Const(Integer.parseInt(number.toString()));
        }

        private ExpressionTemplate parseNegated() {
            if (Character.isDigit(getCharacter())) {
                return parseConstant(true);
            }
            return new Negate(parseData());
        }


        private ExpressionTemplate getBinaryOperator(String operator, ExpressionTemplate left, ExpressionTemplate right) {
            return switch (operator) {
                case "+" -> new Add(left, right);
                case "-" -> new Subtract(left, right);
                case "*" -> new Multiply(left, right);
                case "/" -> new Divide(left, right);
                case "**" -> new Pow(left, right);
                case "//" -> new Log(left, right);
                default -> throw error("Unexpected operator: " + getCharacter());
            };
        }

        private boolean isBinaryOperation(String operator) {
            for (String s : BINARY_OPERATIONS.keySet()) {
                if (s.equals(operator)) {
                    return true;
                }
            }
            return false;
        }

        private boolean isUnaryOperation(String operator) {
            for (String s : UNARY_OPERATIONS.keySet()) {
                if (s.equals(operator)) {
                    return true;
                }
            }
            return false;
        }

        private void skipWhitespace() {
            while (Character.isWhitespace(getCharacter())) {
                take();
            }
        }
    }
}