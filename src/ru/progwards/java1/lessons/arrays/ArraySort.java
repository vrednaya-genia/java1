package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class ArraySort {
    public static void sort(int[] a) {
        for (int i=0; i<a.length; i++) {
            for (int j=i+1; j<a.length; j++) {
                if (a[i]>a[j]) {
                    int a1 = a[i];
                    int a2 = a[j];
                    a[i] = a2;
                    a[j] = a1;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {2,6,-8,3,5,-7};
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
