package cn.comesaday.avt.example.lock.local.controller;

import cn.comesaday.avt.example.lock.distriuted.common.RedisLock;
import cn.comesaday.avt.example.lock.local.thread.LockThread;
import cn.comesaday.coe.common.constant.NumConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <描述>
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-12 14:46
 */
@RestController
@RequestMapping("/example/lock")
public class LockController {

    public static Integer number = 100;

    // 本地锁-内部维护了读锁和写锁-读锁可共享，写锁互斥
    ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);

    // 本地锁-完全的独享锁
    ReentrantLock reentrantLock = new ReentrantLock();

    @Autowired
    private RedisLock redisLock;

    // 线程执行池
    private final static ExecutorService executorService = Executors.newFixedThreadPool(NumConstant.I10);

    @RequestMapping("/test")
    public void lockTest() {
        for (int i = 0; i < 150; i++) {
            LockThread thread = new LockThread(redisLock);
            executorService.submit(thread);
        }
    }
}
