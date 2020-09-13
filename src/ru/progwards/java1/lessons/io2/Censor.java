package ru.progwards.java1.lessons.io2;

import java.io.FileWriter;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Censor {
    static class CensorException extends Throwable {
        String mExc;
        String fName;

        CensorException(String mExc, String fName) {
            this.mExc = mExc;
            this.fName = fName;
        }

        @Override
        public String toString() {
            return "<"+fName+">:<"+mExc+">";
        }
    }

    public static void censorFile(String inoutFileName, String[] obscene) throws CensorException {
        File fn = new File(inoutFileName);
        try (Scanner scanner = new Scanner(fn)) {
            RandomAccessFile raf = new RandomAccessFile(fn, "rw");
            while (scanner.hasNext()) {
                String word = scanner.next();
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
                    }

                for (int i = 0; i < obscene.length; i++) {
                    if (word2.equals(obscene[i])) {
                        for (int j = 0; j < obscene[i].length(); j++) {
                            repl = repl + "*";
                        }
                        repl = prew + repl + postw + " ";
                        raf.write(repl.getBytes());
                        wr = false;
                        break;
                    }
                }
                if (wr) {
                    word = word + " "; // записываем со знаками препинания
                    raf.write(word.getBytes());
                }
            }
        } catch (Throwable e) {
            throw new CensorException(e.getMessage(), inoutFileName);
        }
    }

    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("D:\\123.txt");
            writer.write("Java — язык программирования, " +
                    "разработанный Sun Microsystems " +
                    "(в последующем приобретённой Oracle). ");
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String[] obscene = {"Java", "Oracle", "Sun", "Microsystems"};

        try {
            censorFile("D:\\1234.txt", obscene);
        } catch (CensorException e) {
            System.out.println(e.toString());
        }
    }
}
