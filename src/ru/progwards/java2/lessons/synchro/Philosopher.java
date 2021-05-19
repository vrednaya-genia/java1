package ru.progwards.java2.lessons.synchro;

public class Philosopher implements Runnable {
    private final String name;
    private final Fork right; // вилка справа
    private final Fork left; // вилка слева
    private final long reflectTime; // время, через которое философ проголодается (размышляет) в мс
    private final long eatTime; // время, за которое философ наестся (ест) в мс
    private long reflectSum; // суммарное время, которое философ размышлял в мс
    private long eatSum; // суммарное время, которое философ ел в мс

    public Philosopher(String name, Fork right, Fork left, long reflectTime, long eatTime) {
        this.name = name;
        this.right = right;
        this.left = left;
        this.reflectTime = reflectTime;
        this.eatTime = eatTime;
    }

    // размышлять. Выводит "размышляет "+ name на консоль с периодичностью 0.5 сек
    public void reflect() {

    }

    // есть. Выводит "ест "+ name на консоль с периодичностью 0.5 сек
    public void eat() {

    }

    @Override
    public void run() {

    }

    /* Остальные методы и свойства по вашему усмотрению */
}
