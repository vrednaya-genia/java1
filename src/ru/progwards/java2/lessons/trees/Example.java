package ru.progwards.java2.lessons.trees;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class Example {
    enum Operation {PUT, DEL} // from AvlTree

    public class BinaryTree<K extends Comparable<K>, V> implements Iterable<K>{
        private static final String KEYEXIST = "Key already exist";
        private static final String KEYNOTEXIST = "Key not exist";

        class TreeLeaf<K extends Comparable<K>, V> {
            K key;
            V value;
            TreeLeaf parent;
            TreeLeaf left;
            TreeLeaf right;

            public TreeLeaf(K key, V value) {
                this.key = key;
                this.value = value;
            }

            private TreeLeaf<K,V> find(K key) {
                int cmp = key.compareTo(this.key);
                if (cmp > 0)
                    if (right != null)
                        return right.find(key);
                    else
                        return this;
                if (cmp < 0)
                    if (left != null)
                        return left.find(key);
                    else
                        return this;
                return this;
            }

            void add(TreeLeaf<K, V> leaf) throws TreeException {
                int cmp = leaf.key.compareTo(key);
                if (cmp == 0)
                    throw new TreeException(KEYEXIST);
                if (cmp > 0) {
                    right = leaf;
                    leaf.parent = this;
                } else {
                    left = leaf;
                    leaf.parent = this;
                }
            }

            void delete() throws TreeException {
                if (parent.right == this) {
                    parent.right = right;
                    if (right != null)
                        right.parent = parent;
                    if (left != null)
                        parent.find(left.key).add(left);
                } else {
                    parent.left = left;
                    if (left != null)
                        left.parent = parent;
                    if (right != null)
                        parent.find(right.key).add(right);
                }
            }

            public String toString() {
                return "("+key+","+value+")";
            }

            public void process(Consumer<TreeLeaf<K,V>> consumer) {
                if (left != null)
                    left.process(consumer);
                consumer.accept(this);
                if (right != null)
                    right.process(consumer);
            }
        }

        private TreeLeaf<K, V> root;

        public V find(K key) {
            if (root == null)
                return null;
            TreeLeaf found = root.find(key);
            return found.key.compareTo(key) == 0 ? (V)found.value : null;
        }

        public void add(TreeLeaf<K, V> leaf) throws TreeException {
            if (root == null)
                root = leaf;
            else
                root.find(leaf.key).add(leaf);
        }

        public void add(K key, V value) throws TreeException {
            add(new TreeLeaf<>(key, value));
        }

        public void delete(K key) throws TreeException {
            internaldDelete(key);
        }

        public TreeLeaf<K, V> internaldDelete(K key) throws TreeException {
            if (root == null)
                throw new TreeException(KEYNOTEXIST);

            TreeLeaf found = root.find(key);
            int cmp = found.key.compareTo(key);
            if (cmp != 0)
                throw new TreeException(KEYNOTEXIST);
            if (found.parent == null) {
                if (found.right != null) {
                    root = found.right;
                    if (found.left != null)
                        add(found.left);
                } else if (found.left != null)
                    root = found.left;
                else
                    root = null;
            } else
                found.delete();
            return found;
        }

        public void change(K oldKey, K newKey) throws TreeException {
            TreeLeaf<K, V> current = internaldDelete(oldKey);
            current.key = newKey;
            add(current);
        }

        public void process(Consumer<TreeLeaf<K,V>> consumer) {
            if (root != null)
                root.process(consumer);
        }

        public TreeLeaf<K, V> getRoot() {
            return root;
        }

        public TreeIterator<K,V> getIterator() {
            return new TreeIterator<>(this);
        }

        @Override
        public Iterator<K> iterator() {
            return getIterator();
        }
    }

    public class TreeIterator<K extends Comparable<K>, V> implements Iterator<K> {
        BinaryTree<K,V>.TreeLeaf<K, V> current;
        BinaryTree<K,V>.TreeLeaf<K, V> root;
        boolean leftTree = true;

        public TreeIterator(BinaryTree<K, V> binaryTree) {
            this.root = binaryTree.getRoot();
            current = root;
        }

        private void moveLeft () {
            while (current.left != null) {
                current = current.left;
            }
        }

        @Override
        public boolean hasNext() {
            if (root == null)
                return false;

            // если у корня нет левого поддерева
            if (root.left == null && leftTree) {
                leftTree = false;
                return true;
            } else // иначе в начале уходим по самой левой ветке
                if (current == root && current.left != null && leftTree) {
                    moveLeft();
                    return true;
                }

            // если у корня нет правого поддерева, то обход закончен
            if (root.right == null && !leftTree)
                return false;

            // если есть правый потомок - уходим в него
            if (current.right != null) {
                current = current.right;
                if (current.left != null) {  // если у него есть левый потомок - уходим по левой ветке
                    moveLeft();
                    return true;
                }
            } else {
                // иначе возврат наверх
                if (current.parent.right == current) {
                    while (current.parent.right == current) {
                        current = current.parent;
                        if (current == root && !leftTree) {  // конец обхода
                            return false;
                        }
                    }
                }
                current = current.parent;
                if (current == root && leftTree) {   // переход на правое поддерево
                    leftTree = false;
                }
            }
            return true;
        }

        @Override
        public K next() {
            return current.key;
        }
    }

    public class AVLIterator<K extends Comparable<K>, V> implements Iterator<K> {
        AvlTree<K,V>.TreeLeaf<K, V> current;
        AvlTree<K,V>.TreeLeaf<K, V> root;
        boolean leftTree = true;

        public AVLIterator(AvlTree <K, V> avlTree) {
            this.root = avlTree.getRoot();
            current = root;
        }

        private void moveLeft() {
            while (current.left != null) {
                current = current.left;
            }
        }

        @Override
        public boolean hasNext() {
            if (root == null)
                return false;

            // если у корня нет левого поддерева
            if (root.left == null && leftTree) {
                leftTree = false;
                return true;
            } else // иначе в начале уходим по самой левой ветке
                if (current == root && current.left != null && leftTree) {
                    moveLeft();
                    return true;
                }

            // если у корня нет правого поддерева, то обход закончен
            if (root.right == null && !leftTree)
                return false;

            // если есть правый потомок - уходим в него
            if (current.right != null) {
                current = current.right;
                if (current.left != null) {  // если у него есть левый потомок - уходим по левой ветке
                    moveLeft();
                    return true;
                }
            } else {
                // иначе возврат наверх
                if (current.parent.right == current) {
                    while (current.parent.right == current) {
                        current = current.parent;
                        if (current == root && !leftTree) {  // конец обхода
                            return false;
                        }
                    }
                }
                current = current.parent;
                if (current == root && leftTree) {   // переход на правое поддерево
                    leftTree = false;
                }
            }
            return true;
        }

        @Override
        public K next() {
            return current.key;
        }
    }

    public class BTree<K extends Comparable<K>, V> {
        class Page<K extends Comparable<K>, V> {
            int maxSize;
            V[] values;
            K[] keys;
            Page<K, V>[] children;
            Page<K, V> parent;

            int findKey(K key) {
                for (int i = 0; i < maxSize; i++) {
                    if (key.compareTo(keys[i]) <= 0)
                        return i;
                }
                return maxSize;
            }

            int findKey2(K key) {
                int min = 0;
                int max = maxSize - 1;
                int i = max;
                while (max > min) {
                    i = (max + min) / 2;
                    int cmp = key.compareTo(keys[i]);
                    if (cmp == 0)
                        return i;
                    if (cmp < 0)
                        max = i;
                    else
                        min = i + 1;
                }
                int cmp = key.compareTo(keys[i++]);
                if (cmp > 0)
                    i++;
                return i;
            }

            void setItem(K key, V value) {

            }

            void addItem(int pos, K key, V value, Page<K, V> child) {

            }

            boolean isFull() {
                return maxSize == keys.length;
            }

            void moveTo(Page<K,V> page, int from) {
                int n = keys.length-from+1;
                System.arraycopy(page.keys, from, keys, 0, n);
                System.arraycopy(page.values, from, values, 0, n);
                System.arraycopy(page.children, from, children, 0, n+1);
            }

            void splitPage() {
                int middle = keys.length/2+1;
                Page<K, V> newpage = new Page<>();
                newpage.moveTo(this, middle+1);
                parent.addItem(-1, keys[middle], values[middle], newpage);
                maxSize--;
            }
        }

        private Page<K, V> root;

        public V find(K key) {
            Page cur = root;
            while(cur != null) {
                int i = cur.findKey2(key);
                if (i < cur.keys.length && key.compareTo((K)cur.keys[i]) == 0)
                    return (V)cur.values[i];
                cur = cur.children[i];
            }
            return null;
        }

        public void add(K key, V value) {
            // check root
            if (root == null || root.isFull()) {
                Page<K, V> newroot = new Page<>();
                if (root != null) {
                    root.parent = newroot;
                    root.splitPage();
                }
                root = newroot;
            }
            // search to insert
            Page cur = root;
            int i = -1;
            while (cur != null) {
                if (cur.isFull())
                    cur.splitPage();
                i = cur.findKey2(key);
                if (i < cur.keys.length && key.compareTo((K) cur.keys[i]) == 0)
                    cur.setItem(key, value);
                cur = cur.children[i];
            }
            cur.addItem(i, key, value, null);
        }
    }

    public class AvlTree<K extends Comparable<K>, V> implements Iterable<K> {
        private static final String KEYEXIST = "Key already exist";
        private static final String KEYNOTEXIST = "Key not exist";

        class TreeLeaf <K extends Comparable<K> , V> {
            K key;
            V value;
            TreeLeaf<K,V> parent;
            TreeLeaf<K,V> left;
            TreeLeaf<K,V> right;
            int height;
            int balance;

            public TreeLeaf(K key, V value) {
                this.key = key;
                this.value = value;
            }

            private TreeLeaf<K, V> find(K key) throws TreeException {
                TreeLeaf<K, V> isFind = this;
                int cmp = 0;
                do {
                    cmp = key.compareTo(isFind.key);
                    if (cmp > 0)
                        if (right != null)
                            isFind = isFind.right;
                        else
                            throw new TreeException(KEYNOTEXIST);
                    if (cmp < 0)
                        if (left != null)
                            isFind = isFind.left;
                        else
                            throw new TreeException(KEYNOTEXIST);
                } while (cmp != 0);
                return isFind;
            }

            private TreeLeaf<K, V> findMax() { //если правый не пусто то ищем правый максимальный
                TreeLeaf<K, V> isFind = this;
                while (isFind.right != null)
                    isFind = isFind.right;
                return isFind;
            }

            private TreeLeaf<K, V> findMin() {//если левый не пусто ищем левый минемальный
                TreeLeaf<K, V> isFind = this;
                while (isFind.left != null)
                    isFind = isFind.left;
                return isFind;
            }

            // поиск замены удаляемому элементу
            private TreeLeaf<K,V> findExchange() {
                TreeLeaf<K,V> node;
                if (balance > 0)
                    node = left.findMax();
                else
                    node = right.findMin();
                return node;
            }

            // удаление терминального или у кого только один потомок
            private void deleteTerm() {
                balanceOK = false;
                TreeLeaf<K,V> temp;
                if (right == null && left == null)
                    temp = null;
                else if (right!=null)
                    temp = right;
                else temp = left;

                if (this.equals(root)) {
                    root = (AvlTree.TreeLeaf) temp;
                    return;
                }

                if (parent.right != null && parent.right.equals(this))
                    parent.right = temp;
                else parent.left = temp;
                if (temp != null)
                    temp.parent = parent;
                TreeLeaf<K,V> current = parent;
                while (!balanceOK) {
                    if (current.equals(root))
                        balanceOK = true;
                    current = current.balanceIsNorm(Operation.DEL);
                }
            }

            // проверка баланса
            private TreeLeaf<K, V> balanceIsNorm(Operation oper) {
                newHeight();
                checkBalance();
                if (balance == 2 || balance == -2)
                    doBalance();
                else if (balance == 1 || balance == -1) {
                    if (oper == Operation.DEL || (oper == Operation.PUT && this.equals(root)))
                        balanceOK = true;
                } else if (balance == 0) {
                    if (oper == Operation.PUT)
                        balanceOK = true;
                }
                return parent;
            }

            private void put(TreeLeaf<K, V> leaf) throws TreeException {
                TreeLeaf<K, V> putAt = this;
                boolean putOK = false;
                while (!putOK) {
                    int cmp = leaf.key.compareTo(putAt.key);
                    if (cmp == 0)
                        throw new TreeException(KEYEXIST);
                    if (cmp > 0)
                        if (putAt.right != null) {
                            putAt = putAt.right;
                        } else {
                            putAt.right = leaf;
                            leaf.parent = putAt;
                            putOK = true;
                        }
                    else {
                        if (putAt.left != null) {
                            putAt = putAt.left;
                        } else {
                            putAt.left = leaf;
                            leaf.parent = putAt;
                            putOK = true;
                        }
                    }
                }
                while (!balanceOK)
                    putAt = putAt.balanceIsNorm(Operation.PUT);
            }

            // сделать балансировку
            private void doBalance() {
                if (balance == 2) {
                    if (left.balance < 0)
                        left.smallLeft();
                    smallRight();
                } else if (balance == -2){
                    if (right.balance > 0)
                        right.smallRight();
                    smallLeft();
                }
            }

            // правый поворот
            public void smallRight() {
                TreeLeaf<K, V> temp = left;
                left = temp.right;
                temp.right = this;
                if (this.equals(root)) {       // если это корень
                    temp.parent = null;
                    root = (AvlTree.TreeLeaf) temp;
                } else {
                    temp.parent = parent;
                    if (parent.right != null && parent.right.equals(this))
                        parent.right = temp;
                    else parent.left = temp;
                }
                parent = temp;
                if (left != null)
                    left.parent = this;
                heiAndBal();
            }

            // левый поворот
            public void smallLeft() {
                TreeLeaf<K,V> temp = right;
                right = temp.left;
                temp.left = this;
                if (this.equals(root)) {
                    temp.parent = null;
                    root = (AvlTree.TreeLeaf) temp;
                } else {
                    temp.parent = parent;
                    if (parent.right != null && parent.right.equals(this))
                        parent.right = temp;
                    else parent.left = temp;
                }
                parent = temp;
                if (right != null)
                    right.parent = this;
                heiAndBal();
            }

            // пересчет высоты и баланса после поворотов
            public void heiAndBal() {
                newHeight();
                checkBalance();
                parent.newHeight();
                parent.checkBalance();
            }

            public String toString() {
                return "("+key+","+value+")";
            }

            public void process(Consumer<TreeLeaf<K,V>> consumer) {
                if (left != null)
                    left.process(consumer);
                consumer.accept(this);
                if (right != null)
                    right.process(consumer);
            }

            // пересчет баланса
            public void checkBalance() {
                balance =  right == null ? (left == null ? 0 : left.height - (-1)) : (left == null ? (-1) - right.height : left.height - right.height);
            }

            // пересчет высоты
            public void newHeight() {
                if (left != null && right != null)
                    height = (left.height >= right.height) ? left.height + 1 : right.height + 1;
                else
                    height = (right == null) ? (left == null ? 0 : left.height + 1) : right.height + 1;
            }
        }

        TreeLeaf<K, V> root;
        boolean balanceOK;

        public V find(K key) throws TreeException {
            if (root == null)
                return null;
            return root.find(key).value;
        }

        public void put(TreeLeaf<K, V> leaf) throws TreeException {
            if (root == null)
                root = leaf;
            else {
                balanceOK = false;
                root.put(leaf);
            }
        }

        public void put(K key, V value) throws TreeException {
            put (new TreeLeaf<>(key, value));
        }

        public void delete(K key) throws TreeException {
            internaldDelete(key);
        }

        public TreeLeaf<K, V> internaldDelete(K key) throws TreeException {
            if (root == null)
                throw new TreeException(KEYNOTEXIST);
            TreeLeaf<K,V> foundDel = root.find(key);
            TreeLeaf<K,V> node;
            if (foundDel.left != null && foundDel.right != null) {  // если это не терминальный узел
                node = foundDel.findExchange();
                node.deleteTerm();
                node.right = foundDel.right;                    //меняем ссылки на потомков
                node.left = foundDel.left;
                if (node.left != null)
                    node.left.parent = node;
                if (node.right != null)
                    node.right.parent = node;
                if (foundDel.parent == null)
                    root = node;
                else if (node.key.compareTo(foundDel.parent.key) > 0)  // меняем у родителя
                    foundDel.parent.right = node;
                else
                    foundDel.parent.left = node;
                node.parent = foundDel.parent;                 // меняем ссылку на родителя у заменяемого
                node.newHeight();
                node.checkBalance();
            } else                                         // если удаляемый узел - терминальный или один потомок
                foundDel.deleteTerm();
            return foundDel;
        }

        public void change(K oldKey, K newKey) throws TreeException {
            TreeLeaf<K, V> current = internaldDelete(oldKey);
            current.key = newKey;
            put(current);
        }

        public void process(Consumer<TreeLeaf<K,V>> consumer) {
            if (root != null)
                root.process(consumer);
        }

        TreeLeaf<K, V> getRoot() {
            return root;
        }

        public AVLIterator<K,V> getIterator() {
            return new AVLIterator<>(this);
        }

        @Override
        public Iterator<K> iterator() {
            return getIterator();
        }
    }

    public class AvlWithTreeMapTest {
        static final int ITERATIONS = 200000;
        long lstart, lstop, result1, result2;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        TreeMap<String, String> map1 = new TreeMap<>();
        AvlTree<Integer, Integer> avl = new AvlTree<>();
        AvlTree<String, String> avl1 = new AvlTree<>();
        ArrayList<Integer> list = new ArrayList<>();

        public void addSort() throws TreeException {
            System.out.println("Вставка " + ITERATIONS + " элементов");
            lstart = System.currentTimeMillis();
            for (int i=0; i <= ITERATIONS; i++) {
                avl.put(i,i);
            }
            lstop = System.currentTimeMillis();
            System.out.println("avl - " + (lstop-lstart));

            lstart = System.currentTimeMillis();
            for (int i=0; i <= ITERATIONS; i++) {
                map.put(i, i);
            }
            lstop = System.currentTimeMillis();
            System.out.println("map - " + (lstop-lstart));
        }

        public void findSort(int step) throws TreeException {
            System.out.println("Поиск каждого " + step + "-го элемента");

            lstart = System.currentTimeMillis();
            for (int i=0; i <= ITERATIONS; i+=step) {
                avl.find(i);
            }
            lstop = System.currentTimeMillis();
            System.out.println("avl - " + (lstop-lstart));

            lstart = System.currentTimeMillis();
            for (int i=0; i <= ITERATIONS; i+=step) {
                map.get(i);
            }
            lstop = System.currentTimeMillis();
            System.out.println("map - " + (lstop-lstart));
        }

        public void delSort(int step) throws TreeException {
            System.out.println("Удаление каждого " + step + "-го элемента");

            lstart = System.currentTimeMillis();
            for (int i=0; i <= ITERATIONS; i+=step) {
                avl.delete(i);
            }
            lstop = System.currentTimeMillis();
            System.out.println("avl - " + (lstop-lstart));

            lstart = System.currentTimeMillis();
            for (int i=0; i <= ITERATIONS; i+=step) {
                map.remove(i);
            }
            lstop = System.currentTimeMillis();
            System.out.println("map - " + (lstop-lstart));
        }

        public void addRandom(int less) throws TreeException {
            map.clear();
            avl = new AvlTree<>();
            System.out.println("Вставка " + ITERATIONS/less + " итераций (в " + less + " раз меньше сортированных)");
            for (int i=0; i <= ITERATIONS/less; i++) {
                int key = ThreadLocalRandom.current().nextInt();
                if (!map.containsKey(key)) {
                    lstart = System.currentTimeMillis();
                    avl.put(key,key);
                    lstop = System.currentTimeMillis();
                    result1 += lstop-lstart;

                    lstart = System.currentTimeMillis();
                    map.put(key, key);
                    lstop = System.currentTimeMillis();
                    result2 += lstop-lstart;

                    list.add(key);
                }
            }
            System.out.println("avl - " + result1);
            System.out.println("map - " + result2);
        }

        public void findRandom() throws TreeException {
            result1 = 0;
            result2 = 0;
            System.out.println("Поиск " + list.size() + " значений");
            for (int key : list) {
                lstart = System.currentTimeMillis();
                avl.find(key);
                lstop = System.currentTimeMillis();
                result1 += lstop - lstart;

                lstart = System.currentTimeMillis();
                map.get(key);
                lstop = System.currentTimeMillis();
                result2 += lstop - lstart;
            }
            System.out.println("avl - " + result1);
            System.out.println("map - " + result2);
        }

        public void delRandom() throws TreeException {
            result1 = 0;
            result2 = 0;
            System.out.println("Удаление " + list.size() + " значений");
            for (int key : list) {
                lstart = System.currentTimeMillis();
                avl.delete(key);
                lstop = System.currentTimeMillis();
                result1 += lstop - lstart;

                lstart = System.currentTimeMillis();
                map.remove(key);
                lstop = System.currentTimeMillis();
                result2 += lstop - lstart;
            }
            System.out.println("avl - " + result1);
            System.out.println("map - " + result2);
        }

        public void addStr(String [] strArr) throws TreeException {
            map.clear();
            System.out.println("Вставка" );
            for (String s : strArr) {
                if (!map1.containsKey(s)) {
                    lstart = System.currentTimeMillis();
                    avl1.put(s, s);
                    lstop = System.currentTimeMillis();
                    result1 += lstop - lstart;

                    lstart = System.currentTimeMillis();
                    map1.put(s, s);
                    lstop = System.currentTimeMillis();
                    result2 += lstop - lstart;
                }
            }
            System.out.println("avl - " + result1);
            System.out.println("map - " + result2);
        }

        public void findStr (String [] strArr) throws TreeException {
            result1 = 0;
            result2 = 0;
            System.out.println("Поиск слов");
            for (String key : strArr) {
                if (map1.containsKey(key)) {
                    lstart = System.currentTimeMillis();
                    avl1.find(key);
                    lstop = System.currentTimeMillis();
                    result1 += lstop - lstart;

                    lstart = System.currentTimeMillis();
                    map1.get(key);
                    lstop = System.currentTimeMillis();
                    result2 += lstop - lstart;
                }
            }
            System.out.println("avl - " + result1);
            System.out.println("map - " + result2);
        }

        public void delStr (String [] strArr) throws TreeException {
            result1 = 0;
            result2 = 0;
            System.out.println("Удаление слов");
            for (String key : strArr) {
                if (map1.containsKey(key)) {
                    lstart = System.currentTimeMillis();
                    avl1.delete(key);
                    lstop = System.currentTimeMillis();
                    result1 += lstop - lstart;

                    lstart = System.currentTimeMillis();
                    map1.remove(key);
                    lstop = System.currentTimeMillis();
                    result2 += lstop - lstart;
                }
            }
            System.out.println("avl - " + result1);
            System.out.println("map - " + result2);
        }

        /*
        public static void main(String[] args) throws TreeException {
            AvlWithTreeMapTest avlTest = new AvlWithTreeMapTest();
            System.out.println("Отсортированные данные:");
            avlTest.addSort();
            avlTest.findSort(50);
            avlTest.delSort(50);
            System.out.println();
            System.out.println("Случайные данные:");
            try {
                avlTest.addRandom(5);
                avlTest.findRandom();
                avlTest.delRandom();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println();
            String [] strArr = {"A"};
            try {
                String str = readString(Paths.get("wiki.train.tokens"));
                strArr = str.split("[^A-Za-zА-Яа-я]+");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Данные из wiki.train.tokens");
            avlTest.addStr(strArr);
            avlTest.findStr(strArr);
            avlTest.delStr(strArr);
        }
    */
    }

    public class TreeTest {
        static final int ITERATIONS = 10;

        /*
        public static void main(String[] args) throws TreeException {
            TreeMap<Integer, Integer> map = new TreeMap<>();
            BinaryTree<Integer, String> tree = new BinaryTree<>();
            for(int i=0; i<ITERATIONS; i++) {
                int key = ThreadLocalRandom.current().nextInt();
                if (!map.containsKey(key)) {
                    map.put(key, key);
                    tree.add(key, "key=" + key);
                }
            }

            System.out.println("add passed OK");
            //tree.process(System.out::println);
            ArrayList<BinaryTree.TreeLeaf> sorted = new ArrayList<>();
            tree.process(sorted::add);
            for(BinaryTree.TreeLeaf leaf: sorted) {
                System.out.println(leaf.toString());
            }
            for(Integer i:map.keySet()) {
                tree.find(i);
                tree.delete(i);
            }
            System.out.println("find&delete passed OK");
        }
    */
    }
}

