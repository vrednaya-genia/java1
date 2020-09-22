package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class MatrixIterator<T> implements Iterator<T> {
    private T[][] array;
    private int pos1;
    private int pos2;

    MatrixIterator(T[][] array) {
        this.array = array;
        pos1 = 0;
        pos2 = 0;
    }

    @Override
    public boolean hasNext() {
        return pos2<array[pos1].length || pos1<array.length-1;
    }

    @Override
    public T next() {
        if (pos2==array[pos1].length) {
            pos1 = pos1+1;
            pos2 = 0;
        }
        pos2 = pos2+1;
        return array[pos1][pos2-1];
    }

    public static void main(String[] args) {
        Integer[][] ar = {{5,7}, {7,0,1}, {8}};;

        MatrixIterator mi = new MatrixIterator(ar);
        while (mi.hasNext()) {
            System.out.println(mi.next());
        }
    }
}
