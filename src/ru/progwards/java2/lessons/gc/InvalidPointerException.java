package ru.progwards.java2.lessons.gc;

public class InvalidPointerException extends Exception {

    InvalidPointerException(int ptr) {
        super("Неверный указатель! " + ptr + " не является началом блока!");
    }
}
