package ru.progwards.java1.lessons.basics;

import static java.lang.Integer.parseInt;

public class ReverseDigits {
    public static int reverseDigits(int number) {
        /*
        String str = "";

        while (number != 0) {
            str = str + Integer.toString(number%10);
            number = number/10;
        }

        return parseInt(str);
        */

        int x1 = 100*(number%10);
        int x2 = 10*((number/10)%10);
        int x3 = (number/100)%10;
        number = x1 + x2 + x3;

        return number;
    }

    public static void main(String[] args) {
        System.out.println(reverseDigits(123));
    }
}
