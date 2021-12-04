package com.jason.dsaa.search;


import com.jason.dsaa.nodes.BinaryTreeNode;

/**
 * 二叉排序树。
 * <pre>
 *     二叉排序树的性质：
 *     1.若左子树不为空，则左子树上所有结点的值均小于根结点的值。
 *     2.若右子树不为空，则右子树上所有结点的值均大于根结点的值。
 *     3.它的左右子树也都是二叉排序树。
 * </pre>
 *
 * @author WangChenHol
 * @date 2021-8-16 16:28
 **/
public class BinarySortTree<E extends Comparable<E>> {

    public BinaryTreeNode<E> root; // 根结点

    public BinarySortTree() {
        root = null;
    }

    /**
     * 中根遍历
     *
     * @param node 根结点
     */
    public void inOrderTraverse(BinaryTreeNode<E> node) {
        if (node != null) {
            inOrderTraverse(node.leftChild);
            System.out.print(node.data.toString() + " ");
            inOrderTraverse(node.rightChild);
        }
    }

    /**
     * 查找二叉排序树
     *
     * @param key 待查找数据
     * @return 查找到的结点
     */
    public BinaryTreeNode<E> searchBinarySortTree(E key) {
        if (key == null) {
            return null;
        }

        return searchBinarySortTree(root, key);
    }

    /**
     * 递归算法查找二叉树
     *
     * @param node 查找的二叉树
     * @param key  待查找的数据
     * @return 查找到的结点
     */
    public BinaryTreeNode<E> searchBinarySortTree(BinaryTreeNode<E> node, E key) {
        if (node == null) {
            return null;
        }
        if (node.compareTo(key) == 0) {
            return node;
        }

        if (node.compareTo(key) > 0) {
            return searchBinarySortTree(node.leftChild, key);
        } else {
            return searchBinarySortTree(node.rightChild, key);
        }
    }

    /**
     * 在二叉排序树中插入元素
     *
     * @param data 新元素
     */
    public void insertBinarySortTree(E data) {

        BinaryTreeNode<E> node = new BinaryTreeNode<>(data);
        if (root == null) {
            root = node;
            return;
        }
        insertBinarySortTree(root, data);
    }

    /**
     * 在根结点下插入新元素
     *
     * @param root 跟结点
     * @param data 新元素
     */
    private void insertBinarySortTree(BinaryTreeNode<E> root, E data) {
        if (data.compareTo(root.data) < 0) {
            if (root.leftChild == null) {
                root.leftChild = new BinaryTreeNode<>(data);
            } else {
                insertBinarySortTree(root.leftChild, data);
            }
        } else {
            if (root.rightChild == null) {
                root.rightChild = new BinaryTreeNode<>(data);
            } else {
                insertBinarySortTree(root.rightChild, data);
            }
        }
    }


    /**
     * 删除二叉排序树中的某个结点。时间复杂度是：O(log2n)
     *
     * @param data 被删除的结点值
     * @return 被删除的结点，如果是空则删除失败，或者不存在此结点
     */
    public E removeBinarySortTree(E data) {
        return removeBinarySortTree(root, data, null);
    }

    private E removeBinarySortTree(BinaryTreeNode<E> root, E data, BinaryTreeNode<E> parent) {
        if (root != null) {
            if (data.compareTo(root.data) < 0) {
                return removeBinarySortTree(root.leftChild, data, root);
            } else if (data.compareTo(root.data) > 0) {
                return removeBinarySortTree(root.rightChild, data, root);
            } else if (root.leftChild != null && root.rightChild != null) {
                BinaryTreeNode<E> nextNode = root.rightChild;
                while (nextNode.leftChild != null) {
                    nextNode = nextNode.leftChild;
                }
                root.data = nextNode.data;
                return removeBinarySortTree(root.rightChild, root.data, root);
            } else {
                if (parent == null) {
                    if (root.leftChild != null) {
                        this.root = root.leftChild;
                    } else {
                        this.root = root.rightChild;
                    }
                    return root.data;
                }
                if (root == parent.leftChild) {
                    if (root.leftChild != null) {
                        parent.leftChild = root.leftChild;
                    } else {
                        parent.leftChild = root.rightChild;
                    }
                } else if (root.leftChild != null) {
                    parent.rightChild = root.leftChild;
                } else {
                    parent.rightChild = root.rightChild;
                }
                return root.data;
            }
        }
        return null;
    }

}
