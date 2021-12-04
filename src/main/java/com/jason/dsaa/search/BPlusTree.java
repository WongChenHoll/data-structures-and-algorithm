package com.jason.dsaa.search;


import com.jason.dsaa.nodes.BPlusNode;
import com.jason.dsaa.nodes.BPlusResult;

/**
 * B+树
 * <pre>
 *     0.假如该树是n阶的B+树，则：
 *     1.在B+树中，每个结点含有n个关键字和n个子树。也就是每个关键字对应一个子树。
 *     2.每个结点（除根结点）中的关键字个数m的取值范围：n/2 <= m <= n。树的根结点关键字个数取值范围：1 <= m <=n。
 *     3.树中所有叶子结点包含了全部所有关键字的信息及指向对应记录的指针，且所有叶子结点中的关键字值都是从小到大的顺序依次连接的。
 *     4.所有非叶子结点仅起到 LinkedQueue<AVLTreeNode<E>> queue = new LinkedQueue<>();索引的作用，即结点中的每一个索引项只含有对应子树的最大关键字和指向该子树的指针，不含有该关键字对应记录的存储地址。
 * </pre>
 *
 * @author WangChenHol
 * @date 2021-9-10 16:57
 **/
public class BPlusTree<T extends Comparable<T>> {
    public BPlusNode<T> root = null; // 根结点
    public BPlusNode<T> leaf = null; // 指向关键字最小的叶子结点
    public final int degree; // 阶层，默认是最小值3
    public final int MIN_KEY_NUM; // 每个结点（除根结点）中的关键字个数最小值
    public final int MAX_KEY_NUM; // 每个结点（除根结点）中的关键字个数最小值

    public BPlusTree(int degree) {
        if (degree < 3) {
            throw new IllegalArgumentException("B+树最小为3阶");
        }
        this.degree = degree;
        this.MIN_KEY_NUM = degree / 2;
        this.MAX_KEY_NUM = degree;
    }

    public BPlusResult<T> search(T data) {
        checkDataIsNotNull(data);
        int pos = -1;
        boolean found = false;
        BPlusNode<T> node = this.root;
        while (node != null) {
            int index = 0;
            T[] keys = node.keys;
            // TODO
            while (index < node.keyNum && data.compareTo(keys[index]) > 0) {
                index++;
            }

            node = node.children[pos];
        }
        return new BPlusResult<T>(node, pos, found);
    }

    private void checkDataIsNotNull(T data) {
        if (data == null) {
            throw new NullPointerException("请选择查找的数据");
        }
    }

    public BPlusNode<T> insert(T data) {
        checkDataIsNotNull(data);

        if (this.root == null) {
            BPlusNode<T> node = new BPlusNode<>(this.degree, true);
            node.keys[0] = data;
            node.keyNum++;
            this.root = node;
            return node;
        }
        BPlusResult<T> search = search(data);
        // 不插入已存在的数据
        if (search.found) {
            return null;
        }
        BPlusNode<T> node = search.node;
        if (search.pos > -1 && node.keyNum < MAX_KEY_NUM) {
            addKey(node.keys, data);
            node.keyNum++;
            return node;
        } else {


            return null;
        }
    }

//    public BPlusNode<T> delete(T data) {
//
//    }
//
//    public String display() {
//
//    }

    /**
     * 在关键字数组中添加新的关键字，按升序添加
     *
     * @param keys 关键字数组
     * @param key  新的关键字
     */
    private T[] addKey(T[] keys, T key) {
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
        return keys;
    }
}
