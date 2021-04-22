package cn.comesaday.example.thread.job;

import cn.comesaday.avt.business.apply.model.ApplyInfo;
import cn.comesaday.avt.business.apply.service.ApplyService;
import cn.comesaday.avt.process.water.service.WaterService;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.common.util.DateUtil;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import cn.comesaday.coe.core.basic.bean.result.Result;
import cn.comesaday.example.thread.helper.JobHelper;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <描述> 定时任务
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-01 18:59
 */
@Service
@Transactional
@EnableScheduling
public class AskJob {

    private final static String ASK_SCHEDULE_KEY = "ASK_SCHEDULE_KEY";

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(AskJob.class);

    @Autowired
    private ApplyService applyService;

    @Autowired
    private JobHelper jobHelper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private WaterService processInfoService;

    // 线程执行池
    private final static ExecutorService executorService = Executors.newFixedThreadPool(NumConstant.I10);

    /**
     * <说明> 扫描申请表，创建流程
     * @author ChenWei
     * @date 2021/4/1 19:00
     * @return JsonResult
     */
//    @Scheduled(cron = "0/60 * * * * ? ") // 间隔5秒执行
    public JsonResult doAskScan() {
        try {
            if (jobHelper.getJobLock(ASK_SCHEDULE_KEY)) {
                return Result.fail(ASK_SCHEDULE_KEY + "锁被占用");
            }
            jobHelper.setJobLock(ASK_SCHEDULE_KEY, ASK_SCHEDULE_KEY);
            // 获取所有就绪的申请信息
            logger.info("{}:查询就绪申请内容", DateUtil.formatLDate(new Date()));
            List<ApplyInfo> applyInfos = applyService
                    .findAllByProperty("status", NumConstant.I1);
            if (CollectionUtils.isEmpty(applyInfos)) {
                return Result.fail("此次未查询到就绪信息");
            }
            // 开始创建流程实例
            return doAskProcess(applyInfos);
        } catch (Exception e) {
            logger.error("用户申请计划执行异常" + e.getMessage(), e);
            return Result.fail(e.getMessage());
        } finally {
            jobHelper.removeJobLock(ASK_SCHEDULE_KEY);
        }
    }

    /**
     * <说明> 针对申请信息生成流程实例
     * @param applyInfos List<AskInfo>
     * @author ChenWei
     * @date 2021/4/1 19:15
     * @return cn.comesaday.coe.core.basic.bean.result.JsonResult
     */
    private JsonResult doAskProcess(List<ApplyInfo> applyInfos) {
        List<Future<JsonResult>> results = new LinkedList<>();
        for (ApplyInfo applyInfo : applyInfos) {
            DoAskScanThread doAskScanThread = new DoAskScanThread(applyInfo);
            Future<JsonResult> result = executorService.submit(doAskScanThread);
            results.add(result);
        }
        List<JsonResult> list = new ArrayList<>();
        for (Future<JsonResult> future : results) {
            JsonResult result = null;
            String message = "";
            try {
                // 时间：5秒
                result = future.get(NumConstant.I5, TimeUnit.MINUTES);
            } catch (TimeoutException e) {
                logger.error("future等待线程超时异常..." + e);
                message = "future等待线程超时异常...";
            } catch (InterruptedException e) {
                logger.error("future等待线程中断..." + e);
                message = "future等待线程中断...";
            } catch (ExecutionException e) {
                logger.error("线程池执行异常..." + e);
                message = "线程池执行异常...";
            } catch (Exception e) {
                logger.error("线程池异常..." + e);
                message = "线程池异常...";
            } finally {
                if (null == result) {
                    result = new JsonResult(Boolean.FALSE, message);
                }
            }
            list.add(result);
        }
        return new JsonResult(Boolean.TRUE, list);
    }

    /**
     * <描述> DoAskScanThread
     * <详细背景>
     * @author: ChenWei
     * @CreateAt: 2021-04-01 19:10
     */
    public class DoAskScanThread implements Callable<JsonResult> {

        private ApplyInfo applyInfo;

        public DoAskScanThread(ApplyInfo applyInfo) {
            this.applyInfo = applyInfo;
        }

        @Override
        public JsonResult call() {
            JsonResult result = new JsonResult();
            try {
            } catch (Exception e) {
            } finally {
            }
            return result;
        }
    }
}
