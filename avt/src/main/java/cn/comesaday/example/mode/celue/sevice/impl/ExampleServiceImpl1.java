package cn.comesaday.example.mode.celue.sevice.impl;

import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.example.mode.celue.sevice.ExampleService;

/**
 * <描述> ExampleServiceImpl1
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-13 19:50
 */
public class ExampleServiceImpl1 implements ExampleService {

    @Override
    public boolean matching(Integer factor) {
        return NumConstant.I1 == factor;
    }
}
