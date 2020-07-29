package ru.progwards.java1.lessons.compare_if_cycles;

public class CyclesGoldenFibo {

    public static boolean containsDigit(int number, int digit) {
        while (number != 0) {
            if (number%10 == digit) return true;
            number = number/10;
        }
        return false;
    }

    public static int fiboNumber(int n) {

        if (n==1) return 1;
        if (n==2) return 1;

        int fNum = 0;
        int fNum1 = 1;
        int fNum2 = 1;

        for (int i = 3; i<=n; i++) {
            fNum = fNum1 + fNum2;
            fNum1 = fNum2;
            fNum2 = fNum;
        }
        return fNum;
    }

    public static boolean isGoldenTriangle(int a, int b, int c){
        if ((a==b && (double)a/c > 1.61703 && (double)a/c < 1.61903) ||
                (a==c && (double)a/b > 1.61703 && (double)a/b < 1.61903) ||
                (b==c && (double)b/a > 1.61703 && (double)b/a < 1.61903)) return true;

        return false;
    }

    public static void main(String[] args) {
        int i, j, k;

        for (i=1; i<=15; i++) {
            System.out.println(fiboNumber(i));
        }

        for (i=1; i<=100; i++) {
            for (j=1; j<=100; j++) {
                for (k=1; k<=100; k++) {
                    if (isGoldenTriangle(i, j, k)) System.out.println(i + " " + j + " " + k);
                }
            }
        }
    }
}
