package cn.comesaday.prt.demo.rpc.request;

import java.io.Serializable;

/**
 * <描述> 远程调用参数
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-06 17:30
 */
public class RpcRequest implements Serializable {

    private Class<?> clazz;

    private String interfaceName;

    private String methodName;

    private Object[] params;

    private Class<?>[] paramTypes;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = clazz.getName();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }
}
