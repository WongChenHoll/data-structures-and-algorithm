package com.jason.dsaa.search.exp;

/**
 * @author WangChenHol
 * @date 2021-8-17 16:28
 **/
public class KeyType implements Comparable<KeyType> {
    public int key;

    public KeyType(int key) {
        this.key = key;
    }

    @Override
    public int compareTo(KeyType o) {
        return Integer.compare(key, o.key);
    }

}
