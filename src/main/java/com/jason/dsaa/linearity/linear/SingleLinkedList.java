package com.jason.dsaa.linearity.linear;

import com.jason.structure.nodes.Node;

/**
 * 单链表实现
 *
 * @author WangChenHol
 * @date 2021/1/22 14:24
 **/
public class SingleLinkedList extends LinkedListAbstract implements LinearList {

    public final Node head; // 头指针。线性表的第一个结点的存储地址称为头指针。
    private boolean order = false; // true：头插法，false：尾插法

    public SingleLinkedList() {
        this.head = new Node(); // 初始化头结点
    }

    public SingleLinkedList(boolean order) {
        this();
        this.order = order;
    }

    public SingleLinkedList(Node head) {
        this.head = head;
        this.order = false;
    }

    @Override
    public void clear() {
        this.head.data = null;
        this.head.next = null;
    }

    @Override
    public boolean isEmpty() {
        return this.head.next == null;
    }

    @Override
    public int length() {
        Node node = this.head.next;
        int length = 0;
        while (node != null) {
            node = node.next;
            ++length;
        }
        return length;
    }

    @Override
    public Object get(int i) throws Exception {
        int len = 0;
        Node next = this.head.next;
        while (next != null && len < i) {
            next = next.next;
            ++len;
        }
        if (next == null || len > i) {
            throw new Exception("元素不存在");
        }
        return next.data;
    }

    @Override
    public void insert(int i, Object e) throws Exception {
        Node node = this.head;
        int index = -1;
        while (node != null && index < i - 1) { // 找到第i个结点的前驱
            node = node.next;
            ++index;
        }
        if (node == null || index > i - 1) {
            throw new Exception("位置不合法");
        }
        Node add = new Node(e);
        // 下面两行顺序不能颠倒
        add.next = node.next;
        node.next = add;
    }

    @Override
    public void add(Object object) {
        Node node = new Node(object);
        if (order) {// 头插法
            Node next = head.next;
            head.next = node;
            node.next = next;
        } else { // 尾插法
            Node p = head;
            while (p.next != null) {
                p = p.next;
            }
            p.next = node;
        }
    }

    @Override
    public Object remove(int i) throws Exception {
        Node node = this.head;
        int len = -1;
        while (node.next != null && len < i - 1) { // 找到第i个结点的前驱结点
            node = node.next;
            ++len;
        }
        if (len > i - 1 || node.next == null) {
            throw new Exception("删除的位置不合法");
        }
        Object data = node.next.data; // 被删除的数据
        node.next = node.next.next; // 修改指针链接，释放被删除的数据元素
        return data;
    }

    @Override
    public int indexOf(Object e) {
        int len = 0;
        Node node = head.next;
        while (node != null && !node.data.equals(e)) {
            node = node.next;
            ++len;
        }
        if (node != null) {
            return len;
        } else {
            return -1;
        }
    }

    @Override
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node node = head.next;
        extracted(sb, node);
        return sb.toString();
    }

    private void extracted(StringBuilder sb, Node node) {
        while (node != null) {
            sb.append(node.data).append(", ");
            node = node.next;
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
    }

    @Override
    public SingleLinkedList reverse() {
        Node curr = this.head.next;
        Node fNode = null;
        while (curr != null) {
            Node next = curr.next;
            curr.next = fNode;
            fNode = curr;
            curr = next;

        }
        this.head.next = fNode;
        return this;
    }

    /**
     * 合并两个有序的链表
     *
     * @param list1 有序链表1
     * @param list2 有序链表2
     * @return 合并后的有序链表
     */
    public SingleLinkedList merge(final SingleLinkedList list1, final SingleLinkedList list2) {

        if (isEmpty(list1) && isEmpty(list2)) {
            throw new NullPointerException("两个链表都是空");
        }
        if (isEmpty(list1)) {
            return list2;
        }
        if (isEmpty(list2)) {
            return list1;
        }
        Node n1 = list1.head.next;
        Node n2 = list2.head.next;

        Node head = new Node();
        Node node = head;
        while (n1 != null && n2 != null) {
            Object d1 = n1.data;
            Object d2 = n2.data;
            // 数据大小按hash值比较
            if (d1.hashCode() < d2.hashCode()) {
                node.next = n1;
                n1 = n1.next;
            } else {
                node.next = n2;
                n2 = n2.next;
            }
            node = node.next;
        }
        if (n1 == null) {
            node.next = n2;
        }
        if (n2 == null) {
            node.next = n1;
        }
        return new SingleLinkedList(head);
    }

    private boolean isEmpty(SingleLinkedList list) {
        return list == null || list.head.next == null;
    }

    @Override
    public Node middleNode() {
        if (this.head.next == null) {
            return null;
        }
        Node node = this.head.next;
        // 方式1
//        int m = length() / 2;
//        int i = 0;
//        while (i < m) {
//            node = node.next;
//            i++;
//        }
//        return node;

        // 方式2
        Node fast = node;
        Node slow = node;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    @Override
    public Node deleteLastNode(int index) {
        int length = length();
        if (index > length || index < 1) {
            throw new IndexOutOfBoundsException("不存在的结点");
        }
        Node node = this.head.next;
        int i = 0;
        while ((length - i) > index + 1) {
            node = node.next;
            i++;
        }
        Node next = node.next;
        if (index == 1) {
            node.next = null;
        } else if (index == length) {
            this.head.next = node.next;
        } else {
            node.next = node.next.next;
        }
        return next;
    }

    @Override
    public boolean detectionLinkedlistCentral() {
        return false;
    }
}
