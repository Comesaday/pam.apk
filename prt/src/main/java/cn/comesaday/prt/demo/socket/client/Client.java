package cn.comesaday.prt.demo.socket.client;

import cn.comesaday.prt.demo.socket.message.Message;
import java.io.IOException;
import java.net.Socket;

/**
 * <描述> 客户端
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-29 14:39
 */
public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 3341);
            Sender sender = new Sender(socket);
            Message message = new Message("你好啊啊啊啊啊");
            sender.send(message);
        } catch (IOException e) {
            System.out.println("未找到远程服务");
            e.printStackTrace();
        }
    }
}
