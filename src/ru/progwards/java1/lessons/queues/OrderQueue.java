package ru.progwards.java1.lessons.queues;

import java.util.PriorityQueue;

public class OrderQueue {
    private PriorityQueue<Order> qOrder = new PriorityQueue<>();

    public void add(Order order) {
        qOrder.add(order);
    }

    public Order get() {
        return qOrder.poll();
    }

    public static void main(String[] args) {
        OrderQueue oq = new OrderQueue();

        oq.add(new Order(30000.0));
        oq.add(new Order(11.0));
        oq.add(new Order(15000.0));

        System.out.println(oq.get().getNum());
    }
}
