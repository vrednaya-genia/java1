package ru.progwards.java2.lessons.patterns;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

public final class Profiler {
    private static volatile Profiler instance;

    private final Map<String, StatisticInfo> sections = new ConcurrentHashMap<>(); // <ИмяСекции, ОбщаяИнформация>
    private final Map<String, Long> starts = new ConcurrentHashMap<>();            // <ИмяСекции, Старт>
    private final Map<String, Integer> counts = new ConcurrentHashMap<>();         // <ИмяСекции, КоличествоВходов>
    private final Deque<String> qSections = new LinkedBlockingDeque<>();

    private Profiler() {

    }

    public static Profiler getInstance() {
        if (instance == null) {
            synchronized (Profiler.class) {
                if (instance == null) {
                    instance = new Profiler();
                }
            }
        }
        return instance;
    }

    public void enterSection(String name) {
        if (starts.isEmpty() || !starts.containsKey(name)) {
            counts.put(name, 1);
            qSections.push(name);
        } else {
            counts.put(name, counts.get(name)+1);
            if (!name.equals(qSections.peek())) {
                qSections.push(name);
            }
        }
        starts.put(name, System.currentTimeMillis());
    }

    public void exitSection(String name) {
        long fullTime = System.currentTimeMillis() - starts.get(name);
        long selfTime = fullTime;
        long nestTime = 0;
        boolean isNested = false;
        // учет вложенной секции
        if (!name.equals(qSections.peek())) {
            isNested = true;
            StatisticInfo nest = sections.get(qSections.pop());
            nestTime = nest.fullTime;
        }
        if (isNested) {
            if (sections.containsKey(name)) {
                StatisticInfo val = sections.get(name);
                fullTime += val.fullTime;
                selfTime = fullTime - nestTime;
            } else {
                selfTime = selfTime - nestTime;
            }
        } else {
            if (!sections.isEmpty() && sections.containsKey(name)) {
                StatisticInfo val = sections.get(name);
                fullTime += val.fullTime;
                selfTime += val.selfTime;
            }
        }
        StatisticInfo newSI = new StatisticInfo(name, fullTime, selfTime, counts.get(name));
        sections.put(name, newSI);
    }

    public List<StatisticInfo> getStatisticInfo() {
        List<StatisticInfo> res = new ArrayList<>(instance.sections.values());
        return res.stream().sorted().collect(Collectors.toList());
    }

    public static void main(String[] args) throws InterruptedException {
        Profiler t = Profiler.getInstance();

        t.enterSection("Process1");
        Thread.sleep(100);
        t.exitSection("Process1");

        t.enterSection("Process1");
        Thread.sleep(100);
            t.enterSection("Process2");
            Thread.sleep(200);
            for (int i = 0; i < 3; i++) {
                t.enterSection("Process3");
                Thread.sleep(100);
                t.exitSection("Process3");
            }
            t.exitSection("Process2");
        t.exitSection("Process1");

        t.getStatisticInfo().forEach(System.out::println);
    }
}
