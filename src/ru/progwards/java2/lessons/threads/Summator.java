package ru.progwards.java2.lessons.threads;

import java.math.BigInteger;

class MyThread extends Thread {
    int n;
    int m;

    MyThread(int n, int m) {
        this.n = n;
        this.m = m;
    }

    @Override
    public void run() {
        for (int i=n+1; i<=m; i++) {
            n = n + i;
        }
    }
}

public class Summator {
    int count;
    MyThread[] threads;

    Summator(int count) {
        this.count = count;
        threads = new MyThread[count];
    }

    public BigInteger sum(BigInteger number) {
        BigInteger c = new BigInteger(String.valueOf(count));
        BigInteger d = number.divide(c);
        BigInteger mod = number.mod(c);
        BigInteger n = BigInteger.ONE;
        BigInteger m = d;

        for (int i=1; i<count+1; i++) {
            threads[i] = new MyThread(n.intValue(), m.intValue());
            n = n.add(d);
            m = m.add(d);
            //if (mod )
        }

        return null;
    }

    public static void main(String[] args) {
        Summator summator = new Summator(3);
        System.out.println(summator.sum(BigInteger.valueOf(1_000)));
    }
    /*
    Реализовать класс Summator
    который суммирует все числа от 1 до number в несколько потоков.
    Например для числа 5 должно быть просуммировано 1+2+3+4+5

1.1 Конструктор Summator(int count) -
инициализирует класс,
с указанием в какое количество потоков надо будет проводить суммирование, count - количество потоков.

1.2 Метод public BigInteger sum(BigInteger number) -
который, собственно и запускает потоки выполняющие суммирование,
number - число, до которого надо просуммировать числа.
Для этого нужно будет разбить весь диапазон суммируемых чисел на блоки равного размера,
по количеству потоков. Каждому потоку выдать блок для суммирования от n...m.
Например, если мы суммируем 1000 в 3 потока,
то первому достанется от 1 до 333 второму от 334 до 666,
третьему от 667 до 1000.
После чего результат суммирования каждого
блока нужно будет инкрементировать в общую сумму и вернуть как результат метода.
     */

}
