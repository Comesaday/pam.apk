package cn.comesaday.example.mode.celue.bean;

import java.util.List;

/**
 * <描述> FactoryList
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-13 13:13
 */
public interface FactoryList<E extends MatchingBean<K>, K> extends List<E> {

    E getBean(K factor);
}
