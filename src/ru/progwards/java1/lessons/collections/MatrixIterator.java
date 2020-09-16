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
        return pos2<array[0].length && pos1<array.length;
    }

    @Override
    public T next() {
        if (pos2>array[0].length) {
            pos1 = pos1+1;
            pos2 = 0;
        }
        pos2 = pos2+1;
        return array[pos1-1][pos2-1];
    }
}
