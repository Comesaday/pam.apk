package cn.comesaday.avt.example.collection;

import cn.comesaday.avt.example.collection.list.MyArrayList;
import cn.comesaday.avt.example.collection.list.MyLinkedList;
import cn.comesaday.avt.example.collection.map.MyHashMap;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <描述>
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-24 11:27
 */
@Component
public class CollectionTest {

    public void test() {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        myArrayList.add(11);
        myArrayList.add(22);
        myArrayList.add(33);
        myArrayList.add(44);
        myArrayList.add(55);
        myArrayList.add(66);
        myArrayList.add(77);
        myArrayList.add(88);
        myArrayList.add(99);
        myArrayList.add(91);
        myArrayList.add(92);
        System.out.println(myArrayList.get(1));

        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
        myLinkedList.add(11);
        myLinkedList.add(22);
        myLinkedList.add(33);
        myLinkedList.add(44);
        System.out.println(myLinkedList.get(1));
        System.out.println(Math.log(1) / Math.log(2));

        MyHashMap<Integer, Integer> myHashMap = new MyHashMap<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            myHashMap.put(i, i);
        }
        long end = System.currentTimeMillis();
        System.out.println("myHashMap耗时:" + (end - start));

        Map<Integer, Integer> map = new HashMap<>();
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            map.put(i, i);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("map耗时:" + (end1 - start1));
        System.out.println(myHashMap.get(12));
    }
}
