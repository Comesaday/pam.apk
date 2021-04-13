package cn.comesaday.example.mode.celue.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <描述> FactoryArrayList
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-13 13:15
 */
public class FactoryArrayList<E extends MatchingBean<K>, K> extends ArrayList<E> implements FactoryList<E, K>, Serializable {

    @Override
    public E getBean(K factor) {
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            E next = iterator.next();
            if (next.matching(factor)) {
                return next;
            }
        }
        return null;
    }
}
