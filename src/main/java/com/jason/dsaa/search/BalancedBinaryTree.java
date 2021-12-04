package com.jason.dsaa.search;


import com.jason.dsaa.linearity.queue.LinkedQueue;
import com.jason.dsaa.linearity.stacks.LinkedStack;
import com.jason.dsaa.nodes.AVLTreeNode;

/**
 * 平衡二叉树。
 * <pre>
 *     平衡二叉树更适用于二叉排序树一经建立就很少进行插入和删除操作。
 *     优点：使二叉树的结构更好，提高查找的速度。
 *     在平衡二叉树上查找的时间复杂度：O(log2n)
 * </pre>
 *
 * @author WangChenHol
 * @date 2021-8-18 16:17
 **/
public class BalancedBinaryTree<E extends Comparable<E>> {

    private int depth; // 树的深度
    private int nodeNumber; // 结点个数
    private AVLTreeNode<E> root;

    public static final String INSERT_LOCATION_LEFT = "LEFT";
    public static final String INSERT_LOCATION_RIGHT = "RIGHT";

    public BalancedBinaryTree() {
        this.root = null;
    }

    /**
     * 层次遍历
     *
     * @return 层次遍历的结果
     * @throws Exception 数据异常
     */
    public String levelTraversal() throws Exception {
        StringBuilder traversal = new StringBuilder("平衡二叉树的层次遍历：[");
        if (root == null) {
            return "";
        }
        AVLTreeNode<E> node = root;
        LinkedQueue<AVLTreeNode<E>> queue = new LinkedQueue<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            node = queue.poll();
            traversal.append(node.data.toString()).append(",");
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        traversal.delete(traversal.length() - 1, traversal.length());
        traversal.append("]");
        return traversal.toString();
    }

    public AVLTreeNode<E> searchBalanceBinaryTree(E data) {
        if (data == null) {
            return null;
        }
        return searchBalanceBinaryTree(this.root, data);
    }

    private AVLTreeNode<E> searchBalanceBinaryTree(AVLTreeNode<E> root, E data) {
        if (root == null) {
            return null;
        }
        if (root.data.compareTo(data) == 0) {
            return root;
        }
        if (root.data.compareTo(data) > 0) {
            return searchBalanceBinaryTree(root.left, data);
        }
        if (root.data.compareTo(data) < 0) {
            return searchBalanceBinaryTree(root.right, data);
        }
        return null;
    }

    public AVLTreeNode<E> delete(E data) {
        AVLTreeNode<E> delete = searchBalanceBinaryTree(data);
        if (delete == null) {
            return null;
        }

        LinkedQueue<AVLTreeNode<E>> queue = new LinkedQueue<>();
        AVLTreeNode<E> rootNode = delete;
        while (rootNode != null) {
            queue.offer(rootNode);
            rootNode = rootNode.parent;
        }
        // 删除的是父级的右子树
        if (delete.data.equals(delete.parent.right.data)) {
            if (delete.right != null && delete.left != null) {
                AVLTreeNode<E> left = delete.left;
                AVLTreeNode<E> right = delete.right;
                delete.parent.right = delete.right;
                right.parent = delete.parent;

                AVLTreeNode<E> node = right;
                while (node.left != null) {
                    node = node.left;
                }
                node.left = left;
                left.parent = node;
            } else if (delete.right != null) {
                delete.parent.right = delete.right;
                delete.right.parent = delete.parent;
            } else if (delete.left != null) {
                delete.parent.right = delete.left;
                delete.left.parent = delete.parent;
            } else {
                delete.parent.right = null;
            }
        }

        // 删除的是父级的左子树
        if (delete.data.equals(delete.parent.left.data)) {
            if (delete.right != null && delete.left != null) {
                AVLTreeNode<E> left = delete.left;
                AVLTreeNode<E> right = delete.right;
                delete.parent.left = delete.right;
                right.parent = delete.parent;

                AVLTreeNode<E> node = right;
                while (node.left != null) {
                    node = node.left;
                }
                node.left = left;
                left.parent = node;
            } else if (delete.right != null) {
                delete.parent.left = delete.right;
                delete.right.parent = delete.parent;
            } else if (delete.left != null) {
                delete.parent.left = delete.left;
                delete.left.parent = delete.parent;
            } else {
                delete.parent.right = null;
            }
        }
        return delete;
    }

