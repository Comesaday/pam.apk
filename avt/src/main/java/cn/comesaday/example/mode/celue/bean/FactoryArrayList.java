package cn.comesaday.example.mode.celue.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.OrderComparator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * <描述> FactoryArrayList
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-13 13:15
 */
public class FactoryArrayList<E extends MatchingBean<K>, K> extends ArrayList<E>
        implements FactoryList<E, K>, InitializingBean {

    public FactoryArrayList() {
    }

    public FactoryArrayList(int size) {
        super(size);
    }

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

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!this.isEmpty()) {
            Object[] a = this.toArray();
            OrderComparator.sort(a);
            ListIterator i = this.listIterator();
            for(int j = 0; j < a.length; ++j) {
                i.next();
                i.set(a[j]);
            }
        }
    }
}
