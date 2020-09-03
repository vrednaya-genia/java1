package ru.progwards.java1.lessons.bigints;

public class IntInteger extends AbsInteger {
    int number;

    IntInteger(int n) {
        this.number = n;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }

    public IntInteger add(IntInteger num) {
        IntInteger res = new IntInteger(this.number + num.number);
        return res;
    }
}
