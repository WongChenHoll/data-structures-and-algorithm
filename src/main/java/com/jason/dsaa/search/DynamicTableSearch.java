package com.jason.dsaa.search;


import com.jason.dsaa.nodes.BSubTreeResult;

/**
 * 动态表查找
 *
 * @author WangChenHol
 * @date 2021-8-16 16:27
 **/
public class DynamicTableSearch {


    /**
     * 二叉排序树查找
     *
     * @param tree 二叉排序树
     * @param data 待查找的数据
     * @param <E>  数据类型
     * @return 查找到的数据
     */
    public static <E extends Comparable<E>> E searchBinarySortTree(BinarySortTree<E> tree, E data) {
        return tree.searchBinarySortTree(tree.root, data).data;
    }

    /**
     * 平衡二叉树查找
     *
     * @param tree 平衡二叉树
     * @param data 带查找数据
     * @param <E>  数据类型
     * @return 查找到的数据
     */
    public static <E extends Comparable<E>> E searchBalanceBinaryTree(BalancedBinaryTree<E> tree, E data) {
        return tree.searchBalanceBinaryTree(data).data;
    }

    /**
     * B-树中查找
     *
     * @param tree B-树
     * @param data 待查找数据
     * @param <E>  数据类型
     * @return 查找的结果，如果结果中found值为true，则查找成功，否则则未查找到
     */
    public static <E extends Comparable<E>> BSubTreeResult<E> searchBSubTree(BSubTree<E> tree, E data) {
        return tree.search(tree.root, data);
    }

}
