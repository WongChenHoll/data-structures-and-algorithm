package com.jason.dsaa.internalSort;

/**
 * 归并排序。
 *
 * @author WangChenHol
 * @date 2021-8-5 16:14
 **/
public class MegeringSort {

    /**
     * 归并排序。将两个相邻的有序表合并成一个有序表。
     * 将原数组中的src[start]-src[middle]和src[middle+1]-src[end]排序并复制都dest[start]-dest[end]
     *
     * @param src    源数据
     * @param dest   合并后的数据
     * @param start  源数组中有序序列1起始位置
     * @param middle 源数组中有序列1的终止位置，同时也是有序序列2的起始位置
     * @param end    有序序列2的终止位置
     */
    public static void meger(int[] src, int[] dest, int start, int middle, int end) {

        int i = start;
        int j = middle + 1;
        int k = start;
        while (i <= middle && j <= end) {
            if (src[i] <= src[j]) {
                dest[k++] = src[i++];
            } else {
                dest[k++] = src[j++];
            }
        }
        while (i <= middle) {
            dest[k++] = src[i++];
        }
        while (j <= end) {
            dest[k++] = src[j++];
        }

    }

    /**
     * 归并排序，将两个有序序列src1和src2合并到dest中。
     *
     * @param src1 源有序序列1
     * @param src2 源有序序列2
     * @param dest 合并后的目标序列
     */
    public static void meger(int[] src1, int[] src2, int[] dest) {
        int length1 = src1.length;
        int length2 = src2.length;

        int i = 0, j = 0, k = 0;
        while (i <= length1 - 1 && j <= length2 - 1) {
            if (src1[i] <= src2[j]) {
                dest[k++] = src1[i++];
            } else {
                dest[k++] = src2[j++];
            }
        }
        while (i <= length1 - 1) {
            dest[k++] = src1[i++];
        }
        while (j <= length2 - 1) {
            dest[k++] = src2[j++];
        }
    }

    /**
     * 一趟归并排序算法
     *
     * @param src  源序列
     * @param dest 目标序列
     * @param s    源序列的子序列长度
     * @param n    源序列的长度
     */
    public static void megerPass(int[] src, int[] dest, int s, int n) {
        int position = 0; // 每一对待合并表的第一个元素的下标，初始值是0
        while (position + 2 * s - 1 < n - 1) {
            meger(src, dest, position, position + s - 1, position + 2 * s - 1);
            position = position + 2 * s;
        }
        if (position + s - 1 < n - 1) {
            meger(src, dest, position, position + s - 1, n - 1);
        } else {
            for (int i = position; i <= n - 1; i++) {
                dest[i] = src[i];
            }
        }
    }

    /**
     * 二路归并排序
     *
     * @param datas 待排序序列
     */
    public static void megerSort(int[] datas) {
        int s = 1;
        int length = datas.length;
        int[] temp = new int[length];

        while (s < length) {
            megerPass(datas, temp, s, length);
            s = s * 2;
            megerPass(temp, datas, s, length);
            s = s * 2;
        }
    }
}
