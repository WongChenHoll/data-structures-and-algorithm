package com.jason.dsaa.nodes;


/**
 * 二叉排序树结点。
 * 结点中的值必须实现Comparable接口。
 *
 * @author WangChenHol
 * @date 2021-8-16 17:10
 **/
public class BinaryTreeNode<E extends Comparable<E>> implements Comparable<E> {
    public E data; // 结点域的值
    public BinaryTreeNode<E> leftChild; // 左子树
    public BinaryTreeNode<E> rightChild; // 右子树

    public BinaryTreeNode(E data, BinaryTreeNode<E> leftChild, BinaryTreeNode<E> rightChild) {
        this.data = data;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public BinaryTreeNode(E data) {
        this(data, null, null);
    }

    @Override
    public String toString() {
        Object left = leftChild != null ? leftChild.data : null;
        Object right = rightChild != null ? rightChild.data : null;
        return "BinaryNode{" +
                "data=" + data +
                ", leftChild=" + left +
                ", rightChild=" + right +
                '}';
    }


    @Override
    public int compareTo(E o) {
        return data.compareTo(o);
    }
}
