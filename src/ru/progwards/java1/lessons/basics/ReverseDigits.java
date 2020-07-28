package ru.progwards.java1.lessons.basics;

import static java.lang.Integer.parseInt;

public class ReverseDigits {
    public static int reverseDigits(int number) {
        String str = "";

        while (number != 0) {
            str = str + Integer.toString(number%10);
            number = number/10;
        }

        return parseInt(str);
    }

    public static void main(String[] args) {
        System.out.println(reverseDigits(123));
    }
}
