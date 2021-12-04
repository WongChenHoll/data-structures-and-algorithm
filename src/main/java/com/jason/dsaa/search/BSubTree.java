package com.jason.dsaa.search;


import com.jason.dsaa.nodes.BSubTreeResult;
import com.jason.dsaa.nodes.BTreeNode;

import java.util.Arrays;

/**
 * B-树,一个树的阶，就是这个树中各个节点的子节点个数的最大值.
 * <pre>
 *     在B-树中，假如该树为m阶，则：
 *     1.所有的终端结点（叶子结点）都出现在同一层次上，并且不带任何信息。
 *     2.除根结点之外，所有的非终端结点的关键字个数最少为m/2-1个，最多为m-1个。
 *     3.每个结点的子树数目最少为m/2个，最多为m个。
 * </pre>
 *
 * @author WangChenHol
 * @date 2021-8-24 16:35
 **/
public class BSubTree<E extends Comparable<E>> {
    public BTreeNode<E> root = null;
    public final int degree; // 阶层，默认是最小值3
    private final int minKeyNum; // 每个结点关键字的最小数量
    private final int maxKeyNum; // 每个结点关键字的最大数量
    private final int minNodeNum; // 每个结点的最小子树的数量
    private final int maxNodeNum; // 每个结点的最大子树的数量

    public BSubTree(int t) {
        if (t < 3) {
            throw new IllegalArgumentException("B-树的最小阶层不能小于3");
        }
        this.degree = t;
        this.minKeyNum = t / 2 - 1;
        this.maxKeyNum = t - 1;
        this.minNodeNum = t / 2;
        this.maxNodeNum = t;
    }

    /**
     * 查找结点
     *
     * @param root 查找的B-树的跟结点
     * @param data 待查找的数据
     * @return 查找的结果
     */
    public BSubTreeResult<E> search(BTreeNode<E> root, E data) {
        int index = 0;
        BTreeNode<E> curr = root; // 当前查找的结点
        BTreeNode<E> q = null;
        boolean found = false; // 是否查找到数据
        BSubTreeResult<E> result = new BSubTreeResult<>(); // 返回的结果

        while (!found && curr != null) {
            index = 0;
            while (index < curr.keyNum && data.compareTo(curr.key[index]) > 0) {
                index++;
            }
            if (index < curr.keyNum && data.compareTo(curr.key[index]) == 0) {
                found = true;
            } else {
                q = curr;
                curr = curr.child[index];
            }
        }
        if (!found) {
            curr = q;
        }
        result.i = index;
        result.resultNode = curr;
        result.found = found;
        return result;
    }

    /**
     * 插入数据，所有的插入操作都是在叶子结点上完成的。
     *
     * @param data 数据
     * @return true：插入成功
     */
    public boolean insert(E data) {
        return insert(this.root, data);
    }

    private boolean insert(BTreeNode<E> root, E data) {
        if (root == null) {
            this.root = new BTreeNode<>(this.degree);
            this.root.key[0] = data;
            this.root.keyNum++;
            return true;
        }
        BSubTreeResult<E> search = search(root, data);
        // 已经存在数据，不重新插入，直接返回true
        if (search.found) {
            return true;
        }
        int index = search.i;
        BTreeNode<E> resultNode = search.resultNode;
        // 直接在当前结点添加关键字
        if (resultNode.keyNum < this.maxKeyNum) {
            System.arraycopy(resultNode.key, index, resultNode.key, index + 1, resultNode.keyNum - index);
            resultNode.key[index] = data;
            resultNode.keyNum++;
        } else {
            // 分裂结点
            splitNode(search.resultNode, data);

        }
        return true;
    }

