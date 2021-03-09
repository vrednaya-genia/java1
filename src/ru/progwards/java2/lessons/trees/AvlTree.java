package ru.progwards.java2.lessons.trees;

import java.util.function.Consumer;

public class AvlTree<K extends Comparable<K>, V> {

    class AvlLeaf<K extends Comparable<K>, V> {
        K key;
        V value;
        byte height;
        AvlLeaf<K,V> parent;
        AvlLeaf<K,V> left;
        AvlLeaf<K,V> right;

        public AvlLeaf(K key, V value) {
            this.key = key;
            this.value = value;
            height = 0;
        }

        public void put(AvlLeaf<K,V> leaf) {
            int cmp = leaf.key.compareTo(key);
            if (cmp == 0) {
                value = leaf.value;
                return;
            }
            if (cmp > 0) {
                right = leaf;
            } else {
                left = leaf;
            }
            leaf.parent = this;
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
            return "(" + key + "," + value + ")";
        }
    }
    private AvlLeaf<K,V> root;

    byte getHeight(AvlLeaf<K,V> leaf) {
        return leaf == null ? 0 : leaf.height;
    }

    void setHeight(AvlLeaf<K,V> leaf) {
        leaf.height = (byte)(Math.max(leaf.left.height, leaf.right.height) + 1);
    }

    int balance(AvlLeaf<K,V> leaf) {
        byte lh = leaf.left == null ? 0 : leaf.left.height;
        byte rh = leaf.right == null ? 0 : leaf.right.height;
        return rh - lh;
    }

    // добавить пару ключ-значение, если уже такой ключ есть - заменить
    public void put(K key, V value) {
        put(new AvlLeaf<>(key, value));
    }

    public void put(AvlLeaf<K,V> leaf) {
        if (root == null) {
            root = leaf;
        } else {
            //root.find(leaf.key).add(leaf);
        }
    }

    public void delete(K key) {

    }

    public V find(K key) {
        return null;
    }

    public void change(K oldKey, K newKey) {

    }

    public void process(Consumer<AvlLeaf<K,V>> consumer) {
        root.process(consumer);
    }
}
