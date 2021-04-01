package cn.comesaday.avt.apply.schedule;

import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.avt.apply.service.AskInfoService;
import cn.comesaday.avt.apply.thread.DoAskScanThread;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.common.util.DateUtil;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class AskSchedule {

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(AskSchedule.class);

    @Autowired
    private AskInfoService askInfoService;

    // 线程执行池
    private final static ExecutorService executorService = Executors.newFixedThreadPool(NumConstant.I10);

    /**
     * <说明> 扫描申请表，创建流程
     * @author ChenWei
     * @date 2021/4/1 19:00
     * @return JsonResult
     */
    public JsonResult doAskScan() {
        JsonResult result = new JsonResult();
        try {
            // 获取所有就绪的申请信息
            logger.info("{}:查询就绪申请内容", DateUtil.formatLDate(new Date()));
            List<AskInfo> askInfos = askInfoService
                    .findAllByProperty("status", NumConstant.I2);
            if (CollectionUtils.isEmpty(askInfos)) {
                return result.setSuccess("此次未查询到就绪信息");
            }
            // 开始创建流程实例
            result = doAskProcess(askInfos);
        } catch (Exception e) {
            logger.error("用户申请计划执行异常" + e.getMessage(), e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * <说明> 针对申请信息生成流程实例
     * @param askInfos List<AskInfo>
     * @author ChenWei
     * @date 2021/4/1 19:15
     * @return cn.comesaday.coe.core.basic.bean.result.JsonResult
     */
    private JsonResult doAskProcess(List<AskInfo> askInfos) {
        List<Future<JsonResult>> results = new LinkedList<>();
        for (AskInfo askInfo : askInfos) {
            DoAskScanThread doAskScanThread = new DoAskScanThread(askInfo);
            Future<JsonResult> result = executorService.submit(doAskScanThread);
            results.add(result);
        }
        for (Future<JsonResult> future : results) {
            try {
                // 时间：5秒
                future.get(NumConstant.I5, TimeUnit.MINUTES);
            } catch (TimeoutException e) {
                logger.error("提交申请信息future等待线程超时异常..." + e);
            } catch (InterruptedException e) {
                logger.error("提交申请信息future等待线程中断..." + e);
            } catch (ExecutionException e) {
                logger.error("提交申请信息线程池执行异常..." + e);
            } catch (Exception e) {
                logger.error("提交申请信息线程池异常..." + e);
            }
        }
        return new JsonResult();
    }
}
