package ru.progwards.java1.lessons.io2;

public class PhoneNumber {
    public static String format(String phone) {
        String res = "";
        for (char c : phone.toCharArray())
            if (Character.isDigit(c))
                res = res + c;

        return res;
    }

    public static void main(String[] args) {
        System.out.println(format("8gh6/f5f6f2r4r5r65rr55r"));
    }
}
