package ru.progwards.java2.lessons.recursion;

import java.util.ArrayList;
import java.util.List;

public class HanoiTower {
    private List<List<Integer>> towers;
    private int start;
    private int height;
    private boolean printOn = false;

    // инициализирует башню с size (size<10) кольцами (1..size). pos - номер начального штыря (0,1,2)
    public HanoiTower(int size, int pos) {
        towers = new ArrayList<>();
        start = pos;
        height = size;
        for (int i=0; i<=2; i++) {
            towers.add(new ArrayList<>());
            if (i==pos) {
                List<Integer> listI = towers.get(i);
                for (int j=0; j<size; j++) {
                    listI.add(size - j);
                }
            }
        }
    }

    boolean change(int from, int to) {
        Integer fromSize;
        Integer toSize;

        if (towers.get(from).size()!=0) {
            fromSize = towers.get(from).get(towers.get(from).size()-1);
        } else {
            fromSize = 10;
        }

        if (towers.get(to).size()!=0) {
            toSize = towers.get(to).get(towers.get(to).size() - 1);
        } else {
            toSize = 10;
        }

        if (fromSize<toSize) {
            towers.get(from).remove(fromSize);
            towers.get(to).add(fromSize);
            return true;
        }

        return false;
    }

    boolean isMoved(int to) {
        return towers.get(to).size()==height;
    }

    // переносит башню со штыря from на штырь to
    public void move(int from, int to) {
        // печать текущего положения
        if (printOn) {
            print();
        }

        change(from, to);

        if (isMoved(to)) {
            return;
        }

        print();
        //move();
    }

    void print() {
        String[] printArr = new String[height];
        for (int i = 0; i< height; i++) {
            printArr[i] = "";
        }

        int h = height;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j <= 2; j++) {
                if (towers.get(j).size() > h-1) {
                    printArr[i] = printArr[i] + "<00" + towers.get(j).get(h-1) + "> ";
                } else {
                    printArr[i] = printArr[i] + "  I   ";
                }
            }
            h--;
        }

        for (int i = 0; i< height; i++) {
            System.out.println(printArr[i]);
        }
        System.out.println("=================");
    }
//    каждое кольцо 5 символов - 3 символа добитых слева нулями, края <>
//    пустой штырь - буква I (латинское И большое) - остальное пробелы
//    между краями колец - один пробел
//    высота всегда == size
//    основание - символ "=", 17 шт

    void setTrace(boolean on) {
        printOn = on;
    }

    public static void main(String[] args) {
        HanoiTower h = new HanoiTower(3, 0);
        h.setTrace(true);
        h.move(0, 2);
    }
}
