package com.jason.dsaa.nodes;

/**
 * @author WangChenHol
 * @date 2021-9-13 16:35
 **/
public class BPlusResult<T extends Comparable<T>> {
    public BPlusNode<T> node; // 数据所在的结点
    public int pos; // 数据在结点中的位置
    public boolean found; // true：找到，false：未找到

    public BPlusResult(BPlusNode<T> node, int pos, boolean found) {
        this.node = node;
        this.pos = pos;
        this.found = found;
    }
}
