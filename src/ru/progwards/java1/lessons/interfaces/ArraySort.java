package ru.progwards.java1.lessons.interfaces;

public class ArraySort {

    public static void sort(CompareWeight[] a) {
        CompareWeight a1;
        for (int i=0; i<a.length; i++) {
            for (int j=i+1; j<a.length; j++) {
                if (a[i].compareWeight(a[j]) == CompareWeight.CompareResult.GREATER) {
                    a1 = a[i];
                    a[i] = a[j];
                    a[j] = a1;
                }
            }
        }
    }

    public static void main(String[] args) {
        CompareWeight test1 = new Food(2);
        CompareWeight test2 = new Food(3);
        CompareWeight test3 = new Food(5);
        CompareWeight test4 = new Food(1);
        CompareWeight test5 = new Food(6);

        CompareWeight[] t = {test1, test2, test3, test4, test5};
        sort(t);
        System.out.println("ok");
    }

}
