package ru.progwards.java1.lessons.io2;

public class PhoneNumber {
    public static String format(String phone) throws Exception {
        String res = "";
        for (char c : phone.toCharArray())
            if (Character.isDigit(c))
                res = res + c;

        if (res.length()<10 || res.length()>11) {
            throw new Exception("Неверное количество цифр в телефонном номере");
        }

        if (res.length()==11) {
            res = res.substring(1,11);
        }

        res = "+7(" + res.substring(0,3) + ")" + res.substring(3,6) + "-" + res.substring(6,10);
        return res;
    }

    public static void main(String[] args) {
        try {
            System.out.println(format("8 999 111 22 33"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
