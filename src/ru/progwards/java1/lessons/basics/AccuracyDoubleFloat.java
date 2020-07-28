package ru.progwards.java1.lessons.basics;

public class AccuracyDoubleFloat {
    static final double PI = 3.14;
    static final double R = 6371.2; // радиус Земли в км

    public static double volumeBallDouble(double radius) {
        return 4/3 * PI * radius*radius*radius; // V = 4\3 * pi * R^3
    }

    public static float volumeBallFloat(float radius) {
        return  4/3 * (float)PI * radius*radius*radius;
    }

    public static double calculateAccuracy(double radius) {
        return volumeBallDouble(radius)/volumeBallFloat((float)radius);
    }

    public static void main(String[] args) {
        System.out.println(calculateAccuracy(1.0));
        System.out.println(calculateAccuracy(R));
    }
}
