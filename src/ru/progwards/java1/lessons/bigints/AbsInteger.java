package ru.progwards.java1.lessons.bigints;

public abstract class AbsInteger {
    int number;

    public String toString() {
        return Integer.toString(number);
    }

    static AbsInteger add(AbsInteger num1, AbsInteger num2) {
        int i1 = Integer.valueOf(num1.toString());
        int i2 = Integer.valueOf(num2.toString());
        IntInteger n1 = new IntInteger(i1);
        IntInteger n2 = new IntInteger(i2);
        IntInteger res = n1.add(n2);

        if (res.number<Byte.MAX_VALUE && res.number>Byte.MIN_VALUE) {
            byte b = (byte) res.number;
            ByteInteger resB = new ByteInteger(b);
            return resB;
        }
        if (res.number<Short.MAX_VALUE && res.number>Short.MIN_VALUE) {
            short s = (short) res.number;
            ShortInteger resS = new ShortInteger(s);
            return resS;
        }

        return res;
    }

    public static void main(String[] args) {
        ByteInteger a1 = new ByteInteger((byte)12);
        ByteInteger a2 = new ByteInteger((byte)22);
        ShortInteger a3 = new ShortInteger((short)33);

        System.out.println(a1.toString());
        System.out.println(add(a1,a2));
        System.out.println(add(a1,a3));
    }
}
