package ru.progwards.java2.lessons.patterns;

public enum AbsIntegerFactory {
    INSTANCE;

    private AbsInteger number;

    AbsIntegerFactory() {

    }

    public AbsInteger getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if (number < Byte.MAX_VALUE && number > Byte.MIN_VALUE)
            this.number = new ByteInteger((byte)number);
        else if (number < Short.MAX_VALUE && number > Short.MIN_VALUE)
            this.number = new ShortInteger((short)number);
        else this.number = new IntInteger(number);
    }
}
