package ru.progwards.java1.lessons.queues;

import java.util.ArrayDeque;

public class StackCalc {
    ArrayDeque<Double> stack = new ArrayDeque();

    public void push(double value) {
        stack.addFirst(value);
    }

    public double pop() {
        return stack.pollFirst();
    }

    public void add() {
        Double d1 = pop();
        Double d2 = pop();
        stack.addFirst(d1+d2);
    }

    public void sub() {
        Double d1 = pop();
        Double d2 = pop();
        stack.addFirst(d2-d1);
    }

    public void mul() {
        Double d1 = pop();
        Double d2 = pop();
        stack.addFirst(d2*d1);
    }

    public void div() {
        Double d1 = pop();
        Double d2 = pop();
        stack.addFirst(d2/d1);
    }
}

