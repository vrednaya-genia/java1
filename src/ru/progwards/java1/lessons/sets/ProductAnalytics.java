package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ProductAnalytics {
    private List<Shop> shops; // список магазинов
    private List<Product> products; // могут быть и товары, которых нет в магазинах

    public ProductAnalytics(List<Product> products, List<Shop> shops) {
        this.products = products;
        this.shops = shops;
    }

    // товары из products, которые имеются во всех магазинах
    // пересечение товаров из всех магазинов
    public Set<Product> existInAll() {
        Set<Product> res = new HashSet();
        Iterator<Shop> ish1 = shops.iterator();
        Iterator<Shop> ish2 = shops.iterator();

        Shop sp1 = ish1.next();
        Set<Product> sp1set = new HashSet(sp1.getProducts());
        while (ish2.hasNext()) {
            Shop sp2 = ish2.next();
            Set<Product> sp2set = new HashSet(sp2.getProducts());
            sp1set.retainAll(sp2set);
            if (sp1set.isEmpty()) {
                break;
            }
        }
        if (!sp1set.isEmpty()) {
            Iterator<Product> ipr = sp1set.iterator();
            while (ipr.hasNext()) {
                res.add(ipr.next());
            }
        }

        return res;
    }

    // товары из products, которые имеются хотя бы в одном магазине
    // объединение всех продуктов из всех магазинов
    public Set<Product> existAtListInOne() {
        Set<Product> res = new HashSet();
        Iterator<Shop> ish = shops.iterator();
        while (ish.hasNext()) {
            Shop sp = ish.next();
            Iterator<Product> ipr = sp.getProducts().iterator();
            while (ipr.hasNext()) {
                res.add(ipr.next());
            }
        }
        return res;
    }

    // товары из products, которых нет ни в одном магазине
    // разница общего перечня продуктов с объединением из магазинов
    public Set<Product> notExistInShops() {
        Set<Product> res = new HashSet(products);
        res.removeAll(existAtListInOne());
        return res;
    }

    // товары из products, которые есть только в одном магазине
    // симметричная разница товаров из всех магазинов
    public Set<Product> existOnlyInOne() { // нужно всегда минимум 2 магазина
        Set<Product> res = new HashSet(existAtListInOne());
        Set<Product> retain = new HashSet();
        Iterator<Shop> ish1 = shops.iterator();

        Shop sp1 = ish1.next();
        Set<Product> sp1set = new HashSet(sp1.getProducts());
        Shop sp2 = ish1.next();
        Set<Product> sp2set = new HashSet(sp2.getProducts());

        sp2set.retainAll(sp1set);
        retain.addAll(sp2set);

        while (ish1.hasNext()) {
            Shop spn = ish1.next();
            Set<Product> spnset = new HashSet(spn.getProducts());
            spnset.retainAll(res);
            retain.addAll(spnset);
        }

        res.removeAll(retain);
        return res;
    }

}
