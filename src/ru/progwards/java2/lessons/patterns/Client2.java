package ru.progwards.java2.lessons.patterns;

import java.util.Random;

public class Client2 {
    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for (int i =0; i<threads.length; i++) {
            threads[i] = new Thread(() -> {
                Random random = new Random();
                int next;
                AbsInteger newInteger;
                for (int j = 0; j<10; j++) {
                    next = random.nextInt();
                    AbsIntegerFactory.INSTANCE.setNumber(next);
                    newInteger = AbsIntegerFactory.INSTANCE.getNumber();
                    System.out.println("Число " + next + " и класс " + newInteger.getClass());

                    next = random.nextInt(128);
                    AbsIntegerFactory.INSTANCE.setNumber(next);
                    newInteger = AbsIntegerFactory.INSTANCE.getNumber();
                    System.out.println("Число " + next + " и класс " + newInteger.getClass());

                    next = random.nextInt(Short.MAX_VALUE);
                    AbsIntegerFactory.INSTANCE.setNumber(next);
                    newInteger = AbsIntegerFactory.INSTANCE.getNumber();
                    System.out.println("Число " + next + " и класс " + newInteger.getClass());
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
