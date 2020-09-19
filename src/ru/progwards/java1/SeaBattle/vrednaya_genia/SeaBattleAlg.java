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
        if (x==-1 || y==-1) {
            return false;
        }
        if (x<field.length && y<field[0].length) {
            return true;
        } else {
            return false;
        }
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

    public void toDestroyed(int x, int y, SeaBattle seaBattle) {
        SeaBattle.FireResult fireResult;
        if (paluba>1) {
            if (hits[paluba-2][1]==hits[paluba-1][1]) { // vert
                fireResult = seaBattle.fire(x + 1, y);
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

            if (hits[paluba-2][0]==hits[paluba-1][0]) { // gorz
                fireResult = seaBattle.fire(x, y+1);
                if (fireResult == SeaBattle.FireResult.HIT) {
                    setDots(x, y+1);
                    newPaluba(x, y+1);
                    toDestroyed(x, y+1, seaBattle);
                }
                if (fireResult == SeaBattle.FireResult.DESTROYED) {
                    setDots(x, y+1);
                    return;
                }
            }
        } else {
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
        }
    }

    public void battleAlgorithm(SeaBattle seaBattle) {
        for (int y = 0; y < seaBattle.getSizeX(); y++) {
            for (int x = 0; x < seaBattle.getSizeY(); x++) {
                if (field[x][y] == 'X') {
                    continue;
                }
                if (field[x][y] != '.') {
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
                    if (ships == 10)
                        return;
                }
            }
        }
    }

    // функция для отладки
    static void test() {
        System.out.println("Sea battle");
        SeaBattle seaBattle = new SeaBattle();
        SeaBattleAlg t = new SeaBattleAlg();
        t.init(new SeaBattle());
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
            alg.init(new SeaBattle());
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

