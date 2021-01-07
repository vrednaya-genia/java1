package ru.progwards.java2.lessons.recursion;

public class AsNumbersSum {
    public static String asNumbersSum(int number) {
        return number + " = " + asNumbersSum2(number-1);
    }

    public static String asNumbersSum2(int number) {
        if (number==2) {
            return "1+1";
        }
        String res = number + "+1 = ";
        return res + asNumbersSum(number-1) + "+1";
    }


    public static void main(String[] args) {
        System.out.println(asNumbersSum(5));
    }
}