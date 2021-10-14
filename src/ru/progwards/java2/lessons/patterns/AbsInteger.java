package ru.progwards.java2.lessons.patterns;

public abstract class AbsInteger {
    int number;

    static AbsInteger add(AbsInteger num1, AbsInteger num2) {
        int i1 = Integer.parseInt(num1.toString());
        int i2 = Integer.parseInt(num2.toString());
        IntInteger res = new IntInteger(i1 + i2);

        if (res.number < Byte.MAX_VALUE && res.number > Byte.MIN_VALUE) {
            byte b = (byte) res.number;
            return new ByteInteger(b);
        }

        if (res.number < Short.MAX_VALUE && res.number > Short.MIN_VALUE) {
            short s = (short) res.number;
            return new ShortInteger(s);
        }

        return res;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
