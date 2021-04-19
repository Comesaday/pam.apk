package cn.comesaday.example.mode.celue.bean;

/**
 * <描述> MatchingBean
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-13 13:12
 */
public interface MatchingBean<K> {
    // 匹配比较器
    boolean matching(K factor);
}
