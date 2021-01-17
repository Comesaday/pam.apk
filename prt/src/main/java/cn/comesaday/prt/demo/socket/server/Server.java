package cn.comesaday.prt.demo.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <描述> 服务端
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-29 14:38
 */
public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(3341);
            Socket socket;
            while ((socket = server.accept()) != null) {
                System.out.println("开始处理客户端请求......");
                Receiver receiver = new Receiver(socket);
                ExecutorService executors = Executors.newCachedThreadPool();
                executors.execute(receiver);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
