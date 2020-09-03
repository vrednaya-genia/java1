package ru.progwards.java1.lessons.bigints;

public class ShortInteger extends AbsInteger {
    short number;

    ShortInteger(short n) {
        this.number = n;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
