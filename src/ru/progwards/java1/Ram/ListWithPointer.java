package ru.progwards.java1.Ram;

import java.util.ArrayList;

public class ListWithPointer<T> extends ArrayList<T> {
    private Integer pointer;

    public void nextPointer() {
        pointer++;
    }

    public Integer getPointer() {
        return this.pointer;
    }

    public void setPointer(Integer pointer) {
        this.pointer = pointer;
    }
}
