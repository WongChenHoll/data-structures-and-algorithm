package com.jason.dsaa.linearity.linear;

import com.jason.structure.nodes.Node;

/**
 * @author WangChenHol
 * @date 2021-9-7 10:24
 **/
public abstract class LinkedListAbstract {

    /**
     * 单链表的反转
     * @return 反转后的链表
     */
    protected abstract LinkedListAbstract reverse();


    /**
     * 求链表的中间结点
     *
     * @return 返回中间的结点
     */
    protected abstract Node middleNode();

    /**
     * 删除链表中的倒数第index个结点
     *
     * @param index 结点的位置
     * @return 删除的结点
     */
    protected abstract Node deleteLastNode(int index);

    /**
     * 链表中环的检测
     *
     * @return 检测结果
     */
    protected abstract boolean detectionLinkedlistCentral();
}