    /**
     * 插入结点
     *
     * @param data 新插入结点的数据
     */
    public void insert(E data) {
        if (data == null) {
            return;
        }
        if (this.root == null) {
            this.root = new AVLTreeNode<>(data);
            return;
        }
        LinkedStack stack = new LinkedStack();
        insert(root, data, stack);
        rotate(stack);
    }

    /**
     * 插入新结点。每次插入结点时，将新结点的所经历的父结点入栈
     *
     * @param root  根结点
     * @param data  新插入结点的数据
     * @param stack 插入结点的父结点入栈
     * @return 新插入的结点
     */
    private AVLTreeNode<E> insert(AVLTreeNode<E> root, E data, LinkedStack stack) {

        // 插入的结点小于根结点，则将新结点作为根结点的左子树插入
        if (root.data.compareTo(data) > 0) {
            stack.push(root);
            if (root.left == null) {
                root.left = new AVLTreeNode<>(data, root);
                return root.left;
            } else {
                return insert(root.left, data, stack);
            }
        }
        // 插入的结点大于根结点，则将新结点作为根结点的右子树插入
        if (root.data.compareTo(data) < 0) {
            stack.push(root);
            if (root.right == null) {
                root.right = new AVLTreeNode<>(data, root);
                return root.right;
            } else {
                return insert(root.right, data, stack);
            }
        }
        return null;
    }

    /**
     * 如果新插入结点后失去平衡，则需要进行旋转。
     *
     * @param stack 父结点的栈
     */
    private void rotate(LinkedStack stack) {
        // 出栈，从树的顶部往上，依次判断结点根据平衡因子是否需要旋转
        AVLTreeNode<E> node;
        while (!stack.isEmpty()) {
            // 根据父结点的平衡因子判断是否需要旋转
            node = (AVLTreeNode<E>) stack.pop();
            if (node == null) {
                continue;
            }
            int factor = balanceFactor(node);
            // 如果平衡因子大于等于2时，则需要进行LL、LR旋转
            if (factor >= 2) {
                // 如果父结点的左结点的平衡因子等于1，则是LL旋转，如果是-1则是LR旋转
                if (balanceFactor(node.left) == 1) {
                    rotate_LL(node);
                } else {
                    rotate_LR(node);
                }
            }
            // 如果平衡因子小于等于-2时，则需要进行RR、RL旋转
            if (factor <= -2) {
                // 如果父结点的右结点的平衡因子等于-1，则是RR旋转，如果是1则是RL旋转
                if (balanceFactor(node.right) == -1) {
                    rotate_RR(node);
                } else {
                    rotate_RL(node);
                }
            }
        }
    }

    /**
     * LL型平衡旋转（单向右旋）
     * 在跟结点的左孩子的左子树上插入新结点。
     *
     * @param root 结点
     */
    private void rotate_LL(AVLTreeNode<E> root) {
        AVLTreeNode<E> parent = root.parent; // 根结点的父结点
        AVLTreeNode<E> middle = root.left; // 根节点的左子树，也就是需要旋转的结点

        root.left = middle.right;
        if (root.left != null) {
            root.left.parent = root;
        }
        middle.right = root;
        middle.right.parent = middle;

        if (parent == null) {
            this.root = middle;
            middle.parent = null;
        } else {
            if (parent.left.data.equals(root.data)) {
                parent.left = middle;
            } else {
                parent.right = middle;
            }
            middle.parent = parent;
        }
    }

