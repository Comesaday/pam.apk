package cn.comesaday.example.count;

import org.apache.commons.lang.RandomStringUtils;

import java.util.UUID;

/**
 * <描述> ThreadCount
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-21 18:34
 */
public class ThreadCount {

    public static void main(String[] args) throws Exception {
        System.out.println(RandomStringUtils.randomNumeric(10));
//        CountDownLatch countDownLatch = new CountDownLatch(4);
//        new Thread() {
//            @Override
//            public void run() {
//                countDownLatch.countDown();
//                System.out.println(Thread.currentThread().getName());
//            }
//        }.start();
//        new Thread() {
//            @Override
//            public void run() {
//                countDownLatch.countDown();
//                System.out.println(Thread.currentThread().getName());
//            }
//        }.start();
//        new Thread() {
//            @Override
//            public void run() {
//                countDownLatch.countDown();
//                System.out.println(Thread.currentThread().getName());
//            }
//        }.start();
//        new Thread() {
//            @Override
//            public void run() {
//                countDownLatch.countDown();
//                System.out.println(Thread.currentThread().getName());
//            }
//        }.start();
//        countDownLatch.await();
//        System.out.println(countDownLatch.getCount());
    }
}