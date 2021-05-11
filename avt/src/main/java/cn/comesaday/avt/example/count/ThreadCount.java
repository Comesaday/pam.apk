package cn.comesaday.avt.example.count;

import java.util.concurrent.CountDownLatch;

/**
 * <描述> ThreadCount
 * <详细背景>
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
