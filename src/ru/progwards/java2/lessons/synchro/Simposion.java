package ru.progwards.java2.lessons.synchro;

public class Simposion {
    private static final long time = 7000; // время теста (мс)
    private static final int N = 5; // количество философов и вилок
    private final Thread[] threads = new Thread[N];
    private final Philosopher[] philosophers = new Philosopher[N];

    Simposion(long reflectTime, long eatTime) {
        Fork[] forks = new Fork[N];
        for (int i = 0; i < N; i++) {
            forks[i] = new Fork();
        }

        for (int i = 0; i < N-1; i++) {
            philosophers[i] = new Philosopher("Ф"+(i+1), forks[i], forks[i+1], reflectTime, eatTime);
            threads[i] = new Thread(philosophers[i]);
        }
        philosophers[N-1] = new Philosopher("Ф"+ N, forks[N-1], forks[0], reflectTime, eatTime);
        threads[N-1] = new Thread(philosophers[N-1]);
    }

    void start() {
        for (int i = 0; i < N; i++) {
            threads[i].start();
        }
    }

    void stop() {
        for (int i = 0; i < N; i++) {
            threads[i].interrupt();
        }
    }

    void print() {
        for (int i = 0; i < N; i++) {
            System.out.println(philosophers[i].getInfo());
        }
    }

    void test() {
        try {
            start();
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop();
        print();
    }

    public static void main(String[] args) {
        Simposion s1 = new Simposion(1100, 600);
        //Simposion s2 = new Simposion(600, 1100);
        s1.test();
        //s2.test();
    }
}
