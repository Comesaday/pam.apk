package cn.comesaday.avt.example.counter.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * <描述> ThreadCount
 * <详细背景> 线程计数器，线程执行完计数器减一。主线程await等待子线程都执行完成
 * @author: ChenWei
 * @CreateAt: 2021-04-21 18:34
 */
public class ThreadCount {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        new Thread() {
            @Override
            public void run() {
                threadLocal.set(String.valueOf(countDownLatch.getCount()));
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName());
                System.out.println(threadLocal.get());
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                threadLocal.set(String.valueOf(countDownLatch.getCount()));
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName());
                System.out.println(threadLocal.get());
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                threadLocal.set(String.valueOf(countDownLatch.getCount()));
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName());
                System.out.println(threadLocal.get());
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                threadLocal.set(String.valueOf(countDownLatch.getCount()));
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName());
                System.out.println(threadLocal.get());
            }
        }.start();
        countDownLatch.await();
    }
}
