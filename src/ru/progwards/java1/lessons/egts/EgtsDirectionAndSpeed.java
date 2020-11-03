package ru.progwards.java1.lessons.egts;

public class EgtsDirectionAndSpeed {
    public static int getSpeed(short speedAndDir) {
        return speedAndDir & 0b00000000_00000000_01111111_11111111;
    }

    public static int getDirection(byte dirLow, short speedAndDir) {
        // старший бит направления
        int res = speedAndDir & 0b00000000_00000000_10000000_00000000;
        res >>= 7;
        // младшие биты направления
        int temp = dirLow & 0b00000000_00000000_00000000_11111111;

        res += temp;
        return res;
    }

    public static void main(String[] args) {
        System.out.println(getSpeed((short)0b01111111_11111111));
        System.out.println(getDirection((byte)0b11111111, (short)0b01111111_11111111));
    }
}
