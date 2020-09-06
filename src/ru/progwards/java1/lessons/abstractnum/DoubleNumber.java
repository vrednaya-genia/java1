package ru.progwards.java1.lessons.abstractnum;

public class DoubleNumber extends Number {
    double number;

    public DoubleNumber(double num) {
        number = num;
    }

    @Override
    public Number mul(Number num) {
        if (num == null || getClass() != num.getClass()) {
            return null;
        }
        double num1 = ((DoubleNumber)num).number;
        return new DoubleNumber(this.number*num1);
    }

    @Override
    public Number div(Number num) {
        if (num == null || getClass() != num.getClass()) {
            return null;
        }
        double num1 = ((DoubleNumber)num).number;
        return new DoubleNumber(this.number/num1);
    }

    @Override
    public Number newNumber(String strNum) {
        if (strNum==null) {
            return null;
        }
        if (strNum.indexOf('.')==-1) {
            strNum = strNum + ".0";
        }
        return new DoubleNumber(Double.parseDouble(strNum));
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
