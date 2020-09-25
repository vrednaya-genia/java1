package ru.progwards.java1.lessons.queues;

public class Calculate {
    public static double calculation1() {
        // 2.2*(3+12.1)
        StackCalc sc = new StackCalc();
        sc.push(3.0);
        sc.push(12.1);
        sc.add();
        sc.push(2.2);
        sc.mul();
        return sc.pop();
    }

    public static double calculation2() {
        // (737.22+24)/(55.6-12.1)+(19-3.33)*(87+2*(13.001-9.2))
        StackCalc sc = new StackCalc();
        sc.push(13.001);
        sc.push(9.2);
        sc.sub();
        sc.push(2.0);
        sc.mul();
        sc.push(87.0);
        sc.add();
        sc.push(19.0);
        sc.push(3.33);
        sc.sub();
        sc.mul();
        sc.push(737.22);
        sc.push(24.0);
        sc.add();
        sc.push(55.6);
        sc.push(12.1);
        sc.sub();
        sc.div();
        sc.add();
        return sc.pop();
    }
}
