package cn.comesaday.avt.example.mode.celue.sevice;

import cn.comesaday.avt.example.mode.celue.bean.MatchingBean;

/**
 * <描述> ExampleService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-13 20:01
 */
public interface ExampleService extends MatchingBean<Integer> {

    String apply();

    @Override
    boolean matching(Integer factor);
}
