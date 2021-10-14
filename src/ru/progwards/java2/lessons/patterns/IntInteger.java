package ru.progwards.java2.lessons.patterns;

public class IntInteger extends AbsInteger {
    int number;

    public IntInteger(int n) {
        number = n;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
