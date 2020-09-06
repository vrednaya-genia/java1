package ru.progwards.java1.lessons.abstractnum;

public class Test {
    public static void main(String[] args) {
        IntNumber n1 = new IntNumber(3);
        Cube c1 = new Cube(n1);
        System.out.println(c1.volume());

        DoubleNumber d1 = new DoubleNumber(3.0);
        Cube c2 = new Cube(d1);
        System.out.println(c2.volume());

        Pyramid p1 = new Pyramid(n1);
        System.out.println(p1.volume());

        Pyramid p2 = new Pyramid(d1);
        System.out.println(p2.volume());
    }
}
