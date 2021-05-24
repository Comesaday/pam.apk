package cn.comesaday.avt.example.collection.test;

import cn.comesaday.avt.example.collection.list.MyArrayList;
import cn.comesaday.avt.example.collection.list.MyLinkedList;
import cn.comesaday.avt.example.collection.map.MyHashMap;

import java.util.HashMap;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-05-24 11:27
 */
public class CollectionTest {

    public static void main(String[] args) {
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
        myHashMap.put(11, 11);
        myHashMap.put(22, 22);
        myHashMap.put(22, 22);
        System.out.println("");
    }
}
