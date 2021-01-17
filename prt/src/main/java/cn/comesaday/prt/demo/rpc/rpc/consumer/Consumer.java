package cn.comesaday.prt.demo.rpc.rpc.consumer;

import cn.comesaday.prt.demo.rpc.proxy.RpcProxy;
import cn.comesaday.prt.demo.rpc.request.RpcRequest;
import cn.comesaday.prt.demo.rpc.response.RpcResponse;
import cn.comesaday.prt.demo.rpc.rpc.service.RpcService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-06 10:59
 */
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] args) {
        long time = 0;
        for (int i = 0; i < 1000000; i++) {
            RpcRequest request = new RpcRequest();
            request.setClazz(RpcService.class);
            request.setMethodName("say");
            Object[] params = {"测试的", 100};
            request.setParams(params);
            Class<?>[] paramTypes = {String.class, Integer.class};
            request.setParamTypes(paramTypes);
            RpcResponse response = new RpcProxy(request).execute();
            time += response.getTime();
            logger.info(JSONObject.toJSONString(response));
        }
        logger.info("总时长：" + time);
    }
}
