package ru.progwards.java2.lessons.calculator;

public class CalculatorEasy {
    public static int calculate(String expression) {
        if (expression.contains("*")) {
            int index = expression.indexOf("*");
            int i1 = Integer.parseInt(String.valueOf(expression.charAt(index-1)));
            int i2 = Integer.parseInt(String.valueOf(expression.charAt(index+1)));
            int res = i1*i2;
            String temp = expression.substring(0, index-1);
            String temp2 = expression.substring(index+2);
            String exp = temp + res + temp2;
            return calculate(exp);
        }
        if (expression.contains("/")) {
            int index = expression.indexOf("/");
            int i1 = Integer.parseInt(String.valueOf(expression.charAt(index-1)));
            int i2 = Integer.parseInt(String.valueOf(expression.charAt(index+1)));
            int res = i1/i2;
            String temp = expression.substring(0, index-1);
            String temp2 = expression.substring(index+2);
            String exp = temp + res + temp2;
            return calculate(exp);
        }
        if (expression.contains("+")) {
            int index = expression.indexOf("+");
            int i1 = Integer.parseInt(String.valueOf(expression.charAt(index-1)));
            int i2 = Integer.parseInt(String.valueOf(expression.charAt(index+1)));
            int res = i1+i2;
            String temp = expression.substring(0, index-1);
            String temp2 = expression.substring(index+2);
            String exp = temp + res + temp2;
            return calculate(exp);
        }
        if (expression.contains("-")) {
            int index = expression.indexOf("-");
            int i1 = Integer.parseInt(String.valueOf(expression.charAt(index-1)));
            int i2 = Integer.parseInt(String.valueOf(expression.charAt(index+1)));
            int res = i1-i2;
            String temp = expression.substring(0, index-1);
            String temp2 = expression.substring(index+2);
            String exp = temp + res + temp2;
            return calculate(exp);
        }
        return Integer.parseInt(expression);
    }

    public static void main(String[] args) {
        System.out.println(calculate("9+8/2"));
    }

}
