package ru.progwards.java1.lessons.bigints;

public class ByteInteger extends AbsInteger {
    byte number;

    ByteInteger(byte n) {
        this.number = n;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
