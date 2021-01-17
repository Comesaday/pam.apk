package cn.comesaday.prt.demo.struct.tree;

import com.alibaba.fastjson.JSONObject;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-14 17:43
 */
public class DataStruct {

    public static void main(String[] args) {
        int[] data = {6, 4, 10, 3, 7, 5, 2};
        Tree tree = new Tree();
        for (int i = 0; i < data.length; i++) {
            tree.add(tree, data[i]);
        }
        System.out.println("二叉树数据：" + JSONObject.toJSONString(tree));
        System.out.println("先序遍历");
        tree.preOrder(tree);
        System.out.println("中序遍历");
        tree.middleOrder(tree);
        System.out.println("后序遍历");
        tree.laterOrder(tree);
    }
}
