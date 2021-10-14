package ru.progwards.java2.lessons.patterns;

public class StatisticInfo implements Comparable<StatisticInfo> {
    public String sectionName; // имя секции
    public long fullTime; // полное время выполнения секции в миллисекундах
    public long selfTime; // чистое время выполнения секции в миллисекундах
    public int count; // количество вызовов

    public StatisticInfo(String name, long ft, long st, int cnt) {
        sectionName = name;
        fullTime = ft;
        selfTime = st;
        count = cnt;
    }

    @Override
    public int compareTo(StatisticInfo o) {
        return sectionName.compareTo(o.sectionName);
    }

    @Override
    public String toString() {
        return "{sectionName = '" + sectionName + '\'' +
                ", fullTime = " + fullTime +
                ", selfTime = " + selfTime +
                ", count = " + count + '}';
    }
}

