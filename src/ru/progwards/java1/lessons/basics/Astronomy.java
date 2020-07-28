package ru.progwards.java1.lessons.basics;

public class Astronomy {
    static final double PI = 3.14;

    public static Double sphereSquare(Double r) {
        return 4 * PI * r*r; // S = 4 * pi * R^2
    }

    public static Double earthSquare() {
        return sphereSquare(6_371.2); //км
    }

    public static Double mercurySquare() {
        return sphereSquare(2_439.7); //км
    }

    public static Double jupiterSquare() {
        return sphereSquare(71_492.0); //км
    }

    public static Double earthVsMercury() {
        return earthSquare()/mercurySquare();
    }

    public static Double earthVsJupiter() {
        return earthSquare()/jupiterSquare();
    }

    public static void main(String[] args) {
        System.out.println(sphereSquare(1.2));
        System.out.println(earthSquare());
        System.out.println(mercurySquare());
        System.out.println(jupiterSquare());
        System.out.println(earthVsMercury());
        System.out.println(earthVsJupiter());
    }
}
