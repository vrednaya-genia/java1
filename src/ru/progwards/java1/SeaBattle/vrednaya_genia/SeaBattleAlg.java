package ru.progwards.java1.SeaBattle.vrednaya_genia;

import ru.progwards.java1.SeaBattle.SeaBattle;
import java.util.Arrays;

public class SeaBattleAlg {
    // Вид тестового поля:
    //           0 1 2 3 4 5 6 7 8 9    координата x
    //         0|.|.|.|.|.|.|.|X|.|.|
    //         1|.|.|.|.|.|X|.|.|.|.|
    //         2|X|X|.|.|.|.|.|.|.|.|
    //         3|.|.|.|.|.|.|.|X|X|X|
    //         4|.|.|.|.|X|.|.|.|.|.|
    //         5|.|.|.|.|X|.|.|Х|.|.|
    //         6|.|.|.|.|.|.|.|Х|.|X|
    //         7|X|.|X|.|.|.|.|Х|.|X|
    //         8|X|.|.|.|.|.|.|X|.|.|
    //         9|X|.|.|.|X|.|.|.|.|.|

    char[][] field;
    int ships = 0;
    int paluba;
    int[][] hits = new int[4][2];

    public void init(SeaBattle seaBattle) {
        field = new char[seaBattle.getSizeY()][seaBattle.getSizeX()];
        for (int i = 0; i < seaBattle.getSizeY(); i++) {
            Arrays.fill(field[i], ' ');
        }
    }

    public void newHit() {
        for (int i=0; i<hits.length; i++) {
            for (int j=0; j<hits[0].length; j++) {
                hits[i][j] = 0;
            }
        }
    }

    public void newPaluba(int x, int y) {
        hits[paluba][0] = x;
        hits[paluba][1] = y;
        paluba = paluba + 1;
    }

    public boolean isIn(int x, int y) {
        if (x<0 || y<0) return false;
        return x < field.length && y < field[0].length;
    }

    public void setDots(int x, int y) {
        for (int i=-1; i<2; i++) {
            for (int j=-1; j<2; j++) {
                if (isIn(x+i, y+j) && field[x+i][y+j]==' ') {
                    field[x + i][y + j] = '.';
                }
            }
        }
        field[x][y] = 'X';
    }

    public void toD2pals(int x, int y, SeaBattle seaBattle) {
        SeaBattle.FireResult fireResult;
        //1
        if (isIn(x+1, y)) {
            fireResult = seaBattle.fire(x+1, y);
            if (fireResult == SeaBattle.FireResult.HIT) {
                setDots(x+1, y);
                newPaluba(x+1, y);
                toDestroyed(x+1, y, seaBattle);
            }
            if (fireResult == SeaBattle.FireResult.DESTROYED) {
                setDots(x+1, y);
                return;
            }
        }
        //2
        if (isIn(x, y+1)) {
            fireResult = seaBattle.fire(x, y + 1);
            if (fireResult == SeaBattle.FireResult.HIT) {
                setDots(x, y+1);
                newPaluba(x, y+1);
                toDestroyed(x, y + 1, seaBattle);
            }
            if (fireResult == SeaBattle.FireResult.DESTROYED) {
                setDots(x, y+1);
                return;
            }
        }
        //3
        if (isIn(x-1, y)) {
            fireResult = seaBattle.fire(x-1, y);
            if (fireResult == SeaBattle.FireResult.HIT) {
                setDots(x-1, y);
                newPaluba(x-1, y);
                toDestroyed(x-1, y, seaBattle);
            }
            if (fireResult == SeaBattle.FireResult.DESTROYED) {
                setDots(x-1, y);
                return;
            }
        }
        //4
        if (isIn(x, y-1)) {
            fireResult = seaBattle.fire(x, y - 1);
            if (fireResult == SeaBattle.FireResult.HIT) {
                setDots(x, y-1);
                newPaluba(x, y-1);
                toDestroyed(x, y - 1, seaBattle);
            }
            if (fireResult == SeaBattle.FireResult.DESTROYED) {
                setDots(x, y-1);
                return;
            }
        }
    }

