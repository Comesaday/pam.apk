package cn.comesaday.prt.demo.rpc.response;


import cn.comesaday.prt.demo.rpc.response.tips.RpcTip;

import java.io.Serializable;

/**
 * <描述> rpc调用结果
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-06 13:23
 */
public class RpcResponse implements Serializable {

    public boolean success = true;

    // 提示
    private RpcTip tip;

    // 耗时
    private Long time;

    // 返回数据
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public RpcTip getTip() {
        return tip;
    }

    public void setTip(RpcTip tip) {
        this.tip = tip;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 错误信息
     * @param code 错误码
     * @param message 错误提示
     */
    public void setError(String code, String message) {
        RpcTip tip = new RpcTip(code, message);
        this.success = false;
        this.tip = tip;
    }

    /**
     * 失败信息
     * @return
     */
    public RpcTip getError() {
        if (!success) {
            return tip;
        }
        return null;
    }

    /**
     * 成功返回数据
     * @return
     */
    public Object getData() {
        if (success) {
            return data;
        }
        return null;
    }
}
