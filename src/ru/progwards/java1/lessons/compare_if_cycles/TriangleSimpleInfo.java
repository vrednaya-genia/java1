package ru.progwards.java1.lessons.compare_if_cycles;

public class TriangleSimpleInfo {

    public static int maxSide(int a, int b, int c) {
        int maxS;

        if (a >= b) maxS = a;
            else maxS = b;

        if (c > maxS) maxS = c;

        return maxS;
    }

    public static int minSide(int a, int b, int c) {
        int minS;

        if (a >= b) minS = b;
            else minS = a;

        if (c < minS) minS = c;

        return minS;
    }

    public static boolean isEquilateralTriangle(int a, int b, int c) {
        if (a == b && b == c) return true;
            else return false;
    }

    public static void main(String[] args) {
        System.out.println(maxSide(1, 1, 1));
        System.out.println(minSide(1, 1, 1));
        System.out.println(isEquilateralTriangle(1, 1, 1));
    }
}
