package ru.progwards.java1.lessons.maps;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UsageFrequency {
    private String[] words;

    public void processFile(String fileName) {
        words = new String[0];
        int wordsCount = 0;
        try {
            File fn = new File(fileName);
            try (Scanner scan = new Scanner(fn)) {
                while (scan.hasNext()) {
                    String word = scan.next();
                    wordsCount++;
                    String[] temp = new String[words.length];
                    System.arraycopy(words,0,temp,0,words.length);

                    words = new String[wordsCount];
                    System.arraycopy(temp,0,words,0,temp.length);
                    words[wordsCount-1] = word;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<Character, Integer> getLetters() {
        Map<Character, Integer> res = new HashMap();
        for (int i=0; i<words.length; i++) {
            for (int j=0; j<words[i].length(); j++) {
                Character key = words[i].charAt(j);
                if (Character.isLetter(key) || Character.isDigit(key)) {
                    if (res.containsKey(key)) {
                        res.put(key, res.get(key) + 1);
                    } else {
                        res.put(key, 1);
                    }
                }
            }
        }
        return res;
    }

    public void symbol(String key, Map<String, Integer> res) {
        String key2 = "";
        for (char c : key.toCharArray())
            if (Character.isAlphabetic(c) || Character.isDigit(c)) {
                key2 = key2 + c;
            }

        if ("".equals(key2)) {
            return;
        }

        if (res.containsKey(key2)) {
            res.put(key2, res.get(key2) + 1);
        } else {
            res.put(key2, 1);
        }
    }

    public Map<String, Integer> getWords() {
        Map<String, Integer> res = new HashMap();
        for (int i=0; i<words.length; i++) {
            if (words[i].contains("-")) {
                String[] word2 = new String[2];
                int ind = words[i].indexOf("-");
                word2[0] = words[i].substring(0, ind);
                word2[1] = words[i].substring(ind+1);

                symbol(word2[0], res);
                symbol(word2[1], res);
            } else if (words[i].contains("'")) {
                String[] word2 = new String[2];
                int ind = words[i].indexOf("'");
                word2[0] = words[i].substring(0, ind);
                word2[1] = words[i].substring(ind+1);

                symbol(word2[0], res);
                symbol(word2[1], res);
            } else {
                symbol(words[i], res);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        UsageFrequency test = new UsageFrequency();

        test.processFile("D:\\wiki.test.tokens");
        //test.processFile("D:\\wi654ki.test.tokens");

        System.out.println(test.getLetters());
        System.out.println(test.getWords());
    }
}
