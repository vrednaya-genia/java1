package ru.progwards.java2.lessons.patterns;

public class ShortInteger extends AbsInteger {
    short number;

    public ShortInteger(short n) {
        number = n;
    }

    @Override
    public String toString() {
        return Short.toString(number);
    }
}
