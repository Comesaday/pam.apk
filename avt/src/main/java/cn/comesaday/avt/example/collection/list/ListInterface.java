package cn.comesaday.avt.example.collection.list;

import cn.comesaday.avt.example.collection.CollectionInterface;

/**
 * <描述> ListInterface
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-24 13:37
 */
public interface ListInterface<E> extends CollectionInterface {

    boolean exist(int index);

    void add(E element);

    void add(int index, E element);

    void delete(int index);

    void delete(E element);

    E get(int index);

    void update(int index, E element);
}
