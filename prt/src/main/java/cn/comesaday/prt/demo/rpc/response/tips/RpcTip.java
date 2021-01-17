package cn.comesaday.prt.demo.rpc.response.tips;

/**
 * <描述> 调用提示
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-06 13:39
 */
public class RpcTip {

    // 返回码
    private String code;

    // 返回提示
    private String message;

    public RpcTip(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
