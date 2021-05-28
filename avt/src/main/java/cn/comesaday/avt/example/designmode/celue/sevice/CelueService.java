package cn.comesaday.avt.example.designmode.celue.sevice;

import cn.comesaday.avt.example.designmode.celue.bean.MatchingBean;

/**
 * <描述> ExampleService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-13 20:01
 */
public interface CelueService extends MatchingBean<Integer> {

    String apply();

    @Override
    boolean matching(Integer factor);
}
