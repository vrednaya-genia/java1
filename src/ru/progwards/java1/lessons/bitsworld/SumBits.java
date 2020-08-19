package ru.progwards.java1.lessons.bitsworld;

public class SumBits {
    public static int sumBits(byte value) {
        int result=0;
        for (int i=0; i<8; i++) {
            byte temp = (byte) (value & 0b0000_0001);
            result += temp;
            value = (byte) (value >> 1);
        }
        return result;
    }

    public static void main(String[] args) {
        byte a = 127;
        System.out.println(sumBits(a));
        System.out.println(String.format("%32s", Integer.toBinaryString(a)));
    }
}
