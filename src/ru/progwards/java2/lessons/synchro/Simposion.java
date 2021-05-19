package ru.progwards.java2.lessons.synchro;

public class Simposion {
    // количество философов и вилок
    private static final int COUNT = 5;

    Simposion(long reflectTime, long eatTime) {
        Fork[] forks = new Fork[COUNT];
        for (int i=0; i < COUNT; i++) {
            forks[i] = new Fork();
        }

        Thread[] philosophers = new Thread[COUNT];
        for (int i = 0; i < COUNT-1; i++) {
            philosophers[i] = new Thread(new Philosopher("Ф"+(i+1), forks[i], forks[i+1], reflectTime, eatTime));
        }
        philosophers[COUNT-1] = new Thread(new Philosopher("Ф"+COUNT, forks[COUNT-1], forks[0], reflectTime, eatTime));

        // запустить потоки
    }

    // запускает философскую беседу
    void start() {

    }

    // завершает философскую беседу
    void stop() {

    }

    //печатает результаты беседы в формате
    //Философ name, ел ххх, размышлял xxx
    //где ххх время в мс
    void print() {

    }

    public static void main(String[] args) {
        // тест для философской беседы. Проверить варианты,
        // когда ресурсов (вилок) достаточно (философы долго размышляют и мало едят) и
        // вариант когда не хватает (философы много едят и мало размышляют)
    }
}
