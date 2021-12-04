package com.jason.dsaa.nodes;

/**
 * B+树结点
 *
 * @author WangChenHol
 * @date 2021-9-13 15:08
 **/
public class BPlusNode<T extends Comparable<T>> {
    public int keyNum; // 关键字的数量
    public boolean isLeaf; // 是否是叶子结点
    public T[] keys; // 数据
    public BPlusNode<T>[] children; // 子结点
    public BPlusNode<T> parent; // 父结点
    public BPlusNode<T> next; // 下一个节点

    public BPlusNode(int m, boolean isLeaf, BPlusNode<T> parent, BPlusNode<T> next) {
        this.keyNum = 0;
        this.isLeaf = isLeaf;
        this.keys = (T[]) new Comparable[m];
        this.children = new BPlusNode[m];
        this.parent = parent;
        this.next = next;
    }

    public BPlusNode(int m, boolean isLeaf, BPlusNode<T> parent) {
        this(m, isLeaf, parent, null);
    }

    public BPlusNode(int m, boolean isLeaf) {
        this(m, isLeaf, null);
    }

    public BPlusNode(int m) {
        this(m, false);
    }
}
