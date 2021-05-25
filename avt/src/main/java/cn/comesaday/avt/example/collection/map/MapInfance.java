package cn.comesaday.avt.example.collection.map;

import cn.comesaday.avt.example.collection.CollectionInterface;

/**
 * <描述> MapInfance
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-24 16:13
 */
public interface MapInfance<K, V> extends CollectionInterface {

    void put(K key, V value);

    V get(K key);
}
