package ru.progwards.java2.lessons.calculator;

public class Calculator {
    String expr;

    public Calculator(String expr) {
        this.expr = expr;
    }

    public static int calculate(String expression) {
        return new Calculator(expression).calc();
    }

    public static void main(String[] args) {
        System.out.println(calculate("20+3*(12-3)"));
    }

    String getResult(Operation op, int ind) {
        String num = "";
        int indL = ind;
        while (indL > 0 && Character.isDigit(expr.charAt(indL - 1))) {
            num = expr.charAt(indL - 1) + num;
            indL = indL - 1;
        }
        int i1 = Integer.parseInt(num);

        num = "";
        int indR = ind;
        while (indR < expr.length() - 1 && Character.isDigit(expr.charAt(indR + 1))) {
            num = num + expr.charAt(indR + 1);
            indR = indR + 1;
        }
        int i2 = Integer.parseInt(num);

        int res = switch (op) {
            case MUL -> i1 * i2;
            case DEV -> i1 / i2;
            case ADD -> i1 + i2;
            case SUB -> i1 - i2;
        };
        String temp1 = expr.substring(0, indL);
        String temp2 = expr.substring(indR + 1);
        return temp1 + res + temp2;
    }

    int calc() {
        if (expr.contains("(")) {
            int ind1 = expr.indexOf("(");
            int ind2 = expr.indexOf(")");
            if (ind2==-1) {
                System.out.println("Error");
                return 0;
            }
            String temp = expr.substring(ind1+1,ind2);
            String temp1 = expr.substring(0, ind1);
            String temp2 = expr.substring(ind2 + 1);
            return calculate(temp1 + calculate(temp) + temp2);
        }
        if (expr.contains("*")) {
            int index = expr.indexOf("*");
            return calculate(getResult(Operation.MUL, index));
        }
        if (expr.contains("/")) {
            int index = expr.indexOf("/");
            return calculate(getResult(Operation.DEV, index));
        }
        if (expr.contains("+")) {
            int index = expr.indexOf("+");
            return calculate(getResult(Operation.ADD, index));
        }
        if (expr.contains("-")) {
            int index = expr.indexOf("-");
            return calculate(getResult(Operation.SUB, index));
        }
        return Integer.parseInt(expr);
    }

    enum Operation {MUL, DEV, ADD, SUB}
}
