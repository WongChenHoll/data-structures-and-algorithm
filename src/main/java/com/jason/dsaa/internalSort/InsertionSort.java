package com.jason.dsaa.internalSort;

/**
 * 插入排序。包括直接插入排序和希尔排序
 *
 * @author WangChenHol
 * @date 2021-7-23 16:20
 **/
public class InsertionSort {

    /**
     * 直接插入排序算法.泛型
     *
     * @param datas 数据
     * @param <T>   数据类型
     */
    public static <T extends Comparable<T>> void insertSort(T[] datas) {
        if (datas == null) {
            throw new NullPointerException("数组不能为空");
        }
        int length = datas.length;
        if (length <= 1) {
            return;
        }
        for (int i = 1; i < length; i++) {
            T value = datas[i];
            int j = i - 1;
            for (; j >= 0 && datas[j].compareTo(value) > 0; j--) {
                datas[j + 1] = datas[j];
            }
            datas[j + 1] = value;
        }
    }

    /**
     * 直接插入排序算法。顺序地把待排序的各个记录按其关键字插入到已排序后的适当位置处。<p/>
     * <pre>
     *      将数组中的数据分为两个区间，已排序区间和未排序区间。初始已排序区间只有一个元素，就是数组的第一个元素。
     *      插入算法的核心思想是取未排序区间中的元素，在已排序区间中找到合适的插入位置将其插入，并保证已排序区间数据一直有序。
     *      1.此算法空间复杂度是O(1)。
     *      2.最好的时间复杂度O(n)，最坏时间复杂度是O(n2)。所以平均时间复杂度为 O(n2)。
     *      3.是一种稳定的排序算法。
     * </pre>
     */
    public static void insertSort(int[] datas) {
        if (datas == null) {
            throw new NullPointerException("数组不能为空");
        }
        int length = datas.length;
        if (length <= 1) {
            return;
        }
//        noGuard(datas, length);
        withGuard(datas, length);
    }

    /**
     * 不带监视哨的直接插入算法
     *
     * @param datas  待排序数组
     * @param length 数组长度
     */
    private static void noGuard(int[] datas, int length) {
        for (int i = 1; i < length; i++) {
            int value = datas[i];
            int j = i - 1;
            // for循环两种写法
// 方法一、
//            for (; j >= 0; j--) {
//                if (datas[j] > value) {
//                    datas[j + 1] = datas[j];
//                } else {
//                    break;
//                }
//            }
// 方法二、
            for (; j >= 0 && datas[j] > value; j--) {
                datas[j + 1] = datas[j];
            }
            datas[j + 1] = value;
        }
    }

    /**
     * 带监视哨的直接插入算法。此算法适用于数据量很大的情况
     *
     * @param datas  待排序数组
     * @param length 数组长度
     */
    private static void withGuard(int[] datas, int length) {
        int[] copy = new int[length + 1];
        System.arraycopy(datas, 0, copy, 1, length);
        for (int i = 1; i < length; i++) {
            copy[0] = copy[i];
            int j = i - 1;
            for (; copy[j] > copy[0]; j--) {
                copy[j + 1] = copy[j];
            }
            copy[j + 1] = copy[0];
        }
        System.arraycopy(copy, 1, datas, 0, length);
    }

    /**
     * 希尔排序。<p/>
     * <pre>
     *     1.选取一个小于datas.length的整数d称为增量。将表中数据从下表0开始间隔d位的数据组成一个个子表。
     *     2.对每个字表进行直接插入排序。
     *     3.逐步减小增量d的值，重复上述动作，直至d=1.
     * </pre>
     *
     * @param datas 数据
     */
    public static void shellSort(int[] datas) {
        for (int d = datas.length / 2; d > 0; d = d / 2) { // 增量d，并且逐步减小，直至为1.
            for (int i = d; i < datas.length; i++) {
                int temp = datas[i];
                int j = i - d;
                for (; j >= 0 && datas[j] > temp; j -= d) {
                    datas[j + d] = datas[j];
                }
                datas[j + d] = temp;
            }
        }
    }


}
