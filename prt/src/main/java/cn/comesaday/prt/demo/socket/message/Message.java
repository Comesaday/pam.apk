package cn.comesaday.prt.demo.socket.message;

import java.io.Serializable;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-29 16:34
 */
public class Message implements Serializable {

    private Integer code = -1;

    private String message;

    public Message(String message) {
        this.message = message;
    }

    public Message(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
