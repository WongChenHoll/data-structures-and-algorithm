package com.jason.dsaa.nodes;

/**
 * 平衡二叉树的结点
 *
 * @author ChenHol.Wong
 * @date 2021/8/18 22:27
 */
public class AVLTreeNode<E extends Comparable<E>> implements Comparable<E> {
    public E data; // 结点数据元素
    public AVLTreeNode<E> left; // 左子树
    public AVLTreeNode<E> right; // 右子树
    public AVLTreeNode<E> parent; // 父结点

    public AVLTreeNode(E data) {
        this(data, null, null, null);
    }

    public AVLTreeNode(E data, AVLTreeNode<E> parent) {
        this(data, null, null, parent);
    }

    public AVLTreeNode(E data, AVLTreeNode<E> left, AVLTreeNode<E> right) {
        this(data, left, right, null);
    }

    public AVLTreeNode(E data, AVLTreeNode<E> left, AVLTreeNode<E> right, AVLTreeNode<E> parent) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    @Override
    public String toString() {
        E leftData = left != null ? left.data : null;
        E rightData = right != null ? right.data : null;
        E parentData = parent != null ? parent.data : null;
        return "AVLTreeNode{" +
                "data=" + data +
                ", parent=" + parentData +
                ", left=" + leftData +
                ", right=" + rightData +
                '}';
    }

    @Override
    public int compareTo(E o) {
        return data.compareTo(o);
    }
}
