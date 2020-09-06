package ru.progwards.java1.lessons.abstractnum;

public class IntNumber extends Number {
    int number;

    public IntNumber(int num) {
        number = num;
    }

    @Override
    public Number mul(Number num) {
        if (getClass() != num.getClass()) {
            return null;
        }
        int num1 = ((IntNumber)num).number;
        return new IntNumber(this.number*num1);
    }

    @Override
    public Number div(Number num) {
        if (getClass() != num.getClass()) {
            return null;
        }
        int num1 = ((IntNumber)num).number;
        return new IntNumber(this.number/num1);
    }

    @Override
    public Number newNumber(String strNum) {
        return new IntNumber(Integer.valueOf(strNum));
    }

    @Override
    public String toString() {
        return String.valueOf(number);
        //return Integer.toString(number);
    }
}