    /**
     * RR型平衡旋转（单向左旋）
     * 在跟结点的右孩子的右子树上插入新结点。
     *
     * @param root 结点
     */
    private void rotate_RR(AVLTreeNode<E> root) {
        AVLTreeNode<E> parent = root.parent; // 根结点的父结点
        AVLTreeNode<E> middle = root.right; // 根结点的右子树

        root.right = middle.left;
        if (root.right != null) {
            root.right.parent = root;
        }
        middle.left = root;
        middle.left.parent = middle;

        if (parent == null) {
            this.root = middle;
            middle.parent = null;
        } else {
            if (parent.left.data.equals(root.data)) {
                parent.left = middle;
            } else {
                parent.right = middle;
            }
            middle.parent = parent;
        }
    }

    /**
     * LR型平衡旋转（先左旋后右旋）
     * 在跟结点的左孩子的右子树上插入新结点。
     *
     * @param root 结点
     */
    private void rotate_LR(AVLTreeNode<E> root) {
        AVLTreeNode<E> parent = root.parent; // 根结点的父结点
        AVLTreeNode<E> middle = root.left; // 中间的结点
        AVLTreeNode<E> leaf = root.left.right; // 叶子结点

        // 将叶子结点的左子树调整为中间结点的右子树、叶子结点的右子树调整为根结点的左子树，并更改叶子结点左右子树的父结点。
        middle.right = leaf.left;
        if (middle.right != null) {
            middle.right.parent = middle;
        }
        root.left = leaf.right;
        if (root.left != null) {
            root.left.parent = root;
        }
        // 将中间结点调整为叶子结点的左子树，根结点调整为叶子结点的右子树，并更改根结点和中间结点的父结点
        leaf.left = middle;
        leaf.right = root;
        middle.parent = leaf;
        root.parent = leaf;
        // 调整叶子结点为新的根结点，并调整其父结点
        if (parent == null) {
            this.root = leaf;
            leaf.parent = null;
        } else {
            if (parent.left.data.equals(root.data)) {
                parent.left = leaf;
            } else {
                parent.right = leaf;
            }
            leaf.parent = parent;
        }
    }

    /**
     * RL型平衡旋转（先左旋后右旋）
     * 在跟结点的右孩子的右子树上插入新结点。
     *
     * @param root 结点
     */
    private void rotate_RL(AVLTreeNode<E> root) {
        AVLTreeNode<E> parent = root.parent;
        AVLTreeNode<E> middle = root.right;
        AVLTreeNode<E> leaf = root.right.left;

        root.right = leaf.left;
        if (root.right != null) {
            root.right.parent = root;
        }
        middle.left = leaf.right;
        if (middle.left != null) {
            middle.left.parent = middle;
        }

        leaf.left = root;
        leaf.right = middle;
        leaf.left.parent = leaf;
        leaf.right.parent = leaf;

        if (parent == null) {
            this.root = leaf;
            leaf.parent = null;
        } else {
            if (parent.left.data.equals(root.data)) {
                parent.left = leaf;
            } else {
                parent.right = leaf;
            }
            leaf.parent = parent;
        }
    }


    /**
     * 求某个跟结点的平衡因子
     *
     * @param root 根结点
     * @return 平衡因子（左子树深度-右子树的深度）。<p/>
     * -1：左子树的深度比右子树的深度少1。<p/>
     * 0：左右子树的深度相同。<p/>
     * 1：左子树的深度比右子树的深度多1。
     */
    public int balanceFactor(AVLTreeNode<E> root) {
        if (root == null) {
            return 0;
        }
        return depth(root.left) - depth(root.right);
    }

    /**
     * 求根结点的深度
     *
     * @param node 孩子结点
     * @return 深度
     */
    public int depth(AVLTreeNode<E> node) {
        if (node != null) {
            int leftDepth = depth(node.left);
            int rightDepth = depth(node.right);
            return Math.max(leftDepth, rightDepth) + 1;
        }
        return 0;

    }

    /**
     * 求某个结点的叶子个数
     *
     * @param root 根结点
     * @return 叶子个数
     */
    public int leaves(AVLTreeNode<E> root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return leaves(root.left) + leaves(root.right);
    }

    public int getDepth() {
        return depth;
    }

    public int getNodeNumber() {
        return nodeNumber;
    }
}
