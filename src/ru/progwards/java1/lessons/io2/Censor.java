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

    public static void censorFile(String inoutFileName, String[] obscene) throws CensorException {
        try {
            File fn = new File(inoutFileName);
            try (Scanner scan = new Scanner(fn)) { // воспринимал строку пути как просто строку %)
                RandomAccessFile raf = new RandomAccessFile(fn, "rw");
                while (scan.hasNext()) {
                    String word = scan.next();
                    String repl = "";
                    boolean wr = true;
                    // для сравнения выделяем слово без знаков препинания
                    String word2 = "";
                    String prew = ""; // знаки препинания до слова
                    String postw = ""; // знаки препинания после слова
                    for (char c : word.toCharArray())
                        if (Character.isAlphabetic(c)) {
                            word2 = word2 + c;
                        } else if ("".equals(word2)) {
                            prew = prew + c;
                        } else {
                            postw = postw + c;
                        } // слова через дефиз?

                    for (int i = 0; i < obscene.length; i++) {
                        if (word2.equals(obscene[i])) {
                            for (int j = 0; j < obscene[i].length(); j++) {
                                repl = repl + "*";
                            }
                            if (scan.hasNext()) {
                                repl = prew + repl + postw + " ";
                            }
                            raf.write(repl.getBytes());
                            wr = false;
                            break;
                        }
                    }
                    if (wr) {
                        if (scan.hasNext()) {
                            word = word + " ";
                        }
                        raf.write(word.getBytes());
                    }
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
            writer.write("Java — язык программирования, " +
                    "разработанный Sun Microsystems " +
                    "(в последующем приобретённой Oracle).");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String[] obscene = {"Java", "Oracle", "Sun", "Microsystems"};

        try {
            censorFile("D:\\123.txt", obscene);
        } catch (CensorException e) {
            System.out.println(e.toString());
        }
    }
}
