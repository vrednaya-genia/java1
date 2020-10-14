package ru.progwards.java1.lessons.datetime;

public class StatisticInfo implements Comparable<StatisticInfo> {
    public String sectionName; // имя секции
    public int fullTime; // полное время выполнения секции в миллисекундах
    public int selfTime; // чистое время выполнения секции в миллисекундах
    public int count; // количество вызовов

    StatisticInfo(String name, int ft, int st, int cnt) {
        sectionName = name;
        fullTime = ft;
        selfTime = st;
        count = cnt;
    }

    @Override
    public int compareTo(StatisticInfo o) {
        return sectionName.compareTo(o.sectionName);
    }
}

