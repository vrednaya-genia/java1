package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

public class OrderProcessor {
    Path pathStorage;
    int err;
    List<Order> data = new ArrayList<>();

    // инициализирует класс, с указанием начальной папки для хранения файлов
    public OrderProcessor(String startPath) {
        pathStorage = Paths.get(startPath);
    }

    public boolean isInPeriod(LocalDate start, LocalDate finish, LocalDate one) {
        if (start==null && finish==null) {
            return true;
        }
        if (start==null && (one.isEqual(finish) || one.isBefore(finish))) {
            return true;
        }
        if (finish==null && (one.isEqual(start) || one.isAfter(start))) {
            return true;
        }
        return (one.isEqual(start) || one.isAfter(start)) && (one.isEqual(finish) || one.isBefore(finish));
    }

    public Order fileProcess(Path path) {
        Order res = new Order();
        String fileName = path.getFileName().toString();
        res.shopId = fileName.substring(0, 3);
        res.orderId = fileName.substring(4, 10);
        res.customerId = fileName.substring(11, 15);
        try {
            FileTime lmt = Files.getLastModifiedTime(path);
            ZonedDateTime zdt = ZonedDateTime.parse(lmt.toString());
            res.datetime = zdt.toLocalDateTime();
        } catch (IOException e) {
            System.out.println(e.getMessage() + " ошибка при чтении атрибута:LastModifiedTime");
            return null;
        }

        res.items = null;
        res.sum = 0;
        try {
            List<String> fileLines = Files.readAllLines(path);
            res.items = new ArrayList<>();
            Iterator<String> it = fileLines.iterator();
            while (it.hasNext()) {
                String[] item = it.next().split(",");
                try {
                    OrderItem one = new OrderItem(item[0], Integer.parseInt(item[1]), Double.parseDouble(item[2]));
                    res.items.add(one);
                    res.sum += one.price * one.count;
                } catch (Exception e) {
                    System.out.println(e.getMessage() + " ошибка в структуре файла");
                    return null;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " ошибка при чтении файла");
            return null;
        }

        return res;
    }

    public int loadOrders(LocalDate start, LocalDate finish, String shopId) {
        err = 0;
        String pattern = shopId; // if shopId.length()!=3 files will be not found
        if (shopId==null) {
            pattern = "???";
        }

        PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:**/"+pattern+"-??????-????.csv");
        try {
            Files.walkFileTree(pathStorage, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path p, BasicFileAttributes attrs) {
                    if (pm.matches(p)) {
                        Order one = fileProcess(p);
                        if (one!=null) {
                            if (isInPeriod(start, finish, one.datetime.toLocalDate())) {
                                data.add(one);
                            }
                        } else {
                            err++;
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFileFailed(Path p, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return err;
    }// ??? При этом, если в классе содержалась информация, ее надо удалить

    public List<Order> process(String shopId) {
        List<Order> res = new ArrayList<>();
        if (shopId==null) {
            res.addAll(data);
        } else {
            Iterator<Order> it = data.iterator();
            while (it.hasNext()) {
                Order one = it.next();
                if (one.shopId.equals(shopId)) {
                    res.add(one);
                }
            }
        }
        res.sort(new Comparator<>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.datetime.compareTo(o2.datetime);
            }
        });
        return res;
    }

    public Map<String, Double> statisticsByShop() {
        Map<String, Double> res = new TreeMap<>();
        Iterator<Order> it = data.iterator();
        while (it.hasNext()) {
            Order one = it.next();
            if (!res.isEmpty() && res.containsKey(one.shopId)) {
                res.put(one.shopId, res.get(one.shopId) + one.sum);
            } else {
                res.put(one.shopId, one.sum);
            }
        }
        return res;
    }

    public Map<String, Double> statisticsByGoods(){
        Map<String, Double> res = new TreeMap<>();
        Iterator<Order> it = data.iterator();
        while (it.hasNext()) {
            Order one = it.next();
            Iterator<OrderItem> it2 = one.items.iterator();
            while (it2.hasNext()) {
                OrderItem one2 = it2.next();
                if (!res.isEmpty() && res.containsKey(one2.goodsName)) {
                    res.put(one2.goodsName, res.get(one2.goodsName) + one2.price * one2.count);
                } else {
                    res.put(one2.goodsName, one2.price * one2.count);
                }
            }
        }
        return res;
    }

    public Map<LocalDate, Double> statisticsByDay() {
        Map<LocalDate, Double> res = new TreeMap<>();
        Iterator<Order> it = data.iterator();
        while (it.hasNext()) {
            Order one = it.next();
            LocalDate oneDate = one.datetime.toLocalDate();
            if (!res.isEmpty() && res.containsKey(oneDate)) {
                res.put(oneDate, res.get(oneDate) + one.sum);
            } else {
                res.put(oneDate, one.sum);
            }
        }
        return res;
    }
}
