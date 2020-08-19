package ru.progwards.java1.lessons.bitsworld;

public class Binary {
    byte num;

    public Binary(byte num) {
        this.num = num;
    }

    public String toString() {
        String result = "";
        byte temp1 = this.num;
        for (int i=0; i<8; i++) {
            byte temp2 = (byte) (temp1 & 0b0000_0001);
            result = temp2 + result;
            temp1 = (byte) (temp1 >> 1);
        }
        return result;
    }

    public static void main(String[] args) {
        byte a = 127;
        Binary b = new Binary(a);
        System.out.println(b.toString());
        System.out.println(String.format("%32s", Integer.toBinaryString(a)));
    }
}
