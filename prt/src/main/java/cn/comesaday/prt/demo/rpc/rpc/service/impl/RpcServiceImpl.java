package cn.comesaday.prt.demo.rpc.rpc.service.impl;

import cn.comesaday.prt.demo.rpc.rpc.service.RpcService;
import org.springframework.stereotype.Service;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-06 10:56
 */
@Service
public class RpcServiceImpl implements RpcService {

    @Override
    public String say(String name, Integer score) {
        return "姓名：" + name + "，分数：" + score;
    }
}
