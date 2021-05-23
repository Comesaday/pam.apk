package cn.comesaday.avt.example.lock.local.thread;

import cn.comesaday.avt.example.lock.distriuted.common.RedisLock;
import cn.comesaday.avt.example.lock.local.controller.LockController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-05-12 14:44
 */
public class LockThread implements Runnable {

    private RedisLock redisLock;

    public LockThread(RedisLock redisLock) {
        this.redisLock = redisLock;
    }

    private final static Logger logger = LoggerFactory.getLogger(LockThread.class);

    @Override
    public void run() {
        try {
            Boolean kucun = redisLock.lock("kucun", (long) (2 * 1000));
            if (!kucun) {
                logger.info("获取分布式锁失败");
                return;
            }
            int num = --LockController.number;
            if (num < 0) {
                logger.info("库存不足");
                return;
            }
            logger.info("完成库存-1，剩余库存:" + num);
        } catch (Exception e) {
            logger.info("操作库存失败！");
        } finally {
            redisLock.unlock("kucun");
        }
    }
}
