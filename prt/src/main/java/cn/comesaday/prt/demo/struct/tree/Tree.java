package cn.comesaday.prt.demo.struct.tree;

/**
 * <描述> 二叉树
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-14 17:47
 */
public class Tree {

    // 根节点值
    private Integer data;

    // 左节点
    private Tree left;

    // 右节点
    private Tree right;

    public Tree() {}

    public Tree(Integer data) {
        this.data = data;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public Tree getLeft() {
        return left;
    }

    public void setLeft(Tree left) {
        this.left = left;
    }

    public Tree getRight() {
        return right;
    }

    public void setRight(Tree right) {
        this.right = right;
    }

    public void add(Tree tree, int data) {
        Integer da = tree.getData();
        if (null == da) {
            this.data = data;
        }
        else if (data < da) {
            // 左节点
            Tree leftTree = tree.getLeft();
            if (null == leftTree) {
                leftTree = new Tree(data);
                tree.setLeft(leftTree);
            } else {
                tree.add(leftTree, data);
            }
        } else {
            // 右节点
            Tree rightTree = tree.getRight();
            if (null == rightTree) {
                rightTree = new Tree(data);
                tree.setRight(rightTree);
            } else {
                tree.add(rightTree, data);
            }
        }
    }

    public void preOrder(Tree tree) {
        System.out.println(tree.getData());
        if (null != tree.getLeft()) {
            preOrder(tree.getLeft());
        }
        if (null != tree.getRight()) {
            preOrder(tree.getRight());
        }
    }

    public void middleOrder(Tree tree) {
        if (null != tree.getLeft()) {
            preOrder(tree.getLeft());
        }
        System.out.println(tree.getData());
        if (null != tree.getRight()) {
            preOrder(tree.getRight());
        }
    }

    public void laterOrder(Tree tree) {
        if (null != tree.getLeft()) {
            preOrder(tree.getLeft());
        }
        if (null != tree.getRight()) {
            preOrder(tree.getRight());
        }
        System.out.println(tree.getData());
    }
}
