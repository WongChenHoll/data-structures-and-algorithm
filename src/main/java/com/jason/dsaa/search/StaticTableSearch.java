package com.jason.dsaa.search;

/**
 * 静态表查找。
 * <pre>
 *     静态表查找分为：顺序表或线性表。
 *     顺序表有三种查找方式：顺序查找、二分查找、分块查找
 * </pre>
 *
 * @author WangChenHol
 * @date 2021-8-10 16:54
 **/
public class StaticTableSearch {

    /**
     * 顺序查找。最基本的查找。<p/>
     * 时间复杂度是：O(n)
     *
     * @param datas 带查找的表
     * @param key   关键字
     * @return 表中关键字的位置，如果不存在则为-1
     */
    public static int orderSearch(int[] datas, int key) {
        for (int i = 0; i < datas.length; i++) {
            if (datas[i] == key) {
                return i;
            }
        }
        return -1;
    }

    public static <T extends Comparable<T>> int orderSearch(T[] datas, T key) {
        for (int i = 0; i < datas.length; i++) {
            if (datas[i].compareTo(key) == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 二分法查找<p/>
     * 二分法查找的前提是待查找的表必须是有序表（这里的有序表是从小到大排列的）。<p/>
     * 时间复杂度是：O(nlog2n)
     *
     * @param datas 带查找的表
     * @param key   关键字
     * @return 表中关键字的位置，如果不存在则为-1
     */
    public static int binarySearch(int[] datas, int key) {
        int start = 0, end = datas.length - 1;
        while (start <= end) {
            int mid = (start + end) / 2; // 中间位置
            if (key == datas[mid]) {
                return mid;
            } else if (key > datas[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    public static <T extends Comparable<T>> int binarySearch(T[] datas, T key) {
        int start = 0, end = datas.length - 1;
        while (start <= end) {
            int mid = (start + end) / 2; // 中间位置
            if (key.compareTo(datas[mid]) == 0) {
                return mid;
            } else if (key.compareTo(datas[mid]) > 0) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 分块查找。
     * <pre>
     *     将顺序表分成n块，每一块中的数据元素不一定是有序的，
     *     但第一块的最大值必须小于第二块中的最小值，第二块中的最大值必须小于第三块中的最小值，以此类推......
     *     将每一块中的最大值按块的顺序存放在索引表中。
     *     块的查找可以是顺序查找或者二分法查找，块中的数据只能是顺序查找。
     * </pre>
     *
     * @param datas       查找的表
     * @param index       索引表
     * @param key         关键字
     * @param blockLength 每一块的长度（每一块长度都相同）
     * @return 表中关键字的位置，如果不存在则为-1
     */
    public static int blockSearch(int[] datas, int[] index, int key, int blockLength) {
        int idx = 0;
        for (int i = 0; i < index.length; i++) {
            if (index[i] >= key) {
                idx = i;
                break;
            }
        }
        int[] aim = new int[blockLength]; // 关键字所在的块
        int srcPos = blockLength * idx;
        System.arraycopy(datas, srcPos, aim, 0, Math.min(datas.length - srcPos, blockLength));
        int blockIndex = orderSearch(aim, key); // 关键字在块中的位置
        return srcPos + blockIndex;
    }
}
