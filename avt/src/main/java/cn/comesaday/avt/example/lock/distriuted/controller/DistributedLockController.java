package cn.comesaday.avt.example.lock.distriuted.controller;

import cn.comesaday.avt.example.lock.distriuted.common.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2021-05-15 16:46
 */
@RestController
@RequestMapping("/example/distributed/lock/")
public class DistributedLockController {

    @Autowired
    private RedisLock redisLock;

    private static Integer number = 0;

    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    private final static Logger logger = LoggerFactory.getLogger(DistributedLockController.class);

    @RequestMapping("/test")
    public void test() throws Exception {
        for (int i = 0; i < 100 ; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Boolean lock = redisLock.lock("aaa","222", 4L);
                    if (lock) {
                        logger.info("线程:{},获取到锁", Thread.currentThread().getName());
                    } else {
                        logger.info("线程:{},未获取到锁", Thread.currentThread().getName());
                    }
                }
            });
            Thread.sleep(1L);
        }
    }
}
