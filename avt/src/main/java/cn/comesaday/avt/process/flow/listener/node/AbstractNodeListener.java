package cn.comesaday.avt.process.flow.listener.node;

import cn.comesaday.avt.business.apply.model.ApplyInfo;
import cn.comesaday.avt.process.flow.handler.DefaultFlowHandler;
import org.activiti.engine.delegate.TaskListener;

/**
 * <Description> AbstractNodeListener
 * @author ChenWei
 * @CreateAt 2021-04-22 21:29
 */
public abstract class AbstractNodeListener
        extends DefaultFlowHandler implements TaskListener {

    /**
     * <说明> 更新主表数据
     * @param applyInfo ApplyInfo
     * @param applyTrackId 版本id
     * @author ChenWei
     * @date 2021/4/28 14:25
     * @return void
     */
    protected abstract void updateMainInfo(ApplyInfo applyInfo, Long applyTrackId);
}
