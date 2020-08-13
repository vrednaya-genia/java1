package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class DIntArray {
    private int[] intArray;

    public DIntArray() {
        intArray = new int[0];
    }

    public void add(int num) {
        int[] newIntArray = new int[intArray.length+1];
        System.arraycopy(intArray, 0, newIntArray, 0, intArray.length);
        newIntArray[newIntArray.length-1] = num;

        intArray = new int[newIntArray.length];
        System.arraycopy(newIntArray, 0, intArray, 0, newIntArray.length);
    }

    public void atInsert(int pos, int num) {
        int[] newIntArray = new int[intArray.length+1];
        System.arraycopy(intArray, 0, newIntArray, 0, pos);
        newIntArray[pos] = num;
        System.arraycopy(intArray, pos, newIntArray, pos+1, intArray.length-pos);

        intArray = new int[newIntArray.length];
        System.arraycopy(newIntArray, 0, intArray, 0, newIntArray.length);
    }

    public void atDelete(int pos) {
        int[] newIntArray = new int[intArray.length-1];
        System.arraycopy(intArray, 0, newIntArray, 0, pos);
        System.arraycopy(intArray, pos+1, newIntArray, pos, intArray.length-pos-1);

        intArray = new int[newIntArray.length];
        System.arraycopy(newIntArray, 0, intArray, 0, newIntArray.length);
    }

    public int at(int pos) {
        return intArray[pos];
    }

    public static void main(String[] args) {
        DIntArray a = new DIntArray();

        a.add(46);
        a.add(40);
        a.add(15);
        a.add(82);
        a.atInsert(3,40);
        a.atDelete(2);
        System.out.println(a.at(0));
    }
}
