package ru.progwards.java1.lessons.queues;

public class Order implements Comparable<Order> {
    static int auto = 1;
    private double sum;
    private int num;
    private int cl;

    public Order(double sum) {
        this.sum = sum;
        if (sum>=20000.0) {
            this.cl = 3;
        } else if (sum>=10000.0) {
            this.cl = 2;
        } else {
            this.cl = 1;
        }
        num = auto;
        auto++;
    }

    public double getSum() {
        return sum;
    }

    public int getNum() {
        return num;
    }

    @Override
    public int compareTo(Order o) {
        if (this.cl>o.cl) {
            return -1;
        }
        if (this.cl<o.cl) {
            return 1;
        }
        // если одного класса
        if (this.num>o.num) {
            return 1;
        }
        if (this.num<o.num) {
            return -1;
        }

        return 0;
    }
}
