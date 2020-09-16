package ru.progwards.java1.lessons.io2;

import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Censor {
    static class CensorException extends Exception {
        String mExc;
        String fName;

        CensorException(String mExc, String fName) {
            super(fName+":"+mExc);
            this.mExc = mExc;
            this.fName = fName;
        }

        @Override
        public String toString() {
            return fName+":"+mExc;
        }
    }

    private static String censor(String word, String[] obscene) {
        String symbols = "";
        String prew = ""; // знаки препинания до слова
        String postw = ""; // знаки препинания после слова

        for (char c : word.toCharArray())
            if (Character.isAlphabetic(c)) {
                symbols = symbols + c;
            } else if ("".equals(symbols)) {
                prew = prew + c;
            } else {
                postw = postw + c;
            }

        for (int i = 0; i < obscene.length; i++) {
            if (symbols.equals(obscene[i])) {
                symbols = "";
                for (int j = 0; j < obscene[i].length(); j++) {
                    symbols = symbols + "*";
                }
                break;
            }
        }

        return prew+symbols+postw;
    }

    public static void censorFile(String inoutFileName, String[] obscene) throws CensorException {
        try {
            File fn = new File(inoutFileName);
            try (Scanner scan = new Scanner(fn)) { // воспринимал строку пути как просто строку %)
                RandomAccessFile raf = new RandomAccessFile(fn, "rw");
                while (scan.hasNext()) {
                    String word = scan.next();
                    String res;
                    if (word.contains("-")) {
                        String[] word2 = new String[2];
                        int ind = word.indexOf("-");
                        word2[0] = word.substring(0, ind);
                        word2[1] = word.substring(ind+1);
                        res = censor(word2[0], obscene) + "-" + censor(word2[1], obscene);
                    } else {
                        res = censor(word, obscene);
                    }

                    if (scan.hasNext()) {
                        res = res + " ";
                    }
                    raf.write(res.getBytes());
                }
                raf.close();
            } catch (Exception e) {
                throw new CensorException(e.getMessage(), inoutFileName);
            }
        } catch (Exception e) {
            throw new CensorException(e.getMessage(), inoutFileName);
        }
    }

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("D:\\123.txt")) {
            writer.write("I collect two-sided cards. Java супер)");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String[] obscene = {"Java", "Oracle", "two", "Microsystems"};

        try {
            censorFile("D:\\123.txt", obscene);
        } catch (CensorException e) {
            System.out.println(e.toString());
        }
    }
}
