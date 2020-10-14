package ru.progwards.java1.lessons.datetime;

import java.util.*;

public class Profiler {
    static Map<String, StatisticInfo> Sections = new HashMap<>();
    static Map<String, Long> startTime = new HashMap<>(); // <ИмяСекции, Старт>
    static Map<String, Integer> counts = new HashMap<>(); // <ИмяСекции, КоличествоВходов>
    static Deque<String> qSections = new ArrayDeque<>();

    public static void enterSection(String name) {
        if (startTime.size()==0 || !startTime.containsKey(name)) {
            counts.put(name, 1);
            qSections.addFirst(name);
        } else {
            counts.put(name, counts.get(name)+1);
            if (!name.equals(qSections.peekFirst())) {
                qSections.addFirst(name);
            }
        }
        startTime.put(name, System.currentTimeMillis());
    }

    public static void exitSection(String name) {
        long fullTime = System.currentTimeMillis() - startTime.get(name);
        long selfTime = fullTime;
        long prevTime = 0;
        boolean isNested = false;
        while (!name.equals(qSections.peekFirst())) {
            isNested = true;
            String prevname = qSections.pollFirst();
            StatisticInfo prev = Sections.get(prevname);
            prevTime = prevTime + prev.fullTime;
        }
        if (isNested) {
            if (!Sections.isEmpty() && Sections.containsKey(name)) {
                StatisticInfo val = Sections.get(name);
                fullTime += val.fullTime;
                selfTime = fullTime - prevTime;
            } else {
                selfTime = selfTime - prevTime;
            }
        } else {
            if (!Sections.isEmpty() && Sections.containsKey(name)) {
                StatisticInfo val = Sections.get(name);
                fullTime += val.fullTime;
                selfTime += val.selfTime;
            }
        }
        StatisticInfo newSI = new StatisticInfo(name, (int) fullTime, (int) selfTime, counts.get(name));
        Sections.put(name, newSI);
    }

    public static List<StatisticInfo> getStatisticInfo() {
        Collection<StatisticInfo> temp = Sections.values();
        List<StatisticInfo> res = new ArrayList<>(temp);
        res.sort(StatisticInfo::compareTo);
        return res;
    }

    public static void main(String[] args) {
        Profiler t = new Profiler();

        enterSection("Process1");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        exitSection("Process1");
        enterSection("Process1");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        exitSection("Process1");
        enterSection("Process1");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        exitSection("Process1");
        enterSection("Process1");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        enterSection("Process2");
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        enterSection("Process3");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        exitSection("Process3");
        exitSection("Process2");
        enterSection("Process2");
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        enterSection("Process3");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        exitSection("Process3");
        exitSection("Process2");
        exitSection("Process1");

        List<StatisticInfo> res = getStatisticInfo();
        Iterator<StatisticInfo> it = res.iterator();
        while (it.hasNext()) {
            StatisticInfo tt = it.next();
            System.out.println(tt.sectionName);
            System.out.println(tt.fullTime);
            System.out.println(tt.selfTime);
            System.out.println(tt.count);
        }
    }
}
