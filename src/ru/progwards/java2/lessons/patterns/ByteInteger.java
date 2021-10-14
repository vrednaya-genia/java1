package ru.progwards.java2.lessons.patterns;

public class ByteInteger extends AbsInteger {
    byte number;

    public ByteInteger(byte n) {
        number = n;
    }

    @Override
    public String toString() {
        return Byte.toString(number);
    }
}
