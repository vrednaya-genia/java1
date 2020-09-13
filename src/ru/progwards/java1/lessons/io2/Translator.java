package ru.progwards.java1.lessons.io2;

public class Translator {
    private String[] inLang;
    private String[] outLang;

    Translator(String[] inLang, String[] outLang) {
        this.inLang = inLang;
        this.outLang = outLang;
    }

    public String translate(String sentence) {
        String res = "";
        String[] words = sentence.split("\\s");
        for (int i=0; i<words.length; i++) {
            boolean isUp = Character.isUpperCase(words[i].charAt(0));
            // для поиска выделяем слово без знаков препинания
            // и переводим в нижний регистр
            String word2 = "";
            String prew = ""; // знаки препинания до слова
            String postw = ""; // знаки препинания после слова
            for (char c : words[i].toCharArray())
                if (Character.isAlphabetic(c)) {
                    word2 = word2 + Character.toLowerCase(c);
                } else if ("".equals(word2)) {
                    prew = prew + c;
                } else {
                    postw = postw + c;
                } // слова с дефизом %)

            for (int j=0; j<inLang.length; j++) {
                if (word2.equals(inLang[j])) {
                    if (isUp) {
                        word2 = outLang[j].substring(0,1).toUpperCase()+outLang[j].substring(1);
                    }
                    res = res + prew + word2 + postw + " ";
                    break;
                }
            }
        }

        res = res.substring(0,res.length()-1);
        return res;
    }

    public static void main(String[] args) {
        String[] ain = {"hello", "world"};
        String[] aout = {"привет", "мир"};
        Translator aa = new Translator(ain, aout);
        System.out.println(aa.translate("Hello World!"));
    }
}