/*
struct node // структура для представления узлов дерева
{
	int key;
	unsigned char height;
	node* left;
	node* right;
	node(int k) { key = k; left = right = 0; height = 1; }
};

unsigned char height(node* p)
{
	return p?p->height:0;
}

int bfactor(node* p)
{
	return height(p->right)-height(p->left);
}

void fixheight(node* p)
{
	unsigned char hl = height(p->left);
	unsigned char hr = height(p->right);
	p->height = (hl>hr?hl:hr)+1;
}

node* rotateright(node* p) // правый поворот вокруг p
{
	node* q = p->left;
	p->left = q->right;
	q->right = p;
	fixheight(p);
	fixheight(q);
	return q;
}

node* rotateleft(node* q) // левый поворот вокруг q
{
	node* p = q->right;
	q->right = p->left;
	p->left = q;
	fixheight(q);
	fixheight(p);
	return p;
}

node* balance(node* p) // балансировка узла p
{
	fixheight(p);
	if( bfactor(p)==2 )
	{
		if( bfactor(p->right) < 0 )
			p->right = rotateright(p->right);
		return rotateleft(p);
	}
	if( bfactor(p)==-2 )
	{
		if( bfactor(p->left) > 0  )
			p->left = rotateleft(p->left);
		return rotateright(p);
	}
	return p; // балансировка не нужна
}

node* insert(node* p, int k) // вставка ключа k в дерево с корнем p
{
	if( !p ) return new node(k);
	if( k<p->key )
		p->left = insert(p->left,k);
	else
		p->right = insert(p->right,k);
	return balance(p);
}

node* findmin(node* p) // поиск узла с минимальным ключом в дереве p
{
	return p->left?findmin(p->left):p;
}

node* removemin(node* p) // удаление узла с минимальным ключом из дерева p
{
	if( p->left==0 )
		return p->right;
	p->left = removemin(p->left);
	return balance(p);
}

node* remove(node* p, int k) // удаление ключа k из дерева p
{
	if( !p ) return 0;
	if( k < p->key )
		p->left = remove(p->left,k);
	else if( k > p->key )
		p->right = remove(p->right,k);
	else //  k == p->key
	{
		node* q = p->left;
		node* r = p->right;
		delete p;
		if( !r ) return q;
		node* min = findmin(r);
		min->right = removemin(r);
		min->left = q;
		return balance(min);
	}
	return balance(p);
}

 */