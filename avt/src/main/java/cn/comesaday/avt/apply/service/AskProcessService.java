package cn.comesaday.avt.apply.service;

import cn.comesaday.avt.apply.model.AskProcess;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * <描述> 申请工作流开始处理
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-01 17:23
 */
@Service
public class AskProcessService extends BaseService<AskProcess, Long> {

    /**
     * <说明> 查询流程信息
     * @param askId Long
     * @author ChenWei
     * @date 2021/4/7 17:08
     * @return AskProcess
     */
    public AskProcess queryAskProcess(Long askId) {
        AskProcess askProcess = this.findByProperty("askId", askId);
        if (null == askProcess) {
            return new AskProcess(askId);
        }
        return askProcess;
    }
}