    /**
     * 分裂结点
     *
     * @param curr 分裂的当前结点
     * @return 返回值为处理完当前结点的父结点，如果其父结点还需要分裂，则返回的是其父结点的父结点
     */
    private BTreeNode<E> splitNode(BTreeNode<E> curr, E data) {
        BTreeNode<E> parent = curr.parent;
        E[] currKey = curr.key;
        E[] copyCurrKey = copyKey(data, currKey); // 新的关键字数组
        E middleKey = copyCurrKey[copyCurrKey.length / 2]; // 需要提升的中间关键字
        if (parent == null) {
            // 父结点
            this.root = div(copyCurrKey, middleKey, new BTreeNode<>(this.degree));
            return this.root;
        } else {
            if (parent.keyNum == maxKeyNum) {
                BTreeNode<E> splitNode = splitNode(parent, middleKey);
                BTreeNode<E>[] child = parent.child;
                BTreeNode<E> div = div(copyCurrKey, middleKey, parent);
                splitNode.child[0]=div.child[0];
                splitNode.child[1]=div.child[1];
                return div;
            } else {
                return div(copyCurrKey, middleKey, parent);
            }
        }
    }

    /**
     * 分裂
     *
     * @param middleKey 需要提升的关键字
     * @param parent    父结点
     * @return 父结点
     */
    private BTreeNode<E> div(E[] copyCurrKey, E middleKey, BTreeNode<E> parent) {
        E[] parentKey = parent.key;

        // 将需要提升的关键字添加到父结点的关键字数组中
        BTreeNode<E>[] child = parent.child;
        addKey(parentKey, middleKey);
        parent.keyNum++;
        parent.isLeaf = false;

        int parentKeyPosition = StaticTableSearch.orderSearch(parentKey, middleKey); // 查找新添加的关键字在父结点关键字数组中的位置
        BTreeNode<E> small = new BTreeNode<>(this.degree);
        BTreeNode<E> big = new BTreeNode<>(this.degree);
        divCurrentNodeKey(copyCurrKey, small, big);
        // 移动同级别的结点对应父结点的位置
        if (!isEmpty(child)) {
            System.arraycopy(child, parentKeyPosition, child, parentKeyPosition + 1, this.maxKeyNum - parentKeyPosition);
        }
        small.parent = big.parent = parent;
        child[parentKeyPosition] = small;
        child[parentKeyPosition + 1] = big;

        return parent;
    }

    /**
     * 将当前的关键字拆分给两个分裂后的结点中
     *
     * @param copy  当前结点中关键字数组
     * @param small 子结点1
     * @param big   子结点2
     */
    private void divCurrentNodeKey(E[] copy, BTreeNode<E> small, BTreeNode<E> big) {
        for (int i = 0; i < (this.maxKeyNum + 1) / 2; i++) {
            small.key[i] = copy[i];
            small.keyNum++;
        }
        for (int j = 0; j < (this.maxKeyNum + 1) / 2; j++) {
            big.key[j] = copy[(this.maxKeyNum + 1) / 2 + 1 + j];
            big.keyNum++;
        }
    }


    /**
     * 找到此结点在父结点中的位置
     *
     * @param curr 当前结点
     * @return 父结点中位置
     */
    private int childPosition(BTreeNode<E> curr) {
        BTreeNode<E> parrent = curr.parent;
        BTreeNode<E>[] child = parrent.child;

        for (int i = 0; i < child.length; i++) {
            if (Arrays.equals(curr.key, child[i].key)) {
                return i;
            }
        }
        return -1;
    }

    private E[] copyKey(E data, E[] key) {
        E[] copy = (E[]) new Comparable[key.length + 1];
        System.arraycopy(key, 0, copy, 0, key.length);
        addKey(copy, data);
        return copy;
    }


    /**
     * 在关键字数组中添加新的关键字，按升序添加
     *
     * @param keys 关键字数组
     * @param key  新的关键字
     */
    private void addKey(E[] keys, E key) {
        int i = 0;
        for (; i < keys.length; i++) {
            if (keys[i] == null) {
                keys[i] = key;
                break;
            }
            if (key.compareTo(keys[i]) < 0) {
                int j = keys.length - 1;
                while (j > i) {
                    keys[j] = keys[j - 1];
                    j--;
                }
                keys[j] = key;
                break;
            }
        }
    }

    /**
     * 判断数组是否为空
     *
     * @param arr 数组
     * @return true：空，包括数组对象为空，数组长度为0，数组中所有的元素都是null
     */
    private boolean isEmpty(Object[] arr) {
        if (arr == null || arr.length == 0) {
            return true;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                return false;
            }
        }
        return true;
    }

}
