package ru.progwards.java1.lessons.abstractnum;

public class IntNumber extends Number {
    int number;

    public IntNumber(int num) {
        number = num;
    }

    @Override
    public Number mul(Number num) {
        if (num == null || getClass() != num.getClass()) {
            return null;
        }
        int num1 = ((IntNumber)num).number;
        return new IntNumber(this.number*num1);
    }

    @Override
    public Number div(Number num) {
        if (num == null || getClass() != num.getClass()) {
            return null;
        }
        int num1 = ((IntNumber)num).number;
        return new IntNumber(this.number/num1);
    }

    @Override
    public Number newNumber(String strNum) {
        if (strNum==null) {
            return null;
        }
        if (strNum.indexOf('.')!=-1) {
            String[] parts = strNum.split("\\.");
            return new IntNumber(Integer.parseInt(parts[0]));
        }
        return new IntNumber(Integer.parseInt(strNum));
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
