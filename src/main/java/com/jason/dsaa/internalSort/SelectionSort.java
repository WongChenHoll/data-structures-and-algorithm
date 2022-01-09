package com.jason.dsaa.internalSort;


import com.jason.dsaa.utils.PrintUtil;

/**
 * 选择排序。包括直接选择排序、树形选择排序、堆排序
 *
 * @author WangChenHol
 * @date 2021-8-2 16:24
 **/
public class SelectionSort {

    public static final int MAX = Integer.MAX_VALUE;


    /**
     * 直接选择排序。
     * 空间复杂度：O(1)。
     * 时间复杂度：O(n2)。
     * 是一种不稳定的排序算法。
     *
     * @param datas 待排序数据
     */
    public static void straightSelectionSort(int[] datas) {
        int minValue; // 最小值
        for (int i = 0; i < datas.length - 1; i++) {
            int minIndx = i;  // 最小值的下标
            minValue = datas[i];
            for (int j = i + 1; j < datas.length; j++) {
                if (datas[j] < minValue) {
                    minIndx = j;
                    minValue = datas[j];
                }
            }
            // 将最小值和第i个元素交换位置
            datas[minIndx] = datas[i];
            datas[i] = minValue;
        }
    }

    /**
     * 树形选择排序。
     * 注意：datas的长度必须满足2的k次幂。
     * <pre>
     *     空间复杂度：空间存储需求比较大。
     *     时间复杂度：O(nlog2n)。
     *     是一种稳定的排序算法。
     * </pre>
     *
     * @param datas 待排序数据
     */
    public static void treeSelectionSort(int[] datas) {
        int length = datas.length; // 待排序的数组长度，length一定要是2的幂次方，如果不是则扩大到2的幂次方

        if (!((length & (length - 1)) == 0)) {// 判断length是否是2的幂次方
            int minPower = minPower(length);
            length = (int) Math.pow(2, minPower + 1);
        }
        int treeSize = length * 2 - 1; // 树的存储大小
        int[] tree = new int[treeSize]; // 树的存储空间

        // 由后向前将待排序数据存储在树中（叶子节点）
        for (int i = length - 1, j = treeSize - 1; i >= 0; i--, j--) {
            if (i > datas.length - 1) {
                tree[j] = MAX;
            } else {
                tree[j] = datas[i];
            }
        }
        // 填充非叶子节点
        for (int i = treeSize - 1 - length; i >= 0; i--) {
            tree[i] = Integer.min(tree[2 * i + 1], tree[2 * i + 2]);
        }
        PrintUtil.printIntArray(tree);


        // 排序
        int i = 0;
        while (i < datas.length) {
            int minValue = tree[0];
            datas[i] = minValue; // 最小值

            // 将树中次最小值替换为无穷大值
            int minIndex = 0;// 最小值下标
            while (true) {
                int a = (minIndex + 1) * 2 - 1;
                int b = (minIndex + 1) * 2;
                if (a > tree.length - 1 || b > tree.length - 1) {
                    break;
                }
                int v1 = tree[a];
                int v2 = tree[b];
                // 将最小值的结点置为无穷大
                minIndex = Math.min(v1, v2) == v1 ? a : b;
                tree[minIndex] = MAX;
            }

            // 重新选择生成胜利树，找出最小值
            while (minIndex > 0) {
                if (minIndex % 2 == 0) {
                    tree[minIndex / 2 - 1] = Math.min(tree[minIndex], tree[minIndex - 1]);
                    minIndex = minIndex / 2 - 1;
                } else {
                    tree[minIndex / 2] = Math.min(tree[minIndex], tree[minIndex + 1]);
                    minIndex = minIndex / 2;
                }
            }
            i++;
        }
    }

    /**
     * 求number的2的对数
     */
    private static int minPower(int number) {
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            if (Math.pow(2, i) < 10 && Math.pow(2, i + 1) > 10) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 堆排序。倒序<p/>
     *
     * <pre>
     *     堆是具有以下性质的完全二叉树：
     *     每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；
     *     或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。
     *     一般升序采用大顶堆，降序采用小顶堆。
     *     1、先根据循环创建一个小顶堆。
     *     2、在根据循环不断重新调整堆，是最小的结点在最后。
     *     3、空间复杂度：O(1)。
     *     4、时间复杂度L：O(nlog2n)。是一种不稳定的排序算法。
     * </pre>
     *
     * @param datas 待排序数组
     */
    public static void heapSortDesc(int[] datas) {
        int length = datas.length;
        int temp;
        // 构建有序堆，从第一个非叶子结点开始，也就是第datas.length/2 - 1 个元素开始。
        for (int i = length / 2 - 1; i >= 0; i--) {
            smallTopHeapSift(datas, i, length);
        }
        System.out.println("小顶堆：");
        PrintUtil.printIntArray(datas);
        // 堆排序
        for (int i = length - 1; i > 0; i--) {
            temp = datas[0];
            datas[0] = datas[i];
            datas[i] = temp;
            smallTopHeapSift(datas, 0, i);
        }

    }

    /**
     * 筛选，调整堆.
     * 小顶堆筛选
     *
     * @param datas 堆
     * @param low   下界
     * @param high  上界
     */
    private static void smallTopHeapSift(int[] datas, int low, int high) {
        int root = low; // 子树的根结点
        int child = 2 * low + 1; // root结点的左结点
        int temp = datas[root];
        while (child < high) {
            if (child < high - 1 && datas[child] > datas[child + 1]) {
                child++;
            }
            if (temp > datas[child]) {
                datas[root] = datas[child];
                root = child;
                child = 2 * root + 1;
            } else {
                child = high + 1;
            }
        }
        datas[root] = temp;
    }

    /**
     * 堆排序。正序
     *
     * @param datas 待排序数据
     */
    public static void heapSortAsc(int[] datas) {
        int length = datas.length;
        int temp;
        for (int i = length / 2 - 1; i >= 0; i--) {
            bigTopHeapSift(datas, i, length);
        }
        System.out.println("大顶堆：");
        PrintUtil.printIntArray(datas);

        for (int i = length - 1; i > 0; i--) {
            temp = datas[0];
            datas[0] = datas[i];
            datas[i] = temp;
            bigTopHeapSift(datas, 0, i);
        }
    }

    /**
     * 筛选器，大顶堆。
     */
    private static void bigTopHeapSift(int[] datas, int low, int high) {
        int temp = datas[low];//先取出当前元素i
        for (int k = low * 2 + 1; k < high; k = k * 2 + 1) {//从i结点的左子结点开始，也就是2i+1处开始
            if (k < high - 1 && datas[k] < datas[k + 1]) {//如果左子结点小于右子结点，k指向右子结点
                k++;
            }
            if (datas[k] > temp) {//如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                datas[low] = datas[k];
                low = k;
            } else {
                break;
            }
        }
        datas[low] = temp;//将temp值放到最终的位置
    }

}
