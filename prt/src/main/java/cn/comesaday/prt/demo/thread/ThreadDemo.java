package cn.comesaday.prt.demo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-24 11:21
 */
public class ThreadDemo {

    private final static Logger logger = LoggerFactory.getLogger(ThreadDemo.class);

    private static String code = "000";

    public static void main(String[] args) {
        Ticket ticket = new Ticket(1);
        Thread thread = new Thread(ticket);
        thread.setName("1");
        Thread thread2 = new Thread(ticket);
        thread2.setName("2");
        Thread thread3 = new Thread(ticket);
        thread3.setName("3");
        Thread thread4 = new Thread(ticket);
        thread4.setName("4");

        thread2.start();
        thread.start();
        thread3.start();
        thread4.start();
    }

    static class Ticket implements Runnable {

        private static Integer num = 10000;

        private Integer orderId;

        public Ticket(Integer orderId) {
            this.orderId = orderId;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        private Object lock = new Object();

        @Override
        public void run() {
            synchronized (lock) {
                while (num > 0) {
                    System.out.println("线程" + Thread.currentThread().getName() + "，卖了第" + num + "张票");
                    num --;
                }
            }
        }
    }
}