    public void toD34pals(int x, int y, SeaBattle seaBattle) {
        SeaBattle.FireResult fireResult;
        if (hits[paluba-2][1]==hits[paluba-1][1]) { // vert
            if (hits[paluba-1][0] - hits[paluba-2][0] > 0) {
                if (isIn(x+1, y)) {
                    fireResult = seaBattle.fire(x + 1, y);
                    if (fireResult == SeaBattle.FireResult.HIT) {
                        setDots(x + 1, y);
                        newPaluba(x + 1, y);
                        toDestroyed(x + 1, y, seaBattle);
                    }
                    if (fireResult == SeaBattle.FireResult.DESTROYED) {
                        setDots(x + 1, y);
                        return;
                    }
                }
            } else {
                if (isIn(x-1, y)) {
                    fireResult = seaBattle.fire(x - 1, y);
                    if (fireResult == SeaBattle.FireResult.HIT) {
                        setDots(x - 1, y);
                        newPaluba(x - 1, y);
                        toDestroyed(x - 1, y, seaBattle);
                    }
                    if (fireResult == SeaBattle.FireResult.DESTROYED) {
                        setDots(x - 1, y);
                        return;
                    }
                }
            }
        }
        if (hits[paluba-2][0]==hits[paluba-1][0]) { // gorz
            if (hits[paluba-1][1] - hits[paluba-2][1] > 0) {
                if (isIn(x, y+1)) {
                    fireResult = seaBattle.fire(x, y + 1);
                    if (fireResult == SeaBattle.FireResult.HIT) {
                        setDots(x, y + 1);
                        newPaluba(x, y + 1);
                        toDestroyed(x, y + 1, seaBattle);
                    }
                    if (fireResult == SeaBattle.FireResult.DESTROYED) {
                        setDots(x, y + 1);
                        return;
                    }
                }
            } else {
                if (isIn(x, y-1)) {
                    fireResult = seaBattle.fire(x, y - 1);
                    if (fireResult == SeaBattle.FireResult.HIT) {
                        setDots(x, y - 1);
                        newPaluba(x, y - 1);
                        toDestroyed(x, y - 1, seaBattle);
                    }
                    if (fireResult == SeaBattle.FireResult.DESTROYED) {
                        setDots(x, y - 1);
                        return;
                    }
                }
            }
        }
    }

    public void toDestroyed(int x, int y, SeaBattle seaBattle) {
        if (paluba==1) {
            toD2pals(x, y, seaBattle);
        } else {
            toD34pals(x, y, seaBattle);
        }
    }

    public void shot(int x, int y, SeaBattle seaBattle) {
        SeaBattle.FireResult fireResult = seaBattle.fire(x, y);
        if (fireResult == SeaBattle.FireResult.MISS) {
            field[x][y] = '*';
        }
        if (fireResult == SeaBattle.FireResult.DESTROYED) {
            setDots(x, y);
            ships++;
        }
        if (fireResult == SeaBattle.FireResult.HIT) {
            setDots(x, y);
            newHit();
            paluba = 0;
            newPaluba(x, y);
            toDestroyed(x, y, seaBattle);
            ships++;
        }
    }

    public void battleAlgorithm(SeaBattle seaBattle) {
        init(seaBattle);
        int x;
        int y;
        for (int d = 3; d < seaBattle.getSizeX(); ) { // 4х палубные
            x=d;
            y=0;
            while (x>=0) {
                if (field[x][y] != '.' && field[x][y] != 'X') {
                    shot(x, y, seaBattle);
                }
                x-=1;
                y+=1;
            }
            d=d+4;
        }
        for (int d = 2; d < seaBattle.getSizeY(); ) {
            x=9;
            y=d;
            while (y<=9) {
                if (field[x][y] != '.' && field[x][y] != 'X') {
                    shot(x, y, seaBattle);
                }
                x--;
                y++;
            }
            d=d+4;
        }
        /////////////////////////////////////////////////////////////////////
        for (int d = 1; d < seaBattle.getSizeX(); ) { // 3x палубные
            x=d;
            y=0;
            while (x>=0) {
                if (field[x][y] != '.' && field[x][y] != 'X') {
                    shot(x, y, seaBattle);
                }
                x-=1;
                y+=1;
            }
            d=d+4;
            if (ships == 10)
                return;
        }
        for (int d = 4; d < seaBattle.getSizeY(); ) {
            x=9;
            y=d;
            while (y<=9) {
                if (field[x][y] != '.' && field[x][y] != 'X') {
                    shot(x, y, seaBattle);
                }
                x--;
                y++;
            }
            d=d+4;
            if (ships == 10)
                return;
        }
        ///////////////////////////////////////////////////////////////////////
        for (int d = 0; d < seaBattle.getSizeX(); ) { // остальные
            x=0;
            y=d;
            while (y>=0) {
                if (field[x][y] != '.' && field[x][y] != 'X') {
                    shot(x, y, seaBattle);
                }
                x++;
                y--;
            }
            d=d+2;
            if (ships == 10)
                return;
        }
        for (int d = 1; d < seaBattle.getSizeY(); ) {
            x=d;
            y=9;
            while (x<=9) {
                if (field[x][y] != '.' && field[x][y] != 'X') {
                    shot(x, y, seaBattle);
                }
                x++;
                y--;
            }
            d=d+2;
            if (ships == 10)
                return;
        }
    }

    // функция для отладки
    static void test() {
        System.out.println("Sea battle");
        SeaBattle seaBattle = new SeaBattle(true);
        SeaBattleAlg t = new SeaBattleAlg();
        t.battleAlgorithm(seaBattle);
        System.out.println(seaBattle.getResult());
        for (int i = 0; i < seaBattle.getSizeY(); i++) {
            System.out.println(t.field[i]);
        }
    }
    static void comp() {
        SeaBattleAlg alg = new SeaBattleAlg();
        int result = 0;
        for (int i = 0; i < 1000; i++) {
            SeaBattle seaBattle = new SeaBattle();
            alg.battleAlgorithm(seaBattle);
            result += seaBattle.getResult();
        }
        System.out.println(result/1000);
    }

    public static void main(String[] args) {
        //test();
        comp();
    }
}

