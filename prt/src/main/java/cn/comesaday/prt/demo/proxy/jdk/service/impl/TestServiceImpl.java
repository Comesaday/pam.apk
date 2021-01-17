package cn.comesaday.prt.demo.proxy.jdk.service.impl;

import cn.comesaday.prt.demo.proxy.jdk.service.TestService;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-10 16:54
 */
public class TestServiceImpl implements TestService {

    @Override
    public void say() throws Exception {
        System.out.println("你好！");
    }
}
