package com.jason.dsaa.nodes;

/**
 * B-树返回结果类
 *
 * @author WangChenHol
 * @date 2021-8-24 16:32
 **/
public class BSubTreeResult<E extends Comparable<E>> {
    public BTreeNode<E> resultNode;  // 指向找到的结点
    public int i; // 在结点中的关键码的序号
    public boolean found; // true：找到
}
