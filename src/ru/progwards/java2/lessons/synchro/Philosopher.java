package ru.progwards.java2.lessons.synchro;

public class Philosopher implements Runnable {
    private static final int dt = 500; // дискретность вывода на консоль (мс)
    private final String name;
    private final Fork right;
    private final Fork left;
    private final long reflectTime;
    private final long eatTime;
    private long reflectSum;
    private long eatSum;

    public Philosopher(String name, Fork right, Fork left, long reflectTime, long eatTime) {
        this.name = name;
        this.right = right;
        this.left = left;
        this.reflectTime = reflectTime;
        this.eatTime = eatTime;
        reflectSum = 0L;
        eatSum = 0L;
    }

    public void reflect() throws InterruptedException {
        long reflecting = 0L;
        while (reflecting + dt < reflectTime) {
            System.out.println("размышляет " + name);
            Thread.sleep(dt);
            reflecting = reflecting + dt;
        }
        Thread.sleep(reflectTime - reflecting);
        reflectSum = reflectSum + reflectTime;
    }

    public void eat() throws InterruptedException {
        if (right.getState() == Fork.State.FREE) {
            right.setState(Fork.State.BUSY);
            if (left.getState() == Fork.State.FREE) {
                left.setState(Fork.State.BUSY);

                long eating = 0L;
                while (eating + dt < eatTime) {
                    System.out.println("ест " + name);
                    Thread.sleep(dt);
                    eating = eating + dt;
                }
                Thread.sleep(eatTime - eating);
                eatSum = eatSum + eatTime;

                right.setState(Fork.State.FREE);
                left.setState(Fork.State.FREE);
            } else {
                right.setState(Fork.State.FREE);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            if (Thread.interrupted()) {
                return;
            }
            try {
                eat();
                reflect();
            } catch(InterruptedException e){
                return;
            }
        }
    }

    public String getInfo() {
        return "Философ " + name + " размышлял " + reflectSum + ", ел " + eatSum;
    }
}