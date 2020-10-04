package ru.progwards.java1.lessons.maps;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class SalesInfo {
    ArrayList<String> fio = new ArrayList();
    ArrayList<String> names = new ArrayList();
    ArrayList<Integer> counts = new ArrayList();
    ArrayList<Double> sums = new ArrayList();

    public int loadOrders(String fileName) {
        // ФИ покупателя, наименование товара, количество, сумма
        // String, String, int, double
        int count = 0;
        try {
            File fn = new File(fileName);
            try (Scanner scan = new Scanner(fn).useDelimiter("\\n")) {
                while (scan.hasNext()) {
                    String order = scan.next();
                    String[] data = order.split(",");
                    if (data.length!=4) {
                        continue;
                    }
                    try {
                        int data2 = Integer.parseInt(data[2].trim());
                        double data3 = Double.parseDouble(data[3]);;
                        fio.add(data[0]);
                        names.add(data[1]);
                        counts.add(data2);
                        sums.add(data3);
                    } catch (Exception e) {
                        continue;
                    }
                    count++;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    // вернуть список товаров, отсортированный по наименованию товара.
    // В String - наименование товара, в Double - общая сумма продаж по товару
    public Map<String, Double> getGoods() {
        Map<String, Double> res = new TreeMap();
        for (int i=0; i<names.size(); i++) {
            String key = names.get(i);
            if (res.containsKey(key)) {
                res.put(key, res.get(key) + sums.get(i));
            } else {
                res.put(key, sums.get(i));
            }
        }
        return res;
    }

    // вернуть список покупателей, отсортированный по алфавиту.
    // В String - ФИ, в Double - сумма всех покупок покупателя, в Integer - количество покупок
    public Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers() {
        Map<String, AbstractMap.SimpleEntry<Double, Integer>> res = new TreeMap();
        for (int i=0; i<fio.size(); i++) {
            String key = fio.get(i);
            if (res.containsKey(key)) {
                AbstractMap.SimpleEntry<Double, Integer> entry = res.get(key);
                Double sum = entry.getKey() + sums.get(i);
                Integer count = entry.getValue() + counts.get(i);

                AbstractMap.SimpleEntry<Double, Integer> newentry = new AbstractMap.SimpleEntry(sum, count);
                res.put(key, newentry);
            } else {
                AbstractMap.SimpleEntry<Double, Integer> entry = new AbstractMap.SimpleEntry(sums.get(i), counts.get(i));
                res.put(key, entry);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("D:\\1234.csv")) {
            writer.write("Иванов Сергей, iPhone 10X, 2, 150000\n");
            writer.write("Петрова Анна, наушники JBL, 2, 7000\n");
            writer.write("Иванов Сергей, чехол для iPhone, 1, 1000\n");
            writer.write("Петрова Анна, пакет пластиковый, 1, 3\n");
            writer.write("Радж Кумар, батарейка ААА, 1, 150\n");
            writer.write("Михаил Цикло, iPhone 10X, 1, 75000\n");
            writer.write("Радж Кумар, пакет пластиковый, 1, 3\n");
            writer.write("Михаил Цикло, пакет пластиковый, 1, 3\n");
            writer.write("Иванов Сергей, стекло защитное, 1, 1000\n");
            writer.write("Василий Пупкин, спички, 10, 10\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        SalesInfo si = new SalesInfo();
        try {
            System.out.println(si.loadOrders("D:\\1234.csv"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(si.getGoods());
        System.out.println(si.getCustomers());
    }
}
