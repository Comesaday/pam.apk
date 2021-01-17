package cn.comesaday.prt.demo.rpc.rpc.provider;

import cn.comesaday.coe.common.util.DateUtil;
import cn.comesaday.prt.demo.rpc.response.RpcResponse;
import cn.comesaday.prt.demo.rpc.rpc.service.RpcService;
import cn.comesaday.prt.demo.rpc.rpc.service.impl.RpcServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-06 10:58
 */
public class Provider {

    private final static Logger logger = LoggerFactory.getLogger(Provider.class);

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(3311);
        Map<Object, Object> services = new HashMap<>();
        services.put(RpcService.class, new RpcServiceImpl());
        while (true) {
            // 服务注册、启动
            Socket socket = serverSocket.accept();
            long startTime = System.currentTimeMillis();
            RpcResponse rpcResponse = new RpcResponse();
            try {
                // 接收请求、解码
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String interfaceName = inputStream.readUTF();
                String methodName = inputStream.readUTF();
                Class<?>[] paramTypes = (Class<?>[]) inputStream.readObject();
                Object[] parames = (Object[]) inputStream.readObject();

                // 调用本地服务
                Class<?> aClass = Class.forName(interfaceName);
                Object service = services.get(aClass);
                Method method = aClass.getMethod(methodName, paramTypes);
                Object result = method.invoke(service, parames);
                rpcResponse.setData(result);
            } catch (Exception e) {
                rpcResponse.setError("501", e.getMessage());
                logger.error(e.getMessage(), e);
            } finally {
                long endTime = System.currentTimeMillis();
                logger.info("调用时间：{} ~ {}", DateUtil.formatLDate(startTime), DateUtil.formatLDate(endTime));
                rpcResponse.setTime(endTime - startTime);
                // 向调用者发送结果
                ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
                stream.writeObject(rpcResponse);
            }
        }
    }
}
