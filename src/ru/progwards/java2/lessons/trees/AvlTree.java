package ru.progwards.java2.lessons.trees;

import java.util.function.Consumer;

public class AvlTree<K extends Comparable<K>, V> {
    private static final String KEYNOTEXIST = "Key doesn't exist";

    class AvlLeaf<K extends Comparable<K>, V> {
        K key;
        V value;
        int height;
        AvlLeaf<K,V> parent;
        AvlLeaf<K,V> left;
        AvlLeaf<K,V> right;

        public AvlLeaf(K key, V value) {
            this.key = key;
            this.value = value;
            height = 1;
        }

        private void fixHeight() {
            int hl = left == null ? 0 : left.height;
            int hr = right == null ? 0 : right.height;
            height = Math.max(hl, hr) + 1;
        }

        // пересчет высот родителей
        private void fixHeights() {
            AvlLeaf<K, V> l = this;
            while (l != null) {
                l.fixHeight();
                l = l.parent;
            }
        }

        private int balance() {
            int hl = left == null ? 0 : left.height;
            int hr = right == null ? 0 : right.height;
            return hr - hl;
        }

        private AvlLeaf<K,V> rightRotate() {
            AvlLeaf<K,V> b = left;
            AvlLeaf<K,V> c = b.right;
            left = c;
            b.right = this;
            if (parent != null) {
                if (parent.left == this)
                    parent.left = b;
                else
                    parent.right = b;
            } else
                changeRoot = true;
            b.parent = parent;
            parent = b;
            if (c != null)       // вырожденный случай
                c.parent = this;
            return b;
        }

        private AvlLeaf<K,V> leftRotate() {
            AvlLeaf<K,V> a = right;
            AvlLeaf<K,V> c = a.left;
            right = c;
            a.left = this;
            if (parent != null) {
                if (parent.left == this)
                    parent.left = a;
                else
                    parent.right = a;
            } else
                changeRoot = true;
            a.parent = parent;
            parent = a;
            if (c != null)       // вырожденный случай
                c.parent = this;
            return a;
        }

        public AvlLeaf<K, V> doBalance() {
            AvlLeaf<K, V> newRoot;
            AvlLeaf<K, V> p = this;
            while (p != null) {
                p.fixHeight();
                if (p.balance() == -2) {
                    if (p.left.balance() > 0) {
                        p.left.leftRotate();
                    }
                    newRoot = p.rightRotate();
                    p.fixHeights();
                    return newRoot;
                }
                if (p.balance() == 2) {
                    if (p.right.balance() < 0) {
                        p.right.rightRotate();
                    }
                    newRoot = p.leftRotate();
                    p.fixHeights();
                    return newRoot;
                }
                p = p.parent;
            }
            return null;
        }

        public AvlLeaf<K, V> find(K key) {
            int cmp = key.compareTo(this.key);
            if (cmp > 0) {
                if (right != null)
                    return right.find(key);
                else
                    return this;
            }
            if (cmp < 0) {
                if (left != null)
                    return left.find(key);
                else
                    return this;
            }
            return this;
        }

        // добавить пару ключ-значение, если уже такой ключ есть - заменить
        public void put(AvlLeaf<K,V> leaf) {
            int cmp = leaf.key.compareTo(key);
            if (cmp == 0) {
                value = leaf.value;
                return;
            }
            if (cmp > 0)
                right = leaf;
            else
                left = leaf;
            leaf.parent = this;
        }

        private AvlLeaf<K, V> removeMin() {
            if (left != null)
                return left.removeMin();
            parent.left = right;
            if (right != null)
                right.parent = parent;
            parent.fixHeights();
            return this;
        }

        public AvlLeaf<K,V> remove() {
            AvlLeaf<K,V> newRoot;
            if (right == null) {
                if (parent.right == this)
                    parent.right = left;
                else
                    parent.left = left;
                if (left != null)
                    left.parent = parent;
                newRoot = parent.doBalance();
            } else {
                AvlLeaf<K,V> min;
                if (right.left == null) {
                    min = right;
                } else {
                    min = right.removeMin();
                    min.right = right;
                    if (min.right != null)
                        min.right.parent = min;
                }
                min.left = left;
                if (min.left != null)
                    min.left.parent = min;

                if (parent.right == this)
                    parent.right = min;
                else
                    parent.left = min;
                min.parent = parent;
                newRoot = min.doBalance();
            }
            return newRoot;
        }

        public void process(Consumer<AvlLeaf<K, V>> consumer) {
            if (left != null) {
                left.process(consumer);
            }
            consumer.accept(this);
            if (right != null) {
                right.process(consumer);
            }
        }

        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }

    private AvlLeaf<K,V> root;
    boolean changeRoot = false;

    public AvlLeaf<K,V> find(K key) {
        if (root == null)
            return null;
        AvlLeaf<K,V> found = root.find(key);
        return found.key.compareTo(key) == 0 ? found : null;
    }

    public void put(AvlLeaf<K,V> leaf) {
        changeRoot = false;
        if (root == null) {
            root = leaf;
        } else {
            root.find(leaf.key).put(leaf);
            AvlLeaf<K,V> newRoot = leaf.doBalance();
            if (changeRoot)
                root = newRoot;
        }
    }

    public void put(K key, V value) {
        put(new AvlLeaf<>(key, value));
    }

    public void delete(K key) throws TreeException {
        changeRoot = false;
        if (root == null)
            throw new TreeException(KEYNOTEXIST);

        AvlLeaf<K,V> found = root.find(key);
        int cmp = found.key.compareTo(key);
        if (cmp != 0)
            throw new TreeException(KEYNOTEXIST);

        AvlLeaf<K,V> newRoot;
        if (found.parent == null) {
            if (found.right == null) {
                root = found.left;
                if (root != null) {
                    root.parent = null;
                }
            } else {
                AvlLeaf<K,V> min;
                if (found.right.left == null) {
                    min = found.right;
                } else {
                    min = found.right.removeMin();
                    min.right = found.right;
                    if (min.right != null)
                        min.right.parent = min;
                }
                min.left = found.left;
                if (min.left != null)
                    min.left.parent = min;
                min.parent = null;
                min.fixHeight();
                root = min;
                newRoot = root.doBalance();
                if (changeRoot)
                    root = newRoot;
            }
        } else {
            newRoot = found.remove();
            if (changeRoot)
                root = newRoot;
        }
    }

    public void change(K oldKey, K newKey) throws TreeException {
        AvlLeaf<K,V> current = find(oldKey);
        if (current != null)
            current.key = newKey;
        else
            throw new TreeException(KEYNOTEXIST);
    }

    public void process(Consumer<AvlLeaf<K,V>> consumer) {
        root.process(consumer);
    }
}
