package ru.progwards.java1.lessons.datetime;

import java.util.*;

public class Profiler {
    static Map<String, StatisticInfo> Sections = new HashMap<>();
    static Map<String, Long> startTime = new HashMap<>(); // <ИмяСекции, Старт>
    static Map<String, Integer> counts = new HashMap<>(); // <ИмяСекции, КоличествоВходов>
    static Deque<String> qSections = new ArrayDeque<>();

    public static void enterSection(String name) {
        if (startTime.size()==0 || !startTime.containsKey(name)) {
            startTime.put(name, System.currentTimeMillis());
            counts.put(name, 1);
            qSections.addFirst(name);
        } else {
            startTime.put(name, System.currentTimeMillis());
            counts.put(name, counts.get(name)+1);
            if (!name.equals(qSections.peekFirst())) {
                qSections.addFirst(name);
            }
        }
    }

    public static void exitSection(String name) {
        boolean isNested = false;
        StatisticInfo newSI;
        long fullTime = System.currentTimeMillis() - startTime.get(name);
        if (Sections.containsKey(name)) {
            StatisticInfo val = Sections.get(name);
            fullTime = val.fullTime + fullTime;
        }
        long selfTime;
        long prevTime = 0;
        while (!name.equals(qSections.peekFirst())) {
            isNested = true;
            String prevname = qSections.pollFirst();
            StatisticInfo prev = Sections.get(prevname);
            prevTime = prevTime + prev.fullTime;
        }
        selfTime = fullTime - prevTime;
        if (Sections.containsKey(name) || !isNested) {
            StatisticInfo val = Sections.get(name);
            selfTime = val.selfTime + selfTime;
        }

        newSI = new StatisticInfo(name, (int) fullTime, (int) selfTime, counts.get(name));
        Sections.put(name, newSI);
    }

    public static List<StatisticInfo> getStatisticInfo() {
        Collection<StatisticInfo> temp = Sections.values();
        List<StatisticInfo> res = new ArrayList<>(temp);
        res.sort(StatisticInfo::compareTo);
        return res;
    }
}
