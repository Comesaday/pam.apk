package cn.comesaday.avt.example.collection.list;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <描述> ListDemo
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-30 09:18
 */
public class ListDemo {

    public static void main(String[] args) {
        Integer[] arr = new Integer[] {1, 2, 3, 4, 5};
        List<Integer> list = Arrays.asList(arr);
        Integer[] ar = new Integer[] {4, 5, 6, 7, 8};
        List<Integer> list1 = Arrays.asList(ar);
        // 交集
        List<Integer> collect = list.stream().filter(n -> isJj(list1, n)).collect(Collectors.toList());
        System.out.println("");
        // list集合减去交集
        List<Integer> collect2 = list.stream().filter(n -> !isJj(list1, n)).collect(Collectors.toList());
        // list1集合减去交集
        List<Integer> collect3 = list1.stream().filter(n -> !isJj(list, n)).collect(Collectors.toList());
        Integer integer = list.stream().max((a, b) -> a > b ? 1 : -1).get();
        System.out.println("");
    }

    private static boolean isJj(List<Integer> list1, Integer n) {
        List<Integer> collect = list1.stream().filter(s -> s == n).collect(Collectors.toList());
        return !CollectionUtils.isEmpty(collect);
    }
}
