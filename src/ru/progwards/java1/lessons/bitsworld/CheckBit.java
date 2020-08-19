package ru.progwards.java1.lessons.bitsworld;

public class CheckBit {
    // bitNumber {0..7}
    public static int checkBit(byte value, int bitNumber) {
        /*int result=0;
        for (int i=0; i<=bitNumber; i++) {
            byte temp = (byte) (value & 0b0000_0001);
            result = temp;
            value = (byte) (value >> 1);
        }
        */
        value = (byte) (value >> bitNumber);
        int result = value & 0b0000_0001;
        return result;
    }

    public static void main(String[] args) {
        byte a = -128;
        for (int i=0; i<8; i++) {
            System.out.println(checkBit(a, i));
        }
        System.out.println(String.format("%32s", Integer.toBinaryString(a)));
    }
}
