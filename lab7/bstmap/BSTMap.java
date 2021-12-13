package bstmap;

import edu.princeton.cs.algs4.BST;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B<K,V> {

    private class BSTEntry {
        K key;
        V value;
        BSTEntry left;
        BSTEntry right;

        public BSTEntry(K key, V value, BSTEntry left, BSTEntry right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public BSTEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public BSTEntry get(K sk) {
            if (this == null) {
                return null;
            }
            if (this.key.compareTo(sk) == 0) {
                return this;
            } else if (sk.compareTo(this.key) < 0) {
                if (this.left == null) {
                    return null;
                } else {
                    return this.left.get(sk);
                }
            } else {
                if (this.right == null) {
                    return null;
                } else {
                    return this.right.get(sk);
                }
            }
        }
    }

    public void clear() {
        size = 0;
        list = null;
    }

    private BSTEntry list;
    private int size;

    public boolean containsKey(K key) {
        if (list == null) {
            return false;
        } else return list.get(key) != null;
    }
    public V get(K key) {
        if (list == null) {
            return null;
        }
        return list.get(key).value;
    }

    public int size() {
        return size;
    }

    /*original put helper did not change the tree but returned a new tree */
    private BSTEntry putHelper (BSTEntry b, K key, V value) {
        if (b == null) {
            keySet.add(key);
            return new BSTEntry(key, value);
        }
        else if (key.compareTo(b.key) < 0) {
            //I just cannot believe it that all I miss is 'b.left = ....' that simple sentence...why??
            b.left = putHelper(b.left, key, value);
        } else if (key.compareTo(b.key) > 0) {
            b.right = putHelper(b.right, key, value);
        }
        return b;
    }

    public void put(K key, V value) {
        if (this.containsKey(key)) {
        list.get(key).value = value;
        } else {
            size += 1;
            list = putHelper(list, key, value);
        }
    }

    private Set<K> keySet = new HashSet<>();

    public Set<K> keySet() {
        return keySet;
    }


    public V remove(K key) {
        V deleted = get(key);
        list = deleteHelper(list, key);
        return deleted;
    }

    //这个递归我也没想清楚，哎，还是得归纳总结一下
    private BSTEntry min(BSTEntry b) {
        if (b.left == null) return b;
        else {
            return min(b.left);
        }
    }

    private BSTEntry deleteHelper (BSTEntry b, K key) {
        if (b == null) return null;
        else {
            int cmp = key.compareTo(b.key);
            //这一段我没想出来！注意递归的运用啊！
            if (cmp < 0) b.left = deleteHelper(b.left, key);
            else if (cmp > 0) b.right = deleteHelper(b.right, key);
            else {
                BSTEntry t = b;
                b = min(t.right);
                b.left = t.left;
                b.right = deleteMin(t.right);
            }
        }
        return b;
    }


    public V remove(K key, V value) {
        return null;
    }

    private class BSTMapIter implements Iterator<K> {

        BSTEntry cur;

        public BSTMapIter() {
            cur = list;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }


        @Override
        public K next() {
            K ret = cur.key;


            return ret;
        }
    }

    public void deleteMin() {
        list = deleteMin(list);
    }

    /** 愚笨的我一开始没有理解这个递归，但是还是因为这个等号在作祟！整个函数返回的是删掉了最小值的
     * 树*/
    private BSTEntry deleteMin(BSTEntry x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    public void deleteMax() {
        deleteMax(list);
    }

    private BSTEntry deleteMax(BSTEntry x) {
        if(x.right == null) { return x.left; }
        else {
            deleteMax(x.right);
        }
        return x;
    }

    public Iterator<K> iterator() {
        return new BSTMapIter();
    }

    private void printInOrder(BSTEntry b) {
        if (b == null) {
            return;
        }
        printInOrder(b.left);
        System.out.println(b.key + " : " + b.value);
        printInOrder(b.right);
    }

    public void printInOrder(){
        printInOrder(list);
    }
}
