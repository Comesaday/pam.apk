package cn.comesaday.prt.demo.socket.client;

import cn.comesaday.prt.demo.socket.message.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * <描述> 发送消息
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-29 16:19
 */
public class Sender {

    private Socket socket;

    public Sender(Socket socket) {
        this.socket = socket;
    }

    public void send(Message message) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream writer = new ObjectOutputStream(outputStream);
            writer.writeObject(message);
            writer.flush();
            // 及时关闭socket输入输出流，防止盲等导致线程阻塞
            socket.shutdownOutput();

            InputStream inputStream = socket.getInputStream();
            byte[] arr = new byte[1024];
            int len;
            StringBuilder receive = new StringBuilder();
            while ((len = inputStream.read(arr)) != -1) {
                receive.append(new String(arr, 0, len));
            }
            socket.shutdownInput();
            System.out.println("服务端回复:" + receive);
        } catch (IOException e) {
            System.out.println("消息发送异常");
            e.printStackTrace();
        }
    }
}
