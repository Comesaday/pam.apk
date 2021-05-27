package cn.comesaday.avt.example.retry;

import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <描述> RetryTemplate
 * <详细背景> 模板方法重试机制
 * @author: ChenWei
 * @CreateAt: 2021-05-27 10:41
 */
public abstract class RetryTemplate {

    private static final Logger logger = LoggerFactory.getLogger(RetryTemplate.class);

    // 默认重试次数
    private static final int DEFAULT_RETRY_TIMES = 5;

    // 默认间隔时间-过长容易导致线程阻塞
    private static final long DEFAULT_RETRY_SLEEP = 10;

    // 重试次数
    private int time = DEFAULT_RETRY_TIMES ;

    // 重试间隔时间
    private long sleep = DEFAULT_RETRY_SLEEP;

    // 当前重试
    private int currentTimes = 0;

    public RetryTemplate setTime(int time) {
        if (time < 0) {
            throw new IllegalArgumentException("time is not right");
        }
        this.time = time;
        return this;
    }

    public int getTime() {
        return time;
    }

    public RetryTemplate setSleep(long sleep) {
        if (sleep < 0) {
            throw new IllegalArgumentException("sleep is not right");
        }
        this.sleep = sleep;
        return this;
    }

    public long getSleep() {
        return sleep;
    }

    public abstract JsonResult doService() throws Throwable;

    /**
     * <说明> 同步执行
     * @author ChenWei
     * @date 2021/5/27 14:06
     * @return JsonResult
     */
    public JsonResult execute() throws InterruptedException {
        // 包括第一次执行和重试次数
        Throwable throwable = null;
        for (int execute = 0; execute <= time; execute++) {
            try {
                currentTimes = execute;
                return doService();
            } catch (Throwable e) {
                throwable = e;
                this.printe(execute, e);
                Thread.sleep(sleep);
            }
        }
        return new JsonResult(false, throwable.getMessage());
    }

    private void printe(int execute, Throwable e) {
        if (execute == 0) {
            // 首次执行出错
            logger.error("业务代码执行出错:{}", e.getMessage());
        } else {
            // 重试几次出错
            logger.error("[{}]业务代码执行出错:{}", currentTimes, e.getMessage());
        }
    }

    /**
     * <说明> 异步执行
     * @param executorService ExecutorService
     * @author ChenWei
     * @date 2021/5/27 14:05
     * @return JsonResult
     */
    public JsonResult submit(ExecutorService executorService) throws Exception {
        if (null == executorService) {
            throw new IllegalArgumentException("executorService not allow null");
        }
        return executorService.submit((Callable<JsonResult>) () -> execute())
                .get(this.sleep, TimeUnit.MILLISECONDS);
    }
}
