package ru.progwards.java2.lessons.gc;

public class OutOfMemoryException extends Exception {

    OutOfMemoryException(int size) {
        super("Нет свободного блока размера " + size);
    }
}
