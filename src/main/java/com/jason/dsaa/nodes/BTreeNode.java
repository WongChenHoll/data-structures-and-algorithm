package com.jason.dsaa.nodes;

/**
 * B-树的结点
 *
 * @author WangChenHol
 * @date 2021-8-24 16:18
 **/
public class BTreeNode<E extends Comparable<E>> {

    public int keyNum; // 关键字个数域，该结点有多少个关键字
    public boolean isLeaf; // 是否是叶子结点
    public E[] key; // 关键字数组
    public BTreeNode<E>[] child; // 子树指针数组，P0,P1,P2....
    public BTreeNode<E> parent; // 双亲结点指针，指向父级结点


    /**
     * 创建一个结点
     *
     * @param m 阶数
     */
    public BTreeNode(int m) {
        this.keyNum = 0;
        this.isLeaf = true;
        this.key = (E[]) new Comparable[m - 1];
        this.child = new BTreeNode[m];
        this.parent = null;
    }

}
