package ru.progwards.java1.lessons.compare_if_cycles;

public class TriangleInfo {

    public static boolean isTriangle(int a, int b, int c) {
        if (a < b+c && b < a+c && c < a+b) return true;
            else return false;
    }

    public static boolean isRightTriangle(int a, int b, int c) {
        if (a*a + b*b == c*c || a*a + c*c == b*b || c*c + b*b == a*a) return true;
            else return false;
    }

    public static boolean isIsoscelesTriangle(int a, int b, int c) {
        if (a==b || a==c || b==c) return true;
            else return false;
    }

    public static void main(String[] args) {
        System.out.println(isTriangle(1,1,5));
        System.out.println(isRightTriangle(3,4,5));
        System.out.println(isIsoscelesTriangle(1,3,4));
    }
}
