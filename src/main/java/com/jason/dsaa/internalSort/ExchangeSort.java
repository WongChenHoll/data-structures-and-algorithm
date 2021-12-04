package com.jason.dsaa.internalSort;

/**
 * 交换排序。包括冒泡排序、快速排序
 *
 * @author WangChenHol
 * @date 2021-8-2 16:22
 **/
public class ExchangeSort {

    /**
     * 冒泡排序。
     * <pre>
     *     1.空间复杂度：O(1)。
     *     2.时间复杂度：O(n2)。
     *     3.稳定的排序算法。
     * </pre>
     *
     * @param datas 待排序数据
     */
    public static void bubbleSort(int[] datas) {
        for (int i = 0; i < datas.length - 1; i++) {
            for (int j = 0; j < datas.length - 1 - i; j++) {
                if (datas[j] > datas[j + 1]) {
                    int temp = datas[j];
                    datas[j] = datas[j + 1];
                    datas[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 快速排序，默认从0位置开始，数组最后一个元素之间数据进行排序
     *
     * @param datas 待排序数据
     */
    public static void quickSort(int[] datas) {
        quickSort(datas, 0, datas.length - 1);

    }

    /**
     * 快速排序
     *
     * @param datas 待排序数据
     * @param low   起始位置
     * @param high  结束位置
     */
    public static void quickSort(int[] datas, int low, int high) {
        if (low < high) {
            int partion = partion(datas, low, high);
            quickSort(datas, low, partion - 1);
            quickSort(datas, partion + 1, high);
        }
    }

    /**
     * 获取位置
     */
    private static int partion(int[] datas, int low, int high) {
        int temp = datas[low];
        while (low < high) {
            while (low < high && datas[high] >= temp) {
                high--;
            }
            datas[low] = datas[high];
            while (low < high && datas[low] <= temp) {
                low++;
            }
            datas[high] = datas[low];
        }
        datas[low] = temp;
        return low;
    }
}
