package ch.zhaw.ciel.mse.alg.util;

public class Expression {
    public enum Operator {
        LESS,
        LESS_EQUAL,
        EQUAL,
        GREATER_EQUAL,
        GREATER
    }

    public static boolean evaluate(double left, Operator op, double right) {
        switch (op) {
            case LESS:              return left <  right;
            case LESS_EQUAL:        return left <= right;
            case EQUAL:             return left == right;
            case GREATER_EQUAL:     return left >= right;
            case GREATER:           return left >  right;
        }
        return false;
    }
}
