package cn.comesaday.avt.example.lock.controller;

import cn.comesaday.avt.example.lock.thread.LockThread;
import cn.comesaday.coe.common.constant.NumConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
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

    public static Integer number = 0;

    ReadWriteLock rwl = new ReentrantReadWriteLock();

    // 线程执行池
    private final static ExecutorService executorService = Executors.newFixedThreadPool(NumConstant.I10);

    @RequestMapping("/test")
    public void lockTest() {
        for (int i = 0; i < 100; i++) {
            rwl.readLock().lock();
            LockThread thread = new LockThread();
            executorService.submit(thread);
            rwl.readLock().unlock();
        }
    }
}
