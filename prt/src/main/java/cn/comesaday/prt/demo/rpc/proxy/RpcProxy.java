package cn.comesaday.prt.demo.rpc.proxy;

import cn.comesaday.prt.demo.rpc.request.RpcRequest;
import cn.comesaday.prt.demo.rpc.response.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-06 17:33
 */
public class RpcProxy {

    private static final Logger logger = LoggerFactory.getLogger(RpcProxy.class);

    private RpcRequest request;

    private RpcResponse response;

    public RpcProxy(RpcRequest request) {
        this.request = request;
    }

    public RpcRequest getRequest() {
        return request;
    }

    public void setRequest(RpcRequest request) {
        this.request = request;
    }

    public RpcResponse getResponse() {
        return response;
    }

    public void setResponse(RpcResponse response) {
        this.response = response;
    }

    public RpcResponse execute() {
        RpcResponse result = null;
        try {
            // 参数准备
            Class clazz = request.getClazz();
            String interfaceName = clazz.getName();
            Method method = clazz.getMethod(request.getMethodName(), request.getParamTypes());
            // 发送请求
            Socket socket = new Socket("127.0.0.1", 3311);
            ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
            stream.writeUTF(interfaceName);
            stream.writeUTF(method.getName());
            stream.writeObject(request.getParamTypes());
            stream.writeObject(request.getParams());
            // 返回结果
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            result = (RpcResponse) inputStream.readObject();
            this.response = result;
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
