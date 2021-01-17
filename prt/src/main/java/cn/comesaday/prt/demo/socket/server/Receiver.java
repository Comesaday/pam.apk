package cn.comesaday.prt.demo.socket.server;

import cn.comesaday.prt.demo.socket.message.Message;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * <描述> 接收消息
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-29 16:19
 */
public class Receiver implements Runnable {

    private Socket socket;

    public Receiver(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            Message message = (Message) ois.readObject();
            socket.shutdownInput();
            System.out.println("接收消息:" + JSONObject.toJSONString(message));
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("收到消息\n".getBytes());
            outputStream.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            System.out.println("服务端接收消息异常");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("消息格式不规范");
            e.printStackTrace();
        }
    }
}
