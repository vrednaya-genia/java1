package ru.progwards.java1.lessons.queues;

import java.util.*;

public class CollectionsSort {
    public static void mySort(Collection<Integer> data) {
        Integer[] a = new Integer[data.size()];
        data.toArray(a);
        for (int i=0; i<a.length; i++) {
            for (int j=i+1; j<a.length; j++) {
                if (a[i]>a[j]) {
                    int a1 = a[i];
                    a[i] = a[j];
                    a[j] = a1;
                }
            }
        }
        data.clear();
        //data.addAll(Arrays.asList(a)); // неизменяемый объект
        for (int i=0; i<a.length; i++) {
            data.add(a[i]);
        }
    }

    public static void minSort(Collection<Integer> data) {
        Collection<Integer> res = new ArrayList();
        while (!data.isEmpty()) {
            Integer i1 = Collections.min(data);
            data.remove(i1);
            res.add(i1);
        }
        data.addAll(res);
    }

    static void collSort(Collection<Integer> data) {
        List<Integer> res = new ArrayList(data);
        Collections.sort(res);
        data.clear();
        data.addAll(res);
    }

    static class SortInfo implements Comparable<SortInfo> {
        String name;
        long time;

        SortInfo(String name, long time) {
            this.name = name;
            this.time = time;
        }

        @Override
        public int compareTo(SortInfo o) {
            if (this.time>o.time) {
                return 1;
            }
            if (this.time<o.time) {
                return -1;
            }
            // если одинаковое время
            if (this.name.charAt(0)>o.name.charAt(0)) {
                return 1;
            }
            if (this.name.charAt(0)<o.name.charAt(0)) {
                return -1;
            }
            // если начинаются с одной буквы
            if (this.name.charAt(1)>o.name.charAt(1)) {
                return 1;
            }
            if (this.name.charAt(1)<o.name.charAt(1)) {
                return -1;
            }

            return 0;
        }
    }

    public static Collection<String> compareSort() {
        Collection<Integer> data = new ArrayList();
        for (int i=0; i<1000; i++) {
            Integer itr;
            if (i%2==0) {
                itr = i;
            } else {
                itr = i*3;
            }
            data.add(itr);
        }
        long start = System.currentTimeMillis();
        mySort(data);
        long end = System.currentTimeMillis()-start;
        SortInfo my = new SortInfo("mySort", end);

        data.clear();
        for (int i=0; i<1000; i++) {
            Integer itr;
            if (i%2==0) {
                itr = i;
            } else {
                itr = i*3;
            }
            data.add(itr);
        }
        start = System.currentTimeMillis();
        minSort(data);
        end = System.currentTimeMillis() - start;
        SortInfo min = new SortInfo("minSort", end);

        data.clear();
        for (int i=0; i<1000; i++) {
            Integer itr;
            if (i%2==0) {
                itr = i;
            } else {
                itr = i*3;
            }
            data.add(itr);
        }
        start = System.currentTimeMillis();
        collSort(data);
        end = System.currentTimeMillis() - start;
        SortInfo coll = new SortInfo("collSort", end);

        Queue<SortInfo> t = new PriorityQueue();
        t.add(my);
        t.add(min);
        t.add(coll);

        Collection<String> res = new ArrayList();
        Iterator<SortInfo> iter = t.iterator();
        while (iter.hasNext()) {
            SortInfo isort = t.poll();
            res.add(isort.name);
        }

//        System.out.println("my " + my.time);
//        System.out.println("min " + min.time);
//        System.out.println("coll " + coll.time);

        return res;
    }

    public static void main(String[] args) {
        System.out.println(compareSort());
    }
}
